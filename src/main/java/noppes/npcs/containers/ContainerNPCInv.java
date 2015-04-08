package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.SlotNPCArmor;
import noppes.npcs.entity.EntityNPCInterface;

public class ContainerNPCInv extends Container {

   public ContainerNPCInv(EntityNPCInterface npc, EntityPlayer player) {
      int j1;
      for(j1 = 0; j1 < 4; ++j1) {
         this.addSlotToContainer(new SlotNPCArmor(npc.inventory, j1, 9, 22 + j1 * 18, j1));
      }

      this.addSlotToContainer(new Slot(npc.inventory, 4, 81, 22));
      this.addSlotToContainer(new Slot(npc.inventory, 5, 81, 40));
      this.addSlotToContainer(new Slot(npc.inventory, 6, 81, 58));

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlotToContainer(new Slot(npc.inventory, j1 + 7, 191, 16 + j1 * 21));
      }

      for(j1 = 0; j1 < 3; ++j1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.addSlotToContainer(new Slot(player.inventory, l1 + j1 * 9 + 9, l1 * 18 + 8, 113 + j1 * 18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlotToContainer(new Slot(player.inventory, j1, j1 * 18 + 8, 171));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean canInteractWith(EntityPlayer entityplayer) {
      return true;
   }
}
