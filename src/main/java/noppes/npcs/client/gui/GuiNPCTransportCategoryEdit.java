package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCTransportCategoryEdit extends GuiNPCInterface {

   private GuiScreen parent;
   private String name;
   private int id;


   public GuiNPCTransportCategoryEdit(EntityNPCInterface npc, GuiScreen parent, String name, int id) {
      super(npc);
      this.parent = parent;
      this.name = name;
      this.id = id;
      super.title = "Npc Transport Category";
   }

   public void initGui() {
      super.initGui();
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.width / 2 - 40, 100, 140, 20, this.name));
      this.addLabel(new GuiNpcLabel(1, "Title:", super.width / 2 - 100 + 4, 105, 16777215));
      this.addButton(new GuiNpcButton(2, super.width / 2 - 100, 210, 98, 20, "gui.back"));
      this.addButton(new GuiNpcButton(3, super.width / 2 + 2, 210, 98, 20, "Save"));
   }

   public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 2) {
         NoppesUtil.openGUI(super.player, this.parent);
         Client.sendData(EnumPacketServer.TransportCategoriesGet, new Object[0]);
      }

      if(id == 3) {
         this.save();
         NoppesUtil.openGUI(super.player, this.parent);
         Client.sendData(EnumPacketServer.TransportCategoriesGet, new Object[0]);
      }

   }

   public void save() {
      String name = this.getTextField(1).getText();
      if(!name.trim().isEmpty()) {
         Client.sendData(EnumPacketServer.TransportCategorySave, new Object[]{name, Integer.valueOf(this.id)});
      }
   }
}
