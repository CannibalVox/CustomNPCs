package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.DataScript;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiScriptList;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextArea;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiScript extends GuiNPCInterface implements IGuiData, GuiYesNoCallback, ICustomScrollListener {

   public boolean showScript = false;
   private int activeTab = 0;
   public DataScript script;
   public Map languages = new HashMap();
   private static int activeConsole = 0;


   public GuiScript(EntityNPCInterface npc) {
      super(npc);
      this.script = npc.script;
      super.drawDefaultBackground = true;
      super.closeOnEsc = true;
      super.xSize = 420;
      this.setBackground("menubg.png");
      Client.sendData(EnumPacketServer.ScriptDataGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      super.guiTop += 10;
      GuiMenuTopButton top;
      this.addTopButton(top = new GuiMenuTopButton(13, super.guiLeft + 4, super.guiTop - 17, "script.scripts"));
      top.active = this.showScript;
      this.addTopButton(top = new GuiMenuTopButton(14, top, "gui.settings"));
      top.active = !this.showScript;
      this.addTopButton(new GuiMenuTopButton(15, top, "gui.website"));
      ArrayList list = new ArrayList();
      list.add("script.init");
      list.add("script.update");
      list.add("script.interact");
      list.add("dialog.dialog");
      list.add("script.damaged");
      list.add("script.killed");
      list.add("script.attack");
      list.add("script.target");
      list.add("script.collide");
      list.add("script.kills");
      list.add("script.dialogclosed");
      if(this.showScript) {
         this.addLabel(new GuiNpcLabel(0, "script.hooks", super.guiLeft + 4, super.guiTop + 5));
         GuiCustomScroll l = new GuiCustomScroll(this, 1);
         l.setSize(68, 198);
         l.guiLeft = super.guiLeft + 4;
         l.guiTop = super.guiTop + 14;
         l.setUnsortedList(list);
         l.selected = this.activeTab;
         this.addScroll(l);
         ScriptContainer container = (ScriptContainer)this.script.scripts.get(Integer.valueOf(this.activeTab));
         this.addTextField(new GuiNpcTextArea(2, this, super.fontRendererObj, super.guiLeft + 74, super.guiTop + 4, 239, 208, container == null?"":container.script));
         this.addButton(new GuiNpcButton(102, super.guiLeft + 315, super.guiTop + 4, 80, 20, "gui.clear"));
         this.addButton(new GuiNpcButton(101, super.guiLeft + 315, super.guiTop + 25, 80, 20, "gui.paste"));
         this.addButton(new GuiNpcButton(100, super.guiLeft + 315, super.guiTop + 46, 80, 20, "gui.copy"));
         this.addButton(new GuiNpcButton(107, super.guiLeft + 315, super.guiTop + 70, 80, 20, "script.loadscript"));
         GuiCustomScroll scroll = (new GuiCustomScroll(this, 0)).setUnselectable();
         scroll.setSize(100, 120);
         scroll.guiLeft = super.guiLeft + 315;
         scroll.guiTop = super.guiTop + 92;
         if(container != null) {
            scroll.setList(container.scripts);
         }

         this.addScroll(scroll);
      } else {
         this.addLabel(new GuiNpcLabel(0, "script.console", super.guiLeft + 4, super.guiTop + 16));
         this.getTopButton(14).active = true;
         this.addTextField(new GuiNpcTextArea(2, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 26, 226, 186, this.getConsoleText()));
         this.getTextField(2).canEdit = false;
         this.addButton(new GuiNpcButton(100, super.guiLeft + 232, super.guiTop + 170, 56, 20, "gui.copy"));
         this.addButton(new GuiNpcButton(102, super.guiLeft + 232, super.guiTop + 192, 56, 20, "gui.clear"));
         ArrayList l1 = new ArrayList();
         l1.add("All");
         l1.addAll(list);
         this.addButton(new GuiNpcButton(105, super.guiLeft + 60, super.guiTop + 4, 80, 20, (String[])l1.toArray(new String[l1.size()]), activeConsole));
         this.addLabel(new GuiNpcLabel(1, "script.language", super.guiLeft + 232, super.guiTop + 30));
         this.addButton(new GuiNpcButton(103, super.guiLeft + 294, super.guiTop + 25, 80, 20, (String[])this.languages.keySet().toArray(new String[this.languages.keySet().size()]), this.getScriptIndex()));
         this.getButton(103).enabled = this.languages.size() > 0;
         this.addLabel(new GuiNpcLabel(2, "gui.enabled", super.guiLeft + 232, super.guiTop + 53));
         this.addButton(new GuiNpcButton(104, super.guiLeft + 294, super.guiTop + 48, 50, 20, new String[]{"gui.no", "gui.yes"}, this.script.enabled?1:0));
         if(MinecraftServer.getServer() != null) {
            this.addButton(new GuiNpcButton(106, super.guiLeft + 232, super.guiTop + 71, 150, 20, "script.openfolder"));
         }
      }

   }

   private int getScriptIndex() {
      int i = 0;

      for(Iterator var2 = this.languages.keySet().iterator(); var2.hasNext(); ++i) {
         String language = (String)var2.next();
         if(language.equalsIgnoreCase(this.script.scriptLanguage)) {
            return i;
         }
      }

      return 0;
   }

   private String getConsoleText() {
      String console = "";
      if(activeConsole == 0) {
         Iterator container = this.script.scripts.values().iterator();

         while(container.hasNext()) {
            ScriptContainer container1 = (ScriptContainer)container.next();
            if(!container1.console.isEmpty()) {
               console = console + container1.console + '\n';
            }
         }
      } else {
         ScriptContainer container2 = (ScriptContainer)this.script.scripts.get(Integer.valueOf(activeConsole - 1));
         if(container2 != null) {
            console = container2.console;
         }
      }

      return console;
   }

   public void confirmClicked(boolean flag, int i) {
      if(flag) {
         this.openLink("http://www.kodevelopment.nl/minecraft/customnpcs/scripting");
      }

      this.displayGuiScreen(this);
   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 13) {
         this.showScript = true;
         this.initGui();
      }

      if(guibutton.id == 14) {
         this.setScript();
         this.showScript = false;
         this.initGui();
      }

      if(guibutton.id == 15) {
         GuiConfirmOpenLink container = new GuiConfirmOpenLink(this, "http://www.kodevelopment.nl/minecraft/customnpcs/scripting", 0, true);
         super.mc.displayGuiScreen(container);
      }

      if(guibutton.id == 100) {
         NoppesStringUtils.setClipboardContents(this.getTextField(2).getText());
      }

      if(guibutton.id == 101) {
         this.getTextField(2).setText(NoppesStringUtils.getClipboardContents());
      }

      ScriptContainer container3;
      if(guibutton.id == 102) {
         this.getTextField(2).setText("");
         if(!this.showScript) {
            ScriptContainer container1;
            if(activeConsole == 0) {
               for(Iterator container2 = this.script.scripts.values().iterator(); container2.hasNext(); container1.console = "") {
                  container1 = (ScriptContainer)container2.next();
               }
            } else {
               container3 = (ScriptContainer)this.script.scripts.get(Integer.valueOf(activeConsole - 1));
               if(container3 != null) {
                  container3.console = "";
               }
            }
         }
      }

      if(guibutton.id == 103) {
         this.script.scriptLanguage = ((GuiNpcButton)guibutton).displayString;
      }

      if(guibutton.id == 104) {
         this.script.enabled = ((GuiNpcButton)guibutton).getValue() == 1;
      }

      if(guibutton.id == 105) {
         activeConsole = ((GuiNpcButton)guibutton).getValue();
         this.initGui();
      }

      if(guibutton.id == 106) {
         NoppesUtil.openFolder(ScriptController.Instance.dir);
      }

      if(guibutton.id == 107) {
         container3 = (ScriptContainer)this.script.scripts.get(Integer.valueOf(this.activeTab));
         if(container3 == null) {
            this.script.scripts.put(Integer.valueOf(this.activeTab), container3 = new ScriptContainer());
         }

         this.setSubGui(new GuiScriptList((List)this.languages.get(this.script.scriptLanguage), container3));
      }

   }

   private void setScript() {
      if(this.showScript) {
         ScriptContainer container = (ScriptContainer)this.script.scripts.get(Integer.valueOf(this.activeTab));
         if(container == null) {
            this.script.scripts.put(Integer.valueOf(this.activeTab), container = new ScriptContainer());
         }

         String text = this.getTextField(2).getText();
         text = text.replace("\r\n", "\n");
         text = text.replace("\r", "\n");
         container.script = text;
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      this.script.readFromNBT(compound);
      NBTTagList data = compound.getTagList("Languages", 10);
      HashMap languages = new HashMap();

      for(int i = 0; i < data.tagCount(); ++i) {
         NBTTagCompound comp = data.getCompoundTagAt(i);
         ArrayList scripts = new ArrayList();
         NBTTagList list = comp.getTagList("Scripts", 8);

         for(int j = 0; j < list.tagCount(); ++j) {
            scripts.add(list.getStringTagAt(j));
         }

         languages.put(comp.getString("Language"), scripts);
      }

      this.languages = languages;
      this.initGui();
   }

   public void save() {
      this.setScript();
      Client.sendData(EnumPacketServer.ScriptDataSave, new Object[]{this.script.writeToNBT(new NBTTagCompound())});
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll scroll) {
      if(scroll.id == 1) {
         this.setScript();
         this.activeTab = scroll.selected;
         this.initGui();
      }

   }

}
