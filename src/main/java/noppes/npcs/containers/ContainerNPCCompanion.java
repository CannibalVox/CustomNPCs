package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.containers.ContainerNpcInterface;
import noppes.npcs.containers.InventoryNPC;
import noppes.npcs.containers.SlotCompanionArmor;
import noppes.npcs.containers.SlotCompanionWeapon;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;

public class ContainerNPCCompanion extends ContainerNpcInterface {

   public InventoryNPC currencyMatrix;
   public RoleCompanion role;


   public ContainerNPCCompanion(EntityNPCInterface npc, EntityPlayer player) {
      super(player);
      this.role = (RoleCompanion)npc.roleInterface;

      int l;
      int i;
      for(l = 0; l < 3; ++l) {
         for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i + l * 9 + 9, 6 + i * 18, 87 + l * 18));
         }
      }

      for(l = 0; l < 9; ++l) {
         this.addSlotToContainer(new Slot(player.inventory, l, 6 + l * 18, 145));
      }

      if(this.role.talents.containsKey(EnumCompanionTalent.INVENTORY)) {
         l = (this.role.getTalentLevel(EnumCompanionTalent.INVENTORY) + 1) * 2;

         for(i = 0; i < l; ++i) {
            this.addSlotToContainer(new Slot(this.role.inventory, i, 114 + i % 3 * 18, 8 + i / 3 * 18));
         }
      }

      if(this.role.getTalentLevel(EnumCompanionTalent.ARMOR) > 0) {
         for(l = 0; l < 4; ++l) {
            this.addSlotToContainer(new SlotCompanionArmor(this.role, npc.inventory, l, 6, 8 + l * 18, l));
         }
      }

      if(this.role.getTalentLevel(EnumCompanionTalent.SWORD) > 0) {
         this.addSlotToContainer(new SlotCompanionWeapon(this.role, npc.inventory, 4, 79, 17));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public void onContainerClosed(EntityPlayer entityplayer) {
      super.onContainerClosed(entityplayer);
      this.role.setStats();
   }
}
