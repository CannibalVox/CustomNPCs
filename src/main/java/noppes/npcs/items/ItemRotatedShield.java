package noppes.npcs.items;

import noppes.npcs.constants.EnumNpcToolMaterial;
import noppes.npcs.items.ItemShield;
import org.lwjgl.opengl.GL11;

public class ItemRotatedShield extends ItemShield {

   public ItemRotatedShield(int par1, EnumNpcToolMaterial material) {
      super(par1, material);
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(0.4F, 1.0F, -0.18F);
      GL11.glRotatef(-6.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
   }
}
