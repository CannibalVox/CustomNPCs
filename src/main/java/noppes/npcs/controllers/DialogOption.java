package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;

public class DialogOption {

   public int dialogId = -1;
   public String title = "Talk";
   public EnumOptionType optionType;
   public int optionColor;
   public String command;


   public DialogOption() {
      this.optionType = EnumOptionType.Disabled;
      this.optionColor = 14737632;
      this.command = "";
   }

   public void readNBT(NBTTagCompound compound) {
      if(compound != null) {
         this.title = compound.getString("Title");
         this.dialogId = compound.getInteger("Dialog");
         this.optionColor = compound.getInteger("DialogColor");
         this.optionType = EnumOptionType.values()[compound.getInteger("OptionType")];
         this.command = compound.getString("DialogCommand");
         if(this.optionColor == 0) {
            this.optionColor = 14737632;
         }

      }
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setString("Title", this.title);
      compound.setInteger("OptionType", this.optionType.ordinal());
      compound.setInteger("Dialog", this.dialogId);
      compound.setInteger("DialogColor", this.optionColor);
      compound.setString("DialogCommand", this.command);
      return compound;
   }

   public boolean hasDialog() {
      if(this.dialogId <= 0) {
         return false;
      } else if(!DialogController.instance.hasDialog(this.dialogId)) {
         this.dialogId = -1;
         return false;
      } else {
         return true;
      }
   }

   public Dialog getDialog() {
      return !this.hasDialog()?null:(Dialog)DialogController.instance.dialogs.get(Integer.valueOf(this.dialogId));
   }

   public boolean isAvailable(EntityPlayer player) {
      Dialog dialog = this.getDialog();
      return dialog == null?false:dialog.availability.isAvailable(player);
   }
}
