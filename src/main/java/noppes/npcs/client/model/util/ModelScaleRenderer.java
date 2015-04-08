package noppes.npcs.client.model.util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import noppes.npcs.ModelPartConfig;
import org.lwjgl.opengl.GL11;

public class ModelScaleRenderer extends ModelRenderer {

   public boolean field_78812_q;
   public int field_78811_r;
   protected ModelPartConfig config;
   public float x;
   public float y;
   public float z;


   public ModelScaleRenderer(ModelBase par1ModelBase) {
      super(par1ModelBase);
   }

   public ModelScaleRenderer(ModelBase par1ModelBase, int par2, int par3) {
      this(par1ModelBase);
      this.setTextureOffset(par2, par3);
   }

   public void setConfig(ModelPartConfig config, float x, float y, float z) {
      this.config = config;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void renderChilderen(float par1) {
      if(super.showModel && !super.isHidden) {
         if(!this.field_78812_q) {
            this.compileDisplayList(par1);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(this.x, this.y, this.z);
         GL11.glTranslatef(this.config.transX, this.config.transY, this.config.transZ);
         this.postRender(par1);
         GL11.glScalef(this.config.scaleX, this.config.scaleY, this.config.scaleZ);
         GL11.glCallList(this.field_78811_r);
         if(super.childModels != null) {
            for(int i = 0; i < super.childModels.size(); ++i) {
               ((ModelRenderer)super.childModels.get(i)).render(par1);
            }
         }

         GL11.glPopMatrix();
      }
   }

   public void renderChild(float par1, ModelRenderer model) {
      if(super.showModel && !super.isHidden) {
         GL11.glPushMatrix();
         GL11.glTranslatef(this.x, this.y, this.z);
         GL11.glTranslatef(this.config.transX, this.config.transY, this.config.transZ);
         this.postRender(par1);
         GL11.glScalef(this.config.scaleX, this.config.scaleY, this.config.scaleZ);
         model.render(par1);
         GL11.glPopMatrix();
      }
   }

   public void render(float par1) {
      if(super.showModel && !super.isHidden) {
         if(!this.field_78812_q) {
            this.compileDisplayList(par1);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(this.x, this.y, this.z);
         GL11.glTranslatef(this.config.transX, this.config.transY, this.config.transZ);
         this.postRender(par1);
         GL11.glScalef(this.config.scaleX, this.config.scaleY, this.config.scaleZ);
         GL11.glCallList(this.field_78811_r);
         if(super.childModels != null) {
            for(int i = 0; i < super.childModels.size(); ++i) {
               ((ModelRenderer)super.childModels.get(i)).render(par1);
            }
         }

         GL11.glPopMatrix();
      }
   }

   public void parentRender(float par1) {
      super.render(par1);
   }

   public void compileDisplayList(float par1) {
      this.field_78811_r = GLAllocation.generateDisplayLists(1);
      GL11.glNewList(this.field_78811_r, 4864);
      Tessellator tessellator = Tessellator.instance;

      for(int i = 0; i < super.cubeList.size(); ++i) {
         ((ModelBox)super.cubeList.get(i)).render(tessellator, par1);
      }

      GL11.glEndList();
      this.field_78812_q = true;
   }
}
