package noppes.npcs.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import noppes.npcs.DataStats;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.constants.EnumPotionType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.IProjectileCallback;

public class EntityProjectile extends EntityThrowable {

   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private Block inTile;
   protected boolean field_70193_a = false;
   private int inData = 0;
   public int field_70191_b = 0;
   public int arrowShake = 0;
   public boolean canBePickedUp = false;
   public boolean destroyedOnEntityHit = true;
   private EntityLivingBase thrower;
   private EntityNPCInterface npc;
   public EntityItem entityitem;
   private String throwerName = null;
   private int ticksInGround;
   public int field_70195_i = 0;
   private double accelerationX;
   private double accelerationY;
   private double accelerationZ;
   public float damage = 5.0F;
   public int punch = 0;
   public boolean accelerate = false;
   public boolean explosive = false;
   public boolean explosiveDamage = true;
   public int explosiveRadius = 0;
   public EnumPotionType effect;
   public int duration;
   public int amplify;
   public IProjectileCallback callback;
   public ItemStack callbackItem;


   public EntityProjectile(World par1World) {
      super(par1World);
      this.effect = EnumPotionType.None;
      this.duration = 5;
      this.amplify = 0;
      this.setSize(0.25F, 0.25F);
   }

   protected void entityInit() {
      super.dataWatcher.addObjectByDataType(21, 5);
      super.dataWatcher.addObject(22, String.valueOf(""));
      super.dataWatcher.addObject(23, Integer.valueOf(5));
      super.dataWatcher.addObject(24, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(25, Integer.valueOf(10));
      super.dataWatcher.addObject(26, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(27, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(28, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(29, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(30, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(31, Byte.valueOf((byte)0));
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double par1) {
      double d1 = super.boundingBox.getAverageEdgeLength() * 4.0D;
      d1 *= 64.0D;
      return par1 < d1 * d1;
   }

   public EntityProjectile(World par1World, EntityLivingBase par2EntityLiving, ItemStack item, boolean isNPC) {
      super(par1World);
      this.effect = EnumPotionType.None;
      this.duration = 5;
      this.amplify = 0;
      this.thrower = par2EntityLiving;
      if(this.thrower != null) {
         this.throwerName = this.thrower.getUniqueID().toString();
      }

      this.setThrownItem(item);
      super.dataWatcher.updateObject(27, Byte.valueOf((byte)(this.getItem() == Items.arrow?1:0)));
      this.setSize((float)(super.dataWatcher.getWatchableObjectInt(23) / 10), (float)(super.dataWatcher.getWatchableObjectInt(23) / 10));
      this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
      super.posX -= (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * 0.1F);
      super.posY -= 0.10000000149011612D;
      super.posZ -= (double)(MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * 0.1F);
      this.setPosition(super.posX, super.posY, super.posZ);
      super.yOffset = 0.0F;
      if(isNPC) {
         this.npc = (EntityNPCInterface)this.thrower;
         this.getStatProperties(this.npc.stats);
      }

   }

   public void setThrownItem(ItemStack item) {
      super.dataWatcher.updateObject(21, item);
   }

   public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
      float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
      float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
      float yaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D);
      float pitch = this.hasGravity()?par7:(float)(Math.atan2(par3, (double)f3) * 180.0D / 3.141592653589793D);
      super.prevRotationYaw = super.rotationYaw = yaw;
      super.prevRotationPitch = super.rotationPitch = pitch;
      super.motionX = (double)(MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
      super.motionZ = (double)(MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
      super.motionY = (double)MathHelper.sin((pitch + 1.0F) / 180.0F * 3.1415927F);
      super.motionX += super.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
      super.motionZ += super.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
      super.motionY += super.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
      super.motionX *= (double)this.getSpeed();
      super.motionZ *= (double)this.getSpeed();
      super.motionY *= (double)this.getSpeed();
      this.accelerationX = par1 / (double)f2 * 0.1D;
      this.accelerationY = par3 / (double)f2 * 0.1D;
      this.accelerationZ = par5 / (double)f2 * 0.1D;
      this.ticksInGround = 0;
   }

   public float getAngleForXYZ(double varX, double varY, double varZ, double horiDist, boolean arc) {
      float g = this.getGravityVelocity();
      float var1 = this.getSpeed() * this.getSpeed();
      double var2 = (double)g * horiDist;
      double var3 = (double)g * horiDist * horiDist + 2.0D * varY * (double)var1;
      double var4 = (double)(var1 * var1) - (double)g * var3;
      if(var4 < 0.0D) {
         return 30.0F;
      } else {
         float var6 = arc?var1 + MathHelper.sqrt_double(var4):var1 - MathHelper.sqrt_double(var4);
         float var7 = (float)(Math.atan2((double)var6, var2) * 180.0D / 3.141592653589793D);
         return var7;
      }
   }

   public void shoot(float speed) {
      double varX = (double)(-MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.1415927F));
      double varZ = (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.1415927F));
      double varY = (double)(-MathHelper.sin(super.rotationPitch / 180.0F * 3.1415927F));
      this.setThrowableHeading(varX, varY, varZ, -super.rotationPitch, speed);
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
      if(!super.worldObj.isRemote || !this.field_70193_a) {
         this.setPosition(par1, par3, par5);
         this.setRotation(par7, par8);
      }
   }

   public void onUpdate() {
      super.onEntityUpdate();
      if(super.prevRotationPitch == 0.0F && super.prevRotationYaw == 0.0F) {
         float block = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
         super.prevRotationYaw = super.rotationYaw = (float)(Math.atan2(super.motionX, super.motionZ) * 180.0D / 3.141592653589793D);
         super.prevRotationPitch = super.rotationPitch = (float)(Math.atan2(super.motionY, (double)block) * 180.0D / 3.141592653589793D);
         if(this.isRotating()) {
            super.rotationPitch -= 20.0F;
         }
      }

      if(this.effect == EnumPotionType.Fire && !this.field_70193_a) {
         this.setFire(1);
      }

      Block var17 = super.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
      if((this.isArrow() || this.sticksToWalls()) && var17 != null) {
         var17.setBlockBoundsBasedOnState(super.worldObj, this.xTile, this.yTile, this.zTile);
         AxisAlignedBB vec3 = var17.getCollisionBoundingBoxFromPool(super.worldObj, this.xTile, this.yTile, this.zTile);
         if(vec3 != null && vec3.isVecInside(Vec3.createVectorHelper(super.posX, super.posY, super.posZ))) {
            this.field_70193_a = true;
         }
      }

      if(this.arrowShake > 0) {
         --this.arrowShake;
      }

      if(this.field_70193_a) {
         int var18 = super.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
         if(var17 == this.inTile && var18 == this.inData) {
            ++this.ticksInGround;
            if(this.ticksInGround == 1200) {
               this.setDead();
            }
         } else {
            this.field_70193_a = false;
            super.motionX *= (double)(super.rand.nextFloat() * 0.2F);
            super.motionY *= (double)(super.rand.nextFloat() * 0.2F);
            super.motionZ *= (double)(super.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.field_70195_i = 0;
         }
      } else {
         ++this.field_70195_i;
         if(this.field_70195_i == 1200) {
            this.setDead();
         }

         Vec3 var19 = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
         Vec3 vec31 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
         MovingObjectPosition movingobjectposition = super.worldObj.rayTraceBlocks(var19, vec31, false, true, false);
         var19 = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
         vec31 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
         if(movingobjectposition != null) {
            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
         }

         if(!super.worldObj.isRemote) {
            Entity otherEntity = null;
            List f = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.addCoord(super.motionX, super.motionY, super.motionZ).expand(1.0D, 1.0D, 1.0D));
            double f2 = 0.0D;
            EntityLivingBase k = this.getThrower();

            for(int f4 = 0; f4 < f.size(); ++f4) {
               Entity entity1 = (Entity)f.get(f4);
               if(entity1.canBeCollidedWith() && (!entity1.isEntityEqual(this.thrower) || this.field_70195_i >= 25)) {
                  float f1 = 0.3F;
                  AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                  MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(var19, vec31);
                  if(movingobjectposition1 != null) {
                     double d1 = var19.distanceTo(movingobjectposition1.hitVec);
                     if(d1 < f2 || f2 == 0.0D) {
                         otherEntity = entity1;
                        f2 = d1;
                     }
                  }
               }
            }

            if(otherEntity != null) {
               movingobjectposition = new MovingObjectPosition(otherEntity);
            }

            if(this.npc != null && movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
               EntityPlayer var25 = (EntityPlayer)movingobjectposition.entityHit;
               if(this.npc.faction.isFriendlyToPlayer(var25)) {
                  movingobjectposition = null;
               }
            }
         }

         if(movingobjectposition != null) {
            if(movingobjectposition.typeOfHit == MovingObjectType.BLOCK && super.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal) {
               this.setInPortal();
            } else {
               super.dataWatcher.updateObject(29, Byte.valueOf((byte)0));
               this.onImpact(movingobjectposition);
            }
         }

         super.posX += super.motionX;
         super.posY += super.motionY;
         super.posZ += super.motionZ;
         float var20 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
         super.rotationYaw = (float)(Math.atan2(super.motionX, super.motionZ) * 180.0D / 3.141592653589793D);

         for(super.rotationPitch = (float)(Math.atan2(super.motionY, (double)var20) * 180.0D / 3.141592653589793D); super.rotationPitch - super.prevRotationPitch < -180.0F; super.prevRotationPitch -= 360.0F) {
            ;
         }

         while(super.rotationPitch - super.prevRotationPitch >= 180.0F) {
            super.prevRotationPitch += 360.0F;
         }

         while(super.rotationYaw - super.prevRotationYaw < -180.0F) {
            super.prevRotationYaw -= 360.0F;
         }

         while(super.rotationYaw - super.prevRotationYaw >= 180.0F) {
            super.prevRotationYaw += 360.0F;
         }

         float var21 = this.isArrow()?0.0F:225.0F;
         super.rotationPitch = super.prevRotationPitch + (super.rotationPitch - super.prevRotationPitch) + var21 * 0.2F;
         super.rotationYaw = super.prevRotationYaw + (super.rotationYaw - super.prevRotationYaw) * 0.2F;
         if(this.isRotating()) {
            int var22 = this.isBlock()?10:20;
            super.rotationPitch -= (float)(this.field_70195_i % 15 * var22) * this.getSpeed();
         }

         float var23 = this.getMotionFactor();
         float f3 = this.getGravityVelocity();
         if(this.isInWater()) {
            if(super.worldObj.isRemote) {
               for(int var24 = 0; var24 < 4; ++var24) {
                  float var26 = 0.25F;
                  super.worldObj.spawnParticle("bubble", super.posX - super.motionX * (double)var26, super.posY - super.motionY * (double)var26, super.posZ - super.motionZ * (double)var26, super.motionX, super.motionY, super.motionZ);
               }
            }

            var23 = 0.8F;
         }

         super.motionX *= (double)var23;
         super.motionY *= (double)var23;
         super.motionZ *= (double)var23;
         if(this.hasGravity()) {
            super.motionY -= (double)f3;
         }

         if(this.accelerate) {
            super.motionX += this.accelerationX;
            super.motionY += this.accelerationY;
            super.motionZ += this.accelerationZ;
         }

         if(super.worldObj.isRemote && !super.dataWatcher.getWatchableObjectString(22).equals("")) {
            super.worldObj.spawnParticle(super.dataWatcher.getWatchableObjectString(22), super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D);
         }

         this.setPosition(super.posX, super.posY, super.posZ);
         this.doBlockCollisions();
      }

   }

   public boolean isBlock() {
      ItemStack item = this.getItemDisplay();
      return item == null?false:item.getItem() instanceof ItemBlock;
   }

   private Item getItem() {
      ItemStack item = this.getItemDisplay();
      return item == null?null:item.getItem();
   }

   protected float getMotionFactor() {
      return this.accelerate?0.95F:1.0F;
   }

   protected void onImpact(MovingObjectPosition movingobjectposition) {
      float axisalignedbb;
      int var12;
      int var15;
      if(movingobjectposition.entityHit != null) {
         if(this.callback != null && this.callbackItem != null && movingobjectposition.entityHit instanceof EntityLivingBase && this.callback.onImpact(this, (EntityLivingBase)movingobjectposition.entityHit, this.callbackItem)) {
            return;
         }

         axisalignedbb = this.damage;
         if(axisalignedbb == 0.0F) {
            axisalignedbb = 0.001F;
         }

         if(movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), axisalignedbb)) {
            if(movingobjectposition.entityHit instanceof EntityLivingBase && (this.isArrow() || this.sticksToWalls())) {
               EntityLivingBase list1 = (EntityLivingBase)movingobjectposition.entityHit;
               if(!super.worldObj.isRemote) {
                  list1.setArrowCountInEntity(list1.getArrowCountInEntity() + 1);
               }

               if(this.destroyedOnEntityHit && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
                  this.setDead();
               }
            }

            if(this.isBlock()) {
               super.worldObj.playAuxSFX(2001, (int)movingobjectposition.entityHit.posX, (int)movingobjectposition.entityHit.posY, (int)movingobjectposition.entityHit.posZ, Item.getIdFromItem(this.getItem()));
            } else if(!this.isArrow() && !this.sticksToWalls()) {
               for(var15 = 0; var15 < 8; ++var15) {
                  super.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(this.getItem()), super.posX, super.posY, super.posZ, super.rand.nextGaussian() * 0.15D, super.rand.nextGaussian() * 0.2D, super.rand.nextGaussian() * 0.15D);
               }
            }

            if(this.punch > 0) {
               float var16 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
               if(var16 > 0.0F) {
                  movingobjectposition.entityHit.addVelocity(super.motionX * (double)this.punch * 0.6000000238418579D / (double)var16, 0.1D, super.motionZ * (double)this.punch * 0.6000000238418579D / (double)var16);
               }
            }

            if(this.effect != EnumPotionType.None && movingobjectposition.entityHit instanceof EntityLivingBase) {
               if(this.effect != EnumPotionType.Fire) {
                  var15 = this.getPotionEffect(this.effect);
                  ((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(var15, this.duration * 20, this.amplify));
               } else {
                  movingobjectposition.entityHit.setFire(this.duration);
               }
            }
         } else if(this.hasGravity() && (this.isArrow() || this.sticksToWalls())) {
            super.motionX *= -0.10000000149011612D;
            super.motionY *= -0.10000000149011612D;
            super.motionZ *= -0.10000000149011612D;
            super.rotationYaw += 180.0F;
            super.prevRotationYaw += 180.0F;
            this.field_70195_i = 0;
         }
      } else if(!this.isArrow() && !this.sticksToWalls()) {
         if(this.isBlock()) {
            super.worldObj.playAuxSFX(2001, MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY), MathHelper.floor_double(super.posZ), Item.getIdFromItem(this.getItem()));
         } else {
            for(var12 = 0; var12 < 8; ++var12) {
               super.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(this.getItem()), super.posX, super.posY, super.posZ, super.rand.nextGaussian() * 0.15D, super.rand.nextGaussian() * 0.2D, super.rand.nextGaussian() * 0.15D);
            }
         }
      } else {
         this.xTile = movingobjectposition.blockX;
         this.yTile = movingobjectposition.blockY;
         this.zTile = movingobjectposition.blockZ;
         this.inTile = super.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
         this.inData = super.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
         super.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - super.posX));
         super.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - super.posY));
         super.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - super.posZ));
         axisalignedbb = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
         super.posX -= super.motionX / (double)axisalignedbb * 0.05000000074505806D;
         super.posY -= super.motionY / (double)axisalignedbb * 0.05000000074505806D;
         super.posZ -= super.motionZ / (double)axisalignedbb * 0.05000000074505806D;
         this.field_70193_a = true;
         if(this.isArrow()) {
            this.playSound("random.bowhit", 1.0F, 1.2F / (super.rand.nextFloat() * 0.2F + 0.9F));
         } else {
            this.playSound("random.break", 1.0F, 1.2F / (super.rand.nextFloat() * 0.2F + 0.9F));
         }

         this.arrowShake = 7;
         if(!this.hasGravity()) {
            super.dataWatcher.updateObject(26, Byte.valueOf((byte)1));
         }

         if(this.inTile != null) {
            this.inTile.onEntityCollidedWithBlock(super.worldObj, this.xTile, this.yTile, this.zTile, this);
         }
      }

      if(this.explosive) {
         Iterator iterator;
         if(this.explosiveRadius == 0 && this.effect != EnumPotionType.None) {
            if(this.effect == EnumPotionType.Fire) {
               var12 = movingobjectposition.blockX;
               var15 = movingobjectposition.blockY;
               int var19 = movingobjectposition.blockZ;
               switch(movingobjectposition.sideHit) {
               case 0:
                  --var15;
                  break;
               case 1:
                  ++var15;
                  break;
               case 2:
                  --var19;
                  break;
               case 3:
                  ++var19;
                  break;
               case 4:
                  --var12;
                  break;
               case 5:
                  ++var12;
               }

               if(super.worldObj.isAirBlock(var12, var15, var19)) {
                  super.worldObj.setBlock(var12, var15, var19, Blocks.fire);
               }
            } else {
               AxisAlignedBB var14 = super.boundingBox.expand(4.0D, 2.0D, 4.0D);
               List var18 = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var14);
               if(var18 != null && !var18.isEmpty()) {
                  iterator = var18.iterator();

                  while(iterator.hasNext()) {
                     EntityLivingBase var20 = (EntityLivingBase)iterator.next();
                     double d0 = this.getDistanceSqToEntity(var20);
                     if(d0 < 16.0D) {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                        if(var20 == movingobjectposition.entityHit) {
                           d1 = 1.0D;
                        }

                        int i = this.getPotionEffect(this.effect);
                        if(Potion.potionTypes[i].isInstant()) {
                           Potion.potionTypes[i].affectEntity(this.getThrower(), var20, this.amplify, d1);
                        } else {
                           int j = (int)(d1 * (double)this.duration + 0.5D);
                           if(j > 20) {
                              var20.addPotionEffect(new PotionEffect(i, j, this.amplify));
                           }
                        }
                     }
                  }
               }

               super.worldObj.playAuxSFX(2002, (int)Math.round(super.posX), (int)Math.round(super.posY), (int)Math.round(super.posZ), this.getPotionColor(this.effect));
            }
         } else {
            boolean var13 = super.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && this.explosiveDamage;
            Explosion var17 = new Explosion(super.worldObj, this, super.posX, super.posY, super.posZ, (float)this.explosiveRadius);
            var17.isFlaming = this.effect == EnumPotionType.Fire;
            var17.isSmoking = var13;
            if(var13) {
               var17.doExplosionA();
            }

            var17.doExplosionB(super.worldObj.isRemote);
            if(!super.worldObj.isRemote) {
               iterator = super.worldObj.playerEntities.iterator();

               while(iterator.hasNext()) {
                  EntityPlayer entitylivingbase = (EntityPlayer)iterator.next();
                  if(entitylivingbase.getDistanceSq(super.posX, super.posY, super.posZ) < 4096.0D) {
                     ((EntityPlayerMP)entitylivingbase).playerNetServerHandler.sendPacket(new S27PacketExplosion(super.posX, super.posY, super.posZ, (float)this.explosiveRadius, var17.affectedBlockPositions, (Vec3)var17.func_77277_b().get(entitylivingbase)));
                  }
               }
            }

            if(this.explosiveRadius != 0 && (this.isArrow() || this.sticksToWalls())) {
               this.setDead();
            }
         }
      }

      if(!super.worldObj.isRemote && !this.isArrow() && !this.sticksToWalls()) {
         this.setDead();
      }

   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("xTile", (short)this.xTile);
      par1NBTTagCompound.setShort("yTile", (short)this.yTile);
      par1NBTTagCompound.setShort("zTile", (short)this.zTile);
      par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.inTile));
      par1NBTTagCompound.setByte("inData", (byte)this.inData);
      par1NBTTagCompound.setByte("shake", (byte)this.field_70191_b);
      par1NBTTagCompound.setByte("inGround", (byte)(this.field_70193_a?1:0));
      par1NBTTagCompound.setByte("isArrow", (byte)(this.isArrow()?1:0));
      par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(new double[]{super.motionX, super.motionY, super.motionZ}));
      par1NBTTagCompound.setBoolean("canBePickedUp", this.canBePickedUp);
      if((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
         this.throwerName = this.thrower.getUniqueID().toString();
      }

      par1NBTTagCompound.setString("ownerName", this.throwerName == null?"":this.throwerName);
      if(this.getItemDisplay() != null) {
         par1NBTTagCompound.setTag("Item", this.getItemDisplay().writeToNBT(new NBTTagCompound()));
      }

      par1NBTTagCompound.setFloat("damagev2", this.damage);
      par1NBTTagCompound.setInteger("punch", this.punch);
      par1NBTTagCompound.setInteger("size", super.dataWatcher.getWatchableObjectInt(23));
      par1NBTTagCompound.setInteger("velocity", super.dataWatcher.getWatchableObjectInt(25));
      par1NBTTagCompound.setInteger("explosiveRadius", this.explosiveRadius);
      par1NBTTagCompound.setInteger("effectDuration", this.duration);
      par1NBTTagCompound.setBoolean("gravity", this.hasGravity());
      par1NBTTagCompound.setBoolean("accelerate", this.accelerate);
      par1NBTTagCompound.setByte("glows", super.dataWatcher.getWatchableObjectByte(24));
      par1NBTTagCompound.setBoolean("explosive", this.explosive);
      par1NBTTagCompound.setInteger("PotionEffect", this.effect.ordinal());
      par1NBTTagCompound.setString("trail", super.dataWatcher.getWatchableObjectString(22));
      par1NBTTagCompound.setByte("Render3D", super.dataWatcher.getWatchableObjectByte(28));
      par1NBTTagCompound.setByte("Spins", super.dataWatcher.getWatchableObjectByte(29));
      par1NBTTagCompound.setByte("Sticks", super.dataWatcher.getWatchableObjectByte(30));
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.xTile = par1NBTTagCompound.getShort("xTile");
      this.yTile = par1NBTTagCompound.getShort("yTile");
      this.zTile = par1NBTTagCompound.getShort("zTile");
      this.inTile = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
      this.inData = par1NBTTagCompound.getByte("inData") & 255;
      this.field_70191_b = par1NBTTagCompound.getByte("shake") & 255;
      this.field_70193_a = par1NBTTagCompound.getByte("inGround") == 1;
      super.dataWatcher.updateObject(27, Byte.valueOf(par1NBTTagCompound.getByte("isArrow")));
      this.throwerName = par1NBTTagCompound.getString("ownerName");
      this.canBePickedUp = par1NBTTagCompound.getBoolean("canBePickedUp");
      this.damage = par1NBTTagCompound.getFloat("damagev2");
      this.punch = par1NBTTagCompound.getInteger("punch");
      this.explosiveRadius = par1NBTTagCompound.getInteger("explosiveRadius");
      this.duration = par1NBTTagCompound.getInteger("effectDuration");
      this.accelerate = par1NBTTagCompound.getBoolean("accelerate");
      this.explosive = par1NBTTagCompound.getBoolean("explosive");
      this.effect = EnumPotionType.values()[par1NBTTagCompound.getInteger("PotionEffect") % EnumPotionType.values().length];
      super.dataWatcher.updateObject(22, par1NBTTagCompound.getString("trail"));
      super.dataWatcher.updateObject(23, Integer.valueOf(par1NBTTagCompound.getInteger("size")));
      super.dataWatcher.updateObject(24, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("glows")?1:0)));
      super.dataWatcher.updateObject(25, Integer.valueOf(par1NBTTagCompound.getInteger("velocity")));
      super.dataWatcher.updateObject(26, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("gravity")?1:0)));
      super.dataWatcher.updateObject(28, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("Render3D")?1:0)));
      super.dataWatcher.updateObject(29, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("Spins")?1:0)));
      super.dataWatcher.updateObject(30, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("Sticks")?1:0)));
      if(this.throwerName != null && this.throwerName.length() == 0) {
         this.throwerName = null;
      }

      if(par1NBTTagCompound.hasKey("direction")) {
         NBTTagList var2 = par1NBTTagCompound.getTagList("direction", 6);
         super.motionX = var2.getDoubleAt(0);
         super.motionY = var2.getDoubleAt(1);
         super.motionZ = var2.getDoubleAt(2);
      }

      NBTTagCompound var21 = par1NBTTagCompound.getCompoundTag("Item");
      ItemStack item = ItemStack.loadItemStackFromNBT(var21);
      if(item == null) {
         this.setDead();
      } else {
         super.dataWatcher.updateObject(21, item);
      }

   }

   public EntityLivingBase getThrower() {
      if(this.throwerName != null && !this.throwerName.isEmpty()) {
         try {
            UUID ex = UUID.fromString(this.throwerName);
            if(this.thrower == null && ex != null) {
               this.thrower = super.worldObj.getPlayerEntityByUUID(ex);
            }
         } catch (IllegalArgumentException var2) {
            ;
         }

         return this.thrower;
      } else {
         return null;
      }
   }

   private int getPotionEffect(EnumPotionType p) {
      switch(p) {
      case Poison:
         return Potion.poison.id;
      case Hunger:
         return Potion.hunger.id;
      case Weakness:
         return Potion.weakness.id;
      case Slowness:
         return Potion.moveSlowdown.id;
      case Nausea:
         return Potion.confusion.id;
      case Blindness:
         return Potion.blindness.id;
      case Wither:
         return Potion.wither.id;
      default:
         return 0;
      }
   }

   private int getPotionColor(EnumPotionType p) {
      switch(p) {
      case Poison:
         return 32660;
      case Hunger:
         return 32660;
      case Weakness:
         return 32696;
      case Slowness:
         return 32698;
      case Nausea:
         return 32732;
      case Blindness:
         return Potion.blindness.id;
      case Wither:
         return 32732;
      default:
         return 0;
      }
   }

   public void getStatProperties(DataStats stats) {
      this.damage = (float)stats.pDamage;
      this.punch = stats.pImpact;
      this.accelerate = stats.pXlr8;
      this.explosive = stats.pExplode;
      this.explosiveRadius = stats.pArea;
      this.effect = stats.pEffect;
      this.duration = stats.pDur;
      this.amplify = stats.pEffAmp;
      this.setParticleEffect(stats.pTrail);
      super.dataWatcher.updateObject(23, Integer.valueOf(stats.pSize));
      super.dataWatcher.updateObject(24, Byte.valueOf((byte)(stats.pGlows?1:0)));
      this.setSpeed(stats.pSpeed);
      this.setHasGravity(stats.pPhysics);
      this.setIs3D(stats.pRender3D);
      this.setRotating(stats.pSpin);
      this.setStickInWall(stats.pStick);
   }

   public void setParticleEffect(EnumParticleType type) {
      super.dataWatcher.updateObject(22, type.particleName);
   }

   public void setHasGravity(boolean bo) {
      super.dataWatcher.updateObject(26, Byte.valueOf((byte)(bo?1:0)));
   }

   public void setIs3D(boolean bo) {
      super.dataWatcher.updateObject(28, Byte.valueOf((byte)(bo?1:0)));
   }

   public void setStickInWall(boolean bo) {
      super.dataWatcher.updateObject(30, Byte.valueOf((byte)(bo?1:0)));
   }

   public ItemStack getItemDisplay() {
      return super.dataWatcher.getWatchableObjectItemStack(21);
   }

   public float getBrightness(float par1) {
      return super.dataWatcher.getWatchableObjectByte(24) == 1?1.0F:super.getBrightness(par1);
   }

   @SideOnly(Side.CLIENT)
   public int getBrightnessForRender(float par1) {
      return super.dataWatcher.getWatchableObjectByte(24) == 1?15728880:super.getBrightnessForRender(par1);
   }

   public boolean hasGravity() {
      return super.dataWatcher.getWatchableObjectByte(26) == 1;
   }

   public void setSpeed(int speed) {
      super.dataWatcher.updateObject(25, Integer.valueOf(speed));
   }

   public float getSpeed() {
      return (float)super.dataWatcher.getWatchableObjectInt(25) / 10.0F;
   }

   public boolean isArrow() {
      return super.dataWatcher.getWatchableObjectByte(27) == 1;
   }

   public void setRotating(boolean bo) {
      super.dataWatcher.updateObject(29, Byte.valueOf((byte)(bo?1:0)));
   }

   public boolean isRotating() {
      return super.dataWatcher.getWatchableObjectByte(29) == 1;
   }

   public boolean glows() {
      return super.dataWatcher.getWatchableObjectByte(24) == 1;
   }

   public boolean is3D() {
      return super.dataWatcher.getWatchableObjectByte(28) == 1 || this.isBlock();
   }

   public boolean sticksToWalls() {
      return this.is3D() && super.dataWatcher.getWatchableObjectByte(30) == 1;
   }

   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
      if(!super.worldObj.isRemote && this.canBePickedUp && this.field_70193_a && this.arrowShake <= 0) {
         if(par1EntityPlayer.inventory.addItemStackToInventory(this.getItemDisplay())) {
            this.field_70193_a = false;
            this.playSound("random.pop", 0.2F, ((super.rand.nextFloat() - super.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            par1EntityPlayer.onItemPickup(this, 1);
            this.setDead();
         }

      }
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public IChatComponent getFormattedCommandSenderName() {
      return (IChatComponent)(this.getItemDisplay() != null?new ChatComponentTranslation(this.getItemDisplay().getDisplayName(), new Object[0]):super.getFormattedCommandSenderName());
   }
}
