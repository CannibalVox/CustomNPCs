package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiMailmanSendSetup;
import noppes.npcs.client.gui.SubGuiNpcCommand;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcButtonYesNo;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.ISubGuiListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.PlayerMail;

public class SubGuiNpcDialogExtra extends SubGuiInterface implements ISubGuiListener {

   private Dialog dialog;
   private int slot = 0;
   public GuiScreen parent2;


   public SubGuiNpcDialogExtra(Dialog dialog, GuiScreen parent) {
      this.parent2 = parent;
      this.dialog = dialog;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 4;
      this.addButton(new GuiNpcButton(13, super.guiLeft + 4, y, 164, 20, "mailbox.setup"));
      this.addButton(new GuiNpcButton(14, super.guiLeft + 170, y, 20, 20, "X"));
      if(!this.dialog.mail.subject.isEmpty()) {
         this.getButton(13).setDisplayText(this.dialog.mail.subject);
      }

      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 120;
      y += 22;
      var10001 = new GuiNpcButton(10, var10004, y, 50, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(10, "advMode.command", super.guiLeft + 4, y + 5));
      GuiNpcButtonYesNo var2;
      var10004 = super.guiLeft + 120;
      y += 22;
      var2 = new GuiNpcButtonYesNo(11, var10004, y, this.dialog.hideNPC);
      this.addButton(var2);
      this.addLabel(new GuiNpcLabel(11, "dialog.hideNPC", super.guiLeft + 4, y + 5));
      var10004 = super.guiLeft + 120;
      y += 22;
      var2 = new GuiNpcButtonYesNo(12, var10004, y, this.dialog.showWheel);
      this.addButton(var2);
      this.addLabel(new GuiNpcLabel(12, "dialog.showWheel", super.guiLeft + 4, y + 5));
      var10004 = super.guiLeft + 120;
      y += 22;
      var2 = new GuiNpcButtonYesNo(15, var10004, y, this.dialog.disableEsc);
      this.addButton(var2);
      this.addLabel(new GuiNpcLabel(15, "dialog.disableEsc", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 192, 98, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 10) {
         this.setSubGui(new SubGuiNpcCommand(this.dialog.command));
      }

      if(button.field_146127_k == 11) {
         this.dialog.hideNPC = button.getValue() == 1;
      }

      if(button.field_146127_k == 12) {
         this.dialog.showWheel = button.getValue() == 1;
      }

      if(button.field_146127_k == 15) {
         this.dialog.disableEsc = button.getValue() == 1;
      }

      if(button.field_146127_k == 13) {
         this.setSubGui(new SubGuiMailmanSendSetup(this.dialog.mail, this.getParent()));
      }

      if(button.field_146127_k == 14) {
         this.dialog.mail = new PlayerMail();
         this.initGui();
      }

      if(button.field_146127_k == 66) {
         this.close();
         if(this.parent2 != null) {
            NoppesUtil.openGUI(super.player, this.parent2);
         }
      }

   }

   public void subGuiClosed(SubGuiInterface subgui) {
      if(subgui instanceof SubGuiNpcCommand) {
         this.dialog.command = ((SubGuiNpcCommand)subgui).command;
      }

   }
}
