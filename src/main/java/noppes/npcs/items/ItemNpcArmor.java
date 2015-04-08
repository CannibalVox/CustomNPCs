package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import noppes.npcs.CustomItems;

public class ItemNpcArmor extends ItemArmor {

   private String texture;


   public ItemNpcArmor(int par1, ArmorMaterial par2EnumArmorMaterial, int par4, String texture) {
      super(par2EnumArmorMaterial, 0, par4);
      this.texture = texture;
      this.setCreativeTab(CustomItems.tabArmor);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
      return super.armorType == 2?"customnpcs:textures/armor/" + this.texture + "_2.png":"customnpcs:textures/armor/" + this.texture + "_1.png";
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
