package noppes.npcs.enchants;

import noppes.npcs.enchants.EnchantInterface;
import noppes.npcs.items.ItemGun;
import noppes.npcs.items.ItemStaff;

public class EnchantConfusion extends EnchantInterface {

   public EnchantConfusion() {
      super(3, new Class[]{ItemStaff.class, ItemGun.class});
      this.setName("confusion");
   }

   public int getMinEnchantability(int par1) {
      return 12 + (par1 - 1) * 20;
   }

   public int getMaxEnchantability(int par1) {
      return this.getMinEnchantability(par1) + 25;
   }

   public int getMaxLevel() {
      return 2;
   }
}
