package noppes.npcs.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class ModelNPCMale extends ModelBiped {

   public boolean isDancing;
   public boolean isSleeping;
   public float animationTick;
   public float dancingTicks;


   public ModelNPCMale(float f) {
      this.init(f, 0.0F);
   }

   public void setLivingAnimations(EntityLivingBase par1EntityLiving, float f6, float f5, float par9) {
      this.animationTick += par9;
      this.dancingTicks = (float)CustomNpcs.ticks / 3.978873F;
   }

   public void init(float f, float f1) {
      super.heldItemLeft = 0;
      super.heldItemRight = 0;
      super.isSneak = false;
      super.aimedBow = false;
      super.bipedCloak = new ModelRenderer(this, 0, 0);
      super.bipedCloak.textureHeight = 32.0F;
      super.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, f);
      super.bipedEars = new ModelRenderer(this, 24, 0);
      super.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, f);
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      super.bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f + 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedBody = new ModelRenderer(this, 16, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedRightArm = new ModelRenderer(this, 40, 16);
      super.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightArm.setRotationPoint(-5.0F, 2.0F + f1, 0.0F);
      super.bipedLeftArm = new ModelRenderer(this, 40, 16);
      super.bipedLeftArm.mirror = true;
      super.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f1, 0.0F);
      super.bipedRightLeg = new ModelRenderer(this, 0, 16);
      super.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + f1, 0.0F);
      super.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + f1, 0.0F);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      if(!this.isDancing) {
         super.bipedHead.render(par7);
         super.bipedBody.render(par7);
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
         super.bipedHeadwear.render(par7);
      } else {
         this.renderHead(par1Entity, par7);
         this.renderArms(par1Entity, par7);
         this.renderBody(par1Entity, par7);
         this.renderLegs(par1Entity, par7);
      }

   }

   public void renderHead(Entity entityliving, float par7) {
      if(this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.075F, (float)Math.abs(Math.cos((double)this.dancingTicks)) * 0.125F - 0.02F, (float)(-Math.abs(Math.cos((double)this.dancingTicks))) * 0.075F);
         super.bipedHead.render(par7);
         super.bipedHeadwear.render(par7);
         GL11.glPopMatrix();
      } else {
         super.bipedHead.render(par7);
         super.bipedHeadwear.render(par7);
      }

   }

   public void renderLeftArm(Entity entityliving, float par7) {
      if(this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.025F, (float)Math.abs(Math.cos((double)this.dancingTicks)) * 0.125F - 0.02F, 0.0F);
         super.bipedLeftArm.render(par7);
         GL11.glPopMatrix();
      } else {
         super.bipedLeftArm.render(par7);
      }

   }

   public void renderArms(Entity entity, float par7) {
      this.renderLeftArm(entity, par7);
      this.renderRightArm(entity, par7);
   }

   public void renderRightArm(Entity entityliving, float par7) {
      if(this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.025F, (float)Math.abs(Math.cos((double)this.dancingTicks)) * 0.125F - 0.02F, 0.0F);
         super.bipedRightArm.render(par7);
         GL11.glPopMatrix();
      } else {
         super.bipedRightArm.render(par7);
      }

   }

   public void renderBody(Entity entityliving, float par7) {
      if(this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.015F, 0.0F, 0.0F);
         super.bipedBody.render(par7);
         GL11.glPopMatrix();
      } else {
         super.bipedBody.render(par7);
      }

   }

   public void renderLegs(Entity entityliving, float par7) {
      super.bipedRightLeg.render(par7);
      super.bipedLeftLeg.render(par7);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      super.isRiding = npc.isRiding();
      if(super.isSneak && (npc.currentAnimation == EnumAnimation.CRAWLING || npc.currentAnimation == EnumAnimation.LYING)) {
         super.isSneak = false;
      }

      super.bipedHead.rotateAngleY = par4 / 57.295776F;
      super.bipedHead.rotateAngleX = par5 / 57.295776F;
      super.bipedHeadwear.rotateAngleY = super.bipedHead.rotateAngleY;
      super.bipedHeadwear.rotateAngleX = super.bipedHead.rotateAngleX;
      super.bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 2.0F * par2 * 0.5F;
      super.bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = 0.0F;
      super.bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
      super.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
      super.bipedRightLeg.rotateAngleY = 0.0F;
      super.bipedLeftLeg.rotateAngleY = 0.0F;
      if(super.isRiding) {
         super.bipedRightArm.rotateAngleX += -0.62831855F;
         super.bipedLeftArm.rotateAngleX += -0.62831855F;
         super.bipedRightLeg.rotateAngleX = -1.2566371F;
         super.bipedLeftLeg.rotateAngleX = -1.2566371F;
         super.bipedRightLeg.rotateAngleY = 0.31415927F;
         super.bipedLeftLeg.rotateAngleY = -0.31415927F;
      }

      if(super.heldItemLeft != 0) {
         super.bipedLeftArm.rotateAngleX = super.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F * (float)super.heldItemLeft;
      }

      if(super.heldItemRight != 0) {
         super.bipedRightArm.rotateAngleX = super.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * (float)super.heldItemRight;
      }

      super.bipedRightArm.rotateAngleY = 0.0F;
      super.bipedLeftArm.rotateAngleY = 0.0F;
      float f1;
      float f3;
      if(super.swingProgress > -9990.0F) {
         f1 = super.swingProgress;
         super.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F * 2.0F) * 0.2F;
         super.bipedRightArm.rotationPointZ = MathHelper.sin(super.bipedBody.rotateAngleY) * 5.0F;
         super.bipedRightArm.rotationPointX = -MathHelper.cos(super.bipedBody.rotateAngleY) * 5.0F;
         super.bipedLeftArm.rotationPointZ = -MathHelper.sin(super.bipedBody.rotateAngleY) * 5.0F;
         super.bipedLeftArm.rotationPointX = MathHelper.cos(super.bipedBody.rotateAngleY) * 5.0F;
         super.bipedRightArm.rotateAngleY += super.bipedBody.rotateAngleY;
         super.bipedLeftArm.rotateAngleY += super.bipedBody.rotateAngleY;
         super.bipedLeftArm.rotateAngleX += super.bipedBody.rotateAngleY;
         f1 = 1.0F - super.swingProgress;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         f3 = MathHelper.sin(f1 * 3.1415927F);
         float f4 = MathHelper.sin(super.swingProgress * 3.1415927F) * -(super.bipedHead.rotateAngleX - 0.7F) * 0.75F;
         super.bipedRightArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f3 * 1.2D + (double)f4));
         super.bipedRightArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
         super.bipedRightArm.rotateAngleZ = MathHelper.sin(super.swingProgress * 3.1415927F) * -0.4F;
      }

      if(super.isSneak) {
         super.bipedBody.rotateAngleX = 0.5F;
         super.bipedRightLeg.rotateAngleX -= 0.0F;
         super.bipedLeftLeg.rotateAngleX -= 0.0F;
         super.bipedRightArm.rotateAngleX += 0.4F;
         super.bipedLeftArm.rotateAngleX += 0.4F;
         super.bipedRightLeg.rotationPointZ = 4.0F;
         super.bipedLeftLeg.rotationPointZ = 4.0F;
         super.bipedRightLeg.rotationPointY = 9.0F;
         super.bipedLeftLeg.rotationPointY = 9.0F;
         super.bipedHead.rotationPointY = 1.0F;
      } else {
         super.bipedBody.rotateAngleX = 0.0F;
         super.bipedRightLeg.rotationPointZ = 0.0F;
         super.bipedLeftLeg.rotationPointZ = 0.0F;
         super.bipedRightLeg.rotationPointY = 12.0F;
         super.bipedLeftLeg.rotationPointY = 12.0F;
         super.bipedHead.rotationPointY = 0.0F;
      }

      super.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
      if(super.aimedBow) {
         f1 = 0.0F;
         f3 = 0.0F;
         super.bipedRightArm.rotateAngleZ = 0.0F;
         super.bipedLeftArm.rotateAngleZ = 0.0F;
         super.bipedRightArm.rotateAngleY = -(0.1F - f1 * 0.6F) + super.bipedHead.rotateAngleY;
         super.bipedLeftArm.rotateAngleY = 0.1F - f1 * 0.6F + super.bipedHead.rotateAngleY + 0.4F;
         super.bipedRightArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
         super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
         super.bipedRightArm.rotateAngleX -= f1 * 1.2F - f3 * 0.4F;
         super.bipedLeftArm.rotateAngleX -= f1 * 1.2F - f3 * 0.4F;
         super.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
         super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
         super.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
         super.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
      }

   }

   public void renderEars(float f) {
      super.bipedEars.rotateAngleY = super.bipedHead.rotateAngleY;
      super.bipedEars.rotateAngleX = super.bipedHead.rotateAngleX;
      super.bipedEars.rotationPointX = 0.0F;
      super.bipedEars.rotationPointY = 0.0F;
      super.bipedEars.render(f);
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void renderCloak(float f) {
      super.bipedCloak.render(f);
   }

   public boolean isSleeping(Entity entity) {
      return entity instanceof EntityPlayer && ((EntityPlayer)entity).isPlayerSleeping()?true:((EntityNPCInterface)entity).currentAnimation == EnumAnimation.LYING;
   }
}
