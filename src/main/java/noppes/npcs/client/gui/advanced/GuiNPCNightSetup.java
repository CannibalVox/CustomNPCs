package noppes.npcs.client.gui.advanced;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.TransformData;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCNightSetup extends GuiNPCInterface2 implements IGuiData {

   private TransformData data;


   public GuiNPCNightSetup(EntityNPCInterface npc) {
      super(npc);
      this.data = npc.transform;
      Client.sendData(EnumPacketServer.TransformGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "menu.display", super.guiLeft + 4, super.guiTop + 25));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 104, super.guiTop + 20, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasDisplay?1:0));
      this.addLabel(new GuiNpcLabel(1, "menu.stats", super.guiLeft + 4, super.guiTop + 47));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 104, super.guiTop + 42, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasStats?1:0));
      this.addLabel(new GuiNpcLabel(2, "menu.ai", super.guiLeft + 4, super.guiTop + 69));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 104, super.guiTop + 64, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasAi?1:0));
      this.addLabel(new GuiNpcLabel(3, "menu.inventory", super.guiLeft + 4, super.guiTop + 91));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 104, super.guiTop + 86, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasInv?1:0));
      this.addLabel(new GuiNpcLabel(4, "menu.advanced", super.guiLeft + 4, super.guiTop + 113));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 104, super.guiTop + 108, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasAdvanced?1:0));
      this.addLabel(new GuiNpcLabel(5, "role.name", super.guiLeft + 4, super.guiTop + 135));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 104, super.guiTop + 130, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasRole?1:0));
      this.addLabel(new GuiNpcLabel(6, "job.name", super.guiLeft + 4, super.guiTop + 157));
      this.addButton(new GuiNpcButton(6, super.guiLeft + 104, super.guiTop + 152, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasJob?1:0));
      this.addLabel(new GuiNpcLabel(10, "advanced.editingmode", super.guiLeft + 170, super.guiTop + 9));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 244, super.guiTop + 4, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.editingModus?1:0));
      if(this.data.editingModus) {
         this.addButton(new GuiNpcButton(11, super.guiLeft + 170, super.guiTop + 34, "advanced.loadday"));
         this.addButton(new GuiNpcButton(12, super.guiLeft + 170, super.guiTop + 56, "advanced.loadnight"));
      }

   }

   public void buttonEvent(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.data.hasDisplay = button.getValue() == 1;
      }

      if(button.field_146127_k == 1) {
         this.data.hasStats = button.getValue() == 1;
      }

      if(button.field_146127_k == 2) {
         this.data.hasAi = button.getValue() == 1;
      }

      if(button.field_146127_k == 3) {
         this.data.hasInv = button.getValue() == 1;
      }

      if(button.field_146127_k == 4) {
         this.data.hasAdvanced = button.getValue() == 1;
      }

      if(button.field_146127_k == 5) {
         this.data.hasRole = button.getValue() == 1;
      }

      if(button.field_146127_k == 6) {
         this.data.hasJob = button.getValue() == 1;
      }

      if(button.field_146127_k == 10) {
         this.data.editingModus = button.getValue() == 1;
         this.save();
         this.initGui();
      }

      if(button.field_146127_k == 11) {
         Client.sendData(EnumPacketServer.TransformLoad, new Object[]{Boolean.valueOf(false)});
      }

      if(button.field_146127_k == 12) {
         Client.sendData(EnumPacketServer.TransformLoad, new Object[]{Boolean.valueOf(true)});
      }

   }

   public void save() {
      Client.sendData(EnumPacketServer.TransformSave, new Object[]{this.data.writeOptions(new NBTTagCompound())});
   }

   public void setGuiData(NBTTagCompound compound) {
      this.data.readOptions(compound);
      this.initGui();
   }
}
