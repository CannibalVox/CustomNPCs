package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.SubGuiNpcDialogOption;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;

public class SubGuiNpcDialogOptions extends SubGuiInterface {

   private Dialog dialog;


   public SubGuiNpcDialogOptions(Dialog dialog) {
      this.dialog = dialog;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(66, "dialog.options", super.guiLeft, super.guiTop + 4));
      this.getLabel(66).center(super.xSize);

      for(int i = 0; i < 6; ++i) {
         String optionString = "";
         DialogOption option = (DialogOption)this.dialog.options.get(Integer.valueOf(i));
         if(option != null && option.optionType != EnumOptionType.Disabled) {
            optionString = optionString + option.title;
         }

         this.addLabel(new GuiNpcLabel(i + 10, i + 1 + ": ", super.guiLeft + 4, super.guiTop + 16 + i * 32));
         this.addLabel(new GuiNpcLabel(i, optionString, super.guiLeft + 14, super.guiTop + 12 + i * 32));
         this.addButton(new GuiNpcButton(i, super.guiLeft + 13, super.guiTop + 21 + i * 32, 60, 20, "selectServer.edit"));
      }

      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 194, 98, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id < 6) {
         if(!this.dialog.options.containsKey(Integer.valueOf(id))) {
            this.dialog.options.put(Integer.valueOf(id), new DialogOption());
         }

         this.setSubGui(new SubGuiNpcDialogOption((DialogOption)this.dialog.options.get(Integer.valueOf(id))));
      }

      if(id == 66) {
         this.close();
      }

   }
}
