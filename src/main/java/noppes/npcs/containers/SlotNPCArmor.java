package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

class SlotNPCArmor extends Slot {

   final int armorType;


   SlotNPCArmor(IInventory iinventory, int i, int j, int k, int l) {
      super(iinventory, i, j, k);
      this.armorType = l;
   }

   public int getSlotStackLimit() {
      return 1;
   }

   public IIcon getBackgroundIconIndex() {
      return ItemArmor.getBackgroundIcon(this.armorType);
   }

   public boolean isItemValid(ItemStack itemstack) {
      return itemstack.getItem() instanceof ItemArmor?((ItemArmor)itemstack.getItem()).armorType == this.armorType:(itemstack.getItem() instanceof ItemBlock?this.armorType == 0:false);
   }
}
