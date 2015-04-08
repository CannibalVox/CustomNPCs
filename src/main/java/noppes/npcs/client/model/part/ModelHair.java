package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelHair extends ModelPartInterface {

   private Model2DRenderer model;


   public ModelHair(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 56.0F, 20.0F, 8, 12, 64.0F, 32.0F);
      this.model.setRotationPoint(-4.0F, 12.0F, 3.0F);
      this.model.setScale(0.75F);
      this.addChild(this.model);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      ModelRenderer parent = super.base.bipedHead;
      if(parent.rotateAngleX < 0.0F) {
         super.rotateAngleX = -parent.rotateAngleX * 1.2F;
         if(parent.rotateAngleX > -1.0F) {
            super.rotationPointY = -parent.rotateAngleX * 1.5F;
            super.rotationPointZ = -parent.rotateAngleX * 1.5F;
         }
      } else {
         super.rotateAngleX = 0.0F;
         super.rotationPointY = 0.0F;
         super.rotationPointZ = 0.0F;
      }

   }

   public void initData(ModelData data) {
      ModelPartData config = data.getPartData("hair");
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
