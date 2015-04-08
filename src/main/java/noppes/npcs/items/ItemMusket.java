package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.items.ItemNpcInterface;
import org.lwjgl.opengl.GL11;

public class ItemMusket extends ItemNpcInterface {

   public ItemMusket(int par1) {
      super(par1);
      this.setMaxDurability(129);
      this.setCreativeTab(CustomItems.tabWeapon);
   }

   public void onPlayerStoppedUsing(ItemStack stack, World par2World, EntityPlayer player, int count) {
      if(!player.worldObj.isRemote) {
         if((stack.stackTagCompound.getBoolean("IsLoaded2") || player.capabilities.isCreativeMode) && CustomNpcs.GunsEnabled) {
            if(stack.stackTagCompound.getBoolean("Reloading2") && !player.capabilities.isCreativeMode) {
               stack.stackTagCompound.setBoolean("Reloading2", false);
            } else {
               stack.damageItem(1, player);
               EntityProjectile projectile = new EntityProjectile(player.worldObj, player, new ItemStack(CustomItems.bulletBlack, 1, 0), false);
               projectile.damage = 16.0F;
               projectile.setSpeed(50);
               projectile.setParticleEffect(EnumParticleType.Smoke);
               projectile.shoot(2.0F);
               if(!player.capabilities.isCreativeMode) {
                  this.consumeItem(player, CustomItems.bulletBlack);
               }

               player.worldObj.playSoundAtEntity(player, "random.explode", 0.9F, Item.itemRand.nextFloat() * 0.3F + 1.8F);
               player.worldObj.playSoundAtEntity(player, "ambient.weather.thunder", 2.0F, Item.itemRand.nextFloat() * 0.3F + 1.8F);
               player.worldObj.spawnEntityInWorld(projectile);
               stack.stackTagCompound.setBoolean("IsLoaded2", false);
            }
         } else {
            player.worldObj.playSoundAtEntity(player, "customnpcs:gun.empty", 1.0F, 1.0F);
         }
      }
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      if(!player.worldObj.isRemote) {
         int ticks = this.getMaxItemUseDuration(stack) - count;
         if(!player.capabilities.isCreativeMode && stack.stackTagCompound.getBoolean("Reloading2") && this.hasItem(player, CustomItems.bulletBlack)) {
            if(ticks == 60) {
               player.worldObj.playSoundAtEntity(player, "customnpcs:gun.ak47.load", 1.0F, 1.0F);
               stack.stackTagCompound.setBoolean("IsLoaded2", true);
            }

         }
      }
   }

   public void renderSpecial() {
      GL11.glRotatef(-6.0F, 0.0F, 0.0F, 1.0F);
      GL11.glScalef(0.7F, 0.7F, 0.7F);
      GL11.glTranslatef(0.4F, 0.0F, 0.2F);
   }

   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
      if(stack.stackTagCompound == null) {
         stack.stackTagCompound = new NBTTagCompound();
      }

      if(!player.capabilities.isCreativeMode && this.hasItem(player, CustomItems.bulletBlack) && !stack.stackTagCompound.getBoolean("IsLoaded2")) {
         stack.stackTagCompound.setBoolean("Reloading2", true);
      }

      player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
      return stack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("Reloading2")?EnumAction.block:EnumAction.bow;
   }
}
