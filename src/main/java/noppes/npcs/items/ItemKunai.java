package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemKunai extends ItemNpcWeaponInterface {

   public ItemKunai(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World worldObj, EntityPlayer player, int par4) {
      if(worldObj.isRemote) {
         player.swingItem();
      } else {
         EntityProjectile projectile = new EntityProjectile(worldObj, player, par1ItemStack, false);
         projectile.damage = this.func_150931_i();
         projectile.destroyedOnEntityHit = false;
         projectile.canBePickedUp = !player.capabilities.isCreativeMode;
         projectile.setIs3D(true);
         projectile.setStickInWall(true);
         projectile.setHasGravity(true);
         projectile.setSpeed(12);
         projectile.shoot(1.0F);
         if(!player.capabilities.isCreativeMode) {
            par1ItemStack.damageItem(1, player);
            if(par1ItemStack.stackSize == 0) {
               return;
            }

            player.inventory.mainInventory[player.inventory.currentItem] = null;
         }

         worldObj.playSoundAtEntity(player, "customnpcs:misc.swosh", 1.0F, 1.0F);
         worldObj.spawnEntityInWorld(projectile);
      }
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public void renderSpecial() {
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      GL11.glTranslatef(-0.4F, 0.5F, 0.1F);
   }

   public boolean shouldRotateAroundWhenRendering() {
      return true;
   }
}
