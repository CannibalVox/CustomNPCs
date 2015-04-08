package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobConversation;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobConversation extends ScriptJobInterface {

   private JobConversation job;


   public ScriptJobConversation(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobConversation)npc.jobInterface;
   }

   public int getType() {
      return 6;
   }
}
