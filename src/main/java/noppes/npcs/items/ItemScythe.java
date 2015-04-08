package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemScythe extends ItemNpcWeaponInterface {

   public ItemScythe(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(1.0F, 1.3F, 1.0F);
      GL11.glTranslatef(0.0F, -0.2F, -0.16F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
   }
}
