package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSign extends ModelBase {

   public ModelRenderer Sign = new ModelRenderer(this, 0, 22);
   ModelRenderer Chain2;
   ModelRenderer Bar;
   ModelRenderer Chain1;


   public ModelSign() {
      this.Sign.addBox(0.0F, 0.0F, 0.0F, 14, 9, 1);
      this.Sign.setRotationPoint(-7.0F, 12.0F, -0.5F);
      this.setRotation(this.Sign, 0.0174533F, 0.0F, 0.0F);
      this.Chain2 = new ModelRenderer(this, 0, 0);
      this.Chain2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2);
      this.Chain2.setRotationPoint(5.0F, 11.0F, -1.0F);
      this.Bar = new ModelRenderer(this, 0, 0);
      this.Bar.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
      this.Bar.setRotationPoint(-8.0F, 10.0F, -0.5F);
      this.Chain1 = new ModelRenderer(this, 0, 0);
      this.Chain1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2);
      this.Chain1.setRotationPoint(-6.0F, 11.0F, -1.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Chain2.render(f5);
      this.Bar.render(f5);
      this.Chain1.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
