package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.util.NBTJsonUtil;

public class QuestController {

   public HashMap categories = new HashMap();
   public HashMap quests = new HashMap();
   public static QuestController instance;
   private int lastUsedCatID = 0;
   private int lastUsedQuestID = 0;


   public QuestController() {
      instance = this;
   }

   public void load() {
      this.categories.clear();
      this.quests.clear();
      this.lastUsedCatID = 0;
      this.lastUsedQuestID = 0;

      File dir;
      try {
         dir = new File(CustomNpcs.getWorldSaveDirectory(), "quests.dat");
         if(dir.exists()) {
            this.loadCategoriesOld(dir);
            dir.delete();
            dir = new File(CustomNpcs.getWorldSaveDirectory(), "quests.dat_old");
            if(dir.exists()) {
               dir.delete();
            }

            return;
         }
      } catch (Exception var10) {
         ;
      }

      dir = this.getDir();
      if(!dir.exists()) {
         dir.mkdir();
      } else {
         File[] var2 = dir.listFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if(file.isDirectory()) {
               QuestCategory category = this.loadCategoryDir(file);
               Iterator ite = category.quests.keySet().iterator();

               while(ite.hasNext()) {
                  int id = ((Integer)ite.next()).intValue();
                  if(id > this.lastUsedQuestID) {
                     this.lastUsedQuestID = id;
                  }

                  Quest quest = (Quest)category.quests.get(Integer.valueOf(id));
                  if(this.quests.containsKey(Integer.valueOf(id))) {
                     LogWriter.error("Duplicate id " + quest.id + " from category " + category.title);
                     ite.remove();
                  } else {
                     this.quests.put(Integer.valueOf(id), quest);
                  }
               }

               ++this.lastUsedCatID;
               category.id = this.lastUsedCatID;
               this.categories.put(Integer.valueOf(category.id), category);
            }
         }
      }

   }

   private QuestCategory loadCategoryDir(File dir) {
      QuestCategory category = new QuestCategory();
      category.title = dir.getName();
      File[] var3 = dir.listFiles();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File file = var3[var5];
         if(file.isFile() && file.getName().endsWith(".json")) {
            try {
               Quest e = new Quest();
               e.id = Integer.parseInt(file.getName().substring(0, file.getName().length() - 5));
               e.readNBTPartial(NBTJsonUtil.LoadFile(file));
               category.quests.put(Integer.valueOf(e.id), e);
               e.category = category;
            } catch (Exception var8) {
               ;
            }
         }
      }

      return category;
   }

   private void loadCategoriesOld(File file) throws Exception {
      HashMap categories = new HashMap();
      HashMap quests = new HashMap();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.readCompressed(new FileInputStream(file));
      this.lastUsedCatID = nbttagcompound1.getInteger("lastID");
      this.lastUsedQuestID = nbttagcompound1.getInteger("lastQuestID");
      NBTTagList list = nbttagcompound1.getTagList("Data", 10);
      if(list != null) {
         for(int i = 0; i < list.tagCount(); ++i) {
            QuestCategory category = new QuestCategory();
            category.readNBT(list.getCompoundTagAt(i));
            categories.put(Integer.valueOf(category.id), category);
            Iterator ita = category.quests.entrySet().iterator();

            while(ita.hasNext()) {
               Entry entry = (Entry)ita.next();
               if(quests.containsKey(Integer.valueOf(((Quest)entry.getValue()).id))) {
                  ita.remove();
               } else {
                  quests.put(Integer.valueOf(((Quest)entry.getValue()).id), entry.getValue());
               }
            }
         }
      }

      this.quests = quests;
      this.categories = categories;
   }

   public void removeCategory(int category) {
      QuestCategory cat = (QuestCategory)this.categories.get(Integer.valueOf(category));
      if(cat != null) {
         File dir = new File(this.getDir(), cat.title);
         if(dir.delete()) {
            Iterator var4 = cat.quests.keySet().iterator();

            while(var4.hasNext()) {
               int dia = ((Integer)var4.next()).intValue();
               this.quests.remove(Integer.valueOf(dia));
            }

            this.categories.remove(Integer.valueOf(category));
         }
      }
   }

   public void saveCategory(QuestCategory category) {
      if(this.categories.containsKey(Integer.valueOf(category.id))) {
         QuestCategory dir = (QuestCategory)this.categories.get(Integer.valueOf(category.id));
         if(!dir.title.equals(category.title)) {
            while(this.containsCategoryName(category.title)) {
               category.title = category.title + "_";
            }

            File newdir = new File(this.getDir(), category.title);
            File olddir = new File(this.getDir(), dir.title);
            if(newdir.exists()) {
               return;
            }

            if(!olddir.renameTo(newdir)) {
               return;
            }
         }

         category.quests = dir.quests;
      } else {
         if(category.id < 0) {
            ++this.lastUsedCatID;
            category.id = this.lastUsedCatID;
         }

         while(this.containsCategoryName(category.title)) {
            category.title = category.title + "_";
         }

         File dir1 = new File(this.getDir(), category.title);
         if(!dir1.exists()) {
            dir1.mkdirs();
         }
      }

      this.categories.put(Integer.valueOf(category.id), category);
   }

   private boolean containsCategoryName(String name) {
      name = name.toLowerCase();
      Iterator var2 = this.categories.values().iterator();

      QuestCategory cat;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         cat = (QuestCategory)var2.next();
      } while(!cat.title.toLowerCase().equals(name));

      return true;
   }

   private boolean containsQuestName(QuestCategory category, Quest quest) {
      Iterator var3 = category.quests.values().iterator();

      Quest q;
      do {
         if(!var3.hasNext()) {
            return false;
         }

         q = (Quest)var3.next();
      } while(q.id == quest.id || !q.title.equalsIgnoreCase(quest.title));

      return true;
   }

   public void saveQuest(int categoryID, Quest quest) {
      QuestCategory category = (QuestCategory)this.categories.get(Integer.valueOf(categoryID));
      if(category != null) {
         for(quest.category = category; this.containsQuestName(quest.category, quest); quest.title = quest.title + "_") {
            ;
         }

         if(quest.id < 0) {
            ++this.lastUsedQuestID;
            quest.id = this.lastUsedQuestID;
         }

         this.quests.put(Integer.valueOf(quest.id), quest);
         category.quests.put(Integer.valueOf(quest.id), quest);
         File dir = new File(this.getDir(), category.title);
         if(!dir.exists()) {
            dir.mkdirs();
         }

         File file = new File(dir, quest.id + ".json_new");
         File file2 = new File(dir, quest.id + ".json");

         try {
            NBTJsonUtil.SaveFile(file, quest.writeToNBTPartial(new NBTTagCompound()));
            if(file2.exists()) {
               file2.delete();
            }

            file.renameTo(file2);
         } catch (Exception var8) {
            var8.printStackTrace();
         }

      }
   }

   public void removeQuest(Quest quest) {
      File file = new File(new File(this.getDir(), quest.category.title), quest.id + ".json");
      if(file.delete()) {
         this.quests.remove(Integer.valueOf(quest.id));
         quest.category.quests.remove(Integer.valueOf(quest.id));
      }
   }

   private File getDir() {
      return new File(CustomNpcs.getWorldSaveDirectory(), "quests");
   }
}
