package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCTransportCategoryEdit;
import noppes.npcs.client.gui.mainmenu.GuiNPCGlobalMainMenu;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCManageTransporters extends GuiNPCInterface implements IScrollData {

   private GuiNPCStringSlot slot;
   private HashMap data;
   private boolean selectCategory = true;


   public GuiNPCManageTransporters(EntityNPCInterface npc) {
      super(npc);
      Client.sendData(EnumPacketServer.TransportCategoriesGet, new Object[0]);
      super.drawDefaultBackground = false;
      super.title = "Transport Categories";
      this.data = new HashMap();
   }

   public void initGui() {
      super.initGui();
      Vector list = new Vector();
      this.slot = new GuiNPCStringSlot(list, this, false, 18);
      this.slot.registerScrollButtons(4, 5);
      this.addButton(new GuiNpcButton(0, super.width / 2 - 100, super.height - 52, 65, 20, "gui.add"));
      this.addButton(new GuiNpcButton(1, super.width / 2 - 33, super.height - 52, 65, 20, "selectServer.edit"));
      this.getButton(0).setEnabled(this.selectCategory);
      this.getButton(1).setEnabled(this.selectCategory);
      this.addButton(new GuiNpcButton(3, super.width / 2 + 33, super.height - 52, 65, 20, "gui.remove"));
      this.addButton(new GuiNpcButton(2, super.width / 2 - 100, super.height - 31, 98, 20, "gui.open"));
      this.getButton(2).setEnabled(this.selectCategory);
      this.addButton(new GuiNpcButton(4, super.width / 2 + 2, super.height - 31, 98, 20, "gui.back"));
   }

   public void drawScreen(int i, int j, float f) {
      this.slot.drawScreen(i, j, f);
      super.drawScreen(i, j, f);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0 && this.selectCategory) {
         NoppesUtil.openGUI(super.player, new GuiNPCTransportCategoryEdit(super.npc, this, "", -1));
      }

      if(id == 1) {
         if(this.slot.selected == null || this.slot.selected.isEmpty()) {
            return;
         }

         if(this.selectCategory) {
            NoppesUtil.openGUI(super.player, new GuiNPCTransportCategoryEdit(super.npc, this, this.slot.selected, ((Integer)this.data.get(this.slot.selected)).intValue()));
         }
      }

      if(id == 4) {
         if(this.selectCategory) {
            this.close();
            NoppesUtil.openGUI(super.player, new GuiNPCGlobalMainMenu(super.npc));
         } else {
            super.title = "Transport Categories";
            this.selectCategory = true;
            Client.sendData(EnumPacketServer.TransportCategoriesGet, new Object[0]);
            this.initGui();
         }
      }

      if(id == 3) {
         if(this.slot.selected == null || this.slot.selected.isEmpty()) {
            return;
         }

         this.save();
         if(this.selectCategory) {
            Client.sendData(EnumPacketServer.TransportCategoryRemove, new Object[]{this.data.get(this.slot.selected)});
         } else {
            Client.sendData(EnumPacketServer.TransportRemove, new Object[]{this.data.get(this.slot.selected)});
         }

         this.initGui();
      }

      if(id == 2) {
         this.doubleClicked();
      }

   }

   public void doubleClicked() {
      if(this.slot.selected != null && !this.slot.selected.isEmpty()) {
         if(this.selectCategory) {
            this.selectCategory = false;
            super.title = "TransportLocations";
            Client.sendData(EnumPacketServer.TransportsGet, new Object[]{this.data.get(this.slot.selected)});
            this.initGui();
         }

      }
   }

   public void save() {}

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.slot.setList(list);
   }

   public void setSelected(String selected) {}
}
