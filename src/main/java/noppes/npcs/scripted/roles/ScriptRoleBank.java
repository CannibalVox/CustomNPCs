package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.roles.ScriptRoleInterface;

public class ScriptRoleBank extends ScriptRoleInterface {

   public ScriptRoleBank(EntityNPCInterface npc) {
      super(npc);
   }

   public int getType() {
      return 4;
   }
}
