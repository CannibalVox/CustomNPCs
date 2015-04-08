package noppes.npcs.client.model.part.tails;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.ModelPlaneRenderer;

public class ModelDragonTail extends ModelRenderer {

   public ModelDragonTail(ModelMPM base) {
      super(base);
      byte x = 52;
      byte y = 16;
      ModelRenderer dragon = new ModelRenderer(base, x, y);
      dragon.setRotationPoint(0.0F, 0.0F, 3.0F);
      this.addChild(dragon);
      ModelRenderer DragonTail2 = new ModelRenderer(base, x, y);
      DragonTail2.setRotationPoint(0.0F, 2.0F, 2.0F);
      ModelRenderer DragonTail3 = new ModelRenderer(base, x, y);
      DragonTail3.setRotationPoint(0.0F, 4.5F, 4.0F);
      ModelRenderer DragonTail4 = new ModelRenderer(base, x, y);
      DragonTail4.setRotationPoint(0.0F, 7.0F, 5.75F);
      ModelRenderer DragonTail5 = new ModelRenderer(base, x, y);
      DragonTail5.setRotationPoint(0.0F, 9.0F, 8.0F);
      ModelPlaneRenderer planeLeft = new ModelPlaneRenderer(base, x, y);
      planeLeft.addSidePlane(-1.5F, -1.5F, -1.5F, 3, 3);
      ModelPlaneRenderer planeRight = new ModelPlaneRenderer(base, x, y);
      planeRight.addSidePlane(-1.5F, -1.5F, -1.5F, 3, 3);
      this.setRotation(planeRight, 3.1415927F, 3.1415927F, 0.0F);
      ModelPlaneRenderer planeTop = new ModelPlaneRenderer(base, x, y);
      planeTop.addTopPlane(-1.5F, -1.5F, -1.5F, 3, 3);
      this.setRotation(planeTop, 0.0F, -1.5707964F, 0.0F);
      ModelPlaneRenderer planeBottom = new ModelPlaneRenderer(base, x, y);
      planeBottom.addTopPlane(-1.5F, -1.5F, -1.5F, 3, 3);
      this.setRotation(planeBottom, 0.0F, -1.5707964F, 3.1415927F);
      ModelPlaneRenderer planeBack = new ModelPlaneRenderer(base, x, y);
      planeBack.addBackPlane(-1.5F, -1.5F, -1.5F, 3, 3);
      this.setRotation(planeBack, 0.0F, 0.0F, 1.5707964F);
      ModelPlaneRenderer planeFront = new ModelPlaneRenderer(base, x, y);
      planeFront.addBackPlane(-1.5F, -1.5F, -1.5F, 3, 3);
      this.setRotation(planeFront, 0.0F, 3.1415927F, -1.5707964F);
      dragon.addChild(planeLeft);
      dragon.addChild(planeRight);
      dragon.addChild(planeTop);
      dragon.addChild(planeBottom);
      dragon.addChild(planeFront);
      dragon.addChild(planeBack);
      DragonTail2.addChild(planeLeft);
      DragonTail2.addChild(planeRight);
      DragonTail2.addChild(planeTop);
      DragonTail2.addChild(planeBottom);
      DragonTail2.addChild(planeFront);
      DragonTail2.addChild(planeBack);
      DragonTail3.addChild(planeLeft);
      DragonTail3.addChild(planeRight);
      DragonTail3.addChild(planeTop);
      DragonTail3.addChild(planeBottom);
      DragonTail3.addChild(planeFront);
      DragonTail3.addChild(planeBack);
      DragonTail4.addChild(planeLeft);
      DragonTail4.addChild(planeRight);
      DragonTail4.addChild(planeTop);
      DragonTail4.addChild(planeBottom);
      DragonTail4.addChild(planeFront);
      DragonTail4.addChild(planeBack);
      dragon.addChild(DragonTail2);
      dragon.addChild(DragonTail3);
      dragon.addChild(DragonTail4);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {}

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
