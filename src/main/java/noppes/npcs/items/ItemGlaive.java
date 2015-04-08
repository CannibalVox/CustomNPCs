package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemGlaive extends ItemNpcWeaponInterface {

   public ItemGlaive(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glTranslatef(0.03F, -0.4F, 0.08F);
   }
}
