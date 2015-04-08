package noppes.npcs.client.gui.questtypes;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.quests.QuestKill;

public class GuiNpcQuestTypeKill extends SubGuiInterface implements ITextfieldListener, ICustomScrollListener {

   private GuiScreen parent;
   private GuiCustomScroll scroll;
   private QuestKill quest;
   private GuiNpcTextField lastSelected;


   public GuiNpcQuestTypeKill(EntityNPCInterface npc, Quest q, GuiScreen parent) {
      super.npc = npc;
      this.parent = parent;
      super.title = "Quest Kill Setup";
      this.quest = (QuestKill)q.questInterface;
      this.setBackground("menubg.png");
      super.xSize = 356;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      int i = 0;
      this.addLabel(new GuiNpcLabel(0, "You can fill in npc or player names too", super.guiLeft + 4, super.guiTop + 50));

      for(Iterator data = this.quest.targets.keySet().iterator(); data.hasNext(); ++i) {
         String list = (String)data.next();
         this.addTextField(new GuiNpcTextField(i, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 70 + i * 22, 180, 20, list));
         this.addTextField(new GuiNpcTextField(i + 3, this, super.fontRendererObj, super.guiLeft + 186, super.guiTop + 70 + i * 22, 24, 20, this.quest.targets.get(list) + ""));
         this.getTextField(i + 3).numbersOnly = true;
         this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
      }

      while(i < 3) {
         this.addTextField(new GuiNpcTextField(i, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 70 + i * 22, 180, 20, ""));
         this.addTextField(new GuiNpcTextField(i + 3, this, super.fontRendererObj, super.guiLeft + 186, super.guiTop + 70 + i * 22, 24, 20, "1"));
         this.getTextField(i + 3).numbersOnly = true;
         this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
         ++i;
      }

      Map var10 = EntityList.stringToClassMapping;
      ArrayList var11 = new ArrayList();
      Iterator var4 = var10.keySet().iterator();

      while(var4.hasNext()) {
         Object name = var4.next();
         Class c = (Class)var10.get(name);

         try {
            if(EntityLivingBase.class.isAssignableFrom(c) && !EntityNPCInterface.class.isAssignableFrom(c) && c.getConstructor(new Class[]{World.class}) != null && !Modifier.isAbstract(c.getModifiers())) {
               var11.add(name.toString());
            }
         } catch (SecurityException var8) {
            var8.printStackTrace();
         } catch (NoSuchMethodException var9) {
            ;
         }
      }

      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
      }

      this.scroll.setList(var11);
      this.scroll.setSize(130, 198);
      this.scroll.guiLeft = super.guiLeft + 220;
      this.scroll.guiTop = super.guiTop + 14;
      this.addScroll(this.scroll);
      this.addButton(new GuiNpcButton(0, super.guiLeft + 4, super.guiTop + 140, 98, 20, "gui.back"));
      this.scroll.visible = GuiNpcTextField.isActive();
   }

   protected void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      if(guibutton.id == 0) {
         this.close();
      }

   }

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      this.scroll.visible = GuiNpcTextField.isActive();
   }

   public void save() {}

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      if(guiNpcTextField.id < 3) {
         this.lastSelected = guiNpcTextField;
      }

      this.saveTargets();
   }

   private void saveTargets() {
      HashMap map = new HashMap();

      for(int i = 0; i < 3; ++i) {
         String name = this.getTextField(i).getText();
         if(!name.isEmpty()) {
            map.put(name, Integer.valueOf(this.getTextField(i + 3).getInteger()));
         }
      }

      this.quest.targets = map;
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if(this.lastSelected != null) {
         this.lastSelected.setText(guiCustomScroll.getSelected());
         this.saveTargets();
      }
   }
}
