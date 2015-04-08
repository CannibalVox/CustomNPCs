package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.Bank;

public class BankController {

   public HashMap banks;
   private String filePath = "";
   private static BankController instance;


   public BankController() {
      instance = this;
      this.banks = new HashMap();
      this.loadBanks();
      if(this.banks.isEmpty()) {
         Bank bank = new Bank();
         bank.id = 0;
         bank.name = "Default Bank";

         for(int i = 0; i < 6; ++i) {
            bank.slotTypes.put(Integer.valueOf(i), Integer.valueOf(0));
         }

         this.banks.put(Integer.valueOf(bank.id), bank);
      }

   }

   public static BankController getInstance() {
      if(newInstance()) {
         instance = new BankController();
      }

      return instance;
   }

   private static boolean newInstance() {
      if(instance == null) {
         return true;
      } else {
         File file = CustomNpcs.getWorldSaveDirectory();
         return file == null?false:!instance.filePath.equals(file.getAbsolutePath());
      }
   }

   private void loadBanks() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();
      if(saveDir != null) {
         this.filePath = saveDir.getAbsolutePath();

         try {
            File e = new File(saveDir, "bank.dat");
            if(e.exists()) {
               this.loadBanks(e);
            }
         } catch (Exception var5) {
            try {
               File ee = new File(saveDir, "bank.dat_old");
               if(ee.exists()) {
                  this.loadBanks(ee);
               }
            } catch (Exception var4) {
               ;
            }
         }

      }
   }

   private void loadBanks(File file) throws IOException {
      this.loadBanks(CompressedStreamTools.readCompressed(new FileInputStream(file)));
   }

   public void loadBanks(NBTTagCompound nbttagcompound1) throws IOException {
      HashMap banks = new HashMap();
      NBTTagList list = nbttagcompound1.getTagList("Data", 10);
      if(list != null) {
         for(int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
            Bank bank = new Bank();
            bank.readEntityFromNBT(nbttagcompound);
            banks.put(Integer.valueOf(bank.id), bank);
         }
      }

      this.banks = banks;
   }

   public NBTTagCompound getNBT() {
      NBTTagList list = new NBTTagList();
      Iterator nbttagcompound = this.banks.values().iterator();

      while(nbttagcompound.hasNext()) {
         Bank bank = (Bank)nbttagcompound.next();
         NBTTagCompound nbtfactions = new NBTTagCompound();
         bank.writeEntityToNBT(nbtfactions);
         list.appendTag(nbtfactions);
      }

      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.setTag("Data", list);
      return nbttagcompound1;
   }

   public Bank getBank(int bankId) {
      Bank bank = (Bank)this.banks.get(Integer.valueOf(bankId));
      return bank != null?bank:(Bank)this.banks.values().iterator().next();
   }

   public void saveBanks() {
      try {
         File e = CustomNpcs.getWorldSaveDirectory();
         File file = new File(e, "bank.dat_new");
         File file1 = new File(e, "bank.dat_old");
         File file2 = new File(e, "bank.dat");
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
         var5.printStackTrace();
      }

   }

   public void saveBank(Bank bank) {
      if(bank.id < 0) {
         bank.id = this.getUnusedId();
      }

      this.banks.put(Integer.valueOf(bank.id), bank);
      this.saveBanks();
   }

   public int getUnusedId() {
      int id;
      for(id = 0; this.banks.containsKey(Integer.valueOf(id)); ++id) {
         ;
      }

      return id;
   }

   public void removeBank(int bank) {
      if(bank >= 0 && this.banks.size() > 1) {
         this.banks.remove(Integer.valueOf(bank));
         this.saveBanks();
      }
   }
}
