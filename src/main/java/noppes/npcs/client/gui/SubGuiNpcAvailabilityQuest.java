package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCQuestSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumAvailabilityQuest;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.Quest;

public class SubGuiNpcAvailabilityQuest extends SubGuiInterface implements GuiSelectionListener, IGuiData {

   private Availability availabitily;
   private boolean selectFaction = false;
   private int slot = 0;


   public SubGuiNpcAvailabilityQuest(Availability availabitily) {
      this.availabitily = availabitily;
      this.setBackground("menubg.png");
      super.xSize = 316;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "availability.available", super.guiLeft, super.guiTop + 4));
      this.getLabel(1).center(super.xSize);
      int y = super.guiTop + 12;
      this.addButton(new GuiNpcButton(0, super.guiLeft + 4, y, 90, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.questAvailable.ordinal()));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 96, y, 192, 20, "availability.selectquest"));
      this.getButton(10).setEnabled(this.availabitily.questAvailable != EnumAvailabilityQuest.Always);
      this.addButton(new GuiNpcButton(20, super.guiLeft + 290, y, 20, 20, "X"));
      y += 23;
      this.addButton(new GuiNpcButton(1, super.guiLeft + 4, y, 90, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.quest2Available.ordinal()));
      this.addButton(new GuiNpcButton(11, super.guiLeft + 96, y, 192, 20, "availability.selectquest"));
      this.getButton(11).setEnabled(this.availabitily.quest2Available != EnumAvailabilityQuest.Always);
      this.addButton(new GuiNpcButton(21, super.guiLeft + 290, y, 20, 20, "X"));
      y += 23;
      this.addButton(new GuiNpcButton(2, super.guiLeft + 4, y, 90, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.quest3Available.ordinal()));
      this.addButton(new GuiNpcButton(12, super.guiLeft + 96, y, 192, 20, "availability.selectquest"));
      this.getButton(12).setEnabled(this.availabitily.quest3Available != EnumAvailabilityQuest.Always);
      this.addButton(new GuiNpcButton(22, super.guiLeft + 290, y, 20, 20, "X"));
      y += 23;
      this.addButton(new GuiNpcButton(3, super.guiLeft + 4, y, 90, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.quest4Available.ordinal()));
      this.addButton(new GuiNpcButton(13, super.guiLeft + 96, y, 192, 20, "availability.selectquest"));
      this.getButton(13).setEnabled(this.availabitily.quest4Available != EnumAvailabilityQuest.Always);
      this.addButton(new GuiNpcButton(23, super.guiLeft + 290, y, 20, 20, "X"));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 192, 98, 20, "gui.done"));
      this.updateGuiButtons();
   }

   private void updateGuiButtons() {
      this.getButton(10).setDisplayText("availability.selectquest");
      this.getButton(11).setDisplayText("availability.selectquest");
      this.getButton(12).setDisplayText("availability.selectquest");
      this.getButton(13).setDisplayText("availability.selectquest");
      if(this.availabitily.questId >= 0) {
         Client.sendData(EnumPacketServer.QuestGet, new Object[]{Integer.valueOf(this.availabitily.questId)});
      }

      if(this.availabitily.quest2Id >= 0) {
         Client.sendData(EnumPacketServer.QuestGet, new Object[]{Integer.valueOf(this.availabitily.quest2Id)});
      }

      if(this.availabitily.quest3Id >= 0) {
         Client.sendData(EnumPacketServer.QuestGet, new Object[]{Integer.valueOf(this.availabitily.quest3Id)});
      }

      if(this.availabitily.quest4Id >= 0) {
         Client.sendData(EnumPacketServer.QuestGet, new Object[]{Integer.valueOf(this.availabitily.quest4Id)});
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.availabitily.questAvailable = EnumAvailabilityQuest.values()[button.getValue()];
         if(this.availabitily.questAvailable == EnumAvailabilityQuest.Always) {
            this.availabitily.questId = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 1) {
         this.availabitily.quest2Available = EnumAvailabilityQuest.values()[button.getValue()];
         if(this.availabitily.quest2Available == EnumAvailabilityQuest.Always) {
            this.availabitily.quest2Id = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 2) {
         this.availabitily.quest3Available = EnumAvailabilityQuest.values()[button.getValue()];
         if(this.availabitily.quest3Available == EnumAvailabilityQuest.Always) {
            this.availabitily.quest3Id = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 3) {
         this.availabitily.quest4Available = EnumAvailabilityQuest.values()[button.getValue()];
         if(this.availabitily.quest4Available == EnumAvailabilityQuest.Always) {
            this.availabitily.quest4Id = -1;
         }

         this.initGui();
      }

      GuiNPCQuestSelection gui;
      if(button.field_146127_k == 10) {
         this.slot = 1;
         gui = new GuiNPCQuestSelection(super.npc, this.getParent(), this.availabitily.questId);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 11) {
         this.slot = 2;
         gui = new GuiNPCQuestSelection(super.npc, this.getParent(), this.availabitily.quest2Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 12) {
         this.slot = 3;
         gui = new GuiNPCQuestSelection(super.npc, this.getParent(), this.availabitily.quest3Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 13) {
         this.slot = 4;
         gui = new GuiNPCQuestSelection(super.npc, this.getParent(), this.availabitily.quest4Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 20) {
         this.availabitily.questId = -1;
         this.getButton(10).setDisplayText("availability.selectquest");
      }

      if(button.field_146127_k == 21) {
         this.availabitily.quest2Id = -1;
         this.getButton(11).setDisplayText("availability.selectquest");
      }

      if(button.field_146127_k == 22) {
         this.availabitily.quest3Id = -1;
         this.getButton(12).setDisplayText("availability.selectquest");
      }

      if(button.field_146127_k == 23) {
         this.availabitily.quest4Id = -1;
         this.getButton(13).setDisplayText("availability.selectquest");
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void selected(int id, String name) {
      if(this.slot == 1) {
         this.availabitily.questId = id;
      }

      if(this.slot == 2) {
         this.availabitily.quest2Id = id;
      }

      if(this.slot == 3) {
         this.availabitily.quest3Id = id;
      }

      if(this.slot == 4) {
         this.availabitily.quest4Id = id;
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      Quest quest = new Quest();
      quest.readNBT(compound);
      if(this.availabitily.questId == quest.id) {
         this.getButton(10).setDisplayText(quest.title);
      }

      if(this.availabitily.quest2Id == quest.id) {
         this.getButton(11).setDisplayText(quest.title);
      }

      if(this.availabitily.quest3Id == quest.id) {
         this.getButton(12).setDisplayText(quest.title);
      }

      if(this.availabitily.quest4Id == quest.id) {
         this.getButton(13).setDisplayText(quest.title);
      }

   }
}
