package noppes.npcs.client.model.part.legs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelMermaidLegs extends ModelRenderer {

   ModelRenderer top;
   ModelRenderer middle;
   ModelRenderer bottom;
   ModelRenderer fin1;
   ModelRenderer fin2;


   public ModelMermaidLegs(ModelBase base) {
      super(base);
      super.textureWidth = 64.0F;
      super.textureHeight = 32.0F;
      this.top = new ModelRenderer(base, 0, 16);
      this.top.addBox(-2.0F, -2.5F, -2.0F, 8, 9, 4);
      this.top.setRotationPoint(-2.0F, 14.0F, 1.0F);
      this.setRotation(this.top, 0.26F, 0.0F, 0.0F);
      this.middle = new ModelRenderer(base, 28, 0);
      this.middle.addBox(0.0F, 0.0F, 0.0F, 7, 6, 4);
      this.middle.setRotationPoint(-1.5F, 6.5F, -1.0F);
      this.setRotation(this.middle, 0.86F, 0.0F, 0.0F);
      this.top.addChild(this.middle);
      this.bottom = new ModelRenderer(base, 24, 16);
      this.bottom.addBox(0.0F, 0.0F, 0.0F, 6, 7, 3);
      this.bottom.setRotationPoint(0.5F, 6.0F, 0.5F);
      this.setRotation(this.bottom, 0.15F, 0.0F, 0.0F);
      this.middle.addChild(this.bottom);
      this.fin1 = new ModelRenderer(base, 0, 0);
      this.fin1.addBox(0.0F, 0.0F, 0.0F, 5, 9, 1);
      this.fin1.setRotationPoint(0.0F, 4.5F, 1.0F);
      this.setRotation(this.fin1, 0.05F, 0.0F, 0.5911399F);
      this.bottom.addChild(this.fin1);
      this.fin2 = new ModelRenderer(base, 0, 0);
      this.fin2.mirror = true;
      this.fin2.addBox(-5.0F, 0.0F, 0.0F, 5, 9, 1);
      this.fin2.setRotationPoint(6.0F, 4.5F, 1.0F);
      this.setRotation(this.fin2, 0.05F, 0.0F, -0.591143F);
      this.bottom.addChild(this.fin2);
   }

   public void render(float f5) {
      if(!super.isHidden && super.showModel) {
         this.top.render(f5);
      }
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      float ani = MathHelper.sin(par1 * 0.6662F);
      if((double)ani > 0.2D) {
         ani /= 3.0F;
      }

      this.top.rotateAngleX = 0.26F - ani * 0.2F * par2;
      this.middle.rotateAngleX = 0.86F - ani * 0.24F * par2;
      this.bottom.rotateAngleX = 0.15F - ani * 0.28F * par2;
      this.fin2.rotateAngleX = this.fin1.rotateAngleX = 0.05F - ani * 0.35F * par2;
   }
}
