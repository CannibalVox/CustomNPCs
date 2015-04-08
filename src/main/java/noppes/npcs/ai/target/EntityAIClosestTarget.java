package noppes.npcs.ai.target;

import java.util.Collections;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.MathHelper;

public class EntityAIClosestTarget extends EntityAITarget {

   private final Class targetClass;
   private final int targetChance;
   private final Sorter theNearestAttackableTargetSorter;
   private final IEntitySelector field_82643_g;
   private EntityLivingBase targetEntity;


   public EntityAIClosestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4) {
      this(par1EntityCreature, par2Class, par3, par4, false);
   }

   public EntityAIClosestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5) {
      this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
   }

   public EntityAIClosestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, IEntitySelector par6IEntitySelector) {
      super(par1EntityCreature, par4, par5);
      this.targetClass = par2Class;
      this.targetChance = par3;
      this.theNearestAttackableTargetSorter = new Sorter(par1EntityCreature);
      this.setMutexBits(1);
      this.field_82643_g = par6IEntitySelector;
   }

   public boolean shouldExecute() {
      if(this.targetChance > 0 && super.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         double d0 = this.getTargetDistance();
         List list = super.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, super.taskOwner.boundingBox.expand(d0, (double)MathHelper.ceiling_double_int(d0 / 2.0D), d0), this.field_82643_g);
         Collections.sort(list, this.theNearestAttackableTargetSorter);
         if(list.isEmpty()) {
            return false;
         } else {
            this.targetEntity = (EntityLivingBase)list.get(0);
            return true;
         }
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.targetEntity);
      if(this.targetEntity instanceof EntityMob && ((EntityMob)this.targetEntity).getAttackTarget() == null) {
         ((EntityMob)this.targetEntity).setAttackTarget(super.taskOwner);
         ((EntityMob)this.targetEntity).setTarget(super.taskOwner);
      }

      super.startExecuting();
   }
}
