package noppes.npcs.client.gui.model;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.entity.EntityCustomNpc;

public class GuiModelScale extends GuiModelInterface implements ISliderListener {

   private GuiScreen parent;
   private int type = 0;


   public GuiModelScale(GuiScreen parent, ModelData data, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      super.xOffset = 100;
      super.ySize = 230;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 2;
      this.addLabel(new GuiNpcLabel(20, "Head", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 0) {
         this.drawSlider(y, super.playerdata.head);
         y += 88;
      } else {
         this.addButton(new GuiNpcButton(0, super.guiLeft + 110, y, 60, 20, "Edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(21, "Body", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 1) {
         this.drawSlider(y, super.playerdata.body);
         y += 88;
      } else {
         this.addButton(new GuiNpcButton(1, super.guiLeft + 110, y, 60, 20, "Edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(22, "Arms", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 2) {
         this.drawSlider(y, super.playerdata.arms);
         y += 88;
      } else {
         this.addButton(new GuiNpcButton(2, super.guiLeft + 110, y, 60, 20, "Edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(23, "Legs", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 3) {
         this.drawSlider(y, super.playerdata.legs);
         y += 88;
      } else {
         this.addButton(new GuiNpcButton(3, super.guiLeft + 110, y, 60, 20, "Edit"));
         y += 24;
      }

   }

   private void drawSlider(int y, ModelPartConfig config) {
      y += 15;
      this.addLabel(new GuiNpcLabel(10, "Width", super.guiLeft, y + 5, 16777215));
      this.addSlider(new GuiNpcSlider(this, 10, super.guiLeft + 50, y, config.scaleX - 0.5F));
      y += 22;
      this.addLabel(new GuiNpcLabel(11, "Height", super.guiLeft, y + 5, 16777215));
      this.addSlider(new GuiNpcSlider(this, 11, super.guiLeft + 50, y, config.scaleY - 0.5F));
      y += 22;
      this.addLabel(new GuiNpcLabel(12, "Depth", super.guiLeft, y + 5, 16777215));
      this.addSlider(new GuiNpcSlider(this, 12, super.guiLeft + 50, y, config.scaleZ - 0.5F));
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      if(btn.id < 4) {
         this.type = btn.id;
         this.initGui();
      }

   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }

   public void mouseDragged(GuiNpcSlider slider) {
      int percent = (int)(50.0F + slider.sliderValue * 100.0F);
      slider.setString(percent + "%");
      ModelPartConfig config = super.playerdata.head;
      if(this.type == 1) {
         config = super.playerdata.body;
      } else if(this.type == 2) {
         config = super.playerdata.arms;
      } else if(this.type == 3) {
         config = super.playerdata.legs;
      }

      if(slider.field_146127_k == 10) {
         config.scaleX = slider.sliderValue + 0.5F;
      }

      if(slider.field_146127_k == 11) {
         config.scaleY = slider.sliderValue + 0.5F;
      }

      if(slider.field_146127_k == 12) {
         config.scaleZ = slider.sliderValue + 0.5F;
      }

      super.npc.updateHitbox();
   }

   public void mousePressed(GuiNpcSlider slider) {}

   public void mouseReleased(GuiNpcSlider slider) {}
}
