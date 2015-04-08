package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.ContainerNPCTrader;

class SlotNpcTraderCurrency extends Slot {

   final ContainerNPCTrader field_75224_c;


   public SlotNpcTraderCurrency(ContainerNPCTrader containerplayer, IInventory iinventory, int i, int j, int k) {
      super(iinventory, i, j, k);
      this.field_75224_c = containerplayer;
   }

   public int getSlotStackLimit() {
      return 64;
   }

   public boolean isItemValid(ItemStack itemstack) {
      return this.field_75224_c.role.hasCurrency(itemstack);
   }
}
