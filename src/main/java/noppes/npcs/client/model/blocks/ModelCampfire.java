package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCampfire extends ModelBase {

   ModelRenderer Rock1 = new ModelRenderer(this, 0, 0);
   ModelRenderer Rock2;
   ModelRenderer Rock3;
   ModelRenderer Rock4;
   ModelRenderer Rock5;
   ModelRenderer Rock6;
   ModelRenderer Rock7;
   ModelRenderer Rock8;
   ModelRenderer Log3;
   ModelRenderer Log1;
   ModelRenderer Log4;
   ModelRenderer Log2;


   public ModelCampfire() {
      this.Rock1.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
      this.Rock1.setRotationPoint(5.0F, 22.0F, 3.0F);
      this.setRotation(this.Rock1, 0.0F, -0.7435722F, 0.0F);
      this.Rock2 = new ModelRenderer(this, 0, 0);
      this.Rock2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 6);
      this.Rock2.setRotationPoint(5.0F, 21.0F, -3.0F);
      this.Rock3 = new ModelRenderer(this, 0, 0);
      this.Rock3.addBox(0.0F, 0.0F, 0.0F, 5, 3, 3);
      this.Rock3.setRotationPoint(2.5F, 21.0F, -8.0F);
      this.setRotation(this.Rock3, 0.0F, -0.5576792F, 0.0F);
      this.Rock4 = new ModelRenderer(this, 0, 0);
      this.Rock4.addBox(0.0F, 0.0F, 0.0F, 3, 2, 2);
      this.Rock4.setRotationPoint(-2.0F, 22.0F, -7.5F);
      this.Rock5 = new ModelRenderer(this, 0, 0);
      this.Rock5.addBox(0.0F, 0.0F, -2.0F, 7, 2, 2);
      this.Rock5.setRotationPoint(-3.5F, 22.0F, 7.8F);
      this.Rock6 = new ModelRenderer(this, 0, 0);
      this.Rock6.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
      this.Rock6.setRotationPoint(-5.0F, 21.0F, 3.0F);
      this.setRotation(this.Rock6, 0.0F, -1.003822F, 0.0F);
      this.Rock7 = new ModelRenderer(this, 0, 0);
      this.Rock7.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3);
      this.Rock7.setRotationPoint(-7.0F, 21.0F, -4.5F);
      this.setRotation(this.Rock7, 0.0F, 0.8551081F, 0.0F);
      this.Rock8 = new ModelRenderer(this, 0, 0);
      this.Rock8.addBox(0.0F, 0.0F, 0.0F, 3, 2, 6);
      this.Rock8.setRotationPoint(-8.0F, 22.0F, -3.0F);
      this.Log3 = new ModelRenderer(this, 0, 16);
      this.Log3.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Log3.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.setRotation(this.Log3, 0.3717861F, -1.487144F, -0.1487144F);
      this.Log1 = new ModelRenderer(this, 8, 21);
      this.Log1.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Log1.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.setRotation(this.Log1, -0.1487144F, 0.0F, -0.3717861F);
      this.Log4 = new ModelRenderer(this, 0, 16);
      this.Log4.addBox(0.0F, 0.0F, -2.0F, 2, 9, 2);
      this.Log4.setRotationPoint(1.0F, 16.0F, 1.0F);
      this.setRotation(this.Log4, -0.3346075F, 3.141593F, 0.0F);
      this.Log2 = new ModelRenderer(this, 0, 20);
      this.Log2.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Log2.setRotationPoint(1.0F, 16.0F, -1.0F);
      this.setRotation(this.Log2, 0.2974289F, 3.141593F, 0.0F);
   }

   public void renderRock(float f5) {
      this.Rock1.render(f5);
      this.Rock2.render(f5);
      this.Rock3.render(f5);
      this.Rock4.render(f5);
      this.Rock5.render(f5);
      this.Rock6.render(f5);
      this.Rock7.render(f5);
      this.Rock8.render(f5);
   }

   public void renderLog(float f5) {
      this.Log3.render(f5);
      this.Log1.render(f5);
      this.Log4.render(f5);
      this.Log2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
