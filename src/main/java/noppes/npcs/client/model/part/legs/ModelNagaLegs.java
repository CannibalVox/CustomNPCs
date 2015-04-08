package noppes.npcs.client.model.part.legs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelNagaLegs extends ModelRenderer {

   private ModelRenderer nagaPart1;
   private ModelRenderer nagaPart2;
   private ModelRenderer nagaPart3;
   private ModelRenderer nagaPart4;
   private ModelRenderer nagaPart5;
   public boolean isRiding = false;
   public boolean isSneaking = false;
   public boolean isSleeping = false;
   public boolean isCrawling = false;


   public ModelNagaLegs(ModelBase base) {
      super(base);
      this.nagaPart1 = new ModelRenderer(base, 0, 0);
      ModelRenderer legPart = new ModelRenderer(base, 0, 16);
      legPart.addBox(0.0F, -2.0F, -2.0F, 4, 4, 4);
      legPart.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.nagaPart1.addChild(legPart);
      legPart = new ModelRenderer(base, 0, 16);
      legPart.mirror = true;
      legPart.addBox(0.0F, -2.0F, -2.0F, 4, 4, 4);
      this.nagaPart1.addChild(legPart);
      this.nagaPart2 = new ModelRenderer(base, 0, 0);
      this.nagaPart2.childModels = this.nagaPart1.childModels;
      this.nagaPart3 = new ModelRenderer(base, 0, 0);
      ModelPlaneRenderer plane = new ModelPlaneRenderer(base, 4, 24);
      plane.addBackPlane(0.0F, -2.0F, 0.0F, 4, 4);
      plane.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 4, 24);
      plane.mirror = true;
      plane.addBackPlane(0.0F, -2.0F, 0.0F, 4, 4);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 8, 24);
      plane.addBackPlane(0.0F, -2.0F, 6.0F, 4, 4);
      plane.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 8, 24);
      plane.mirror = true;
      plane.addBackPlane(0.0F, -2.0F, 6.0F, 4, 4);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 4, 26);
      plane.addTopPlane(0.0F, -2.0F, -6.0F, 4, 6);
      plane.setRotationPoint(-4.0F, 0.0F, 0.0F);
      plane.rotateAngleX = 3.1415927F;
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 4, 26);
      plane.mirror = true;
      plane.addTopPlane(0.0F, -2.0F, -6.0F, 4, 6);
      plane.rotateAngleX = 3.1415927F;
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 8, 26);
      plane.addTopPlane(0.0F, -2.0F, 0.0F, 4, 6);
      plane.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 8, 26);
      plane.mirror = true;
      plane.addTopPlane(0.0F, -2.0F, 0.0F, 4, 6);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 0, 26);
      plane.rotateAngleX = 1.5707964F;
      plane.addSidePlane(0.0F, 0.0F, -2.0F, 6, 4);
      plane.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.nagaPart3.addChild(plane);
      plane = new ModelPlaneRenderer(base, 0, 26);
      plane.rotateAngleX = 1.5707964F;
      plane.addSidePlane(4.0F, 0.0F, -2.0F, 6, 4);
      this.nagaPart3.addChild(plane);
      this.nagaPart4 = new ModelRenderer(base, 0, 0);
      this.nagaPart4.childModels = this.nagaPart3.childModels;
      this.nagaPart5 = new ModelRenderer(base, 0, 0);
      legPart = new ModelRenderer(base, 56, 20);
      legPart.addBox(0.0F, 0.0F, -2.0F, 2, 5, 2);
      legPart.setRotationPoint(-2.0F, 0.0F, 0.0F);
      legPart.rotateAngleX = 1.5707964F;
      this.nagaPart5.addChild(legPart);
      legPart = new ModelRenderer(base, 56, 20);
      legPart.mirror = true;
      legPart.addBox(0.0F, 0.0F, -2.0F, 2, 5, 2);
      legPart.rotateAngleX = 1.5707964F;
      this.nagaPart5.addChild(legPart);
      this.addChild(this.nagaPart1);
      this.addChild(this.nagaPart2);
      this.addChild(this.nagaPart3);
      this.addChild(this.nagaPart4);
      this.addChild(this.nagaPart5);
      this.nagaPart1.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.nagaPart2.setRotationPoint(0.0F, 18.0F, 0.6F);
      this.nagaPart3.setRotationPoint(0.0F, 22.0F, -0.3F);
      this.nagaPart4.setRotationPoint(0.0F, 22.0F, 5.0F);
      this.nagaPart5.setRotationPoint(0.0F, 22.0F, 10.0F);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      this.nagaPart1.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.26F * par2;
      this.nagaPart2.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.5F * par2;
      this.nagaPart3.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.26F * par2;
      this.nagaPart4.rotateAngleY = -MathHelper.cos(par1 * 0.6662F) * 0.16F * par2;
      this.nagaPart5.rotateAngleY = -MathHelper.cos(par1 * 0.6662F) * 0.3F * par2;
      this.nagaPart1.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.nagaPart2.setRotationPoint(0.0F, 18.0F, 0.6F);
      this.nagaPart3.setRotationPoint(0.0F, 22.0F, -0.3F);
      this.nagaPart4.setRotationPoint(0.0F, 22.0F, 5.0F);
      this.nagaPart5.setRotationPoint(0.0F, 22.0F, 10.0F);
      this.nagaPart1.rotateAngleX = 0.0F;
      this.nagaPart2.rotateAngleX = 0.0F;
      this.nagaPart3.rotateAngleX = 0.0F;
      this.nagaPart4.rotateAngleX = 0.0F;
      this.nagaPart5.rotateAngleX = 0.0F;
      if(this.isSleeping || this.isCrawling) {
         this.nagaPart3.rotateAngleX = -1.5707964F;
         this.nagaPart4.rotateAngleX = -1.5707964F;
         this.nagaPart5.rotateAngleX = -1.5707964F;
         this.nagaPart3.rotationPointY -= 2.0F;
         this.nagaPart3.rotationPointZ = 0.9F;
         this.nagaPart4.rotationPointY += 4.0F;
         this.nagaPart4.rotationPointZ = 0.9F;
         this.nagaPart5.rotationPointY += 7.0F;
         this.nagaPart5.rotationPointZ = 2.9F;
      }

      if(this.isRiding) {
         --this.nagaPart1.rotationPointY;
         this.nagaPart1.rotateAngleX = -0.19634955F;
         this.nagaPart1.rotationPointZ = -1.0F;
         this.nagaPart2.rotationPointY -= 4.0F;
         this.nagaPart2.rotationPointZ = -1.0F;
         this.nagaPart3.rotationPointY -= 9.0F;
         --this.nagaPart3.rotationPointZ;
         this.nagaPart4.rotationPointY -= 13.0F;
         --this.nagaPart4.rotationPointZ;
         this.nagaPart5.rotationPointY -= 9.0F;
         --this.nagaPart5.rotationPointZ;
         if(this.isSneaking) {
            this.nagaPart1.rotationPointZ += 5.0F;
            this.nagaPart3.rotationPointZ += 5.0F;
            this.nagaPart4.rotationPointZ += 5.0F;
            this.nagaPart5.rotationPointZ += 4.0F;
            --this.nagaPart1.rotationPointY;
            --this.nagaPart2.rotationPointY;
            --this.nagaPart3.rotationPointY;
            --this.nagaPart4.rotationPointY;
            --this.nagaPart5.rotationPointY;
         }
      } else if(this.isSneaking) {
         --this.nagaPart1.rotationPointY;
         --this.nagaPart2.rotationPointY;
         --this.nagaPart3.rotationPointY;
         --this.nagaPart4.rotationPointY;
         --this.nagaPart5.rotationPointY;
         this.nagaPart1.rotationPointZ = 5.0F;
         this.nagaPart2.rotationPointZ = 3.0F;
      }

   }

   public void render(float par7) {
      if(!super.isHidden && super.showModel) {
         this.nagaPart1.render(par7);
         this.nagaPart3.render(par7);
         if(!this.isRiding) {
            this.nagaPart2.render(par7);
         }

         GL11.glPushMatrix();
         GL11.glScalef(0.74F, 0.7F, 0.85F);
         GL11.glTranslatef(this.nagaPart3.rotateAngleY, 0.66F, 0.06F);
         this.nagaPart4.render(par7);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glTranslatef(this.nagaPart3.rotateAngleY + this.nagaPart4.rotateAngleY, 0.0F, 0.0F);
         this.nagaPart5.render(par7);
         GL11.glPopMatrix();
      }
   }
}
