package noppes.npcs.client.model.part;

import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelClaws extends ModelPartInterface {

   private Model2DRenderer model;
   private boolean isRight = false;


   public ModelClaws(ModelMPM base, boolean isRight) {
      super(base);
      this.isRight = isRight;
      this.model = new Model2DRenderer(base, 0.0F, 16.0F, 4, 4, 64.0F, 32.0F);
      if(isRight) {
         this.model.setRotationPoint(-2.0F, 14.0F, -2.0F);
      } else {
         this.model.setRotationPoint(3.0F, 14.0F, -2.0F);
      }

      this.model.rotateAngleY = -1.5707964F;
      this.model.setScale(0.25F);
      this.addChild(this.model);
   }

   public void initData(ModelData data) {
      ModelPartData config = data.getPartData("claws");
      if(config != null && (!this.isRight || config.type != 1) && (this.isRight || config.type != 2)) {
         super.color = config.color;
         super.isHidden = false;
         if(!config.playerTexture) {
            super.location = config.getResource();
         } else {
            super.location = null;
         }

      } else {
         super.isHidden = true;
      }
   }
}
