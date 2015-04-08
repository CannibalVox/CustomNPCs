package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.items.ItemNpcInterface;
import org.lwjgl.opengl.GL11;

public class ItemThrowingWeapon extends ItemNpcInterface {

   private boolean rotating = false;
   private int damage = 2;
   private boolean dropItem = false;


   public ItemThrowingWeapon(int par1) {
      super(par1);
      this.setCreativeTab(CustomItems.tabWeapon);
   }

   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World worldObj, EntityPlayer player, int par4) {
      if(worldObj.isRemote) {
         player.swingItem();
      } else {
         EntityProjectile projectile = new EntityProjectile(worldObj, player, new ItemStack(par1ItemStack.getItem(), 1, par1ItemStack.getMetadata()), false);
         projectile.damage = (float)this.damage;
         projectile.canBePickedUp = !player.capabilities.isCreativeMode && this.dropItem;
         projectile.setRotating(this.rotating);
         projectile.setIs3D(true);
         projectile.setStickInWall(true);
         projectile.setHasGravity(true);
         projectile.setSpeed(12);
         if(!player.capabilities.isCreativeMode) {
            this.consumeItem(player, this);
         }

         projectile.shoot(1.0F);
         worldObj.playSoundAtEntity(player, "customnpcs:misc.swosh", 1.0F, 1.0F);
         worldObj.spawnEntityInWorld(projectile);
      }
   }

   public ItemThrowingWeapon setRotating() {
      this.rotating = true;
      return this;
   }

   public ItemThrowingWeapon setDamage(int damage) {
      this.damage = damage;
      return this;
   }

   public ItemThrowingWeapon setDropItem() {
      this.dropItem = true;
      return this;
   }

   public void renderSpecial() {
      super.renderSpecial();
      GL11.glTranslatef(0.2F, 0.1F, 0.06F);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }
}
