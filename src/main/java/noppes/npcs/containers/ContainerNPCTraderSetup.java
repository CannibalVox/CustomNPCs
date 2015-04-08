package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleTrader;

public class ContainerNPCTraderSetup extends Container {

   public RoleTrader role;


   public ContainerNPCTraderSetup(EntityNPCInterface npc, EntityPlayer player) {
      this.role = (RoleTrader)npc.roleInterface;

      int j1;
      int var6;
      for(j1 = 0; j1 < 18; ++j1) {
         byte l1 = 7;
         var6 = l1 + j1 % 3 * 94;
         byte y = 15;
         int var7 = y + j1 / 3 * 22;
         this.addSlotToContainer(new Slot(this.role.inventoryCurrency, j1 + 18, var6, var7));
         this.addSlotToContainer(new Slot(this.role.inventoryCurrency, j1, var6 + 18, var7));
         this.addSlotToContainer(new Slot(this.role.inventorySold, j1, var6 + 43, var7));
      }

      for(j1 = 0; j1 < 3; ++j1) {
         for(var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(player.inventory, var6 + j1 * 9 + 9, 48 + var6 * 18, 147 + j1 * 18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlotToContainer(new Slot(player.inventory, j1, 48 + j1 * 18, 205));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean canInteractWith(EntityPlayer entityplayer) {
      return true;
   }
}
