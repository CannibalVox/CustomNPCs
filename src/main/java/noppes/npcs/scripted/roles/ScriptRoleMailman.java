package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.roles.ScriptRoleInterface;

public class ScriptRoleMailman extends ScriptRoleInterface {

   public ScriptRoleMailman(EntityNPCInterface npc) {
      super(npc);
   }

   public int getType() {
      return 1;
   }
}
