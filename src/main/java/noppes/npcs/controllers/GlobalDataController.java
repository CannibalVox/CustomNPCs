package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomNpcs;

public class GlobalDataController {

   public static GlobalDataController instance;
   private int itemGiverId = 0;


   public GlobalDataController() {
      instance = this;
      this.load();
   }

   private void load() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();

      try {
         File e = new File(saveDir, "global.dat");
         if(e.exists()) {
            this.loadData(e);
         }
      } catch (Exception var5) {
         try {
            File ee = new File(saveDir, "global.dat_old");
            if(ee.exists()) {
               this.loadData(ee);
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   private void loadData(File file) throws Exception {
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.readCompressed(new FileInputStream(file));
      this.itemGiverId = nbttagcompound1.getInteger("itemGiverId");
   }

   public void saveData() {
      try {
         File e = CustomNpcs.getWorldSaveDirectory();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setInteger("itemGiverId", this.itemGiverId);
         File file = new File(e, "global.dat_new");
         File file1 = new File(e, "global.dat_old");
         File file2 = new File(e, "global.dat");
         CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file));
         if(file1.exists()) {
            file1.delete();
         }

         file2.renameTo(file1);
         if(file2.exists()) {
            file2.delete();
         }

         file.renameTo(file2);
         if(file.exists()) {
            file.delete();
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public int incrementItemGiverId() {
      ++this.itemGiverId;
      this.saveData();
      return this.itemGiverId;
   }
}
