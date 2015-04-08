package noppes.npcs.scripted;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.scripted.ScriptEntity;
import noppes.npcs.scripted.ScriptLivingBase;

public class ScriptLiving extends ScriptLivingBase {

   private EntityLiving entity;


   public ScriptLiving(EntityLiving entity) {
      super(entity);
      this.entity = entity;
   }

   public boolean isAttacking() {
      return super.isAttacking() || this.entity.getAttackTarget() != null;
   }

   public void setAttackTarget(ScriptLivingBase living) {
      if(living == null) {
         this.entity.setAttackTarget((EntityLivingBase)null);
      } else {
         this.entity.setAttackTarget(living.entity);
      }

      super.setAttackTarget(living);
   }

   public ScriptLivingBase getAttackTarget() {
      ScriptLivingBase base = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity(this.entity.getAttackTarget());
      return base != null?base:super.getAttackTarget();
   }

   public void navigateTo(double x, double y, double z, double speed) {
      this.entity.getNavigator().tryMoveToXYZ(x, y, z, speed);
   }

   public void clearNavigation() {
      this.entity.getNavigator().clearPathEntity();
   }

   public boolean isNavigating() {
      return !this.entity.getNavigator().noPath();
   }

   public boolean canSeeEntity(ScriptEntity entity) {
      return this.entity.getEntitySenses().canSee(entity.entity);
   }
}
