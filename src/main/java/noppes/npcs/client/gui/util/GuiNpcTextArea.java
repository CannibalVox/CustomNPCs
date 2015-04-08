package noppes.npcs.client.gui.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNpcTextArea extends GuiNpcTextField {

   public boolean inMenu = true;
   public boolean numbersOnly = false;
   private int posX;
   private int posY;
   private int width;
   private int height;
   private int cursorCounter;
   private FontRenderer fontrenderer;
   private int cursorPosition = 0;
   private int listHeight;
   private float scrolledY = 0.0F;
   private int startClick = -1;
   private boolean clickVerticalBar = false;
   private boolean wrapLine = true;


   public GuiNpcTextArea(int id, GuiScreen guiscreen, FontRenderer fontrenderer, int i, int j, int k, int l, String s) {
      super(id, guiscreen, fontrenderer, i, j, k, l, s);
      this.posX = i;
      this.posY = j;
      this.width = k;
      this.listHeight = this.height = l;
      this.fontrenderer = fontrenderer;
      this.setMaxStringLength(Integer.MAX_VALUE);
      this.setText(s);
   }

   public void updateCursorCounter() {
      ++this.cursorCounter;
   }

   public boolean textboxKeyTyped(char c, int i) {
      if(this.isFocused() && super.canEdit) {
         String originalText = this.getText();
         this.setText(originalText);
         if(c == 13 || c == 10) {
            this.setText(originalText.substring(0, this.cursorPosition) + c + originalText.substring(this.cursorPosition));
         }

         this.setCursorPositionZero();
         this.moveCursorBy(this.cursorPosition);
         boolean bo = super.textboxKeyTyped(c, i);
         String newText = this.getText();
         if(i != 211) {
            this.cursorPosition += newText.length() - originalText.length();
         }

         if(i == 203 && this.cursorPosition > 0) {
            --this.cursorPosition;
         }

         if(i == 205 && this.cursorPosition < newText.length()) {
            ++this.cursorPosition;
         }

         return bo;
      } else {
         return false;
      }
   }

   public void mouseClicked(int i, int j, int k) {
      boolean wasFocused = this.isFocused();
      super.mouseClicked(i, j, k);
      if(this.hoverVerticalScrollBar(i, j)) {
         this.clickVerticalBar = true;
         this.startClick = -1;
      } else if(k == 0 && super.canEdit) {
         int x = i - this.posX;
         int y = (j - this.posY - 4) / this.fontrenderer.FONT_HEIGHT + this.getStartLineY();
         this.cursorPosition = 0;
         List lines = this.getLines();
         int charCount = 0;
         int lineCount = 0;
         int maxSize = this.width - (this.isScrolling()?14:4);

         for(int g = 0; g < lines.size(); ++g) {
            String wholeLine = (String)lines.get(g);
            String line = "";
            char[] var14 = wholeLine.toCharArray();
            int var15 = var14.length;

            for(int var16 = 0; var16 < var15; ++var16) {
               char c = var14[var16];
               this.cursorPosition = charCount;
               if(this.fontrenderer.getStringWidth(line + c) > maxSize && this.wrapLine) {
                  ++lineCount;
                  line = "";
                  if(y < lineCount) {
                     break;
                  }
               }

               if(lineCount == y && x <= this.fontrenderer.getStringWidth(line + c)) {
                  return;
               }

               ++charCount;
               line = line + c;
            }

            this.cursorPosition = charCount;
            ++lineCount;
            ++charCount;
            if(y < lineCount) {
               break;
            }
         }

         if(y >= lineCount) {
            this.cursorPosition = this.getText().length();
         }

      }
   }

   private List getLines() {
      ArrayList list = new ArrayList();
      String line = "";
      char[] var3 = this.getText().toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char c = var3[var5];
         if(c != 13 && c != 10) {
            line = line + c;
         } else {
            list.add(line);
            line = "";
         }
      }

      list.add(line);
      return list;
   }

   private int getStartLineY() {
      if(!this.isScrolling()) {
         this.scrolledY = 0.0F;
      }

      return MathHelper.ceiling_double_int((double)(this.scrolledY * (float)this.listHeight / (float)this.fontrenderer.FONT_HEIGHT));
   }

   public void drawTextBox(int mouseX, int mouseY) {
      drawRect(this.posX - 1, this.posY - 1, this.posX + this.width + 1, this.posY + this.height + 1, -6250336);
      drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, -16777216);
      int color = 14737632;
      boolean flag = this.isFocused() && this.cursorCounter / 6 % 2 == 0;
      int startLine = this.getStartLineY();
      int maxLine = this.height / this.fontrenderer.FONT_HEIGHT + startLine;
      List lines = this.getLines();
      int charCount = 0;
      int lineCount = 0;
      int maxSize = this.width - (this.isScrolling()?14:4);

      int k2;
      for(k2 = 0; k2 < lines.size(); ++k2) {
         String wholeLine = (String)lines.get(k2);
         String line = "";
         char[] xx = wholeLine.toCharArray();
         int yy = xx.length;

         for(int var16 = 0; var16 < yy; ++var16) {
            char c = xx[var16];
            if(this.fontrenderer.getStringWidth(line + c) > maxSize && this.wrapLine) {
               if(lineCount >= startLine && lineCount < maxLine) {
                  this.drawString(this.fontrenderer, line, this.posX + 4, this.posY + 4 + (lineCount - startLine) * this.fontrenderer.FONT_HEIGHT, color);
               }

               line = "";
               ++lineCount;
            }

            if(flag && charCount == this.cursorPosition && lineCount >= startLine && lineCount < maxLine && super.canEdit) {
               int xx1 = this.posX + this.fontrenderer.getStringWidth(line) + 3;
               int yy1 = this.posY + (lineCount - startLine) * this.fontrenderer.FONT_HEIGHT + 4;
               if(this.getText().length() == this.cursorPosition) {
                  this.fontrenderer.drawString("_", xx1, yy1, color);
               } else {
                  this.drawCursorVertical(xx1, yy1, xx1 + 1, yy1 + this.fontrenderer.FONT_HEIGHT);
               }
            }

            ++charCount;
            line = line + c;
         }

         if(lineCount >= startLine && lineCount < maxLine) {
            this.drawString(this.fontrenderer, line, this.posX + 4, this.posY + 4 + (lineCount - startLine) * this.fontrenderer.FONT_HEIGHT, color);
            if(flag && charCount == this.cursorPosition && super.canEdit) {
               int var20 = this.posX + this.fontrenderer.getStringWidth(line) + 3;
               yy = this.posY + (lineCount - startLine) * this.fontrenderer.FONT_HEIGHT + 4;
               if(this.getText().length() == this.cursorPosition) {
                  this.fontrenderer.drawString("_", var20, yy, color);
               } else {
                  this.drawCursorVertical(var20, yy, var20 + 1, yy + this.fontrenderer.FONT_HEIGHT);
               }
            }
         }

         ++lineCount;
         ++charCount;
      }

      k2 = Mouse.getDWheel();
      if(k2 != 0 && this.isFocused()) {
         this.addScrollY(k2 < 0?-10:10);
      }

      if(Mouse.isButtonDown(0)) {
         if(this.clickVerticalBar) {
            if(this.startClick >= 0) {
               this.addScrollY(this.startClick - (mouseY - this.posY));
            }

            if(this.hoverVerticalScrollBar(mouseX, mouseY)) {
               this.startClick = mouseY - this.posY;
            }

            this.startClick = mouseY - this.posY;
         }
      } else {
         this.clickVerticalBar = false;
      }

      this.listHeight = lineCount * this.fontrenderer.FONT_HEIGHT;
      this.drawVerticalScrollBar();
   }

   private boolean isScrolling() {
      return this.listHeight > this.height - 4;
   }

   private void addScrollY(int scrolled) {
      this.scrolledY -= 1.0F * (float)scrolled / (float)this.height;
      if(this.scrolledY < 0.0F) {
         this.scrolledY = 0.0F;
      }

      float max = 1.0F - 1.0F * (float)(this.height + 2) / (float)this.listHeight;
      if(this.scrolledY > max) {
         this.scrolledY = max;
      }

   }

   private boolean hoverVerticalScrollBar(int x, int y) {
      return this.listHeight <= this.height - 4?false:this.posY < y && this.posY + this.height > y && x < this.posX + this.width && x > this.posX + (this.width - 8);
   }

   private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
      int i1;
      if(p_146188_1_ < p_146188_3_) {
         i1 = p_146188_1_;
         p_146188_1_ = p_146188_3_;
         p_146188_3_ = i1;
      }

      if(p_146188_2_ < p_146188_4_) {
         i1 = p_146188_2_;
         p_146188_2_ = p_146188_4_;
         p_146188_4_ = i1;
      }

      if(p_146188_3_ > this.posX + this.width) {
         p_146188_3_ = this.posX + this.width;
      }

      if(p_146188_1_ > this.posX + this.width) {
         p_146188_1_ = this.posX + this.width;
      }

      Tessellator tessellator = Tessellator.instance;
      GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
      GL11.glDisable(3553);
      GL11.glEnable(3058);
      GL11.glLogicOp(5387);
      tessellator.startDrawingQuads();
      tessellator.addVertex((double)p_146188_1_, (double)p_146188_4_, 0.0D);
      tessellator.addVertex((double)p_146188_3_, (double)p_146188_4_, 0.0D);
      tessellator.addVertex((double)p_146188_3_, (double)p_146188_2_, 0.0D);
      tessellator.addVertex((double)p_146188_1_, (double)p_146188_2_, 0.0D);
      tessellator.draw();
      GL11.glDisable(3058);
      GL11.glEnable(3553);
   }

   private int getVerticalBarSize() {
      return (int)(1.0F * (float)this.height / (float)this.listHeight * (float)(this.height - 4));
   }

   private void drawVerticalScrollBar() {
      if(this.listHeight > this.height - 4) {
         Minecraft.getMinecraft().renderEngine.bindTexture(GuiCustomScroll.resource);
         int x = this.posX + this.width - 6;
         int y = (int)((float)this.posY + this.scrolledY * (float)this.height) + 2;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int sbSize = this.getVerticalBarSize();
         this.drawTexturedModalRect(x, y, this.width, 9, 5, 1);

         for(int k = 0; k < sbSize; ++k) {
            this.drawTexturedModalRect(x, y + k, this.width, 10, 5, 1);
         }

         this.drawTexturedModalRect(x, y, this.width, 11, 5, 1);
      }
   }
}
