package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.roles.JobPuppet;

public class GuiNpcPuppet extends GuiModelInterface implements ISliderListener {

   private GuiScreen parent;
   private int type = 6;
   private JobPuppet job;
   private JobPuppet.PartConfig part;


   public GuiNpcPuppet(GuiScreen parent, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      super.xOffset = 100;
      super.ySize = 230;
      this.job = (JobPuppet)npc.jobInterface;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop;
      this.addLabel(new GuiNpcLabel(26, "gui.settings", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 6) {
         GuiNpcButton var10001;
         int var10004 = super.guiLeft + 120;
         y += 14;
         var10001 = new GuiNpcButton(30, var10004, y, 60, 20, new String[]{"gui.yes", "gui.no"}, this.job.whileStanding?0:1);
         this.addButton(var10001);
         this.addLabel(new GuiNpcLabel(30, "puppet.standing", super.guiLeft + 30, y + 5, 16777215));
         var10004 = super.guiLeft + 120;
         y += 22;
         var10001 = new GuiNpcButton(31, var10004, y, 60, 20, new String[]{"gui.yes", "gui.no"}, this.job.whileMoving?0:1);
         this.addButton(var10001);
         this.addLabel(new GuiNpcLabel(31, "puppet.walking", super.guiLeft + 30, y + 5, 16777215));
         var10004 = super.guiLeft + 120;
         y += 22;
         var10001 = new GuiNpcButton(32, var10004, y, 60, 20, new String[]{"gui.yes", "gui.no"}, this.job.whileAttacking?0:1);
         this.addButton(var10001);
         this.addLabel(new GuiNpcLabel(32, "puppet.attacking", super.guiLeft + 30, y + 5, 16777215));
         y += 24;
      } else {
         this.addButton(new GuiNpcButton(6, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(20, "model.head", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 0) {
         this.drawSlider(y, this.job.head);
         y += 90;
      } else {
         this.addButton(new GuiNpcButton(0, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(21, "model.body", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 1) {
         this.drawSlider(y, this.job.body);
         y += 90;
      } else {
         this.addButton(new GuiNpcButton(1, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(22, "model.larm", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 2) {
         this.drawSlider(y, this.job.larm);
         y += 90;
      } else {
         this.addButton(new GuiNpcButton(2, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(23, "model.rarm", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 3) {
         this.drawSlider(y, this.job.rarm);
         y += 90;
      } else {
         this.addButton(new GuiNpcButton(3, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(24, "model.lleg", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 4) {
         this.drawSlider(y, this.job.lleg);
         y += 90;
      } else {
         this.addButton(new GuiNpcButton(4, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

      this.addLabel(new GuiNpcLabel(25, "model.rarm", super.guiLeft + 55, y + 5, 16777215));
      if(this.type == 5) {
         this.drawSlider(y, this.job.rleg);
         y += 90;
      } else {
         this.addButton(new GuiNpcButton(5, super.guiLeft + 110, y, 60, 20, "selectServer.edit"));
         y += 24;
      }

   }

   private void drawSlider(int y, JobPuppet.PartConfig config) {
      this.part = config;
      this.addButton(new GuiNpcButton(29, super.guiLeft + 100, y, 80, 20, new String[]{"gui.enabled", "gui.disabled"}, config.disabled?1:0));
      y += 22;
      this.addLabel(new GuiNpcLabel(10, "X", super.guiLeft, y + 5, 16777215));
      this.addSlider(new GuiNpcSlider(this, 10, super.guiLeft + 50, y, config.rotationX + 0.5F));
      y += 22;
      this.addLabel(new GuiNpcLabel(11, "Y", super.guiLeft, y + 5, 16777215));
      this.addSlider(new GuiNpcSlider(this, 11, super.guiLeft + 50, y, config.rotationY + 0.5F));
      y += 22;
      this.addLabel(new GuiNpcLabel(12, "Z", super.guiLeft, y + 5, 16777215));
      this.addSlider(new GuiNpcSlider(this, 12, super.guiLeft + 50, y, config.rotationZ + 0.5F));
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      if(btn.id < 7) {
         this.type = btn.id;
         this.initGui();
      }

      if(btn instanceof GuiNpcButton) {
         GuiNpcButton button = (GuiNpcButton)btn;
         if(btn.id == 29) {
            this.part.disabled = button.getValue() == 1;
         }

         if(btn.id == 30) {
            this.job.whileStanding = button.getValue() == 0;
         }

         if(btn.id == 31) {
            this.job.whileMoving = button.getValue() == 0;
         }

         if(btn.id == 32) {
            this.job.whileAttacking = button.getValue() == 0;
         }

      }
   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
      Client.sendData(EnumPacketServer.JobSave, new Object[]{this.job.writeToNBT(new NBTTagCompound())});
   }

   public void mouseDragged(GuiNpcSlider slider) {
      int percent = (int)(slider.sliderValue * 360.0F);
      slider.setString(percent + "%");
      if(slider.field_146127_k == 10) {
         this.part.rotationX = slider.sliderValue - 0.5F;
      }

      if(slider.field_146127_k == 11) {
         this.part.rotationY = slider.sliderValue - 0.5F;
      }

      if(slider.field_146127_k == 12) {
         this.part.rotationZ = slider.sliderValue - 0.5F;
      }

      super.npc.updateHitbox();
   }

   public void mousePressed(GuiNpcSlider slider) {}

   public void mouseReleased(GuiNpcSlider slider) {}
}
