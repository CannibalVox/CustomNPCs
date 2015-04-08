package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLamp extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 0, 6);
   ModelRenderer Top1;
   ModelRenderer Top2;
   ModelRenderer Top3;
   ModelRenderer Handle;
   ModelRenderer Shape1;


   public ModelLamp() {
      this.Base.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4);
      this.Base.setRotationPoint(-2.0F, 16.0F, -2.0F);
      this.Top1 = new ModelRenderer(this, 0, 0);
      this.Top1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5);
      this.Top1.setRotationPoint(-2.5F, 16.0F, -2.5F);
      this.Top2 = new ModelRenderer(this, 0, 0);
      this.Top2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Top2.setRotationPoint(-2.0F, 15.5F, -2.0F);
      this.Top3 = new ModelRenderer(this, 0, 0);
      this.Top3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.Top3.setRotationPoint(-1.5F, 15.0F, -1.5F);
      this.Handle = new ModelRenderer(this, 24, 0);
      this.Handle.addBox(0.0F, 0.0F, 0.0F, 3, 0, 3);
      this.Handle.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.setRotation(this.Handle, 0.296706F, 0.1745329F, 0.0F);
      this.Shape1 = new ModelRenderer(this, 0, 17);
      this.Shape1.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4);
      this.Shape1.setRotationPoint(0.0F, 23.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Top1.render(f5);
      this.Top2.render(f5);
      this.Top3.render(f5);
      this.Handle.render(f5);
      this.Shape1.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
