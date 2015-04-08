package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCQuestSelection;
import noppes.npcs.client.gui.player.GuiMailmanWrite;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.controllers.PlayerMail;

public class SubGuiMailmanSendSetup extends SubGuiInterface implements ITextfieldListener, GuiSelectionListener {

   private PlayerMail mail;
   private GuiNPCQuestSelection questSelection;


   public SubGuiMailmanSendSetup(PlayerMail mail, GuiScreen parent) {
      super.parent = parent;
      super.xSize = 256;
      this.setBackground("menubg.png");
      this.mail = mail;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "mailbox.subject", super.guiLeft + 4, super.guiTop + 19));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 14, 180, 20, this.mail.subject));
      this.addLabel(new GuiNpcLabel(0, "mailbox.sender", super.guiLeft + 4, super.guiTop + 41));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 36, 180, 20, this.mail.sender));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 29, super.guiTop + 100, "mailbox.write"));
      this.addLabel(new GuiNpcLabel(3, "quest.quest", super.guiLeft + 13, super.guiTop + 135));
      String title = this.mail.questTitle;
      if(title.isEmpty()) {
         title = "gui.select";
      }

      this.addButton(new GuiNpcButton(3, super.guiLeft + 70, super.guiTop + 130, 100, 20, title));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 171, super.guiTop + 130, 20, 20, "X"));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 26, super.guiTop + 190, 100, 20, "gui.done"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 130, super.guiTop + 190, 100, 20, "gui.cancel"));
      if(super.player.openContainer instanceof ContainerMail) {
         ContainerMail container = (ContainerMail)super.player.openContainer;
         this.mail.items = container.mail.items;
      }

   }

   public void buttonEvent(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.close();
      }

      if(id == 1) {
         this.mail.questId = -1;
         this.mail.questTitle = "";
         this.mail.message = new NBTTagCompound();
         this.close();
      }

      if(id == 2) {
         GuiMailmanWrite.parent = super.parent;
         GuiMailmanWrite.mail = this.mail;
         Client.sendData(EnumPacketServer.MailOpenSetup, new Object[]{this.mail.writeNBT()});
      }

      if(id == 3) {
         NoppesUtil.openGUI(super.player, this.questSelection = new GuiNPCQuestSelection(super.npc, this.getParent(), this.mail.questId));
         this.questSelection.listener = this;
      }

      if(id == 4) {
         this.mail.questId = -1;
         this.mail.questTitle = "";
         this.initGui();
      }

   }

   public void selected(int ob, String name) {
      this.mail.questId = ob;
      this.mail.questTitle = this.questSelection.getSelected();
      this.initGui();
   }

   public void save() {}

   public void unFocused(GuiNpcTextField textField) {
      if(textField.id == 0) {
         this.mail.sender = textField.getText();
      }

      if(textField.id == 1) {
         this.mail.subject = textField.getText();
      }

   }
}
