package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCouchCornerWool extends ModelBase {

   ModelRenderer Wool1 = new ModelRenderer(this, 11, 3);
   ModelRenderer Wool2;
   ModelRenderer Wool3;
   ModelRenderer Wool4;


   public ModelCouchCornerWool() {
      this.Wool1.addBox(0.0F, 0.0F, 0.0F, 13, 5, 2);
      this.Wool1.setRotationPoint(-7.0F, 16.0F, -8.0F);
      this.Wool2 = new ModelRenderer(this, 2, 4);
      this.Wool2.addBox(0.0F, 0.0F, 0.0F, 2, 10, 13);
      this.Wool2.setRotationPoint(-7.0F, 6.0F, -8.0F);
      this.Wool3 = new ModelRenderer(this, 14, 15);
      this.Wool3.addBox(0.0F, 0.0F, 0.0F, 15, 10, 2);
      this.Wool3.setRotationPoint(-7.0F, 6.0F, 5.0F);
      this.Wool4 = new ModelRenderer(this, 0, 45);
      this.Wool4.addBox(0.0F, 0.0F, 0.0F, 15, 5, 13);
      this.Wool4.setRotationPoint(-7.0F, 16.0F, -6.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Wool1.render(f5);
      this.Wool2.render(f5);
      this.Wool3.render(f5);
      this.Wool4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
