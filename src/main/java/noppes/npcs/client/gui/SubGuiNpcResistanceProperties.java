package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.Resistances;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcResistanceProperties extends SubGuiInterface implements ISliderListener {

   private Resistances resistances;


   public SubGuiNpcResistanceProperties(Resistances resistances) {
      this.resistances = resistances;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "enchantment.knockback", super.guiLeft + 4, super.guiTop + 15));
      this.addSlider(new GuiNpcSlider(this, 0, super.guiLeft + 94, super.guiTop + 10, (int)(this.resistances.knockback * 100.0F - 100.0F) + "%", this.resistances.knockback / 2.0F));
      this.addLabel(new GuiNpcLabel(1, "item.arrow.name", super.guiLeft + 4, super.guiTop + 37));
      this.addSlider(new GuiNpcSlider(this, 1, super.guiLeft + 94, super.guiTop + 32, (int)(this.resistances.arrow * 100.0F - 100.0F) + "%", this.resistances.arrow / 2.0F));
      this.addLabel(new GuiNpcLabel(2, "stats.melee", super.guiLeft + 4, super.guiTop + 59));
      this.addSlider(new GuiNpcSlider(this, 2, super.guiLeft + 94, super.guiTop + 54, (int)(this.resistances.playermelee * 100.0F - 100.0F) + "%", this.resistances.playermelee / 2.0F));
      this.addLabel(new GuiNpcLabel(3, "stats.explosion", super.guiLeft + 4, super.guiTop + 81));
      this.addSlider(new GuiNpcSlider(this, 3, super.guiLeft + 94, super.guiTop + 76, (int)(this.resistances.explosion * 100.0F - 100.0F) + "%", this.resistances.explosion / 2.0F));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 190, super.guiTop + 190, 60, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 66) {
         this.close();
      }

   }

   public void mouseDragged(GuiNpcSlider slider) {
      slider.displayString = (int)(slider.sliderValue * 200.0F - 100.0F) + "%";
   }

   public void mousePressed(GuiNpcSlider slider) {}

   public void mouseReleased(GuiNpcSlider slider) {
      if(slider.field_146127_k == 0) {
         this.resistances.knockback = slider.sliderValue * 2.0F;
      }

      if(slider.field_146127_k == 1) {
         this.resistances.arrow = slider.sliderValue * 2.0F;
      }

      if(slider.field_146127_k == 2) {
         this.resistances.playermelee = slider.sliderValue * 2.0F;
      }

      if(slider.field_146127_k == 3) {
         this.resistances.explosion = slider.sliderValue * 2.0F;
      }

   }
}
