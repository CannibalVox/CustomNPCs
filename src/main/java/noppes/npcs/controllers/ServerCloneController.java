package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.util.NBTJsonUtil;

public class ServerCloneController {

   public static ServerCloneController Instance;


   public ServerCloneController() {
      this.loadClones();
   }

   private void loadClones() {
      try {
         File e = new File(CustomNpcs.getWorldSaveDirectory(), "clonednpcs.dat");
         if(e.exists()) {
            Map clones = this.loadOldClones(e);
            e.delete();
            e = new File(CustomNpcs.getWorldSaveDirectory(), "clonednpcs.dat_old");
            if(e.exists()) {
               e.delete();
            }

            Iterator var3 = clones.keySet().iterator();

            while(var3.hasNext()) {
               int tab = ((Integer)var3.next()).intValue();
               Map map = (Map)clones.get(Integer.valueOf(tab));
               Iterator var6 = map.keySet().iterator();

               while(var6.hasNext()) {
                  String name = (String)var6.next();
                  this.saveClone(tab, name, (NBTTagCompound)map.get(name));
               }
            }
         }
      } catch (Exception var8) {
         LogWriter.except(var8);
      }

   }

   public File getDir() {
      File dir = new File(CustomNpcs.getWorldSaveDirectory(), "clones");
      if(!dir.exists()) {
         dir.mkdir();
      }

      return dir;
   }

   private Map loadOldClones(File file) throws Exception {
      HashMap clones = new HashMap();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.readCompressed(new FileInputStream(file));
      NBTTagList list = nbttagcompound1.getTagList("Data", 10);
      if(list == null) {
         return clones;
      } else {
         for(int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound compound = list.getCompoundTagAt(i);
            if(!compound.hasKey("ClonedTab")) {
               compound.setInteger("ClonedTab", 1);
            }

            Object tab = (Map)clones.get(Integer.valueOf(compound.getInteger("ClonedTab")));
            if(tab == null) {
               clones.put(Integer.valueOf(compound.getInteger("ClonedTab")), tab = new HashMap());
            }

            String name = compound.getString("ClonedName");

            for(int number = 1; ((Map)tab).containsKey(name); name = String.format("%s%s", new Object[]{compound.getString("ClonedName"), Integer.valueOf(number)})) {
               ++number;
            }

            compound.removeTag("ClonedName");
            compound.removeTag("ClonedTab");
            compound.removeTag("ClonedDate");
            this.cleanTags(compound);
            ((Map)tab).put(name, compound);
         }

         return clones;
      }
   }

   public NBTTagCompound getCloneData(ICommandSender player, String name, int tab) {
      File file = new File(new File(this.getDir(), tab + ""), name + ".json");
      if(!file.exists()) {
         if(player != null) {
            player.addChatMessage(new ChatComponentText("Could not find clone file"));
         }

         return null;
      } else {
         try {
            return NBTJsonUtil.LoadFile(file);
         } catch (Exception var6) {
            LogWriter.except(var6);
            if(player != null) {
               player.addChatMessage(new ChatComponentText(var6.getMessage()));
            }

            return null;
         }
      }
   }

   public void saveClone(int tab, String name, NBTTagCompound compound) {
      try {
         File e = new File(this.getDir(), tab + "");
         if(!e.exists()) {
            e.mkdir();
         }

         String filename = name + ".json";
         File file = new File(e, filename + "_new");
         File file2 = new File(e, filename);
         NBTJsonUtil.SaveFile(file, compound);
         if(file2.exists()) {
            file2.delete();
         }

         file.renameTo(file2);
      } catch (Exception var8) {
         LogWriter.except(var8);
      }

   }

   public List getClones(int tab) {
      ArrayList list = new ArrayList();
      File dir = new File(this.getDir(), tab + "");
      if(dir.exists() && dir.isDirectory()) {
         String[] var4 = dir.list();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String file = var4[var6];
            if(file.endsWith(".json")) {
               list.add(file.substring(0, file.length() - 5));
            }
         }

         return list;
      } else {
         return list;
      }
   }

   public boolean removeClone(String name, int tab) {
      File file = new File(new File(this.getDir(), tab + ""), name + ".json");
      if(!file.exists()) {
         return false;
      } else {
         file.delete();
         return true;
      }
   }

   public String addClone(NBTTagCompound nbttagcompound, String name, int tab) {
      this.cleanTags(nbttagcompound);
      this.saveClone(tab, name, nbttagcompound);
      return name;
   }

   public void cleanTags(NBTTagCompound nbttagcompound) {
      if(nbttagcompound.hasKey("ItemGiverId")) {
         nbttagcompound.setInteger("ItemGiverId", 0);
      }

      if(nbttagcompound.hasKey("TransporterId")) {
         nbttagcompound.setInteger("TransporterId", -1);
      }

      nbttagcompound.removeTag("StartPosNew");
      nbttagcompound.removeTag("StartPos");
      nbttagcompound.removeTag("MovingPathNew");
      nbttagcompound.removeTag("Pos");
      nbttagcompound.removeTag("Riding");
      if(!nbttagcompound.hasKey("ModRev")) {
         nbttagcompound.setInteger("ModRev", 1);
      }

      NBTTagCompound adv;
      if(nbttagcompound.hasKey("TransformRole")) {
         adv = nbttagcompound.getCompoundTag("TransformRole");
         adv.setInteger("TransporterId", -1);
         nbttagcompound.setTag("TransformRole", adv);
      }

      if(nbttagcompound.hasKey("TransformJob")) {
         adv = nbttagcompound.getCompoundTag("TransformJob");
         adv.setInteger("ItemGiverId", 0);
         nbttagcompound.setTag("TransformJob", adv);
      }

      if(nbttagcompound.hasKey("TransformAI")) {
         adv = nbttagcompound.getCompoundTag("TransformAI");
         adv.removeTag("StartPosNew");
         adv.removeTag("StartPos");
         adv.removeTag("MovingPathNew");
         nbttagcompound.setTag("TransformAI", adv);
      }

   }
}
