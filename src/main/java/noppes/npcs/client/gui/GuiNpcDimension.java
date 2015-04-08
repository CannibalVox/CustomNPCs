package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;

public class GuiNpcDimension extends GuiNPCInterface implements IScrollData {

   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();


   public GuiNpcDimension() {
      super.xSize = 256;
      this.setBackground("menubg.png");
      Client.sendData(EnumPacketServer.DimensionsGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 208);
      }

      this.scroll.guiLeft = super.guiLeft + 4;
      this.scroll.guiTop = super.guiTop + 4;
      this.addScroll(this.scroll);
      String title = StatCollector.translateToLocal("Dimensions");
      int x = (super.xSize - super.fontRendererObj.getStringWidth(title)) / 2;
      this.addLabel(new GuiNpcLabel(0, title, super.guiLeft + x, super.guiTop - 8));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 170, super.guiTop + 72, 82, 20, "remote.tp"));
   }

   public void confirmClicked(boolean flag, int i) {
      if(flag) {
         Client.sendData(EnumPacketServer.RemoteDelete, new Object[]{this.data.get(this.scroll.getSelected())});
      }

      NoppesUtil.openGUI(super.player, this);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(this.data.containsKey(this.scroll.getSelected())) {
         if(id == 4) {
            Client.sendData(EnumPacketServer.DimensionTeleport, new Object[]{this.data.get(this.scroll.getSelected())});
            this.close();
         }

      }
   }

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      this.scroll.mouseClicked(i, j, k);
   }

   public void keyTyped(char c, int i) {
      if(i == 1 || this.isInventoryKey(i)) {
         this.close();
      }

   }

   public void save() {}

   public void setData(Vector list, HashMap data) {
      this.scroll.setList(list);
      this.data = data;
   }

   public void setSelected(String selected) {
      this.getButton(3).setDisplayText(selected);
   }
}
