package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.controllers.Quest;

public class ContainerNpcQuestReward extends Container {

   public ContainerNpcQuestReward(EntityPlayer player) {
      Quest quest = NoppesUtilServer.getEditingQuest(player);
      if(player.worldObj.isRemote) {
         quest = GuiNPCManageQuest.quest;
      }

      int j1;
      int l1;
      for(j1 = 0; j1 < 3; ++j1) {
         for(l1 = 0; l1 < 3; ++l1) {
            this.addSlotToContainer(new Slot(quest.rewardItems, l1 + j1 * 3, 105 + l1 * 18, 17 + j1 * 18));
         }
      }

      for(j1 = 0; j1 < 3; ++j1) {
         for(l1 = 0; l1 < 9; ++l1) {
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

   public boolean canInteractWith(EntityPlayer entityplayer) {
      return true;
   }
}
