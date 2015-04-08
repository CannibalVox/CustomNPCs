package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IButtonListener;
import org.lwjgl.opengl.GL11;

public class GuiMenuTopButton extends GuiNpcButton {

   public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/menutopbutton.png");
   protected int field_146121_g;
   public boolean active;
   public boolean hover;
   public boolean rotated;
   public IButtonListener listener;


   public GuiMenuTopButton(int i, int j, int k, String s) {
      super(i, j, k, StatCollector.translateToLocal(s));
      this.hover = false;
      this.rotated = false;
      this.active = false;
      super.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(super.displayString) + 12;
      this.field_146121_g = 20;
   }

   public GuiMenuTopButton(int i, GuiButton parent, String s) {
      this(i, parent.xPosition + parent.width, parent.yPosition, s);
   }

   public GuiMenuTopButton(int i, GuiButton parent, String s, IButtonListener listener) {
      this(i, parent, s);
      this.listener = listener;
   }

   public int getHoverState(boolean flag) {
      byte byte0 = 1;
      if(this.active) {
         byte0 = 0;
      } else if(flag) {
         byte0 = 2;
      }

      return byte0;
   }

   public void drawButton(Minecraft minecraft, int i, int j) {
      if(this.getVisible()) {
         GL11.glPushMatrix();
         minecraft.renderEngine.bindTexture(resource);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int height = this.field_146121_g - (this.active?0:2);
         this.hover = i >= super.xPosition && j >= super.yPosition && i < super.xPosition + this.getWidth() && j < super.yPosition + height;
         int k = this.getHoverState(this.hover);
         this.drawTexturedModalRect(super.xPosition, super.yPosition, 0, k * 20, this.getWidth() / 2, height);
         this.drawTexturedModalRect(super.xPosition + this.getWidth() / 2, super.yPosition, 200 - this.getWidth() / 2, k * 20, this.getWidth() / 2, height);
         this.mouseDragged(minecraft, i, j);
         FontRenderer fontrenderer = minecraft.fontRendererObj;
         if(this.rotated) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         }

         if(this.active) {
            this.drawCenteredString(fontrenderer, super.displayString, super.xPosition + this.getWidth() / 2, super.yPosition + (height - 8) / 2, 16777120);
         } else if(this.hover) {
            this.drawCenteredString(fontrenderer, super.displayString, super.xPosition + this.getWidth() / 2, super.yPosition + (height - 8) / 2, 16777120);
         } else {
            this.drawCenteredString(fontrenderer, super.displayString, super.xPosition + this.getWidth() / 2, super.yPosition + (height - 8) / 2, 14737632);
         }

         GL11.glPopMatrix();
      }
   }

   protected void mouseDragged(Minecraft minecraft, int i, int j) {}

   public void mouseReleased(int i, int j) {}

   public boolean mousePressed(Minecraft minecraft, int i, int j) {
      boolean bo = !this.active && this.getVisible() && this.hover;
      if(bo && this.listener != null) {
         this.listener.actionPerformed(this);
         return false;
      } else {
         return bo;
      }
   }

}
