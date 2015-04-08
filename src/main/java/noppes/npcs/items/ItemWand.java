package noppes.npcs.items;

import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.items.ItemNpcInterface;
import org.lwjgl.opengl.GL11;

public class ItemWand extends ItemNpcInterface {

   public ItemWand(int par1) {
      super(par1);
      this.setCreativeTab(CustomItems.tabMisc);
   }

   public boolean hasEffect(ItemStack par1ItemStack, int pass) {
      return true;
   }

   public void renderSpecial() {
      GL11.glScalef(0.54F, 0.54F, 0.54F);
      GL11.glTranslatef(0.1F, 0.5F, 0.1F);
   }
}
