package noppes.npcs.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumNpcToolMaterial;
import noppes.npcs.enchants.EnchantInterface;
import noppes.npcs.entity.EntityMagicProjectile;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.items.ItemNpcInterface;
import noppes.npcs.util.IProjectileCallback;
import org.lwjgl.opengl.GL11;

public class ItemStaff extends ItemNpcInterface implements IProjectileCallback {

   private EnumNpcToolMaterial material;


   public ItemStaff(int par1, EnumNpcToolMaterial material) {
      super(par1);
      this.material = material;
      this.setCreativeTab(CustomItems.tabWeapon);
   }

   public void renderSpecial() {
      GL11.glScalef(1.0F, 1.14F, 1.0F);
      GL11.glTranslatef(0.14F, -0.3F, 0.08F);
   }

   public void onPlayerStoppedUsing(ItemStack stack, World worldObj, EntityPlayer player, int par4) {
      if(!worldObj.isRemote) {
         if(stack.stackTagCompound != null) {
            Entity entity = ((WorldServer)player.worldObj).getEntityByID(stack.stackTagCompound.getInteger("MagicProjectile"));
            if(entity != null && entity instanceof EntityProjectile) {
               EntityProjectile item = (EntityProjectile)entity;
               item.callback = this;
               item.callbackItem = stack;
               item.explosive = true;
               item.explosiveDamage = false;
               item.explosiveRadius = 1;
               item.prevRotationYaw = item.rotationYaw = player.rotationYaw;
               item.prevRotationPitch = item.rotationPitch = player.rotationPitch;
               item.shoot(2.0F);
               player.worldObj.playSoundAtEntity(player, "customnpcs:magic.shot", 1.0F, 1.0F);
            }
         }
      }
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int tick = this.getMaxItemUseDuration(stack) - count;
      if(player.worldObj.isRemote) {
         this.spawnParticle(stack, player);
      } else {
         int chargeTime = 20 + this.material.getHarvestLevel() * 8;
         double dx;
         double dz;
         if(tick == chargeTime) {
            if(!player.capabilities.isCreativeMode && !this.hasInfinite(stack)) {
               if(!this.hasItem(player, CustomItems.mana)) {
                  return;
               }

               this.consumeItem(player, CustomItems.mana);
            }

            player.worldObj.playSoundAtEntity(player, "customnpcs:magic.charge", 1.0F, 1.0F);
            if(stack.stackTagCompound == null) {
               stack.stackTagCompound = new NBTTagCompound();
            }

            int entity = 6 + this.material.getDamageVsEntity() + player.worldObj.rand.nextInt(4);
            entity = (int)((float)entity + (float)(entity * EnchantInterface.getLevel(EnchantInterface.Damage, stack)) * 0.5F);
            EntityMagicProjectile item = new EntityMagicProjectile(player.worldObj, player, this.getProjectile(stack), false);
            item.damage = (float)entity;
            item.setSpeed(25);
            dx = (double)(-MathHelper.sin((float)((double)(player.rotationYaw / 180.0F) * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch / 180.0F) * 3.141592653589793D)));
            dz = (double)(MathHelper.cos((float)((double)(player.rotationYaw / 180.0F) * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch / 180.0F) * 3.141592653589793D)));
            item.setPosition(player.posX + dx * 0.8D, player.posY + 1.5D - (double)(player.rotationPitch / 80.0F), player.posZ + dz * 0.8D);
            player.worldObj.spawnEntityInWorld(item);
            stack.stackTagCompound.setInteger("MagicProjectile", item.getEntityId());
         }

         if(tick > chargeTime && stack.stackTagCompound != null) {
            Entity entity1 = ((WorldServer)player.worldObj).getEntityByID(stack.stackTagCompound.getInteger("MagicProjectile"));
            if(entity1 == null || !(entity1 instanceof EntityProjectile)) {
               return;
            }

            EntityProjectile item1 = (EntityProjectile)entity1;
            item1.field_70195_i = 0;
            dx = (double)(-MathHelper.sin((float)((double)(player.rotationYaw / 180.0F) * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch / 180.0F) * 3.141592653589793D)));
            dz = (double)(MathHelper.cos((float)((double)(player.rotationYaw / 180.0F) * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch / 180.0F) * 3.141592653589793D)));
            item1.setPosition(player.posX + dx * 0.8D, player.posY + 1.5D - (double)(player.rotationPitch / 80.0F), player.posZ + dz * 0.8D);
         }

      }
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public ItemStack getProjectile(ItemStack stack) {
      return stack.getItem() == CustomItems.staffWood?new ItemStack(CustomItems.spellNature):(stack.getItem() != CustomItems.staffStone && stack.getItem() != CustomItems.staffDemonic?(stack.getItem() == CustomItems.staffIron?new ItemStack(CustomItems.spellHoly):(stack.getItem() == CustomItems.staffBronze?new ItemStack(CustomItems.spellLightning):(stack.getItem() == CustomItems.staffGold?new ItemStack(CustomItems.spellFire):(stack.getItem() != CustomItems.staffDiamond && stack.getItem() != CustomItems.staffFrost?(stack.getItem() == CustomItems.staffEmerald?new ItemStack(CustomItems.spellArcane):new ItemStack(CustomItems.orb, 1, stack.getMetadata())):new ItemStack(CustomItems.spellIce))))):new ItemStack(CustomItems.spellDark));
   }

   public void spawnParticle(ItemStack stack, EntityPlayer player) {
      if(stack.getItem() == CustomItems.staffWood) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(5), Integer.valueOf(2)});
         CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(12), Integer.valueOf(2)});
      } else if(stack.getItem() != CustomItems.staffStone && stack.getItem() != CustomItems.staffDemonic) {
         if(stack.getItem() == CustomItems.staffBronze) {
            CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(8648694), Integer.valueOf(2)});
            CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(6091007), Integer.valueOf(2)});
         } else if(stack.getItem() != CustomItems.staffIron && stack.getItem() != CustomItems.staffMithril) {
            if(stack.getItem() == CustomItems.staffGold) {
               CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(1), Integer.valueOf(2)});
               CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(14), Integer.valueOf(2)});
            } else if(stack.getItem() != CustomItems.staffDiamond && stack.getItem() != CustomItems.staffFrost) {
               if(stack.getItem() == CustomItems.staffEmerald) {
                  CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(16761831), Integer.valueOf(2)});
                  CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(16487167), Integer.valueOf(2)});
               }
            } else {
               CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(9756653), Integer.valueOf(2)});
               CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(4503295), Integer.valueOf(2)});
            }
         } else {
            CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(16580553), Integer.valueOf(2)});
            CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(15728535), Integer.valueOf(2)});
         }
      } else {
         CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(5649239), Integer.valueOf(2)});
         CustomNpcs.proxy.spawnParticle(player, "Spell", new Object[]{Integer.valueOf(4400964), Integer.valueOf(2)});
      }

   }

   public int getItemEnchantability() {
      return this.material.getEnchantability();
   }

   public boolean isItemTool(ItemStack par1ItemStack) {
      return true;
   }

   public boolean onImpact(EntityProjectile entityProjectile, EntityLivingBase entity, ItemStack itemstack) {
      int confusion = EnchantInterface.getLevel(EnchantInterface.Confusion, itemstack);
      if(confusion > 0 && entity.getRNG().nextInt(4) > confusion) {
         entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 100));
      }

      int poison = EnchantInterface.getLevel(EnchantInterface.Poison, itemstack);
      if(poison > 0 && entity.getRNG().nextInt(4) > poison) {
         entity.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
      }

      return false;
   }

   public boolean hasInfinite(ItemStack stack) {
      return EnchantInterface.getLevel(EnchantInterface.Infinite, stack) > 0;
   }
}
