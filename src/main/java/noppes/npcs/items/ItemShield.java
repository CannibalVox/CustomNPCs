package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.constants.EnumNpcToolMaterial;
import noppes.npcs.items.ItemNpcInterface;
import org.lwjgl.opengl.GL11;

public class ItemShield extends ItemNpcInterface {

   public EnumNpcToolMaterial material;


   public ItemShield(int par1, EnumNpcToolMaterial material) {
      super(par1);
      this.material = material;
      this.setMaxDurability(material.getMaxUses());
      this.setCreativeTab(CustomItems.tabWeapon);
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(0.0F, 0.0F, -0.2F);
      GL11.glRotatef(-6.0F, 0.0F, 1.0F, 0.0F);
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.block;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }
}
