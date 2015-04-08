package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.NpcMiscInventory;

public class Bank {

   public int id = -1;
   public String name = "";
   public HashMap slotTypes = new HashMap();
   public int startSlots = 1;
   public int maxSlots = 6;
   public NpcMiscInventory currencyInventory = new NpcMiscInventory(6);
   public NpcMiscInventory upgradeInventory = new NpcMiscInventory(6);


   public Bank() {
      for(int i = 0; i < 6; ++i) {
         this.slotTypes.put(Integer.valueOf(i), Integer.valueOf(0));
      }

   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("BankID", this.id);
      nbttagcompound.setTag("BankCurrency", this.currencyInventory.getToNBT());
      nbttagcompound.setTag("BankUpgrade", this.upgradeInventory.getToNBT());
      nbttagcompound.setString("Username", this.name);
      nbttagcompound.setInteger("MaxSlots", this.maxSlots);
      nbttagcompound.setInteger("StartSlots", this.startSlots);
      nbttagcompound.setTag("BankTypes", NBTTags.nbtIntegerIntegerMap(this.slotTypes));
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.id = nbttagcompound.getInteger("BankID");
      this.name = nbttagcompound.getString("Username");
      this.startSlots = nbttagcompound.getInteger("StartSlots");
      this.maxSlots = nbttagcompound.getInteger("MaxSlots");
      this.slotTypes = NBTTags.getIntegerIntegerMap(nbttagcompound.getTagList("BankTypes", 10));
      this.currencyInventory.setFromNBT(nbttagcompound.getCompoundTag("BankCurrency"));
      this.upgradeInventory.setFromNBT(nbttagcompound.getCompoundTag("BankUpgrade"));
   }

   public boolean isUpgraded(int slot) {
      return this.slotTypes.get(Integer.valueOf(slot)) != null && ((Integer)this.slotTypes.get(Integer.valueOf(slot))).intValue() == 2;
   }

   public boolean canBeUpgraded(int slot) {
      return this.upgradeInventory.getStackInSlot(slot) == null?false:this.slotTypes.get(Integer.valueOf(slot)) == null || ((Integer)this.slotTypes.get(Integer.valueOf(slot))).intValue() == 0;
   }

   public int getMaxSlots() {
      for(int i = 0; i < this.maxSlots; ++i) {
         if(this.currencyInventory.getStackInSlot(i) == null && i > this.startSlots - 1) {
            return i;
         }
      }

      return this.maxSlots;
   }
}
