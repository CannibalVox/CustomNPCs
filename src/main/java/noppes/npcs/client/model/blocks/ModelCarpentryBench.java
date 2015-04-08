package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCarpentryBench extends ModelBase {

   ModelRenderer Leg1;
   ModelRenderer Leg2;
   ModelRenderer Leg3;
   ModelRenderer Leg4;
   ModelRenderer Bottom_plate;
   ModelRenderer Desktop;
   ModelRenderer Backboard;
   ModelRenderer Vice_Jaw1;
   ModelRenderer Vice_Jaw2;
   ModelRenderer Vice_Base1;
   ModelRenderer Vice_Base2;
   ModelRenderer Vice_Crank;
   ModelRenderer Vice_Screw;
   ModelRenderer Blueprint;


   public ModelCarpentryBench() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Leg1 = new ModelRenderer(this, 0, 0);
      this.Leg1.addBox(0.0F, 0.0F, 0.0F, 2, 14, 2);
      this.Leg1.setRotationPoint(6.0F, 10.0F, 5.0F);
      this.Leg2 = new ModelRenderer(this, 0, 0);
      this.Leg2.addBox(0.0F, 0.0F, 0.0F, 2, 14, 2);
      this.Leg2.setRotationPoint(6.0F, 10.0F, -5.0F);
      this.Leg3 = new ModelRenderer(this, 0, 0);
      this.Leg3.addBox(0.0F, 0.0F, 0.0F, 2, 14, 2);
      this.Leg3.setRotationPoint(-8.0F, 10.0F, 5.0F);
      this.Leg4 = new ModelRenderer(this, 0, 0);
      this.Leg4.addBox(0.0F, 0.0F, 0.0F, 2, 14, 2);
      this.Leg4.setRotationPoint(-8.0F, 10.0F, -5.0F);
      this.Bottom_plate = new ModelRenderer(this, 0, 24);
      this.Bottom_plate.addBox(0.0F, 0.0F, 0.0F, 14, 1, 10);
      this.Bottom_plate.setRotationPoint(-7.0F, 21.0F, -4.0F);
      this.Bottom_plate.setTextureSize(130, 64);
      this.Desktop = new ModelRenderer(this, 0, 3);
      this.Desktop.addBox(0.0F, 0.0F, 0.0F, 18, 2, 13);
      this.Desktop.setRotationPoint(-9.0F, 9.0F, -6.0F);
      this.Backboard = new ModelRenderer(this, 0, 18);
      this.Backboard.addBox(-1.0F, 0.0F, 0.0F, 18, 5, 1);
      this.Backboard.setRotationPoint(-8.0F, 7.0F, 7.0F);
      this.Vice_Jaw1 = new ModelRenderer(this, 54, 18);
      this.Vice_Jaw1.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Vice_Jaw1.setRotationPoint(3.0F, 6.0F, -8.0F);
      this.Vice_Jaw2 = new ModelRenderer(this, 54, 21);
      this.Vice_Jaw2.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Vice_Jaw2.setRotationPoint(3.0F, 6.0F, -6.0F);
      this.Vice_Base1 = new ModelRenderer(this, 38, 30);
      this.Vice_Base1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.Vice_Base1.setRotationPoint(3.0F, 8.0F, -5.0F);
      this.Vice_Base2 = new ModelRenderer(this, 38, 25);
      this.Vice_Base2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2);
      this.Vice_Base2.setRotationPoint(4.0F, 7.0F, -5.0F);
      this.Vice_Crank = new ModelRenderer(this, 54, 24);
      this.Vice_Crank.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
      this.Vice_Crank.setRotationPoint(6.0F, 6.0F, -9.0F);
      this.Vice_Screw = new ModelRenderer(this, 44, 25);
      this.Vice_Screw.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4);
      this.Vice_Screw.setRotationPoint(4.0F, 8.0F, -8.0F);
      this.Blueprint = new ModelRenderer(this, 31, 18);
      this.Blueprint.addBox(0.0F, 0.0F, 0.0F, 8, 0, 7);
      this.Blueprint.setRotationPoint(0.0F, 9.0F, 1.0F);
      this.setRotation(this.Blueprint, 0.3271718F, 0.1487144F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Leg1.render(f5);
      this.Leg2.render(f5);
      this.Leg3.render(f5);
      this.Leg4.render(f5);
      this.Bottom_plate.render(f5);
      this.Desktop.render(f5);
      this.Backboard.render(f5);
      this.Vice_Jaw1.render(f5);
      this.Vice_Jaw2.render(f5);
      this.Vice_Base1.render(f5);
      this.Vice_Base2.render(f5);
      this.Vice_Crank.render(f5);
      this.Vice_Screw.render(f5);
      this.Blueprint.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
