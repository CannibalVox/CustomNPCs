package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemBroadSword extends ItemNpcWeaponInterface {

   public ItemBroadSword(ToolMaterial tool) {
      super(tool);
   }

   public void renderSpecial() {
      GL11.glScalef(1.0F, 1.2F, 1.0F);
      GL11.glTranslatef(-0.12F, 0.14F, -0.16F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
   }
}
