package noppes.npcs.client.model.animation;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.ModelMPM;

public class AniCrawling {

   public static void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity, ModelMPM model) {
      model.bipedHead.rotateAngleZ = -par4 / 57.295776F;
      model.bipedHead.rotateAngleY = 0.0F;
      model.bipedHead.rotateAngleX = -0.95993114F;
      model.bipedHeadwear.rotateAngleX = model.bipedHead.rotateAngleX;
      model.bipedHeadwear.rotateAngleY = model.bipedHead.rotateAngleY;
      model.bipedHeadwear.rotateAngleZ = model.bipedHead.rotateAngleZ;
      if((double)par2 > 0.25D) {
         par2 = 0.25F;
      }

      float movement = MathHelper.cos(par1 * 0.8F + 3.1415927F) * par2;
      model.bipedLeftArm.rotateAngleX = 3.1415927F - movement * 0.25F;
      model.bipedLeftArm.rotateAngleY = movement * -0.46F;
      model.bipedLeftArm.rotateAngleZ = movement * -0.2F;
      model.bipedLeftArm.rotationPointY = 2.0F - movement * 9.0F;
      model.bipedRightArm.rotateAngleX = 3.1415927F + movement * 0.25F;
      model.bipedRightArm.rotateAngleY = movement * -0.4F;
      model.bipedRightArm.rotateAngleZ = movement * -0.2F;
      model.bipedRightArm.rotationPointY = 2.0F + movement * 9.0F;
      model.bipedBody.rotateAngleY = movement * 0.1F;
      model.bipedBody.rotateAngleX = 0.0F;
      model.bipedBody.rotateAngleZ = movement * 0.1F;
      model.bipedLeftLeg.rotateAngleX = movement * 0.1F;
      model.bipedLeftLeg.rotateAngleY = movement * 0.1F;
      model.bipedLeftLeg.rotateAngleZ = -0.122173056F - movement * 0.25F;
      model.bipedLeftLeg.rotationPointY = 10.4F + movement * 9.0F;
      model.bipedLeftLeg.rotationPointZ = movement * 0.6F - 0.01F;
      model.bipedRightLeg.rotateAngleX = movement * -0.1F;
      model.bipedRightLeg.rotateAngleY = movement * 0.1F;
      model.bipedRightLeg.rotateAngleZ = 0.122173056F - movement * 0.25F;
      model.bipedRightLeg.rotationPointY = 10.4F - movement * 9.0F;
      model.bipedRightLeg.rotationPointZ = movement * -0.6F - 0.01F;
   }
}
