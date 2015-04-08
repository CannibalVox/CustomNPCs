package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataAI;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumStandingType;

public class SubGuiNpcMovement extends SubGuiInterface implements ITextfieldListener {

   private DataAI ai;


   public SubGuiNpcMovement(DataAI ai) {
      this.ai = ai;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 4;
      this.addLabel(new GuiNpcLabel(0, "movement.type", super.guiLeft + 4, y + 5));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 80, y, 100, 20, EnumMovingType.names(), this.ai.movingType.ordinal()));
      GuiNpcButton var2;
      GuiNpcTextField var10001;
      int var10004;
      int var10005;
      if(this.ai.movingType == EnumMovingType.Wandering) {
         var10005 = super.guiLeft + 100;
         y += 22;
         var10001 = new GuiNpcTextField(4, this, var10005, y, 40, 20, this.ai.walkingRange + "");
         this.addTextField(var10001);
         this.getTextField(4).numbersOnly = true;
         this.getTextField(4).setMinMaxDefault(0, 1000, 5);
         this.addLabel(new GuiNpcLabel(4, "gui.range", super.guiLeft + 4, y + 5));
         var10004 = super.guiLeft + 100;
         y += 22;
         var2 = new GuiNpcButton(5, var10004, y, 50, 20, new String[]{"gui.no", "gui.yes"}, this.ai.npcInteracting?1:0);
         this.addButton(var2);
         this.addLabel(new GuiNpcLabel(5, "movement.wanderinteract", super.guiLeft + 4, y + 5));
      } else if(this.ai.movingType == EnumMovingType.Standing) {
         var10005 = super.guiLeft + 99;
         y += 22;
         var10001 = new GuiNpcTextField(7, this, var10005, y, 24, 20, (int)this.ai.bodyOffsetX + "");
         this.addTextField(var10001);
         this.addLabel(new GuiNpcLabel(17, "spawner.posoffset", super.guiLeft + 4, y + 5));
         this.addLabel(new GuiNpcLabel(7, "X:", super.guiLeft + 115, y + 5));
         this.getTextField(7).numbersOnly = true;
         this.getTextField(7).setMinMaxDefault(0, 10, 5);
         this.addLabel(new GuiNpcLabel(8, "Y:", super.guiLeft + 125, y + 5));
         this.addTextField(new GuiNpcTextField(8, this, super.guiLeft + 135, y, 24, 20, (int)this.ai.bodyOffsetY + ""));
         this.getTextField(8).numbersOnly = true;
         this.getTextField(8).setMinMaxDefault(0, 10, 5);
         this.addLabel(new GuiNpcLabel(9, "Z:", super.guiLeft + 161, y + 5));
         this.addTextField(new GuiNpcTextField(9, this, super.guiLeft + 171, y, 24, 20, (int)this.ai.bodyOffsetZ + ""));
         this.getTextField(9).numbersOnly = true;
         this.getTextField(9).setMinMaxDefault(0, 10, 5);
         var10004 = super.guiLeft + 80;
         y += 22;
         var2 = new GuiNpcButton(3, var10004, y, 100, 20, new String[]{"stats.normal", "movement.sitting", "movement.lying", "movement.sneaking", "movement.dancing", "movement.aiming", "movement.crawling", "movement.hug"}, this.ai.animationType.ordinal());
         this.addButton(var2);
         this.addLabel(new GuiNpcLabel(3, "movement.animation", super.guiLeft + 4, y + 5));
         if(this.ai.animationType != EnumAnimation.LYING) {
            var10004 = super.guiLeft + 80;
            y += 22;
            var2 = new GuiNpcButton(4, var10004, y, 80, 20, new String[]{"movement.body", "movement.manual", "movement.stalking", "movement.head"}, this.ai.standingType.ordinal());
            this.addButton(var2);
            this.addLabel(new GuiNpcLabel(1, "movement.rotation", super.guiLeft + 4, y + 5));
         } else {
            var10005 = super.guiLeft + 99;
            y += 22;
            var10001 = new GuiNpcTextField(5, this, var10005, y, 40, 20, this.ai.orientation + "");
            this.addTextField(var10001);
            this.getTextField(5).numbersOnly = true;
            this.getTextField(5).setMinMaxDefault(0, 359, 0);
            this.addLabel(new GuiNpcLabel(6, "movement.rotation", super.guiLeft + 4, y + 5));
            this.addLabel(new GuiNpcLabel(5, "(0-359)", super.guiLeft + 142, y + 5));
         }

         if(this.ai.standingType == EnumStandingType.NoRotation || this.ai.standingType == EnumStandingType.HeadRotation) {
            this.addTextField(new GuiNpcTextField(5, this, super.guiLeft + 165, y, 40, 20, this.ai.orientation + ""));
            this.getTextField(5).numbersOnly = true;
            this.getTextField(5).setMinMaxDefault(0, 359, 0);
            this.addLabel(new GuiNpcLabel(5, "(0-359)", super.guiLeft + 207, y + 5));
         }
      }

      if(this.ai.movingType != EnumMovingType.Standing) {
         var10004 = super.guiLeft + 80;
         y += 22;
         var2 = new GuiNpcButton(12, var10004, y, 100, 20, new String[]{"stats.normal", "movement.sneaking", "movement.aiming", "movement.dancing", "movement.crawling", "movement.hug"}, this.ai.animationType.getWalkingAnimation());
         this.addButton(var2);
         this.addLabel(new GuiNpcLabel(12, "movement.animation", super.guiLeft + 4, y + 5));
      }

      if(this.ai.movingType == EnumMovingType.MovingPath) {
         var10004 = super.guiLeft + 80;
         y += 22;
         var2 = new GuiNpcButton(8, var10004, y, 80, 20, new String[]{"ai.looping", "ai.backtracking"}, this.ai.movingPattern);
         this.addButton(var2);
         this.addLabel(new GuiNpcLabel(8, "movement.name", super.guiLeft + 4, y + 5));
         var10004 = super.guiLeft + 80;
         y += 22;
         var2 = new GuiNpcButton(9, var10004, y, 80, 20, new String[]{"gui.no", "gui.yes"}, this.ai.movingPause?1:0);
         this.addButton(var2);
         this.addLabel(new GuiNpcLabel(9, "movement.pauses", super.guiLeft + 4, y + 5));
      }

      var10004 = super.guiLeft + 100;
      y += 22;
      var2 = new GuiNpcButton(13, var10004, y, 50, 20, new String[]{"gui.no", "gui.yes"}, this.ai.stopAndInteract?1:0);
      this.addButton(var2);
      this.addLabel(new GuiNpcLabel(13, "movement.stopinteract", super.guiLeft + 4, y + 5));
      var10005 = super.guiLeft + 80;
      y += 22;
      var10001 = new GuiNpcTextField(14, this, var10005, y, 50, 18, this.ai.getWalkingSpeed() + "");
      this.addTextField(var10001);
      this.getTextField(14).numbersOnly = true;
      this.getTextField(14).setMinMaxDefault(0, 10, 4);
      this.addLabel(new GuiNpcLabel(14, "stats.walkspeed", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 190, super.guiTop + 190, 60, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.ai.movingType = EnumMovingType.values()[button.getValue()];
         if(this.ai.movingType != EnumMovingType.Standing) {
            this.ai.animationType = EnumAnimation.NONE;
            this.ai.standingType = EnumStandingType.RotateBody;
            this.ai.bodyOffsetX = this.ai.bodyOffsetY = this.ai.bodyOffsetZ = 5.0F;
         }

         this.initGui();
      } else if(button.field_146127_k == 3) {
         this.ai.animationType = EnumAnimation.values()[button.getValue()];
         this.initGui();
      } else if(button.field_146127_k == 4) {
         this.ai.standingType = EnumStandingType.values()[button.getValue()];
         this.initGui();
      } else if(button.field_146127_k == 5) {
         this.ai.npcInteracting = button.getValue() == 1;
      } else if(button.field_146127_k == 8) {
         this.ai.movingPattern = button.getValue();
      } else if(button.field_146127_k == 9) {
         this.ai.movingPause = button.getValue() == 1;
      } else if(button.field_146127_k == 12) {
         if(button.getValue() == 0) {
            this.ai.animationType = EnumAnimation.NONE;
         }

         if(button.getValue() == 1) {
            this.ai.animationType = EnumAnimation.SNEAKING;
         }

         if(button.getValue() == 2) {
            this.ai.animationType = EnumAnimation.AIMING;
         }

         if(button.getValue() == 3) {
            this.ai.animationType = EnumAnimation.DANCING;
         }

         if(button.getValue() == 4) {
            this.ai.animationType = EnumAnimation.CRAWLING;
         }

         if(button.getValue() == 5) {
            this.ai.animationType = EnumAnimation.HUG;
         }
      } else if(button.field_146127_k == 13) {
         this.ai.stopAndInteract = button.getValue() == 1;
      } else if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 7) {
         this.ai.bodyOffsetX = (float)textfield.getInteger();
      } else if(textfield.id == 8) {
         this.ai.bodyOffsetY = (float)textfield.getInteger();
      } else if(textfield.id == 9) {
         this.ai.bodyOffsetZ = (float)textfield.getInteger();
      } else if(textfield.id == 5) {
         this.ai.orientation = textfield.getInteger();
      } else if(textfield.id == 4) {
         this.ai.walkingRange = textfield.getInteger();
      } else if(textfield.id == 14) {
         this.ai.setWalkingSpeed(textfield.getInteger());
      }

   }
}
