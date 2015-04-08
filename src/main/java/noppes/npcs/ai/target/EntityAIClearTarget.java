package noppes.npcs.ai.target;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIClearTarget extends EntityAITarget {

   private EntityNPCInterface npc;
   private EntityLivingBase target;


   public EntityAIClearTarget(EntityNPCInterface npc) {
      super(npc, false);
      this.npc = npc;
   }

   public boolean shouldExecute() {
      this.target = super.taskOwner.getAttackTarget();
      if(this.target == null) {
         return false;
      } else if(this.target instanceof EntityPlayer && ((EntityPlayer)this.target).capabilities.disableDamage) {
         return true;
      } else {
         int distance = this.npc.stats.aggroRange * 2 * this.npc.stats.aggroRange;
         return this.npc.getOwner() != null && this.npc.getDistanceSqToEntity(this.npc.getOwner()) > (double)distance?true:this.npc.getDistanceSqToEntity(this.target) > (double)distance;
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget((EntityLivingBase)null);
      if(this.target == super.taskOwner.getAITarget()) {
         super.taskOwner.setRevengeTarget((EntityLivingBase)null);
      }

      super.startExecuting();
   }
}
