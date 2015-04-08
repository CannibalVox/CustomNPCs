package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.ModelData;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiModelInterface extends GuiNPCInterface {

   public ModelData playerdata;
   private static float rotation = 0.0F;
   private GuiNpcButton left;
   private GuiNpcButton right;
   private GuiNpcButton zoom;
   private GuiNpcButton unzoom;
   private static float zoomed = 60.0F;
   public int xOffset = 0;
   public EntityCustomNpc npc;
   private long start = -1L;


   public GuiModelInterface(EntityCustomNpc npc) {
      this.npc = npc;
      this.playerdata = npc.modelData;
      super.xSize = 380;
      super.drawDefaultBackground = false;
   }

   public void initGui() {
      super.initGui();
      this.addButton(this.unzoom = new GuiNpcButton(666, super.guiLeft + 148 + this.xOffset, super.guiTop + 200, 20, 20, "-"));
      this.addButton(this.zoom = new GuiNpcButton(667, super.guiLeft + 214 + this.xOffset, super.guiTop + 200, 20, 20, "+"));
      this.addButton(this.left = new GuiNpcButton(668, super.guiLeft + 170 + this.xOffset, super.guiTop + 200, 20, 20, "<"));
      this.addButton(this.right = new GuiNpcButton(669, super.guiLeft + 192 + this.xOffset, super.guiTop + 200, 20, 20, ">"));
      this.addButton(new GuiNpcButton(66, super.width - 22, 2, 20, 20, "X"));
   }

   protected void actionPerformed(GuiButton btn) {
      if(btn.id == 66) {
         this.close();
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void drawScreen(int par1, int par2, float par3) {
      if(Mouse.isButtonDown(0)) {
         if(this.left.mousePressed(super.mc, par1, par2)) {
            rotation += par3 * 2.0F;
         } else if(this.right.mousePressed(super.mc, par1, par2)) {
            rotation -= par3 * 2.0F;
         } else if(this.zoom.mousePressed(super.mc, par1, par2)) {
            zoomed += par3 * 2.0F;
         } else if(this.unzoom.mousePressed(super.mc, par1, par2) && zoomed > 10.0F) {
            zoomed -= par3 * 2.0F;
         }
      }

      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Object entity = this.playerdata.getEntity(this.npc);
      if(entity == null) {
         entity = this.npc;
      }

      EntityUtil.Copy(this.npc, (EntityLivingBase)entity);
      int l = super.guiLeft + 190 + this.xOffset;
      int i1 = super.guiTop + 180;
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)l, (float)i1, 60.0F);
      GL11.glScalef(-zoomed, zoomed, zoomed);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = ((EntityLivingBase)entity).renderYawOffset;
      float f3 = ((Entity)entity).rotationYaw;
      float f4 = ((Entity)entity).rotationPitch;
      float f7 = ((EntityLivingBase)entity).rotationYawHead;
      float f5 = (float)l - (float)par1;
      float f6 = (float)(i1 - 50) - (float)par2;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f6 / 80.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      ((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = rotation;
      ((Entity)entity).prevRotationYaw = ((Entity)entity).rotationYaw = (float)Math.atan((double)(f5 / 80.0F)) * 40.0F + rotation;
      ((Entity)entity).rotationPitch = -((float)Math.atan((double)(f6 / 80.0F))) * 20.0F;
      ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = ((Entity)entity).rotationYaw;
      GL11.glTranslatef(0.0F, ((Entity)entity).yOffset, 0.0F);
      RenderManager.instance.playerViewY = 180.0F;

      try {
         RenderManager.instance.renderEntityWithPosYaw((Entity)entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      } catch (Exception var14) {
         this.playerdata.setEntityClass((Class)null);
      }

      ((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = f2;
      ((Entity)entity).prevRotationYaw = ((Entity)entity).rotationYaw = f3;
      ((Entity)entity).rotationPitch = f4;
      ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = f7;
      GL11.glPopMatrix();
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable('\u803a');
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, 500.065F);
      super.drawScreen(par1, par2, par3);
      GL11.glPopMatrix();
   }

   public void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      if(par2 == 1) {
         this.close();
      }

   }

   public void close() {
      super.mc.displayGuiScreen((GuiScreen)null);
      super.mc.setIngameFocus();
   }

   public void save() {}

}
