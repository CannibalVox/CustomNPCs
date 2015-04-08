package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCFactionSelection;
import noppes.npcs.client.gui.SubGuiNpcAvailabilityDialog;
import noppes.npcs.client.gui.SubGuiNpcAvailabilityQuest;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumAvailabilityFactionType;
import noppes.npcs.constants.EnumDayTime;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.Faction;

public class SubGuiNpcAvailability extends SubGuiInterface implements ITextfieldListener, GuiSelectionListener, IGuiData {

   private Availability availabitily;
   private int slot = 0;


   public SubGuiNpcAvailability(Availability availabitily) {
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
      this.addButton(new GuiNpcButton(0, super.guiLeft + 34, super.guiTop + 12, 180, 20, "availability.selectdialog"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 34, super.guiTop + 35, 180, 20, "availability.selectquest"));
      this.addButton(new GuiNpcButton(20, super.guiLeft + 4, super.guiTop + 104, 50, 20, new String[]{"availability.always", "availability.is", "availability.isnot"}, this.availabitily.factionAvailable.ordinal()));
      this.addButton(new GuiNpcButton(22, super.guiLeft + 56, super.guiTop + 104, 60, 20, new String[]{"faction.friendly", "faction.neutral", "faction.unfriendly"}, this.availabitily.factionStance.ordinal()));
      this.addButton(new GuiNpcButton(21, super.guiLeft + 118, super.guiTop + 104, 110, 20, "availability.selectfaction"));
      this.getButton(21).setEnabled(this.availabitily.factionAvailable != EnumAvailabilityFactionType.Always);
      this.getButton(22).setEnabled(this.availabitily.factionAvailable != EnumAvailabilityFactionType.Always);
      this.addButton(new GuiNpcButton(23, super.guiLeft + 230, super.guiTop + 104, 20, 20, "X"));
      this.addButton(new GuiNpcButton(24, super.guiLeft + 4, super.guiTop + 126, 50, 20, new String[]{"availability.always", "availability.is", "availability.isnot"}, this.availabitily.faction2Available.ordinal()));
      this.addButton(new GuiNpcButton(27, super.guiLeft + 56, super.guiTop + 126, 60, 20, new String[]{"faction.friendly", "faction.neutral", "faction.unfriendly"}, this.availabitily.faction2Stance.ordinal()));
      this.addButton(new GuiNpcButton(25, super.guiLeft + 118, super.guiTop + 126, 110, 20, "availability.selectfaction"));
      this.getButton(25).setEnabled(this.availabitily.faction2Available != EnumAvailabilityFactionType.Always);
      this.getButton(27).setEnabled(this.availabitily.faction2Available != EnumAvailabilityFactionType.Always);
      this.addButton(new GuiNpcButton(26, super.guiLeft + 230, super.guiTop + 126, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(50, "availability.daytime", super.guiLeft + 4, super.guiTop + 153));
      this.addButton(new GuiNpcButton(50, super.guiLeft + 50, super.guiTop + 148, 150, 20, new String[]{"availability.wholeday", "availability.night", "availability.day"}, this.availabitily.daytime.ordinal()));
      this.addLabel(new GuiNpcLabel(51, "availability.minlevel", super.guiLeft + 4, super.guiTop + 175));
      this.addTextField(new GuiNpcTextField(51, this, super.fontRendererObj, super.guiLeft + 50, super.guiTop + 170, 90, 20, this.availabitily.minPlayerLevel + ""));
      this.getTextField(51).numbersOnly = true;
      this.getTextField(51).setMinMaxDefault(0, 400, 0);
      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 192, 98, 20, "gui.done"));
      this.updateGuiButtons();
   }

   private void updateGuiButtons() {
      if(this.availabitily.factionId >= 0) {
         Client.sendData(EnumPacketServer.FactionGet, new Object[]{Integer.valueOf(this.availabitily.factionId)});
      }

      if(this.availabitily.faction2Id >= 0) {
         Client.sendData(EnumPacketServer.FactionGet, new Object[]{Integer.valueOf(this.availabitily.faction2Id)});
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.setSubGui(new SubGuiNpcAvailabilityDialog(this.availabitily));
      }

      if(button.field_146127_k == 1) {
         this.setSubGui(new SubGuiNpcAvailabilityQuest(this.availabitily));
      }

      if(button.field_146127_k == 20) {
         this.availabitily.setFactionAvailability(button.getValue());
         if(this.availabitily.factionAvailable == EnumAvailabilityFactionType.Always) {
            this.availabitily.factionId = -1;
         }

         this.initGui();
      }

      if(button.field_146127_k == 24) {
         this.availabitily.setFaction2Availability(button.getValue());
         if(this.availabitily.faction2Available == EnumAvailabilityFactionType.Always) {
            this.availabitily.faction2Id = -1;
         }

         this.initGui();
      }

      GuiNPCFactionSelection gui;
      if(button.field_146127_k == 21) {
         this.slot = 1;
         gui = new GuiNPCFactionSelection(super.npc, this.getParent(), this.availabitily.factionId);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 25) {
         this.slot = 2;
         gui = new GuiNPCFactionSelection(super.npc, this.getParent(), this.availabitily.faction2Id);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 22) {
         this.availabitily.setFactionAvailabilityStance(button.getValue());
      }

      if(button.field_146127_k == 27) {
         this.availabitily.setFaction2AvailabilityStance(button.getValue());
      }

      if(button.field_146127_k == 23) {
         this.availabitily.factionId = -1;
         this.getButton(21).setDisplayText("availability.selectfaction");
      }

      if(button.field_146127_k == 26) {
         this.availabitily.faction2Id = -1;
         this.getButton(25).setDisplayText("availability.selectfaction");
      }

      if(button.field_146127_k == 50) {
         this.availabitily.daytime = EnumDayTime.values()[button.getValue()];
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void selected(int id, String name) {
      if(this.slot == 1) {
         this.availabitily.factionId = id;
      }

      if(this.slot == 2) {
         this.availabitily.faction2Id = id;
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if(compound.hasKey("Slot")) {
         Faction faction = new Faction();
         faction.readNBT(compound);
         if(this.availabitily.factionId == faction.id) {
            this.getButton(21).setDisplayText(faction.name);
         }

         if(this.availabitily.faction2Id == faction.id) {
            this.getButton(25).setDisplayText(faction.name);
         }
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 51) {
         this.availabitily.minPlayerLevel = textfield.getInteger();
      }

   }
}
