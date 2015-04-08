package noppes.npcs.client.gui.roles;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobHealer;

public class GuiNpcHealer extends GuiNPCInterface2 {

   private JobHealer job;


   public GuiNpcHealer(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobHealer)npc.jobInterface;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "Healing Speed:", super.guiLeft + 60, super.guiTop + 110));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 130, super.guiTop + 105, 40, 20, this.job.speed + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(1, 10, 8);
      this.addLabel(new GuiNpcLabel(2, "Range:", super.guiLeft + 60, super.guiTop + 133));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 130, super.guiTop + 128, 40, 20, this.job.range + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(2, 20, 5);
   }

   public void elementClicked() {}

   public void save() {
      this.job.speed = this.getTextField(1).getInteger();
      this.job.range = this.getTextField(2).getInteger();
      Client.sendData(EnumPacketServer.JobSave, new Object[]{this.job.writeToNBT(new NBTTagCompound())});
   }
}
