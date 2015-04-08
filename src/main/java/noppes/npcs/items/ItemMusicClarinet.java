package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.items.ItemMusic;
import org.lwjgl.opengl.GL11;

public class ItemMusicClarinet extends ItemMusic {

   public void renderSpecial() {
      GL11.glScalef(0.8F, 0.9F, 0.8F);
      GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.44F, -0.7F, -0.04F);
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }
}
