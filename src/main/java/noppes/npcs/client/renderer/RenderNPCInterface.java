package noppes.npcs.client.renderer;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.File;
import java.security.MessageDigest;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.renderer.ImageBufferDownloadAlt;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumStandingType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderNPCInterface extends RenderLiving {

   public RenderNPCInterface(ModelBase model, float f) {
      super(model, f);
   }

   protected void renderName(EntityNPCInterface npc, double d, double d1, double d2) {
      if(this.canRenderName(npc)) {
         float f2 = npc.getDistanceToEntity(super.renderManager.livingPlayer);
         float f3 = npc.isSneaking()?32.0F:64.0F;
         if(f2 <= f3) {
            float scale;
            if(npc.messages != null) {
               scale = npc.baseHeight / 5.0F * (float)npc.display.modelSize;
               float height = npc.height * (1.2F + (!npc.display.showName()?0.0F:(npc.display.title.isEmpty()?0.15F:0.25F)));
               npc.messages.renderMessages(d, d1 + (double)height, d2, 0.666667F * scale);
            }

            scale = npc.baseHeight / 5.0F * (float)npc.display.modelSize;
            boolean height1 = false;
            if(npc.display.showName()) {
               String s = npc.getCommandSenderName();
               if(!npc.display.title.isEmpty()) {
                  this.renderLivingLabel(npc, d, d1 + (double)npc.height - (double)(0.06F * scale), d2, 64, new Object[]{"<" + npc.display.title + ">", Float.valueOf(0.6F), s, Float.valueOf(1.0F)});
                  height1 = true;
               } else {
                  this.renderLivingLabel(npc, d, d1 + (double)npc.height - (double)(0.06F * scale), d2, 64, new Object[]{s, Float.valueOf(1.0F)});
                  height1 = true;
               }
            }

         }
      }
   }

   public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      EntityNPCInterface npc = (EntityNPCInterface)par1Entity;
      if(!npc.isKilled()) {
         super.doRenderShadowAndFire(par1Entity, par2, par4, par6, par8, par9);
      }

   }

   protected void renderLivingLabel(EntityNPCInterface npc, double d, double d1, double d2, int i, Object ... obs) {
      FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
      i = npc.getBrightnessForRender(0.0F);
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
      float f1 = npc.baseHeight / 5.0F * (float)npc.display.modelSize;
      float f2 = 0.01666667F * f1;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d + 0.0F, (float)d1, (float)d2);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      Tessellator tessellator = Tessellator.instance;
      float height = f1 / 6.5F;

      for(j = 0; j < obs.length; j += 2) {
         float scale = ((Float)obs[j + 1]).floatValue();
         height += f1 / 6.5F * scale;
         GL11.glPushMatrix();
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(3553);
         String s = obs[j].toString();
         GL11.glTranslatef(0.0F, height, 0.0F);
         GL11.glScalef(-f2 * scale, -f2 * scale, f2 * scale);
         tessellator.startDrawingQuads();
         int size = fontrenderer.getStringWidth(s) / 2;
         tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
         tessellator.addVertex((double)(-size - 1), -1.0D, 0.0D);
         tessellator.addVertex((double)(-size - 1), 8.0D, 0.0D);
         tessellator.addVertex((double)(size + 1), 8.0D, 0.0D);
         tessellator.addVertex((double)(size + 1), -1.0D, 0.0D);
         tessellator.draw();
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int color = npc.faction.color;
         fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, color);
         GL11.glPopMatrix();
      }

      GL11.glEnable(3008);
      GL11.glEnable(2896);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   protected void renderPlayerScale(EntityNPCInterface npc, float f) {
      GL11.glScalef(npc.scaleX / 5.0F * (float)npc.display.modelSize, npc.scaleY / 5.0F * (float)npc.display.modelSize, npc.scaleZ / 5.0F * (float)npc.display.modelSize);
   }

   protected void renderPlayerSleep(EntityNPCInterface npc, double d, double d1, double d2) {
      super.shadowSize = (float)npc.display.modelSize / 10.0F;
      float xOffset = 0.0F;
      float yOffset = npc.currentAnimation == EnumAnimation.NONE?npc.ai.bodyOffsetY / 10.0F - 0.5F:0.0F;
      float zOffset = 0.0F;
      if(npc.isEntityAlive()) {
         if(npc.isPlayerSleeping()) {
            xOffset = (float)(-Math.cos(Math.toRadians((double)(180 - npc.ai.orientation))));
            zOffset = (float)(-Math.sin(Math.toRadians((double)npc.ai.orientation)));
            yOffset += 0.14F;
         } else if(npc.isRiding()) {
            yOffset -= 0.5F - ((EntityCustomNpc)npc).modelData.getLegsY() * 0.8F;
         }
      }

      this.renderLiving(npc, d, d1, d2, xOffset, yOffset, zOffset);
   }

   private void renderLiving(EntityNPCInterface npc, double d, double d1, double d2, float xoffset, float yoffset, float zoffset) {
      xoffset = xoffset / 5.0F * (float)npc.display.modelSize;
      yoffset = yoffset / 5.0F * (float)npc.display.modelSize;
      zoffset = zoffset / 5.0F * (float)npc.display.modelSize;
      super.renderLivingAt(npc, d + (double)xoffset, d1 + (double)yoffset, d2 + (double)zoffset);
   }

   protected void rotateCorpse(EntityLivingBase entity, float f, float f1, float f2) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      if(npc.isEntityAlive() && npc.isPlayerSleeping()) {
         GL11.glRotatef((float)npc.ai.orientation, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.getDeathMaxRotation(npc), 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
      } else if(npc.isEntityAlive() && npc.currentAnimation == EnumAnimation.CRAWLING) {
         GL11.glRotatef(270.0F - f1, 0.0F, 1.0F, 0.0F);
         float scale = (float)((EntityCustomNpc)npc).display.modelSize / 5.0F;
         GL11.glTranslated((double)(-scale + ((EntityCustomNpc)npc).modelData.getLegsY() * scale), 0.14000000059604645D, 0.0D);
         GL11.glRotatef(270.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
      } else {
         super.rotateCorpse(npc, f, f1, f2);
      }

   }

   protected void passSpecialRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
      this.renderName((EntityNPCInterface)par1EntityLivingBase, par2, par4, par6);
   }

   protected void preRenderCallback(EntityLivingBase entityliving, float f) {
      this.renderPlayerScale((EntityNPCInterface)entityliving, f);
   }

   public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      EntityNPCInterface npc = (EntityNPCInterface)entityliving;
      if(!npc.isKilled() || !npc.stats.hideKilledBody || npc.deathTime <= 20) {
         if((npc.display.showBossBar == 1 || npc.display.showBossBar == 2 && npc.isAttacking()) && !npc.isKilled() && npc.deathTime <= 20 && npc.canSee(Minecraft.getMinecraft().thePlayer)) {
            BossStatus.setBossStatus(npc, true);
         }

         if(npc.ai.standingType == EnumStandingType.HeadRotation && !npc.isWalking()) {
            npc.prevRenderYawOffset = npc.renderYawOffset = (float)npc.ai.orientation;
         }

         super.doRender(entityliving, d, d1, d2, f, f1);
      }
   }

   protected void renderModel(EntityLivingBase entityliving, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.renderModel(entityliving, par2, par3, par4, par5, par6, par7);
      EntityNPCInterface npc = (EntityNPCInterface)entityliving;
      if(!npc.display.glowTexture.isEmpty()) {
         GL11.glDepthFunc(515);
         if(npc.textureGlowLocation == null) {
            npc.textureGlowLocation = new ResourceLocation(npc.display.glowTexture);
         }

         this.bindTexture(npc.textureGlowLocation);
         float f1 = 1.0F;
         GL11.glEnable(3042);
         GL11.glBlendFunc(1, 1);
         GL11.glDisable(2896);
         if(npc.isInvisible()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         GL11.glScalef(1.001F, 1.001F, 1.001F);
         if(super.mainModel instanceof ModelMPM) {
            ((ModelMPM)super.mainModel).isArmor = true;
            super.mainModel.render(entityliving, par2, par3, par4, par5, par6, par7);
            ((ModelMPM)super.mainModel).isArmor = false;
         } else {
            super.mainModel.render(entityliving, par2, par3, par4, par5, par6, par7);
         }

         GL11.glPopMatrix();
         GL11.glEnable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
         GL11.glDepthFunc(515);
         GL11.glDisable(3042);
      }

   }

   protected float handleRotationFloat(EntityLivingBase par1EntityLiving, float par2) {
      EntityNPCInterface npc = (EntityNPCInterface)par1EntityLiving;
      return !npc.isKilled() && !npc.display.NoLivingAnimation?super.handleRotationFloat(par1EntityLiving, par2):0.0F;
   }

   protected void renderLivingAt(EntityLivingBase entityliving, double d, double d1, double d2) {
      this.renderPlayerSleep((EntityNPCInterface)entityliving, d, d1, d2);
   }

   public ResourceLocation getEntityTexture(Entity entity) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      if(npc.textureLocation == null) {
         if(npc.display.skinType == 1 && npc.display.playerProfile != null) {
            Minecraft var11 = Minecraft.getMinecraft();
            Map var12 = var11.getSkinManager().loadSkinFromCache(npc.display.playerProfile);
            if(var12.containsKey(Type.SKIN)) {
               npc.textureLocation = var11.getSkinManager().loadSkin((MinecraftProfileTexture)var12.get(Type.SKIN), Type.SKIN);
            }
         } else if(npc.display.skinType == 2) {
            try {
               MessageDigest ex = MessageDigest.getInstance("MD5");
               byte[] hash = ex.digest(npc.display.url.getBytes("UTF-8"));
               StringBuilder sb = new StringBuilder(2 * hash.length);
               byte[] var6 = hash;
               int var7 = hash.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  byte b = var6[var8];
                  sb.append(String.format("%02x", new Object[]{Integer.valueOf(b & 255)}));
               }

               npc.textureLocation = new ResourceLocation("skins/" + sb.toString());
               this.func_110301_a((File)null, npc.textureLocation, npc.display.url);
            } catch (Exception var10) {
               ;
            }
         } else if(npc.display.skinType == 0) {
            npc.textureLocation = new ResourceLocation(npc.display.texture);
         }
      }

      return npc.textureLocation == null?AbstractClientPlayer.locationStevePng:npc.textureLocation;
   }

   private void func_110301_a(File file, ResourceLocation resource, String par1Str) {
      TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
      ThreadDownloadImageData object = new ThreadDownloadImageData(file, par1Str, SkinManager.DEFAULT_SKIN, new ImageBufferDownloadAlt());
      texturemanager.loadTexture(resource, object);
   }
}
