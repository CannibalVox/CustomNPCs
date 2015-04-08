package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcSoundSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcConversationLine extends SubGuiInterface implements ITextfieldListener {

   public String line;
   public String sound;
   private GuiNpcSoundSelection gui;


   public SubGuiNpcConversationLine(String line, String sound) {
      this.line = line;
      this.sound = sound;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "Line", super.guiLeft + 4, super.guiTop + 10));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 22, 200, 20, this.line));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 4, super.guiTop + 55, 90, 20, "Select Sound"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 96, super.guiTop + 55, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(1, this.sound, super.guiLeft + 4, super.guiTop + 81));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 162, super.guiTop + 192, 90, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      this.line = textfield.getText();
   }

   public void elementClicked() {
      this.sound = this.gui.getSelected();
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 1) {
         NoppesUtil.openGUI(super.player, this.gui = new GuiNpcSoundSelection(super.npc, super.parent, this.sound));
      }

      if(id == 2) {
         this.sound = "";
         this.initGui();
      }

      if(id == 66) {
         this.close();
      }

   }
}
