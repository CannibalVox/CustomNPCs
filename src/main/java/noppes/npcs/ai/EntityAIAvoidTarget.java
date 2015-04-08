package noppes.npcs.ai;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;
import noppes.npcs.ai.RandomPositionGeneratorAlt;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.constants.EnumNavType;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAvoidTarget extends EntityAIBase {

   private EntityNPCInterface theEntity;
   private Entity closestLivingEntity;
   private float distanceFromEntity;
   private float health;
   private PathEntity entityPathEntity;
   private PathNavigate entityPathNavigate;
   private Class targetEntityClass;


   public EntityAIAvoidTarget(EntityNPCInterface par1EntityNPC) {
      this.theEntity = par1EntityNPC;
      this.distanceFromEntity = (float)this.theEntity.stats.aggroRange;
      this.health = this.theEntity.getHealth();
      this.entityPathNavigate = par1EntityNPC.getNavigator();
      this.setMutexBits(AiMutex.PASSIVE + AiMutex.LOOK);
   }

   public boolean shouldExecute() {
      EntityLivingBase target = this.theEntity.getAttackTarget();
      if(target == null) {
         return false;
      } else {
         this.targetEntityClass = target.getClass();
         if(this.targetEntityClass == EntityPlayer.class) {
            this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, (double)this.distanceFromEntity);
            if(this.closestLivingEntity == null) {
               return false;
            }
         } else {
            List var2 = this.theEntity.worldObj.getEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double)this.distanceFromEntity, 3.0D, (double)this.distanceFromEntity));
            if(var2.isEmpty()) {
               return false;
            }

            this.closestLivingEntity = (Entity)var2.get(0);
         }

         if(!this.theEntity.getEntitySenses().canSee(this.closestLivingEntity) && this.theEntity.ai.directLOS) {
            return false;
         } else {
            Vec3 var21 = RandomPositionGeneratorAlt.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
            boolean var3 = this.theEntity.inventory.getProjectile() == null || this.theEntity.ai.useRangeMelee == 2;
            boolean var4 = var3?this.health == this.theEntity.getHealth():this.theEntity.getRangedTask() != null && !this.theEntity.getRangedTask().hasFired();
            if(var21 == null) {
               return false;
            } else if(this.closestLivingEntity.getDistanceSq(var21.xCoord, var21.yCoord, var21.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
               return false;
            } else if(this.theEntity.ai.tacticalVariant == EnumNavType.HitNRun && var4) {
               return false;
            } else {
               this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var21.xCoord, var21.yCoord, var21.zCoord);
               return this.entityPathEntity == null?false:this.entityPathEntity.isDestinationSame(var21);
            }
         }
      }
   }

   public boolean continueExecuting() {
      return !this.entityPathNavigate.noPath();
   }

   public void startExecuting() {
      this.entityPathNavigate.setPath(this.entityPathEntity, 1.0D);
   }

   public void resetTask() {
      this.closestLivingEntity = null;
      this.theEntity.setAttackTarget((EntityLivingBase)null);
   }

   public void updateTask() {
      if(this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
         this.theEntity.getNavigator().setSpeed(1.2D);
      } else {
         this.theEntity.getNavigator().setSpeed(1.0D);
      }

      if(this.theEntity.ai.tacticalVariant == EnumNavType.HitNRun) {
         float dist = this.theEntity.getDistanceToEntity(this.closestLivingEntity);
         if(dist > this.distanceFromEntity || dist < (float)this.theEntity.ai.tacticalRadius) {
            this.health = this.theEntity.getHealth();
         }
      }

   }
}
