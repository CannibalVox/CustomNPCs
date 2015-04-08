package noppes.npcs.scripted;

import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.scripted.ScriptEvent;
import noppes.npcs.scripted.ScriptLivingBase;

public class ScriptEventTarget extends ScriptEvent {

   private ScriptLivingBase target;


   public ScriptEventTarget(EntityLivingBase target) {
      if(target != null) {
         this.target = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity(target);
      }

   }

   public ScriptLivingBase getTarget() {
      return this.target;
   }

   public void setTarget(ScriptLivingBase target) {
      this.target = target;
   }
}
