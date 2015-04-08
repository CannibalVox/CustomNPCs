package noppes.npcs.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.init.Blocks;

public class EntityAIBustDoor extends EntityAIDoorInteract {

   private int breakingTime;
   private int field_75358_j = -1;


   public EntityAIBustDoor(EntityLiving par1EntityLiving) {
      super(par1EntityLiving);
   }

   public boolean shouldExecute() {
      return !super.shouldExecute()?false:(!super.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")?false:!super.doorBlock.func_150015_f(super.theEntity.worldObj, super.entityPosX, super.entityPosY, super.entityPosZ));
   }

   public void startExecuting() {
      super.startExecuting();
      this.breakingTime = 0;
   }

   public boolean continueExecuting() {
      double var1 = super.theEntity.getDistanceSq((double)super.entityPosX, (double)super.entityPosY, (double)super.entityPosZ);
      return this.breakingTime <= 240 && !super.doorBlock.func_150015_f(super.theEntity.worldObj, super.entityPosX, super.entityPosY, super.entityPosZ) && var1 < 4.0D;
   }

   public void resetTask() {
      super.resetTask();
      super.theEntity.worldObj.destroyBlockInWorldPartially(super.theEntity.getEntityId(), super.entityPosX, super.entityPosY, super.entityPosZ, -1);
   }

   public void updateTask() {
      super.updateTask();
      if(super.theEntity.getRNG().nextInt(20) == 0) {
         super.theEntity.worldObj.playAuxSFX(1010, super.entityPosX, super.entityPosY, super.entityPosZ, 0);
         super.theEntity.swingItem();
      }

      ++this.breakingTime;
      int var1 = (int)((float)this.breakingTime / 240.0F * 10.0F);
      if(var1 != this.field_75358_j) {
         super.theEntity.worldObj.destroyBlockInWorldPartially(super.theEntity.getEntityId(), super.entityPosX, super.entityPosY, super.entityPosZ, var1);
         this.field_75358_j = var1;
      }

      if(this.breakingTime == 240) {
         super.theEntity.worldObj.setBlock(super.entityPosX, super.entityPosY, super.entityPosZ, Blocks.air);
         super.theEntity.worldObj.playAuxSFX(1012, super.entityPosX, super.entityPosY, super.entityPosZ, 0);
         super.theEntity.worldObj.playAuxSFX(2001, super.entityPosX, super.entityPosY, super.entityPosZ, Block.getIdFromBlock(super.doorBlock));
      }

   }
}
