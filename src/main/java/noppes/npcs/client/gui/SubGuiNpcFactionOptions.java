package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.FactionOptions;

public class SubGuiNpcFactionOptions extends SubGuiInterface implements IScrollData, ICustomScrollListener {

   private FactionOptions options;
   private HashMap data = new HashMap();
   private GuiCustomScroll scrollFactions;
   private int selected = -1;


   public SubGuiNpcFactionOptions(FactionOptions options) {
      this.options = options;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
      Client.sendData(EnumPacketServer.FactionsGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      if(this.scrollFactions == null) {
         this.scrollFactions = new GuiCustomScroll(this, 0);
         this.scrollFactions.setSize(120, 208);
         this.scrollFactions.guiLeft = super.guiLeft + 130;
         this.scrollFactions.guiTop = super.guiTop + 4;
      }

      this.addScroll(this.scrollFactions);
      this.addLabel(new GuiNpcLabel(0, "1: ", super.guiLeft + 4, super.guiTop + 12));
      String label;
      if(this.data.containsValue(Integer.valueOf(this.options.factionId))) {
         this.addLabel(new GuiNpcLabel(1, this.getFactionName(this.options.factionId), super.guiLeft + 12, super.guiTop + 8));
         label = "";
         if(this.options.decreaseFactionPoints) {
            label = label + StatCollector.translateToLocal("gui.decrease");
         } else {
            label = label + StatCollector.translateToLocal("gui.increase");
         }

         label = label + " " + this.options.factionPoints + " " + StatCollector.translateToLocal("faction.points");
         this.addLabel(new GuiNpcLabel(3, label, super.guiLeft + 12, super.guiTop + 16));
         this.addButton(new GuiNpcButton(0, super.guiLeft + 110, super.guiTop + 7, 20, 20, "X"));
      }

      this.addLabel(new GuiNpcLabel(4, "2: ", super.guiLeft + 4, super.guiTop + 40));
      if(this.data.containsValue(Integer.valueOf(this.options.faction2Id))) {
         this.addLabel(new GuiNpcLabel(5, this.getFactionName(this.options.faction2Id), super.guiLeft + 12, super.guiTop + 36));
         label = "";
         if(this.options.decreaseFaction2Points) {
            label = label + StatCollector.translateToLocal("gui.decrease");
         } else {
            label = label + StatCollector.translateToLocal("gui.increase");
         }

         label = label + " " + this.options.faction2Points + " " + StatCollector.translateToLocal("faction.points");
         this.addLabel(new GuiNpcLabel(6, label, super.guiLeft + 12, super.guiTop + 44));
         this.addButton(new GuiNpcButton(1, super.guiLeft + 110, super.guiTop + 35, 20, 20, "X"));
      }

      if(this.selected >= 0 && (!this.data.containsValue(Integer.valueOf(this.options.faction2Id)) || !this.data.containsValue(Integer.valueOf(this.options.factionId))) && !this.options.hasFaction(this.selected)) {
         this.addButton(new GuiNpcButton(2, super.guiLeft + 4, super.guiTop + 60, 90, 20, new String[]{"gui.increase", "gui.decrease"}, 0));
         this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 82, 110, 20, "10"));
         this.getTextField(1).numbersOnly = true;
         this.getTextField(1).setMinMaxDefault(1, 100000, 10);
         this.addButton(new GuiNpcButton(3, super.guiLeft + 4, super.guiTop + 104, 60, 20, "gui.add"));
      }

      this.addButton(new GuiNpcButton(66, super.guiLeft + 20, super.guiTop + 192, 90, 20, "gui.done"));
   }

   private String getFactionName(int faction) {
      Iterator var2 = this.data.keySet().iterator();

      String s;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         s = (String)var2.next();
      } while(((Integer)this.data.get(s)).intValue() != faction);

      return s;
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.options.factionId = -1;
         this.initGui();
      }

      if(id == 1) {
         this.options.faction2Id = -1;
         this.initGui();
      }

      if(id == 3) {
         if(!this.data.containsValue(Integer.valueOf(this.options.factionId))) {
            this.options.factionId = this.selected;
            this.options.decreaseFactionPoints = this.getButton(2).getValue() == 1;
            this.options.factionPoints = this.getTextField(1).getInteger();
         } else if(!this.data.containsValue(Integer.valueOf(this.options.faction2Id))) {
            this.options.faction2Id = this.selected;
            this.options.decreaseFaction2Points = this.getButton(2).getValue() == 1;
            this.options.faction2Points = this.getTextField(1).getInteger();
         }

         this.initGui();
      }

      if(id == 66) {
         this.close();
      }

   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      this.selected = ((Integer)this.data.get(guiCustomScroll.getSelected())).intValue();
      this.initGui();
   }

   public void setData(Vector list, HashMap data) {
      GuiCustomScroll scroll = this.getScroll(0);
      String name = scroll.getSelected();
      this.data = data;
      scroll.setList(list);
      if(name != null) {
         scroll.setSelected(name);
      }

      this.initGui();
   }

   public void setSelected(String selected) {}
}
