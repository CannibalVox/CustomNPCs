package noppes.npcs.controllers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent.Save;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.controllers.ScriptEntityData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.ScriptAnimal;
import noppes.npcs.scripted.ScriptEntity;
import noppes.npcs.scripted.ScriptLiving;
import noppes.npcs.scripted.ScriptLivingBase;
import noppes.npcs.scripted.ScriptMonster;
import noppes.npcs.scripted.ScriptPlayer;
import noppes.npcs.scripted.ScriptWorld;
import noppes.npcs.util.NBTJsonUtil;

public class ScriptController {

   public static ScriptController Instance;
   public static boolean HasStart = false;
   private ScriptEngineManager manager;
   public Map languages = new HashMap();
   public Map scripts = new HashMap();
   public long lastLoaded = 0L;
   public File dir;
   public NBTTagCompound compound = new NBTTagCompound();
   private boolean loaded = false;
   public boolean shouldSave = false;


   public ScriptController() {
      this.loaded = false;
      Instance = this;
      this.manager = new ScriptEngineManager();
      LogWriter.info("Script Engines Available:");
      Iterator var1 = this.manager.getEngineFactories().iterator();

      while(var1.hasNext()) {
         ScriptEngineFactory fac = (ScriptEngineFactory)var1.next();
         if(!fac.getExtensions().isEmpty()) {
            this.manager.getEngineByName(fac.getLanguageName());
            String ext = "." + ((String)fac.getExtensions().get(0)).toLowerCase();
            LogWriter.info(fac.getLanguageName() + ": " + ext);
            this.languages.put(fac.getLanguageName(), ext);
         }
      }

   }

   private void loadCategories() {
      if(!this.getSavedFile().exists()) {
         this.shouldSave = true;
      }

      (new ScriptWorld((WorldServer)null)).clearTempData();
      this.dir = new File(CustomNpcs.getWorldSaveDirectory(), "scripts");
      if(!this.dir.exists()) {
         this.dir.mkdir();
      }

      this.scripts.clear();
      Iterator var1 = this.languages.keySet().iterator();

      while(var1.hasNext()) {
         String language = (String)var1.next();
         String ext = (String)this.languages.get(language);
         File scriptDir = new File(this.dir, language.toLowerCase());
         if(!scriptDir.exists()) {
            scriptDir.mkdir();
         } else {
            this.loadDir(scriptDir, "", ext);
         }
      }

      this.lastLoaded = System.currentTimeMillis();
   }

   private void loadDir(File dir, String name, String ext) {
      File[] var4 = dir.listFiles();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File file = var4[var6];
         String filename = name + file.getName().toLowerCase();
         if(file.isDirectory()) {
            this.loadDir(file, filename + "/", ext);
         } else if(filename.endsWith(ext)) {
            try {
               this.scripts.put(filename, this.readFile(file));
            } catch (IOException var10) {
               var10.printStackTrace();
            }
         }
      }

   }

   public boolean loadStoredData() {
      try {
         this.loadCategories();
         File e = this.getSavedFile();
         if(!e.exists()) {
            return false;
         } else {
            this.compound = NBTJsonUtil.LoadFile(e);
            this.shouldSave = false;
            return true;
         }
      } catch (Exception var2) {
         LogWriter.except(var2);
         return false;
      }
   }

   private File getSavedFile() {
      return new File(this.dir, "world_data.json");
   }

   private String readFile(File file) throws IOException {
      BufferedReader br = new BufferedReader(new FileReader(file));

      try {
         StringBuilder sb = new StringBuilder();

         for(String line = br.readLine(); line != null; line = br.readLine()) {
            sb.append(line);
            sb.append("\n");
         }

         String var5 = sb.toString();
         return var5;
      } finally {
         br.close();
      }
   }

   public ScriptEngine getEngineByName(String language) {
      return this.manager.getEngineByName(language);
   }

   public NBTTagList nbtLanguages() {
      NBTTagList list = new NBTTagList();
      Iterator var2 = this.languages.keySet().iterator();

      while(var2.hasNext()) {
         String language = (String)var2.next();
         NBTTagCompound compound = new NBTTagCompound();
         NBTTagList scripts = new NBTTagList();
         Iterator var6 = this.getScripts(language).iterator();

         while(var6.hasNext()) {
            String script = (String)var6.next();
            scripts.appendTag(new NBTTagString(script));
         }

         compound.setTag("Scripts", scripts);
         compound.setString("Language", language);
         list.appendTag(compound);
      }

      return list;
   }

   private List getScripts(String language) {
      ArrayList list = new ArrayList();
      String ext = (String)this.languages.get(language);
      if(ext == null) {
         return list;
      } else {
         Iterator var4 = this.scripts.keySet().iterator();

         while(var4.hasNext()) {
            String script = (String)var4.next();
            if(script.endsWith(ext)) {
               list.add(script);
            }
         }

         return list;
      }
   }

   public ScriptEntity getScriptForEntity(Entity entity) {
      if(entity == null) {
         return null;
      } else if(entity instanceof EntityNPCInterface) {
         return ((EntityNPCInterface)entity).script.dummyNpc;
      } else {
         ScriptEntityData data = (ScriptEntityData)entity.getExtendedProperties("ScriptedObject");
         if(data != null) {
            return data.base;
         } else {
            if(entity instanceof EntityPlayerMP) {
               data = new ScriptEntityData(new ScriptPlayer((EntityPlayerMP)entity));
            } else if(entity instanceof EntityAnimal) {
               data = new ScriptEntityData(new ScriptAnimal((EntityAnimal)entity));
            } else if(entity instanceof EntityMob) {
               data = new ScriptEntityData(new ScriptMonster((EntityMob)entity));
            } else if(entity instanceof EntityLiving) {
               data = new ScriptEntityData(new ScriptLiving((EntityLiving)entity));
            } else if(entity instanceof EntityLivingBase) {
               data = new ScriptEntityData(new ScriptLivingBase((EntityLivingBase)entity));
            } else {
               data = new ScriptEntityData(new ScriptEntity(entity));
            }

            entity.registerExtendedProperties("ScriptedObject", data);
            return data.base;
         }
      }
   }

   @SubscribeEvent
   public void saveWorld(Save event) {
      if(this.shouldSave && !event.world.isRemote && event.world == MinecraftServer.getServer().worldServers[0]) {
         try {
            NBTJsonUtil.SaveFile(this.getSavedFile(), this.compound);
         } catch (Exception var3) {
            LogWriter.except(var3);
         }

         this.shouldSave = false;
      }
   }

}
