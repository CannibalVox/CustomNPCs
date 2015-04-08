package noppes.npcs.items;

import noppes.npcs.items.ItemThrowingWeapon;
import org.lwjgl.opengl.GL11;

public class ItemThrowingShuriken extends ItemThrowingWeapon {

   public ItemThrowingShuriken(int par1) {
      super(par1);
   }

   public void renderSpecial() {
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glTranslatef(-0.1F, 0.3F, 0.0F);
   }

   public boolean shouldRotateAroundWhenRendering() {
      return true;
   }
}
