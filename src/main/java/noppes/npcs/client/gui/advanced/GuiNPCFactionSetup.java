package noppes.npcs.client.gui.advanced;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiNpcFactionOptions;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCFactionSetup extends GuiNPCInterface2 implements IScrollData, ICustomScrollListener {

   private GuiCustomScroll scrollFactions;
   private HashMap data = new HashMap();


   public GuiNPCFactionSetup(EntityNPCInterface npc) {
      super(npc);
      Client.sendData(EnumPacketServer.FactionsGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "faction.attackHostile", super.guiLeft + 4, super.guiTop + 25));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 144, super.guiTop + 20, 40, 20, new String[]{"gui.no", "gui.yes"}, super.npc.advanced.attackOtherFactions?1:0));
      this.addLabel(new GuiNpcLabel(1, "faction.defend", super.guiLeft + 4, super.guiTop + 47));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 144, super.guiTop + 42, 40, 20, new String[]{"gui.no", "gui.yes"}, super.npc.advanced.defendFaction?1:0));
      this.addLabel(new GuiNpcLabel(12, "faction.ondeath", super.guiLeft + 4, super.guiTop + 69));
      this.addButton(new GuiNpcButton(12, super.guiLeft + 90, super.guiTop + 64, 80, 20, "faction.points"));
      if(this.scrollFactions == null) {
         this.scrollFactions = new GuiCustomScroll(this, 0);
         this.scrollFactions.setSize(180, 200);
      }

      this.scrollFactions.guiLeft = super.guiLeft + 200;
      this.scrollFactions.guiTop = super.guiTop + 4;
      this.addScroll(this.scrollFactions);
   }

   public void buttonEvent(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         super.npc.advanced.attackOtherFactions = button.getValue() == 1;
      }

      if(button.field_146127_k == 1) {
         super.npc.advanced.defendFaction = button.getValue() == 1;
      }

      if(button.field_146127_k == 12) {
         this.setSubGui(new SubGuiNpcFactionOptions(super.npc.advanced.factions));
      }

   }

   public void setData(Vector list, HashMap data) {
      String name = super.npc.getFaction().name;
      this.data = data;
      this.scrollFactions.setList(list);
      if(name != null) {
         this.setSelected(name);
      }

   }

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      if(k == 0 && this.scrollFactions != null) {
         this.scrollFactions.mouseClicked(i, j, k);
      }

   }

   public void setSelected(String selected) {
      this.scrollFactions.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if(guiCustomScroll.id == 0) {
         Client.sendData(EnumPacketServer.FactionSet, new Object[]{this.data.get(this.scrollFactions.getSelected())});
      }

   }

   public void save() {
      Client.sendData(EnumPacketServer.MainmenuAdvancedSave, new Object[]{super.npc.advanced.writeToNBT(new NBTTagCompound())});
   }
}
