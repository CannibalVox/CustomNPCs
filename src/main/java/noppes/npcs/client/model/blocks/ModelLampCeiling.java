package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLampCeiling extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 0, 6);
   ModelRenderer Top1;
   ModelRenderer Top2;
   ModelRenderer Top3;
   ModelRenderer Chain8;
   ModelRenderer Chain1;
   ModelRenderer Chain2;
   ModelRenderer Chain3;
   ModelRenderer Chain4;
   ModelRenderer Chain5;
   ModelRenderer Chain6;
   ModelRenderer Chain7;
   ModelRenderer TippyTop1;
   ModelRenderer TippyTop2;
   ModelRenderer Shape3;
   ModelRenderer Shape1;
   ModelRenderer Shape2;


   public ModelLampCeiling() {
      this.Base.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4);
      this.Base.setRotationPoint(-2.0F, 17.0F, -2.0F);
      this.Top1 = new ModelRenderer(this, 0, 0);
      this.Top1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5);
      this.Top1.setRotationPoint(-2.5F, 17.0F, -2.5F);
      this.Top2 = new ModelRenderer(this, 0, 0);
      this.Top2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Top2.setRotationPoint(-2.0F, 16.5F, -2.0F);
      this.Top3 = new ModelRenderer(this, 0, 0);
      this.Top3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.Top3.setRotationPoint(-1.5F, 16.0F, -1.5F);
      this.Chain8 = new ModelRenderer(this, 0, 0);
      this.Chain8.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Chain8.setRotationPoint(-0.5F, 14.0F, -1.5F);
      this.Chain1 = new ModelRenderer(this, 0, 0);
      this.Chain1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Chain1.setRotationPoint(0.5F, 8.0F, -0.5F);
      this.Chain2 = new ModelRenderer(this, 0, 0);
      this.Chain2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Chain2.setRotationPoint(-1.5F, 8.0F, -0.5F);
      this.Chain3 = new ModelRenderer(this, 0, 0);
      this.Chain3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Chain3.setRotationPoint(-0.5F, 10.0F, 0.5F);
      this.Chain4 = new ModelRenderer(this, 0, 0);
      this.Chain4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Chain4.setRotationPoint(-0.5F, 10.0F, -1.5F);
      this.Chain5 = new ModelRenderer(this, 0, 0);
      this.Chain5.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Chain5.setRotationPoint(-1.5F, 12.0F, -0.5F);
      this.Chain6 = new ModelRenderer(this, 0, 0);
      this.Chain6.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
      this.Chain6.setRotationPoint(0.5F, 12.0F, -0.5F);
      this.Chain7 = new ModelRenderer(this, 0, 0);
      this.Chain7.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Chain7.setRotationPoint(-0.5F, 14.0F, 0.5F);
      this.TippyTop1 = new ModelRenderer(this, 0, 0);
      this.TippyTop1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.TippyTop1.setRotationPoint(-2.8F, 8.0F, 0.0F);
      this.setRotation(this.TippyTop1, 0.0F, 0.7853982F, 0.0F);
      this.TippyTop2 = new ModelRenderer(this, 0, 0);
      this.TippyTop2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.TippyTop2.setRotationPoint(-2.1F, 8.5F, 0.0F);
      this.setRotation(this.TippyTop2, 0.0F, 0.7853982F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 0, 0);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape3.setRotationPoint(-0.5F, 14.0F, -0.5F);
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape1.setRotationPoint(-0.5F, 10.0F, -0.5F);
      this.Shape2 = new ModelRenderer(this, 0, 0);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape2.setRotationPoint(-0.5F, 12.0F, -0.5F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Top1.render(f5);
      this.Top2.render(f5);
      this.Top3.render(f5);
      this.Chain8.render(f5);
      this.Chain1.render(f5);
      this.Chain2.render(f5);
      this.Chain3.render(f5);
      this.Chain4.render(f5);
      this.Chain5.render(f5);
      this.Chain6.render(f5);
      this.Chain7.render(f5);
      this.TippyTop1.render(f5);
      this.TippyTop2.render(f5);
      this.Shape3.render(f5);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
