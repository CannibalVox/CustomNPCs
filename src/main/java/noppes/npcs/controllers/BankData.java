package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.entity.EntityNPCInterface;

public class BankData {

   public HashMap itemSlots = new HashMap();
   public HashMap upgradedSlots = new HashMap();
   public int unlockedSlots = 0;
   public int bankId = -1;


   public BankData() {
      for(int i = 0; i < 6; ++i) {
         this.itemSlots.put(Integer.valueOf(i), new NpcMiscInventory(54));
         this.upgradedSlots.put(Integer.valueOf(i), Boolean.valueOf(false));
      }

   }

   public void readNBT(NBTTagCompound nbttagcompound) {
      this.bankId = nbttagcompound.getInteger("DataBankId");
      this.unlockedSlots = nbttagcompound.getInteger("UnlockedSlots");
      this.itemSlots = this.getItemSlots(nbttagcompound.getTagList("BankInv", 10));
      this.upgradedSlots = NBTTags.getBooleanList(nbttagcompound.getTagList("UpdatedSlots", 10));
   }

   private HashMap getItemSlots(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         int slot = nbttagcompound.getInteger("Slot");
         NpcMiscInventory inv = new NpcMiscInventory(54);
         inv.setFromNBT(nbttagcompound.getCompoundTag("BankItems"));
         list.put(Integer.valueOf(slot), inv);
      }

      return list;
   }

   public void writeNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("DataBankId", this.bankId);
      nbttagcompound.setInteger("UnlockedSlots", this.unlockedSlots);
      nbttagcompound.setTag("UpdatedSlots", NBTTags.nbtBooleanList(this.upgradedSlots));
      nbttagcompound.setTag("BankInv", this.nbtItemSlots(this.itemSlots));
   }

   private NBTTagList nbtItemSlots(HashMap items) {
      NBTTagList list = new NBTTagList();
      Iterator var3 = items.keySet().iterator();

      while(var3.hasNext()) {
         int slot = ((Integer)var3.next()).intValue();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setInteger("Slot", slot);
         nbttagcompound.setTag("BankItems", ((NpcMiscInventory)items.get(Integer.valueOf(slot))).getToNBT());
         list.appendTag(nbttagcompound);
      }

      return list;
   }

   public boolean isUpgraded(Bank bank, int slot) {
      return bank.isUpgraded(slot)?true:bank.canBeUpgraded(slot) && ((Boolean)this.upgradedSlots.get(Integer.valueOf(slot))).booleanValue();
   }

   public void openBankGui(EntityPlayer player, EntityNPCInterface npc, int bankId, int slot) {
      Bank bank = (Bank)BankController.getInstance().banks.get(Integer.valueOf(bankId));
      if(bank == null) {
         bank = (Bank)BankController.getInstance().banks.values().iterator().next();
      }

      if(bank.getMaxSlots() > slot) {
         if(bank.startSlots > this.unlockedSlots) {
            this.unlockedSlots = bank.startSlots;
         }

         ItemStack currency = null;
         if(this.unlockedSlots <= slot) {
            currency = bank.currencyInventory.getStackInSlot(slot);
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUnlock, npc, slot, bank.id, 0);
         } else if(this.isUpgraded(bank, slot)) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankLarge, npc, slot, bank.id, 0);
         } else if(bank.canBeUpgraded(slot)) {
            currency = bank.upgradeInventory.getStackInSlot(slot);
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUprade, npc, slot, bank.id, 0);
         } else {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankSmall, npc, slot, bank.id, 0);
         }

         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("MaxSlots", bank.getMaxSlots());
         compound.setInteger("UnlockedSlots", this.unlockedSlots);
         if(currency != null) {
            compound.setTag("Currency", currency.writeToNBT(new NBTTagCompound()));
            ContainerNPCBankInterface container = this.getContainer(player);
            container.currency.item = currency;
         }

         Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI_DATA, new Object[]{compound});
      }
   }

   private ContainerNPCBankInterface getContainer(EntityPlayer player) {
      Container con = player.openContainer;
      return con != null && con instanceof ContainerNPCBankInterface?(ContainerNPCBankInterface)con:null;
   }
}
