package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.ContainerNPCTrader;

public class InventoryNpcTrader implements IInventory {

   private String inventoryTitle;
   private int slotsCount;
   private ItemStack[] inventoryContents;
   private ContainerNPCTrader con;


   public InventoryNpcTrader(String s, int i, ContainerNPCTrader con) {
      this.con = con;
      this.inventoryTitle = s;
      this.slotsCount = i;
      this.inventoryContents = new ItemStack[i];
   }

   public ItemStack getStackInSlot(int i) {
      ItemStack toBuy = this.inventoryContents[i];
      return toBuy == null?null:ItemStack.copyItemStack(toBuy);
   }

   public ItemStack decrStackSize(int i, int j) {
      if(this.inventoryContents[i] != null) {
         ItemStack itemstack = this.inventoryContents[i];
         return ItemStack.copyItemStack(itemstack);
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      if(itemstack != null) {
         this.inventoryContents[i] = itemstack.copy();
      }

      this.markDirty();
   }

   public int getSizeInventory() {
      return this.slotsCount;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer entityplayer) {
      return true;
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
