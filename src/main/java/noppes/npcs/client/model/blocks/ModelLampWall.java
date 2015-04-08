package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLampWall extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 0, 6);
   ModelRenderer Top1;
   ModelRenderer Top2;
   ModelRenderer Top3;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape5;


   public ModelLampWall() {
      this.Base.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4);
      this.Base.setRotationPoint(-2.0F, 14.0F, 1.0F);
      this.Top1 = new ModelRenderer(this, 0, 0);
      this.Top1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5);
      this.Top1.setRotationPoint(-2.5F, 14.0F, 0.5F);
      this.Top2 = new ModelRenderer(this, 0, 0);
      this.Top2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Top2.setRotationPoint(-2.0F, 13.5F, 1.0F);
      this.Top3 = new ModelRenderer(this, 0, 0);
      this.Top3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.Top3.setRotationPoint(-1.5F, 13.0F, 1.5F);
      this.Shape2 = new ModelRenderer(this, 0, 0);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
      this.Shape2.setRotationPoint(-0.5F, 11.0F, 3.5F);
      this.Shape3 = new ModelRenderer(this, 0, 0);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
      this.Shape3.setRotationPoint(0.0F, 9.5F, 6.5F);
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.7853982F);
      this.Shape4 = new ModelRenderer(this, 0, 0);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Shape4.setRotationPoint(-0.5F, 10.5F, 2.5F);
      this.Shape5 = new ModelRenderer(this, 0, 0);
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 4, 4, 1);
      this.Shape5.setRotationPoint(0.0F, 8.7F, 7.0F);
      this.setRotation(this.Shape5, 0.0F, 0.0F, 0.7853982F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Top1.render(f5);
      this.Top2.render(f5);
      this.Top3.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
