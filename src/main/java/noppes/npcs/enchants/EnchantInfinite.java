package noppes.npcs.enchants;

import noppes.npcs.enchants.EnchantInterface;
import noppes.npcs.items.ItemGun;
import noppes.npcs.items.ItemStaff;

public class EnchantInfinite extends EnchantInterface {

   public EnchantInfinite() {
      super(3, new Class[]{ItemStaff.class, ItemGun.class});
      this.setName("infinite");
   }

   public int getMinEnchantability(int par1) {
      return 20;
   }

   public int getMaxEnchantability(int par1) {
      return 50;
   }

   public int getMaxLevel() {
      return 1;
   }
}
