package noppes.npcs;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import noppes.npcs.entity.EntityNPCInterface;

public class NoppesStringUtils {

   public static String formatText(String text, Object ... obs) {
      Object[] var2 = obs;
      int var3 = obs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object ob = var2[var4];
         if(ob instanceof EntityPlayer) {
            String username = ((EntityPlayer)ob).getDisplayName();
            text = text.replace("{player}", username);
            text = text.replace("@p", username);
         } else if(ob instanceof EntityNPCInterface) {
            text = text.replace("@npc", ((EntityNPCInterface)ob).getCommandSenderName());
         }
      }

      text = text.replace("&", Character.toChars(167)[0] + "");
      return text;
   }

   public static void setClipboardContents(String aString) {
      StringSelection stringSelection = new StringSelection(aString);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(stringSelection, new ClipboardOwner() {
         public void lostOwnership(Clipboard arg0, Transferable arg1) {}
      });
   }

   public static String getClipboardContents() {
      String result = "";
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable contents = clipboard.getContents((Object)null);
      boolean hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
      if(hasTransferableText) {
         try {
            result = (String)contents.getTransferData(DataFlavor.stringFlavor);
         } catch (UnsupportedFlavorException var5) {
            System.err.println(var5);
            var5.printStackTrace();
         } catch (IOException var6) {
            System.err.println(var6);
            var6.printStackTrace();
         }
      }

      return result;
   }

   public static int nextNewLine(String s) {
      int index = s.indexOf("\n");
      int indexR = s.indexOf("\r");
      if(indexR < index) {
         ;
      }

      return 0;
   }

   public static String translate(Object ... arr) {
      String s = "";
      Object[] var2 = arr;
      int var3 = arr.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object str = var2[var4];
         s = s + StatCollector.translateToLocal(str.toString());
      }

      return s;
   }
}
