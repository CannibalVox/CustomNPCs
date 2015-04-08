package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTombstone2 extends ModelBase {

   ModelRenderer Top = new ModelRenderer(this, 0, 0);
   ModelRenderer mid;


   public ModelTombstone2() {
      this.Top.addBox(0.0F, 0.0F, 0.0F, 10, 1, 4);
      this.Top.setRotationPoint(-5.0F, 9.0F, -2.0F);
      this.mid = new ModelRenderer(this, 0, 0);
      this.mid.addBox(0.0F, 0.0F, 0.0F, 12, 14, 4);
      this.mid.setRotationPoint(-6.0F, 10.0F, -2.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Top.render(f5);
      this.mid.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
