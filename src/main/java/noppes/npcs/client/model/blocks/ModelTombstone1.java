package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTombstone1 extends ModelBase {

   ModelRenderer Mid = new ModelRenderer(this, 36, 0);
   ModelRenderer OuterEdge1;
   ModelRenderer OuterEdge2;
   ModelRenderer OuterEdgeTop;


   public ModelTombstone1() {
      this.Mid.addBox(0.0F, 0.0F, 0.0F, 10, 14, 3);
      this.Mid.setRotationPoint(-5.0F, 10.0F, -1.5F);
      this.OuterEdge1 = new ModelRenderer(this, 0, 0);
      this.OuterEdge1.addBox(0.0F, 0.0F, 0.0F, 2, 16, 4);
      this.OuterEdge1.setRotationPoint(-7.0F, 8.0F, -2.0F);
      this.OuterEdge2 = new ModelRenderer(this, 0, 0);
      this.OuterEdge2.mirror = true;
      this.OuterEdge2.addBox(1.0F, 0.0F, 0.0F, 2, 16, 4);
      this.OuterEdge2.setRotationPoint(4.0F, 8.0F, -2.0F);
      this.OuterEdgeTop = new ModelRenderer(this, 0, 22);
      this.OuterEdgeTop.addBox(0.0F, 0.0F, 0.0F, 10, 2, 4);
      this.OuterEdgeTop.setRotationPoint(-5.0F, 8.0F, -2.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Mid.render(f5);
      this.OuterEdge1.render(f5);
      this.OuterEdge2.render(f5);
      this.OuterEdgeTop.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
