package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStool extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 9, 3);
   ModelRenderer Leg1;
   ModelRenderer Leg2;
   ModelRenderer Leg3;
   ModelRenderer Leg4;
   ModelRenderer Shape1;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;


   public ModelStool() {
      this.Base.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10);
      this.Base.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.Leg1 = new ModelRenderer(this, 0, 12);
      this.Leg1.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 1);
      this.Leg1.setRotationPoint(2.0F, 17.0F, 2.0F);
      this.setRotation(this.Leg1, 0.3316126F, 0.7853982F, 0.0F);
      this.Leg2 = new ModelRenderer(this, 0, 12);
      this.Leg2.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 1);
      this.Leg2.setRotationPoint(2.0F, 17.0F, -2.0F);
      this.setRotation(this.Leg2, 0.3316126F, 2.356194F, -0.0081449F);
      this.Leg3 = new ModelRenderer(this, 0, 12);
      this.Leg3.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 1);
      this.Leg3.setRotationPoint(-2.0F, 17.0F, 2.0F);
      this.setRotation(this.Leg3, 0.3316126F, -0.7853982F, 0.0F);
      this.Leg4 = new ModelRenderer(this, 0, 12);
      this.Leg4.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 1);
      this.Leg4.setRotationPoint(-2.0F, 17.0F, -2.0F);
      this.setRotation(this.Leg4, 0.3316126F, -2.356194F, 0.0F);
      this.Shape1 = new ModelRenderer(this, 0, 11);
      this.Shape1.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 1);
      this.Shape1.setRotationPoint(2.4F, 19.0F, 0.0F);
      this.setRotation(this.Shape1, 0.0F, 1.570796F, 0.0F);
      this.Shape2 = new ModelRenderer(this, 0, 11);
      this.Shape2.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 1);
      this.Shape2.setRotationPoint(0.0F, 19.0F, 2.4F);
      this.Shape3 = new ModelRenderer(this, 0, 11);
      this.Shape3.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 1);
      this.Shape3.setRotationPoint(0.0F, 19.0F, -3.4F);
      this.Shape4 = new ModelRenderer(this, 0, 11);
      this.Shape4.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 1);
      this.Shape4.setRotationPoint(-3.4F, 19.0F, 0.0F);
      this.setRotation(this.Shape4, 0.0F, 1.570796F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Leg1.render(f5);
      this.Leg2.render(f5);
      this.Leg3.render(f5);
      this.Leg4.render(f5);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
