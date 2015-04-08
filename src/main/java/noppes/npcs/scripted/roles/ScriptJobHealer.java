package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.scripted.ScriptLivingBase;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobHealer extends ScriptJobInterface {

   private JobHealer job;


   public ScriptJobHealer(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobHealer)npc.jobInterface;
   }

   public void heal(ScriptLivingBase entity, float amount) {
      this.job.heal(entity.getMinecraftEntity(), amount);
   }

   public int getType() {
      return 2;
   }
}
