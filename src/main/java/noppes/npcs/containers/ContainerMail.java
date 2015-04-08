package noppes.npcs.containers;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.containers.ContainerNpcInterface;
import noppes.npcs.containers.SlotValid;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.PlayerMailData;

public class ContainerMail extends ContainerNpcInterface {

   private static final String __OBFID = "CL_00001742";
   public static PlayerMail staticmail = new PlayerMail();
   public PlayerMail mail = new PlayerMail();
   private boolean canEdit;
   private boolean canSend;


   public ContainerMail(EntityPlayer player, boolean canEdit, boolean canSend) {
      super(player);
      this.mail = staticmail;
      staticmail = new PlayerMail();
      this.canEdit = canEdit;
      this.canSend = canSend;
      player.inventory.openChest();

      int k;
      for(k = 0; k < 4; ++k) {
         this.addSlotToContainer(new SlotValid(this.mail, k, 179 + k * 24, 138, canEdit));
      }

      int j;
      for(j = 0; j < 3; ++j) {
         for(k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 28 + k * 18, 175 + j * 18));
         }
      }

      for(j = 0; j < 9; ++j) {
         this.addSlotToContainer(new Slot(player.inventory, j, 28 + j * 18, 230));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
      ItemStack itemstack = null;
      Slot slot = (Slot)super.inventorySlots.get(par2);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(par2 < 4) {
            if(!this.mergeItemStack(itemstack1, 4, super.inventorySlots.size(), true)) {
               return null;
            }
         } else if(!this.canEdit || !this.mergeItemStack(itemstack1, 0, 4, false)) {
            return null;
         }

         if(itemstack1.stackSize == 0) {
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }
      }

      return itemstack;
   }

   public void onContainerClosed(EntityPlayer player) {
      super.onContainerClosed(player);
      if(!this.canEdit && !player.worldObj.isRemote) {
         PlayerMailData data = PlayerDataController.instance.getPlayerData(player).mailData;
         Iterator it = data.playermail.iterator();

         while(it.hasNext()) {
            PlayerMail mail = (PlayerMail)it.next();
            if(mail.time == this.mail.time && mail.sender.equals(this.mail.sender)) {
               mail.readNBT(this.mail.writeNBT());
               break;
            }
         }
      }

   }

}
