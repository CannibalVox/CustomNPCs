package noppes.npcs.client.gui.advanced;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcSoundSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCSoundsMenu extends GuiNPCInterface2 implements ITextfieldListener {

   private GuiNpcSoundSelection gui;
   private GuiNpcTextField selectedField;


   public GuiNPCSoundsMenu(EntityNPCInterface npc) {
      super(npc);
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "advanced.idlesound", super.guiLeft + 5, super.guiTop + 20));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 15, 200, 20, super.npc.advanced.idleSound));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 290, super.guiTop + 15, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(2, "advanced.angersound", super.guiLeft + 5, super.guiTop + 45));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 40, 200, 20, super.npc.advanced.angrySound));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 290, super.guiTop + 40, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(3, "advanced.hurtsound", super.guiLeft + 5, super.guiTop + 70));
      this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 65, 200, 20, super.npc.advanced.hurtSound));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 290, super.guiTop + 65, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(4, "advanced.deathsound", super.guiLeft + 5, super.guiTop + 95));
      this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 90, 200, 20, super.npc.advanced.deathSound));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 290, super.guiTop + 90, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(5, "advanced.stepsound", super.guiLeft + 5, super.guiTop + 120));
      this.addTextField(new GuiNpcTextField(5, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 115, 200, 20, super.npc.advanced.stepSound));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 290, super.guiTop + 115, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(6, "advanced.haspitch", super.guiLeft + 5, super.guiTop + 150));
      this.addButton(new GuiNpcButton(6, super.guiLeft + 120, super.guiTop + 145, 80, 20, new String[]{"gui.no", "gui.yes"}, super.npc.advanced.disablePitch?0:1));
   }

   public void buttonEvent(GuiButton button) {
      if(button.id == 6) {
         super.npc.advanced.disablePitch = ((GuiNpcButton)button).getValue() == 0;
      } else {
         this.selectedField = this.getTextField(button.id);
         NoppesUtil.openGUI(super.player, this.gui = new GuiNpcSoundSelection(super.npc, this, this.selectedField.getText()));
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 0) {
         super.npc.advanced.idleSound = textfield.getText();
      }

      if(textfield.id == 2) {
         super.npc.advanced.angrySound = textfield.getText();
      }

      if(textfield.id == 3) {
         super.npc.advanced.hurtSound = textfield.getText();
      }

      if(textfield.id == 4) {
         super.npc.advanced.deathSound = textfield.getText();
      }

      if(textfield.id == 5) {
         super.npc.advanced.stepSound = textfield.getText();
      }

   }

   public void elementClicked() {
      this.selectedField.setText(this.gui.getSelected());
      this.unFocused(this.selectedField);
   }

   public void save() {
      Client.sendData(EnumPacketServer.MainmenuAdvancedSave, new Object[]{super.npc.advanced.writeToNBT(new NBTTagCompound())});
   }
}
