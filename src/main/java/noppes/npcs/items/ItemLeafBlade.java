package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemLeafBlade extends ItemNpcWeaponInterface {

   public ItemLeafBlade(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(0.8F, 0.8F, 0.8F);
      GL11.glTranslatef(-0.2F, 0.28F, -0.12F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-16.0F, 0.0F, 0.0F, 1.0F);
   }
}
