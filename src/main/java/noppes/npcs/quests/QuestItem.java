package noppes.npcs.quests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.quests.QuestInterface;

public class QuestItem extends QuestInterface {

   public NpcMiscInventory items = new NpcMiscInventory(3);
   public boolean leaveItems = false;
   public boolean ignoreDamage = false;
   public boolean ignoreNBT = false;


   public void readEntityFromNBT(NBTTagCompound compound) {
      this.items.setFromNBT(compound.getCompoundTag("Items"));
      this.leaveItems = compound.getBoolean("LeaveItems");
      this.ignoreDamage = compound.getBoolean("IgnoreDamage");
      this.ignoreNBT = compound.getBoolean("IgnoreNBT");
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setTag("Items", this.items.getToNBT());
      compound.setBoolean("LeaveItems", this.leaveItems);
      compound.setBoolean("IgnoreDamage", this.ignoreDamage);
      compound.setBoolean("IgnoreNBT", this.ignoreNBT);
   }

   public boolean isCompleted(EntityPlayer player) {
      HashMap map = this.getProcessSet(player);
      Iterator var3 = this.items.items.values().iterator();

      while(var3.hasNext()) {
         ItemStack reqItem = (ItemStack)var3.next();
         boolean done = false;
         Iterator var6 = map.values().iterator();

         while(true) {
            if(var6.hasNext()) {
               ItemStack item = (ItemStack)var6.next();
               if(!NoppesUtilPlayer.compareItems(reqItem, item, this.ignoreDamage, this.ignoreNBT) || item.stackSize < reqItem.stackSize) {
                  continue;
               }

               done = true;
            }

            if(!done) {
               return false;
            }
            break;
         }
      }

      return true;
   }

   public HashMap getProcessSet(EntityPlayer player) {
      HashMap map = new HashMap();
      Iterator var3 = this.items.items.keySet().iterator();

      int slot;
      ItemStack item1;
      while(var3.hasNext()) {
         slot = ((Integer)var3.next()).intValue();
         ItemStack item = (ItemStack)this.items.items.get(Integer.valueOf(slot));
         if(item != null) {
            item1 = item.copy();
            item1.stackSize = 0;
            map.put(Integer.valueOf(slot), item1);
         }
      }

      ItemStack[] var9 = player.inventory.mainInventory;
      slot = var9.length;

      for(int var10 = 0; var10 < slot; ++var10) {
         item1 = var9[var10];
         if(item1 != null) {
            Iterator var7 = map.values().iterator();

            while(var7.hasNext()) {
               ItemStack questItem = (ItemStack)var7.next();
               if(NoppesUtilPlayer.compareItems(questItem, item1, this.ignoreDamage, this.ignoreNBT)) {
                  questItem.stackSize += item1.stackSize;
               }
            }
         }
      }

      return map;
   }

   public void handleComplete(EntityPlayer player) {
      if(!this.leaveItems) {
         Iterator var2 = this.items.items.values().iterator();

         while(var2.hasNext()) {
            ItemStack questitem = (ItemStack)var2.next();
            int stacksize = questitem.stackSize;

            for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
               ItemStack item = player.inventory.mainInventory[i];
               if(item != null && NoppesUtilPlayer.compareItems(item, questitem, this.ignoreDamage, this.ignoreNBT)) {
                  int size = item.stackSize;
                  if(stacksize - size >= 0) {
                     player.inventory.setInventorySlotContents(i, (ItemStack)null);
                     item.splitStack(size);
                  } else {
                     item.splitStack(stacksize);
                  }

                  stacksize -= size;
                  if(stacksize <= 0) {
                     break;
                  }
               }
            }
         }

      }
   }

   public Vector getQuestLogStatus(EntityPlayer player) {
      Vector vec = new Vector();
      HashMap map = this.getProcessSet(player);
      Iterator var4 = map.keySet().iterator();

      while(var4.hasNext()) {
         int slot = ((Integer)var4.next()).intValue();
         ItemStack item = (ItemStack)map.get(Integer.valueOf(slot));
         ItemStack quest = (ItemStack)this.items.items.get(Integer.valueOf(slot));
         if(item != null) {
            String process = item.stackSize + "";
            if(item.stackSize > quest.stackSize) {
               process = quest.stackSize + "";
            }

            process = process + "/" + quest.stackSize + "";
            if(item.hasDisplayName()) {
               vec.add(item.getDisplayName() + ": " + process);
            } else {
               vec.add(item.getUnlocalizedName() + ".name" + ": " + process);
            }
         }
      }

      return vec;
   }
}
