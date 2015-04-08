package noppes.npcs.ai;

import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIMovingPath extends EntityAIBase {

   private EntityNPCInterface npc;
   private int[] pos;


   public EntityAIMovingPath(EntityNPCInterface par1EntityNPCInterface) {
      this.npc = par1EntityNPCInterface;
      this.setMutexBits(AiMutex.PASSIVE);
   }

   public boolean shouldExecute() {
      if(!this.npc.isAttacking() && !this.npc.isInteracting() && (this.npc.getRNG().nextInt(40) == 0 || !this.npc.ai.movingPause) && this.npc.getNavigator().noPath() && !this.npc.isInteracting()) {
         List list = this.npc.ai.getMovingPath();
         if(list.size() < 2) {
            return false;
         } else {
            this.npc.ai.incrementMovingPath();
            this.pos = this.npc.ai.getCurrentMovingPath();
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      if(!this.npc.isAttacking() && !this.npc.isInteracting()) {
         return !this.npc.getNavigator().noPath();
      } else {
         this.npc.ai.decreaseMovingPath();
         return false;
      }
   }

   public void startExecuting() {
      this.npc.getNavigator().tryMoveToXYZ((double)this.pos[0] + 0.5D, (double)this.pos[1], (double)this.pos[2] + 0.5D, 1.0D);
   }
}
