package noppes.npcs.controllers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.controllers.Faction;

public class FactionController {

   public HashMap factions;
   private static FactionController instance;
   private int lastUsedID = 0;


   public FactionController() {
      instance = this;
      this.factions = new HashMap();
      this.loadFactions();
      if(this.factions.isEmpty()) {
         this.factions.put(Integer.valueOf(0), new Faction(0, "Friendly", '\udd00', 2000));
         this.factions.put(Integer.valueOf(1), new Faction(1, "Neutral", 15916288, 1000));
         this.factions.put(Integer.valueOf(2), new Faction(2, "Aggressive", 14483456, 0));
      }

   }

   public static FactionController getInstance() {
      return instance;
   }

   private void loadFactions() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();
      if(saveDir != null) {
         try {
            File e = new File(saveDir, "factions.dat");
            if(e.exists()) {
               this.loadFactionsFile(e);
            }
         } catch (Exception var5) {
            try {
               File ee = new File(saveDir, "factions.dat_old");
               if(ee.exists()) {
                  this.loadFactionsFile(ee);
               }
            } catch (Exception var4) {
               ;
            }
         }

      }
   }

   private void loadFactionsFile(File file) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
      this.loadFactions(var1);
      var1.close();
   }

   public void loadFactions(DataInputStream stream) throws IOException {
      HashMap factions = new HashMap();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.read(stream);
      this.lastUsedID = nbttagcompound1.getInteger("lastID");
      NBTTagList list = nbttagcompound1.getTagList("NPCFactions", 10);
      if(list != null) {
         for(int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
            Faction faction = new Faction();
            faction.readNBT(nbttagcompound);
            factions.put(Integer.valueOf(faction.id), faction);
         }
      }

      this.factions = factions;
   }

   public NBTTagCompound getNBT() {
      NBTTagList list = new NBTTagList();
      Iterator nbttagcompound = this.factions.keySet().iterator();

      while(nbttagcompound.hasNext()) {
         int slot = ((Integer)nbttagcompound.next()).intValue();
         Faction faction = (Faction)this.factions.get(Integer.valueOf(slot));
         NBTTagCompound nbtfactions = new NBTTagCompound();
         faction.writeNBT(nbtfactions);
         list.appendTag(nbtfactions);
      }

      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.setInteger("lastID", this.lastUsedID);
      nbttagcompound1.setTag("NPCFactions", list);
      return nbttagcompound1;
   }

   public void saveFactions() {
      try {
         File e = CustomNpcs.getWorldSaveDirectory();
         File file = new File(e, "factions.dat_new");
         File file1 = new File(e, "factions.dat_old");
         File file2 = new File(e, "factions.dat");
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

   public Faction getFaction(int faction) {
      return (Faction)this.factions.get(Integer.valueOf(faction));
   }

   public void saveFaction(Faction faction) {
      if(faction.id < 0) {
         for(faction.id = this.getUnusedId(); this.hasName(faction.name); faction.name = faction.name + "_") {
            ;
         }
      } else {
         Faction existing = (Faction)this.factions.get(Integer.valueOf(faction.id));
         if(existing != null && !existing.name.equals(faction.name)) {
            while(this.hasName(faction.name)) {
               faction.name = faction.name + "_";
            }
         }
      }

      this.factions.remove(Integer.valueOf(faction.id));
      this.factions.put(Integer.valueOf(faction.id), faction);
      this.saveFactions();
   }

   public int getUnusedId() {
      if(this.lastUsedID == 0) {
         Iterator var1 = this.factions.keySet().iterator();

         while(var1.hasNext()) {
            int catid = ((Integer)var1.next()).intValue();
            if(catid > this.lastUsedID) {
               this.lastUsedID = catid;
            }
         }
      }

      ++this.lastUsedID;
      return this.lastUsedID;
   }

   public void removeFaction(int id) {
      if(id >= 0 && this.factions.size() > 1) {
         this.factions.remove(Integer.valueOf(id));
         this.saveFactions();
      }
   }

   public int getFirstFactionId() {
      return ((Integer)this.factions.keySet().iterator().next()).intValue();
   }

   public Faction getFirstFaction() {
      return (Faction)this.factions.values().iterator().next();
   }

   public boolean hasName(String newName) {
      if(newName.trim().isEmpty()) {
         return true;
      } else {
         Iterator var2 = this.factions.values().iterator();

         Faction faction;
         do {
            if(!var2.hasNext()) {
               return false;
            }

            faction = (Faction)var2.next();
         } while(!faction.name.equals(newName));

         return true;
      }
   }

   public Faction getFactionFromName(String factioname) {
      Iterator var2 = getInstance().factions.entrySet().iterator();

      Entry entryfaction;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         entryfaction = (Entry)var2.next();
      } while(!((Faction)entryfaction.getValue()).name.equalsIgnoreCase(factioname));

      return (Faction)entryfaction.getValue();
   }

   public String[] getNames() {
      String[] names = new String[this.factions.size()];
      int i = 0;

      for(Iterator var3 = this.factions.values().iterator(); var3.hasNext(); ++i) {
         Faction faction = (Faction)var3.next();
         names[i] = faction.name.toLowerCase();
      }

      return names;
   }
}
