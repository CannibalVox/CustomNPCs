package noppes.npcs.client.model.part;

import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelFin extends ModelPartInterface {

   private Model2DRenderer model;


   public ModelFin(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 56.0F, 20.0F, 8, 12, 64.0F, 32.0F);
      this.model.setRotationPoint(-0.5F, 12.0F, 10.0F);
      this.model.setScale(0.74F);
      this.model.rotateAngleY = 1.5707964F;
      this.addChild(this.model);
   }

   public void initData(ModelData data) {
      ModelPartData config = data.getPartData("fin");
      if(config == null) {
         super.isHidden = true;
      } else {
         super.color = config.color;
         super.isHidden = false;
         if(!config.playerTexture) {
            super.location = config.getResource();
         } else {
            super.location = null;
         }

      }
   }
}
