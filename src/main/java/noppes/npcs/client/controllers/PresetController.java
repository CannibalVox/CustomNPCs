package noppes.npcs.client.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.LogWriter;
import noppes.npcs.client.controllers.Preset;

public class PresetController {

   public HashMap presets = new HashMap();
   private File dir;
   public static PresetController instance;


   public PresetController(File dir) {
      instance = this;
      this.dir = dir;
      this.load();
   }

   public Preset getPreset(String username) {
      if(this.presets.isEmpty()) {
         this.load();
      }

      return (Preset)this.presets.get(username.toLowerCase());
   }

   public void load() {
      NBTTagCompound compound = this.loadPreset();
      HashMap presets = new HashMap();
      if(compound != null) {
         NBTTagList list = compound.getTagList("Presets", 10);

         for(int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound comp = list.getCompoundTagAt(i);
            Preset preset = new Preset();
            preset.readFromNBT(comp);
            presets.put(preset.name.toLowerCase(), preset);
         }
      }

      Preset.FillDefault(presets);
      this.presets = presets;
   }

   private NBTTagCompound loadPreset() {
      String filename = "presets.dat";

      File e;
      try {
         e = new File(this.dir, filename);
         return !e.exists()?null:CompressedStreamTools.readCompressed(new FileInputStream(e));
      } catch (Exception var4) {
         LogWriter.except(var4);

         try {
            e = new File(this.dir, filename + "_old");
            return !e.exists()?null:CompressedStreamTools.readCompressed(new FileInputStream(e));
         } catch (Exception var3) {
            LogWriter.except(var3);
            return null;
         }
      }
   }

   public void save() {
      NBTTagCompound compound = new NBTTagCompound();
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.presets.values().iterator();

      while(var3.hasNext()) {
         Preset preset = (Preset)var3.next();
         list.appendTag(preset.writeToNBT());
      }

      compound.setTag("Presets", list);
      this.savePreset(compound);
   }

   private void savePreset(NBTTagCompound compound) {
      String filename = "presets.dat";

      try {
         File e = new File(this.dir, filename + "_new");
         File file1 = new File(this.dir, filename + "_old");
         File file2 = new File(this.dir, filename);
         CompressedStreamTools.writeCompressed(compound, new FileOutputStream(e));
         if(file1.exists()) {
            file1.delete();
         }

         file2.renameTo(file1);
         if(file2.exists()) {
            file2.delete();
         }

         e.renameTo(file2);
         if(e.exists()) {
            e.delete();
         }
      } catch (Exception var6) {
         LogWriter.except(var6);
      }

   }

   public void addPreset(Preset preset) {
      while(this.presets.containsKey(preset.name.toLowerCase())) {
         preset.name = preset.name + "_";
      }

      this.presets.put(preset.name.toLowerCase(), preset);
      this.save();
   }

   public void removePreset(String preset) {
      if(preset != null) {
         this.presets.remove(preset.toLowerCase());
         this.save();
      }
   }
}
