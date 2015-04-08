package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.roles.JobItemGiver;

public class PlayerItemGiverData {

   private HashMap itemgivers = new HashMap();
   private HashMap chained = new HashMap();


   public void loadNBTData(NBTTagCompound compound) {
      this.chained = NBTTags.getIntegerIntegerMap(compound.getTagList("ItemGiverChained", 10));
      this.itemgivers = NBTTags.getIntegerLongMap(compound.getTagList("ItemGiversList", 10));
   }

   public void saveNBTData(NBTTagCompound compound) {
      compound.setTag("ItemGiverChained", NBTTags.nbtIntegerIntegerMap(this.chained));
      compound.setTag("ItemGiversList", NBTTags.nbtIntegerLongMap(this.itemgivers));
   }

   public boolean hasInteractedBefore(JobItemGiver jobItemGiver) {
      return this.itemgivers.containsKey(Integer.valueOf(jobItemGiver.itemGiverId));
   }

   public long getTime(JobItemGiver jobItemGiver) {
      return ((Long)this.itemgivers.get(Integer.valueOf(jobItemGiver.itemGiverId))).longValue();
   }

   public void setTime(JobItemGiver jobItemGiver, long day) {
      this.itemgivers.put(Integer.valueOf(jobItemGiver.itemGiverId), Long.valueOf(day));
   }

   public int getItemIndex(JobItemGiver jobItemGiver) {
      return this.chained.containsKey(Integer.valueOf(jobItemGiver.itemGiverId))?((Integer)this.chained.get(Integer.valueOf(jobItemGiver.itemGiverId))).intValue():0;
   }

   public void setItemIndex(JobItemGiver jobItemGiver, int i) {
      this.chained.put(Integer.valueOf(jobItemGiver.itemGiverId), Integer.valueOf(i));
   }
}
