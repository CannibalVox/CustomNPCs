package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAnimation extends EntityAIBase {

   private EntityNPCInterface npc;
   private boolean isAttacking = false;
   private boolean isDead = false;
   private boolean isAtStartpoint = false;
   private boolean hasPath = false;
   private int tick = 4;


   public EntityAIAnimation(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean shouldExecute() {
      this.isDead = !this.npc.isEntityAlive();
      if(this.isDead) {
         return this.npc.currentAnimation != EnumAnimation.LYING;
      } else if(this.npc.stats.aimWhileShooting && this.npc.isAttacking()) {
         return this.npc.currentAnimation != EnumAnimation.AIMING;
      } else if(this.npc.ai.animationType == EnumAnimation.NONE) {
         return this.npc.currentAnimation != EnumAnimation.NONE;
      } else {
         this.isAttacking = this.npc.isAttacking();
         if(this.npc.ai.returnToStart) {
            this.isAtStartpoint = this.npc.isVeryNearAssignedPlace();
         }

         this.hasPath = !this.npc.getNavigator().noPath();
         return this.npc.ai.movingType == EnumMovingType.Standing && this.hasNavigation() && this.npc.currentAnimation.getWalkingAnimation() == 0?this.npc.currentAnimation != EnumAnimation.NONE:this.npc.currentAnimation != this.npc.ai.animationType;
      }
   }

   public void updateTask() {
      if(this.npc.stats.aimWhileShooting && this.npc.isAttacking()) {
         this.setAnimation(EnumAnimation.AIMING);
      } else {
         EnumAnimation type = this.npc.ai.animationType;
         if(this.isDead) {
            type = EnumAnimation.LYING;
         } else if(this.npc.ai.movingType == EnumMovingType.Standing && this.npc.ai.animationType.getWalkingAnimation() == 0 && this.hasNavigation()) {
            type = EnumAnimation.NONE;
         }

         this.setAnimation(type);
      }
   }

   private void setAnimation(EnumAnimation animation) {
      this.npc.setCurrentAnimation(animation);
      this.npc.updateHitbox();
      this.npc.setPosition(this.npc.posX, this.npc.posY, this.npc.posZ);
   }

   private boolean hasNavigation() {
      return this.isAttacking || this.npc.ai.returnToStart && !this.isAtStartpoint && !this.npc.isFollower() || this.hasPath;
   }
}
