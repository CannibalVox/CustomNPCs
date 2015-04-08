package noppes.npcs.client.gui.model;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.ModelData;
import noppes.npcs.client.controllers.Preset;
import noppes.npcs.client.controllers.PresetController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;

public class GuiPresetSave extends GuiNPCInterface {

   private ModelData data;
   private GuiScreen parent;


   public GuiPresetSave(GuiScreen parent, ModelData data) {
      this.data = data;
      this.parent = parent;
      super.xSize = 200;
      super.drawDefaultBackground = true;
   }

   public void initGui() {
      super.initGui();
      this.addTextField(new GuiNpcTextField(0, this, super.guiLeft, super.guiTop + 70, 200, 20, ""));
      this.addButton(new GuiNpcButton(0, super.guiLeft, super.guiTop + 100, 98, 20, "Save"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 100, super.guiTop + 100, 98, 20, "Cancel"));
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      GuiNpcButton button = (GuiNpcButton)btn;
      if(button.field_146127_k == 0) {
         String name = this.getTextField(0).getText().trim();
         if(name.isEmpty()) {
            return;
         }

         Preset preset = new Preset();
         preset.name = name;
         preset.data = this.data.copy();
         PresetController.instance.addPreset(preset);
      }

      super.mc.displayGuiScreen(this.parent);
   }

   public void save() {}
}
