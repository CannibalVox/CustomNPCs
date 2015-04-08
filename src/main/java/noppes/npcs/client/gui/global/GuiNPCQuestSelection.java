package noppes.npcs.client.gui.global;

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

public class GuiNPCQuestSelection extends GuiNPCInterface implements IScrollData {

   private GuiNPCStringSlot slot;
   private GuiScreen parent;
   private HashMap data;
   private boolean selectCategory = true;
   public GuiSelectionListener listener;
   private int quest;


   public GuiNPCQuestSelection(EntityNPCInterface npc, GuiScreen parent, int quest) {
      super(npc);
      super.drawDefaultBackground = false;
      super.title = "Select Quest Category";
      this.parent = parent;
      this.data = new HashMap();
      this.quest = quest;
      if(quest >= 0) {
         Client.sendData(EnumPacketServer.QuestsGetFromQuest, new Object[]{Integer.valueOf(quest)});
         this.selectCategory = false;
         super.title = "Select Dialog";
      } else {
         Client.sendData(EnumPacketServer.QuestCategoriesGet, new Object[]{Integer.valueOf(quest)});
      }

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
         if(this.selectCategory) {
            this.close();
            NoppesUtil.openGUI(super.player, this.parent);
         } else {
            super.title = "Select Dialog Category";
            this.selectCategory = true;
            Client.sendData(EnumPacketServer.QuestCategoriesGet, new Object[]{Integer.valueOf(this.quest)});
         }
      }

      if(id == 4) {
         if(this.slot.selected == null || this.slot.selected.isEmpty()) {
            return;
         }

         this.doubleClicked();
      }

   }

   public String getSelected() {
      return this.slot.selected;
   }

   public void doubleClicked() {
      if(this.slot.selected != null && !this.slot.selected.isEmpty()) {
         if(this.selectCategory) {
            this.selectCategory = false;
            super.title = "Select Quest";
            Client.sendData(EnumPacketServer.QuestsGet, new Object[]{this.data.get(this.slot.selected)});
         } else {
            this.quest = ((Integer)this.data.get(this.slot.selected)).intValue();
            this.close();
            NoppesUtil.openGUI(super.player, this.parent);
         }

      }
   }

   public void save() {
      if(this.quest >= 0 && this.listener != null) {
         this.listener.selected(this.quest, this.slot.selected);
      }

   }

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.slot.setList(list);
      if(this.quest >= 0) {
         Iterator var3 = data.keySet().iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            if(((Integer)data.get(name)).intValue() == this.quest) {
               this.slot.selected = name;
            }
         }
      }

   }

   public void setSelected(String selected) {}
}
