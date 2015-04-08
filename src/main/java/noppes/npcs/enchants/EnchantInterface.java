package noppes.npcs.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.enchants.EnchantConfusion;
import noppes.npcs.enchants.EnchantDamage;
import noppes.npcs.enchants.EnchantInfinite;
import noppes.npcs.enchants.EnchantPoison;

public abstract class EnchantInterface extends Enchantment {

   private static EnumEnchantmentType CustomNpcsType;
   public static EnchantInterface Damage;
   public static EnchantInterface Poison;
   public static EnchantInterface Confusion;
   public static EnchantInterface Infinite;
   private Class[] classes;


   protected EnchantInterface(int par2, Class ... obs) {
      super(CustomNpcs.EnchantStartId++, par2, CustomNpcsType);
      this.classes = obs;
   }

   public boolean canApply(ItemStack par1ItemStack) {
      if(par1ItemStack.getItem() == null) {
         return false;
      } else {
         Class[] var2 = this.classes;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class cls = var2[var4];
            if(cls.isInstance(par1ItemStack.getItem())) {
               return true;
            }
         }

         return false;
      }
   }

   public static void load() {
      if(!CustomNpcs.DisableEnchants) {
         CustomNpcsType = EnumHelper.addEnchantmentType("customnpcs_enchants");

         try {
            Damage = new EnchantDamage();
            Poison = new EnchantPoison();
            Confusion = new EnchantConfusion();
            Infinite = new EnchantInfinite();
         } catch (Exception var1) {
            LogWriter.except(var1);
         }
      }

   }

   public static int getLevel(EnchantInterface enchant, ItemStack stack) {
      return !CustomNpcs.DisableEnchants && enchant != null?EnchantmentHelper.getEnchantmentLevel(enchant.effectId, stack):0;
   }
}
