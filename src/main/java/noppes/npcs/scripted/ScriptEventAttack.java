package noppes.npcs.scripted;

import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.scripted.ScriptEvent;
import noppes.npcs.scripted.ScriptLivingBase;

public class ScriptEventAttack extends ScriptEvent {

   private float damage;
   private ScriptLivingBase target;
   private boolean isRanged;


   public ScriptEventAttack(float damage, EntityLivingBase target, boolean isRanged) {
      this.damage = damage;
      this.isRanged = isRanged;
      this.target = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity(target);
   }

   public ScriptLivingBase getTarget() {
      return this.target;
   }

   public float getDamage() {
      return this.damage;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public boolean isRange() {
      return this.isRanged;
   }
}
