package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.client.gui.util.ISliderListener;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNpcSlider extends GuiButton {

   private ISliderListener listener;
   public int field_146127_k;
   public float sliderValue;
   public boolean dragging;


   public GuiNpcSlider(GuiScreen parent, int id, int xPos, int yPos, String displayString, float sliderValue) {
      super(id, xPos, yPos, 150, 20, NoppesStringUtils.translate(new Object[]{displayString}));
      this.sliderValue = 1.0F;
      this.field_146127_k = id;
      this.sliderValue = sliderValue;
      if(parent instanceof ISliderListener) {
         this.listener = (ISliderListener)parent;
      }

   }

   public GuiNpcSlider(GuiScreen parent, int id, int xPos, int yPos, float sliderValue) {
      this(parent, id, xPos, yPos, "", sliderValue);
      if(this.listener != null) {
         this.listener.mouseDragged(this);
      }

   }

   public GuiNpcSlider(GuiScreen parent, int id, int xPos, int yPos, int width, int height, float sliderValue) {
      this(parent, id, xPos, yPos, "", sliderValue);
      super.width = width;
      super.height = height;
      if(this.listener != null) {
         this.listener.mouseDragged(this);
      }

   }

   public void mouseDragged(Minecraft mc, int par2, int par3) {
      if(super.visible) {
         mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
         if(this.dragging) {
            this.sliderValue = (float)(par2 - (super.xPosition + 4)) / (float)(super.width - 8);
            if(this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            }

            if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }

            if(this.listener != null) {
               this.listener.mouseDragged(this);
            }

            if(!Mouse.isButtonDown(0)) {
               this.drawButtonForegroundLayer(0, 0);
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)), super.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
      }
   }

   public String getDisplayString() {
      return super.displayString;
   }

   public void setString(String str) {
      super.displayString = NoppesStringUtils.translate(new Object[]{str});
   }

   public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
      if(super.enabled && super.visible && par2 >= super.xPosition && par3 >= super.yPosition && par2 < super.xPosition + super.width && par3 < super.yPosition + super.height) {
         this.sliderValue = (float)(par2 - (super.xPosition + 4)) / (float)(super.width - 8);
         if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         if(this.listener != null) {
            this.listener.mousePressed(this);
         }

         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public void drawButtonForegroundLayer(int par1, int par2) {
      this.dragging = false;
      if(this.listener != null) {
         this.listener.mouseReleased(this);
      }

   }

   public int getHoverState(boolean par1) {
      return 0;
   }
}
