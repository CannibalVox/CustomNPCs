package noppes.npcs.ai.target;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {

   EntityNPCInterface npc;
   EntityLivingBase theOwnerAttacker;
   private int field_142051_e;


   public EntityAIOwnerHurtByTarget(EntityNPCInterface npc) {
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
            this.theOwnerAttacker = entitylivingbase.getAITarget();
            int i = entitylivingbase.getRevengeTimer();
            return i != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false);
         }
      } else {
         return false;
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.theOwnerAttacker);
      EntityLivingBase entitylivingbase = this.npc.getOwner();
      if(entitylivingbase != null) {
         this.field_142051_e = entitylivingbase.getRevengeTimer();
      }

      super.startExecuting();
   }
}
