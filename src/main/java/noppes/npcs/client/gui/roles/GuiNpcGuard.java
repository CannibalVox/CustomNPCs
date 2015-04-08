package noppes.npcs.client.gui.roles;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobGuard;

public class GuiNpcGuard extends GuiNPCInterface2 {

   private JobGuard role;
   private GuiCustomScroll scroll1;
   private GuiCustomScroll scroll2;


   public GuiNpcGuard(EntityNPCInterface npc) {
      super(npc);
      this.role = (JobGuard)npc.jobInterface;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "guard.animals", super.guiLeft + 10, super.guiTop + 9));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 85, super.guiTop + 4, 50, 20, new String[]{"gui.no", "gui.yes"}, this.role.attacksAnimals?1:0));
      this.addLabel(new GuiNpcLabel(1, "guard.mobs", super.guiLeft + 140, super.guiTop + 9));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 222, super.guiTop + 4, 50, 20, new String[]{"gui.no", "gui.yes"}, this.role.attackHostileMobs?1:0));
      this.addLabel(new GuiNpcLabel(2, "guard.creepers", super.guiLeft + 275, super.guiTop + 9));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 360, super.guiTop + 4, 50, 20, new String[]{"gui.no", "gui.yes"}, this.role.attackCreepers?1:0));
      this.getButton(2).enabled = this.role.attackHostileMobs;
      this.addLabel(new GuiNpcLabel(3, "guard.specifictargets", super.guiLeft + 10, super.guiTop + 31));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 85, super.guiTop + 26, 50, 20, new String[]{"gui.no", "gui.yes"}, this.role.specific?1:0));
      if(this.role.specific) {
         if(this.scroll1 == null) {
            this.scroll1 = new GuiCustomScroll(this, 0);
            this.scroll1.setSize(175, 154);
         }

         this.scroll1.guiLeft = super.guiLeft + 4;
         this.scroll1.guiTop = super.guiTop + 58;
         this.addScroll(this.scroll1);
         this.addLabel(new GuiNpcLabel(11, "guard.availableTargets", super.guiLeft + 4, super.guiTop + 48));
         if(this.scroll2 == null) {
            this.scroll2 = new GuiCustomScroll(this, 1);
            this.scroll2.setSize(175, 154);
         }

         this.scroll2.guiLeft = super.guiLeft + 235;
         this.scroll2.guiTop = super.guiTop + 58;
         this.addScroll(this.scroll2);
         this.addLabel(new GuiNpcLabel(12, "guard.currentTargets", super.guiLeft + 235, super.guiTop + 48));
         ArrayList all = new ArrayList();
         Iterator var2 = EntityList.stringToClassMapping.keySet().iterator();

         while(var2.hasNext()) {
            Object entity = var2.next();
            String name = "entity." + entity + ".name";
            Class cl = (Class)EntityList.stringToClassMapping.get(entity);
            if(!this.role.targets.contains(name) && !EntityNPCInterface.class.isAssignableFrom(cl) && EntityLivingBase.class.isAssignableFrom(cl)) {
               all.add(name);
            }
         }

         this.scroll1.setList(all);
         this.scroll2.setList(this.role.targets);
         this.addButton(new GuiNpcButton(11, super.guiLeft + 180, super.guiTop + 80, 55, 20, ">"));
         this.addButton(new GuiNpcButton(12, super.guiLeft + 180, super.guiTop + 102, 55, 20, "<"));
         this.addButton(new GuiNpcButton(13, super.guiLeft + 180, super.guiTop + 130, 55, 20, ">>"));
         this.addButton(new GuiNpcButton(14, super.guiLeft + 180, super.guiTop + 152, 55, 20, "<<"));
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.role.attacksAnimals = button.getValue() == 1;
      }

      if(button.field_146127_k == 1) {
         this.role.attackHostileMobs = button.getValue() == 1;
         this.initGui();
      }

      if(button.field_146127_k == 2) {
         this.role.attackCreepers = button.getValue() == 1;
      }

      if(button.field_146127_k == 3) {
         this.role.specific = button.getValue() == 1;
         this.initGui();
      }

      if(button.field_146127_k == 11 && this.scroll1.hasSelected()) {
         this.role.targets.add(this.scroll1.getSelected());
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 12 && this.scroll2.hasSelected()) {
         this.role.targets.remove(this.scroll2.getSelected());
         this.scroll2.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 13) {
         this.role.targets.clear();
         ArrayList all = new ArrayList();
         Iterator var4 = EntityList.stringToClassMapping.keySet().iterator();

         while(var4.hasNext()) {
            Object entity = var4.next();
            String name = "entity." + entity + ".name";
            Class cl = (Class)EntityList.stringToClassMapping.get(entity);
            if(EntityLivingBase.class.isAssignableFrom(cl)) {
               all.add(name);
            }
         }

         this.role.targets = all;
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 14) {
         this.role.targets.clear();
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

   }

   public void save() {
      Client.sendData(EnumPacketServer.JobSave, new Object[]{this.role.writeToNBT(new NBTTagCompound())});
   }
}
