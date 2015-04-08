package noppes.npcs.controllers;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.controllers.Quest;

public class QuestData {

   public Quest quest;
   public boolean isCompleted;
   public NBTTagCompound extraData = new NBTTagCompound();


   public QuestData(Quest quest) {
      this.quest = quest;
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setBoolean("QuestCompleted", this.isCompleted);
      nbttagcompound.setTag("ExtraData", this.extraData);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.isCompleted = nbttagcompound.getBoolean("QuestCompleted");
      this.extraData = nbttagcompound.getCompoundTag("ExtraData");
   }
}
