package noppes.npcs.containers;

import java.util.Iterator;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import noppes.npcs.roles.RoleFollower;

class SlotNpcMercenaryCurrency extends Slot {

   RoleFollower role;


   public SlotNpcMercenaryCurrency(RoleFollower role, IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
      this.role = role;
   }

   public int getSlotStackLimit() {
      return 64;
   }

   public boolean isItemValid(ItemStack itemstack) {
      Item item = itemstack.getItem();
      Iterator var3 = this.role.inventory.items.values().iterator();

      ItemStack is;
      do {
         do {
            if(!var3.hasNext()) {
               return false;
            }

            is = (ItemStack)var3.next();
         } while(item != is.getItem());
      } while(itemstack.getHasSubtypes() && itemstack.getMetadata() != is.getMetadata());

      return true;
   }
}
