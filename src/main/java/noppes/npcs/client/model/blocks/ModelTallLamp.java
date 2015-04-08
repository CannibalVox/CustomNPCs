package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTallLamp extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 6, 2);
   ModelRenderer MiddleStick;
   ModelRenderer LampShadeStick1;
   ModelRenderer LampShadeStick2;
   ModelRenderer LampShadeStick3;
   ModelRenderer LampShadeStick4;


   public ModelTallLamp() {
      this.Base.addBox(-6.0F, 0.0F, -6.0F, 12, 1, 12);
      this.Base.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.MiddleStick = new ModelRenderer(this, 12, 2);
      this.MiddleStick.addBox(-1.0F, 0.0F, -1.0F, 2, 28, 2);
      this.MiddleStick.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.LampShadeStick1 = new ModelRenderer(this, 0, 30);
      this.LampShadeStick1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.LampShadeStick1.setRotationPoint(1.0F, -1.0F, -0.5F);
      this.LampShadeStick2 = new ModelRenderer(this, 0, 30);
      this.LampShadeStick2.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.LampShadeStick2.setRotationPoint(-0.5F, -1.0F, -1.0F);
      this.setRotation(this.LampShadeStick2, 0.0F, 1.570796F, 0.0F);
      this.LampShadeStick3 = new ModelRenderer(this, 0, 30);
      this.LampShadeStick3.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.LampShadeStick3.setRotationPoint(-1.0F, -1.0F, 0.5F);
      this.setRotation(this.LampShadeStick3, 0.0F, 3.141593F, 0.0F);
      this.LampShadeStick4 = new ModelRenderer(this, 0, 30);
      this.LampShadeStick4.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.LampShadeStick4.setRotationPoint(0.5F, -1.0F, 1.0F);
      this.setRotation(this.LampShadeStick4, 0.0F, -1.570796F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.MiddleStick.render(f5);
      this.LampShadeStick1.render(f5);
      this.LampShadeStick2.render(f5);
      this.LampShadeStick3.render(f5);
      this.LampShadeStick4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
