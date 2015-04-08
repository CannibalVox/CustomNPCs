package noppes.npcs.client.gui.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionTalents;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumCompanionStage;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;

public class GuiNpcCompanion extends GuiNPCInterface2 implements ITextfieldListener, ISliderListener {

   private RoleCompanion role;
   private List talents = new ArrayList();


   public GuiNpcCompanion(EntityNPCInterface npc) {
      super(npc);
      this.role = (RoleCompanion)npc.roleInterface;
   }

   public void initGui() {
      super.initGui();
      this.talents.clear();
      int y = super.guiTop + 4;
      this.addButton(new GuiNpcButton(0, super.guiLeft + 70, y, 90, 20, new String[]{EnumCompanionStage.BABY.name, EnumCompanionStage.CHILD.name, EnumCompanionStage.TEEN.name, EnumCompanionStage.ADULT.name, EnumCompanionStage.FULLGROWN.name}, this.role.stage.ordinal()));
      this.addLabel(new GuiNpcLabel(0, "companion.stage", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 162, y, 90, 20, "gui.update"));
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 70;
      y += 22;
      var10001 = new GuiNpcButton(2, var10004, y, 90, 20, new String[]{"gui.no", "gui.yes"}, this.role.canAge?1:0);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(2, "companion.age", super.guiLeft + 4, y + 5));
      if(this.role.canAge) {
         this.addTextField(new GuiNpcTextField(2, this, super.guiLeft + 162, y, 140, 20, this.role.ticksActive + ""));
         this.getTextField(2).numbersOnly = true;
         this.getTextField(2).setMinMaxDefault(0, Integer.MAX_VALUE, 0);
      }

      List var10000 = this.talents;
      GuiNpcCompanionTalents.GuiTalent var2;
      int var10005 = super.guiLeft + 4;
      y += 26;
      var2 = new GuiNpcCompanionTalents.GuiTalent(this.role, EnumCompanionTalent.INVENTORY, var10005, y);
      var10000.add(var2);
      this.addSlider(new GuiNpcSlider(this, 10, super.guiLeft + 30, y + 2, 100, 20, (float)this.role.getExp(EnumCompanionTalent.INVENTORY) / 5000.0F));
      var10000 = this.talents;
      var10005 = super.guiLeft + 4;
      y += 26;
      var2 = new GuiNpcCompanionTalents.GuiTalent(this.role, EnumCompanionTalent.ARMOR, var10005, y);
      var10000.add(var2);
      this.addSlider(new GuiNpcSlider(this, 11, super.guiLeft + 30, y + 2, 100, 20, (float)this.role.getExp(EnumCompanionTalent.ARMOR) / 5000.0F));
      var10000 = this.talents;
      var10005 = super.guiLeft + 4;
      y += 26;
      var2 = new GuiNpcCompanionTalents.GuiTalent(this.role, EnumCompanionTalent.SWORD, var10005, y);
      var10000.add(var2);
      this.addSlider(new GuiNpcSlider(this, 12, super.guiLeft + 30, y + 2, 100, 20, (float)this.role.getExp(EnumCompanionTalent.SWORD) / 5000.0F));
   }

   public void buttonEvent(GuiButton guibutton) {
      GuiNpcButton button;
      if(guibutton.id == 0) {
         button = (GuiNpcButton)guibutton;
         this.role.matureTo(EnumCompanionStage.values()[button.getValue()]);
         if(this.role.canAge) {
            this.role.ticksActive = (long)this.role.stage.matureAge;
         }

         this.initGui();
      }

      if(guibutton.id == 1) {
         Client.sendData(EnumPacketServer.RoleCompanionUpdate, new Object[]{Integer.valueOf(this.role.stage.ordinal())});
      }

      if(guibutton.id == 2) {
         button = (GuiNpcButton)guibutton;
         this.role.canAge = button.getValue() == 1;
         this.initGui();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 2) {
         this.role.ticksActive = (long)textfield.getInteger();
      }

   }

   public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);
      Iterator var4 = this.talents.iterator();

      while(var4.hasNext()) {
         GuiNpcCompanionTalents.GuiTalent talent = (GuiNpcCompanionTalents.GuiTalent)var4.next();
         talent.drawScreen(i, j, f);
      }

   }

   public void elementClicked() {}

   public void save() {
      Client.sendData(EnumPacketServer.RoleSave, new Object[]{this.role.writeToNBT(new NBTTagCompound())});
   }

   public void mouseDragged(GuiNpcSlider slider) {
      if(slider.sliderValue <= 0.0F) {
         slider.setString("gui.disabled");
         this.role.talents.remove(EnumCompanionTalent.values()[slider.field_146127_k - 10]);
      } else {
         slider.displayString = (int)(slider.sliderValue * 50.0F) * 100 + " exp";
         this.role.setExp(EnumCompanionTalent.values()[slider.field_146127_k - 10], (int)(slider.sliderValue * 50.0F) * 100);
      }

   }

   public void mousePressed(GuiNpcSlider slider) {}

   public void mouseReleased(GuiNpcSlider slider) {}
}
