package noppes.npcs;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilPlayer;

public class NpcMiscInventory implements IInventory {

   public HashMap items = new HashMap();
   public int stackLimit = 64;
   private int size;


   public NpcMiscInventory(int size) {
      this.size = size;
   }

   public NBTTagCompound getToNBT() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setTag("NpcMiscInv", NBTTags.nbtItemStackList(this.items));
      return nbttagcompound;
   }

   public void setFromNBT(NBTTagCompound nbttagcompound) {
      this.items = NBTTags.getItemStackList(nbttagcompound.getTagList("NpcMiscInv", 10));
   }

   public int getSizeInventory() {
      return this.size;
   }

   public ItemStack getStackInSlot(int var1) {
      return (ItemStack)this.items.get(Integer.valueOf(var1));
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.items.get(Integer.valueOf(par1)) == null) {
         return null;
      } else {
         ItemStack var4 = null;
         if(((ItemStack)this.items.get(Integer.valueOf(par1))).stackSize <= par2) {
            var4 = (ItemStack)this.items.get(Integer.valueOf(par1));
            this.items.put(Integer.valueOf(par1), (Object)null);
         } else {
            var4 = ((ItemStack)this.items.get(Integer.valueOf(par1))).splitStack(par2);
            if(((ItemStack)this.items.get(Integer.valueOf(par1))).stackSize == 0) {
               this.items.put(Integer.valueOf(par1), (Object)null);
            }
         }

         return var4;
      }
   }

   public boolean decrStackSize(ItemStack eating, int decrease) {
      Iterator var3 = this.items.keySet().iterator();

      int slot;
      ItemStack item;
      do {
         if(!var3.hasNext()) {
            return false;
         }

         slot = ((Integer)var3.next()).intValue();
         item = (ItemStack)this.items.get(Integer.valueOf(slot));
      } while(this.items == null || eating != item || item.stackSize < decrease);

      item.splitStack(decrease);
      if(item.stackSize <= 0) {
         this.items.put(Integer.valueOf(slot), (Object)null);
      }

      return true;
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if(this.items.get(Integer.valueOf(var1)) != null) {
         ItemStack var3 = (ItemStack)this.items.get(Integer.valueOf(var1));
         this.items.put(Integer.valueOf(var1), (Object)null);
         return var3;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      if(var1 < this.getSizeInventory()) {
         this.items.put(Integer.valueOf(var1), var2);
      }
   }

   public int getInventoryStackLimit() {
      return this.stackLimit;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return true;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return true;
   }

   public String getInventoryName() {
      return "Npc Misc Inventory";
   }

   public boolean isCustomInventoryName() {
      return true;
   }

   public void markDirty() {}

   public void openChest() {}

   public void closeChest() {}

   public boolean addItemStack(ItemStack item) {
      boolean merged = false;

      ItemStack mergable;
      int slot;
      while((mergable = this.getMergableItem(item)) != null && mergable.stackSize > 0) {
         slot = mergable.getMaxStackSize() - mergable.stackSize;
         if(slot > item.stackSize) {
            mergable.stackSize = mergable.getMaxStackSize();
            item.stackSize -= slot;
            merged = true;
         } else {
            mergable.stackSize += item.stackSize;
            item.stackSize = 0;
         }
      }

      if(item.stackSize <= 0) {
         return true;
      } else {
         slot = this.firstFreeSlot();
         if(slot >= 0) {
            this.items.put(Integer.valueOf(slot), item.copy());
            item.stackSize = 0;
            return true;
         } else {
            return merged;
         }
      }
   }

   public ItemStack getMergableItem(ItemStack item) {
      Iterator var2 = this.items.values().iterator();

      ItemStack is;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         is = (ItemStack)var2.next();
      } while(!NoppesUtilPlayer.compareItems(item, is, false, false) || is.stackSize >= is.getMaxStackSize());

      return is;
   }

   public int firstFreeSlot() {
      for(int i = 0; i < this.getSizeInventory(); ++i) {
         if(this.items.get(Integer.valueOf(i)) == null) {
            return i;
         }
      }

      return -1;
   }

   public void setSize(int i) {
      this.size = i;
   }
}
