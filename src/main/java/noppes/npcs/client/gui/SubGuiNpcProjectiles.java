package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataStats;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.constants.EnumPotionType;

public class SubGuiNpcProjectiles extends SubGuiInterface implements ITextfieldListener {

   private DataStats stats;
   private String[] potionNames = new String[]{"gui.none", "tile.fire.name", "potion.poison", "potion.hunger", "potion.weakness", "potion.moveSlowdown", "potion.confusion", "potion.blindness", "potion.wither"};
   private String[] trailNames = new String[]{"gui.none", "Smoke", "Portal", "Redstone", "Lightning", "LargeSmoke", "Magic", "Enchant"};


   public SubGuiNpcProjectiles(DataStats stats) {
      this.stats = stats;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "enchantment.arrowDamage", super.guiLeft + 5, super.guiTop + 15));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 45, super.guiTop + 10, 50, 18, this.stats.pDamage + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(0, 9999, 5);
      this.addLabel(new GuiNpcLabel(2, "enchantment.arrowKnockback", super.guiLeft + 110, super.guiTop + 15));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 150, super.guiTop + 10, 50, 18, this.stats.pImpact + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(0, 3, 0);
      this.addLabel(new GuiNpcLabel(3, "stats.size", super.guiLeft + 5, super.guiTop + 45));
      this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 45, super.guiTop + 40, 50, 18, this.stats.pSize + ""));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(3).setMinMaxDefault(1, 10, 10);
      this.addLabel(new GuiNpcLabel(4, "stats.speed", super.guiLeft + 5, super.guiTop + 75));
      this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.guiLeft + 45, super.guiTop + 70, 50, 18, this.stats.pSpeed + ""));
      this.getTextField(4).numbersOnly = true;
      this.getTextField(4).setMinMaxDefault(1, 50, 10);
      this.addLabel(new GuiNpcLabel(5, "stats.hasgravity", super.guiLeft + 5, super.guiTop + 105));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 60, super.guiTop + 100, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pPhysics?1:0));
      if(!this.stats.pPhysics) {
         this.addButton(new GuiNpcButton(1, super.guiLeft + 140, super.guiTop + 100, 60, 20, new String[]{"gui.constant", "gui.accelerate"}, this.stats.pXlr8?1:0));
      }

      this.addLabel(new GuiNpcLabel(6, "stats.explosive", super.guiLeft + 5, super.guiTop + 135));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 60, super.guiTop + 130, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pExplode?1:0));
      if(this.stats.pExplode) {
         this.addButton(new GuiNpcButton(3, super.guiLeft + 140, super.guiTop + 130, 60, 20, new String[]{"gui.none", "gui.small", "gui.medium", "gui.large"}, this.stats.pArea));
      }

      this.addLabel(new GuiNpcLabel(7, "stats.rangedeffect", super.guiLeft + 5, super.guiTop + 165));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 60, super.guiTop + 160, 60, 20, this.potionNames, this.stats.pEffect.ordinal()));
      if(this.stats.pEffect != EnumPotionType.None) {
         this.addTextField(new GuiNpcTextField(5, this, super.fontRendererObj, super.guiLeft + 140, super.guiTop + 160, 60, 18, this.stats.pDur + ""));
         this.getTextField(5).numbersOnly = true;
         this.getTextField(5).setMinMaxDefault(1, 99999, 5);
         if(this.stats.pEffect != EnumPotionType.Fire) {
            this.addButton(new GuiNpcButton(10, super.guiLeft + 210, super.guiTop + 160, 40, 20, new String[]{"stats.regular", "stats.amplified"}, this.stats.pEffAmp));
         }
      }

      this.addLabel(new GuiNpcLabel(8, "stats.trail", super.guiLeft + 5, super.guiTop + 195));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 60, super.guiTop + 190, 60, 20, this.trailNames, this.stats.pTrail.ordinal()));
      this.addButton(new GuiNpcButton(7, super.guiLeft + 220, super.guiTop + 10, 30, 20, new String[]{"2D", "3D"}, this.stats.pRender3D?1:0));
      if(this.stats.pRender3D) {
         this.addLabel(new GuiNpcLabel(10, "stats.spin", super.guiLeft + 160, super.guiTop + 45));
         this.addButton(new GuiNpcButton(8, super.guiLeft + 220, super.guiTop + 40, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pSpin?1:0));
         this.addLabel(new GuiNpcLabel(11, "stats.stick", super.guiLeft + 160, super.guiTop + 75));
         this.addButton(new GuiNpcButton(9, super.guiLeft + 220, super.guiTop + 70, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pStick?1:0));
      }

      this.addButton(new GuiNpcButton(6, super.guiLeft + 140, super.guiTop + 190, 60, 20, new String[]{"stats.noglow", "stats.glows"}, this.stats.pGlows?1:0));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 210, super.guiTop + 190, 40, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 1) {
         this.stats.pDamage = textfield.getInteger();
      } else if(textfield.id == 2) {
         this.stats.pImpact = textfield.getInteger();
      } else if(textfield.id == 3) {
         this.stats.pSize = textfield.getInteger();
      } else if(textfield.id == 4) {
         this.stats.pSpeed = textfield.getInteger();
      } else if(textfield.id == 5) {
         this.stats.pDur = textfield.getInteger();
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.stats.pPhysics = button.getValue() == 1;
         this.initGui();
      }

      if(button.field_146127_k == 1) {
         this.stats.pXlr8 = button.getValue() == 1;
      }

      if(button.field_146127_k == 2) {
         this.stats.pExplode = button.getValue() == 1;
         this.initGui();
      }

      if(button.field_146127_k == 3) {
         this.stats.pArea = button.getValue();
      }

      if(button.field_146127_k == 4) {
         this.stats.pEffect = EnumPotionType.values()[button.getValue()];
         this.initGui();
      }

      if(button.field_146127_k == 5) {
         this.stats.pTrail = EnumParticleType.values()[button.getValue()];
      }

      if(button.field_146127_k == 6) {
         this.stats.pGlows = button.getValue() == 1;
      }

      if(button.field_146127_k == 7) {
         this.stats.pRender3D = button.getValue() == 1;
         this.initGui();
      }

      if(button.field_146127_k == 8) {
         this.stats.pSpin = button.getValue() == 1;
      }

      if(button.field_146127_k == 9) {
         this.stats.pStick = button.getValue() == 1;
      }

      if(button.field_146127_k == 10) {
         this.stats.pEffAmp = button.getValue();
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }
}
