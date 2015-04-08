package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.controllers.Bank;

public class ContainerManageBanks extends Container {

   public Bank bank = new Bank();


   public ContainerManageBanks(EntityPlayer player) {
      int j1;
      byte y;
      int var6;
      for(j1 = 0; j1 < 6; ++j1) {
         byte x = 36;
         y = 38;
         var6 = y + j1 * 22;
         this.addSlotToContainer(new Slot(this.bank.currencyInventory, j1, x, var6));
      }

      for(j1 = 0; j1 < 6; ++j1) {
         short var5 = 142;
         y = 38;
         var6 = y + j1 * 22;
         this.addSlotToContainer(new Slot(this.bank.upgradeInventory, j1, var5, var6));
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlotToContainer(new Slot(player.inventory, j1, 8 + j1 * 18, 171));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean canInteractWith(EntityPlayer entityplayer) {
      return true;
   }

   public void setBank(Bank bank2) {
      for(int i = 0; i < 6; ++i) {
         this.bank.currencyInventory.setInventorySlotContents(i, bank2.currencyInventory.getStackInSlot(i));
         this.bank.upgradeInventory.setInventorySlotContents(i, bank2.upgradeInventory.getStackInSlot(i));
      }

   }
}
