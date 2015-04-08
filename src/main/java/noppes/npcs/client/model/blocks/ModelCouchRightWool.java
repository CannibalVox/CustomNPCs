package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCouchRightWool extends ModelBase {

   ModelRenderer Wool1 = new ModelRenderer(this, 3, 0);
   ModelRenderer Wool2;


   public ModelCouchRightWool() {
      this.Wool1.mirror = true;
      this.Wool1.addBox(0.0F, 0.0F, 0.0F, 14, 5, 13);
      this.Wool1.setRotationPoint(-8.0F, 16.0F, -6.0F);
      this.Wool2 = new ModelRenderer(this, 14, 0);
      this.Wool2.mirror = true;
      this.Wool2.addBox(0.0F, 0.0F, 0.0F, 14, 10, 2);
      this.Wool2.setRotationPoint(-8.0F, 6.0F, 5.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Wool1.render(f5);
      this.Wool2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
