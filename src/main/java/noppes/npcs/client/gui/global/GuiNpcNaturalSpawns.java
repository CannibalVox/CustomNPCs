package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.GuiNpcMobSpawnerSelector;
import noppes.npcs.client.gui.SubGuiNpcBiomes;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.SpawnData;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcNaturalSpawns extends GuiNPCInterface2 implements IGuiData, IScrollData, ITextfieldListener, ICustomScrollListener, ISliderListener {

   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private SpawnData spawn = new SpawnData();


   public GuiNpcNaturalSpawns(EntityNPCInterface npc) {
      super(npc);
      Client.sendData(EnumPacketServer.NaturalSpawnGetAll, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(143, 208);
      }

      this.scroll.guiLeft = super.guiLeft + 214;
      this.scroll.guiTop = super.guiTop + 4;
      this.addScroll(this.scroll);
      this.addButton(new GuiNpcButton(1, super.guiLeft + 358, super.guiTop + 38, 58, 20, "gui.add"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 358, super.guiTop + 61, 58, 20, "gui.remove"));
      if(this.spawn.id >= 0) {
         this.showSpawn();
      }

   }

   private void showSpawn() {
      this.addLabel(new GuiNpcLabel(1, "gui.title", super.guiLeft + 4, super.guiTop + 8));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 3, 140, 20, this.spawn.name));
      this.addLabel(new GuiNpcLabel(3, "spawning.biomes", super.guiLeft + 4, super.guiTop + 30));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 120, super.guiTop + 25, 50, 20, "selectServer.edit"));
      this.addSlider(new GuiNpcSlider(this, 4, super.guiLeft + 4, super.guiTop + 47, 180, 20, (float)this.spawn.itemWeight / 100.0F));
      int y = super.guiTop + 70;
      this.addButton(new GuiNpcButton(25, super.guiLeft + 14, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(5, "1:", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 36, y, 170, 20, this.getTitle(this.spawn.compound1)));
   }

   private String getTitle(NBTTagCompound compound) {
      return compound != null && compound.hasKey("ClonedName")?compound.getString("ClonedName"):"gui.selectnpc";
   }

   public void buttonEvent(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 1) {
         this.save();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
            ;
         }

         SpawnData spawn = new SpawnData();
         spawn.name = name;
         Client.sendData(EnumPacketServer.NaturalSpawnSave, new Object[]{spawn.writeNBT(new NBTTagCompound())});
      }

      if(id == 2 && this.data.containsKey(this.scroll.getSelected())) {
         Client.sendData(EnumPacketServer.NaturalSpawnRemove, new Object[]{Integer.valueOf(this.spawn.id)});
         this.spawn = new SpawnData();
         this.scroll.clear();
      }

      if(id == 3) {
         this.setSubGui(new SubGuiNpcBiomes(this.spawn));
      }

      if(id == 5) {
         this.setSubGui(new GuiNpcMobSpawnerSelector());
      }

      if(id == 25) {
         this.spawn.compound1 = new NBTTagCompound();
         this.initGui();
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      String name = guiNpcTextField.getText();
      if(!name.isEmpty() && !this.data.containsKey(name)) {
         String old = this.spawn.name;
         this.data.remove(old);
         this.spawn.name = name;
         this.data.put(this.spawn.name, Integer.valueOf(this.spawn.id));
         this.scroll.replace(old, this.spawn.name);
      } else {
         guiNpcTextField.setText(this.spawn.name);
      }

   }

   public void setData(Vector list, HashMap data) {
      String name = this.scroll.getSelected();
      this.data = data;
      this.scroll.setList(list);
      if(name != null) {
         this.scroll.setSelected(name);
      }

      this.initGui();
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if(guiCustomScroll.id == 0) {
         this.save();
         String selected = this.scroll.getSelected();
         this.spawn = new SpawnData();
         Client.sendData(EnumPacketServer.NaturalSpawnGet, new Object[]{this.data.get(selected)});
      }

   }

   public void save() {
      GuiNpcTextField.unfocus();
      if(this.spawn.id >= 0) {
         Client.sendData(EnumPacketServer.NaturalSpawnSave, new Object[]{this.spawn.writeNBT(new NBTTagCompound())});
      }

   }

   public void setSelected(String selected) {}

   public void closeSubGui(SubGuiInterface gui) {
      super.closeSubGui(gui);
      if(gui instanceof GuiNpcMobSpawnerSelector) {
         GuiNpcMobSpawnerSelector selector = (GuiNpcMobSpawnerSelector)gui;
         NBTTagCompound compound = selector.getCompound();
         if(compound != null) {
            this.spawn.compound1 = compound;
         }

         this.initGui();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      this.spawn.readNBT(compound);
      this.setSelected(this.spawn.name);
      this.initGui();
   }

   public void mouseDragged(GuiNpcSlider guiNpcSlider) {
      guiNpcSlider.displayString = StatCollector.translateToLocal("spawning.weightedChance") + ": " + (int)(guiNpcSlider.sliderValue * 100.0F);
   }

   public void mousePressed(GuiNpcSlider guiNpcSlider) {}

   public void mouseReleased(GuiNpcSlider guiNpcSlider) {
      this.spawn.itemWeight = (int)(guiNpcSlider.sliderValue * 100.0F);
   }
}
