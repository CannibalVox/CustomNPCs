package noppes.npcs.roles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.BankData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RoleBank extends RoleInterface {

   public int bankId = -1;


   public RoleBank(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("RoleBankID", this.bankId);
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.bankId = nbttagcompound.getInteger("RoleBankID");
   }

   public void interact(EntityPlayer player) {
      BankData data = PlayerDataController.instance.getBankData(player, this.bankId).getBankOrDefault(this.bankId);
      data.openBankGui(player, super.npc, this.bankId, 0);
      super.npc.say(player, super.npc.advanced.getInteractLine());
   }

   public Bank getBank() {
      Bank bank = (Bank)BankController.getInstance().banks.get(Integer.valueOf(this.bankId));
      return bank != null?bank:(Bank)BankController.getInstance().banks.values().iterator().next();
   }
}
