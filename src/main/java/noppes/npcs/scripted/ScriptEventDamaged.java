package noppes.npcs.scripted;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.scripted.ScriptEvent;
import noppes.npcs.scripted.ScriptLivingBase;

public class ScriptEventDamaged extends ScriptEvent {

   private float damage;
   private boolean clear = false;
   private ScriptLivingBase source;
   private DamageSource damagesource;


   public ScriptEventDamaged(float damage, EntityLivingBase attackingEntity, DamageSource damagesource) {
      this.damage = damage;
      this.damagesource = damagesource;
      this.source = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity(attackingEntity);
   }

   public ScriptLivingBase getSource() {
      return this.source;
   }

   public void setClearTarget(boolean bo) {
      this.clear = bo;
   }

   public boolean getClearTarget() {
      return this.clear;
   }

   public float getDamage() {
      return this.damage;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public String getType() {
      return this.damagesource.damageType;
   }
}
