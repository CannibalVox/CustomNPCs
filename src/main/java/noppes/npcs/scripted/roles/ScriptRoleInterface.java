package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;

public class ScriptRoleInterface {

   protected EntityNPCInterface npc;


   public ScriptRoleInterface(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public int getType() {
      return 0;
   }
}
