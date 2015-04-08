package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAttackTarget extends EntityAIBase {

   World worldObj;
   EntityNPCInterface attacker;
   EntityLivingBase entityTarget;
   int attackTick = 0;
   PathEntity entityPathEntity;
   private int field_75445_i;
   private boolean navOverride = false;


   public EntityAIAttackTarget(EntityNPCInterface par1EntityLiving) {
      this.attacker = par1EntityLiving;
      this.worldObj = par1EntityLiving.worldObj;
      this.setMutexBits(this.navOverride?AiMutex.PATHING:AiMutex.LOOK + AiMutex.PASSIVE);
   }

   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
      if(entitylivingbase == null) {
         return false;
      } else if(!entitylivingbase.isEntityAlive()) {
         return false;
      } else if(this.attacker.inventory.getProjectile() != null && this.attacker.ai.useRangeMelee == 0) {
         return false;
      } else {
         double var2 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
         double var3 = (double)(this.attacker.ai.distanceToMelee * this.attacker.ai.distanceToMelee);
         if(this.attacker.ai.useRangeMelee == 1 && var2 > var3) {
            return false;
         } else {
            this.entityTarget = entitylivingbase;
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
            return this.entityPathEntity != null;
         }
      }
   }

   public boolean continueExecuting() {
      this.entityTarget = this.attacker.getAttackTarget();
      return this.entityTarget != null && this.entityTarget.isEntityAlive()?(this.attacker.getDistanceToEntity(this.entityTarget) > (float)this.attacker.stats.aggroRange?false:(this.attacker.ai.useRangeMelee == 1 && this.attacker.getDistanceSqToEntity(this.entityTarget) > (double)(this.attacker.ai.distanceToMelee * this.attacker.ai.distanceToMelee)?false:this.attacker.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ)))):false;
   }

   public void startExecuting() {
      if(!this.navOverride) {
         this.attacker.getNavigator().setPath(this.entityPathEntity, 1.3D);
      }

      this.field_75445_i = 0;
      if(this.attacker.getRangedTask() != null && this.attacker.ai.useRangeMelee == 2) {
         this.attacker.getRangedTask().navOverride(true);
      }

   }

   public void resetTask() {
      this.entityPathEntity = null;
      this.entityTarget = null;
      this.attacker.setAttackTarget((EntityLivingBase)null);
      this.attacker.getNavigator().clearPathEntity();
      if(this.attacker.getRangedTask() != null && this.attacker.ai.useRangeMelee == 2) {
         this.attacker.getRangedTask().navOverride(false);
      }

   }

   public void updateTask() {
      this.attacker.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
      if(!this.navOverride && --this.field_75445_i <= 0) {
         this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
         this.attacker.getNavigator().tryMoveToEntityLiving(this.entityTarget, 1.2999999523162842D);
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      double distance = this.attacker.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
      double range = (double)((float)(this.attacker.stats.attackRange * this.attacker.stats.attackRange) + this.entityTarget.width);
      double minRange = (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + this.entityTarget.width);
      if(minRange > range) {
         range = minRange;
      }

      if(distance <= range && this.attacker.canSee(this.entityTarget) && this.attackTick <= 0) {
         this.attackTick = this.attacker.stats.attackSpeed;
         this.attacker.swingItem();
         this.attacker.attackEntityAsMob(this.entityTarget);
      }

   }

   public void navOverride(boolean nav) {
      this.navOverride = nav;
      this.setMutexBits(this.navOverride?AiMutex.PATHING:AiMutex.LOOK + AiMutex.PASSIVE);
   }
}
