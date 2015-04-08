package noppes.npcs.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.JsonException;
import noppes.npcs.util.NBTJsonUtil;

public class LinkedNpcController {

   public static LinkedNpcController Instance;
   public List list = new ArrayList();


   public LinkedNpcController() {
      Instance = this;
      this.load();
   }

   private void load() {
      try {
         this.loadNpcs();
      } catch (Exception var2) {
         LogWriter.except(var2);
      }

   }

   public File getDir() {
      return new File(CustomNpcs.getWorldSaveDirectory(), "linkednpcs");
   }

   private void loadNpcs() {
      LogWriter.info("Loading Linked Npcs");
      File dir = this.getDir();
      if(!dir.exists()) {
         dir.mkdir();
      }

      ArrayList list = new ArrayList();
      File[] var3 = dir.listFiles();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File file = var3[var5];
         if(file.getName().endsWith(".json")) {
            try {
               NBTTagCompound e = NBTJsonUtil.LoadFile(file);
               LinkedNpcController.LinkedData linked = new LinkedNpcController.LinkedData();
               linked.setNBT(e);
               list.add(linked);
            } catch (Exception var9) {
               LogWriter.error("Error loading: " + file.getName(), var9);
            }
         }
      }

      this.list = list;
      LogWriter.info("Done loading Linked Npcs");
   }

   public void save() {
      Iterator var1 = this.list.iterator();

      while(var1.hasNext()) {
         LinkedNpcController.LinkedData npc = (LinkedNpcController.LinkedData)var1.next();

         try {
            this.saveNpc(npc);
         } catch (IOException var4) {
            LogWriter.except(var4);
         }
      }

   }

   private void saveNpc(LinkedNpcController.LinkedData npc) throws IOException {
      File file = new File(this.getDir(), npc.name + ".json_new");
      File file1 = new File(this.getDir(), npc.name + ".json");

      try {
         NBTJsonUtil.SaveFile(file, npc.getNBT());
         if(file1.exists()) {
            file1.delete();
         }

         file.renameTo(file1);
      } catch (JsonException var5) {
         LogWriter.except(var5);
      }

   }

   public void loadNpcData(EntityNPCInterface npc) {
      LinkedNpcController.LinkedData data = this.getData(npc.linkedName);
      if(data == null) {
         npc.linkedLast = 0L;
         npc.linkedName = "";
         npc.linkedData = null;
      } else {
         npc.linkedData = data;
         if(npc.posX == 0.0D && npc.posY == 0.0D && npc.posX == 0.0D) {
            return;
         }

         npc.linkedLast = data.time;
         List points = npc.ai.getMovingPath();
         NBTTagCompound compound = NBTTags.NBTMerge(this.readNpcData(npc), data.data);
         npc.display.readToNBT(compound);
         npc.stats.readToNBT(compound);
         npc.advanced.readToNBT(compound);
         npc.inventory.readEntityFromNBT(compound);
         if(compound.hasKey("ModelData")) {
            ((EntityCustomNpc)npc).modelData.readFromNBT(compound.getCompoundTag("ModelData"));
         }

         npc.ai.readToNBT(compound);
         npc.transform.readToNBT(compound);
         npc.ai.setMovingPath(points);
         NoppesUtilServer.updateNpc(npc);
      }

   }

   private void cleanTags(NBTTagCompound compound) {
      compound.removeTag("MovingPathNew");
   }

   public LinkedNpcController.LinkedData getData(String name) {
      Iterator var2 = this.list.iterator();

      LinkedNpcController.LinkedData data;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         data = (LinkedNpcController.LinkedData)var2.next();
      } while(!data.name.equalsIgnoreCase(name));

      return data;
   }

   private NBTTagCompound readNpcData(EntityNPCInterface npc) {
      NBTTagCompound compound = new NBTTagCompound();
      npc.display.writeToNBT(compound);
      npc.inventory.writeEntityToNBT(compound);
      npc.stats.writeToNBT(compound);
      npc.ai.writeToNBT(compound);
      npc.advanced.writeToNBT(compound);
      npc.transform.writeToNBT(compound);
      compound.setTag("ModelData", ((EntityCustomNpc)npc).modelData.writeToNBT());
      return compound;
   }

   public void saveNpcData(EntityNPCInterface npc) {
      NBTTagCompound compound = this.readNpcData(npc);
      this.cleanTags(compound);
      if(!npc.linkedData.data.equals(compound)) {
         npc.linkedData.data = compound;
         npc.linkedData.time = System.currentTimeMillis();
         this.save();
      }
   }

   public void removeData(String name) {
      Iterator ita = this.list.iterator();

      while(ita.hasNext()) {
         if(((LinkedNpcController.LinkedData)ita.next()).name.equalsIgnoreCase(name)) {
            ita.remove();
         }
      }

      this.save();
   }

   public void addData(String name) {
      if(this.getData(name) == null && !name.isEmpty()) {
         LinkedNpcController.LinkedData data = new LinkedNpcController.LinkedData();
         data.name = name;
         this.list.add(data);
         this.save();
      }
   }

   public static class LinkedData {

      public String name = "LinkedNpc";
      public long time = 0L;
      public NBTTagCompound data = new NBTTagCompound();


      public LinkedData() {
         this.time = System.currentTimeMillis();
      }

      public void setNBT(NBTTagCompound compound) {
         this.name = compound.getString("LinkedName");
         this.data = compound.getCompoundTag("NPCData");
      }

      public NBTTagCompound getNBT() {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setString("LinkedName", this.name);
         compound.setTag("NPCData", this.data);
         return compound;
      }
   }
}
