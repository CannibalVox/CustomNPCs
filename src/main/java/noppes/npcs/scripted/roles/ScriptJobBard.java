package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobBard;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobBard extends ScriptJobInterface {

   private JobBard job;


   public ScriptJobBard(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobBard)npc.jobInterface;
   }

   public int getType() {
      return 1;
   }

   public String getSong() {
      return this.job.song;
   }

   public void setSong(String song) {
      this.job.song = song;
      super.npc.script.clientNeedsUpdate = true;
   }
}
