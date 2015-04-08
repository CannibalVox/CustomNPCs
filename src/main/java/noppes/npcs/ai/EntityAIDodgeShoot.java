package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;
import noppes.npcs.ai.RandomPositionGeneratorAlt;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIDodgeShoot extends EntityAIBase {

   private EntityNPCInterface entity;
   private double xPosition;
   private double yPosition;
   private double zPosition;


   public EntityAIDodgeShoot(EntityNPCInterface par1EntityNPCInterface) {
      this.entity = par1EntityNPCInterface;
      this.setMutexBits(AiMutex.PASSIVE);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.entity.getAttackTarget();
      if(var1 != null && var1.isEntityAlive()) {
         if(this.entity.inventory.getProjectile() == null) {
            return false;
         } else if(this.entity.getRangedTask() == null) {
            return false;
         } else {
            Vec3 vec = this.entity.getRangedTask().hasFired()?RandomPositionGeneratorAlt.findRandomTarget(this.entity, 4, 1):null;
            if(vec == null) {
               return false;
            } else {
               this.xPosition = vec.xCoord;
               this.yPosition = vec.yCoord;
               this.zPosition = vec.zCoord;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      return !this.entity.getNavigator().noPath();
   }

   public void startExecuting() {
      this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1.2D);
   }

   public void updateTask() {
      if(this.entity.getAttackTarget() != null) {
         this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30.0F, 30.0F);
      }

   }
}
