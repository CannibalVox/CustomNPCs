package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTallLampTop extends ModelBase {

   ModelRenderer LampShade1 = new ModelRenderer(this, 0, 0);
   ModelRenderer LampShade3;
   ModelRenderer LampShade2;
   ModelRenderer LampShade4;


   public ModelTallLampTop() {
      this.LampShade1.addBox(-0.5F, -6.0F, -6.0F, 1, 12, 12);
      this.LampShade1.setRotationPoint(6.0F, -1.0F, 0.0F);
      this.LampShade3 = new ModelRenderer(this, 0, 0);
      this.LampShade3.addBox(-6.0F, -6.0F, -0.5F, 12, 12, 1);
      this.LampShade3.setRotationPoint(0.0F, -1.0F, -6.0F);
      this.LampShade2 = new ModelRenderer(this, 0, 0);
      this.LampShade2.addBox(-0.5F, -6.0F, -6.0F, 1, 12, 12);
      this.LampShade2.setRotationPoint(-6.0F, -1.0F, 0.0F);
      this.LampShade4 = new ModelRenderer(this, 0, 0);
      this.LampShade4.addBox(-6.0F, -6.0F, -0.5F, 12, 12, 1);
      this.LampShade4.setRotationPoint(0.0F, -1.0F, 6.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.LampShade1.render(f5);
      this.LampShade3.render(f5);
      this.LampShade2.render(f5);
      this.LampShade4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
