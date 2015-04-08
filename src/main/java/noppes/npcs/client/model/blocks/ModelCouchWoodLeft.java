package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCouchWoodLeft extends ModelBase {

   ModelRenderer Shape1 = new ModelRenderer(this, 0, 0);
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
   ModelRenderer Shape12;
   ModelRenderer Shape13;
   ModelRenderer Shape14;
   ModelRenderer Shape15;
   ModelRenderer Shape16;
   ModelRenderer Shape17;
   ModelRenderer Shape18;


   public ModelCouchWoodLeft() {
      this.Shape1.mirror = true;
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 10);
      this.Shape1.setRotationPoint(-8.0F, 18.0F, -4.0F);
      this.Shape2 = new ModelRenderer(this, 0, 0);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2);
      this.Shape2.setRotationPoint(7.0F, 21.0F, -6.0F);
      this.Shape3 = new ModelRenderer(this, 0, 0);
      this.Shape3.mirror = true;
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
      this.Shape3.setRotationPoint(-8.0F, 14.0F, 0.5F);
      this.Shape4 = new ModelRenderer(this, 0, 0);
      this.Shape4.mirror = true;
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 14, 2, 1);
      this.Shape4.setRotationPoint(-6.0F, 7.0F, 7.0F);
      this.Shape5 = new ModelRenderer(this, 0, 0);
      this.Shape5.mirror = true;
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 14, 2, 2);
      this.Shape5.setRotationPoint(-6.0F, 19.0F, 6.0F);
      this.Shape6 = new ModelRenderer(this, 0, 0);
      this.Shape6.mirror = true;
      this.Shape6.addBox(0.0F, 0.0F, 0.0F, 2, 1, 13);
      this.Shape6.setRotationPoint(-8.0F, 13.0F, -7.0F);
      this.Shape7 = new ModelRenderer(this, 0, 0);
      this.Shape7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 10);
      this.Shape7.setRotationPoint(-6.0F, 19.0F, -4.0F);
      this.Shape8 = new ModelRenderer(this, 0, 0);
      this.Shape8.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2);
      this.Shape8.setRotationPoint(7.0F, 21.0F, 6.0F);
      this.Shape9 = new ModelRenderer(this, 0, 0);
      this.Shape9.addBox(0.0F, 0.0F, 0.0F, 2, 1, 10);
      this.Shape9.setRotationPoint(5.0F, 19.0F, -4.0F);
      this.Shape10 = new ModelRenderer(this, 0, 0);
      this.Shape10.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
      this.Shape10.setRotationPoint(-6.0F, 9.0F, 7.0F);
      this.Shape11 = new ModelRenderer(this, 0, 0);
      this.Shape11.mirror = true;
      this.Shape11.addBox(0.0F, 0.0F, 0.0F, 14, 2, 2);
      this.Shape11.setRotationPoint(-6.0F, 19.0F, -6.0F);
      this.Shape12 = new ModelRenderer(this, 0, 0);
      this.Shape12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 10);
      this.Shape12.setRotationPoint(-3.0F, 19.0F, -4.0F);
      this.Shape13 = new ModelRenderer(this, 0, 0);
      this.Shape13.addBox(0.0F, 0.0F, 0.0F, 2, 1, 10);
      this.Shape13.setRotationPoint(1.0F, 19.0F, -4.0F);
      this.Shape14 = new ModelRenderer(this, 0, 0);
      this.Shape14.addBox(0.0F, 0.0F, 0.0F, 2, 10, 1);
      this.Shape14.setRotationPoint(-3.0F, 9.0F, 7.0F);
      this.Shape15 = new ModelRenderer(this, 0, 0);
      this.Shape15.addBox(0.0F, 0.0F, 0.0F, 2, 10, 1);
      this.Shape15.setRotationPoint(1.0F, 9.0F, 7.0F);
      this.Shape16 = new ModelRenderer(this, 0, 0);
      this.Shape16.addBox(0.0F, 0.0F, 0.0F, 2, 10, 1);
      this.Shape16.setRotationPoint(5.0F, 9.0F, 7.0F);
      this.Shape17 = new ModelRenderer(this, 0, 0);
      this.Shape17.mirror = true;
      this.Shape17.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2);
      this.Shape17.setRotationPoint(-8.0F, 14.0F, -6.0F);
      this.Shape18 = new ModelRenderer(this, 0, 0);
      this.Shape18.mirror = true;
      this.Shape18.addBox(0.0F, 0.0F, 0.0F, 2, 18, 2);
      this.Shape18.setRotationPoint(-8.0F, 6.0F, 6.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
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
      this.Shape12.render(f5);
      this.Shape13.render(f5);
      this.Shape14.render(f5);
      this.Shape15.render(f5);
      this.Shape16.render(f5);
      this.Shape17.render(f5);
      this.Shape18.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
