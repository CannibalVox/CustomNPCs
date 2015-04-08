package noppes.npcs.scripted;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.scripted.ScriptEntity;
import noppes.npcs.scripted.ScriptItemStack;

public class ScriptLivingBase extends ScriptEntity {

   protected EntityLivingBase entity;


   public ScriptLivingBase(EntityLivingBase entity) {
      super(entity);
      this.entity = entity;
   }

   public float getHealth() {
      return this.entity.getHealth();
   }

   public void setHealth(float health) {
      this.entity.setHealth(health);
   }

   public float getMaxHealth() {
      return this.entity.getMaxHealth();
   }

   public boolean isAttacking() {
      return this.entity.getAITarget() != null;
   }

   public void setAttackTarget(ScriptLivingBase living) {
      if(living == null) {
         this.entity.setRevengeTarget((EntityLivingBase)null);
      } else {
         this.entity.setRevengeTarget(living.entity);
      }

   }

   public ScriptLivingBase getAttackTarget() {
      return (ScriptLivingBase)ScriptController.Instance.getScriptForEntity(this.entity.getAITarget());
   }

   public int getType() {
      return 5;
   }

   public boolean typeOf(int type) {
      return type == 5?true:super.typeOf(type);
   }

   public boolean canSeeEntity(ScriptEntity entity) {
      return this.entity.canEntityBeSeen(entity.entity);
   }

   public EntityLivingBase getMinecraftEntity() {
      return this.entity;
   }

   public void swingHand() {
      this.entity.swingItem();
   }

   public void addPotionEffect(int effect, int duration, int strength, boolean hideParticles) {
      if(effect >= 0 && effect < Potion.potionTypes.length && Potion.potionTypes[effect] != null) {
         if(strength < 0) {
            strength = 0;
         } else if(strength > 255) {
            strength = 255;
         }

         if(duration < 0) {
            duration = 0;
         } else if(duration > 1000000) {
            duration = 1000000;
         }

         if(!Potion.potionTypes[effect].isInstant()) {
            duration *= 20;
         }

         if(duration == 0) {
            this.entity.removePotionEffect(effect);
         } else {
            this.entity.addPotionEffect(new PotionEffect(effect, duration, strength));
         }

      }
   }

   public void clearPotionEffects() {
      this.entity.clearActivePotions();
   }

   public int getPotionEffect(int effect) {
      PotionEffect pf = this.entity.getActivePotionEffect(Potion.potionTypes[effect]);
      return pf == null?-1:pf.getAmplifier();
   }

   public ScriptItemStack getHeldItem() {
      ItemStack item = this.entity.getHeldItem();
      return item == null?null:new ScriptItemStack(item);
   }

   public void setHeldItem(ScriptItemStack item) {
      this.entity.setCurrentItemOrArmor(0, item == null?null:item.item);
   }

   public ScriptItemStack getArmor(int slot) {
      ItemStack item = this.entity.getEquipmentInSlot(slot + 1);
      return item == null?null:new ScriptItemStack(item);
   }

   public void setArmor(int slot, ScriptItemStack item) {
      this.entity.setCurrentItemOrArmor(slot + 1, item == null?null:item.item);
   }
}
