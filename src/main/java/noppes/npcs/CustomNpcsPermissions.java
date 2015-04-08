package noppes.npcs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.LogWriter;

public class CustomNpcsPermissions {

   public static CustomNpcsPermissions Instance = new CustomNpcsPermissions();
   private Class bukkit;
   private Method getPlayer;
   private Method hasPermission;


   public CustomNpcsPermissions() {
      try {
         this.bukkit = Class.forName("org.bukkit.Bukkit");
         this.getPlayer = this.bukkit.getMethod("getPlayer", new Class[]{String.class});
         this.hasPermission = Class.forName("org.bukkit.entity.Player").getMethod("hasPermission", new Class[]{String.class});
         LogWriter.info("Bukkit permissions enabled");
      } catch (ClassNotFoundException var2) {
         ;
      } catch (NoSuchMethodException var3) {
         var3.printStackTrace();
      } catch (SecurityException var4) {
         var4.printStackTrace();
      }

   }

   public static boolean hasPermission(EntityPlayer player, String permission) {
      return Instance.bukkit != null?Instance.bukkitPermission(player.getCommandSenderName(), permission):true;
   }

   private boolean bukkitPermission(String username, String permission) {
      try {
         Object e = this.getPlayer.invoke((Object)null, new Object[]{username});
         return ((Boolean)this.hasPermission.invoke(e, new Object[]{permission})).booleanValue();
      } catch (IllegalAccessException var4) {
         var4.printStackTrace();
      } catch (IllegalArgumentException var5) {
         var5.printStackTrace();
      } catch (InvocationTargetException var6) {
         var6.printStackTrace();
      }

      return false;
   }

}
