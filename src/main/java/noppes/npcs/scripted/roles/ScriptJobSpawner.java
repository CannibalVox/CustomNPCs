package noppes.npcs.scripted.roles;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobSpawner;
import noppes.npcs.scripted.ScriptLivingBase;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobSpawner extends ScriptJobInterface {

   private JobSpawner job;


   public ScriptJobSpawner(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobSpawner)npc.jobInterface;
   }

   public int getType() {
      return 6;
   }

   public ScriptLivingBase spawnEntity(int number) {
      EntityLivingBase base = this.job.spawnEntity(number);
      return base == null?null:(ScriptLivingBase)ScriptController.Instance.getScriptForEntity(base);
   }

   public void removeAllSpawned() {
      EntityLivingBase entity;
      for(Iterator var1 = this.job.spawned.iterator(); var1.hasNext(); entity.isDead = true) {
         entity = (EntityLivingBase)var1.next();
      }

      this.job.spawned = new ArrayList();
   }
}
