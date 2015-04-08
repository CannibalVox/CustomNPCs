package noppes.npcs.quests;

import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.quests.QuestInterface;

public class QuestLocation extends QuestInterface {

   public String location = "";
   public String location2 = "";
   public String location3 = "";


   public void readEntityFromNBT(NBTTagCompound compound) {
      this.location = compound.getString("QuestLocation");
      this.location2 = compound.getString("QuestLocation2");
      this.location3 = compound.getString("QuestLocation3");
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setString("QuestLocation", this.location);
      compound.setString("QuestLocation2", this.location2);
      compound.setString("QuestLocation3", this.location3);
   }

   public boolean isCompleted(EntityPlayer player) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(Integer.valueOf(super.questId));
      return data == null?false:this.getFound(data, 0);
   }

   public void handleComplete(EntityPlayer player) {}

   public Vector getQuestLogStatus(EntityPlayer player) {
      Vector vec = new Vector();
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(Integer.valueOf(super.questId));
      if(data == null) {
         return vec;
      } else {
         String found = StatCollector.translateToLocal("quest.found");
         String notfound = StatCollector.translateToLocal("quest.notfound");
         if(!this.location.isEmpty()) {
            vec.add(this.location + ": " + (this.getFound(data, 1)?found:notfound));
         }

         if(!this.location2.isEmpty()) {
            vec.add(this.location2 + ": " + (this.getFound(data, 2)?found:notfound));
         }

         if(!this.location3.isEmpty()) {
            vec.add(this.location3 + ": " + (this.getFound(data, 3)?found:notfound));
         }

         return vec;
      }
   }

   public boolean getFound(QuestData data, int i) {
      return i == 1?data.extraData.getBoolean("LocationFound"):(i == 2?data.extraData.getBoolean("Location2Found"):(i == 3?data.extraData.getBoolean("Location3Found"):(!this.location.isEmpty() && !data.extraData.getBoolean("LocationFound")?false:(!this.location2.isEmpty() && !data.extraData.getBoolean("Location2Found")?false:this.location3.isEmpty() || data.extraData.getBoolean("Location3Found")))));
   }

   public boolean setFound(QuestData data, String location) {
      if(location.equalsIgnoreCase(this.location) && !data.extraData.getBoolean("LocationFound")) {
         data.extraData.setBoolean("LocationFound", true);
         return true;
      } else if(location.equalsIgnoreCase(this.location2) && !data.extraData.getBoolean("LocationFound2")) {
         data.extraData.setBoolean("Location2Found", true);
         return true;
      } else if(location.equalsIgnoreCase(this.location3) && !data.extraData.getBoolean("LocationFound3")) {
         data.extraData.setBoolean("Location3Found", true);
         return true;
      } else {
         return false;
      }
   }
}
