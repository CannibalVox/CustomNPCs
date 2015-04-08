package noppes.npcs.controllers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandom;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.controllers.SpawnData;

public class SpawnController {

   public HashMap biomes = new HashMap();
   public ArrayList data = new ArrayList();
   public Random random = new Random();
   public static SpawnController instance;
   private int lastUsedID = 0;


   public SpawnController() {
      instance = this;
      this.loadData();
   }

   private void loadData() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();
      if(saveDir != null) {
         try {
            File e = new File(saveDir, "spawns.dat");
            if(e.exists()) {
               this.loadDataFile(e);
            }
         } catch (Exception var5) {
            try {
               File ee = new File(saveDir, "spawns.dat_old");
               if(ee.exists()) {
                  this.loadDataFile(ee);
               }
            } catch (Exception var4) {
               ;
            }
         }

      }
   }

   private void loadDataFile(File file) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
      this.loadData(var1);
      var1.close();
   }

   public void loadData(DataInputStream stream) throws IOException {
      ArrayList data = new ArrayList();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.read(stream);
      this.lastUsedID = nbttagcompound1.getInteger("lastID");
      NBTTagList nbtlist = nbttagcompound1.getTagList("NPCSpawnData", 10);
      if(nbtlist != null) {
         for(int i = 0; i < nbtlist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbtlist.getCompoundTagAt(i);
            SpawnData spawn = new SpawnData();
            spawn.readNBT(nbttagcompound);
            data.add(spawn);
         }
      }

      this.data = data;
      this.fillBiomeData();
   }

   public NBTTagCompound getNBT() {
      NBTTagList list = new NBTTagList();
      Iterator nbttagcompound = this.data.iterator();

      while(nbttagcompound.hasNext()) {
         SpawnData spawn = (SpawnData)nbttagcompound.next();
         NBTTagCompound nbtfactions = new NBTTagCompound();
         spawn.writeNBT(nbtfactions);
         list.appendTag(nbtfactions);
      }

      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.setInteger("lastID", this.lastUsedID);
      nbttagcompound1.setTag("NPCSpawnData", list);
      return nbttagcompound1;
   }

   public void saveData() {
      try {
         File e = CustomNpcs.getWorldSaveDirectory();
         File file = new File(e, "spawns.dat_new");
         File file1 = new File(e, "spawns.dat_old");
         File file2 = new File(e, "spawns.dat");
         CompressedStreamTools.writeCompressed(this.getNBT(), new FileOutputStream(file));
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
      } catch (Exception var5) {
         LogWriter.except(var5);
      }

   }

   public SpawnData getSpawnData(int id) {
      Iterator var2 = this.data.iterator();

      SpawnData spawn;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         spawn = (SpawnData)var2.next();
      } while(spawn.id != id);

      return spawn;
   }

   public void saveSpawnData(SpawnData spawn) {
      if(spawn.id < 0) {
         spawn.id = this.getUnusedId();
      }

      SpawnData original = this.getSpawnData(spawn.id);
      if(original == null) {
         this.data.add(spawn);
      } else {
         original.readNBT(spawn.writeNBT(new NBTTagCompound()));
      }

      this.fillBiomeData();
      this.saveData();
   }

   private void fillBiomeData() {
      HashMap biomes = new HashMap();
      Iterator var2 = this.data.iterator();

      while(var2.hasNext()) {
         SpawnData spawn = (SpawnData)var2.next();

         Object list;
         for(Iterator var4 = spawn.biomes.iterator(); var4.hasNext(); ((List)list).add(spawn)) {
            String s = (String)var4.next();
            list = (List)biomes.get(s);
            if(list == null) {
               biomes.put(s, list = new ArrayList());
            }
         }
      }

      this.biomes = biomes;
   }

   public int getUnusedId() {
      ++this.lastUsedID;
      return this.lastUsedID;
   }

   public void removeSpawnData(int id) {
      ArrayList data = new ArrayList();
      Iterator var3 = this.data.iterator();

      while(var3.hasNext()) {
         SpawnData spawn = (SpawnData)var3.next();
         if(spawn.id != id) {
            data.add(spawn);
         }
      }

      this.data = data;
      this.fillBiomeData();
      this.saveData();
   }

   public List getSpawnList(String biome) {
      return (List)this.biomes.get(biome);
   }

   public SpawnData getRandomSpawnData(String biome, boolean isAir) {
      List list = this.getSpawnList(biome);
      return list != null && !list.isEmpty()?(SpawnData)WeightedRandom.getRandomItem(this.random, list):null;
   }

   public Map getScroll() {
      HashMap map = new HashMap();
      Iterator var2 = this.data.iterator();

      while(var2.hasNext()) {
         SpawnData spawn = (SpawnData)var2.next();
         map.put(spawn.name, Integer.valueOf(spawn.id));
      }

      return map;
   }
}
