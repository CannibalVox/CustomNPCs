package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWallBanner extends ModelBase {

   ModelRenderer MiddleStick = new ModelRenderer(this, 56, 0);
   ModelRenderer StickDecoration;
   ModelRenderer TopDecoration;
   ModelRenderer FlagPole1;
   ModelRenderer FlagPole2;


   public ModelWallBanner() {
      this.MiddleStick.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2);
      this.MiddleStick.setRotationPoint(0.0F, -9.0F, 6.5F);
      this.StickDecoration = new ModelRenderer(this, 11, 12);
      this.StickDecoration.addBox(0.0F, 0.0F, 0.0F, 16, 3, 3);
      this.StickDecoration.setRotationPoint(-8.0F, -7.5F, 5.0F);
      this.TopDecoration = new ModelRenderer(this, 45, 19);
      this.TopDecoration.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.TopDecoration.setRotationPoint(-0.5F, -10.0F, 6.0F);
      this.FlagPole1 = new ModelRenderer(this, 45, 19);
      this.FlagPole1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.FlagPole1.setRotationPoint(-7.0F, -6.5F, 4.0F);
      this.FlagPole2 = new ModelRenderer(this, 45, 19);
      this.FlagPole2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.FlagPole2.setRotationPoint(6.0F, -6.5F, 4.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.MiddleStick.render(f5);
      this.StickDecoration.render(f5);
      this.TopDecoration.render(f5);
      this.FlagPole1.render(f5);
      this.FlagPole2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
