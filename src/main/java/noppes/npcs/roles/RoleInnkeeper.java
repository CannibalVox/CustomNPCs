package noppes.npcs.roles;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import noppes.npcs.controllers.InnDoorData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RoleInnkeeper extends RoleInterface {

   private String innName = "Inn";
   private HashMap doors = new HashMap();


   public RoleInnkeeper(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setString("InnName", this.innName);
      nbttagcompound.setTag("InnDoors", this.nbtInnDoors(this.doors));
      return nbttagcompound;
   }

   private NBTBase nbtInnDoors(HashMap doors1) {
      NBTTagList nbttaglist = new NBTTagList();
      if(doors1 == null) {
         return nbttaglist;
      } else {
         HashMap doors2 = doors1;
         Iterator var4 = doors1.keySet().iterator();

         while(var4.hasNext()) {
            String name = (String)var4.next();
            InnDoorData door = (InnDoorData)doors2.get(name);
            if(door != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.setString("Name", name);
               nbttagcompound.setInteger("posX", door.x);
               nbttagcompound.setInteger("posY", door.y);
               nbttagcompound.setInteger("posZ", door.z);
               nbttaglist.appendTag(nbttagcompound);
            }
         }

         return nbttaglist;
      }
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.innName = nbttagcompound.getString("InnName");
      this.doors = this.getInnDoors(nbttagcompound.getTagList("InnDoors", 10));
   }

   private HashMap getInnDoors(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         String name = nbttagcompound.getString("Name");
         InnDoorData door = new InnDoorData();
         door.x = nbttagcompound.getInteger("posX");
         door.y = nbttagcompound.getInteger("posY");
         door.z = nbttagcompound.getInteger("posZ");
         list.put(name, door);
      }

      return list;
   }

   public void interact(EntityPlayer player) {
      super.npc.say(player, super.npc.advanced.getInteractLine());
      if(this.doors.isEmpty()) {
         player.addChatMessage(new ChatComponentTranslation("No Rooms available", new Object[0]));
      }

   }
}
