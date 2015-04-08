package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataStats;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcRespawn extends SubGuiInterface implements ITextfieldListener {

   private DataStats stats;


   public SubGuiNpcRespawn(DataStats stats) {
      this.stats = stats;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "stats.respawn", super.guiLeft + 5, super.guiTop + 35));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 122, super.guiTop + 30, 56, 20, new String[]{"gui.yes", "gui.day", "gui.night", "gui.no"}, this.stats.spawnCycle));
      if(this.stats.respawnTime > 0) {
         this.addLabel(new GuiNpcLabel(3, "gui.time", super.guiLeft + 5, super.guiTop + 57));
         this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 122, super.guiTop + 53, 50, 18, this.stats.respawnTime + ""));
         this.getTextField(2).numbersOnly = true;
         this.getTextField(2).setMinMaxDefault(1, 99999, 20);
         this.addLabel(new GuiNpcLabel(4, "stats.deadbody", super.guiLeft + 4, super.guiTop + 79));
         this.addButton(new GuiNpcButton(4, super.guiLeft + 122, super.guiTop + 74, 51, 20, new String[]{"gui.no", "gui.yes"}, this.stats.hideKilledBody?1:0));
      }

      this.addLabel(new GuiNpcLabel(1, "stats.naturallydespawns", super.guiLeft + 4, super.guiTop + 101));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 122, super.guiTop + 96, 51, 20, new String[]{"gui.no", "gui.yes"}, this.stats.canDespawn?1:0));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 190, 98, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.stats.spawnCycle = button.getValue();
         if(this.stats.spawnCycle == 3) {
            this.stats.respawnTime = 0;
         } else {
            this.stats.respawnTime = 20;
         }

         this.initGui();
      } else if(button.field_146127_k == 1) {
         this.stats.canDespawn = button.getValue() == 1;
      } else if(button.field_146127_k == 4) {
         this.stats.hideKilledBody = button.getValue() == 1;
      }

      if(id == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 2) {
         this.stats.respawnTime = textfield.getInteger();
      }

   }
}
