package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTable extends ModelBase {

   public ModelRenderer Shape1 = new ModelRenderer(this, 0, 0);
   public ModelRenderer Table;
   public ModelRenderer Shape3;
   public ModelRenderer Shape4;
   public ModelRenderer Shape5;


   public ModelTable() {
      this.Shape1.mirror = true;
      this.Shape1.addBox(-1.0F, 0.0F, -1.0F, 2, 14, 2);
      this.Shape1.setRotationPoint(-6.0F, 10.0F, 6.0F);
      this.Table = new ModelRenderer(this, 0, 0);
      this.Table.addBox(0.0F, -2.0F, 0.0F, 16, 2, 16);
      this.Table.setRotationPoint(-8.0F, 10.0F, -8.0F);
      this.Shape3 = new ModelRenderer(this, 0, 0);
      this.Shape3.addBox(-1.0F, 0.0F, -1.0F, 2, 14, 2);
      this.Shape3.setRotationPoint(6.0F, 10.0F, -6.0F);
      this.Shape4 = new ModelRenderer(this, 0, 0);
      this.Shape4.addBox(-1.0F, 0.0F, -1.0F, 2, 14, 2);
      this.Shape4.setRotationPoint(6.0F, 10.0F, 6.0F);
      this.Shape5 = new ModelRenderer(this, 0, 0);
      this.Shape5.mirror = true;
      this.Shape5.addBox(-1.0F, 0.0F, -1.0F, 2, 14, 2);
      this.Shape5.setRotationPoint(-6.0F, 10.0F, -6.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Shape1.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
