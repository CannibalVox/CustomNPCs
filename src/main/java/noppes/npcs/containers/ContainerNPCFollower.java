package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.ContainerNpcInterface;
import noppes.npcs.containers.InventoryNPC;
import noppes.npcs.containers.SlotNpcMercenaryCurrency;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class ContainerNPCFollower extends ContainerNpcInterface {

   public InventoryNPC currencyMatrix;
   public RoleFollower role;


   public ContainerNPCFollower(EntityNPCInterface npc, EntityPlayer player) {
      super(player);
      this.role = (RoleFollower)npc.roleInterface;
      this.currencyMatrix = new InventoryNPC("currency", 1, this);
      this.addSlotToContainer(new SlotNpcMercenaryCurrency(this.role, this.currencyMatrix, 0, 26, 9));

      for(int j1 = 0; j1 < 9; ++j1) {
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
