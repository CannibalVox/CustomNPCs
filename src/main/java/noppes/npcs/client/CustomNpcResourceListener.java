package noppes.npcs.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;

public class CustomNpcResourceListener implements IResourceManagerReloadListener {

   public static int DefaultTextColor = 4210752;


   public void onResourceManagerReload(IResourceManager var1) {
      if(var1 instanceof SimpleReloadableResourceManager) {
         this.createTextureCache(new File(CustomNpcs.Dir, "assets/customnpcs/textures/cache"));
         SimpleReloadableResourceManager simplemanager = (SimpleReloadableResourceManager)var1;
         FolderResourcePack pack = new FolderResourcePack(CustomNpcs.Dir);
         simplemanager.reloadResourcePack(pack);

         try {
            DefaultTextColor = Integer.parseInt(StatCollector.translateToLocal("customnpcs.defaultTextColor"), 16);
         } catch (NumberFormatException var5) {
            DefaultTextColor = 4210752;
         }
      }

   }

   private void createTextureCache(File dir) {
      if(dir != null) {
         this.enlargeTexture("planks_oak", dir);
         this.enlargeTexture("planks_big_oak", dir);
         this.enlargeTexture("planks_birch", dir);
         this.enlargeTexture("planks_jungle", dir);
         this.enlargeTexture("planks_spruce", dir);
         this.enlargeTexture("planks_acacia", dir);
         this.enlargeTexture("iron_block", dir);
         this.enlargeTexture("diamond_block", dir);
         this.enlargeTexture("stone", dir);
         this.enlargeTexture("gold_block", dir);
         this.enlargeTexture("wool_colored_white", dir);
      }
   }

   private void enlargeTexture(String texture, File dir) {
      try {
         IResourceManager e = Minecraft.getMinecraft().getResourceManager();
         ResourceLocation location = new ResourceLocation("textures/blocks/" + texture + ".png");
         BufferedImage bufferedimage = ImageIO.read(e.getResource(location).getInputStream());
         int i = bufferedimage.getWidth();
         int j = bufferedimage.getHeight();
         BufferedImage image = new BufferedImage(i * 4, j * 2, 1);
         Graphics g = image.getGraphics();
         g.drawImage(bufferedimage, 0, 0, (ImageObserver)null);
         g.drawImage(bufferedimage, i, 0, (ImageObserver)null);
         g.drawImage(bufferedimage, i * 2, 0, (ImageObserver)null);
         g.drawImage(bufferedimage, i * 3, 0, (ImageObserver)null);
         g.drawImage(bufferedimage, 0, i, (ImageObserver)null);
         g.drawImage(bufferedimage, i, j, (ImageObserver)null);
         g.drawImage(bufferedimage, i * 2, j, (ImageObserver)null);
         g.drawImage(bufferedimage, i * 3, j, (ImageObserver)null);
         ImageIO.write(image, "png", new File(dir, texture + ".png"));
      } catch (Exception var10) {
         LogWriter.error("Failed caching texture: " + texture, var10);
      }

   }

}
