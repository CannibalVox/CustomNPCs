package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;

public class PlayerFactionData {

   public HashMap factionData = new HashMap();


   public void loadNBTData(NBTTagCompound compound) {
      HashMap factionData = new HashMap();
      if(compound != null) {
         NBTTagList list = compound.getTagList("FactionData", 10);
         if(list != null) {
            for(int i = 0; i < list.tagCount(); ++i) {
               NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
               factionData.put(Integer.valueOf(nbttagcompound.getInteger("Faction")), Integer.valueOf(nbttagcompound.getInteger("Points")));
            }

            this.factionData = factionData;
         }
      }
   }

   public void saveNBTData(NBTTagCompound compound) {
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.factionData.keySet().iterator();

      while(var3.hasNext()) {
         int faction = ((Integer)var3.next()).intValue();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setInteger("Faction", faction);
         nbttagcompound.setInteger("Points", ((Integer)this.factionData.get(Integer.valueOf(faction))).intValue());
         list.appendTag(nbttagcompound);
      }

      compound.setTag("FactionData", list);
   }

   public int getFactionPoints(int id) {
      if(!this.factionData.containsKey(Integer.valueOf(id))) {
         Faction faction = FactionController.getInstance().getFaction(id);
         this.factionData.put(Integer.valueOf(id), Integer.valueOf(faction == null?-1:faction.defaultPoints));
      }

      return ((Integer)this.factionData.get(Integer.valueOf(id))).intValue();
   }

   public void increasePoints(int factionId, int points) {
      if(!this.factionData.containsKey(Integer.valueOf(factionId))) {
         Faction faction = FactionController.getInstance().getFaction(factionId);
         this.factionData.put(Integer.valueOf(factionId), Integer.valueOf(faction == null?-1:faction.defaultPoints));
      }

      this.factionData.put(Integer.valueOf(factionId), Integer.valueOf(((Integer)this.factionData.get(Integer.valueOf(factionId))).intValue() + points));
   }

   public NBTTagCompound getPlayerGuiData() {
      NBTTagCompound compound = new NBTTagCompound();
      this.saveNBTData(compound);
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.factionData.keySet().iterator();

      while(var3.hasNext()) {
         int id = ((Integer)var3.next()).intValue();
         Faction faction = FactionController.getInstance().getFaction(id);
         if(faction != null && !faction.hideFaction) {
            NBTTagCompound com = new NBTTagCompound();
            faction.writeNBT(com);
            list.appendTag(com);
         }
      }

      compound.setTag("FactionList", list);
      return compound;
   }
}
