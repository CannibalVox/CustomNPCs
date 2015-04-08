package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIWorldLines extends EntityAIBase {

   private EntityNPCInterface npc;


   public EntityAIWorldLines(EntityNPCInterface npc) {
      this.npc = npc;
      this.setMutexBits(AiMutex.PASSIVE);
   }

   public boolean shouldExecute() {
      return !this.npc.isAttacking() && !this.npc.isKilled() && this.npc.advanced.hasWorldLines() && this.npc.getRNG().nextInt(1900) == 1;
   }

   public void startExecuting() {
      this.npc.saySurrounding(this.npc.advanced.getWorldLine());
   }
}
