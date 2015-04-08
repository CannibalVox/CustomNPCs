package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GuiNpcButton extends GuiButton {

   protected String[] display;
   private int displayValue;
   public int field_146127_k;


   public GuiNpcButton(int i, int j, int k, String s) {
      super(i, j, k, StatCollector.translateToLocal(s));
      this.displayValue = 0;
      this.field_146127_k = i;
   }

   public GuiNpcButton(int i, int j, int k, String[] display, int val) {
      this(i, j, k, display[val]);
      this.display = display;
      this.displayValue = val;
   }

   public GuiNpcButton(int i, int j, int k, int l, int m, String string) {
      super(i, j, k, l, m, StatCollector.translateToLocal(string));
      this.displayValue = 0;
      this.field_146127_k = i;
   }

   public GuiNpcButton(int i, int j, int k, int l, int m, String[] display, int val) {
      this(i, j, k, l, m, display.length == 0?"":display[val % display.length]);
      this.display = display;
      this.displayValue = display.length == 0?0:val % display.length;
   }

   public void setDisplayText(String text) {
      super.displayString = StatCollector.translateToLocal(text);
   }

   public int getValue() {
      return this.displayValue;
   }

   public void setEnabled(boolean bo) {
      super.enabled = bo;
   }

   public void setVisible(boolean b) {
      super.visible = b;
   }

   public boolean getVisible() {
      return super.visible;
   }

   public void setDisplay(int value) {
      this.displayValue = value;
      this.setDisplayText(this.display[value]);
   }

   public void setTextColor(int color) {
      this.packedFGColour = color;
   }

   public boolean mousePressed(Minecraft minecraft, int i, int j) {
      boolean bo = super.mousePressed(minecraft, i, j);
      if(bo && this.display != null && this.display.length != 0) {
         this.displayValue = (this.displayValue + 1) % this.display.length;
         this.setDisplayText(this.display[this.displayValue]);
      }

      return bo;
   }

   public int getWidth() {
      return super.width;
   }
}
