package noppes.npcs.client.gui.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCAdvancedLinkedNpc extends GuiNPCInterface2 implements IScrollData, ICustomScrollListener {

   private GuiCustomScroll scroll;
   private List data = new ArrayList();
   public static GuiScreen Instance;


   public GuiNPCAdvancedLinkedNpc(EntityNPCInterface npc) {
      super(npc);
      Instance = this;
      Client.sendData(EnumPacketServer.LinkedGetAll, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(1, super.guiLeft + 358, super.guiTop + 38, 58, 20, "gui.clear"));
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(143, 208);
      }

      this.scroll.guiLeft = super.guiLeft + 137;
      this.scroll.guiTop = super.guiTop + 4;
      this.scroll.setSelected(super.npc.linkedName);
      this.scroll.setList(this.data);
      this.addScroll(this.scroll);
   }

   public void buttonEvent(GuiButton button) {
      if(button.id == 1) {
         Client.sendData(EnumPacketServer.LinkedSet, new Object[]{""});
      }

   }

   public void setData(Vector list, HashMap data) {
      this.data = new ArrayList(list);
      this.initGui();
   }

   public void setSelected(String selected) {
      this.scroll.setSelected(selected);
   }

   public void save() {}

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      Client.sendData(EnumPacketServer.LinkedSet, new Object[]{guiCustomScroll.getSelected()});
   }
}
