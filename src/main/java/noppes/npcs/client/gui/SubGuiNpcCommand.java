package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcCommand extends SubGuiInterface implements ITextfieldListener {

   public String command;


   public SubGuiNpcCommand(String command) {
      this.command = command;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 84, 248, 20, this.command));
      this.getTextField(4).setMaxStringLength(32767);
      this.addLabel(new GuiNpcLabel(4, "advMode.command", super.guiLeft + 4, super.guiTop + 110));
      this.addLabel(new GuiNpcLabel(5, "advMode.nearestPlayer", super.guiLeft + 4, super.guiTop + 125));
      this.addLabel(new GuiNpcLabel(6, "advMode.randomPlayer", super.guiLeft + 4, super.guiTop + 140));
      this.addLabel(new GuiNpcLabel(7, "advMode.allPlayers", super.guiLeft + 4, super.guiTop + 155));
      this.addLabel(new GuiNpcLabel(8, "dialog.commandoptionplayer", super.guiLeft + 4, super.guiTop + 170));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 190, 98, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 4) {
         this.command = textfield.getText();
      }

   }
}
