package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBarrelLit extends ModelBase {

   ModelRenderer Top = new ModelRenderer(this, 0, 0);
   ModelRenderer Bottom;


   public ModelBarrelLit() {
      this.Top.addBox(0.0F, 0.0F, 0.0F, 16, 0, 16);
      this.Top.setRotationPoint(-8.0F, 9.0F, -8.0F);
      this.Bottom = new ModelRenderer(this, 0, 0);
      this.Bottom.addBox(0.0F, 0.0F, 0.0F, 16, 0, 16);
      this.Bottom.setRotationPoint(-8.0F, 23.0F, -8.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Bottom.render(f5);
      this.Top.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = y;
      model.rotateAngleY = x;
      model.rotateAngleZ = z;
   }
}
