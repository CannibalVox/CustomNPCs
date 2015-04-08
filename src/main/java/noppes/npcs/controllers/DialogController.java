package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.util.NBTJsonUtil;

public class DialogController {

   public HashMap categories = new HashMap();
   public HashMap dialogs = new HashMap();
   public static DialogController instance;
   private int lastUsedDialogID = 0;
   private int lastUsedCatID = 0;


   public DialogController() {
      instance = this;
      this.load();
   }

   public void load() {
      LogWriter.info("Loading Dialogs");
      this.loadCategories();
      LogWriter.info("Done loading Dialogs");
   }

   private void loadCategories() {
      this.categories.clear();
      this.dialogs.clear();
      this.lastUsedCatID = 0;
      this.lastUsedDialogID = 0;

      File dir;
      try {
         dir = new File(CustomNpcs.getWorldSaveDirectory(), "dialog.dat");
         if(dir.exists()) {
            this.loadCategoriesOld(dir);
            dir.delete();
            dir = new File(CustomNpcs.getWorldSaveDirectory(), "dialog.dat_old");
            if(dir.exists()) {
               dir.delete();
            }

            return;
         }
      } catch (Exception var10) {
         LogWriter.except(var10);
      }

      dir = this.getDir();
      if(!dir.exists()) {
         dir.mkdir();
         this.loadDefaultDialogs();
      } else {
         File[] var2 = dir.listFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if(file.isDirectory()) {
               DialogCategory category = this.loadCategoryDir(file);
               Iterator ite = category.dialogs.keySet().iterator();

               while(ite.hasNext()) {
                  int id = ((Integer)ite.next()).intValue();
                  if(id > this.lastUsedDialogID) {
                     this.lastUsedDialogID = id;
                  }

                  Dialog dialog = (Dialog)category.dialogs.get(Integer.valueOf(id));
                  if(this.dialogs.containsKey(Integer.valueOf(id))) {
                     LogWriter.error("Duplicate id " + dialog.id + " from category " + category.title);
                     ite.remove();
                  } else {
                     this.dialogs.put(Integer.valueOf(id), dialog);
                  }
               }

               ++this.lastUsedCatID;
               category.id = this.lastUsedCatID;
               this.categories.put(Integer.valueOf(category.id), category);
            }
         }
      }

   }

   private DialogCategory loadCategoryDir(File dir) {
      DialogCategory category = new DialogCategory();
      category.title = dir.getName();
      File[] var3 = dir.listFiles();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File file = var3[var5];
         if(file.isFile() && file.getName().endsWith(".json")) {
            try {
               Dialog e = new Dialog();
               e.id = Integer.parseInt(file.getName().substring(0, file.getName().length() - 5));
               e.readNBTPartial(NBTJsonUtil.LoadFile(file));
               category.dialogs.put(Integer.valueOf(e.id), e);
               e.category = category;
            } catch (Exception var8) {
               ;
            }
         }
      }

      return category;
   }

   private void loadCategoriesOld(File file) throws Exception {
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.readCompressed(new FileInputStream(file));
      NBTTagList list = nbttagcompound1.getTagList("Data", 10);
      if(list != null) {
         for(int i = 0; i < list.tagCount(); ++i) {
            DialogCategory category = new DialogCategory();
            category.readNBT(list.getCompoundTagAt(i));
            this.saveCategory(category);
            Iterator ita = category.dialogs.entrySet().iterator();

            while(ita.hasNext()) {
               Entry entry = (Entry)ita.next();
               Dialog dialog = (Dialog)entry.getValue();
               dialog.category = category;
               if(this.dialogs.containsKey(Integer.valueOf(dialog.id))) {
                  ita.remove();
               } else {
                  this.saveDialog(category.id, dialog);
               }
            }
         }

      }
   }

   private void loadDefaultDialogs() {
      DialogCategory cat = new DialogCategory();
      cat.id = this.lastUsedCatID++;
      cat.title = "Villager";
      Dialog dia1 = new Dialog();
      dia1.id = 1;
      dia1.category = cat;
      dia1.title = "Start";
      dia1.text = "Hello {player}, \n\nWelcome to our village. I hope you enjoy your stay";
      Dialog dia2 = new Dialog();
      dia2.id = 2;
      dia2.category = cat;
      dia2.title = "Ask about village";
      dia2.text = "This village has been around for ages. Enjoy your stay here.";
      Dialog dia3 = new Dialog();
      dia3.id = 3;
      dia3.category = cat;
      dia3.title = "Who are you";
      dia3.text = "I\'m a villager here. I have lived in this village my whole life.";
      cat.dialogs.put(Integer.valueOf(dia1.id), dia1);
      cat.dialogs.put(Integer.valueOf(dia2.id), dia2);
      cat.dialogs.put(Integer.valueOf(dia3.id), dia3);
      DialogOption option = new DialogOption();
      option.title = "Tell me something about this village";
      option.dialogId = 2;
      option.optionType = EnumOptionType.DialogOption;
      DialogOption option2 = new DialogOption();
      option2.title = "Who are you?";
      option2.dialogId = 3;
      option2.optionType = EnumOptionType.DialogOption;
      DialogOption option3 = new DialogOption();
      option3.title = "Goodbye";
      option3.optionType = EnumOptionType.QuitOption;
      dia1.options.put(Integer.valueOf(0), option2);
      dia1.options.put(Integer.valueOf(1), option);
      dia1.options.put(Integer.valueOf(2), option3);
      DialogOption option4 = new DialogOption();
      option4.title = "Back";
      option4.dialogId = 1;
      dia2.options.put(Integer.valueOf(1), option4);
      dia3.options.put(Integer.valueOf(1), option4);
      this.lastUsedDialogID = 3;
      this.saveCategory(cat);
      this.saveDialog(cat.id, dia1);
      this.saveDialog(cat.id, dia2);
      this.saveDialog(cat.id, dia3);
   }

   public void saveCategory(DialogCategory category) {
      if(this.categories.containsKey(Integer.valueOf(category.id))) {
         DialogCategory dir = (DialogCategory)this.categories.get(Integer.valueOf(category.id));
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

         category.dialogs = dir.dialogs;
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

   public void removeCategory(int category) {
      DialogCategory cat = (DialogCategory)this.categories.get(Integer.valueOf(category));
      if(cat != null) {
         File dir = new File(this.getDir(), cat.title);
         if(dir.delete()) {
            Iterator var4 = cat.dialogs.keySet().iterator();

            while(var4.hasNext()) {
               int dia = ((Integer)var4.next()).intValue();
               this.dialogs.remove(Integer.valueOf(dia));
            }

            this.categories.remove(Integer.valueOf(category));
         }
      }
   }

   private boolean containsCategoryName(String name) {
      name = name.toLowerCase();
      Iterator var2 = this.categories.values().iterator();

      DialogCategory cat;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         cat = (DialogCategory)var2.next();
      } while(!cat.title.toLowerCase().equals(name));

      return true;
   }

   private boolean containsDialogName(DialogCategory category, Dialog dialog) {
      Iterator var3 = category.dialogs.values().iterator();

      Dialog dia;
      do {
         if(!var3.hasNext()) {
            return false;
         }

         dia = (Dialog)var3.next();
      } while(dia.id == dialog.id || !dia.title.equalsIgnoreCase(dialog.title));

      return true;
   }

   public Dialog saveDialog(int categoryId, Dialog dialog) {
      DialogCategory category = (DialogCategory)this.categories.get(Integer.valueOf(categoryId));
      if(category == null) {
         return dialog;
      } else {
         for(dialog.category = category; this.containsDialogName(dialog.category, dialog); dialog.title = dialog.title + "_") {
            ;
         }

         if(dialog.id < 0) {
            ++this.lastUsedDialogID;
            dialog.id = this.lastUsedDialogID;
         }

         this.dialogs.put(Integer.valueOf(dialog.id), dialog);
         category.dialogs.put(Integer.valueOf(dialog.id), dialog);
         File dir = new File(this.getDir(), category.title);
         if(!dir.exists()) {
            dir.mkdirs();
         }

         File file = new File(dir, dialog.id + ".json_new");
         File file2 = new File(dir, dialog.id + ".json");

         try {
            NBTJsonUtil.SaveFile(file, dialog.writeToNBTPartial(new NBTTagCompound()));
            if(file2.exists()) {
               file2.delete();
            }

            file.renameTo(file2);
         } catch (Exception var8) {
            LogWriter.except(var8);
         }

         return dialog;
      }
   }

   public void removeDialog(Dialog dialog) {
      DialogCategory category = dialog.category;
      File file = new File(new File(this.getDir(), category.title), dialog.id + ".json");
      if(file.delete()) {
         category.dialogs.remove(Integer.valueOf(dialog.id));
         this.dialogs.remove(Integer.valueOf(dialog.id));
      }
   }

   private File getDir() {
      return new File(CustomNpcs.getWorldSaveDirectory(), "dialogs");
   }

   public boolean hasDialog(int dialogId) {
      return this.dialogs.containsKey(Integer.valueOf(dialogId));
   }

   public Map getScroll() {
      HashMap map = new HashMap();
      Iterator var2 = this.categories.values().iterator();

      while(var2.hasNext()) {
         DialogCategory category = (DialogCategory)var2.next();
         map.put(category.title, Integer.valueOf(category.id));
      }

      return map;
   }
}
