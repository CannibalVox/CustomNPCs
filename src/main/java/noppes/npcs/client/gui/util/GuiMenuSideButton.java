package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiNpcButton;
import org.lwjgl.opengl.GL11;

public class GuiMenuSideButton extends GuiNpcButton {

   public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/menusidebutton.png");
   public boolean active;


   public GuiMenuSideButton(int i, int j, int k, String s) {
      this(i, j, k, 200, 20, s);
   }

   public GuiMenuSideButton(int i, int j, int k, int l, int i1, String s) {
      super(i, j, k, l, i1, s);
      this.active = false;
   }

   public int getHoverState(boolean flag) {
      return this.active?0:1;
   }

   public void drawButton(Minecraft minecraft, int i, int j) {
      if(super.visible) {
         FontRenderer fontrenderer = minecraft.fontRendererObj;
         minecraft.renderEngine.bindTexture(resource);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int width = super.width + (this.active?2:0);
         super.hovered = i >= super.xPosition && j >= super.yPosition && i < super.xPosition + width && j < super.yPosition + super.height;
         int k = this.getHoverState(super.hovered);
         this.drawTexturedModalRect(super.xPosition, super.yPosition, 0, k * 22, width, super.height);
         this.mouseDragged(minecraft, i, j);
         String text = "";
         float maxWidth = (float)width * 0.75F;
         if((float)fontrenderer.getStringWidth(super.displayString) > maxWidth) {
            for(int h = 0; h < super.displayString.length(); ++h) {
               char c = super.displayString.charAt(h);
               if((float)fontrenderer.getStringWidth(text + c) > maxWidth) {
                  break;
               }

               text = text + c;
            }

            text = text + "...";
         } else {
            text = super.displayString;
         }

         if(this.active) {
            this.drawCenteredString(fontrenderer, text, super.xPosition + width / 2, super.yPosition + (super.height - 8) / 2, 16777120);
         } else if(super.hovered) {
            this.drawCenteredString(fontrenderer, text, super.xPosition + width / 2, super.yPosition + (super.height - 8) / 2, 16777120);
         } else {
            this.drawCenteredString(fontrenderer, text, super.xPosition + width / 2, super.yPosition + (super.height - 8) / 2, 14737632);
         }

      }
   }

   protected void mouseDragged(Minecraft minecraft, int i, int j) {}

   public void mouseReleased(int i, int j) {}

   public boolean mousePressed(Minecraft minecraft, int i, int j) {
      return !this.active && super.visible && super.hovered;
   }

}
