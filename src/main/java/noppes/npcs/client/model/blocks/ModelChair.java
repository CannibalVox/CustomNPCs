package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChair extends ModelBase {

   ModelRenderer Leg1 = new ModelRenderer(this, 0, 0);
   ModelRenderer Leg2;
   ModelRenderer Leg3;
   ModelRenderer Leg4;
   ModelRenderer Shape1;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape5;
   ModelRenderer Shape6;
   ModelRenderer Shape7;
   ModelRenderer Shape8;
   ModelRenderer Shape9;
   ModelRenderer Shape10;
   ModelRenderer Shape11;


   public ModelChair() {
      this.Leg1.mirror = true;
      this.Leg1.addBox(0.0F, 0.0F, 0.0F, 1, 18, 1);
      this.Leg1.setRotationPoint(4.01F, 6.0F, 5.01F);
      this.Leg2 = new ModelRenderer(this, 0, 0);
      this.Leg2.mirror = true;
      this.Leg2.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1);
      this.Leg2.setRotationPoint(4.01F, 15.5F, -5.01F);
      this.Leg3 = new ModelRenderer(this, 0, 0);
      this.Leg3.addBox(0.0F, 0.0F, 0.0F, 1, 18, 1);
      this.Leg3.setRotationPoint(-5.01F, 6.0F, 5.01F);
      this.Leg4 = new ModelRenderer(this, 0, 0);
      this.Leg4.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1);
      this.Leg4.setRotationPoint(-5.01F, 15.5F, -5.01F);
      this.Shape1 = new ModelRenderer(this, 8, 2);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 10, 1, 11);
      this.Shape1.setRotationPoint(-5.0F, 16.0F, -5.0F);
      this.Shape2 = new ModelRenderer(this, 4, 4);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Shape2.setRotationPoint(-1.5F, 6.51F, 5.5F);
      this.Shape3 = new ModelRenderer(this, 4, 4);
      this.Shape3.mirror = true;
      this.Shape3.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Shape3.setRotationPoint(4.0F, 6.5F, 5.0F);
      this.setRotation(this.Shape3, 0.0F, 0.2094395F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 4, 4);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Shape4.setRotationPoint(-4.0F, 6.5F, 5.0F);
      this.setRotation(this.Shape4, 0.0F, -0.2094395F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 46, 0);
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1);
      this.Shape5.setRotationPoint(-4.0F, 19.0F, 5.0F);
      this.Shape6 = new ModelRenderer(this, 46, 0);
      this.Shape6.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1);
      this.Shape6.setRotationPoint(-4.0F, 19.0F, -5.0F);
      this.Shape7 = new ModelRenderer(this, 11, 13);
      this.Shape7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 9);
      this.Shape7.setRotationPoint(-5.0F, 20.0F, -4.0F);
      this.Shape8 = new ModelRenderer(this, 11, 13);
      this.Shape8.mirror = true;
      this.Shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 9);
      this.Shape8.setRotationPoint(4.0F, 20.0F, -4.0F);
      this.Shape9 = new ModelRenderer(this, 0, 0);
      this.Shape9.mirror = true;
      this.Shape9.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
      this.Shape9.setRotationPoint(2.0F, 8.0F, 5.5F);
      this.setRotation(this.Shape9, -0.0523599F, 0.0F, 0.0F);
      this.Shape10 = new ModelRenderer(this, 0, 0);
      this.Shape10.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
      this.Shape10.setRotationPoint(-3.0F, 8.0F, 5.5F);
      this.setRotation(this.Shape10, -0.0523599F, 0.0F, 0.0F);
      this.Shape11 = new ModelRenderer(this, 0, 0);
      this.Shape11.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
      this.Shape11.setRotationPoint(-0.5F, 8.0F, 5.6F);
      this.setRotation(this.Shape11, -0.0698132F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Leg1.render(f5);
      this.Leg2.render(f5);
      this.Leg3.render(f5);
      this.Leg4.render(f5);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
      this.Shape6.render(f5);
      this.Shape7.render(f5);
      this.Shape8.render(f5);
      this.Shape9.render(f5);
      this.Shape10.render(f5);
      this.Shape11.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
