package noppes.npcs.scripted;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.scripted.ScriptEvent;
import noppes.npcs.scripted.ScriptLivingBase;

public class ScriptEventKilled extends ScriptEvent {

   private ScriptLivingBase source;
   private DamageSource damagesource;


   public ScriptEventKilled(EntityLivingBase target, DamageSource damagesource) {
      this.damagesource = damagesource;
      this.source = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity(target);
   }

   public ScriptLivingBase getSource() {
      return this.source;
   }

   public String getType() {
      return this.damagesource.damageType;
   }
}
