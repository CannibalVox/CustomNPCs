package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcRemoteEditor extends GuiNPCInterface implements IScrollData, GuiYesNoCallback {

   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();


   public GuiNpcRemoteEditor() {
      super.xSize = 256;
      this.setBackground("menubg.png");
      Client.sendData(EnumPacketServer.RemoteNpcsGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 208);
         this.scroll.guiLeft = super.guiLeft + 4;
         this.scroll.guiTop = super.guiTop + 4;
      }

      this.addScroll(this.scroll);
      String title = StatCollector.translateToLocal("remote.title");
      int x = (super.xSize - super.fontRendererObj.getStringWidth(title)) / 2;
      this.addLabel(new GuiNpcLabel(0, title, super.guiLeft + x, super.guiTop - 8));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 170, super.guiTop + 6, 82, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 170, super.guiTop + 28, 82, 20, "selectWorld.deleteButton"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 170, super.guiTop + 50, 82, 20, "remote.reset"));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 170, super.guiTop + 72, 82, 20, "remote.tp"));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 170, super.guiTop + 110, 82, 20, "remote.resetall"));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 170, super.guiTop + 132, 82, 20, "remote.freeze"));
   }

   public void confirmClicked(boolean flag, int i) {
      if(flag) {
         Client.sendData(EnumPacketServer.RemoteDelete, new Object[]{this.data.get(this.scroll.getSelected())});
      }

      NoppesUtil.openGUI(super.player, this);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 3) {
         Client.sendData(EnumPacketServer.RemoteFreeze, new Object[0]);
      }

      if(id == 5) {
         Iterator entity = this.data.values().iterator();

         while(entity.hasNext()) {
            int ids = ((Integer)entity.next()).intValue();
            Client.sendData(EnumPacketServer.RemoteReset, new Object[]{Integer.valueOf(ids)});
            Entity entity1 = super.player.worldObj.getEntityByID(ids);
            if(entity1 != null && entity1 instanceof EntityNPCInterface) {
               ((EntityNPCInterface)entity1).reset();
            }
         }
      }

      if(this.data.containsKey(this.scroll.getSelected())) {
         if(id == 0) {
            Client.sendData(EnumPacketServer.RemoteMainMenu, new Object[]{this.data.get(this.scroll.getSelected())});
         }

         if(id == 1) {
            GuiYesNo entity2 = new GuiYesNo(this, "Confirm", StatCollector.translateToLocal("gui.delete"), 0);
            this.displayGuiScreen(entity2);
         }

         if(id == 2) {
            Client.sendData(EnumPacketServer.RemoteReset, new Object[]{this.data.get(this.scroll.getSelected())});
            Entity entity3 = super.player.worldObj.getEntityByID(((Integer)this.data.get(this.scroll.getSelected())).intValue());
            if(entity3 != null && entity3 instanceof EntityNPCInterface) {
               ((EntityNPCInterface)entity3).reset();
            }
         }

         if(id == 4) {
            Client.sendData(EnumPacketServer.RemoteTpToNpc, new Object[]{this.data.get(this.scroll.getSelected())});
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
