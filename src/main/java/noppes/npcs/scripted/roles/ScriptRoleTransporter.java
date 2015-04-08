package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.roles.ScriptRoleInterface;

public class ScriptRoleTransporter extends ScriptRoleInterface {

   public ScriptRoleTransporter(EntityNPCInterface npc) {
      super(npc);
   }

   public int getType() {
      return 5;
   }
}
