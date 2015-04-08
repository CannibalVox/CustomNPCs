package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCDialogSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumAvailabilityDialog;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.Dialog;

public class SubGuiNpcAvailabilityDialog extends SubGuiInterface implements GuiSelectionListener, IGuiData {

   private Availability availabitily;
   private int slot = 0;


   public SubGuiNpcAvailabilityDialog(Availability availabitily) {
      this.availabitily = availabitily;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "availability.available", super.guiLeft, super.guiTop + 4));
      this.getLabel(1).center(super.xSize);
      int y = super.guiTop + 12;
      this.addButton(new GuiNpcButton(0, super.guiLeft + 4, y, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialogAvailable.ordinal()));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 56, y, 172, 20, "availability.selectdialog"));
      this.getButton(10).setEnabled(this.availabitily.dialogAvailable != EnumAvailabilityDialog.Always);
      this.addButton(new GuiNpcButton(20, super.guiLeft + 230, y, 20, 20, "X"));
      y += 23;
      this.addButton(new GuiNpcButton(1, super.guiLeft + 4, y, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialog2Available.ordinal()));
      this.addButton(new GuiNpcButton(11, super.guiLeft + 56, y, 172, 20, "availability.selectdialog"));
      this.getButton(11).setEnabled(this.availabitily.dialog2Available != EnumAvailabilityDialog.Always);
      this.addButton(new GuiNpcButton(21, super.guiLeft + 230, y, 20, 20, "X"));
      y += 23;
      this.addButton(new GuiNpcButton(2, super.guiLeft + 4, y, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialog3Available.ordinal()));
      this.addButton(new GuiNpcButton(12, super.guiLeft + 56, y, 172, 20, "availability.selectdialog"));
      this.getButton(12).setEnabled(this.availabitily.dialog3Available != EnumAvailabilityDialog.Always);
      this.addButton(new GuiNpcButton(22, super.guiLeft + 230, y, 20, 20, "X"));
      y += 23;
      this.addButton(new GuiNpcButton(3, super.guiLeft + 4, y, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialog4Available.ordinal()));
      this.addButton(new GuiNpcButton(13, super.guiLeft + 56, y, 172, 20, "availability.selectdialog"));
      this.getButton(13).setEnabled(this.availabitily.dialog4Available != EnumAvailabilityDialog.Always);
      this.addButton(new GuiNpcButton(23, super.guiLeft + 230, y, 20, 20, "X"));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 192, 98, 20, "gui.done"));
      this.updateGuiButtons();
   }

   private void updateGuiButtons() {
      this.getButton(10).setDisplayText("availability.selectdialog");
      this.getButton(11).setDisplayText("availability.selectdialog");
      this.getButton(12).setDisplayText("availability.selectdialog");
      this.getButton(13).setDisplayText("availability.selectdialog");
      if(this.availabitily.dialogId >= 0) {
         Client.sendData(EnumPacketServer.DialogGet, new Object[]{Integer.valueOf(this.availabitily.dialogId)});
      }

      if(this.availabitily.dialog2Id >= 0) {
         Client.sendData(EnumPacketServer.DialogGet, new Object[]{Integer.valueOf(this.availabitily.dialog2Id)});
      }

      if(this.availabitily.dialog3Id >= 0) {
         Client.sendData(EnumPacketServer.DialogGet, new Object[]{Integer.valueOf(this.availabitily.dialog3Id)});
      }

      if(this.availabitily.dialog4Id >= 0) {
         Client.sendData(EnumPacketServer.DialogGet, new Object[]{Integer.valueOf(this.availabitily.dialog4Id)});
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.availabitily.dialogAvailable = EnumAvailabilityDialog.values()[button.getValue()];
         if(this.availabitily.dialogAvailable == EnumAvailabilityDialog.Always) {
            this.availabitily.dialogId = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 1) {
         this.availabitily.dialog2Available = EnumAvailabilityDialog.values()[button.getValue()];
         if(this.availabitily.dialog2Available == EnumAvailabilityDialog.Always) {
            this.availabitily.dialog2Id = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 2) {
         this.availabitily.dialog3Available = EnumAvailabilityDialog.values()[button.getValue()];
         if(this.availabitily.dialog3Available == EnumAvailabilityDialog.Always) {
            this.availabitily.dialog3Id = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 3) {
         this.availabitily.dialog4Available = EnumAvailabilityDialog.values()[button.getValue()];
         if(this.availabitily.dialog4Available == EnumAvailabilityDialog.Always) {
            this.availabitily.dialog4Id = -1;
         }

         this.initGui();
      }

      GuiNPCDialogSelection gui;
      if(button.field_146127_k == 10) {
         this.slot = 1;
         gui = new GuiNPCDialogSelection(super.npc, this.getParent(), this.availabitily.dialogId);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 11) {
         this.slot = 2;
         gui = new GuiNPCDialogSelection(super.npc, this.getParent(), this.availabitily.dialog2Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 12) {
         this.slot = 3;
         gui = new GuiNPCDialogSelection(super.npc, this.getParent(), this.availabitily.dialog3Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 13) {
         this.slot = 4;
         gui = new GuiNPCDialogSelection(super.npc, this.getParent(), this.availabitily.dialog4Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 20) {
         this.availabitily.dialogId = -1;
         this.getButton(10).setDisplayText("availability.selectdialog");
      }

      if(button.field_146127_k == 21) {
         this.availabitily.dialog2Id = -1;
         this.getButton(11).setDisplayText("availability.selectdialog");
      }

      if(button.field_146127_k == 22) {
         this.availabitily.dialog3Id = -1;
         this.getButton(12).setDisplayText("availability.selectdialog");
      }

      if(button.field_146127_k == 23) {
         this.availabitily.dialog4Id = -1;
         this.getButton(13).setDisplayText("availability.selectdialog");
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void selected(int id, String name) {
      if(this.slot == 1) {
         this.availabitily.dialogId = id;
      }

      if(this.slot == 2) {
         this.availabitily.dialog2Id = id;
      }

      if(this.slot == 3) {
         this.availabitily.dialog3Id = id;
      }

      if(this.slot == 4) {
         this.availabitily.dialog4Id = id;
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      Dialog dialog = new Dialog();
      dialog.readNBT(compound);
      if(this.availabitily.dialogId == dialog.id) {
         this.getButton(10).setDisplayText(dialog.title);
      }

      if(this.availabitily.dialog2Id == dialog.id) {
         this.getButton(11).setDisplayText(dialog.title);
      }

      if(this.availabitily.dialog3Id == dialog.id) {
         this.getButton(12).setDisplayText(dialog.title);
      }

      if(this.availabitily.dialog4Id == dialog.id) {
         this.getButton(13).setDisplayText(dialog.title);
      }

   }
}
