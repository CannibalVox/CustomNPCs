package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.GuiNpcMobSpawnerSelector;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobSpawner;

public class GuiNpcSpawner extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {

   private JobSpawner job;
   private int slot = -1;
   public String title1 = "gui.selectnpc";
   public String title2 = "gui.selectnpc";
   public String title3 = "gui.selectnpc";
   public String title4 = "gui.selectnpc";
   public String title5 = "gui.selectnpc";
   public String title6 = "gui.selectnpc";


   public GuiNpcSpawner(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobSpawner)npc.jobInterface;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 6;
      this.addButton(new GuiNpcButton(20, super.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(0, "1:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 50, y, this.title1));
      y += 23;
      this.addButton(new GuiNpcButton(21, super.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(1, "2:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 50, y, this.title2));
      y += 23;
      this.addButton(new GuiNpcButton(22, super.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(2, "3:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 50, y, this.title3));
      y += 23;
      this.addButton(new GuiNpcButton(23, super.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(3, "4:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 50, y, this.title4));
      y += 23;
      this.addButton(new GuiNpcButton(24, super.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(4, "5:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 50, y, this.title5));
      y += 23;
      this.addButton(new GuiNpcButton(25, super.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(5, "6:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 50, y, this.title6));
      y += 23;
      this.addLabel(new GuiNpcLabel(6, "spawner.diesafter", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(26, super.guiLeft + 115, y, 40, 20, new String[]{"gui.yes", "gui.no"}, this.job.doesntDie?1:0));
      this.addLabel(new GuiNpcLabel(11, "spawner.despawn", super.guiLeft + 170, y + 5));
      this.addButton(new GuiNpcButton(11, super.guiLeft + 335, y, 40, 20, new String[]{"gui.no", "gui.yes"}, this.job.despawnOnTargetLost?1:0));
      y += 23;
      this.addLabel(new GuiNpcLabel(7, StatCollector.translateToLocal("spawner.posoffset") + " X:", super.guiLeft + 4, y + 5));
      this.addTextField(new GuiNpcTextField(7, this, super.fontRendererObj, super.guiLeft + 99, y, 24, 20, this.job.xOffset + ""));
      this.getTextField(7).numbersOnly = true;
      this.getTextField(7).setMinMaxDefault(-9, 9, 0);
      this.addLabel(new GuiNpcLabel(8, "Y:", super.guiLeft + 125, y + 5));
      this.addTextField(new GuiNpcTextField(8, this, super.fontRendererObj, super.guiLeft + 135, y, 24, 20, this.job.yOffset + ""));
      this.getTextField(8).numbersOnly = true;
      this.getTextField(8).setMinMaxDefault(-9, 9, 0);
      this.addLabel(new GuiNpcLabel(9, "Z:", super.guiLeft + 161, y + 5));
      this.addTextField(new GuiNpcTextField(9, this, super.fontRendererObj, super.guiLeft + 171, y, 24, 20, this.job.zOffset + ""));
      this.getTextField(9).numbersOnly = true;
      this.getTextField(9).setMinMaxDefault(-9, 9, 0);
      y += 23;
      this.addLabel(new GuiNpcLabel(10, "spawner.type", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 80, y, 100, 20, new String[]{"spawner.one", "spawner.all", "spawner.random"}, this.job.spawnType));
   }

   public void elementClicked() {}

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k >= 0 && button.field_146127_k < 6) {
         this.slot = button.field_146127_k + 1;
         this.setSubGui(new GuiNpcMobSpawnerSelector());
      }

      if(button.field_146127_k >= 20 && button.field_146127_k < 26) {
         this.job.setJobCompound(button.field_146127_k - 19, (NBTTagCompound)null);
         Client.sendData(EnumPacketServer.JobSpawnerRemove, new Object[]{Integer.valueOf(button.field_146127_k - 19)});
      }

      if(button.field_146127_k == 26) {
         this.job.doesntDie = button.getValue() == 1;
      }

      if(button.field_146127_k == 10) {
         this.job.spawnType = button.getValue();
      }

      if(button.field_146127_k == 11) {
         this.job.despawnOnTargetLost = button.getValue() == 1;
      }

   }

   public void closeSubGui(SubGuiInterface gui) {
      super.closeSubGui(gui);
      GuiNpcMobSpawnerSelector selector = (GuiNpcMobSpawnerSelector)gui;
      if(selector.isServer) {
         String compound = selector.getSelected();
         if(compound != null) {
            Client.sendData(EnumPacketServer.JobSpawnerAdd, new Object[]{Boolean.valueOf(selector.isServer), compound, Integer.valueOf(selector.activeTab), Integer.valueOf(this.slot)});
         }
      } else {
         NBTTagCompound compound1 = selector.getCompound();
         if(compound1 != null) {
            this.job.setJobCompound(this.slot, compound1);
            Client.sendData(EnumPacketServer.JobSpawnerAdd, new Object[]{Boolean.valueOf(selector.isServer), Integer.valueOf(this.slot), compound1});
         }
      }

      this.initGui();
   }

   public void save() {
      NBTTagCompound compound = this.job.writeToNBT(new NBTTagCompound());
      this.job.cleanCompound(compound);
      Client.sendData(EnumPacketServer.JobSave, new Object[]{compound});
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 7) {
         this.job.xOffset = textfield.getInteger();
      }

      if(textfield.id == 8) {
         this.job.yOffset = textfield.getInteger();
      }

      if(textfield.id == 9) {
         this.job.zOffset = textfield.getInteger();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      this.title1 = compound.getString("Title1");
      this.title2 = compound.getString("Title2");
      this.title3 = compound.getString("Title3");
      this.title4 = compound.getString("Title4");
      this.title5 = compound.getString("Title5");
      this.title6 = compound.getString("Title6");
      this.initGui();
   }
}
