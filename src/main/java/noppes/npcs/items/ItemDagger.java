package noppes.npcs.items;

import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemDagger extends ItemNpcWeaponInterface {

   public ItemDagger(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(0.14F, 0.22F, 0.06F);
   }
}
