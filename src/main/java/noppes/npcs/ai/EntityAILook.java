package noppes.npcs.ai;

import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.constants.EnumStandingType;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAILook extends EntityAIBase {

   private final EntityNPCInterface npc;
   private int idle = 0;
   private double lookX;
   private double lookZ;
   boolean rotatebody;


   public EntityAILook(EntityNPCInterface npc) {
      this.npc = npc;
      this.setMutexBits(AiMutex.LOOK);
   }

   public boolean shouldExecute() {
      return !this.npc.isAttacking() && this.npc.getNavigator().noPath() && !this.npc.isPlayerSleeping() && this.npc.isEntityAlive();
   }

   public void startExecuting() {
      this.rotatebody = this.npc.ai.standingType == EnumStandingType.RotateBody || this.npc.ai.standingType == EnumStandingType.HeadRotation;
   }

   public void resetTask() {
      this.rotatebody = false;
   }

   public void updateTask() {
      if(this.npc.ai.standingType == EnumStandingType.Stalking) {
         EntityPlayer var1 = this.npc.worldObj.getClosestPlayerToEntity(this.npc, 16.0D);
         if(var1 == null) {
            this.rotatebody = true;
         } else {
            this.npc.getLookHelper().setLookPositionWithEntity(var1, 10.0F, (float)this.npc.getVerticalFaceSpeed());
         }
      }

      if(this.npc.isInteracting()) {
         Iterator var11 = this.npc.interactingEntities.iterator();
         EntityLivingBase closest = null;
         double closestDistance = 12.0D;

         while(var11.hasNext()) {
            EntityLivingBase entity = (EntityLivingBase)var11.next();
            double distance = entity.getDistanceSqToEntity(this.npc);
            if(distance < closestDistance) {
               closestDistance = entity.getDistanceSqToEntity(this.npc);
               closest = entity;
            } else if(distance > 12.0D) {
               var11.remove();
            }
         }

         if(closest != null) {
            this.npc.getLookHelper().setLookPositionWithEntity(closest, 10.0F, (float)this.npc.getVerticalFaceSpeed());
            return;
         }
      }

      if(this.rotatebody) {
         if(this.idle == 0 && this.npc.getRNG().nextFloat() < 0.02F) {
            double var12 = 6.283185307179586D * this.npc.getRNG().nextDouble();
            if(this.npc.ai.standingType == EnumStandingType.HeadRotation) {
               var12 = 0.017453292519943295D * (double)this.npc.ai.orientation + 0.6283185307179586D + 1.8849555921538759D * this.npc.getRNG().nextDouble();
            }

            this.lookX = Math.cos(var12);
            this.lookZ = Math.sin(var12);
            this.idle = 20 + this.npc.getRNG().nextInt(20);
         }

         if(this.idle > 0) {
            --this.idle;
            this.npc.getLookHelper().setLookPosition(this.npc.posX + this.lookX, this.npc.posY + (double)this.npc.getEyeHeight(), this.npc.posZ + this.lookZ, 10.0F, (float)this.npc.getVerticalFaceSpeed());
         }
      }

      if(this.npc.ai.standingType == EnumStandingType.NoRotation) {
         this.npc.rotationYawHead = this.npc.rotationYaw = this.npc.renderYawOffset = (float)this.npc.ai.orientation;
      }

   }
}
