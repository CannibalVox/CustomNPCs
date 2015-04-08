package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.TransportLocation;

public class TransportCategory {

   public int id = -1;
   public String title = "";
   public HashMap locations = new HashMap();


   public Vector getDefaultLocations() {
      Vector list = new Vector();
      Iterator var2 = this.locations.values().iterator();

      while(var2.hasNext()) {
         TransportLocation loc = (TransportLocation)var2.next();
         if(loc.isDefault()) {
            list.add(loc);
         }
      }

      return list;
   }

   public void readNBT(NBTTagCompound compound) {
      this.id = compound.getInteger("CategoryId");
      this.title = compound.getString("CategoryTitle");
      NBTTagList locs = compound.getTagList("CategoryLocations", 10);
      if(locs != null && locs.tagCount() != 0) {
         for(int ii = 0; ii < locs.tagCount(); ++ii) {
            TransportLocation location = new TransportLocation();
            location.readNBT(locs.getCompoundTagAt(ii));
            location.category = this;
            this.locations.put(Integer.valueOf(location.id), location);
         }

      }
   }

   public void writeNBT(NBTTagCompound compound) {
      compound.setInteger("CategoryId", this.id);
      compound.setString("CategoryTitle", this.title);
      NBTTagList locs = new NBTTagList();
      Iterator var3 = this.locations.values().iterator();

      while(var3.hasNext()) {
         TransportLocation location = (TransportLocation)var3.next();
         locs.appendTag(location.writeNBT());
      }

      compound.setTag("CategoryLocations", locs);
   }
}
