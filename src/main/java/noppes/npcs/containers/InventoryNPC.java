package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryNPC implements IInventory {

   private String inventoryTitle;
   private int slotsCount;
   private ItemStack[] inventoryContents;
   private Container con;


   public InventoryNPC(String s, int i, Container con) {
      this.con = con;
      this.inventoryTitle = s;
      this.slotsCount = i;
      this.inventoryContents = new ItemStack[i];
   }

   public ItemStack getStackInSlot(int i) {
      return this.inventoryContents[i];
   }

   public ItemStack decrStackSize(int i, int j) {
      if(this.inventoryContents[i] != null) {
         ItemStack itemstack1;
         if(this.inventoryContents[i].stackSize <= j) {
            itemstack1 = this.inventoryContents[i];
            this.inventoryContents[i] = null;
            return itemstack1;
         } else {
            itemstack1 = this.inventoryContents[i].splitStack(j);
            if(this.inventoryContents[i].stackSize == 0) {
               this.inventoryContents[i] = null;
            }

            return itemstack1;
         }
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      this.inventoryContents[i] = itemstack;
      if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
         itemstack.stackSize = this.getInventoryStackLimit();
      }

   }

   public int getSizeInventory() {
      return this.slotsCount;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer entityplayer) {
      return false;
   }

   public ItemStack getStackInSlotOnClosing(int i) {
      return null;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return true;
   }

   public String getInventoryName() {
      return this.inventoryTitle;
   }

   public boolean isCustomInventoryName() {
      return true;
   }

   public void markDirty() {
      this.con.onCraftMatrixChanged(this);
   }

   public void openChest() {}

   public void closeChest() {}
}
