package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWeaponRack extends ModelBase {

   ModelRenderer Support_1 = new ModelRenderer(this, 0, 0);
   ModelRenderer Support_2;
   ModelRenderer Support_3;
   ModelRenderer Support_4;
   ModelRenderer Support_5;
   ModelRenderer Support_6;
   ModelRenderer Rung_1_A;
   ModelRenderer Rung_1_B;
   ModelRenderer Rung_1_C;
   ModelRenderer Rung_2_A;
   ModelRenderer Rung_2_B;
   ModelRenderer Rung_2_C;
   ModelRenderer Rung_3_A;
   ModelRenderer Rung_3_B;
   ModelRenderer Rung_3_C;
   ModelRenderer Cross_Top_1;
   ModelRenderer Cross_Top_2;
   ModelRenderer Bottom_Support_1;
   ModelRenderer Bottom_Support_2;
   ModelRenderer Middle_Support_1;


   public ModelWeaponRack() {
      this.Support_1.addBox(0.0F, 0.0F, 0.0F, 1, 30, 2);
      this.Support_1.setRotationPoint(-5.0F, -6.9F, 5.0F);
      this.Support_2 = new ModelRenderer(this, 0, 0);
      this.Support_2.addBox(0.0F, 0.0F, 0.0F, 1, 30, 2);
      this.Support_2.setRotationPoint(-8.01F, -6.9F, 5.0F);
      this.Support_3 = new ModelRenderer(this, 0, 0);
      this.Support_3.addBox(0.0F, 0.0F, 0.0F, 1, 30, 2);
      this.Support_3.setRotationPoint(-2.0F, -6.9F, 5.0F);
      this.Support_4 = new ModelRenderer(this, 0, 0);
      this.Support_4.addBox(0.0F, 0.0F, 0.0F, 1, 30, 2);
      this.Support_4.setRotationPoint(1.0F, -6.9F, 5.0F);
      this.Support_5 = new ModelRenderer(this, 0, 0);
      this.Support_5.addBox(0.0F, 0.0F, 0.0F, 1, 30, 2);
      this.Support_5.setRotationPoint(4.0F, -6.9F, 5.0F);
      this.Support_6 = new ModelRenderer(this, 0, 0);
      this.Support_6.addBox(0.0F, 0.0F, 0.0F, 1, 30, 2);
      this.Support_6.setRotationPoint(7.01F, -6.9F, 5.0F);
      this.Rung_1_A = new ModelRenderer(this, 0, 22);
      this.Rung_1_A.addBox(0.0F, 0.0F, 0.0F, 4, 1, 0);
      this.Rung_1_A.setRotationPoint(-8.0F, 11.0F, 3.99F);
      this.Rung_1_B = new ModelRenderer(this, 0, 24);
      this.Rung_1_B.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Rung_1_B.setRotationPoint(-8.0F, 11.0F, 4.0F);
      this.Rung_1_C = new ModelRenderer(this, 0, 24);
      this.Rung_1_C.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Rung_1_C.setRotationPoint(-5.0F, 11.0F, 4.0F);
      this.Rung_2_A = new ModelRenderer(this, 0, 22);
      this.Rung_2_A.addBox(0.0F, 0.0F, 0.0F, 4, 1, 0);
      this.Rung_2_A.setRotationPoint(-2.0F, 11.0F, 3.99F);
      this.Rung_2_B = new ModelRenderer(this, 0, 24);
      this.Rung_2_B.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Rung_2_B.setRotationPoint(-2.0F, 11.0F, 4.0F);
      this.Rung_2_C = new ModelRenderer(this, 0, 24);
      this.Rung_2_C.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Rung_2_C.setRotationPoint(1.0F, 11.0F, 4.0F);
      this.Rung_3_A = new ModelRenderer(this, 0, 22);
      this.Rung_3_A.addBox(0.0F, 0.0F, 0.0F, 4, 1, 0);
      this.Rung_3_A.setRotationPoint(4.0F, 11.0F, 3.99F);
      this.Rung_3_B = new ModelRenderer(this, 0, 24);
      this.Rung_3_B.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Rung_3_B.setRotationPoint(4.0F, 11.0F, 4.0F);
      this.Rung_3_C = new ModelRenderer(this, 0, 24);
      this.Rung_3_C.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Rung_3_C.setRotationPoint(7.0F, 11.0F, 4.0F);
      this.Cross_Top_1 = new ModelRenderer(this, 6, 0);
      this.Cross_Top_1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 1);
      this.Cross_Top_1.setRotationPoint(-8.0F, -8.6F, 6.0F);
      this.setRotation(this.Cross_Top_1, -0.5235988F, 0.0F, 0.0F);
      this.Cross_Top_2 = new ModelRenderer(this, 6, 0);
      this.Cross_Top_2.addBox(0.0F, 0.0F, 0.0F, 16, 2, 1);
      this.Cross_Top_2.setRotationPoint(-8.0F, -8.6F, 6.01F);
      this.Bottom_Support_1 = new ModelRenderer(this, 6, 0);
      this.Bottom_Support_1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 1);
      this.Bottom_Support_1.setRotationPoint(-8.0F, 23.0F, 6.0F);
      this.setRotation(this.Bottom_Support_1, -1.570796F, 0.0F, 0.0F);
      this.Bottom_Support_2 = new ModelRenderer(this, 6, 0);
      this.Bottom_Support_2.addBox(0.0F, 0.0F, 0.0F, 16, 2, 1);
      this.Bottom_Support_2.setRotationPoint(-8.0F, 23.0F, 8.0F);
      this.setRotation(this.Bottom_Support_2, -1.570796F, 0.0F, 0.0F);
      this.Middle_Support_1 = new ModelRenderer(this, 6, 3);
      this.Middle_Support_1.addBox(0.0F, 0.0F, 0.0F, 16, 1, 3);
      this.Middle_Support_1.setRotationPoint(-8.0F, 10.0F, 7.01F);
      this.setRotation(this.Middle_Support_1, -1.570796F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Support_1.render(f5);
      this.Support_2.render(f5);
      this.Support_3.render(f5);
      this.Support_4.render(f5);
      this.Support_5.render(f5);
      this.Support_6.render(f5);
      this.Rung_1_A.render(f5);
      this.Rung_1_B.render(f5);
      this.Rung_1_C.render(f5);
      this.Rung_2_A.render(f5);
      this.Rung_2_B.render(f5);
      this.Rung_2_C.render(f5);
      this.Rung_3_A.render(f5);
      this.Rung_3_B.render(f5);
      this.Rung_3_C.render(f5);
      this.Cross_Top_1.render(f5);
      this.Cross_Top_2.render(f5);
      this.Bottom_Support_1.render(f5);
      this.Bottom_Support_2.render(f5);
      this.Middle_Support_1.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
