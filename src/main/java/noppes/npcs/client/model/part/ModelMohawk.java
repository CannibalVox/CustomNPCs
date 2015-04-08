package noppes.npcs.client.model.part;

import net.minecraft.entity.Entity;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelMohawk extends ModelPartInterface {

   private Model2DRenderer model;


   public ModelMohawk(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 0, 0, 13, 13);
      this.model.setRotationPoint(-0.5F, 0.0F, 9.0F);
      this.setRotation(this.model, 0.0F, 1.5707964F, 0.0F);
      this.model.setScale(0.825F);
      this.addChild(this.model);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {}

   public void initData(ModelData data) {
      ModelPartData config = data.getPartData("mohawk");
      if(config == null) {
         super.isHidden = true;
      } else {
         super.color = config.color;
         super.isHidden = false;
         super.location = config.getResource();
      }
   }
}
