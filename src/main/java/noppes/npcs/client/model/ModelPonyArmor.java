package noppes.npcs.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityNPCInterface;

public class ModelPonyArmor extends ModelBase {

   private boolean rainboom;
   public ModelRenderer head;
   public ModelRenderer Body;
   public ModelRenderer BodyBack;
   public ModelRenderer rightarm;
   public ModelRenderer LeftArm;
   public ModelRenderer RightLeg;
   public ModelRenderer LeftLeg;
   public ModelRenderer rightarm2;
   public ModelRenderer LeftArm2;
   public ModelRenderer RightLeg2;
   public ModelRenderer LeftLeg2;
   public boolean isPegasus = false;
   public boolean isUnicorn = false;
   public boolean isSleeping = false;
   public boolean isFlying = false;
   public boolean isGlow = false;
   public boolean isSneak = false;
   public boolean aimedBow;
   public int heldItemRight;


   public ModelPonyArmor(float f) {
      this.init(f, 0.0F);
   }

   public void init(float strech, float f) {
      float f2 = 0.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 8, strech);
      this.head.setRotationPoint(f2, f3, f4);
      float f5 = 0.0F;
      float f6 = 0.0F;
      float f7 = 0.0F;
      this.Body = new ModelRenderer(this, 16, 16);
      this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, strech);
      this.Body.setRotationPoint(f5, f6 + f, f7);
      this.BodyBack = new ModelRenderer(this, 0, 0);
      this.BodyBack.addBox(-4.0F, 4.0F, 6.0F, 8, 8, 8, strech);
      this.BodyBack.setRotationPoint(f5, f6 + f, f7);
      this.rightarm = new ModelRenderer(this, 0, 16);
      this.rightarm.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.rightarm.setRotationPoint(-3.0F, 8.0F + f, 0.0F);
      this.LeftArm = new ModelRenderer(this, 0, 16);
      this.LeftArm.mirror = true;
      this.LeftArm.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.LeftArm.setRotationPoint(3.0F, 8.0F + f, 0.0F);
      this.RightLeg = new ModelRenderer(this, 0, 16);
      this.RightLeg.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.RightLeg.setRotationPoint(-3.0F, 0.0F + f, 0.0F);
      this.LeftLeg = new ModelRenderer(this, 0, 16);
      this.LeftLeg.mirror = true;
      this.LeftLeg.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.LeftLeg.setRotationPoint(3.0F, 0.0F + f, 0.0F);
      this.rightarm2 = new ModelRenderer(this, 0, 16);
      this.rightarm2.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech * 0.5F);
      this.rightarm2.setRotationPoint(-3.0F, 8.0F + f, 0.0F);
      this.LeftArm2 = new ModelRenderer(this, 0, 16);
      this.LeftArm2.mirror = true;
      this.LeftArm2.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech * 0.5F);
      this.LeftArm2.setRotationPoint(3.0F, 8.0F + f, 0.0F);
      this.RightLeg2 = new ModelRenderer(this, 0, 16);
      this.RightLeg2.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech * 0.5F);
      this.RightLeg2.setRotationPoint(-3.0F, 0.0F + f, 0.0F);
      this.LeftLeg2 = new ModelRenderer(this, 0, 16);
      this.LeftLeg2.mirror = true;
      this.LeftLeg2.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech * 0.5F);
      this.LeftLeg2.setRotationPoint(3.0F, 0.0F + f, 0.0F);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      if(!super.isRiding) {
         super.isRiding = npc.currentAnimation == EnumAnimation.SITTING;
      }

      if(this.isSneak && (npc.currentAnimation == EnumAnimation.CRAWLING || npc.currentAnimation == EnumAnimation.LYING)) {
         this.isSneak = false;
      }

      this.rainboom = false;
      float f6;
      float f7;
      if(this.isSleeping) {
         f6 = 1.4F;
         f7 = 0.1F;
      } else {
         f6 = f3 / 57.29578F;
         f7 = f4 / 57.29578F;
      }

      this.head.rotateAngleY = f6;
      this.head.rotateAngleX = f7;
      float f8;
      float f9;
      float f10;
      float f11;
      if(this.isFlying && this.isPegasus) {
         if(f1 < 0.9999F) {
            this.rainboom = false;
            f8 = MathHelper.sin(0.0F - f1 * 0.5F);
            f9 = MathHelper.sin(0.0F - f1 * 0.5F);
            f10 = MathHelper.sin(f1 * 0.5F);
            f11 = MathHelper.sin(f1 * 0.5F);
         } else {
            this.rainboom = true;
            f8 = 4.712F;
            f9 = 4.712F;
            f10 = 1.571F;
            f11 = 1.571F;
         }

         this.rightarm.rotateAngleY = 0.2F;
         this.LeftArm.rotateAngleY = -0.2F;
         this.RightLeg.rotateAngleY = -0.2F;
         this.LeftLeg.rotateAngleY = 0.2F;
         this.rightarm2.rotateAngleY = 0.2F;
         this.LeftArm2.rotateAngleY = -0.2F;
         this.RightLeg2.rotateAngleY = -0.2F;
         this.LeftLeg2.rotateAngleY = 0.2F;
      } else {
         f8 = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.6F * f1;
         f9 = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
         f10 = MathHelper.cos(f * 0.6662F) * 0.3F * f1;
         f11 = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.3F * f1;
         this.rightarm.rotateAngleY = 0.0F;
         this.LeftArm.rotateAngleY = 0.0F;
         this.RightLeg.rotateAngleY = 0.0F;
         this.LeftLeg.rotateAngleY = 0.0F;
         this.rightarm2.rotateAngleY = 0.0F;
         this.LeftArm2.rotateAngleY = 0.0F;
         this.RightLeg2.rotateAngleY = 0.0F;
         this.LeftLeg2.rotateAngleY = 0.0F;
      }

      if(this.isSleeping) {
         f8 = 4.712F;
         f9 = 4.712F;
         f10 = 1.571F;
         f11 = 1.571F;
      }

      this.rightarm.rotateAngleX = f8;
      this.LeftArm.rotateAngleX = f9;
      this.RightLeg.rotateAngleX = f10;
      this.LeftLeg.rotateAngleX = f11;
      this.rightarm.rotateAngleZ = 0.0F;
      this.LeftArm.rotateAngleZ = 0.0F;
      this.rightarm2.rotateAngleX = f8;
      this.LeftArm2.rotateAngleX = f9;
      this.RightLeg2.rotateAngleX = f10;
      this.LeftLeg2.rotateAngleX = f11;
      this.rightarm2.rotateAngleZ = 0.0F;
      this.LeftArm2.rotateAngleZ = 0.0F;
      if(this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
         this.rightarm.rotateAngleX = this.rightarm.rotateAngleX * 0.5F - 0.3141593F;
         this.rightarm2.rotateAngleX = this.rightarm2.rotateAngleX * 0.5F - 0.3141593F;
      }

      float f12 = 0.0F;
      if(f5 > -9990.0F && !this.isUnicorn) {
         f12 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F * 2.0F) * 0.2F;
      }

      this.Body.rotateAngleY = (float)((double)f12 * 0.2D);
      this.BodyBack.rotateAngleY = (float)((double)f12 * 0.2D);
      float f13 = MathHelper.sin(this.Body.rotateAngleY) * 5.0F;
      float f14 = MathHelper.cos(this.Body.rotateAngleY) * 5.0F;
      float f15 = 4.0F;
      if(this.isSneak && !this.isFlying) {
         f15 = 0.0F;
      }

      if(this.isSleeping) {
         f15 = 2.6F;
      }

      if(this.rainboom) {
         this.rightarm.rotationPointZ = f13 + 2.0F;
         this.rightarm2.rotationPointZ = f13 + 2.0F;
         this.LeftArm.rotationPointZ = 0.0F - f13 + 2.0F;
         this.LeftArm2.rotationPointZ = 0.0F - f13 + 2.0F;
      } else {
         this.rightarm.rotationPointZ = f13 + 1.0F;
         this.rightarm2.rotationPointZ = f13 + 1.0F;
         this.LeftArm.rotationPointZ = 0.0F - f13 + 1.0F;
         this.LeftArm2.rotationPointZ = 0.0F - f13 + 1.0F;
      }

      this.rightarm.rotationPointX = 0.0F - f14 - 1.0F + f15;
      this.rightarm2.rotationPointX = 0.0F - f14 - 1.0F + f15;
      this.LeftArm.rotationPointX = f14 + 1.0F - f15;
      this.LeftArm2.rotationPointX = f14 + 1.0F - f15;
      this.RightLeg.rotationPointX = 0.0F - f14 - 1.0F + f15;
      this.RightLeg2.rotationPointX = 0.0F - f14 - 1.0F + f15;
      this.LeftLeg.rotationPointX = f14 + 1.0F - f15;
      this.LeftLeg2.rotationPointX = f14 + 1.0F - f15;
      this.rightarm.rotateAngleY += this.Body.rotateAngleY;
      this.rightarm2.rotateAngleY += this.Body.rotateAngleY;
      this.LeftArm.rotateAngleY += this.Body.rotateAngleY;
      this.LeftArm2.rotateAngleY += this.Body.rotateAngleY;
      this.LeftArm.rotateAngleX += this.Body.rotateAngleY;
      this.LeftArm2.rotateAngleX += this.Body.rotateAngleY;
      this.rightarm.rotationPointY = 8.0F;
      this.LeftArm.rotationPointY = 8.0F;
      this.RightLeg.rotationPointY = 4.0F;
      this.LeftLeg.rotationPointY = 4.0F;
      this.rightarm2.rotationPointY = 8.0F;
      this.LeftArm2.rotationPointY = 8.0F;
      this.RightLeg2.rotationPointY = 4.0F;
      this.LeftLeg2.rotationPointY = 4.0F;
      float f20;
      float f25;
      float f29;
      float f32;
      if(f5 > -9990.0F && !this.isUnicorn) {
         f20 = 1.0F - f5;
         f20 *= f20 * f20;
         f20 = 1.0F - f20;
         f25 = MathHelper.sin(f20 * 3.141593F);
         f29 = MathHelper.sin(f5 * 3.141593F);
         f32 = f29 * -(this.head.rotateAngleX - 0.7F) * 0.75F;
      }

      float f34;
      float f36;
      if(this.isSneak && !this.isFlying) {
         f20 = 0.4F;
         f25 = 7.0F;
         f29 = -4.0F;
         this.Body.rotateAngleX = f20;
         this.Body.rotationPointY = f25;
         this.Body.rotationPointZ = f29;
         this.BodyBack.rotateAngleX = f20;
         this.BodyBack.rotationPointY = f25;
         this.BodyBack.rotationPointZ = f29;
         this.RightLeg.rotateAngleX -= 0.0F;
         this.LeftLeg.rotateAngleX -= 0.0F;
         this.rightarm.rotateAngleX -= 0.4F;
         this.LeftArm.rotateAngleX -= 0.4F;
         this.RightLeg.rotationPointZ = 10.0F;
         this.LeftLeg.rotationPointZ = 10.0F;
         this.RightLeg.rotationPointY = 7.0F;
         this.LeftLeg.rotationPointY = 7.0F;
         this.RightLeg2.rotateAngleX -= 0.0F;
         this.LeftLeg2.rotateAngleX -= 0.0F;
         this.rightarm2.rotateAngleX -= 0.4F;
         this.LeftArm2.rotateAngleX -= 0.4F;
         this.RightLeg2.rotationPointZ = 10.0F;
         this.LeftLeg2.rotationPointZ = 10.0F;
         this.RightLeg2.rotationPointY = 7.0F;
         this.LeftLeg2.rotationPointY = 7.0F;
         if(this.isSleeping) {
            f32 = 2.0F;
            f34 = -1.0F;
            f36 = 1.0F;
         } else {
            f32 = 6.0F;
            f34 = -2.0F;
            f36 = 0.0F;
         }

         this.head.rotationPointY = f32;
         this.head.rotationPointZ = f34;
         this.head.rotationPointX = f36;
      } else {
         f20 = 0.0F;
         f25 = 0.0F;
         f29 = 0.0F;
         this.Body.rotateAngleX = f20;
         this.Body.rotationPointY = f25;
         this.Body.rotationPointZ = f29;
         this.BodyBack.rotateAngleX = f20;
         this.BodyBack.rotationPointY = f25;
         this.BodyBack.rotationPointZ = f29;
         this.RightLeg.rotationPointZ = 10.0F;
         this.LeftLeg.rotationPointZ = 10.0F;
         this.RightLeg.rotationPointY = 8.0F;
         this.LeftLeg.rotationPointY = 8.0F;
         this.RightLeg2.rotationPointZ = 10.0F;
         this.LeftLeg2.rotationPointZ = 10.0F;
         this.RightLeg2.rotationPointY = 8.0F;
         this.LeftLeg2.rotationPointY = 8.0F;
         f32 = MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
         f34 = MathHelper.sin(f2 * 0.067F) * 0.05F;
         f36 = 0.0F;
         float f37 = 0.0F;
         this.head.rotationPointY = f36;
         this.head.rotationPointZ = f37;
      }

      if(this.isSleeping) {
         this.rightarm.rotationPointZ += 6.0F;
         this.LeftArm.rotationPointZ += 6.0F;
         this.RightLeg.rotationPointZ -= 8.0F;
         this.LeftLeg.rotationPointZ -= 8.0F;
         this.rightarm.rotationPointY += 2.0F;
         this.LeftArm.rotationPointY += 2.0F;
         this.RightLeg.rotationPointY += 2.0F;
         this.LeftLeg.rotationPointY += 2.0F;
         this.rightarm2.rotationPointZ += 6.0F;
         this.LeftArm2.rotationPointZ += 6.0F;
         this.RightLeg2.rotationPointZ -= 8.0F;
         this.LeftLeg2.rotationPointZ -= 8.0F;
         this.rightarm2.rotationPointY += 2.0F;
         this.LeftArm2.rotationPointY += 2.0F;
         this.RightLeg2.rotationPointY += 2.0F;
         this.LeftLeg2.rotationPointY += 2.0F;
      }

      if(this.aimedBow && !this.isUnicorn) {
         f20 = 0.0F;
         f25 = 0.0F;
         this.rightarm.rotateAngleZ = 0.0F;
         this.rightarm.rotateAngleY = -(0.1F - f20 * 0.6F) + this.head.rotateAngleY;
         this.rightarm.rotateAngleX = 4.712F + this.head.rotateAngleX;
         this.rightarm.rotateAngleX -= f20 * 1.2F - f25 * 0.4F;
         this.rightarm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
         this.rightarm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
         this.rightarm2.rotateAngleZ = 0.0F;
         this.rightarm2.rotateAngleY = -(0.1F - f20 * 0.6F) + this.head.rotateAngleY;
         this.rightarm2.rotateAngleX = 4.712F + this.head.rotateAngleX;
         this.rightarm2.rotateAngleX -= f20 * 1.2F - f25 * 0.4F;
         this.rightarm2.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
         this.rightarm2.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
         ++this.rightarm.rotationPointZ;
         ++this.rightarm2.rotationPointZ;
      }

   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.head.render(f5);
      this.Body.render(f5);
      this.BodyBack.render(f5);
      this.LeftArm.render(f5);
      this.rightarm.render(f5);
      this.LeftLeg.render(f5);
      this.RightLeg.render(f5);
      this.LeftArm2.render(f5);
      this.rightarm2.render(f5);
      this.LeftLeg2.render(f5);
      this.RightLeg2.render(f5);
   }
}
