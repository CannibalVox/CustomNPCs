package noppes.npcs.client.gui.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiColorSelector;
import noppes.npcs.client.gui.SubGuiNpcFactionPoints;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ISubGuiListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.Faction;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCManageFactions extends GuiNPCInterface2 implements IScrollData, ICustomScrollListener, ITextfieldListener, IGuiData, ISubGuiListener {

   private GuiCustomScroll scrollFactions;
   private HashMap data = new HashMap();
   private Faction faction = new Faction();
   private String selected = null;


   public GuiNPCManageFactions(EntityNPCInterface npc) {
      super(npc);
      Client.sendData(EnumPacketServer.FactionsGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(0, super.guiLeft + 368, super.guiTop + 8, 45, 20, "gui.add"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 368, super.guiTop + 32, 45, 20, "gui.remove"));
      if(this.scrollFactions == null) {
         this.scrollFactions = new GuiCustomScroll(this, 0);
         this.scrollFactions.setSize(143, 208);
      }

      this.scrollFactions.guiLeft = super.guiLeft + 220;
      this.scrollFactions.guiTop = super.guiTop + 4;
      this.addScroll(this.scrollFactions);
      if(this.faction.id != -1) {
         this.addTextField(new GuiNpcTextField(0, this, super.guiLeft + 40, super.guiTop + 4, 136, 20, this.faction.name));
         this.getTextField(0).setMaxStringLength(20);
         this.addLabel(new GuiNpcLabel(0, "gui.name", super.guiLeft + 8, super.guiTop + 9));
         this.addLabel(new GuiNpcLabel(10, "ID", super.guiLeft + 178, super.guiTop + 4));
         this.addLabel(new GuiNpcLabel(11, this.faction.id + "", super.guiLeft + 178, super.guiTop + 14));

         String color;
         for(color = Integer.toHexString(this.faction.color); color.length() < 6; color = "0" + color) {
            ;
         }

         this.addButton(new GuiNpcButton(10, super.guiLeft + 40, super.guiTop + 26, 60, 20, color));
         this.addLabel(new GuiNpcLabel(1, "gui.color", super.guiLeft + 8, super.guiTop + 31));
         this.getButton(10).setTextColor(this.faction.color);
         this.addLabel(new GuiNpcLabel(2, "faction.points", super.guiLeft + 8, super.guiTop + 53));
         this.addButton(new GuiNpcButton(2, super.guiLeft + 100, super.guiTop + 48, 45, 20, "selectServer.edit"));
         this.addLabel(new GuiNpcLabel(3, "faction.hidden", super.guiLeft + 8, super.guiTop + 75));
         this.addButton(new GuiNpcButton(3, super.guiLeft + 100, super.guiTop + 70, 45, 20, new String[]{"gui.no", "gui.yes"}, this.faction.hideFaction?1:0));
         this.addLabel(new GuiNpcLabel(4, "faction.attacked", super.guiLeft + 8, super.guiTop + 97));
         this.addButton(new GuiNpcButton(4, super.guiLeft + 100, super.guiTop + 92, 45, 20, new String[]{"gui.no", "gui.yes"}, this.faction.getsAttacked?1:0));
         this.addLabel(new GuiNpcLabel(6, "faction.hostiles", super.guiLeft + 8, super.guiTop + 145));
         ArrayList hostileList = new ArrayList(this.scrollFactions.getList());
         hostileList.remove(this.faction.name);
         HashSet set = new HashSet();
         Iterator scrollHostileFactions = this.data.keySet().iterator();

         while(scrollHostileFactions.hasNext()) {
            String s = (String)scrollHostileFactions.next();
            if(!s.equals(this.faction.name) && this.faction.attackFactions.contains(this.data.get(s))) {
               set.add(s);
            }
         }

         GuiCustomScroll scrollHostileFactions1 = new GuiCustomScroll(this, 1, true);
         scrollHostileFactions1.setSize(163, 58);
         scrollHostileFactions1.guiLeft = super.guiLeft + 4;
         scrollHostileFactions1.guiTop = super.guiTop + 154;
         scrollHostileFactions1.setList(hostileList);
         scrollHostileFactions1.setSelectedList(set);
         this.addScroll(scrollHostileFactions1);
      }
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.save();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
            ;
         }

         Faction faction = new Faction(-1, name, '\uff00', 1000);
         NBTTagCompound compound = new NBTTagCompound();
         faction.writeNBT(compound);
         Client.sendData(EnumPacketServer.FactionSave, new Object[]{compound});
      }

      if(button.field_146127_k == 1 && this.data.containsKey(this.scrollFactions.getSelected())) {
         Client.sendData(EnumPacketServer.FactionRemove, new Object[]{this.data.get(this.selected)});
         this.scrollFactions.clear();
         this.faction = new Faction();
         this.initGui();
      }

      if(button.field_146127_k == 2) {
         this.setSubGui(new SubGuiNpcFactionPoints(this.faction));
      }

      if(button.field_146127_k == 3) {
         this.faction.hideFaction = button.getValue() == 1;
      }

      if(button.field_146127_k == 4) {
         this.faction.getsAttacked = button.getValue() == 1;
      }

      if(button.field_146127_k == 10) {
         this.setSubGui(new SubGuiColorSelector(this.faction.color));
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      this.faction = new Faction();
      this.faction.readNBT(compound);
      this.setSelected(this.faction.name);
      this.initGui();
   }

   public void setData(Vector list, HashMap data) {
      String name = this.scrollFactions.getSelected();
      this.data = data;
      this.scrollFactions.setList(list);
      if(name != null) {
         this.scrollFactions.setSelected(name);
      }

   }

   public void setSelected(String selected) {
      this.selected = selected;
      this.scrollFactions.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if(guiCustomScroll.id == 0) {
         this.save();
         this.selected = this.scrollFactions.getSelected();
         Client.sendData(EnumPacketServer.FactionGet, new Object[]{this.data.get(this.selected)});
      } else if(guiCustomScroll.id == 1) {
         HashSet set = new HashSet();
         Iterator var6 = guiCustomScroll.getSelectedList().iterator();

         while(var6.hasNext()) {
            String s = (String)var6.next();
            if(this.data.containsKey(s)) {
               set.add(this.data.get(s));
            }
         }

         this.faction.attackFactions = set;
         this.save();
      }

   }

   public void save() {
      if(this.selected != null && this.data.containsKey(this.selected) && this.faction != null) {
         NBTTagCompound compound = new NBTTagCompound();
         this.faction.writeNBT(compound);
         Client.sendData(EnumPacketServer.FactionSave, new Object[]{compound});
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      if(this.faction.id != -1) {
         if(guiNpcTextField.id == 0) {
            String color = guiNpcTextField.getText();
            if(!color.isEmpty() && !this.data.containsKey(color)) {
               String e = this.faction.name;
               this.data.remove(this.faction.name);
               this.faction.name = color;
               this.data.put(this.faction.name, Integer.valueOf(this.faction.id));
               this.selected = color;
               this.scrollFactions.replace(e, this.faction.name);
            }
         } else if(guiNpcTextField.id == 1) {
            boolean color1 = false;

            int color2;
            try {
               color2 = Integer.parseInt(guiNpcTextField.getText(), 16);
            } catch (NumberFormatException var4) {
               color2 = 0;
            }

            this.faction.color = color2;
            guiNpcTextField.setTextColor(this.faction.color);
         }

      }
   }

   public void subGuiClosed(SubGuiInterface subgui) {
      if(subgui instanceof SubGuiColorSelector) {
         this.faction.color = ((SubGuiColorSelector)subgui).color;
         this.initGui();
      }

   }
}
