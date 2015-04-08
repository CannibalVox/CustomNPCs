package noppes.npcs.controllers;

import java.util.HashSet;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PlayerTransportData {

   public HashSet transports = new HashSet();


   public void loadNBTData(NBTTagCompound compound) {
      HashSet dialogsRead = new HashSet();
      if(compound != null) {
         NBTTagList list = compound.getTagList("TransportData", 10);
         if(list != null) {
            for(int i = 0; i < list.tagCount(); ++i) {
               NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
               dialogsRead.add(Integer.valueOf(nbttagcompound.getInteger("Transport")));
            }

            this.transports = dialogsRead;
         }
      }
   }

   public void saveNBTData(NBTTagCompound compound) {
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.transports.iterator();

      while(var3.hasNext()) {
         int dia = ((Integer)var3.next()).intValue();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setInteger("Transport", dia);
         list.appendTag(nbttagcompound);
      }

      compound.setTag("TransportData", list);
   }
}
