package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextArea;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcTextArea extends SubGuiInterface {

   public String text;


   public SubGuiNpcTextArea(String text) {
      this.text = text;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addTextField(new GuiNpcTextArea(2, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 4, 186, 208, this.text));
      super.buttonList.add(new GuiNpcButton(102, super.guiLeft + 196, super.guiTop + 20, 56, 20, "gui.clear"));
      super.buttonList.add(new GuiNpcButton(101, super.guiLeft + 196, super.guiTop + 43, 56, 20, "gui.paste"));
      super.buttonList.add(new GuiNpcButton(100, super.guiLeft + 196, super.guiTop + 66, 56, 20, "gui.copy"));
      super.buttonList.add(new GuiNpcButton(0, super.guiLeft + 196, super.guiTop + 160, 56, 20, "gui.close"));
   }

   public void close() {
      this.text = this.getTextField(2).getText();
      super.close();
   }

   public void buttonEvent(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 100) {
         NoppesStringUtils.setClipboardContents(this.getTextField(2).getText());
      }

      if(id == 101) {
         this.getTextField(2).setText(NoppesStringUtils.getClipboardContents());
      }

      if(id == 102) {
         this.getTextField(2).setText("");
      }

      if(id == 0) {
         this.close();
      }

   }
}
