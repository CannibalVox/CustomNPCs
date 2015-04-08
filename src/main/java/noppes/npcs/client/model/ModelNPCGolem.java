package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.ModelNPCMale;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityNPCInterface;

public class ModelNPCGolem extends ModelNPCMale {

   private ModelRenderer bipedLowerBody;


   public ModelNPCGolem(float f) {
      super(f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      short short1 = 128;
      short short2 = 128;
      float f2 = -7.0F;
      super.bipedHead = (new ModelRenderer(this)).setTextureSize(short1, short2);
      super.bipedHead.setRotationPoint(0.0F, f2, -2.0F);
      super.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, f);
      super.bipedHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, f);
      super.bipedHeadwear = (new ModelRenderer(this)).setTextureSize(short1, short2);
      super.bipedHeadwear.setRotationPoint(0.0F, f2, -2.0F);
      super.bipedHeadwear.setTextureOffset(0, 85).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, f + 0.5F);
      super.bipedBody = (new ModelRenderer(this)).setTextureSize(short1, short2);
      super.bipedBody.setRotationPoint(0.0F, 0.0F + f2, 0.0F);
      super.bipedBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, f + 0.2F);
      super.bipedBody.setTextureOffset(0, 21).addBox(-9.0F, -2.0F, -6.0F, 18, 8, 11, f);
      this.bipedLowerBody = (new ModelRenderer(this)).setTextureSize(short1, short2);
      this.bipedLowerBody.setRotationPoint(0.0F, 0.0F + f2, 0.0F);
      this.bipedLowerBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, f + 0.5F);
      this.bipedLowerBody.setTextureOffset(30, 70).addBox(-4.5F, 6.0F, -3.0F, 9, 9, 6, f + 0.4F);
      super.bipedRightArm = (new ModelRenderer(this)).setTextureSize(short1, short2);
      super.bipedRightArm.setRotationPoint(0.0F, f2, 0.0F);
      super.bipedRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, f + 0.2F);
      super.bipedRightArm.setTextureOffset(80, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 20, 6, f);
      super.bipedRightArm.setTextureOffset(100, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 20, 6, f + 1.0F);
      super.bipedLeftArm = (new ModelRenderer(this)).setTextureSize(short1, short2);
      super.bipedLeftArm.setRotationPoint(0.0F, f2, 0.0F);
      super.bipedLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, f + 0.2F);
      super.bipedLeftArm.setTextureOffset(80, 58).addBox(9.0F, -2.5F, -3.0F, 4, 20, 6, f);
      super.bipedLeftArm.setTextureOffset(100, 58).addBox(9.0F, -2.5F, -3.0F, 4, 20, 6, f + 1.0F);
      super.bipedLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(short1, short2);
      super.bipedLeftLeg.setRotationPoint(-4.0F, 18.0F + f2, 0.0F);
      super.bipedLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f);
      super.bipedRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(short1, short2);
      super.bipedRightLeg.mirror = true;
      super.bipedRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + f2, 0.0F);
      super.bipedRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
      this.bipedLowerBody.render(par7);
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
      super.bipedLeftLeg.rotateAngleX = -1.5F * this.func_78172_a(par1, 13.0F) * par2;
      super.bipedRightLeg.rotateAngleX = 1.5F * this.func_78172_a(par1, 13.0F) * par2;
      super.bipedLeftLeg.rotateAngleY = 0.0F;
      super.bipedRightLeg.rotateAngleY = 0.0F;
      float f6 = MathHelper.sin(super.swingProgress * 3.1415927F);
      float f7 = MathHelper.sin((16.0F - (1.0F - super.swingProgress) * (1.0F - super.swingProgress)) * 3.1415927F);
      if((double)super.swingProgress > 0.0D) {
         super.bipedRightArm.rotateAngleZ = 0.0F;
         super.bipedLeftArm.rotateAngleZ = 0.0F;
         super.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
         super.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
         super.bipedRightArm.rotateAngleX = 0.0F;
         super.bipedLeftArm.rotateAngleX = 0.0F;
         super.bipedRightArm.rotateAngleX = -1.5707964F;
         super.bipedLeftArm.rotateAngleX = -1.5707964F;
         super.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
         super.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      } else if(super.aimedBow) {
         float f1 = 0.0F;
         float f3 = 0.0F;
         super.bipedRightArm.rotateAngleZ = 0.0F;
         super.bipedRightArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
         super.bipedRightArm.rotateAngleX -= f1 * 1.2F - f3 * 0.4F;
         super.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
         super.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
         super.bipedLeftArm.rotateAngleX = (-0.2F - 1.5F * this.func_78172_a(par1, 13.0F)) * par2;
         super.bipedBody.rotateAngleY = -(0.1F - f1 * 0.6F) + super.bipedHead.rotateAngleY;
         super.bipedRightArm.rotateAngleY = -(0.1F - f1 * 0.6F) + super.bipedHead.rotateAngleY;
         super.bipedLeftArm.rotateAngleY = 0.1F - f1 * 0.6F + super.bipedHead.rotateAngleY;
      } else {
         super.bipedRightArm.rotateAngleX = (-0.2F + 1.5F * this.func_78172_a(par1, 13.0F)) * par2;
         super.bipedLeftArm.rotateAngleX = (-0.2F - 1.5F * this.func_78172_a(par1, 13.0F)) * par2;
         super.bipedBody.rotateAngleY = 0.0F;
         super.bipedRightArm.rotateAngleY = 0.0F;
         super.bipedLeftArm.rotateAngleY = 0.0F;
         super.bipedRightArm.rotateAngleZ = 0.0F;
         super.bipedLeftArm.rotateAngleZ = 0.0F;
      }

      if(super.isRiding) {
         super.bipedRightArm.rotateAngleX += -0.62831855F;
         super.bipedLeftArm.rotateAngleX += -0.62831855F;
         super.bipedLeftLeg.rotateAngleX = -1.2566371F;
         super.bipedRightLeg.rotateAngleX = -1.2566371F;
         super.bipedLeftLeg.rotateAngleY = 0.31415927F;
         super.bipedRightLeg.rotateAngleY = -0.31415927F;
      }

   }

   private float func_78172_a(float par1, float par2) {
      return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
   }
}
