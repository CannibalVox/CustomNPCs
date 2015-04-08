package noppes.npcs.ai.target;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIOwnerHurtTarget extends EntityAITarget {

   EntityNPCInterface npc;
   EntityLivingBase theTarget;
   private int field_142050_e;


   public EntityAIOwnerHurtTarget(EntityNPCInterface npc) {
      super(npc, false);
      this.npc = npc;
      this.setMutexBits(AiMutex.PASSIVE);
   }

   public boolean shouldExecute() {
      if(this.npc.isFollower() && this.npc.roleInterface != null && this.npc.roleInterface.defendOwner()) {
         EntityLivingBase entitylivingbase = this.npc.getOwner();
         if(entitylivingbase == null) {
            return false;
         } else {
            this.theTarget = entitylivingbase.getLastAttacker();
            int i = entitylivingbase.getLastAttackerTime();
            return i != this.field_142050_e && this.isSuitableTarget(this.theTarget, false);
         }
      } else {
         return false;
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.theTarget);
      EntityLivingBase entitylivingbase = this.npc.getOwner();
      if(entitylivingbase != null) {
         this.field_142050_e = entitylivingbase.getLastAttackerTime();
      }

      super.startExecuting();
   }
}
