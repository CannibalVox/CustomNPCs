package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobGuard;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobGuard extends ScriptJobInterface {

   private JobGuard job;


   public ScriptJobGuard(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobGuard)npc.jobInterface;
   }

   public int getType() {
      return 3;
   }
}
