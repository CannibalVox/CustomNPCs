package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ai.RandomPositionGeneratorAlt;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIReturn extends EntityAIBase {

   public static final int MaxTotalTicks = 600;
   private final EntityNPCInterface npc;
   private int stuckTicks = 0;
   private int totalTicks = 0;
   private double endPosX;
   private double endPosY;
   private double endPosZ;
   private boolean wasAttacked = false;
   private double[] preAttackPos;
   private int stuckCount = 0;


   public EntityAIReturn(EntityNPCInterface npc) {
      this.npc = npc;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.npc.hasOwner() && this.npc.ai.returnToStart && !this.npc.isKilled() && this.npc.getNavigator().noPath() && !this.npc.isInteracting()) {
         if(this.npc.ai.findShelter == 0 && (!this.npc.worldObj.isDaytime() || this.npc.worldObj.isRaining()) && !this.npc.worldObj.provider.hasNoSky) {
            if(this.npc.worldObj.canBlockSeeTheSky((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos()) || this.npc.worldObj.getFullBlockLightValue((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos()) <= 8) {
               return false;
            }
         } else if(this.npc.ai.findShelter == 1 && this.npc.worldObj.isDaytime() && this.npc.worldObj.canBlockSeeTheSky((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos())) {
            return false;
         }

         if(this.npc.isAttacking()) {
            if(!this.wasAttacked) {
               this.wasAttacked = true;
               this.preAttackPos = new double[]{this.npc.posX, this.npc.posY, this.npc.posZ};
            }

            return false;
         } else {
            return !this.npc.isAttacking() && this.wasAttacked?true:(this.npc.ai.movingType == EnumMovingType.MovingPath && this.npc.ai.getDistanceSqToPathPoint() < (double)(CustomNpcs.NpcNavRange * CustomNpcs.NpcNavRange)?false:(this.npc.ai.movingType == EnumMovingType.Wandering?this.npc.getDistanceSq((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos()) > (double)(this.npc.ai.walkingRange * this.npc.ai.walkingRange):(this.npc.ai.movingType == EnumMovingType.Standing?!this.npc.isVeryNearAssignedPlace():false)));
         }
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      return !this.npc.isFollower() && !this.npc.isKilled() && !this.npc.isAttacking() && !this.npc.isVeryNearAssignedPlace() && this.totalTicks <= 600 && !this.npc.isInteracting();
   }

   public void updateTask() {
      ++this.totalTicks;
      if(this.totalTicks > 600) {
         this.npc.setPosition(this.endPosX, this.endPosY, this.endPosZ);
         this.npc.getNavigator().clearPathEntity();
      } else {
         if(this.stuckTicks > 0) {
            --this.stuckTicks;
         } else if(this.npc.getNavigator().noPath()) {
            ++this.stuckCount;
            this.stuckTicks = 10;
            if((this.totalTicks <= 30 || !this.wasAttacked || !this.isTooFar()) && this.stuckCount <= 5) {
               this.navigate(this.stuckCount % 2 == 1);
            } else {
               this.npc.setPosition(this.endPosX, this.endPosY, this.endPosZ);
               this.npc.getNavigator().clearPathEntity();
            }
         } else {
            this.stuckCount = 0;
         }

      }
   }

   private boolean isTooFar() {
      int allowedDistance = this.npc.stats.aggroRange * 2;
      if(this.npc.ai.movingType == EnumMovingType.Wandering) {
         allowedDistance += this.npc.ai.walkingRange;
      }

      return this.npc.getDistanceSq(this.endPosX, this.endPosY, this.endPosZ) > (double)(allowedDistance * allowedDistance);
   }

   public void startExecuting() {
      this.stuckTicks = 0;
      this.totalTicks = 0;
      this.stuckCount = 0;
      this.navigate(false);
   }

   private void navigate(boolean towards) {
      if(!this.wasAttacked) {
         this.endPosX = (double)this.npc.getStartXPos();
         this.endPosY = this.npc.getStartYPos();
         this.endPosZ = (double)this.npc.getStartZPos();
      } else {
         this.endPosX = this.preAttackPos[0];
         this.endPosY = this.preAttackPos[1];
         this.endPosZ = this.preAttackPos[2];
      }

      double posX = this.endPosX;
      double posY = this.endPosY;
      double posZ = this.endPosZ;
      double range = this.npc.getDistance(posX, posY, posZ);
      if(range > (double)CustomNpcs.NpcNavRange || towards) {
         int distance = (int)range;
         if(distance > CustomNpcs.NpcNavRange) {
            distance = CustomNpcs.NpcNavRange / 2;
         } else {
            distance /= 2;
         }

         if(distance > 2) {
            Vec3 start = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 pos = RandomPositionGeneratorAlt.findRandomTargetBlockTowards(this.npc, distance, distance / 2 > 7?7:distance / 2, start);
            if(pos != null) {
               posX = pos.xCoord;
               posY = pos.yCoord;
               posZ = pos.zCoord;
            }
         }
      }

      this.npc.getNavigator().clearPathEntity();
      this.npc.getNavigator().tryMoveToXYZ(posX, posY, posZ, 1.0D);
   }

   public void resetTask() {
      this.wasAttacked = false;
      this.npc.getNavigator().clearPathEntity();
   }
}
