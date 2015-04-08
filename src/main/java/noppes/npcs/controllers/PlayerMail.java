package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;

public class PlayerMail implements IInventory {

   public String subject = "";
   public String sender = "";
   public NBTTagCompound message = new NBTTagCompound();
   public long time = 0L;
   public boolean beenRead = false;
   public int questId = -1;
   public String questTitle = "";
   public ItemStack[] items = new ItemStack[4];
   public long timePast;


   public void readNBT(NBTTagCompound compound) {
      this.subject = compound.getString("Subject");
      this.sender = compound.getString("Sender");
      this.time = compound.getLong("Time");
      this.beenRead = compound.getBoolean("BeenRead");
      this.message = compound.getCompoundTag("Message");
      this.timePast = compound.getLong("TimePast");
      if(compound.hasKey("MailQuest")) {
         this.questId = compound.getInteger("MailQuest");
      }

      this.questTitle = compound.getString("MailQuestTitle");
      this.items = new ItemStack[this.getSizeInventory()];
      NBTTagList nbttaglist = compound.getTagList("MailItems", 10);

      for(int i = 0; i < nbttaglist.tagCount(); ++i) {
         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
         int j = nbttagcompound1.getByte("Slot") & 255;
         if(j >= 0 && j < this.items.length) {
            this.items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
         }
      }

   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setString("Subject", this.subject);
      compound.setString("Sender", this.sender);
      compound.setLong("Time", this.time);
      compound.setBoolean("BeenRead", this.beenRead);
      compound.setTag("Message", this.message);
      compound.setLong("TimePast", System.currentTimeMillis() - this.time);
      compound.setInteger("MailQuest", this.questId);
      if(this.hasQuest()) {
         compound.setString("MailQuestTitle", this.getQuest().title);
      }

      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)i);
            this.items[i].writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
         }
      }

      compound.setTag("MailItems", nbttaglist);
      return compound;
   }

   public boolean isValid() {
      return !this.subject.isEmpty() && !this.message.hasNoTags() && !this.sender.isEmpty();
   }

   public boolean hasQuest() {
      return this.getQuest() != null;
   }

   public Quest getQuest() {
      return QuestController.instance != null?(Quest)QuestController.instance.quests.get(Integer.valueOf(this.questId)):null;
   }

   public int getSizeInventory() {
      return 4;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public ItemStack getStackInSlot(int var1) {
      return this.items[var1];
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.items[par1] != null) {
         ItemStack itemstack;
         if(this.items[par1].stackSize <= par2) {
            itemstack = this.items[par1];
            this.items[par1] = null;
            this.markDirty();
            return itemstack;
         } else {
            itemstack = this.items[par1].splitStack(par2);
            if(this.items[par1].stackSize == 0) {
               this.items[par1] = null;
            }

            this.markDirty();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if(this.items[var1] != null) {
         ItemStack itemstack = this.items[var1];
         this.items[var1] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      this.items[par1] = par2ItemStack;
      if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
         par2ItemStack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   public String getInventoryName() {
      return null;
   }

   public boolean isCustomInventoryName() {
      return false;
   }

   public void markDirty() {}

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return true;
   }

   public void openChest() {}

   public void closeChest() {}

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public PlayerMail copy() {
      PlayerMail mail = new PlayerMail();
      mail.readNBT(this.writeNBT());
      return mail;
   }
}
