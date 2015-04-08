package noppes.npcs.client.gui.questtypes;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.quests.QuestLocation;

public class GuiNpcQuestTypeLocation extends SubGuiInterface implements ITextfieldListener {

   private GuiScreen parent;
   private QuestLocation quest;


   public GuiNpcQuestTypeLocation(EntityNPCInterface npc, Quest q, GuiScreen parent) {
      super.npc = npc;
      this.parent = parent;
      super.title = "Quest Location Setup";
      this.quest = (QuestLocation)q.questInterface;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "Fill in the name of your Location Quest Block", super.guiLeft + 4, super.guiTop + 50));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 70, 180, 20, this.quest.location));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 92, 180, 20, this.quest.location2));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 114, 180, 20, this.quest.location3));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 150, super.guiTop + 190, 98, 20, "gui.back"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      if(guibutton.id == 0) {
         this.close();
      }

   }

   public void save() {}

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 0) {
         this.quest.location = textfield.getText();
      }

      if(textfield.id == 1) {
         this.quest.location2 = textfield.getText();
      }

      if(textfield.id == 2) {
         this.quest.location3 = textfield.getText();
      }

   }
}
