package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCandleWall extends ModelBase {

   ModelRenderer Base = new ModelRenderer(this, 0, 0);
   ModelRenderer Bar1;
   ModelRenderer Bar2;
   ModelRenderer Bar3;
   ModelRenderer Bar4;
   ModelRenderer Wax;
   ModelRenderer Wall2;
   ModelRenderer Wall1;
   ModelRenderer Bar5;
   ModelRenderer Bar6;


   public ModelCandleWall() {
      this.Base.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Base.setRotationPoint(-2.0F, 13.0F, -4.0F);
      this.Bar1 = new ModelRenderer(this, 0, 0);
      this.Bar1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6);
      this.Bar1.setRotationPoint(-3.0F, 12.0F, -5.0F);
      this.Bar2 = new ModelRenderer(this, 0, 0);
      this.Bar2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6);
      this.Bar2.setRotationPoint(2.0F, 12.0F, -5.0F);
      this.Bar3 = new ModelRenderer(this, 0, 0);
      this.Bar3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
      this.Bar3.setRotationPoint(-2.0F, 12.0F, -5.0F);
      this.Bar4 = new ModelRenderer(this, 0, 0);
      this.Bar4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
      this.Bar4.setRotationPoint(-2.0F, 12.0F, 0.0F);
      this.Wax = new ModelRenderer(this, 16, 0);
      this.Wax.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Wax.setRotationPoint(-1.0F, 9.0F, -3.0F);
      this.Wall2 = new ModelRenderer(this, 0, 0);
      this.Wall2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
      this.Wall2.setRotationPoint(0.0F, 13.7F, -7.5F);
      this.setRotation(this.Wall2, 0.0F, 0.0F, 0.7853982F);
      this.Wall1 = new ModelRenderer(this, 0, 0);
      this.Wall1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 1);
      this.Wall1.setRotationPoint(0.0F, 13.0F, -8.0F);
      this.setRotation(this.Wall1, 0.0F, 0.0F, 0.7853982F);
      this.Bar5 = new ModelRenderer(this, 0, 0);
      this.Bar5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Bar5.setRotationPoint(-0.5F, 13.5F, -2.5F);
      this.Bar6 = new ModelRenderer(this, 0, 0);
      this.Bar6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5);
      this.Bar6.setRotationPoint(-0.5F, 15.5F, -6.5F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Bar1.render(f5);
      this.Bar2.render(f5);
      this.Bar3.render(f5);
      this.Bar4.render(f5);
      this.Wax.render(f5);
      this.Wall2.render(f5);
      this.Wall1.render(f5);
      this.Bar5.render(f5);
      this.Bar6.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
