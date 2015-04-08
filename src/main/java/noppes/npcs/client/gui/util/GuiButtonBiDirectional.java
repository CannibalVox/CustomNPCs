package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiNpcButton;
import org.lwjgl.opengl.GL11;

public class GuiButtonBiDirectional extends GuiNpcButton {

   public static final ResourceLocation resource = new ResourceLocation("customnpcs:textures/gui/arrowbuttons.png");


   public GuiButtonBiDirectional(int id, int x, int y, int width, int height, String[] arr, int current) {
      super(id, x, y, width, height, arr, current);
   }

   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if(super.visible) {
         boolean hover = mouseX >= super.xPosition && mouseY >= super.yPosition && mouseX < super.xPosition + super.width && mouseY < super.yPosition + super.height;
         boolean hoverL = mouseX >= super.xPosition && mouseY >= super.yPosition && mouseX < super.xPosition + 11 && mouseY < super.yPosition + super.height;
         boolean hoverR = !hoverL && mouseX >= super.xPosition + super.width - 11 && mouseY >= super.yPosition && mouseX < super.xPosition + super.width && mouseY < super.yPosition + super.height;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         mc.getTextureManager().bindTexture(resource);
         this.drawTexturedModalRect(super.xPosition, super.yPosition, 0, hoverL?40:20, 11, 20);
         this.drawTexturedModalRect(super.xPosition + super.width - 11, super.yPosition, 11, hoverR?40:20, 11, 20);
         int l = 16777215;
         if(this.packedFGColour != 0) {
            l = this.packedFGColour;
         } else if(!super.enabled) {
            l = 10526880;
         } else if(hover) {
            l = 16777120;
         }

         String text = "";
         float maxWidth = (float)(super.width - 36);
         if((float)mc.fontRendererObj.getStringWidth(super.displayString) > maxWidth) {
            for(int h = 0; h < super.displayString.length(); ++h) {
               char c = super.displayString.charAt(h);
               text = text + c;
               if((float)mc.fontRendererObj.getStringWidth(text) > maxWidth) {
                  break;
               }
            }

            text = text + "...";
         } else {
            text = super.displayString;
         }

         if(hover) {
            text = "§n" + text;
         }

         this.drawCenteredString(mc.fontRendererObj, text, super.xPosition + super.width / 2, super.yPosition + (super.height - 8) / 2, l);
      }
   }

   public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
      int value = this.getValue();
      boolean bo = super.mousePressed(minecraft, mouseX, mouseY);
      if(bo && super.display != null && super.display.length != 0) {
         boolean hoverL = mouseX >= super.xPosition && mouseY >= super.yPosition && mouseX < super.xPosition + 11 && mouseY < super.yPosition + super.height;
         boolean hoverR = !hoverL && mouseX >= super.xPosition + 11 && mouseY >= super.yPosition && mouseX < super.xPosition + super.width && mouseY < super.yPosition + super.height;
         if(hoverR) {
            value = (value + 1) % super.display.length;
         }

         if(hoverL) {
            if(value <= 0) {
               value = super.display.length;
            }

            --value;
         }

         this.setDisplay(value);
      }

      return bo;
   }

}
