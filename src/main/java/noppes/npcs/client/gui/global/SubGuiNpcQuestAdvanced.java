package noppes.npcs.client.gui.global;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiMailmanSendSetup;
import noppes.npcs.client.gui.SubGuiNpcCommand;
import noppes.npcs.client.gui.SubGuiNpcFactionOptions;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.global.GuiNPCQuestSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.Quest;

public class SubGuiNpcQuestAdvanced extends SubGuiInterface implements ITextfieldListener {

   private Quest quest;
   private GuiNPCManageQuest parent;


   public SubGuiNpcQuestAdvanced(Quest quest, GuiNPCManageQuest parent) {
      this.quest = quest;
      this.parent = parent;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(10, "faction.options", super.guiLeft + 4, super.guiTop + 17));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 120, super.guiTop + 12, 50, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(13, super.guiLeft + 4, super.guiTop + 35, 164, 20, "mailbox.setup"));
      this.addButton(new GuiNpcButton(14, super.guiLeft + 170, super.guiTop + 35, 20, 20, "X"));
      if(!this.quest.mail.subject.isEmpty()) {
         this.getButton(13).setDisplayText(this.quest.mail.subject);
      }

      this.addButton(new GuiNpcButton(11, super.guiLeft + 4, super.guiTop + 58, 164, 20, "quest.next"));
      this.addButton(new GuiNpcButton(12, super.guiLeft + 170, super.guiTop + 58, 20, 20, "X"));
      if(!this.quest.nextQuestTitle.isEmpty()) {
         this.getButton(11).setDisplayText(this.quest.nextQuestTitle);
      }

      this.addLabel(new GuiNpcLabel(9, "advMode.command", super.guiLeft + 4, super.guiTop + 86));
      this.addButton(new GuiNpcButton(9, super.guiLeft + 120, super.guiTop + 81, 50, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 190, super.guiTop + 190, 60, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 9) {
         this.parent.setSubGui(new SubGuiNpcCommand(this.quest.command));
      }

      if(button.field_146127_k == 10) {
         this.parent.setSubGui(new SubGuiNpcFactionOptions(this.quest.factionOptions));
      }

      if(button.field_146127_k == 11 && this.quest.id >= 0) {
         NoppesUtil.openGUI(super.player, new GuiNPCQuestSelection(super.npc, this.getParent(), this.quest.nextQuestid));
      }

      if(button.field_146127_k == 12 && this.quest.id >= 0) {
         this.quest.nextQuestid = -1;
         this.initGui();
      }

      if(button.field_146127_k == 13) {
         this.parent.setSubGui(new SubGuiMailmanSendSetup(this.quest.mail, this.getParent()));
      }

      if(button.field_146127_k == 14) {
         this.quest.mail = new PlayerMail();
         this.initGui();
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {}
}
