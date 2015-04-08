package noppes.npcs.client.gui.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiEditText;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ISubGuiListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCManageLinkedNpc extends GuiNPCInterface2 implements IScrollData, ISubGuiListener {

   private GuiCustomScroll scroll;
   private List data = new ArrayList();
   public static GuiScreen Instance;


   public GuiNPCManageLinkedNpc(EntityNPCInterface npc) {
      super(npc);
      Instance = this;
      Client.sendData(EnumPacketServer.LinkedGetAll, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(1, super.guiLeft + 358, super.guiTop + 38, 58, 20, "gui.add"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 358, super.guiTop + 61, 58, 20, "gui.remove"));
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(143, 208);
      }

      this.scroll.guiLeft = super.guiLeft + 214;
      this.scroll.guiTop = super.guiTop + 4;
      this.scroll.setList(this.data);
      this.addScroll(this.scroll);
   }

   public void buttonEvent(GuiButton button) {
      if(button.id == 1) {
         this.save();
         this.setSubGui(new SubGuiEditText("New"));
      }

      if(button.id == 2 && this.scroll.hasSelected()) {
         Client.sendData(EnumPacketServer.LinkedRemove, new Object[]{this.scroll.getSelected()});
      }

   }

   public void subGuiClosed(SubGuiInterface subgui) {
      if(!((SubGuiEditText)subgui).cancelled) {
         Client.sendData(EnumPacketServer.LinkedAdd, new Object[]{((SubGuiEditText)subgui).text});
      }

   }

   public void setData(Vector list, HashMap data) {
      this.data = new ArrayList(list);
      this.initGui();
   }

   public void setSelected(String selected) {}

   public void save() {}
}
