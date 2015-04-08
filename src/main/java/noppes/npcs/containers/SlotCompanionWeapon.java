package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.roles.RoleCompanion;

class SlotCompanionWeapon extends Slot {

   final RoleCompanion role;


   public SlotCompanionWeapon(RoleCompanion role, IInventory iinventory, int id, int x, int y) {
      super(iinventory, id, x, y);
      this.role = role;
   }

   public int getSlotStackLimit() {
      return 1;
   }

   public boolean isItemValid(ItemStack itemstack) {
      return this.role.canWearSword(itemstack);
   }
}
