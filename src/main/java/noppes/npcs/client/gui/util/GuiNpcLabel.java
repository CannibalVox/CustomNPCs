package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.CustomNpcResourceListener;

public class GuiNpcLabel {

   public String label;
   public int x;
   public int y;
   public int color;
   public boolean enabled;
   public int id;


   public GuiNpcLabel(int id, Object label, int x, int y, int color) {
      this.enabled = true;
      this.id = id;
      this.label = StatCollector.translateToLocal(label.toString());
      this.x = x;
      this.y = y;
      this.color = color;
   }

   public GuiNpcLabel(int id, Object label, int x, int y) {
      this(id, label, x, y, CustomNpcResourceListener.DefaultTextColor);
   }

   public void drawLabel(GuiScreen gui, FontRenderer fontRenderer) {
      if(this.enabled) {
         fontRenderer.drawString(this.label, this.x, this.y, this.color);
      }

   }

   public void center(int width) {
      int size = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.label);
      this.x += (width - size) / 2;
   }
}
