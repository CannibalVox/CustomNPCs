package noppes.npcs.enchants;

import noppes.npcs.enchants.EnchantInterface;
import noppes.npcs.items.ItemGun;
import noppes.npcs.items.ItemStaff;

public class EnchantDamage extends EnchantInterface {

   public EnchantDamage() {
      super(10, new Class[]{ItemStaff.class, ItemGun.class});
      this.setName("damage");
   }

   public int getMinEnchantability(int par1) {
      return 1 + (par1 - 1) * 10;
   }

   public int getMaxEnchantability(int par1) {
      return this.getMinEnchantability(par1) + 15;
   }

   public int getMaxLevel() {
      return 5;
   }
}
