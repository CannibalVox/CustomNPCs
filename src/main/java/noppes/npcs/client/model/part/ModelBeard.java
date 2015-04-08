package noppes.npcs.client.model.part;

import net.minecraft.entity.Entity;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelBeard extends ModelPartInterface {

   private Model2DRenderer model;


   public ModelBeard(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 56.0F, 20.0F, 8, 12, 64.0F, 32.0F);
      this.model.setRotationPoint(-3.99F, 11.9F, -4.0F);
      this.model.setScale(0.74F);
      this.addChild(this.model);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      if(super.base.bipedHead.rotateAngleX > 0.0F) {
         super.rotateAngleX = -super.base.bipedHead.rotateAngleX;
      } else {
         super.rotateAngleX = 0.0F;
      }

   }

   public void initData(ModelData data) {
      ModelPartData config = data.getPartData("beard");
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
