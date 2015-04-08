package noppes.npcs.ai;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIOrbitTarget extends EntityAIBase {

   private EntityNPCInterface theEntity;
   private EntityLivingBase targetEntity;
   private double movePosX;
   private double movePosY;
   private double movePosZ;
   private double speed;
   private float distance;
   private int delay = 0;
   private float angle = 0.0F;
   private int direction = 1;
   private float targetDistance;
   private boolean decay;
   private boolean canNavigate = true;
   private float decayRate = 1.0F;
   private int tick = 0;


   public EntityAIOrbitTarget(EntityNPCInterface par1EntityCreature, double par2, float par4, boolean par5) {
      this.theEntity = par1EntityCreature;
      this.speed = par2;
      this.distance = par4;
      this.decay = par5;
      this.setMutexBits(AiMutex.PASSIVE + AiMutex.LOOK);
   }

   public boolean shouldExecute() {
      if(--this.delay > 0) {
         return false;
      } else {
         this.targetEntity = this.theEntity.getAttackTarget();
         if(this.targetEntity == null) {
            return false;
         } else {
            double d0 = (double)this.theEntity.getDistanceToEntity(this.targetEntity);
            return d0 >= (double)(this.distance / 2.0F) && (this.theEntity.inventory.getProjectile() != null || d0 <= (double)this.distance);
         }
      }
   }

   public boolean continueExecuting() {
      double d0 = (double)this.targetEntity.getDistanceToEntity(this.theEntity);
      return this.targetEntity.isEntityAlive() && d0 >= (double)(this.distance / 2.0F) && d0 <= (double)(this.distance * 1.5F) && !this.theEntity.isInWater() && this.canNavigate;
   }

   public void resetTask() {
      this.theEntity.getNavigator().clearPathEntity();
      this.delay = 60;
      if(this.theEntity.getRangedTask() != null) {
         this.theEntity.getRangedTask().navOverride(false);
      }

   }

   public void startExecuting() {
      this.canNavigate = true;
      Random random = this.theEntity.getRNG();
      this.direction = random.nextInt(10) > 5?1:-1;
      this.decayRate = random.nextFloat() + this.distance / 16.0F;
      this.targetDistance = this.theEntity.getDistanceToEntity(this.targetEntity);
      double d0 = this.theEntity.posX - this.targetEntity.posX;
      double d1 = this.theEntity.posZ - this.targetEntity.posZ;
      this.angle = (float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D);
      if(this.theEntity.getRangedTask() != null) {
         this.theEntity.getRangedTask().navOverride(true);
      }

   }

   public void updateTask() {
      this.theEntity.getLookHelper().setLookPositionWithEntity(this.targetEntity, 30.0F, 30.0F);
      if(this.theEntity.getNavigator().noPath() && this.tick >= 0 && this.theEntity.onGround && !this.theEntity.isInWater()) {
         double d0 = (double)this.targetDistance * (double)MathHelper.cos(this.angle / 180.0F * 3.1415927F);
         double d1 = (double)this.targetDistance * (double)MathHelper.sin(this.angle / 180.0F * 3.1415927F);
         this.movePosX = this.targetEntity.posX + d0;
         this.movePosY = this.targetEntity.boundingBox.maxY;
         this.movePosZ = this.targetEntity.posZ + d1;
         this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
         this.angle += 15.0F * (float)this.direction;
         this.tick = MathHelper.ceiling_double_int(this.theEntity.getDistance(this.movePosX, this.movePosY, this.movePosZ) / (double)(this.theEntity.getSpeed() / 20.0F));
         if(this.decay) {
            this.targetDistance -= this.decayRate;
         }
      }

      if(this.tick >= 0) {
         --this.tick;
      }

   }
}
