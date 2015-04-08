package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataStats;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPotionType;

public class SubGuiNpcMeleeProperties extends SubGuiInterface implements ITextfieldListener {

   private DataStats stats;
   private String[] potionNames = new String[]{"gui.none", "tile.fire.name", "potion.poison", "potion.hunger", "potion.weakness", "potion.moveSlowdown", "potion.confusion", "potion.blindness", "potion.wither"};


   public SubGuiNpcMeleeProperties(DataStats stats) {
      this.stats = stats;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "stats.meleestrength", super.guiLeft + 5, super.guiTop + 15));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 85, super.guiTop + 10, 50, 18, this.stats.getAttackStrength() + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(0, 99999, 5);
      this.addLabel(new GuiNpcLabel(2, "stats.meleerange", super.guiLeft + 5, super.guiTop + 45));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 85, super.guiTop + 40, 50, 18, this.stats.attackRange + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(1, 30, 2);
      this.addLabel(new GuiNpcLabel(3, "stats.meleespeed", super.guiLeft + 5, super.guiTop + 75));
      this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 85, super.guiTop + 70, 50, 18, this.stats.attackSpeed + ""));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(3).setMinMaxDefault(1, 1000, 20);
      this.addLabel(new GuiNpcLabel(4, "enchantment.knockback", super.guiLeft + 5, super.guiTop + 105));
      this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.guiLeft + 85, super.guiTop + 100, 50, 18, this.stats.knockback + ""));
      this.getTextField(4).numbersOnly = true;
      this.getTextField(4).setMinMaxDefault(0, 4, 0);
      this.addLabel(new GuiNpcLabel(5, "stats.meleeeffect", super.guiLeft + 5, super.guiTop + 135));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 85, super.guiTop + 130, 52, 20, this.potionNames, this.stats.potionType.ordinal()));
      if(this.stats.potionType != EnumPotionType.None) {
         this.addLabel(new GuiNpcLabel(6, "gui.time", super.guiLeft + 5, super.guiTop + 165));
         this.addTextField(new GuiNpcTextField(6, this, super.fontRendererObj, super.guiLeft + 85, super.guiTop + 160, 50, 18, this.stats.potionDuration + ""));
         this.getTextField(6).numbersOnly = true;
         this.getTextField(6).setMinMaxDefault(1, 99999, 5);
         if(this.stats.potionType != EnumPotionType.Fire) {
            this.addLabel(new GuiNpcLabel(7, "stats.amplify", super.guiLeft + 5, super.guiTop + 195));
            this.addButton(new GuiNpcButton(7, super.guiLeft + 85, super.guiTop + 190, 52, 20, new String[]{"gui.no", "gui.yes"}, this.stats.potionAmp));
         }
      }

      this.addButton(new GuiNpcButton(66, super.guiLeft + 165, super.guiTop + 192, 90, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 1) {
         this.stats.setAttackStrength(textfield.getInteger());
      } else if(textfield.id == 2) {
         this.stats.attackRange = textfield.getInteger();
      } else if(textfield.id == 3) {
         this.stats.attackSpeed = textfield.getInteger();
      } else if(textfield.id == 4) {
         this.stats.knockback = textfield.getInteger();
      } else if(textfield.id == 6) {
         this.stats.potionDuration = textfield.getInteger();
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 5) {
         this.stats.potionType = EnumPotionType.values()[button.getValue()];
         this.initGui();
      }

      if(button.field_146127_k == 7) {
         this.stats.potionAmp = button.getValue();
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }
}
