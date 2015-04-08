package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemClaw extends ItemNpcWeaponInterface {

   public ItemClaw(int par1, ToolMaterial material) {
      super(par1, material);
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(-0.6F, 0.2F, -0.2F);
      GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
      GL11.glRotatef(6.0F, 1.0F, 0.0F, 0.0F);
   }
}
