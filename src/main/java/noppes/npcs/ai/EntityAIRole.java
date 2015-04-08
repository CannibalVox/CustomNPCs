package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIRole extends EntityAIBase {

   private EntityNPCInterface npc;


   public EntityAIRole(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean shouldExecute() {
      return !this.npc.isKilled() && this.npc.roleInterface != null?this.npc.roleInterface.aiShouldExecute():false;
   }

   public void startExecuting() {
      this.npc.roleInterface.aiStartExecuting();
   }

   public boolean continueExecuting() {
      return !this.npc.isKilled() && this.npc.roleInterface != null?this.npc.roleInterface.aiContinueExecute():false;
   }

   public void updateTask() {
      if(this.npc.roleInterface != null) {
         this.npc.roleInterface.aiUpdateTask();
      }

   }
}
