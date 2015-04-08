package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;

public class ScriptJobInterface {

   protected EntityNPCInterface npc;


   public ScriptJobInterface(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public int getType() {
      return 0;
   }
}
