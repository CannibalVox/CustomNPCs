package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.client.Client;
import noppes.npcs.client.controllers.ClientCloneController;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;

public class GuiNpcMobSpawnerSelector extends SubGuiInterface implements IGuiData {

   private GuiCustomScroll scroll;
   private List list;
   private static String search = "";
   public int activeTab = 1;
   public boolean isServer = false;


   public GuiNpcMobSpawnerSelector() {
      super.xSize = 256;
      super.closeOnEsc = true;
      this.setBackground("menubg.png");
   }

   public void initGui() {
      super.initGui();
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 188);
      } else {
         this.scroll.clear();
      }

      this.scroll.guiLeft = super.guiLeft + 4;
      this.scroll.guiTop = super.guiTop + 26;
      this.addScroll(this.scroll);
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 4, 165, 20, search));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 171, super.guiTop + 80, 80, 20, "gui.done"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 171, super.guiTop + 103, 80, 20, "gui.cancel"));
      this.addSideButton(new GuiMenuSideButton(21, super.guiLeft - 69, super.guiTop + 2, 70, 22, "Tab 1"));
      this.addSideButton(new GuiMenuSideButton(22, super.guiLeft - 69, super.guiTop + 23, 70, 22, "Tab 2"));
      this.addSideButton(new GuiMenuSideButton(23, super.guiLeft - 69, super.guiTop + 44, 70, 22, "Tab 3"));
      this.addSideButton(new GuiMenuSideButton(24, super.guiLeft - 69, super.guiTop + 65, 70, 22, "Tab 4"));
      this.addSideButton(new GuiMenuSideButton(25, super.guiLeft - 69, super.guiTop + 86, 70, 22, "Tab 5"));
      this.addSideButton(new GuiMenuSideButton(26, super.guiLeft - 69, super.guiTop + 107, 70, 22, "Tab 6"));
      this.addSideButton(new GuiMenuSideButton(27, super.guiLeft - 69, super.guiTop + 128, 70, 22, "Tab 7"));
      this.addSideButton(new GuiMenuSideButton(28, super.guiLeft - 69, super.guiTop + 149, 70, 22, "Tab 8"));
      this.addSideButton(new GuiMenuSideButton(29, super.guiLeft - 69, super.guiTop + 170, 70, 22, "Tab 9"));
      this.getSideButton(20 + this.activeTab).active = true;
      this.showClones();
   }

   public String getSelected() {
      return this.scroll.getSelected();
   }

   private void showClones() {
      if(this.isServer) {
         Client.sendData(EnumPacketServer.CloneList, new Object[]{Integer.valueOf(this.activeTab)});
      } else {
         new ArrayList();
         this.list = new ArrayList(ClientCloneController.Instance.getClones(this.activeTab));
         this.scroll.setList(this.getSearchList());
      }
   }

   public void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      if(!search.equals(this.getTextField(1).getText())) {
         search = this.getTextField(1).getText().toLowerCase();
         this.scroll.setList(this.getSearchList());
      }
   }

   private List getSearchList() {
      if(search.isEmpty()) {
         return new ArrayList(this.list);
      } else {
         ArrayList list = new ArrayList();
         Iterator var2 = this.list.iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();
            if(name.toLowerCase().contains(search)) {
               list.add(name);
            }
         }

         return list;
      }
   }

   public NBTTagCompound getCompound() {
      String sel = this.scroll.getSelected();
      return sel == null?null:ClientCloneController.Instance.getCloneData(super.player, sel, this.activeTab);
   }

   public void buttonEvent(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.close();
      }

      if(id == 1) {
         this.scroll.clear();
         this.close();
      }

      if(id > 20) {
         this.activeTab = id - 20;
         this.initGui();
      }

   }

   protected NBTTagList newDoubleNBTList(double ... par1ArrayOfDouble) {
      NBTTagList nbttaglist = new NBTTagList();
      double[] adouble = par1ArrayOfDouble;
      int i = par1ArrayOfDouble.length;

      for(int j = 0; j < i; ++j) {
         double d1 = adouble[j];
         nbttaglist.appendTag(new NBTTagDouble(d1));
      }

      return nbttaglist;
   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      NBTTagList nbtlist = compound.getTagList("List", 8);
      ArrayList list = new ArrayList();

      for(int i = 0; i < nbtlist.tagCount(); ++i) {
         list.add(nbtlist.getStringTagAt(i));
      }

      this.list = list;
      this.scroll.setList(this.getSearchList());
   }

}
