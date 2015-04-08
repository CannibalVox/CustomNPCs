package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTombstone3 extends ModelBase {

   ModelRenderer Bottom = new ModelRenderer(this, 0, 0);
   ModelRenderer Piece5;
   ModelRenderer Piece2;
   ModelRenderer Piece1;
   ModelRenderer Piece4;
   ModelRenderer Piece3;
   ModelRenderer Piece7;


   public ModelTombstone3() {
      this.Bottom.addBox(0.0F, 0.0F, 0.0F, 12, 5, 4);
      this.Bottom.setRotationPoint(-6.0F, 19.0F, -2.0F);
      this.Piece5 = new ModelRenderer(this, 0, 0);
      this.Piece5.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Piece5.setRotationPoint(-4.0F, 16.0F, -2.0F);
      this.Piece2 = new ModelRenderer(this, 0, 0);
      this.Piece2.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4);
      this.Piece2.setRotationPoint(2.0F, 17.0F, -2.0F);
      this.Piece1 = new ModelRenderer(this, 0, 0);
      this.Piece1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 4);
      this.Piece1.setRotationPoint(-5.0F, 17.0F, -2.0F);
      this.Piece4 = new ModelRenderer(this, 0, 0);
      this.Piece4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4);
      this.Piece4.setRotationPoint(-5.0F, 14.0F, -2.0F);
      this.Piece3 = new ModelRenderer(this, 0, 0);
      this.Piece3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 4);
      this.Piece3.setRotationPoint(3.0F, 16.0F, -2.0F);
      this.Piece7 = new ModelRenderer(this, 0, 0);
      this.Piece7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4);
      this.Piece7.setRotationPoint(-4.0F, 15.0F, -2.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Bottom.render(f5);
      this.Piece5.render(f5);
      this.Piece2.render(f5);
      this.Piece1.render(f5);
      this.Piece4.render(f5);
      this.Piece3.render(f5);
      this.Piece7.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
