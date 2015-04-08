package noppes.npcs.ai.selector;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import noppes.npcs.entity.EntityNPCInterface;

public class NPCInteractSelector implements IEntitySelector {

   private EntityNPCInterface npc;


   public NPCInteractSelector(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean isEntityApplicable(Entity entity) {
      if(entity != this.npc && entity instanceof EntityNPCInterface && this.npc.isEntityAlive()) {
         EntityNPCInterface selected = (EntityNPCInterface)entity;
         return !selected.isAttacking() && !this.npc.getFaction().isAggressiveToNpc(selected) && this.npc.ai.stopAndInteract;
      } else {
         return false;
      }
   }
}
