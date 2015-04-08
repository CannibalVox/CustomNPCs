package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.ContainerNPCBankInterface;

public class SlotNpcBankCurrency extends Slot {

   public ItemStack item;


   public SlotNpcBankCurrency(ContainerNPCBankInterface containerplayer, IInventory iinventory, int i, int j, int k) {
      super(iinventory, i, j, k);
   }

   public int getSlotStackLimit() {
      return 64;
   }

   public boolean isItemValid(ItemStack itemstack) {
      return this.item == null?false:this.item.getItem() == itemstack.getItem() && (!this.item.getHasSubtypes() || this.item.getMetadata() == itemstack.getMetadata());
   }
}
