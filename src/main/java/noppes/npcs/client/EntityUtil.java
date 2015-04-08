package noppes.npcs.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityUtil {

   public static void Copy(EntityLivingBase copied, EntityLivingBase entity) {
      entity.worldObj = copied.worldObj;
      entity.deathTime = copied.deathTime;
      entity.distanceWalkedModified = copied.distanceWalkedModified;
      entity.prevDistanceWalkedModified = copied.distanceWalkedModified;
      entity.onGround = copied.onGround;
      entity.distanceWalkedOnStepModified = copied.distanceWalkedOnStepModified;
      entity.moveForward = copied.moveForward;
      entity.moveStrafing = copied.moveStrafing;
      entity.setPosition(copied.posX, copied.posY, copied.posZ);
      entity.boundingBox.setBB(copied.boundingBox);
      entity.prevPosX = copied.prevPosX;
      entity.prevPosY = copied.prevPosY;
      entity.prevPosZ = copied.prevPosZ;
      entity.motionX = copied.motionX;
      entity.motionY = copied.motionY;
      entity.motionZ = copied.motionZ;
      entity.rotationYaw = copied.rotationYaw;
      entity.rotationPitch = copied.rotationPitch;
      entity.prevRotationYaw = copied.prevRotationYaw;
      entity.prevRotationPitch = copied.prevRotationPitch;
      entity.rotationYawHead = copied.rotationYawHead;
      entity.prevRotationYawHead = copied.prevRotationYawHead;
      entity.prevRenderYawOffset = copied.prevRenderYawOffset;
      entity.cameraPitch = copied.cameraPitch;
      entity.prevCameraPitch = copied.prevCameraPitch;
      entity.renderYawOffset = copied.renderYawOffset;
      entity.lastTickPosX = copied.lastTickPosX;
      entity.lastTickPosY = copied.lastTickPosY;
      entity.lastTickPosZ = copied.lastTickPosZ;
      entity.limbSwingAmount = copied.limbSwingAmount;
      entity.prevLimbSwingAmount = copied.prevLimbSwingAmount;
      entity.limbSwing = copied.limbSwing;
      entity.swingProgress = copied.swingProgress;
      entity.prevSwingProgress = copied.prevSwingProgress;
      entity.isSwingInProgress = copied.isSwingInProgress;
      entity.swingProgressInt = copied.swingProgressInt;
      entity.ticksExisted = copied.ticksExisted;
      if(entity instanceof EntityPlayer && copied instanceof EntityPlayer) {
         EntityPlayer npc = (EntityPlayer)entity;
         EntityPlayer target = (EntityPlayer)copied;
         npc.cameraYaw = target.cameraYaw;
         npc.prevCameraYaw = target.prevCameraYaw;
         npc.field_71091_bM = target.field_71091_bM;
         npc.field_71096_bN = target.field_71096_bN;
         npc.field_71097_bO = target.field_71097_bO;
         npc.field_71094_bP = target.field_71094_bP;
         npc.field_71095_bQ = target.field_71095_bQ;
         npc.field_71085_bR = target.field_71085_bR;
      }

      if(entity instanceof EntityDragon) {
         entity.rotationYaw += 180.0F;
      }

      if(entity instanceof EntityChicken) {
         ((EntityChicken)entity).destPos = copied.onGround?0.0F:1.0F;
      }

      for(int var4 = 0; var4 < 5; ++var4) {
         entity.setCurrentItemOrArmor(var4, copied.getEquipmentInSlot(var4));
      }

      if(copied instanceof EntityNPCInterface && entity instanceof EntityNPCInterface) {
         EntityNPCInterface var5 = (EntityNPCInterface)copied;
         EntityNPCInterface var6 = (EntityNPCInterface)entity;
         var6.textureLocation = var5.textureLocation;
         var6.textureGlowLocation = var5.textureGlowLocation;
         var6.textureCloakLocation = var5.textureCloakLocation;
         var6.display = var5.display;
         var6.inventory = var5.inventory;
         var6.currentAnimation = var5.currentAnimation;
         var6.setDataWatcher(var5.getDataWatcher());
      }

   }
}
