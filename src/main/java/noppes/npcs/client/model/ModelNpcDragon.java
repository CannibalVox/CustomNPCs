package noppes.npcs.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import noppes.npcs.entity.EntityNpcDragon;
import org.lwjgl.opengl.GL11;

public class ModelNpcDragon extends ModelBase {

   private ModelRenderer head;
   private ModelRenderer neck;
   private ModelRenderer jaw;
   private ModelRenderer body;
   private ModelRenderer rearLeg;
   private ModelRenderer frontLeg;
   private ModelRenderer rearLegTip;
   private ModelRenderer frontLegTip;
   private ModelRenderer rearFoot;
   private ModelRenderer frontFoot;
   private ModelRenderer wing;
   private ModelRenderer wingTip;
   private float field_40317_s;


   public ModelNpcDragon(float f) {
      super.textureWidth = 256;
      super.textureHeight = 256;
      this.setTextureOffset("body.body", 0, 0);
      this.setTextureOffset("wing.skin", -56, 88);
      this.setTextureOffset("wingtip.skin", -56, 144);
      this.setTextureOffset("rearleg.main", 0, 0);
      this.setTextureOffset("rearfoot.main", 112, 0);
      this.setTextureOffset("rearlegtip.main", 196, 0);
      this.setTextureOffset("head.upperhead", 112, 30);
      this.setTextureOffset("wing.bone", 112, 88);
      this.setTextureOffset("head.upperlip", 176, 44);
      this.setTextureOffset("jaw.jaw", 176, 65);
      this.setTextureOffset("frontleg.main", 112, 104);
      this.setTextureOffset("wingtip.bone", 112, 136);
      this.setTextureOffset("frontfoot.main", 144, 104);
      this.setTextureOffset("neck.box", 192, 104);
      this.setTextureOffset("frontlegtip.main", 226, 138);
      this.setTextureOffset("body.scale", 220, 53);
      this.setTextureOffset("head.scale", 0, 0);
      this.setTextureOffset("neck.scale", 48, 0);
      this.setTextureOffset("head.nostril", 112, 0);
      float f1 = -16.0F;
      this.head = new ModelRenderer(this, "head");
      this.head.addBox("upperlip", -6.0F, -1.0F, -8.0F + f1, 12, 5, 16);
      this.head.addBox("upperhead", -8.0F, -8.0F, 6.0F + f1, 16, 16, 16);
      this.head.mirror = true;
      this.head.addBox("scale", -5.0F, -12.0F, 12.0F + f1, 2, 4, 6);
      this.head.addBox("nostril", -5.0F, -3.0F, -6.0F + f1, 2, 2, 4);
      this.head.mirror = false;
      this.head.addBox("scale", 3.0F, -12.0F, 12.0F + f1, 2, 4, 6);
      this.head.addBox("nostril", 3.0F, -3.0F, -6.0F + f1, 2, 2, 4);
      this.jaw = new ModelRenderer(this, "jaw");
      this.jaw.setRotationPoint(0.0F, 4.0F, 8.0F + f1);
      this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.head.addChild(this.jaw);
      this.neck = new ModelRenderer(this, "neck");
      this.neck.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.neck.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
      this.body = new ModelRenderer(this, "body");
      this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
      this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
      this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
      this.wing = new ModelRenderer(this, "wing");
      this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
      this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wingTip = new ModelRenderer(this, "wingtip");
      this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
      this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wing.addChild(this.wingTip);
      this.frontLeg = new ModelRenderer(this, "frontleg");
      this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
      this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
      this.frontLegTip = new ModelRenderer(this, "frontlegtip");
      this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
      this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
      this.frontLeg.addChild(this.frontLegTip);
      this.frontFoot = new ModelRenderer(this, "frontfoot");
      this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
      this.frontLegTip.addChild(this.frontFoot);
      this.rearLeg = new ModelRenderer(this, "rearleg");
      this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
      this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
      this.rearLegTip = new ModelRenderer(this, "rearlegtip");
      this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
      this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
      this.rearLeg.addChild(this.rearLegTip);
      this.rearFoot = new ModelRenderer(this, "rearfoot");
      this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
      this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
      this.rearLegTip.addChild(this.rearFoot);
   }

   public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
      this.field_40317_s = f2;
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      EntityNpcDragon entitydragon = (EntityNpcDragon)entity;
      GL11.glPushMatrix();
      float f6 = entitydragon.field_40173_aw + (entitydragon.field_40172_ax - entitydragon.field_40173_aw) * this.field_40317_s;
      this.jaw.rotateAngleX = (float)(Math.sin((double)(f6 * 3.1415927F * 2.0F)) + 1.0D) * 0.2F;
      float f7 = (float)(Math.sin((double)(f6 * 3.1415927F * 2.0F - 1.0F)) + 1.0D);
      f7 = (f7 * f7 * 1.0F + f7 * 2.0F) * 0.05F;
      GL11.glTranslatef(0.0F, f7 - 2.0F, -3.0F);
      GL11.glRotatef(f7 * 2.0F, 1.0F, 0.0F, 0.0F);
      float f8 = -30.0F;
      float f9 = 22.0F;
      float f10 = 0.0F;
      float f11 = 1.5F;
      double[] ad = entitydragon.func_40160_a(6, this.field_40317_s);
      float f12 = this.func_40307_a(entitydragon.func_40160_a(5, this.field_40317_s)[0] - entitydragon.func_40160_a(10, this.field_40317_s)[0]);
      float f13 = this.func_40307_a(entitydragon.func_40160_a(5, this.field_40317_s)[0] + (double)(f12 / 2.0F));
      f8 += 2.0F;
      float f14 = 0.0F;
      float f15 = f6 * 3.141593F * 2.0F;
      f8 = 20.0F;
      f9 = -12.0F;

      for(int ad1 = 0; ad1 < 5; ++ad1) {
         double[] k = entitydragon.func_40160_a(5 - ad1, this.field_40317_s);
         f14 = (float)Math.cos((double)((float)ad1 * 0.45F + f15)) * 0.15F;
         this.neck.rotateAngleY = this.func_40307_a(k[0] - ad[0]) * 3.1415927F / 180.0F * f11;
         this.neck.rotateAngleX = f14 + (float)(k[1] - ad[1]) * 3.1415927F / 180.0F * f11 * 5.0F;
         this.neck.rotateAngleZ = -this.func_40307_a(k[0] - (double)f13) * 3.1415927F / 180.0F * f11;
         this.neck.rotationPointY = f8;
         this.neck.rotationPointZ = f9;
         this.neck.rotationPointX = f10;
         f8 = (float)((double)f8 + Math.sin((double)this.neck.rotateAngleX) * 10.0D);
         f9 = (float)((double)f9 - Math.cos((double)this.neck.rotateAngleY) * Math.cos((double)this.neck.rotateAngleX) * 10.0D);
         f10 = (float)((double)f10 - Math.sin((double)this.neck.rotateAngleY) * Math.cos((double)this.neck.rotateAngleX) * 10.0D);
         this.neck.render(f5);
      }

      this.head.rotationPointY = f8;
      this.head.rotationPointZ = f9;
      this.head.rotationPointX = f10;
      double[] var23 = entitydragon.func_40160_a(0, this.field_40317_s);
      this.head.rotateAngleY = this.func_40307_a(var23[0] - ad[0]) * 3.1415927F / 180.0F * 1.0F;
      this.head.rotateAngleZ = -this.func_40307_a(var23[0] - (double)f13) * 3.1415927F / 180.0F * 1.0F;
      this.head.render(f5);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 1.0F, 0.0F);
      if(entitydragon.onGround) {
         GL11.glRotatef(-f12 * f11 * 0.3F, 0.0F, 0.0F, 1.0F);
      } else {
         GL11.glRotatef(-f12 * f11 * 1.0F, 0.0F, 0.0F, 1.0F);
      }

      GL11.glTranslatef(0.0F, -1.0F, 0.0F);
      this.body.rotateAngleZ = 0.0F;
      this.body.render(f5);
      int var24;
      if(entitydragon.onGround) {
         for(var24 = 0; var24 < 2; ++var24) {
            GL11.glEnable(2884);
            this.wing.rotateAngleX = 0.25F;
            this.wing.rotateAngleY = 0.95F;
            this.wing.rotateAngleZ = -0.5F;
            this.wingTip.rotateAngleZ = -0.4F;
            this.frontLeg.rotateAngleX = MathHelper.cos((float)((double)(f * 0.6662F) + (var24 == 0?0.0D:3.141592653589793D))) * 0.6F * f1 + 0.45F + f7 * 0.5F;
            this.frontLegTip.rotateAngleX = -1.3F - f7 * 1.2F;
            this.frontFoot.rotateAngleX = 0.85F + f7 * 0.5F;
            this.frontLeg.render(f5);
            this.rearLeg.rotateAngleX = MathHelper.cos((float)((double)(f * 0.6662F) + (var24 == 0?3.141592653589793D:0.0D))) * 0.6F * f1 + 0.75F + f7 * 0.5F;
            this.rearLegTip.rotateAngleX = -1.6F - f7 * 0.8F;
            this.rearLegTip.rotationPointY = 20.0F;
            this.rearLegTip.rotationPointZ = 2.0F;
            this.rearFoot.rotateAngleX = 0.85F + f7 * 0.2F;
            this.rearLeg.render(f5);
            this.wing.render(f5);
            GL11.glScalef(-1.0F, 1.0F, 1.0F);
            if(var24 == 0) {
               GL11.glCullFace(1028);
            }
         }
      } else {
         for(var24 = 0; var24 < 2; ++var24) {
            GL11.glEnable(2884);
            float ad2 = f6 * 3.1415927F * 2.0F;
            this.wing.rotateAngleX = 0.125F - (float)Math.cos((double)ad2) * 0.2F;
            this.wing.rotateAngleY = 0.25F;
            this.wing.rotateAngleZ = (float)(Math.sin((double)ad2) + 0.125D) * 0.8F;
            this.wingTip.rotateAngleZ = -((float)(Math.sin((double)(ad2 + 2.0F)) + 0.5D)) * 0.75F;
            this.rearLegTip.rotationPointY = 32.0F;
            this.rearLegTip.rotationPointZ = -2.0F;
            this.rearLeg.rotateAngleX = 1.0F + f7 * 0.1F;
            this.rearLegTip.rotateAngleX = 0.5F + f7 * 0.1F;
            this.rearFoot.rotateAngleX = 0.75F + f7 * 0.1F;
            this.frontLeg.rotateAngleX = 1.3F + f7 * 0.1F;
            this.frontLegTip.rotateAngleX = -0.5F - f7 * 0.1F;
            this.frontFoot.rotateAngleX = 0.75F + f7 * 0.1F;
            this.wing.render(f5);
            this.frontLeg.render(f5);
            this.rearLeg.render(f5);
            GL11.glScalef(-1.0F, 1.0F, 1.0F);
            if(var24 == 0) {
               GL11.glCullFace(1028);
            }
         }
      }

      GL11.glPopMatrix();
      GL11.glCullFace(1029);
      GL11.glDisable(2884);
      f14 = -((float)Math.sin((double)(f6 * 3.141593F * 2.0F))) * 0.0F;
      f15 = f6 * 3.1415927F * 2.0F;
      f8 = 10.0F;
      f9 = 60.0F;
      f10 = 0.0F;
      ad = entitydragon.func_40160_a(11, this.field_40317_s);

      for(var24 = 0; var24 < 12; ++var24) {
         double[] var25 = entitydragon.func_40160_a(12 + var24, this.field_40317_s);
         f14 = (float)((double)f14 + Math.sin((double)((float)var24 * 0.45F + f15)) * 0.05000000074505806D);
         this.neck.rotateAngleY = (this.func_40307_a(var25[0] - ad[0]) * f11 + 180.0F) * 3.1415927F / 180.0F;
         this.neck.rotateAngleX = f14 + (float)(var25[1] - ad[1]) * 3.1415927F / 180.0F * f11 * 5.0F;
         this.neck.rotateAngleZ = this.func_40307_a(var25[0] - (double)f13) * 3.1415927F / 180.0F * f11;
         this.neck.rotationPointY = f8;
         this.neck.rotationPointZ = f9;
         this.neck.rotationPointX = f10;
         f8 = (float)((double)f8 + Math.sin((double)this.neck.rotateAngleX) * 10.0D);
         f9 = (float)((double)f9 - Math.cos((double)this.neck.rotateAngleY) * Math.cos((double)this.neck.rotateAngleX) * 10.0D);
         f10 = (float)((double)f10 - Math.sin((double)this.neck.rotateAngleY) * Math.cos((double)this.neck.rotateAngleX) * 10.0D);
         this.neck.render(f5);
      }

      GL11.glPopMatrix();
   }

   private float func_40307_a(double d) {
      while(d >= 180.0D) {
         d -= 360.0D;
      }

      while(d < -180.0D) {
         d += 360.0D;
      }

      return (float)d;
   }
}
