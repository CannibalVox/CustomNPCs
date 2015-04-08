package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCFactionSelection extends GuiNPCInterface implements IScrollData {

   private GuiNPCStringSlot slot;
   private GuiScreen parent;
   private HashMap data = new HashMap();
   private int factionId;
   public GuiSelectionListener listener;


   public GuiNPCFactionSelection(EntityNPCInterface npc, GuiScreen parent, int dialog) {
      super(npc);
      super.drawDefaultBackground = false;
      super.title = "Select Dialog Category";
      this.parent = parent;
      this.factionId = dialog;
      Client.sendData(EnumPacketServer.FactionsGet, new Object[0]);
      if(parent instanceof GuiSelectionListener) {
         this.listener = (GuiSelectionListener)parent;
      }

   }

   public void initGui() {
      super.initGui();
      Vector list = new Vector();
      this.slot = new GuiNPCStringSlot(list, this, false, 18);
      this.slot.registerScrollButtons(4, 5);
      this.addButton(new GuiNpcButton(2, super.width / 2 - 100, super.height - 41, 98, 20, "gui.back"));
      this.addButton(new GuiNpcButton(4, super.width / 2 + 2, super.height - 41, 98, 20, "mco.template.button.select"));
   }

   public void drawScreen(int i, int j, float f) {
      this.slot.drawScreen(i, j, f);
      super.drawScreen(i, j, f);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 2) {
         this.close();
         NoppesUtil.openGUI(super.player, this.parent);
      }

      if(id == 4) {
         this.doubleClicked();
      }

   }

   public void doubleClicked() {
      if(this.slot.selected != null && !this.slot.selected.isEmpty()) {
         this.factionId = ((Integer)this.data.get(this.slot.selected)).intValue();
         this.close();
         NoppesUtil.openGUI(super.player, this.parent);
      }
   }

   public void save() {
      if(this.factionId >= 0 && this.listener != null) {
         this.listener.selected(this.factionId, this.slot.selected);
      }

   }

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.slot.setList(list);
      if(this.factionId >= 0) {
         Iterator var3 = data.keySet().iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            if(((Integer)data.get(name)).intValue() == this.factionId) {
               this.slot.selected = name;
            }
         }
      }

   }

   public void setSelected(String selected) {}
}
