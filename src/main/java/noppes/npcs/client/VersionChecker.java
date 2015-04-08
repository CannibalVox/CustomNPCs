package noppes.npcs.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentTranslation;

public class VersionChecker extends Thread {

   private int revision = 11;


   public void run() {
      String name = "§2CustomNpcs§f";
      String link = "§9§nClick here";
      String text = name + " installed. For more info " + link;
      if(this.hasUpdate()) {
         text = name + '\u00a7' + "4 update available " + link;
      }

      EntityClientPlayerMP player;
      try {
         player = Minecraft.getMinecraft().thePlayer;
      } catch (NoSuchMethodError var7) {
         return;
      }

      while((player = Minecraft.getMinecraft().thePlayer) == null) {
         try {
            Thread.sleep(2000L);
         } catch (InterruptedException var6) {
            var6.printStackTrace();
         }
      }

      ChatComponentTranslation message = new ChatComponentTranslation(text, new Object[0]);
      message.getChatStyle().setChatClickEvent(new ClickEvent(Action.OPEN_URL, "http://www.kodevelopment.nl/minecraft/customnpcs/"));
      player.addChatMessage(message);
   }

   private boolean hasUpdate() {
      try {
         URL e = new URL("https://dl.dropboxusercontent.com/u/3096920/update/minecraft/1.7/CustomNPCs.txt");
         URLConnection yc = e.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         String inputLine = in.readLine();
         if(inputLine == null) {
            return false;
         } else {
            int newVersion = Integer.parseInt(inputLine);
            return this.revision < newVersion;
         }
      } catch (Exception var6) {
         return false;
      }
   }
}
