package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIJob extends EntityAIBase {

   private EntityNPCInterface npc;


   public EntityAIJob(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean shouldExecute() {
      return !this.npc.isKilled() && this.npc.jobInterface != null?this.npc.jobInterface.aiShouldExecute():false;
   }

   public void startExecuting() {
      this.npc.jobInterface.aiStartExecuting();
   }

   public boolean continueExecuting() {
      return !this.npc.isKilled() && this.npc.jobInterface != null?this.npc.jobInterface.aiContinueExecute():false;
   }

   public void updateTask() {
      this.npc.jobInterface.aiUpdateTask();
   }

   public void resetTask() {
      if(this.npc.jobInterface != null) {
         this.npc.jobInterface.resetTask();
      }

   }
}
