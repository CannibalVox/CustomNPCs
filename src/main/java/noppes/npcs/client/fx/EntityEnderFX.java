package noppes.npcs.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.opengl.GL11;

public class EntityEnderFX extends EntityPortalFX {

   private float portalParticleScale;
   private int particleNumber;
   private EntityCustomNpc npc;
   private static final ResourceLocation resource = new ResourceLocation("textures/particle/particles.png");
   private final ResourceLocation location;
   private boolean move = true;
   private float startX = 0.0F;
   private float startY = 0.0F;
   private float startZ = 0.0F;


   public EntityEnderFX(EntityCustomNpc npc, double par2, double par4, double par6, double par8, double par10, double par12, ModelPartData data) {
      super(npc.worldObj, par2, par4, par6, par8, par10, par12);
      this.npc = npc;
      this.particleNumber = npc.getRNG().nextInt(2);
      this.portalParticleScale = super.particleScale = super.rand.nextFloat() * 0.2F + 0.5F;
      super.particleRed = (float)(data.color >> 16 & 255) / 255.0F;
      super.particleGreen = (float)(data.color >> 8 & 255) / 255.0F;
      super.particleBlue = (float)(data.color & 255) / 255.0F;
      if(npc.getRNG().nextInt(3) == 1) {
         this.move = false;
         this.startX = (float)npc.posX;
         this.startY = (float)npc.posY;
         this.startZ = (float)npc.posZ;
      }

      if(data.playerTexture) {
         this.location = npc.textureLocation;
      } else {
         this.location = new ResourceLocation(data.texture);
      }

   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      if(this.move) {
         this.startX = (float)(this.npc.prevPosX + (this.npc.posX - this.npc.prevPosX) * (double)par2);
         this.startY = (float)(this.npc.prevPosY + (this.npc.posY - this.npc.prevPosY) * (double)par2);
         this.startZ = (float)(this.npc.prevPosZ + (this.npc.posZ - this.npc.prevPosZ) * (double)par2);
      }

      Tessellator tessellator = Tessellator.instance;
      tessellator.draw();
      float scale = ((float)super.particleAge + par2) / (float)super.particleMaxAge;
      scale = 1.0F - scale;
      scale *= scale;
      scale = 1.0F - scale;
      super.particleScale = this.portalParticleScale * scale;
      ClientProxy.bindTexture(this.location);
      float f = 0.875F;
      float f1 = f + 0.125F;
      float f2 = 0.75F - (float)this.particleNumber * 0.25F;
      float f3 = f2 + 0.25F;
      float f4 = 0.1F * super.particleScale;
      float f5 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)par2 - EntityFX.interpPosX + (double)this.startX);
      float f6 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)par2 - EntityFX.interpPosY + (double)this.startY);
      float f7 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)par2 - EntityFX.interpPosZ + (double)this.startZ);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      tessellator.startDrawingQuads();
      tessellator.setBrightness(240);
      par1Tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
      par1Tessellator.setColorRGBA_F(super.particleRed, super.particleGreen, super.particleBlue, 1.0F);
      par1Tessellator.addVertexWithUV((double)(f5 - par3 * f4 - par6 * f4), (double)(f6 - par4 * f4), (double)(f7 - par5 * f4 - par7 * f4), (double)f1, (double)f3);
      par1Tessellator.addVertexWithUV((double)(f5 - par3 * f4 + par6 * f4), (double)(f6 + par4 * f4), (double)(f7 - par5 * f4 + par7 * f4), (double)f1, (double)f2);
      par1Tessellator.addVertexWithUV((double)(f5 + par3 * f4 + par6 * f4), (double)(f6 + par4 * f4), (double)(f7 + par5 * f4 + par7 * f4), (double)f, (double)f2);
      par1Tessellator.addVertexWithUV((double)(f5 + par3 * f4 - par6 * f4), (double)(f6 - par4 * f4), (double)(f7 + par5 * f4 - par7 * f4), (double)f, (double)f3);
      tessellator.draw();
      ClientProxy.bindTexture(resource);
      tessellator.startDrawingQuads();
   }

   public int getFXLayer() {
      return 0;
   }

}
