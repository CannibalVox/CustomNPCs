package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataAI;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiNpcMovement;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumNavType;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcAI extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {

   private DataAI ai;


   public GuiNpcAI(EntityNPCInterface npc) {
      super(npc, 6);
      this.ai = npc.ai;
      Client.sendData(EnumPacketServer.MainmenuAIGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "ai.enemyresponse", super.guiLeft + 5, super.guiTop + 17));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 86, super.guiTop + 10, 60, 20, new String[]{"gui.retaliate", "gui.panic", "gui.retreat", "gui.nothing"}, super.npc.ai.onAttack));
      this.addLabel(new GuiNpcLabel(1, "ai.door", super.guiLeft + 5, super.guiTop + 40));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 86, super.guiTop + 35, 60, 20, new String[]{"gui.break", "gui.open", "gui.disabled"}, super.npc.ai.doorInteract));
      this.addLabel(new GuiNpcLabel(12, "ai.swim", super.guiLeft + 5, super.guiTop + 65));
      this.addButton(new GuiNpcButton(7, super.guiLeft + 86, super.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.ai.canSwim?1:0));
      this.addLabel(new GuiNpcLabel(13, "ai.shelter", super.guiLeft + 5, super.guiTop + 90));
      this.addButton(new GuiNpcButton(9, super.guiLeft + 86, super.guiTop + 85, 60, 20, new String[]{"gui.darkness", "gui.sunlight", "gui.disabled"}, super.npc.ai.findShelter));
      this.addLabel(new GuiNpcLabel(14, "ai.clearlos", super.guiLeft + 5, super.guiTop + 115));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 86, super.guiTop + 110, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.ai.directLOS?1:0));
      this.addLabel(new GuiNpcLabel(18, "ai.sprint", super.guiLeft + 5, super.guiTop + 140));
      this.addButton(new GuiNpcButton(16, super.guiLeft + 86, super.guiTop + 135, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.ai.canSprint?1:0));
      this.addLabel(new GuiNpcLabel(10, "ai.avoidwater", super.guiLeft + 150, super.guiTop + 17));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 230, super.guiTop + 10, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.getNavigator().getAvoidsWater()?1:0));
      this.addLabel(new GuiNpcLabel(11, "ai.return", super.guiLeft + 150, super.guiTop + 40));
      this.addButton(new GuiNpcButton(6, super.guiLeft + 230, super.guiTop + 35, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.ai.returnToStart?1:0));
      this.addLabel(new GuiNpcLabel(17, "ai.leapattarget", super.guiLeft + 150, super.guiTop + 65));
      this.addButton(new GuiNpcButton(15, super.guiLeft + 230, super.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.ai.canLeap?1:0));
      this.addLabel(new GuiNpcLabel(15, "ai.indirect", super.guiLeft + 150, super.guiTop + 90));
      this.addButton(new GuiNpcButton(13, super.guiLeft + 230, super.guiTop + 85, 60, 20, new String[]{"gui.no", "gui.whendistant", "gui.whenhidden"}, this.ai.canFireIndirect));
      this.addLabel(new GuiNpcLabel(16, "ai.rangemelee", super.guiLeft + 150, super.guiTop + 115));
      this.addButton(new GuiNpcButton(14, super.guiLeft + 230, super.guiTop + 110, 60, 20, new String[]{super.npc.inventory.getProjectile() == null?"gui.no":"gui.always", "gui.untilclose", "gui.whenavailable"}, this.ai.useRangeMelee));
      if(this.ai.useRangeMelee >= 1) {
         this.addLabel(new GuiNpcLabel(20, "gui.minrange", super.guiLeft + 300, super.guiTop + 115));
         this.addTextField(new GuiNpcTextField(6, this, super.fontRendererObj, super.guiLeft + 380, super.guiTop + 110, 30, 20, this.ai.distanceToMelee + ""));
         this.getTextField(6).numbersOnly = true;
         this.getTextField(6).setMinMaxDefault(1, super.npc.stats.aggroRange, 5);
      }

      this.addLabel(new GuiNpcLabel(19, "ai.tacticalvariant", super.guiLeft + 150, super.guiTop + 140));
      this.addButton(new GuiNpcButton(17, super.guiLeft + 230, super.guiTop + 135, 60, 20, EnumNavType.names(), this.ai.tacticalVariant.ordinal()));
      if(this.ai.tacticalVariant != EnumNavType.Default && this.ai.tacticalVariant != EnumNavType.None) {
         String label = "";
         switch(this.ai.tacticalVariant) {
         case Surround:
            label = "gui.orbitdistance";
            break;
         case HitNRun:
            label = "gui.fightifthisclose";
            break;
         case Ambush:
            label = "gui.ambushdistance";
            break;
         case Stalk:
            label = "gui.ambushdistance";
            break;
         default:
            label = "gui.engagedistance";
         }

         this.addLabel(new GuiNpcLabel(21, label, super.guiLeft + 300, super.guiTop + 140));
         this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 380, super.guiTop + 135, 30, 20, this.ai.tacticalRadius + ""));
         this.getTextField(3).numbersOnly = true;
         this.getTextField(3).setMinMaxDefault(1, super.npc.stats.aggroRange, 5);
      }

      this.addLabel(new GuiNpcLabel(22, "ai.cobwebAffected", super.guiLeft + 150, super.guiTop + 165));
      this.addButton(new GuiNpcButton(22, super.guiLeft + 230, super.guiTop + 160, 60, 20, new String[]{"gui.no", "gui.yes"}, super.npc.ai.ignoreCobweb?0:1));
      this.getButton(17).setEnabled(this.ai.onAttack == 0);
      this.getButton(15).setEnabled(this.ai.onAttack == 0);
      this.getButton(13).setEnabled(super.npc.inventory.getProjectile() != null);
      this.getButton(14).setEnabled(super.npc.inventory.getProjectile() != null);
      this.getButton(10).setEnabled(this.ai.tacticalVariant != EnumNavType.Stalk || this.ai.tacticalVariant != EnumNavType.None);
      this.addLabel(new GuiNpcLabel(2, "ai.movement", super.guiLeft + 4, super.guiTop + 165));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 86, super.guiTop + 160, 60, 20, "selectServer.edit"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 3) {
         this.ai.tacticalRadius = textfield.getInteger();
      }

      if(textfield.id == 6) {
         this.ai.distanceToMelee = textfield.getInteger();
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.ai.onAttack = button.getValue();
         this.initGui();
      } else if(button.field_146127_k == 1) {
         this.ai.doorInteract = button.getValue();
      } else if(button.field_146127_k == 2) {
         this.setSubGui(new SubGuiNpcMovement(this.ai));
      } else if(button.field_146127_k == 5) {
         super.npc.setAvoidWater(button.getValue() == 1);
      } else if(button.field_146127_k == 6) {
         this.ai.returnToStart = button.getValue() == 1;
      } else if(button.field_146127_k == 7) {
         this.ai.canSwim = button.getValue() == 1;
      } else if(button.field_146127_k == 9) {
         this.ai.findShelter = button.getValue();
      } else if(button.field_146127_k == 10) {
         this.ai.directLOS = button.getValue() == 1;
      } else if(button.field_146127_k == 13) {
         this.ai.canFireIndirect = button.getValue();
      } else if(button.field_146127_k == 14) {
         this.ai.useRangeMelee = button.getValue();
         this.initGui();
      } else if(button.field_146127_k == 15) {
         this.ai.canLeap = button.getValue() == 1;
      } else if(button.field_146127_k == 16) {
         this.ai.canSprint = button.getValue() == 1;
      } else if(button.field_146127_k == 17) {
         this.ai.tacticalVariant = EnumNavType.values()[button.getValue()];
         this.ai.directLOS = EnumNavType.values()[button.getValue()] == EnumNavType.Stalk?false:this.ai.directLOS;
         this.initGui();
      } else if(button.field_146127_k == 22) {
         this.ai.ignoreCobweb = button.getValue() == 0;
      }

   }

   public void save() {
      Client.sendData(EnumPacketServer.MainmenuAISave, new Object[]{this.ai.writeToNBT(new NBTTagCompound())});
   }

   public void setGuiData(NBTTagCompound compound) {
      this.ai.readToNBT(compound);
      this.initGui();
   }
}
