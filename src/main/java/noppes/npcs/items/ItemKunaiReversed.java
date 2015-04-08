package noppes.npcs.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.CustomItems;
import noppes.npcs.items.ItemKunai;
import org.lwjgl.opengl.GL11;

public class ItemKunaiReversed extends ItemKunai {

   public ItemKunaiReversed(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(-0.4F, -0.9F, 0.2F);
   }

   public void registerIcons(IIconRegister par1IconRegister) {
      super.itemIcon = CustomItems.kunai.getIconFromDamage(0);
   }
}
