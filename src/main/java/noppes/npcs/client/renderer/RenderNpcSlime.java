package noppes.npcs.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderNpcSlime extends RenderNPCInterface {

   private ModelBase scaleAmount;


   public RenderNpcSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
      super(par1ModelBase, par3);
      this.scaleAmount = par2ModelBase;
   }

   protected int shouldSlimeRenderPass(EntityNPCInterface par1EntitySlime, int par2, float par3) {
      if(par1EntitySlime.isInvisible()) {
         return 0;
      } else if(par2 == 0) {
         this.setRenderPassModel(this.scaleAmount);
         GL11.glEnable(2977);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         return 1;
      } else {
         if(par2 == 1) {
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }

         return -1;
      }
   }

   protected int func_77032_a(EntityLivingBase par1EntityLiving, int par2, float par3) {
      return this.shouldSlimeRenderPass((EntityNPCInterface)par1EntityLiving, par2, par3);
   }
}
