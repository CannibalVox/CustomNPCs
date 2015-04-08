package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLongCrate extends ModelBase {

   ModelRenderer Vertical1 = new ModelRenderer(this, 80, 0);
   ModelRenderer Horizontal1;
   ModelRenderer Cratebody;
   ModelRenderer Horizontal2;
   ModelRenderer Vertical3;
   ModelRenderer Vertical4;
   ModelRenderer Vertical2;


   public ModelLongCrate() {
      this.Vertical1.addBox(0.0F, 0.0F, 0.0F, 4, 13, 1);
      this.Vertical1.setRotationPoint(-12.0F, 11.0F, 8.0F);
      this.Horizontal1 = new ModelRenderer(this, 0, 0);
      this.Horizontal1.mirror = true;
      this.Horizontal1.addBox(0.0F, 0.0F, 0.0F, 4, 1, 18);
      this.Horizontal1.setRotationPoint(8.0F, 10.0F, -9.0F);
      this.Cratebody = new ModelRenderer(this, 8, 0);
      this.Cratebody.addBox(-16.0F, 0.0F, -8.0F, 32, 13, 16);
      this.Cratebody.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.Horizontal2 = new ModelRenderer(this, 0, 0);
      this.Horizontal2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 18);
      this.Horizontal2.setRotationPoint(-12.0F, 10.0F, -9.0F);
      this.Vertical3 = new ModelRenderer(this, 80, 0);
      this.Vertical3.addBox(0.0F, 0.0F, 0.0F, 4, 13, 1);
      this.Vertical3.setRotationPoint(-12.0F, 11.0F, -9.0F);
      this.Vertical4 = new ModelRenderer(this, 80, 0);
      this.Vertical4.mirror = true;
      this.Vertical4.addBox(0.0F, 0.0F, 0.0F, 4, 13, 1);
      this.Vertical4.setRotationPoint(8.0F, 11.0F, -9.0F);
      this.Vertical2 = new ModelRenderer(this, 80, 0);
      this.Vertical2.mirror = true;
      this.Vertical2.addBox(0.0F, 0.0F, 0.0F, 4, 13, 1);
      this.Vertical2.setRotationPoint(8.0F, 11.0F, 8.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Vertical1.render(f5);
      this.Horizontal1.render(f5);
      this.Cratebody.render(f5);
      this.Horizontal2.render(f5);
      this.Vertical3.render(f5);
      this.Vertical4.render(f5);
      this.Vertical2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
