package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemWarhammer extends ItemNpcWeaponInterface {

   public ItemWarhammer(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(1.2F, 1.4F, 1.0F);
      GL11.glTranslatef(0.2F, -0.08F, 0.08F);
   }
}
