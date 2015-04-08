package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBanner extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 3, 1);
   ModelRenderer MiddleStick;
   ModelRenderer StickDecoration;
   ModelRenderer TopDecoration;
   ModelRenderer FlagPole1;
   ModelRenderer FlagPole2;
   ModelRenderer BaseDeco1;
   ModelRenderer BaseDeco2;
   ModelRenderer BaseDeco3;
   ModelRenderer BaseDeco4;


   public ModelBanner() {
      this.Base.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 14);
      this.Base.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.MiddleStick = new ModelRenderer(this, 12, 2);
      this.MiddleStick.addBox(-1.0F, 0.0F, -1.0F, 2, 32, 2);
      this.MiddleStick.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.StickDecoration = new ModelRenderer(this, 11, 12);
      this.StickDecoration.addBox(0.0F, 0.0F, 0.0F, 16, 3, 3);
      this.StickDecoration.setRotationPoint(-8.0F, -7.5F, -1.5F);
      this.TopDecoration = new ModelRenderer(this, 45, 19);
      this.TopDecoration.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.TopDecoration.setRotationPoint(-0.5F, -10.0F, -0.5F);
      this.FlagPole1 = new ModelRenderer(this, 45, 19);
      this.FlagPole1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.FlagPole1.setRotationPoint(-7.0F, -6.5F, -2.5F);
      this.FlagPole2 = new ModelRenderer(this, 45, 19);
      this.FlagPole2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.FlagPole2.setRotationPoint(6.0F, -6.5F, -2.5F);
      this.BaseDeco1 = new ModelRenderer(this, 1, 14);
      this.BaseDeco1.addBox(0.0F, 0.0F, 0.0F, 12, 1, 1);
      this.BaseDeco1.setRotationPoint(-6.0F, 23.0F, -8.0F);
      this.BaseDeco2 = new ModelRenderer(this, 1, 14);
      this.BaseDeco2.addBox(0.0F, 0.0F, 0.0F, 12, 1, 1);
      this.BaseDeco2.setRotationPoint(-6.0F, 23.0F, 7.0F);
      this.BaseDeco3 = new ModelRenderer(this, 2, 2);
      this.BaseDeco3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 12);
      this.BaseDeco3.setRotationPoint(-8.0F, 23.0F, -6.0F);
      this.BaseDeco4 = new ModelRenderer(this, 2, 2);
      this.BaseDeco4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 12);
      this.BaseDeco4.setRotationPoint(7.0F, 23.0F, -6.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.MiddleStick.render(f5);
      this.StickDecoration.render(f5);
      this.TopDecoration.render(f5);
      this.FlagPole1.render(f5);
      this.FlagPole2.render(f5);
      this.BaseDeco1.render(f5);
      this.BaseDeco2.render(f5);
      this.BaseDeco3.render(f5);
      this.BaseDeco4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
