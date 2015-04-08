package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataStats;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcSoundSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcButtonYesNo;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcRangeProperties extends SubGuiInterface implements ITextfieldListener {

   private DataStats stats;
   private GuiNpcSoundSelection gui;


   public SubGuiNpcRangeProperties(DataStats stats) {
      this.stats = stats;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 4;
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 85, y, 50, 18, this.stats.accuracy + ""));
      this.addLabel(new GuiNpcLabel(1, "stats.accuracy", super.guiLeft + 5, y + 5));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(0, 100, 90);
      GuiNpcTextField var10001;
      int var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(2, this, super.fontRendererObj, var10006, y, 50, 18, this.stats.rangedRange + "");
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(2, "stats.rangedrange", super.guiLeft + 5, y + 5));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(1, 64, 2);
      var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(3, this, super.fontRendererObj, var10006, y, 50, 18, this.stats.minDelay + "");
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(3, "stats.mindelay", super.guiLeft + 5, y + 5));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(3).setMinMaxDefault(1, 9999, 20);
      var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(4, this, super.fontRendererObj, var10006, y, 50, 18, this.stats.maxDelay + "");
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(4, "stats.maxdelay", super.guiLeft + 5, y + 5));
      this.getTextField(4).numbersOnly = true;
      this.getTextField(4).setMinMaxDefault(1, 9999, 20);
      var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(5, this, super.fontRendererObj, var10006, y, 50, 18, this.stats.fireRate + "");
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(5, "stats.burstspeed", super.guiLeft + 5, y + 5));
      this.getTextField(5).numbersOnly = true;
      this.getTextField(5).setMinMaxDefault(0, 30, 0);
      var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(6, this, super.fontRendererObj, var10006, y, 50, 18, this.stats.burstCount + "");
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(6, "stats.burstcount", super.guiLeft + 5, y + 5));
      this.getTextField(6).numbersOnly = true;
      this.getTextField(6).setMinMaxDefault(1, 100, 20);
      var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(7, this, super.fontRendererObj, var10006, y, 100, 20, this.stats.fireSound);
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(7, "stats.firesound:", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(7, super.guiLeft + 187, y, 60, 20, "mco.template.button.select"));
      var10006 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcTextField(8, this, super.fontRendererObj, var10006, y, 50, 18, this.stats.shotCount + "");
      this.addTextField(var10001);
      this.addLabel(new GuiNpcLabel(8, "stats.shotcount", super.guiLeft + 5, y + 5));
      this.getTextField(8).numbersOnly = true;
      this.getTextField(8).setMinMaxDefault(1, 10, 1);
      GuiNpcButtonYesNo var2;
      int var10004 = super.guiLeft + 100;
      y += 22;
      var2 = new GuiNpcButtonYesNo(9, var10004, y, this.stats.aimWhileShooting);
      this.addButton(var2);
      this.addLabel(new GuiNpcLabel(9, "stats.aimWhileShooting", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 190, super.guiTop + 190, 60, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 1) {
         this.stats.accuracy = textfield.getInteger();
      } else if(textfield.id == 2) {
         this.stats.rangedRange = textfield.getInteger();
      } else if(textfield.id == 3) {
         if(textfield.getInteger() > this.stats.maxDelay) {
            this.stats.minDelay = this.stats.maxDelay;
            textfield.setText(this.stats.minDelay + "");
         } else {
            this.stats.minDelay = textfield.getInteger();
         }
      } else if(textfield.id == 4) {
         if(textfield.getInteger() < this.stats.minDelay) {
            this.stats.maxDelay = this.stats.minDelay;
            textfield.setText(this.stats.maxDelay + "");
         } else {
            this.stats.maxDelay = textfield.getInteger();
         }
      } else if(textfield.id == 5) {
         this.stats.fireRate = textfield.getInteger();
      } else if(textfield.id == 6) {
         this.stats.burstCount = textfield.getInteger();
      } else if(textfield.id == 7) {
         this.stats.fireSound = textfield.getText();
      } else if(textfield.id == 8) {
         this.stats.shotCount = textfield.getInteger();
      }

   }

   public void elementClicked() {
      this.getTextField(7).setText(this.gui.getSelected());
      this.unFocused(this.getTextField(7));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 7) {
         NoppesUtil.openGUI(super.player, this.gui = new GuiNpcSoundSelection(super.npc, super.parent, this.getTextField(7).getText()));
      }

      if(id == 66) {
         this.close();
      } else if(id == 9) {
         this.stats.aimWhileShooting = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

   }
}
