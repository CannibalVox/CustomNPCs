package noppes.npcs.quests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.quests.QuestInterface;

public class QuestKill extends QuestInterface {

   public HashMap targets = new HashMap();


   public void readEntityFromNBT(NBTTagCompound compound) {
      this.targets = NBTTags.getStringIntegerMap(compound.getTagList("QuestDialogs", 10));
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setTag("QuestDialogs", NBTTags.nbtStringIntegerMap(this.targets));
   }

   public boolean isCompleted(EntityPlayer player) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(Integer.valueOf(super.questId));
      if(data == null) {
         return false;
      } else {
         HashMap killed = this.getKilled(data);
         if(killed.size() != this.targets.size()) {
            return false;
         } else {
            Iterator var5 = killed.keySet().iterator();

            String entity;
            do {
               if(!var5.hasNext()) {
                  return true;
               }

               entity = (String)var5.next();
            } while(this.targets.containsKey(entity) && ((Integer)this.targets.get(entity)).intValue() <= ((Integer)killed.get(entity)).intValue());

            return false;
         }
      }
   }

   public void handleComplete(EntityPlayer player) {}

   public Vector getQuestLogStatus(EntityPlayer player) {
      Vector vec = new Vector();
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(Integer.valueOf(super.questId));
      if(data == null) {
         return vec;
      } else {
         HashMap killed = this.getKilled(data);
         Iterator var6 = this.targets.keySet().iterator();

         while(var6.hasNext()) {
            String entityName = (String)var6.next();
            int amount = 0;
            if(killed.containsKey(entityName)) {
               amount = ((Integer)killed.get(entityName)).intValue();
            }

            String state = amount + "/" + this.targets.get(entityName);
            vec.add(entityName + ": " + state);
         }

         return vec;
      }
   }

   public HashMap getKilled(QuestData data) {
      return NBTTags.getStringIntegerMap(data.extraData.getTagList("Killed", 10));
   }

   public void setKilled(QuestData data, HashMap killed) {
      data.extraData.setTag("Killed", NBTTags.nbtStringIntegerMap(killed));
   }
}
