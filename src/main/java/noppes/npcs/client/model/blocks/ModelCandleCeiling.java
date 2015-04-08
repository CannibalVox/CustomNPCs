package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCandleCeiling extends ModelBase {

   ModelRenderer Wax1 = new ModelRenderer(this, 16, 0);
   ModelRenderer Wax2;
   ModelRenderer Wax3;
   ModelRenderer Wax4;
   ModelRenderer TippyTop1;
   ModelRenderer TippyTop2;
   ModelRenderer Middle;
   ModelRenderer BottomBar1;
   ModelRenderer Rod1;
   ModelRenderer Rod2;
   ModelRenderer Rod3;
   ModelRenderer Rod4;
   ModelRenderer Base1;
   ModelRenderer Base2;
   ModelRenderer Base3;
   ModelRenderer Base4;
   ModelRenderer BottomBar3;
   ModelRenderer BottomBar2;
   ModelRenderer BottomBar4;


   public ModelCandleCeiling() {
      this.Wax1.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Wax1.setRotationPoint(-1.0F, 15.5F, 5.0F);
      this.Wax2 = new ModelRenderer(this, 16, 0);
      this.Wax2.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Wax2.setRotationPoint(7.0F, 15.5F, 1.0F);
      this.setRotation(this.Wax2, 0.0F, 3.141593F, 0.0F);
      this.Wax3 = new ModelRenderer(this, 16, 0);
      this.Wax3.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Wax3.setRotationPoint(-7.0F, 15.5F, -1.0F);
      this.Wax4 = new ModelRenderer(this, 16, 0);
      this.Wax4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Wax4.setRotationPoint(1.0F, 15.5F, -5.0F);
      this.setRotation(this.Wax4, 0.0F, 3.141593F, 0.0F);
      this.TippyTop1 = new ModelRenderer(this, 0, 0);
      this.TippyTop1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.TippyTop1.setRotationPoint(-2.8F, 7.5F, 0.0F);
      this.setRotation(this.TippyTop1, 0.0F, 0.7853982F, 0.0F);
      this.TippyTop2 = new ModelRenderer(this, 0, 0);
      this.TippyTop2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.TippyTop2.setRotationPoint(-2.1F, 8.0F, 0.0F);
      this.setRotation(this.TippyTop2, 0.0F, 0.7853982F, 0.0F);
      this.Middle = new ModelRenderer(this, 0, 0);
      this.Middle.addBox(0.0F, 0.0F, 0.0F, 1, 13, 1);
      this.Middle.setRotationPoint(-0.5F, 9.0F, -0.5F);
      this.BottomBar1 = new ModelRenderer(this, 0, 4);
      this.BottomBar1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5);
      this.BottomBar1.setRotationPoint(-0.5F, 21.0F, 0.5F);
      this.Rod1 = new ModelRenderer(this, 0, 0);
      this.Rod1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Rod1.setRotationPoint(-0.5F, 20.0F, 5.5F);
      this.Rod2 = new ModelRenderer(this, 0, 0);
      this.Rod2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Rod2.setRotationPoint(5.5F, 20.0F, -0.5F);
      this.Rod3 = new ModelRenderer(this, 0, 0);
      this.Rod3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Rod3.setRotationPoint(-6.5F, 20.0F, -0.5F);
      this.Rod4 = new ModelRenderer(this, 0, 0);
      this.Rod4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Rod4.setRotationPoint(-0.5F, 20.0F, -6.5F);
      this.Base1 = new ModelRenderer(this, 0, 0);
      this.Base1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Base1.setRotationPoint(-2.0F, 19.0F, 4.0F);
      this.Base2 = new ModelRenderer(this, 0, 0);
      this.Base2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Base2.setRotationPoint(4.0F, 19.0F, -2.0F);
      this.Base3 = new ModelRenderer(this, 0, 0);
      this.Base3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Base3.setRotationPoint(-8.0F, 19.0F, -2.0F);
      this.Base4 = new ModelRenderer(this, 0, 0);
      this.Base4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Base4.setRotationPoint(-2.0F, 19.0F, -8.0F);
      this.BottomBar3 = new ModelRenderer(this, 0, 4);
      this.BottomBar3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5);
      this.BottomBar3.setRotationPoint(-0.5F, 21.0F, -0.5F);
      this.setRotation(this.BottomBar3, 0.0F, -1.570796F, 0.0F);
      this.BottomBar2 = new ModelRenderer(this, 0, 4);
      this.BottomBar2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5);
      this.BottomBar2.setRotationPoint(0.5F, 21.0F, 0.5F);
      this.setRotation(this.BottomBar2, 0.0F, 1.570796F, 0.0F);
      this.BottomBar4 = new ModelRenderer(this, 0, 4);
      this.BottomBar4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5);
      this.BottomBar4.setRotationPoint(0.4F, 21.0F, -0.5F);
      this.setRotation(this.BottomBar4, 0.0F, 3.141593F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Wax1.render(f5);
      this.Wax2.render(f5);
      this.Wax3.render(f5);
      this.Wax4.render(f5);
      this.TippyTop1.render(f5);
      this.TippyTop2.render(f5);
      this.Middle.render(f5);
      this.BottomBar1.render(f5);
      this.Rod1.render(f5);
      this.Rod2.render(f5);
      this.Rod3.render(f5);
      this.Rod4.render(f5);
      this.Base1.render(f5);
      this.Base2.render(f5);
      this.Base3.render(f5);
      this.Base4.render(f5);
      this.BottomBar3.render(f5);
      this.BottomBar2.render(f5);
      this.BottomBar4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
