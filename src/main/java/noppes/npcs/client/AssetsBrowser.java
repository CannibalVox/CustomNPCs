package noppes.npcs.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.NPCResourceHelper;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import org.apache.commons.lang3.StringUtils;

public class AssetsBrowser {

   public boolean isRoot;
   private int depth;
   private String folder;
   public HashSet folders = new HashSet();
   public HashSet files = new HashSet();
   private String[] extensions;


   public AssetsBrowser(String folder, String[] extensions) {
      this.extensions = extensions;
      this.setFolder(folder);
   }

   public void setFolder(String folder) {
      if(!folder.endsWith("/")) {
         folder = folder + "/";
      }

      this.isRoot = folder.length() <= 1;
      this.folder = "/assets" + folder;
      this.depth = StringUtils.countMatches(this.folder, "/");
      this.getFiles();
   }

   public AssetsBrowser(String[] extensions) {
      this.extensions = extensions;
   }

   private void getFiles() {
      this.folders.clear();
      this.files.clear();
      ResourcePackRepository repos = Minecraft.getMinecraft().getResourcePackRepository();
      SimpleReloadableResourceManager simplemanager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
      Map map = (Map)ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, simplemanager, 2);
      HashSet set = new HashSet();
      Iterator var5 = map.keySet().iterator();

      String mod;
      while(var5.hasNext()) {
         mod = (String)var5.next();
         if(map.get(mod) instanceof FallbackResourceManager) {
            FallbackResourceManager manager = (FallbackResourceManager)map.get(mod);
            List list = (List)ObfuscationReflectionHelper.getPrivateValue(FallbackResourceManager.class, manager, 0);
            Iterator var9 = list.iterator();

            while(var9.hasNext()) {
               IResourcePack pack = (IResourcePack)var9.next();
               if(pack instanceof AbstractResourcePack) {
                  AbstractResourcePack p = (AbstractResourcePack)pack;
                  File file = NPCResourceHelper.getPackFile(p);
                  set.add(file.getAbsolutePath());
               }
            }
         }
      }

      var5 = set.iterator();

      while(var5.hasNext()) {
         mod = (String)var5.next();
         this.progressFile(new File(mod));
      }

      var5 = Loader.instance().getModList().iterator();

      while(var5.hasNext()) {
         ModContainer mod1 = (ModContainer)var5.next();
         if(mod1.getSource().exists()) {
            this.progressFile(mod1.getSource());
         }
      }

   }

   private void progressFile(File file) {
      try {
         if(!file.isDirectory() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip"))) {
            ZipFile e1 = new ZipFile(file);
            Enumeration entries = e1.entries();

            while(entries.hasMoreElements()) {
               ZipEntry zipentry = (ZipEntry)entries.nextElement();
               String entryName = zipentry.getName();
               this.checkFile(entryName);
            }

            e1.close();
         } else if(file.isDirectory()) {
            int e = file.getAbsolutePath().length();
            this.checkFolder(file, e);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   private void checkFolder(File file, int length) {
      File[] files = file.listFiles();
      if(files != null) {
         File[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File f = var4[var6];
            String name = f.getAbsolutePath().substring(length);
            name = name.replace("\\", "/");
            if(!name.startsWith("/")) {
               name = "/" + name;
            }

            if(f.isDirectory() && (this.folder.startsWith(name) || name.startsWith(this.folder))) {
               this.checkFile(name + "/");
               this.checkFolder(f, length);
            } else {
               this.checkFile(name);
            }
         }

      }
   }

   private void checkFile(String name) {
      if(!name.startsWith("/")) {
         name = "/" + name;
      }

      if(name.startsWith(this.folder)) {
         String[] split = name.split("/");
         int count = split.length;
         if(count == this.depth + 1) {
            if(this.validExtension(name)) {
               this.files.add(split[this.depth]);
            }
         } else if(this.depth + 1 < count) {
            this.folders.add(split[this.depth]);
         }

      }
   }

   private boolean validExtension(String entryName) {
      int index = entryName.indexOf(".");
      if(index < 0) {
         return false;
      } else {
         String extension = entryName.substring(index + 1);
         String[] var4 = this.extensions;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String ex = var4[var6];
            if(ex.equalsIgnoreCase(extension)) {
               return true;
            }
         }

         return false;
      }
   }

   public String getAsset(String asset) {
      String[] split = this.folder.split("/");
      if(split.length < 3) {
         return null;
      } else {
         String texture = split[2] + ":";
         texture = texture + this.folder.substring(texture.length() + 8) + asset;
         return texture;
      }
   }

   public static String getRoot(String asset) {
      String mod = "minecraft";
      int index = asset.indexOf(":");
      if(index > 0) {
         mod = asset.substring(0, index);
         asset = asset.substring(index + 1);
      }

      if(asset.startsWith("/")) {
         asset = asset.substring(1);
      }

      String location = "/" + mod + "/" + asset;
      index = location.lastIndexOf("/");
      if(index > 0) {
         location = location.substring(0, index);
      }

      return location;
   }
}
