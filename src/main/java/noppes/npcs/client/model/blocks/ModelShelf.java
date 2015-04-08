package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelShelf extends ModelBase {

   public ModelRenderer SupportLeft2 = new ModelRenderer(this, 0, 0);
   ModelRenderer Top;
   public ModelRenderer SupportLeft1;
   public ModelRenderer SupportRight1;
   public ModelRenderer SupportRight2;


   public ModelShelf() {
      this.SupportLeft2.mirror = true;
      this.SupportLeft2.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2);
      this.SupportLeft2.setRotationPoint(-7.498F, 9.5F, -0.5F);
      this.setRotation(this.SupportLeft2, 0.7853982F, 0.0F, 0.0F);
      this.Top = new ModelRenderer(this, 5, 0);
      this.Top.addBox(0.0F, 0.0F, 0.0F, 16, 2, 11);
      this.Top.setRotationPoint(-8.0F, 8.0F, -3.0F);
      this.SupportLeft1 = new ModelRenderer(this, 0, 0);
      this.SupportLeft1.mirror = true;
      this.SupportLeft1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2);
      this.SupportLeft1.setRotationPoint(-7.5F, 10.0F, 6.0F);
      this.SupportRight1 = new ModelRenderer(this, 0, 0);
      this.SupportRight1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2);
      this.SupportRight1.setRotationPoint(5.5F, 10.0F, 6.0F);
      this.SupportRight2 = new ModelRenderer(this, 0, 0);
      this.SupportRight2.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2);
      this.SupportRight2.setRotationPoint(5.498F, 9.5F, -0.5F);
      this.setRotation(this.SupportRight2, 0.7853982F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.SupportLeft2.render(f5);
      this.Top.render(f5);
      this.SupportLeft1.render(f5);
      this.SupportRight1.render(f5);
      this.SupportRight2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
