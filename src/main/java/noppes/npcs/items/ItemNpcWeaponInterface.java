package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.items.ItemRenderInterface;
import org.lwjgl.opengl.GL11;

public class ItemNpcWeaponInterface extends ItemSword implements ItemRenderInterface {

   public ItemNpcWeaponInterface(int par1, ToolMaterial material) {
      this(material);
   }

   public ItemNpcWeaponInterface(ToolMaterial material) {
      super(material);
      this.setCreativeTab(CustomItems.tab);
      CustomNpcs.proxy.registerItem(this);
      this.setCreativeTab(CustomItems.tabWeapon);
   }

   public void renderSpecial() {
      GL11.glScalef(0.66F, 0.66F, 0.66F);
      GL11.glTranslatef(0.16F, 0.26F, 0.06F);
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
