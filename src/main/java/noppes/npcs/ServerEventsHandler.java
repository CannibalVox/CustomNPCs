package noppes.npcs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NPCSpawning;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.Server;
import noppes.npcs.blocks.tiles.TileBanner;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.items.ItemExcalibur;
import noppes.npcs.items.ItemShield;
import noppes.npcs.items.ItemSoulstoneEmpty;
import noppes.npcs.quests.QuestKill;
import noppes.npcs.roles.RoleFollower;

public class ServerEventsHandler {

   public static EntityVillager Merchant;
   public static Entity mounted;


   @SubscribeEvent
   public void invoke(EntityInteractEvent event) {
      ItemStack item = event.entityPlayer.getCurrentEquippedItem();
      if(item != null) {
         boolean isRemote = event.entityPlayer.worldObj.isRemote;
         boolean npcInteracted = event.target instanceof EntityNPCInterface;
         if(isRemote || !CustomNpcs.OpsOnly || MinecraftServer.getServer().getConfigurationManager().canSendCommands(event.entityPlayer.getGameProfile())) {
            if(!isRemote && item.getItem() == CustomItems.soulstoneEmpty && event.target instanceof EntityLivingBase) {
               ((ItemSoulstoneEmpty)item.getItem()).store((EntityLivingBase)event.target, item, event.entityPlayer);
            }

            CustomNpcsPermissions var10000;
            if(item.getItem() == CustomItems.wand && npcInteracted && !isRemote) {
               var10000 = CustomNpcsPermissions.Instance;
               if(!CustomNpcsPermissions.hasPermission(event.entityPlayer, "customnpcs.npc.gui")) {
                  return;
               }

               event.setCanceled(true);
               NoppesUtilServer.sendOpenGui(event.entityPlayer, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)event.target);
            } else if(item.getItem() == CustomItems.cloner && !isRemote && !(event.target instanceof EntityPlayer)) {
               NBTTagCompound player1 = new NBTTagCompound();
               if(!event.target.writeToNBTOptional(player1)) {
                  return;
               }

               PlayerData merchantrecipelist1 = PlayerDataController.instance.getPlayerData(event.entityPlayer);
               ServerCloneController.Instance.cleanTags(player1);
               if(!Server.sendData((EntityPlayerMP)event.entityPlayer, EnumPacketClient.CLONE, new Object[]{player1})) {
                  event.entityPlayer.addChatMessage(new ChatComponentText("Entity too big to clone"));
               }

               merchantrecipelist1.cloned = player1;
               event.setCanceled(true);
            } else if(item.getItem() == CustomItems.scripter && !isRemote && npcInteracted) {
               var10000 = CustomNpcsPermissions.Instance;
               if(!CustomNpcsPermissions.hasPermission(event.entityPlayer, "customnpcs.npc.gui")) {
                  return;
               }

               NoppesUtilServer.setEditingNpc(event.entityPlayer, (EntityNPCInterface)event.target);
               event.setCanceled(true);
               Server.sendData((EntityPlayerMP)event.entityPlayer, EnumPacketClient.GUI, new Object[]{Integer.valueOf(EnumGuiType.Script.ordinal())});
            } else if(item.getItem() == CustomItems.mount) {
               var10000 = CustomNpcsPermissions.Instance;
               if(!CustomNpcsPermissions.hasPermission(event.entityPlayer, "customnpcs.mounter")) {
                  return;
               }

               event.setCanceled(true);
               mounted = event.target;
               if(isRemote) {
                  CustomNpcs.proxy.openGui(MathHelper.floor_double(mounted.posX), MathHelper.floor_double(mounted.posY), MathHelper.floor_double(mounted.posZ), EnumGuiType.MobSpawnerMounter, event.entityPlayer);
               }
            } else if(item.getItem() == CustomItems.wand && event.target instanceof EntityVillager) {
               var10000 = CustomNpcsPermissions.Instance;
               if(!CustomNpcsPermissions.hasPermission(event.entityPlayer, "customnpcs.villager")) {
                  return;
               }

               event.setCanceled(true);
               Merchant = (EntityVillager)event.target;
               if(!isRemote) {
                  EntityPlayerMP player = (EntityPlayerMP)event.entityPlayer;
                  player.openGui(CustomNpcs.instance, EnumGuiType.MerchantAdd.ordinal(), player.worldObj, 0, 0, 0);
                  MerchantRecipeList merchantrecipelist = Merchant.getRecipes(player);
                  if(merchantrecipelist != null) {
                     Server.sendData(player, EnumPacketClient.VILLAGER_LIST, new Object[]{merchantrecipelist});
                  }
               }
            }

         }
      }
   }

   @SubscribeEvent
   public void invoke(LivingHurtEvent event) {
      if(event.entityLiving instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)event.entityLiving;
         if(!event.source.isUnblockable() && !event.source.isFireDamage()) {
            if(player.isBlocking()) {
               ItemStack item = player.getCurrentEquippedItem();
               if(item != null && item.getItem() instanceof ItemShield) {
                  if(((ItemShield)item.getItem()).material.getDamageVsEntity() >= player.getRNG().nextInt(9)) {
                     float damage = (float)item.getMetadata() + event.ammount;
                     item.damageItem((int)event.ammount, player);
                     if(damage > (float)item.getMaxDurability()) {
                        event.ammount = damage - (float)item.getMaxDurability();
                     } else {
                        event.ammount = 0.0F;
                        event.setCanceled(true);
                     }

                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void invoke(PlayerInteractEvent event) {
      EntityPlayer player = event.entityPlayer;
      Block block = player.worldObj.getBlock(event.x, event.y, event.z);
      if(event.action == Action.LEFT_CLICK_BLOCK && player.getHeldItem() != null && player.getHeldItem().getItem() == CustomItems.teleporter) {
         event.setCanceled(true);
      }

      RecipeController item;
      NBTTagList y;
      int meta;
      Iterator tile;
      RecipeCarpentry recipe;
      NBTTagCompound compound;
      NBTTagCompound var12;
      if(block == Blocks.crafting_table && event.action == Action.RIGHT_CLICK_BLOCK && !player.worldObj.isRemote) {
         item = RecipeController.instance;
         y = new NBTTagList();
         meta = 0;
         tile = item.globalRecipes.values().iterator();

         while(tile.hasNext()) {
            recipe = (RecipeCarpentry)tile.next();
            y.appendTag(recipe.writeNBT());
            ++meta;
            if(meta % 10 == 0) {
               compound = new NBTTagCompound();
               compound.setTag("recipes", y);
               Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, new Object[]{compound});
               y = new NBTTagList();
            }
         }

         if(meta % 10 != 0) {
            var12 = new NBTTagCompound();
            var12.setTag("recipes", y);
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, new Object[]{var12});
         }

         Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_WORKBENCH, new Object[0]);
      }

      if(block == CustomItems.carpentyBench && event.action == Action.RIGHT_CLICK_BLOCK && !player.worldObj.isRemote) {
         item = RecipeController.instance;
         y = new NBTTagList();
         meta = 0;
         tile = item.anvilRecipes.values().iterator();

         while(tile.hasNext()) {
            recipe = (RecipeCarpentry)tile.next();
            y.appendTag(recipe.writeNBT());
            ++meta;
            if(meta % 10 == 0) {
               compound = new NBTTagCompound();
               compound.setTag("recipes", y);
               Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, new Object[]{compound});
               y = new NBTTagList();
            }
         }

         if(meta % 10 != 0) {
            var12 = new NBTTagCompound();
            var12.setTag("recipes", y);
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, new Object[]{var12});
         }

         Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_CARPENTRYBENCH, new Object[0]);
      }

      if((block == CustomItems.banner || block == CustomItems.wallBanner || block == CustomItems.sign) && event.action == Action.RIGHT_CLICK_BLOCK) {
         ItemStack var10 = player.inventory.getCurrentItem();
         if(var10 == null || var10.getItem() == null) {
            return;
         }

         int var11 = event.y;
         meta = player.worldObj.getBlockMetadata(event.x, event.y, event.z);
         if(meta >= 7) {
            --var11;
         }

         TileBanner var13 = (TileBanner)player.worldObj.getTileEntity(event.x, var11, event.z);
         if(!var13.canEdit()) {
            if(var10.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, "customnpcs.editblocks")) {
               var13.time = System.currentTimeMillis();
               if(player.worldObj.isRemote) {
                  player.addChatComponentMessage(new ChatComponentTranslation("availability.editIcon", new Object[0]));
               }
            }

            return;
         }

         if(!player.worldObj.isRemote) {
            var13.icon = var10.copy();
            player.worldObj.markBlockForUpdate(event.x, var11, event.z);
            event.setCanceled(true);
         }
      }

   }

   @SubscribeEvent
   public void invoke(LivingDeathEvent event) {
      if(!event.entityLiving.worldObj.isRemote) {
         if(event.source.getEntity() != null) {
            if(event.source.getEntity() instanceof EntityPlayer) {
               this.doExcalibur((EntityPlayer)event.source.getEntity(), event.entityLiving);
            }

            if(event.source.getEntity() instanceof EntityNPCInterface && event.entityLiving != null) {
               EntityNPCInterface data = (EntityNPCInterface)event.source.getEntity();
               Line line = data.advanced.getKillLine();
               if(line != null) {
                  data.saySurrounding(line.formatTarget(event.entityLiving));
               }

               data.script.callScript(EnumScriptType.KILLS, new Object[]{"target", event.entityLiving});
            }

            EntityPlayer data1 = null;
            if(event.source.getEntity() instanceof EntityPlayer) {
               data1 = (EntityPlayer)event.source.getEntity();
            } else if(event.source.getEntity() instanceof EntityNPCInterface && ((EntityNPCInterface)event.source.getEntity()).advanced.role == EnumRoleType.Follower) {
               data1 = ((RoleFollower)((EntityNPCInterface)event.source.getEntity()).roleInterface).owner;
            }

            if(data1 != null) {
               this.doQuest(data1, event.entityLiving, true);
               if(event.entityLiving instanceof EntityNPCInterface) {
                  this.doFactionPoints(data1, (EntityNPCInterface)event.entityLiving);
               }
            }
         }

         if(event.entityLiving instanceof EntityPlayer) {
            PlayerData data2 = PlayerDataController.instance.getPlayerData((EntityPlayer)event.entityLiving);
            data2.saveNBTData((NBTTagCompound)null);
         }

      }
   }

   private void doExcalibur(EntityPlayer player, EntityLivingBase entity) {
      ItemStack item = player.getCurrentEquippedItem();
      if(item != null && item.getItem() == CustomItems.excalibur) {
         Server.sendData((EntityPlayerMP)player, EnumPacketClient.PLAY_MUSIC, new Object[]{"customnpcs:songs.excalibur"});
         player.addChatMessage(new ChatComponentTranslation("<" + StatCollector.translateToLocal(item.getItem().getUnlocalizedName() + ".name") + "> " + ItemExcalibur.quotes[player.getRNG().nextInt(ItemExcalibur.quotes.length)], new Object[0]));
      }
   }

   private void doFactionPoints(EntityPlayer player, EntityNPCInterface npc) {
      npc.advanced.factions.addPoints(player);
   }

   private void doQuest(EntityPlayer player, EntityLivingBase entity, boolean all) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      boolean change = false;
      String entityName = EntityList.getEntityString(entity);
      Iterator var7 = playerdata.activeQuests.values().iterator();

      while(var7.hasNext()) {
         QuestData data = (QuestData)var7.next();
         if(data.quest.type == EnumQuestType.Kill || data.quest.type == EnumQuestType.AreaKill) {
            if(data.quest.type == EnumQuestType.AreaKill && all) {
               List name = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, entity.boundingBox.expand(10.0D, 10.0D, 10.0D));
               Iterator quest = name.iterator();

               while(quest.hasNext()) {
                  EntityPlayer killed = (EntityPlayer)quest.next();
                  if(killed != player) {
                     this.doQuest(killed, entity, false);
                  }
               }
            }

            String name1 = entityName;
            QuestKill quest1 = (QuestKill)data.quest.questInterface;
            if(quest1.targets.containsKey(entity.getCommandSenderName())) {
               name1 = entity.getCommandSenderName();
            } else if(!quest1.targets.containsKey(entityName)) {
               continue;
            }

            HashMap killed1 = quest1.getKilled(data);
            if(!killed1.containsKey(name1) || ((Integer)killed1.get(name1)).intValue() < ((Integer)quest1.targets.get(name1)).intValue()) {
               int amount = 0;
               if(killed1.containsKey(name1)) {
                  amount = ((Integer)killed1.get(name1)).intValue();
               }

               killed1.put(name1, Integer.valueOf(amount + 1));
               quest1.setKilled(data, killed1);
               change = true;
            }
         }
      }

      if(change) {
         playerdata.checkQuestCompletion(player, EnumQuestType.Kill);
      }
   }

   @SubscribeEvent
   public void pickUp(EntityItemPickupEvent event) {
      if(!event.entityPlayer.worldObj.isRemote) {
         PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(event.entityPlayer).questData;
         playerdata.checkQuestCompletion(event.entityPlayer, EnumQuestType.Item);
      }
   }

   @SubscribeEvent
   public void construct(EntityConstructing event) {
      if(event.entity.worldObj == null || !(event.entity instanceof EntityPlayer)) {
         ;
      }
   }

   @SubscribeEvent
   public void populateChunk(Post event) {
      NPCSpawning.performWorldGenSpawning(event.world, event.chunkX, event.chunkZ, event.rand);
   }
}
