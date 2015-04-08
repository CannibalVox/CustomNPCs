package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import noppes.npcs.roles.RoleCompanion;

class SlotCompanionArmor extends Slot {

   final int armorType;
   final RoleCompanion role;


   public SlotCompanionArmor(RoleCompanion role, IInventory iinventory, int id, int x, int y, int type) {
      super(iinventory, id, x, y);
      this.armorType = type;
      this.role = role;
   }

   public int getSlotStackLimit() {
      return 1;
   }

   public IIcon getBackgroundIconIndex() {
      return ItemArmor.getBackgroundIcon(this.armorType);
   }

   public boolean isItemValid(ItemStack itemstack) {
      return itemstack.getItem() instanceof ItemArmor && this.role.canWearArmor(itemstack)?((ItemArmor)itemstack.getItem()).armorType == this.armorType:(itemstack.getItem() instanceof ItemBlock?this.armorType == 0:false);
   }
}
