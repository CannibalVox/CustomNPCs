package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.GuiNpcSoundSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobBard;

public class GuiNpcBard extends GuiNPCInterface2 {

   private JobBard job;
   private GuiNpcSoundSelection gui;


   public GuiNpcBard(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobBard)npc.jobInterface;
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(1, super.guiLeft + 55, super.guiTop + 15, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(0, this.job.song, super.guiLeft + 80, super.guiTop + 20));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 75, super.guiTop + 50, "gui.selectSound"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 75, super.guiTop + 71, new String[]{"gui.none", "item.npcBanjo.name", "item.npcViolin.name", "item.npcGuitar.name", "item.npcFrenchHorn.name", "item.npcHarp.name"}, this.job.getInstrument().ordinal()));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 75, super.guiTop + 92, new String[]{"bard.jukebox", "bard.background"}, this.job.isStreamer?0:1));
      this.addLabel(new GuiNpcLabel(2, "bard.ondistance", super.guiLeft + 60, super.guiTop + 143));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 160, super.guiTop + 138, 40, 20, this.job.minRange + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(2, 64, 5);
      this.addLabel(new GuiNpcLabel(4, "bard.hasoff", super.guiLeft + 60, super.guiTop + 166));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 160, super.guiTop + 161, 60, 20, new String[]{"gui.no", "gui.yes"}, this.job.hasOffRange?1:0));
      this.addLabel(new GuiNpcLabel(3, "bard.offdistance", super.guiLeft + 60, super.guiTop + 189));
      this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 160, super.guiTop + 184, 40, 20, this.job.maxRange + ""));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(3).setMinMaxDefault(2, 64, 10);
      this.getLabel(3).enabled = this.job.hasOffRange;
      this.getTextField(3).enabled = this.job.hasOffRange;
   }

   public void elementClicked() {
      this.job.song = this.gui.getSelected();
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.gui = new GuiNpcSoundSelection(super.npc, this, this.job.song);
         NoppesUtil.openGUI(super.player, this.gui);
         this.job.song = "";
         MusicController.Instance.stopMusic();
      }

      if(button.field_146127_k == 1) {
         this.job.song = "";
         this.getLabel(0).label = "";
         MusicController.Instance.stopMusic();
      }

      if(button.field_146127_k == 2) {
         this.job.setInstrument(button.getValue());
      }

      if(button.field_146127_k == 3) {
         this.job.isStreamer = button.getValue() == 0;
         this.initGui();
      }

      if(button.field_146127_k == 4) {
         this.job.hasOffRange = button.getValue() == 1;
         this.initGui();
      }

   }

   public void save() {
      this.job.minRange = this.getTextField(2).getInteger();
      this.job.maxRange = this.getTextField(3).getInteger();
      if(this.job.minRange > this.job.maxRange) {
         this.job.maxRange = this.job.minRange;
      }

      MusicController.Instance.stopMusic();
      Client.sendData(EnumPacketServer.JobSave, new Object[]{this.job.writeToNBT(new NBTTagCompound())});
   }
}
