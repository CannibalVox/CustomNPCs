package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.ScriptContainer;

public class GuiScriptList extends SubGuiInterface {

   private GuiCustomScroll scroll1;
   private GuiCustomScroll scroll2;
   private ScriptContainer container;
   private List scripts;


   public GuiScriptList(List scripts, ScriptContainer container) {
      this.container = container;
      this.setBackground("menubg.png");
      super.xSize = 346;
      super.ySize = 216;
      if(scripts == null) {
         scripts = new ArrayList();
      }

      this.scripts = (List)scripts;
   }

   public void initGui() {
      super.initGui();
      if(this.scroll1 == null) {
         this.scroll1 = new GuiCustomScroll(this, 0);
         this.scroll1.setSize(140, 180);
      }

      this.scroll1.guiLeft = super.guiLeft + 4;
      this.scroll1.guiTop = super.guiTop + 14;
      this.addScroll(this.scroll1);
      this.addLabel(new GuiNpcLabel(1, "script.availableScripts", super.guiLeft + 4, super.guiTop + 4));
      if(this.scroll2 == null) {
         this.scroll2 = new GuiCustomScroll(this, 1);
         this.scroll2.setSize(140, 180);
      }

      this.scroll2.guiLeft = super.guiLeft + 200;
      this.scroll2.guiTop = super.guiTop + 14;
      this.addScroll(this.scroll2);
      this.addLabel(new GuiNpcLabel(2, "script.loadedScripts", super.guiLeft + 200, super.guiTop + 4));
      ArrayList temp = new ArrayList(this.scripts);
      temp.removeAll(this.container.scripts);
      this.scroll1.setList(temp);
      this.scroll2.setList(this.container.scripts);
      this.addButton(new GuiNpcButton(1, super.guiLeft + 145, super.guiTop + 40, 55, 20, ">"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 145, super.guiTop + 62, 55, 20, "<"));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 145, super.guiTop + 90, 55, 20, ">>"));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 145, super.guiTop + 112, 55, 20, "<<"));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 260, super.guiTop + 194, 60, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 1 && this.scroll1.hasSelected()) {
         this.container.scripts.add(this.scroll1.getSelected());
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 2 && this.scroll2.hasSelected()) {
         this.container.scripts.remove(this.scroll2.getSelected());
         this.scroll2.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 3) {
         this.container.scripts.clear();
         Iterator var3 = this.scripts.iterator();

         while(var3.hasNext()) {
            String script = (String)var3.next();
            this.container.scripts.add(script);
         }

         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 4) {
         this.container.scripts.clear();
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void save() {}
}
