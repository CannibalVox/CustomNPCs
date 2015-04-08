package noppes.npcs.client.gui.util;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiHoverText extends GuiScreen {

   private int x;
   private int y;
   public int id;
   protected static final ResourceLocation buttonTextures = new ResourceLocation("customnpcs:textures/gui/info.png");
   private String text;


   public GuiHoverText(int id, String text, int x, int y) {
      this.text = text;
      this.id = id;
      this.x = x;
      this.y = y;
   }

   public void drawScreen(int par1, int par2, float par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.getTextureManager().bindTexture(buttonTextures);
      this.drawTexturedModalRect(this.x, this.y, 0, 0, 12, 12);
      if(this.inArea(this.x, this.y, 12, 12, par1, par2)) {
         ArrayList lines = new ArrayList();
         lines.add(this.text);
         this.drawHoveringText(lines, this.x + 8, this.y + 6, super.fontRendererObj);
         GL11.glDisable(2896);
      }

   }

   public boolean inArea(int x, int y, int width, int height, int mouseX, int mouseY) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

}
