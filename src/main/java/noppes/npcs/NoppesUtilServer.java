package noppes.npcs;

import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.MobSpawnerBaseLogic.WeightedRandomMinecart;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NBTTags;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPlayerData;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerBankData;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerDialogData;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.PlayerTransportData;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.TransportCategory;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.TransportLocation;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleTransporter;
import noppes.npcs.scripted.ScriptEventDialog;

public class NoppesUtilServer {

   private static HashMap editingQuests = new HashMap();


   public static void setEditingNpc(EntityPlayer player, EntityNPCInterface npc) {
      PlayerData data = PlayerDataController.instance.getPlayerData(player);
      data.editingNpc = npc;
      if(npc != null) {
         Server.sendData((EntityPlayerMP)player, EnumPacketClient.EDIT_NPC, new Object[]{Integer.valueOf(npc.getEntityId())});
      }

   }

   public static EntityNPCInterface getEditingNpc(EntityPlayer player) {
      PlayerData data = PlayerDataController.instance.getPlayerData(player);
      return data.editingNpc;
   }

   public static void setEditingQuest(EntityPlayer player, Quest quest) {
      editingQuests.put(player.getCommandSenderName(), quest);
   }

   public static Quest getEditingQuest(EntityPlayer player) {
      return (Quest)editingQuests.get(player.getCommandSenderName());
   }

   public static void sendRoleData(EntityPlayer player, EntityNPCInterface npc) {
      if(npc.advanced.role != EnumRoleType.None) {
         NBTTagCompound comp = new NBTTagCompound();
         npc.roleInterface.writeToNBT(comp);
         comp.setInteger("EntityId", npc.getEntityId());
         comp.setInteger("Role", npc.advanced.role.ordinal());
         Server.sendData((EntityPlayerMP)player, EnumPacketClient.ROLE, new Object[]{comp});
      }
   }

   public static void sendFactionDataAll(EntityPlayerMP player) {
      HashMap map = new HashMap();
      Iterator var2 = FactionController.getInstance().factions.values().iterator();

      while(var2.hasNext()) {
         Faction faction = (Faction)var2.next();
         map.put(faction.name, Integer.valueOf(faction.id));
      }

      sendScrollData(player, map);
   }

   public static void sendBankDataAll(EntityPlayerMP player) {
      HashMap map = new HashMap();
      Iterator var2 = BankController.getInstance().banks.values().iterator();

      while(var2.hasNext()) {
         Bank bank = (Bank)var2.next();
         map.put(bank.name, Integer.valueOf(bank.id));
      }

      sendScrollData(player, map);
   }

   public static void openDialog(EntityPlayer player, EntityNPCInterface npc, Dialog dia) {
      Dialog dialog = dia.copy(player);
      Server.sendData((EntityPlayerMP)player, EnumPacketClient.DIALOG, new Object[]{Integer.valueOf(npc.getEntityId()), dialog.writeToNBT(new NBTTagCompound())});
      dia.factionOptions.addPoints(player);
      if(dialog.hasQuest()) {
         PlayerQuestController.addActiveQuest(dialog.getQuest(), player);
      }

      if(!dialog.command.isEmpty()) {
         runCommand(player, npc.getCommandSenderName(), dialog.command);
      }

      if(dialog.mail.isValid()) {
         PlayerDataController.instance.addPlayerMessage(player.getCommandSenderName(), dialog.mail);
      }

      PlayerDialogData data = PlayerDataController.instance.getPlayerData(player).dialogData;
      if(!data.dialogsRead.contains(Integer.valueOf(dialog.id))) {
         data.dialogsRead.add(Integer.valueOf(dialog.id));
      }

      setEditingNpc(player, npc);
      npc.script.callScript(EnumScriptType.DIALOG, new Object[]{"event", new ScriptEventDialog(), "player", player, "dialog", Integer.valueOf(dialog.id)});
   }

   public static void runCommand(EntityPlayer player, String name, String command) {
      runCommand(player, name, command, player);
   }

   public static void runCommand(EntityLivingBase executer, String name, String command, EntityPlayer player) {
      if(player != null) {
         command = command.replace("@dp", player.getCommandSenderName());
      }

      TileEntityCommandBlock tile = new TileEntityCommandBlock();
      tile.setWorldObj(executer.worldObj);
      tile.xCoord = MathHelper.floor_double(executer.posX);
      tile.yCoord = MathHelper.floor_double(executer.posY);
      tile.zCoord = MathHelper.floor_double(executer.posZ);
      CommandBlockLogic logic = tile.func_145993_a();
      logic.setCommand(command);
      logic.func_145754_b("@" + name);
      logic.func_145755_a(executer.worldObj);
   }

   public static void consumeItemStack(int i, EntityPlayer player) {
      ItemStack item = player.inventory.getCurrentItem();
      if(!player.capabilities.isCreativeMode && item != null) {
         --item.stackSize;
         if(item.stackSize <= 0) {
            player.destroyCurrentEquippedItem();
         }

      }
   }

   public static DataOutputStream getDataOutputStream(ByteArrayOutputStream stream) throws IOException {
      return new DataOutputStream(new GZIPOutputStream(stream));
   }

   public static void sendOpenGui(EntityPlayer player, EnumGuiType gui, EntityNPCInterface npc) {
      sendOpenGui(player, gui, npc, 0, 0, 0);
   }

   public static void sendOpenGui(EntityPlayer player, EnumGuiType gui, EntityNPCInterface npc, int i, int j, int k) {
      if(player instanceof EntityPlayerMP) {
         setEditingNpc(player, npc);
         sendExtraData(player, npc, gui, i, j, k);
         if(CustomNpcs.proxy.getServerGuiElement(gui.ordinal(), player, player.worldObj, i, j, k) != null) {
            player.openGui(CustomNpcs.instance, gui.ordinal(), player.worldObj, i, j, k);
         } else {
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI, new Object[]{Integer.valueOf(gui.ordinal()), Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)});
            ArrayList list = getScrollData(player, gui, npc);
            if(list != null && !list.isEmpty()) {
               Server.sendData((EntityPlayerMP)player, EnumPacketClient.SCROLL_LIST, new Object[]{list});
            }
         }
      }
   }

   private static void sendExtraData(EntityPlayer player, EntityNPCInterface npc, EnumGuiType gui, int i, int j, int k) {
      if(gui == EnumGuiType.PlayerFollower || gui == EnumGuiType.PlayerFollowerHire || gui == EnumGuiType.PlayerTrader || gui == EnumGuiType.PlayerTransporter) {
         sendRoleData(player, npc);
      }

   }

   private static ArrayList getScrollData(EntityPlayer player, EnumGuiType gui, EntityNPCInterface npc) {
      if(gui == EnumGuiType.PlayerTransporter) {
         RoleTransporter role = (RoleTransporter)npc.roleInterface;
         ArrayList list = new ArrayList();
         TransportLocation location = role.getLocation();
         String name = role.getLocation().name;
         Iterator playerdata = location.category.getDefaultLocations().iterator();

         while(playerdata.hasNext()) {
            TransportLocation loc = (TransportLocation)playerdata.next();
            if(!list.contains(loc.name)) {
               list.add(loc.name);
            }
         }

         PlayerTransportData playerdata1 = PlayerDataController.instance.getPlayerData(player).transportData;
         Iterator loc2 = playerdata1.transports.iterator();

         while(loc2.hasNext()) {
            int i = ((Integer)loc2.next()).intValue();
            TransportLocation loc1 = TransportController.getInstance().getTransport(i);
            if(loc1 != null && location.category.locations.containsKey(Integer.valueOf(loc1.id)) && !list.contains(loc1.name)) {
               list.add(loc1.name);
            }
         }

         list.remove(name);
         return list;
      } else {
         return null;
      }
   }

   public static void spawnParticle(Entity entity, String particle, int dimension) {
      Server.sendAssociatedData(entity, EnumPacketClient.PARTICLE, new Object[]{Double.valueOf(entity.posX), Double.valueOf(entity.posY), Double.valueOf(entity.posZ), Float.valueOf(entity.height), Float.valueOf(entity.width), Float.valueOf(entity.yOffset), particle});
   }

   public static void deleteNpc(EntityNPCInterface npc, EntityPlayer player) {
      Server.sendAssociatedData(npc, EnumPacketClient.DELETE_NPC, new Object[]{Integer.valueOf(npc.getEntityId())});
   }

   public static void createMobSpawner(int x, int y, int z, NBTTagCompound comp, EntityPlayer player) {
      comp.removeTag("Pos");
      ServerCloneController.Instance.cleanTags(comp);
      if(comp.getString("id").equalsIgnoreCase("entityhorse")) {
         player.addChatMessage(new ChatComponentTranslation("Currently you cant create horse spawner, its a minecraft bug", new Object[0]));
      } else {
         player.worldObj.setBlock(x, y, z, Blocks.mob_spawner);
         TileEntityMobSpawner tile = (TileEntityMobSpawner)player.worldObj.getTileEntity(x, y, z);
         MobSpawnerBaseLogic logic = tile.func_145881_a();
         WeightedRandomMinecart var10001;
         logic.getClass();
         var10001 = logic.new WeightedRandomMinecart(comp, comp.getString("id"));
         logic.setRandomEntity(var10001);
      }
   }

   public static void sendQuestCategoryData(EntityPlayerMP player) {
      HashMap map = new HashMap();
      Iterator var2 = QuestController.instance.categories.values().iterator();

      while(var2.hasNext()) {
         QuestCategory category = (QuestCategory)var2.next();
         map.put(category.title, Integer.valueOf(category.id));
      }

      sendScrollData(player, map);
   }

   public static void sendPlayerData(EnumPlayerData type, EntityPlayerMP player, String name) throws IOException {
      HashMap map = new HashMap();
      if(type == EnumPlayerData.Players) {
         File playerdata = PlayerDataController.instance.getSaveDir();
         Iterator data = PlayerDataController.instance.getUsernameData().keySet().iterator();

         while(data.hasNext()) {
            String username = (String)data.next();
            map.put(username, Integer.valueOf(0));
         }
      } else {
         PlayerData playerdata1 = PlayerDataController.instance.getDataFromUsername(name);
         int factionId;
         Iterator username1;
         if(type == EnumPlayerData.Dialog) {
            PlayerDialogData data1 = playerdata1.dialogData;
            username1 = data1.dialogsRead.iterator();

            while(username1.hasNext()) {
               factionId = ((Integer)username1.next()).intValue();
               Dialog faction = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(factionId));
               if(faction != null) {
                  map.put(faction.category.title + ": " + faction.title, Integer.valueOf(factionId));
               }
            }
         } else if(type == EnumPlayerData.Quest) {
            PlayerQuestData data2 = playerdata1.questData;
            username1 = data2.activeQuests.keySet().iterator();

            Quest faction1;
            while(username1.hasNext()) {
               factionId = ((Integer)username1.next()).intValue();
               faction1 = (Quest)QuestController.instance.quests.get(Integer.valueOf(factionId));
               if(faction1 != null) {
                  map.put(faction1.category.title + ": " + faction1.title + "(Active quest)", Integer.valueOf(factionId));
               }
            }

            username1 = data2.finishedQuests.keySet().iterator();

            while(username1.hasNext()) {
               factionId = ((Integer)username1.next()).intValue();
               faction1 = (Quest)QuestController.instance.quests.get(Integer.valueOf(factionId));
               if(faction1 != null) {
                  map.put(faction1.category.title + ": " + faction1.title + "(Finished quest)", Integer.valueOf(factionId));
               }
            }
         } else if(type == EnumPlayerData.Transport) {
            PlayerTransportData data3 = playerdata1.transportData;
            username1 = data3.transports.iterator();

            while(username1.hasNext()) {
               factionId = ((Integer)username1.next()).intValue();
               TransportLocation faction2 = TransportController.getInstance().getTransport(factionId);
               if(faction2 != null) {
                  map.put(faction2.category.title + ": " + faction2.name, Integer.valueOf(factionId));
               }
            }
         } else if(type == EnumPlayerData.Bank) {
            PlayerBankData data4 = playerdata1.bankData;
            username1 = data4.banks.keySet().iterator();

            while(username1.hasNext()) {
               factionId = ((Integer)username1.next()).intValue();
               Bank faction3 = (Bank)BankController.getInstance().banks.get(Integer.valueOf(factionId));
               if(faction3 != null) {
                  map.put(faction3.name, Integer.valueOf(factionId));
               }
            }
         } else if(type == EnumPlayerData.Factions) {
            PlayerFactionData data5 = playerdata1.factionData;
            username1 = data5.factionData.keySet().iterator();

            while(username1.hasNext()) {
               factionId = ((Integer)username1.next()).intValue();
               Faction faction4 = (Faction)FactionController.getInstance().factions.get(Integer.valueOf(factionId));
               if(faction4 != null) {
                  map.put(faction4.name + "(" + data5.getFactionPoints(factionId) + ")", Integer.valueOf(factionId));
               }
            }
         }
      }

      sendScrollData(player, map);
   }

   public static void removePlayerData(ByteBuf buffer, EntityPlayerMP player) throws IOException {
      int id = buffer.readInt();
      if(EnumPlayerData.values().length > id) {
         String name = Server.readString(buffer);
         EnumPlayerData type = EnumPlayerData.values()[id];
         EntityPlayerMP pl = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(name);
         PlayerData playerdata = null;
         if(pl == null) {
            playerdata = PlayerDataController.instance.getDataFromUsername(name);
         } else {
            playerdata = PlayerDataController.instance.getPlayerData(pl);
         }

         if(type == EnumPlayerData.Players) {
            File data = new File(PlayerDataController.instance.getSaveDir(), playerdata.uuid + ".json");
            if(data.exists()) {
               data.delete();
            }

            if(pl != null) {
               playerdata.setNBT(new NBTTagCompound());
               sendPlayerData(type, player, name);
               playerdata.saveNBTData((NBTTagCompound)null);
               return;
            }
         }

         if(type == EnumPlayerData.Quest) {
            PlayerQuestData data1 = playerdata.questData;
            int questId = buffer.readInt();
            data1.activeQuests.remove(Integer.valueOf(questId));
            data1.finishedQuests.remove(Integer.valueOf(questId));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if(type == EnumPlayerData.Dialog) {
            PlayerDialogData data2 = playerdata.dialogData;
            data2.dialogsRead.remove(Integer.valueOf(buffer.readInt()));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if(type == EnumPlayerData.Transport) {
            PlayerTransportData data3 = playerdata.transportData;
            data3.transports.remove(Integer.valueOf(buffer.readInt()));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if(type == EnumPlayerData.Bank) {
            PlayerBankData data4 = playerdata.bankData;
            data4.banks.remove(Integer.valueOf(buffer.readInt()));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if(type == EnumPlayerData.Factions) {
            PlayerFactionData data5 = playerdata.factionData;
            data5.factionData.remove(Integer.valueOf(buffer.readInt()));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         sendPlayerData(type, player, name);
      }
   }

   public static void sendRecipeData(EntityPlayerMP player, int size) {
      HashMap map = new HashMap();
      Iterator var3;
      RecipeCarpentry recipe;
      if(size == 3) {
         var3 = RecipeController.instance.globalRecipes.values().iterator();

         while(var3.hasNext()) {
            recipe = (RecipeCarpentry)var3.next();
            map.put(recipe.name, Integer.valueOf(recipe.id));
         }
      } else {
         var3 = RecipeController.instance.anvilRecipes.values().iterator();

         while(var3.hasNext()) {
            recipe = (RecipeCarpentry)var3.next();
            map.put(recipe.name, Integer.valueOf(recipe.id));
         }
      }

      sendScrollData(player, map);
   }

   public static void sendScrollData(EntityPlayerMP player, Map map) {
      HashMap send = new HashMap();
      Iterator var3 = map.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         send.put(key, map.get(key));
         if(send.size() == 100) {
            Server.sendData(player, EnumPacketClient.SCROLL_DATA_PART, new Object[]{send});
            send = new HashMap();
         }
      }

      Server.sendData(player, EnumPacketClient.SCROLL_DATA, new Object[]{send});
   }

   public static void sendDialogData(EntityPlayerMP player, DialogCategory category) {
      if(category != null) {
         HashMap map = new HashMap();
         Iterator var3 = category.dialogs.values().iterator();

         while(var3.hasNext()) {
            Dialog dialog = (Dialog)var3.next();
            map.put(dialog.title, Integer.valueOf(dialog.id));
         }

         sendScrollData(player, map);
      }
   }

   public static void sendQuestData(EntityPlayerMP player, QuestCategory category) {
      if(category != null) {
         HashMap map = new HashMap();
         Iterator var3 = category.quests.values().iterator();

         while(var3.hasNext()) {
            Quest quest = (Quest)var3.next();
            map.put(quest.title, Integer.valueOf(quest.id));
         }

         sendScrollData(player, map);
      }
   }

   public static void sendTransportCategoryData(EntityPlayerMP player) {
      HashMap map = new HashMap();
      Iterator var2 = TransportController.getInstance().categories.values().iterator();

      while(var2.hasNext()) {
         TransportCategory category = (TransportCategory)var2.next();
         map.put(category.title, Integer.valueOf(category.id));
      }

      sendScrollData(player, map);
   }

   public static void sendTransportData(EntityPlayerMP player, int categoryid) {
      TransportCategory category = (TransportCategory)TransportController.getInstance().categories.get(Integer.valueOf(categoryid));
      if(category != null) {
         HashMap map = new HashMap();
         Iterator var4 = category.locations.values().iterator();

         while(var4.hasNext()) {
            TransportLocation transport = (TransportLocation)var4.next();
            map.put(transport.name, Integer.valueOf(transport.id));
         }

         sendScrollData(player, map);
      }
   }

   public static void sendNpcDialogs(EntityPlayer player) {
      EntityNPCInterface npc = getEditingNpc(player);
      if(npc != null) {
         Iterator var2 = npc.dialogs.keySet().iterator();

         while(var2.hasNext()) {
            int pos = ((Integer)var2.next()).intValue();
            DialogOption option = (DialogOption)npc.dialogs.get(Integer.valueOf(pos));
            if(option != null && option.hasDialog()) {
               NBTTagCompound compound = option.writeNBT();
               compound.setInteger("Position", pos);
               Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI_DATA, new Object[]{compound});
            }
         }

      }
   }

   public static DialogOption setNpcDialog(int slot, int dialogId, EntityPlayer player) throws IOException {
      EntityNPCInterface npc = getEditingNpc(player);
      if(npc == null) {
         return null;
      } else {
         if(!npc.dialogs.containsKey(Integer.valueOf(slot))) {
            npc.dialogs.put(Integer.valueOf(slot), new DialogOption());
         }

         DialogOption option = (DialogOption)npc.dialogs.get(Integer.valueOf(slot));
         option.dialogId = dialogId;
         if(option.hasDialog()) {
            option.title = option.getDialog().title;
         }

         return option;
      }
   }

   public static void saveTileEntity(EntityPlayerMP player, NBTTagCompound compound) {
      int x = compound.getInteger("x");
      int y = compound.getInteger("y");
      int z = compound.getInteger("z");
      TileEntity tile = player.worldObj.getTileEntity(x, y, z);
      if(tile != null) {
         tile.readFromNBT(compound);
      }

   }

   public static void setRecipeGui(EntityPlayerMP player, RecipeCarpentry recipe) {
      if(recipe != null) {
         if(player.openContainer instanceof ContainerManageRecipes) {
            ContainerManageRecipes container = (ContainerManageRecipes)player.openContainer;
            container.setRecipe(recipe);
            Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{recipe.writeNBT()});
         }
      }
   }

   public static void sendBank(EntityPlayerMP player, Bank bank) {
      NBTTagCompound compound = new NBTTagCompound();
      bank.writeEntityToNBT(compound);
      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound});
      if(player.openContainer instanceof ContainerManageBanks) {
         ((ContainerManageBanks)player.openContainer).setBank(bank);
      }

      player.updateCraftingInventory(player.openContainer, player.openContainer.getInventory());
   }

   public static void sendNearbyNpcs(EntityPlayerMP player) {
      List npcs = player.worldObj.getEntitiesWithinAABB(EntityNPCInterface.class, player.boundingBox.expand(120.0D, 120.0D, 120.0D));
      HashMap map = new HashMap();
      Iterator var3 = npcs.iterator();

      while(var3.hasNext()) {
         EntityNPCInterface npc = (EntityNPCInterface)var3.next();
         if(!npc.isDead) {
            float distance = player.getDistanceToEntity(npc);
            DecimalFormat df = new DecimalFormat("#.#");
            String s = df.format((double)distance);
            if(distance < 10.0F) {
               s = "0" + s;
            }

            map.put(s + " : " + npc.display.name, Integer.valueOf(npc.getEntityId()));
         }
      }

      sendScrollData(player, map);
   }

   public static void sendGuiError(EntityPlayer player, int i) {
      Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI_ERROR, new Object[]{Integer.valueOf(i), new NBTTagCompound()});
   }

   public static void sendGuiClose(EntityPlayerMP player, int i, NBTTagCompound comp) {
      if(player.openContainer != player.inventoryContainer) {
         player.openContainer = player.inventoryContainer;
      }

      Server.sendData(player, EnumPacketClient.GUI_CLOSE, new Object[]{Integer.valueOf(i), comp});
   }

   public static void updateNpc(EntityNPCInterface npc) {
      NBTTagCompound compound = npc.writeSpawnData();
      compound.setInteger("EntityId", npc.getEntityId());
      Server.sendAssociatedData(npc, EnumPacketClient.UPDATE_NPC, new Object[]{compound});
   }

   public static Entity spawnClone(NBTTagCompound compound, int x, int y, int z, World worldObj) {
      ServerCloneController.Instance.cleanTags(compound);
      compound.setTag("Pos", NBTTags.nbtDoubleList(new double[]{(double)x + 0.5D, (double)(y + 1), (double)z + 0.5D}));
      Entity entity = EntityList.createEntityFromNBT(compound, worldObj);
      if(entity == null) {
         return null;
      } else {
         if(entity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.ai.startPos = new int[]{MathHelper.floor_double(npc.posX), MathHelper.floor_double(npc.posY), MathHelper.floor_double(npc.posZ)};
         }

         worldObj.spawnEntityInWorld(entity);
         return entity;
      }
   }

   public static boolean isOp(EntityPlayer player) {
      return MinecraftServer.getServer().getConfigurationManager().canSendCommands(player.getGameProfile());
   }

   public static void GivePlayerItem(Entity entity, EntityPlayer player, ItemStack item) {
      if(!entity.worldObj.isRemote && item != null) {
         item = item.copy();
         float f = 0.7F;
         double d = (double)(entity.worldObj.rand.nextFloat() * f) + (double)(1.0F - f);
         double d1 = (double)(entity.worldObj.rand.nextFloat() * f) + (double)(1.0F - f);
         double d2 = (double)(entity.worldObj.rand.nextFloat() * f) + (double)(1.0F - f);
         EntityItem entityitem = new EntityItem(entity.worldObj, entity.posX + d, entity.posY + d1, entity.posZ + d2, item);
         entityitem.delayBeforeCanPickup = 2;
         entity.worldObj.spawnEntityInWorld(entityitem);
         int i = item.stackSize;
         if(player.inventory.addItemStackToInventory(item)) {
            entity.worldObj.playSoundAtEntity(entityitem, "random.pop", 0.2F, ((entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.onItemPickup(entityitem, i);
            if(item.stackSize <= 0) {
               entityitem.setDead();
            }
         }

      }
   }

}
