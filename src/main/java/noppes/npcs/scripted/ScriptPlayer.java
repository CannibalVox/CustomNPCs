package noppes.npcs.scripted;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldSettings;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerDialogData;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.scripted.ScriptItemStack;
import noppes.npcs.scripted.ScriptLivingBase;
import noppes.npcs.util.ValueUtil;

public class ScriptPlayer extends ScriptLivingBase {

   protected EntityPlayerMP player;


   public ScriptPlayer(EntityPlayerMP player) {
      super(player);
      this.player = player;
   }

   public String getDisplayName() {
      return this.player.getDisplayName();
   }

   public String getName() {
      return this.player.getCommandSenderName();
   }

   public void setPosition(double x, double y, double z) {
      NoppesUtilPlayer.teleportPlayer(this.player, x, y, z, this.player.dimension);
   }

   public boolean hasFinishedQuest(int id) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(this.player).questData;
      return data.finishedQuests.containsKey(Integer.valueOf(id));
   }

   public boolean hasActiveQuest(int id) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(this.player).questData;
      return data.activeQuests.containsKey(Integer.valueOf(id));
   }

   public boolean hasReadDialog(int id) {
      PlayerDialogData data = PlayerDataController.instance.getPlayerData(this.player).dialogData;
      return data.dialogsRead.contains(Integer.valueOf(id));
   }

   public void startQuest(int id) {
      Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(id));
      if(quest != null) {
         PlayerData data = PlayerDataController.instance.getPlayerData(this.player);
         QuestData questdata = new QuestData(quest);
         data.questData.activeQuests.put(Integer.valueOf(id), questdata);
      }
   }

   public void finishQuest(int id) {
      Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(id));
      if(quest != null) {
         PlayerData data = PlayerDataController.instance.getPlayerData(this.player);
         data.questData.finishedQuests.put(Integer.valueOf(id), Long.valueOf(System.currentTimeMillis()));
      }
   }

   public void stopQuest(int id) {
      Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(id));
      if(quest != null) {
         PlayerData data = PlayerDataController.instance.getPlayerData(this.player);
         data.questData.activeQuests.remove(Integer.valueOf(id));
      }
   }

   public void removeQuest(int id) {
      Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(id));
      if(quest != null) {
         PlayerData data = PlayerDataController.instance.getPlayerData(this.player);
         data.questData.activeQuests.remove(Integer.valueOf(id));
         data.questData.finishedQuests.remove(Integer.valueOf(id));
      }
   }

   public int getType() {
      return 1;
   }

   public boolean typeOf(int type) {
      return type == 1?true:super.typeOf(type);
   }

   public void addFactionPoints(int faction, int points) {
      PlayerData data = PlayerDataController.instance.getPlayerData(this.player);
      data.factionData.increasePoints(faction, points);
   }

   public int getFactionPoints(int faction) {
      PlayerData data = PlayerDataController.instance.getPlayerData(this.player);
      return data.factionData.getFactionPoints(faction);
   }

   public void sendMessage(String message) {
      this.player.addChatMessage(new ChatComponentTranslation(NoppesStringUtils.formatText(message, new Object[]{this.player}), new Object[0]));
   }

   public int getMode() {
      return this.player.theItemInWorldManager.getGameType().getID();
   }

   public void setMode(int type) {
      this.player.setGameType(WorldSettings.getGameTypeById(type));
   }

   public int inventoryItemCount(ScriptItemStack item) {
      int i = 0;
      ItemStack[] var3 = this.player.inventory.mainInventory;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack is = var3[var5];
         if(is != null && is.isItemEqual(item.item)) {
            i += is.stackSize;
         }
      }

      return i;
   }

   public boolean removeItem(ScriptItemStack item, int amount) {
      int count = this.inventoryItemCount(item);
      if(amount > count) {
         return false;
      } else {
         if(count == amount) {
            this.removeAllItems(item);
         } else {
            for(int i = 0; i < this.player.inventory.mainInventory.length; ++i) {
               ItemStack is = this.player.inventory.mainInventory[i];
               if(is != null && is.isItemEqual(item.item)) {
                  if(amount <= is.stackSize) {
                     is.splitStack(amount);
                     break;
                  }

                  this.player.inventory.mainInventory[i] = null;
                  amount -= is.stackSize;
               }
            }
         }

         return true;
      }
   }

   public boolean removeItem(String id, int damage, int amount) {
      Item item = (Item)Item.itemRegistry.getObject(id);
      return item == null?false:this.removeItem(new ScriptItemStack(new ItemStack(item, 1, damage)), amount);
   }

   public boolean giveItem(ScriptItemStack item, int amount) {
      String itemname = Item.itemRegistry.getNameForObject(item.getMCItemStack().getItem());
      return this.giveItem(itemname, item.getItemDamage(), amount);
   }

   public boolean giveItem(String id, int damage, int amount) {
      Item item = (Item)Item.itemRegistry.getObject(id);
      return item == null?false:this.player.inventory.addItemStackToInventory(new ItemStack(item, amount, damage));
   }

   public void setSpawnpoint(int x, int y, int z) {
      x = ValueUtil.CorrectInt(x, -30000000, 30000000);
      z = ValueUtil.CorrectInt(z, -30000000, 30000000);
      y = ValueUtil.CorrectInt(y, 0, 256);
      this.player.setSpawnChunk(new ChunkCoordinates(x, y, z), true);
   }

   public void resetSpawnpoint() {
      this.player.setSpawnChunk((ChunkCoordinates)null, false);
   }

   public void removeAllItems(ScriptItemStack item) {
      for(int i = 0; i < this.player.inventory.mainInventory.length; ++i) {
         ItemStack is = this.player.inventory.mainInventory[i];
         if(is != null && is.isItemEqual(item.item)) {
            this.player.inventory.mainInventory[i] = null;
         }
      }

   }

   public boolean hasAchievement(String achievement) {
      StatBase statbase = StatList.getOneShotStat(achievement);
      return statbase != null && statbase instanceof Achievement?this.player.getStatFile().hasAchievementUnlocked((Achievement)statbase):false;
   }

   public boolean hasBukkitPermission(String permission) {
      return CustomNpcsPermissions.hasPermission(this.player, permission);
   }

   public int getExpLevel() {
      return this.player.experienceLevel;
   }

   public void setExpLevel(int level) {
      this.player.experienceLevel = level;
   }
}
