package noppes.npcs.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.containers.ContainerNpcInterface;

public class ContainerMerchantAdd extends ContainerNpcInterface {

   private IMerchant theMerchant;
   private InventoryBasic merchantInventory;
   private final World theWorld;


   public ContainerMerchantAdd(EntityPlayer player, IMerchant par2IMerchant, World par3World) {
      super(player);
      this.theMerchant = par2IMerchant;
      this.theWorld = par3World;
      this.merchantInventory = new InventoryBasic("", false, 3);
      this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
      this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
      this.addSlotToContainer(new Slot(this.merchantInventory, 2, 120, 53));

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
      }

   }

   public void onCraftGuiOpened(ICrafting par1ICrafting) {
      super.onCraftGuiOpened(par1ICrafting);
   }

   public void detectAndSendChanges() {
      super.detectAndSendChanges();
   }

   public void onCraftMatrixChanged(IInventory par1IInventory) {
      super.onCraftMatrixChanged(par1IInventory);
   }

   public void setCurrentRecipeIndex(int par1) {}

   @SideOnly(Side.CLIENT)
   public void updateProgressBar(int par1, int par2) {}

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
      ItemStack itemstack = null;
      Slot slot = (Slot)super.inventorySlots.get(par2);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(par2 != 0 && par2 != 1 && par2 != 2) {
            if(par2 >= 3 && par2 < 30) {
               if(!this.mergeItemStack(itemstack1, 30, 39, false)) {
                  return null;
               }
            } else if(par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
               return null;
            }
         } else if(!this.mergeItemStack(itemstack1, 3, 39, false)) {
            return null;
         }

         if(itemstack1.stackSize == 0) {
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }

         if(itemstack1.stackSize == itemstack.stackSize) {
            return null;
         }

         slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
      }

      return itemstack;
   }

   public void onContainerClosed(EntityPlayer par1EntityPlayer) {
      super.onContainerClosed(par1EntityPlayer);
      this.theMerchant.setCustomer((EntityPlayer)null);
      super.onContainerClosed(par1EntityPlayer);
      if(!this.theWorld.isRemote) {
         ItemStack itemstack = this.merchantInventory.getStackInSlotOnClosing(0);
         if(itemstack != null) {
            par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
         }

         itemstack = this.merchantInventory.getStackInSlotOnClosing(1);
         if(itemstack != null) {
            par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
         }
      }

   }
}
