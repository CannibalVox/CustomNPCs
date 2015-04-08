package noppes.npcs.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelSkirtArmor extends ModelBiped {

   private ModelPlaneRenderer Shape1;


   public ModelSkirtArmor() {
      float pi = 0.62831855F;
      this.Shape1 = new ModelPlaneRenderer(this, 4, 20);
      this.Shape1.addSidePlane(0.0F, 0.0F, 0.0F, 9, 2);
      ModelPlaneRenderer part1 = new ModelPlaneRenderer(this, 6, 20);
      part1.addSidePlane(2.0F, 0.0F, 0.0F, 9, 2);
      part1.rotateAngleY = -1.5707964F;
      this.Shape1.addChild(part1);
      this.Shape1.setRotationPoint(2.4F, 8.8F, 0.0F);
      this.setRotation(this.Shape1, 0.3F, -0.2F, -0.2F);
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, super.bipedRightLeg.rotationPointZ * par7);
      GL11.glScalef(1.6F, 1.04F, 1.6F);

      for(int i = 0; i < 10; ++i) {
         GL11.glRotatef(36.0F, 0.0F, 1.0F, 0.0F);
         this.Shape1.render(par7);
      }

      GL11.glPopMatrix();
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
      this.setRotation(this.Shape1, 0.3F, -0.2F, -0.2F);
      super.isSneak = par7Entity.isSneaking();
      super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
      this.Shape1.rotateAngleX += super.bipedLeftArm.rotateAngleX * 0.02F;
      this.Shape1.rotateAngleZ += super.bipedLeftArm.rotateAngleX * 0.06F;
      this.Shape1.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.02F - 0.05F;
   }
}
