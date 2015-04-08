package noppes.npcs.client.gui.model;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.gui.model.GuiModelColor;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.entity.EntityCustomNpc;

public class GuiModelHead extends GuiModelInterface {

   private GuiScreen parent;
   private final String[] arrHeadwear = new String[]{"gui.no", "gui.yes", "Solid"};
   private final String[] arrHair = new String[]{"gui.no", "Player", "Long", "Thin", "Stylish", "Ponytail"};
   private final String[] arrBeard = new String[]{"gui.no", "Player", "Standard", "Viking", "Long", "Short"};
   private final String[] arrMohawk = new String[]{"gui.no", "Type1"};
   private final String[] arrSnout = new String[]{"gui.no", "Player Small", "Player Medium", "Player Large", "Player Bunny", "Small1", "Medium1", "Large1", "Bunny1"};
   private final String[] arrEars = new String[]{"gui.no", "Player", "Player Bunny", "Bunny", "Type1"};


   public GuiModelHead(GuiScreen parent, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      super.xOffset = 60;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 20;
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(0, var10004, y, 70, 20, this.arrHeadwear, super.playerdata.headwear);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(0, "Headwear", super.guiLeft, y + 5, 16777215));
      ModelPartData hair = super.playerdata.getPartData("hair");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(1, var10004, y, 70, 20, this.arrHair, hair == null?0:hair.type + 1);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(1, "Hair", super.guiLeft, y + 5, 16777215));
      if(hair != null) {
         this.addButton(new GuiNpcButton(11, super.guiLeft + 122, y, 40, 20, hair.getColor()));
      }

      ModelPartData mohawk = super.playerdata.getPartData("mohawk");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(2, var10004, y, 70, 20, this.arrMohawk, mohawk == null?0:mohawk.type);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(2, "Mohawk", super.guiLeft, y + 5, 16777215));
      if(mohawk != null) {
         this.addButton(new GuiNpcButton(12, super.guiLeft + 122, y, 40, 20, mohawk.getColor()));
      }

      ModelPartData beard = super.playerdata.getPartData("beard");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(3, var10004, y, 70, 20, this.arrBeard, beard == null?0:beard.type + 1);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(3, "Beard", super.guiLeft, y + 5, 16777215));
      if(beard != null) {
         this.addButton(new GuiNpcButton(13, super.guiLeft + 122, y, 40, 20, beard.getColor()));
      }

      ModelPartData snout = super.playerdata.getPartData("snout");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(4, var10004, y, 70, 20, this.arrSnout, snout == null?0:snout.type + (snout.playerTexture?1:5));
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(4, "Snout", super.guiLeft, y + 5, 16777215));
      if(snout != null) {
         this.addButton(new GuiNpcButton(14, super.guiLeft + 122, y, 40, 20, snout.getColor()));
      }

      ModelPartData ears = super.playerdata.getPartData("ears");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(5, var10004, y, 70, 20, this.arrEars, this.getEars(ears));
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(5, "Ears", super.guiLeft, y + 5, 16777215));
      if(ears != null) {
         this.addButton(new GuiNpcButton(15, super.guiLeft + 122, y, 40, 20, ears.getColor()));
      }

   }

   private int getEars(ModelPartData data) {
      return data == null?0:(data.playerTexture && data.type == 0?1:(data.playerTexture && data.type == 1?2:(data.type == 0?4:(data.type == 1?3:0))));
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      GuiNpcButton button = (GuiNpcButton)btn;
      if(button.field_146127_k == 0) {
         super.playerdata.headwear = (byte)button.getValue();
      }

      ModelPartData value;
      if(button.field_146127_k == 1) {
         if(button.getValue() == 0) {
            super.playerdata.removePart("hair");
         } else {
            value = super.playerdata.getOrCreatePart("hair");
            if(button.getValue() > 1) {
               value.setTexture("hair/hair" + (button.getValue() - 1), button.getValue() - 1);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 2) {
         if(button.getValue() == 0) {
            super.playerdata.removePart("mohawk");
         } else {
            value = super.playerdata.getOrCreatePart("mohawk");
            if(button.getValue() > 0) {
               value.setTexture("hair/mohawk" + button.getValue(), button.getValue());
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 3) {
         if(button.getValue() == 0) {
            super.playerdata.removePart("beard");
         } else {
            value = super.playerdata.getOrCreatePart("beard");
            if(button.getValue() > 1) {
               value.setTexture("beard/beard" + (button.getValue() - 1), button.getValue() - 1);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 4) {
         if(button.getValue() == 0) {
            super.playerdata.removePart("snout");
         } else if(button.getValue() < 5) {
            value = super.playerdata.getOrCreatePart("snout");
            value.type = (byte)(button.getValue() - 1);
         } else {
            value = super.playerdata.getOrCreatePart("snout");
            byte data = 0;
            if(button.displayString.startsWith("Medium")) {
               data = 1;
            }

            if(button.displayString.startsWith("Large")) {
               data = 2;
            }

            if(button.displayString.startsWith("Bunny")) {
               data = 3;
            }

            value.setTexture("snout/" + button.displayString.toLowerCase(), data);
         }

         this.initGui();
      }

      if(button.field_146127_k == 5) {
         int value1 = button.getValue();
         if(value1 == 0) {
            super.playerdata.removePart("ears");
         } else {
            ModelPartData data1 = super.playerdata.getOrCreatePart("ears");
            if(value1 == 1) {
               data1.setTexture("", 0);
            }

            if(value1 == 2) {
               data1.setTexture("", 1);
            }

            if(value1 == 3) {
               data1.setTexture("ears/bunny1", 1);
            }

            if(value1 == 4) {
               data1.setTexture("ears/type1", 0);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 11) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("hair"), super.npc));
      }

      if(button.field_146127_k == 12) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("mohawk"), super.npc));
      }

      if(button.field_146127_k == 13) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("beard"), super.npc));
      }

      if(button.field_146127_k == 14) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("snout"), super.npc));
      }

      if(button.field_146127_k == 15) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("ears"), super.npc));
      }

   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }
}
