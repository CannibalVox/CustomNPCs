package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBarrel extends ModelBase {

   ModelRenderer Plank1 = new ModelRenderer(this, 10, 0);
   ModelRenderer Plank2;
   ModelRenderer Plank3;
   ModelRenderer Plank4;
   ModelRenderer Plank5;
   ModelRenderer Plank6;
   ModelRenderer Plank7;
   ModelRenderer Plank8;
   ModelRenderer Plank9;
   ModelRenderer Plank10;
   ModelRenderer Plank11;
   ModelRenderer Plank12;


   public ModelBarrel() {
      this.Plank1.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank1.setRotationPoint(0.0F, 7.01F, 0.0F);
      this.setRotation(this.Plank1, 0.0F, 0.0F, 1.570796F);
      this.Plank2 = new ModelRenderer(this, 10, 8);
      this.Plank2.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank2.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.setRotation(this.Plank2, 0.0F, 0.5235988F, 1.570796F);
      this.Plank3 = new ModelRenderer(this, 10, 0);
      this.Plank3.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank3.setRotationPoint(0.0F, 7.01F, 0.0F);
      this.setRotation(this.Plank3, 0.0F, 1.047198F, 1.570796F);
      this.Plank4 = new ModelRenderer(this, 10, 8);
      this.Plank4.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank4.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.setRotation(this.Plank4, 0.0F, 1.570796F, 1.570796F);
      this.Plank5 = new ModelRenderer(this, 10, 0);
      this.Plank5.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank5.setRotationPoint(0.0F, 7.01F, 0.0F);
      this.setRotation(this.Plank5, 0.0F, 2.094395F, 1.570796F);
      this.Plank6 = new ModelRenderer(this, 10, 8);
      this.Plank6.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank6.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.setRotation(this.Plank6, 0.0F, 2.617994F, 1.570796F);
      this.Plank7 = new ModelRenderer(this, 10, 0);
      this.Plank7.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank7.setRotationPoint(0.0F, 7.01F, 0.0F);
      this.setRotation(this.Plank7, 0.0F, 3.150901F, 1.570796F);
      this.Plank8 = new ModelRenderer(this, 10, 8);
      this.Plank8.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank8.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.setRotation(this.Plank8, 0.0F, -2.617994F, 1.570796F);
      this.Plank9 = new ModelRenderer(this, 10, 0);
      this.Plank9.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank9.setRotationPoint(0.0F, 7.01F, 0.0F);
      this.setRotation(this.Plank9, 0.0F, -2.094395F, 1.570796F);
      this.Plank10 = new ModelRenderer(this, 10, 8);
      this.Plank10.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank10.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.setRotation(this.Plank10, 0.0F, -1.570796F, 1.570796F);
      this.Plank11 = new ModelRenderer(this, 10, 0);
      this.Plank11.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank11.setRotationPoint(0.0F, 7.01F, 0.0F);
      this.setRotation(this.Plank11, 0.0F, -1.047198F, 1.570796F);
      this.Plank12 = new ModelRenderer(this, 10, 0);
      this.Plank12.addBox(0.0F, 6.5F, -2.0F, 17, 1, 4);
      this.Plank12.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.setRotation(this.Plank12, 0.0F, -0.5235988F, 1.570796F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Plank1.render(f5);
      this.Plank2.render(f5);
      this.Plank3.render(f5);
      this.Plank4.render(f5);
      this.Plank5.render(f5);
      this.Plank6.render(f5);
      this.Plank7.render(f5);
      this.Plank8.render(f5);
      this.Plank9.render(f5);
      this.Plank10.render(f5);
      this.Plank11.render(f5);
      this.Plank12.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = y;
      model.rotateAngleY = x;
      model.rotateAngleZ = z;
   }
}
