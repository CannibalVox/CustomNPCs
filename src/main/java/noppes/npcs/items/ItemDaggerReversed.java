package noppes.npcs.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemDagger;
import org.lwjgl.opengl.GL11;

public class ItemDaggerReversed extends ItemDagger {

   private ItemDagger dagger;


   public ItemDaggerReversed(int par1, ItemDagger dagger, ToolMaterial tool) {
      super(par1, tool);
      this.dagger = dagger;
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(0.16F, 0.6F, -0.16F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
   }

   public void registerIcons(IIconRegister par1IconRegister) {
      super.itemIcon = this.dagger.getIconFromDamage(0);
   }
}
