package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIFollow extends EntityAIBase {

   private EntityNPCInterface npc;
   private EntityLivingBase owner;
   private double distance;
   public int updateTick = 0;


   public EntityAIFollow(EntityNPCInterface npc) {
      this.npc = npc;
      this.setMutexBits(AiMutex.PASSIVE + AiMutex.LOOK);
   }

   public boolean shouldExecute() {
      return !this.excute()?false:this.distance > (double)this.npc.followRange();
   }

   public boolean excute() {
      if(this.npc.isEntityAlive() && this.npc.isFollower() && !this.npc.isAttacking() && (this.owner = this.npc.getOwner()) != null && this.npc.ai.animationType != EnumAnimation.SITTING) {
         this.distance = this.npc.getDistanceSqToEntity(this.owner);
         return true;
      } else {
         return false;
      }
   }

   public void startExecuting() {
      this.updateTick = 10;
   }

   public boolean continueExecuting() {
      return !this.npc.getNavigator().noPath() && this.distance > 4.0D && this.excute();
   }

   public void resetTask() {
      this.owner = null;
      this.npc.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      ++this.updateTick;
      if(this.updateTick >= 10) {
         this.updateTick = 0;
         this.npc.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float)this.npc.getVerticalFaceSpeed());
         double speed = 1.0D + this.distance / 150.0D;
         if(speed > 3.0D) {
            speed = 3.0D;
         }

         if(!this.npc.getNavigator().tryMoveToEntityLiving(this.owner, speed) && this.distance >= 225.0D) {
            int i = MathHelper.floor_double(this.owner.posX) - 2;
            int j = MathHelper.floor_double(this.owner.posZ) - 2;
            int k = MathHelper.floor_double(this.owner.boundingBox.minY);

            for(int l = 0; l <= 4; ++l) {
               for(int i1 = 0; i1 <= 4; ++i1) {
                  if((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.doesBlockHaveSolidTopSurface(this.npc.worldObj, i + l, k - 1, j + i1) && !this.npc.worldObj.getBlock(i + l, k, j + i1).isNormalCube() && !this.npc.worldObj.getBlock(i + l, k + 1, j + i1).isNormalCube()) {
                     this.npc.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.npc.rotationYaw, this.npc.rotationPitch);
                     this.npc.getNavigator().clearPathEntity();
                     return;
                  }
               }
            }

         }
      }
   }
}
