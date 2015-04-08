package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobItemGiver;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobItemGiver extends ScriptJobInterface {

   private JobItemGiver job;


   public ScriptJobItemGiver(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobItemGiver)npc.jobInterface;
   }

   public int getType() {
      return 5;
   }
}
