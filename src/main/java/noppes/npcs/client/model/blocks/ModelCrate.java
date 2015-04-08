package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrate extends ModelBase {

   ModelRenderer sticky1 = new ModelRenderer(this, 0, 0);
   ModelRenderer sticky2;
   ModelRenderer sticky3;
   ModelRenderer sticky4;
   ModelRenderer core;
   ModelRenderer sticky1top;
   ModelRenderer sticky2top;
   ModelRenderer sticky3top;
   ModelRenderer sticky4top;
   ModelRenderer sidestick2;
   ModelRenderer sidestick3;
   ModelRenderer sidestick1;
   ModelRenderer sidestick4;
   ModelRenderer sidestuff2;
   ModelRenderer sidestuff1;
   ModelRenderer sidestuff3;
   ModelRenderer sidestuff4;
   ModelRenderer Shape1;
   ModelRenderer Shape2;


   public ModelCrate() {
      this.sticky1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12);
      this.sticky1.setRotationPoint(6.0F, 22.0F, -6.0F);
      this.sticky2 = new ModelRenderer(this, 0, 0);
      this.sticky2.addBox(0.0F, 0.0F, 0.0F, 12, 2, 2);
      this.sticky2.setRotationPoint(-6.0F, 22.0F, -8.0F);
      this.sticky3 = new ModelRenderer(this, 0, 0);
      this.sticky3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12);
      this.sticky3.setRotationPoint(-8.0F, 22.0F, -6.0F);
      this.sticky4 = new ModelRenderer(this, 32, 0);
      this.sticky4.addBox(0.0F, 0.0F, 0.0F, 12, 2, 2);
      this.sticky4.setRotationPoint(-6.0F, 22.0F, 6.0F);
      this.core = new ModelRenderer(this, 0, 0);
      this.core.addBox(-8.0F, 0.0F, -8.0F, 16, 16, 16, -1.0F);
      this.core.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.sticky1top = new ModelRenderer(this, 0, 0);
      this.sticky1top.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12);
      this.sticky1top.setRotationPoint(6.0F, 8.0F, -6.0F);
      this.sticky2top = new ModelRenderer(this, 0, 0);
      this.sticky2top.addBox(0.0F, 0.0F, 0.0F, 12, 2, 2);
      this.sticky2top.setRotationPoint(-6.0F, 8.0F, 6.0F);
      this.sticky3top = new ModelRenderer(this, 0, 0);
      this.sticky3top.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12);
      this.sticky3top.setRotationPoint(-8.0F, 8.0F, -6.0F);
      this.sticky4top = new ModelRenderer(this, 0, 0);
      this.sticky4top.addBox(0.0F, 0.0F, 0.0F, 12, 2, 2);
      this.sticky4top.setRotationPoint(-6.0F, 8.0F, -8.0F);
      this.sidestick1 = new ModelRenderer(this, 0, 0);
      this.sidestick1.addBox(0.0F, 0.0F, 0.0F, 2, 16, 2);
      this.sidestick1.setRotationPoint(-8.0F, 8.0F, 6.0F);
      this.sidestick2 = new ModelRenderer(this, 0, 0);
      this.sidestick2.addBox(0.0F, 0.0F, 0.0F, 2, 16, 2);
      this.sidestick2.setRotationPoint(6.0F, 8.0F, 6.0F);
      this.sidestick3 = new ModelRenderer(this, 0, 0);
      this.sidestick3.addBox(0.0F, 0.0F, 0.0F, 2, 16, 2);
      this.sidestick3.setRotationPoint(-8.0F, 8.0F, -8.0F);
      this.sidestick4 = new ModelRenderer(this, 0, 0);
      this.sidestick4.addBox(0.0F, 0.0F, 0.0F, 2, 16, 2);
      this.sidestick4.setRotationPoint(6.0F, 8.0F, -8.0F);
      this.sidestuff1 = new ModelRenderer(this, 0, 0);
      this.sidestuff1.addBox(0.0F, 1.0F, 0.0F, 1, 18, 2);
      this.sidestuff1.setRotationPoint(6.0F, 8.5F, -6.5F);
      this.setRotation(this.sidestuff1, -0.7853982F, 1.570796F, 0.0F);
      this.sidestuff2 = new ModelRenderer(this, 0, 0);
      this.sidestuff2.addBox(0.0F, -1.0F, 0.0F, 1, 18, 2);
      this.sidestuff2.setRotationPoint(-7.5F, 9.5F, 5.0F);
      this.setRotation(this.sidestuff2, -0.7853982F, 0.0F, 0.0F);
      this.sidestuff3 = new ModelRenderer(this, 0, 0);
      this.sidestuff3.addBox(0.0F, 1.0F, 0.0F, 1, 18, 2);
      this.sidestuff3.setRotationPoint(7.5F, 8.5F, -6.0F);
      this.setRotation(this.sidestuff3, -0.7853982F, 3.141593F, 0.0F);
      this.sidestuff4 = new ModelRenderer(this, 0, 0);
      this.sidestuff4.addBox(0.0F, 1.0F, 0.0F, 1, 18, 2);
      this.sidestuff4.setRotationPoint(-6.0F, 8.5F, 6.5F);
      this.setRotation(this.sidestuff4, -0.7853982F, -1.570796F, 0.0F);
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 18, 1, 2);
      this.Shape1.setRotationPoint(-5.5F, 22.5F, -7.0F);
      this.setRotation(this.Shape1, 0.0F, -0.7853982F, 0.0F);
      this.Shape2 = new ModelRenderer(this, 0, 0);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 18, 1, 2);
      this.Shape2.setRotationPoint(-5.5F, 8.5F, -7.0F);
      this.setRotation(this.Shape2, 0.0F, -0.7853982F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.sticky1.render(f5);
      this.sticky2.render(f5);
      this.sticky3.render(f5);
      this.sticky4.render(f5);
      this.core.render(f5);
      this.sticky1top.render(f5);
      this.sticky2top.render(f5);
      this.sticky3top.render(f5);
      this.sticky4top.render(f5);
      this.sidestick1.render(f5);
      this.sidestick2.render(f5);
      this.sidestick3.render(f5);
      this.sidestick4.render(f5);
      this.sidestuff1.render(f5);
      this.sidestuff2.render(f5);
      this.sidestuff3.render(f5);
      this.sidestuff4.render(f5);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
