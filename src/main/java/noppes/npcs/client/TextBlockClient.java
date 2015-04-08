package noppes.npcs.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.TextBlock;

public class TextBlockClient extends TextBlock {

   private ChatStyle style;
   public int color;
   public String name;


   public TextBlockClient(String name, String text, int lineWidth, int color, Object ... obs) {
      this(text, lineWidth, obs);
      this.color = color;
      this.name = name;
   }

   public TextBlockClient(String text, int lineWidth, Object ... obs) {
      this.color = 14737632;
      this.style = new ChatStyle();
      text = NoppesStringUtils.formatText(text, obs);
      String line = "";
      text = text.replace("\n", " \n ");
      text = text.replace("\r", " \r ");
      String[] words = text.split(" ");
      FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
      String[] var7 = words;
      int var8 = words.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String word = var7[var9];
         if(!word.isEmpty()) {
            if(word.length() == 1) {
               char newLine = word.charAt(0);
               if(newLine == 13 || newLine == 10) {
                  this.addLine(line);
                  line = "";
                  continue;
               }
            }

            String var12;
            if(line.isEmpty()) {
               var12 = word;
            } else {
               var12 = line + " " + word;
            }

            if(font.getStringWidth(var12) > lineWidth) {
               this.addLine(line);
               line = word.trim();
            } else {
               line = var12;
            }
         }
      }

      if(!line.isEmpty()) {
         this.addLine(line);
      }

   }

   private void addLine(String text) {
      ChatComponentText line = new ChatComponentText(text);
      line.setChatStyle(this.style);
      super.lines.add(line);
   }
}
