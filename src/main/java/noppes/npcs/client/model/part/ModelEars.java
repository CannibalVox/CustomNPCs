package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelEars extends ModelPartInterface {

   private ModelRenderer ears;
   private ModelRenderer bunny;


   public ModelEars(ModelMPM par1ModelBase) {
      super(par1ModelBase);
      this.ears = new ModelRenderer(super.base);
      this.addChild(this.ears);
      Model2DRenderer right = new Model2DRenderer(super.base, 56.0F, 0.0F, 8, 4, 64.0F, 32.0F);
      right.setRotationPoint(-7.44F, -7.3F, -0.0F);
      right.setScale(0.234F, 0.234F);
      right.setThickness(1.16F);
      this.ears.addChild(right);
      Model2DRenderer left = new Model2DRenderer(super.base, 56.0F, 0.0F, 8, 4, 64.0F, 32.0F);
      left.setRotationPoint(7.44F, -7.3F, 1.15F);
      left.setScale(0.234F, 0.234F);
      this.setRotation(left, 0.0F, 3.1415927F, 0.0F);
      left.setThickness(1.16F);
      this.ears.addChild(left);
      Model2DRenderer right2 = new Model2DRenderer(super.base, 56.0F, 4.0F, 8, 4, 64.0F, 32.0F);
      right2.setRotationPoint(-7.44F, -7.3F, 1.14F);
      right2.setScale(0.234F, 0.234F);
      right2.setThickness(1.16F);
      this.ears.addChild(right2);
      Model2DRenderer left2 = new Model2DRenderer(super.base, 56.0F, 4.0F, 8, 4, 64.0F, 32.0F);
      left2.setRotationPoint(7.44F, -7.3F, 2.31F);
      left2.setScale(0.234F, 0.234F);
      this.setRotation(left2, 0.0F, 3.1415927F, 0.0F);
      left2.setThickness(1.16F);
      this.ears.addChild(left2);
      this.bunny = new ModelRenderer(super.base);
      this.addChild(this.bunny);
      ModelRenderer earleft = new ModelRenderer(super.base, 56, 0);
      earleft.mirror = true;
      earleft.addBox(-1.466667F, -4.0F, 0.0F, 3, 7, 1);
      earleft.setRotationPoint(2.533333F, -11.0F, 0.0F);
      this.bunny.addChild(earleft);
      ModelRenderer earright = new ModelRenderer(super.base, 56, 0);
      earright.addBox(-1.5F, -4.0F, 0.0F, 3, 7, 1);
      earright.setRotationPoint(-2.466667F, -11.0F, 0.0F);
      this.bunny.addChild(earright);
   }

   public void initData(ModelData data) {
      ModelPartData config = data.getPartData("ears");
      if(config == null) {
         super.isHidden = true;
      } else {
         super.isHidden = false;
         super.color = config.color;
         this.ears.isHidden = config.type != 0;
         this.bunny.isHidden = config.type != 1;
         if(!config.playerTexture) {
            super.location = config.getResource();
         } else {
            super.location = null;
         }

      }
   }
}
