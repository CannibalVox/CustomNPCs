package noppes.npcs.client.model.part.legs;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.npcs.client.model.ModelMPM;

public class ModelDigitigradeLegs extends ModelRenderer {

   private ModelRenderer rightleg;
   private ModelRenderer rightleg2;
   private ModelRenderer rightleglow;
   private ModelRenderer rightfoot;
   private ModelRenderer leftleg;
   private ModelRenderer leftleg2;
   private ModelRenderer leftleglow;
   private ModelRenderer leftfoot;
   public boolean isRiding = false;
   public boolean isSneaking = false;
   public boolean isSleeping = false;
   public boolean isCrawling = false;
   private ModelMPM base;


   public ModelDigitigradeLegs(ModelMPM base) {
      super(base);
      this.base = base;
      this.rightleg = new ModelRenderer(base, 0, 16);
      this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
      this.rightleg.setRotationPoint(-2.1F, 11.0F, 0.0F);
      this.setRotation(this.rightleg, -0.3F, 0.0F, 0.0F);
      this.addChild(this.rightleg);
      this.rightleg2 = new ModelRenderer(base, 0, 20);
      this.rightleg2.addBox(-1.5F, -1.0F, -2.0F, 3, 7, 3);
      this.rightleg2.setRotationPoint(0.0F, 4.1F, 0.0F);
      this.setRotation(this.rightleg2, 1.1F, 0.0F, 0.0F);
      this.rightleg.addChild(this.rightleg2);
      this.rightleglow = new ModelRenderer(base, 0, 24);
      this.rightleglow.addBox(-1.5F, 0.0F, -1.0F, 3, 5, 2);
      this.rightleglow.setRotationPoint(0.0F, 5.0F, 0.0F);
      this.setRotation(this.rightleglow, -1.35F, 0.0F, 0.0F);
      this.rightleg2.addChild(this.rightleglow);
      this.rightfoot = new ModelRenderer(base, 1, 26);
      this.rightfoot.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 4);
      this.rightfoot.setRotationPoint(0.0F, 3.7F, 1.2F);
      this.setRotation(this.rightfoot, 0.55F, 0.0F, 0.0F);
      this.rightleglow.addChild(this.rightfoot);
      this.leftleg = new ModelRenderer(base, 0, 16);
      this.leftleg.mirror = true;
      this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
      this.leftleg.setRotationPoint(2.1F, 11.0F, 0.0F);
      this.setRotation(this.leftleg, -0.3F, 0.0F, 0.0F);
      this.addChild(this.leftleg);
      this.leftleg2 = new ModelRenderer(base, 0, 20);
      this.leftleg2.mirror = true;
      this.leftleg2.addBox(-1.5F, -1.0F, -2.0F, 3, 7, 3);
      this.leftleg2.setRotationPoint(0.0F, 4.1F, 0.0F);
      this.setRotation(this.leftleg2, 1.1F, 0.0F, 0.0F);
      this.leftleg.addChild(this.leftleg2);
      this.leftleglow = new ModelRenderer(base, 0, 24);
      this.leftleglow.mirror = true;
      this.leftleglow.addBox(-1.5F, 0.0F, -1.0F, 3, 5, 2);
      this.leftleglow.setRotationPoint(0.0F, 5.0F, 0.0F);
      this.setRotation(this.leftleglow, -1.35F, 0.0F, 0.0F);
      this.leftleg2.addChild(this.leftleglow);
      this.leftfoot = new ModelRenderer(base, 1, 26);
      this.leftfoot.mirror = true;
      this.leftfoot.addBox(-1.5F, 0.0F, -5.0F, 3, 2, 4);
      this.leftfoot.setRotationPoint(0.0F, 3.7F, 1.2F);
      this.setRotation(this.leftfoot, 0.55F, 0.0F, 0.0F);
      this.leftleglow.addChild(this.leftfoot);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      this.rightleg.rotateAngleX = this.base.bipedRightLeg.rotateAngleX - 0.3F;
      this.leftleg.rotateAngleX = this.base.bipedLeftLeg.rotateAngleX - 0.3F;
      this.rightleg.rotationPointY = this.base.bipedRightLeg.rotationPointY;
      this.leftleg.rotationPointY = this.base.bipedLeftLeg.rotationPointY;
      this.rightleg.rotationPointZ = this.base.bipedRightLeg.rotationPointZ;
      this.leftleg.rotationPointZ = this.base.bipedLeftLeg.rotationPointZ;
      if(!this.base.isSneak) {
         --this.leftleg.rotationPointY;
         --this.rightleg.rotationPointY;
      }

   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
