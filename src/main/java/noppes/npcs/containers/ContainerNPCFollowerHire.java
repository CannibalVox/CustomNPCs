package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.ContainerNpcInterface;
import noppes.npcs.containers.SlotNpcMercenaryCurrency;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class ContainerNPCFollowerHire extends ContainerNpcInterface {

   public InventoryBasic currencyMatrix;
   public RoleFollower role;


   public ContainerNPCFollowerHire(EntityNPCInterface npc, EntityPlayer player) {
      super(player);
      this.role = (RoleFollower)npc.roleInterface;
      this.currencyMatrix = new InventoryBasic("currency", false, 1);
      this.addSlotToContainer(new SlotNpcMercenaryCurrency(this.role, this.currencyMatrix, 0, 44, 35));

      int j1;
      for(j1 = 0; j1 < 3; ++j1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.addSlotToContainer(new Slot(player.inventory, l1 + j1 * 9 + 9, 8 + l1 * 18, 84 + j1 * 18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlotToContainer(new Slot(player.inventory, j1, 8 + j1 * 18, 142));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public void onContainerClosed(EntityPlayer entityplayer) {
      super.onContainerClosed(entityplayer);
      if(!entityplayer.worldObj.isRemote) {
         ItemStack itemstack = this.currencyMatrix.getStackInSlotOnClosing(0);
         if(itemstack != null && !entityplayer.worldObj.isRemote) {
            entityplayer.entityDropItem(itemstack, 0.0F);
         }
      }

   }
}
