package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCouchCorner extends ModelBase {

   ModelRenderer Leg1 = new ModelRenderer(this, 0, 0);
   ModelRenderer Leg2;
   ModelRenderer Leg3;
   ModelRenderer Leg4;
   ModelRenderer Leg5;
   ModelRenderer Back;
   ModelRenderer Back2;
   ModelRenderer Bottom;
   ModelRenderer Bottom2;


   public ModelCouchCorner() {
      this.Leg1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
      this.Leg1.setRotationPoint(7.0F, 23.0F, 6.0F);
      this.Leg2 = new ModelRenderer(this, 0, 0);
      this.Leg2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Leg2.setRotationPoint(4.0F, 23.0F, -8.0F);
      this.Leg3 = new ModelRenderer(this, 0, 0);
      this.Leg3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Leg3.setRotationPoint(-8.0F, 23.0F, -8.0F);
      this.Leg4 = new ModelRenderer(this, 0, 0);
      this.Leg4.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Leg4.setRotationPoint(-8.0F, 23.0F, 6.0F);
      this.Leg5 = new ModelRenderer(this, 0, 0);
      this.Leg5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
      this.Leg5.setRotationPoint(7.0F, 23.0F, -6.0F);
      this.Back = new ModelRenderer(this, 1, 1);
      this.Back.addBox(0.0F, 0.0F, 0.0F, 1, 15, 15);
      this.Back.setRotationPoint(-8.0F, 6.0F, -8.0F);
      this.Back2 = new ModelRenderer(this, 14, 15);
      this.Back2.addBox(0.0F, 0.0F, 0.0F, 16, 15, 1);
      this.Back2.setRotationPoint(-8.0F, 6.0F, 7.0F);
      this.Bottom = new ModelRenderer(this, 4, 0);
      this.Bottom.addBox(0.0F, 0.0F, 0.0F, 16, 2, 14);
      this.Bottom.setRotationPoint(-8.0F, 21.0F, -6.0F);
      this.Bottom2 = new ModelRenderer(this, 0, 0);
      this.Bottom2.addBox(0.0F, 0.0F, 0.0F, 14, 2, 2);
      this.Bottom2.setRotationPoint(-8.0F, 21.0F, -8.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Leg1.render(f5);
      this.Leg2.render(f5);
      this.Leg3.render(f5);
      this.Leg4.render(f5);
      this.Leg5.render(f5);
      this.Back.render(f5);
      this.Back2.render(f5);
      this.Bottom.render(f5);
      this.Bottom2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
