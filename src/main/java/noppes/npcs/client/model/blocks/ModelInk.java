package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelInk extends ModelBase {

   ModelRenderer InkMid = new ModelRenderer(this, 0, 25);
   ModelRenderer InkTop;
   ModelRenderer InkBottom;
   ModelRenderer Shape1;
   ModelRenderer InkBottom2;


   public ModelInk() {
      this.InkMid.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.InkMid.setRotationPoint(5.0F, 21.0F, 3.5F);
      this.InkTop = new ModelRenderer(this, 0, 22);
      this.InkTop.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.InkTop.setRotationPoint(4.5F, 20.0F, 3.0F);
      this.InkBottom = new ModelRenderer(this, 3, 16);
      this.InkBottom.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.InkBottom.setRotationPoint(4.0F, 22.0F, 2.5F);
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 0, 13, 3);
      this.Shape1.setRotationPoint(5.5F, 10.0F, 2.5F);
      this.InkBottom2 = new ModelRenderer(this, 0, 27);
      this.InkBottom2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.InkBottom2.setRotationPoint(4.0F, 23.0F, 2.5F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Shape1.render(f5);
      this.InkMid.render(f5);
      this.InkTop.render(f5);
      this.InkBottom2.render(f5);
      this.InkBottom.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
