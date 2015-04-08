package noppes.npcs.client.gui.model;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.gui.model.GuiModelColor;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.entity.EntityCustomNpc;

public class GuiModelLegs extends GuiModelInterface {

   private GuiScreen parent;
   private final String[] arrLegs = new String[]{"gui.no", "Player", "Player Naga", "Spider", "Horse", "Naga", "Mermaid", "Digitigrade"};
   private final String[] arrTail = new String[]{"gui.no", "Player", "Player Dragon", "Cat", "Wolf", "Horse", "Dragon", "Squirrel"};


   public GuiModelLegs(GuiScreen parent, EntityCustomNpc npc) {
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
      var10001 = new GuiNpcButton(1, var10004, y, 70, 20, this.arrLegs, this.getNagaIndex(super.playerdata.legParts));
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(1, "Legs", super.guiLeft, y + 5, 16777215));
      if(super.playerdata.legParts.type > 0) {
         this.addButton(new GuiNpcButton(11, super.guiLeft + 122, y, 40, 20, super.playerdata.legParts.getColor()));
      }

      ModelPartData tail = super.playerdata.getPartData("tail");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(2, var10004, y, 70, 20, this.arrTail, this.getTailIndex(tail));
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(2, "Tail", super.guiLeft, y + 5, 16777215));
      if(tail != null) {
         this.addButton(new GuiNpcButton(12, super.guiLeft + 122, y, 40, 20, tail.getColor()));
      }

   }

   private int getNagaIndex(ModelPartData data) {
      return !data.playerTexture && data.type == 1?5:(data.type == 4?6:(data.type == 5?7:data.type + 1));
   }

   private int getTailIndex(ModelPartData data) {
      return data == null?0:(data.playerTexture && data.type == 0?1:(data.type == 0 && data.texture.contains("tail1")?3:(data.type == 0 && data.texture.contains("tail2")?4:(data.playerTexture && data.type == 1?2:(data.type == 1?6:(data.type == 2?5:(data.type == 3?7:0)))))));
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      GuiNpcButton button = (GuiNpcButton)btn;
      if(button.field_146127_k == 1) {
         ModelPartData value = super.playerdata.legParts;
         int data = button.getValue() - 1;
         if(data < 1) {
            value.color = 16777215;
         }

         if(data < 2) {
            value.setTexture("", data);
         }

         if(data == 2) {
            value.setTexture("legs/spider1", 2);
         }

         if(data == 3) {
            value.setTexture("legs/horse1", 3);
         }

         if(data == 4) {
            value.setTexture("legs/naga1", 1);
         }

         if(data == 5) {
            value.setTexture("legs/mermaid1", 4);
         }

         if(data == 6) {
            value.setTexture("", 5);
         }

         this.initGui();
      }

      if(button.field_146127_k == 2) {
         int value1 = button.getValue();
         if(value1 == 0) {
            super.playerdata.removePart("tail");
         } else {
            ModelPartData data1 = super.playerdata.getOrCreatePart("tail");
            if(value1 == 1) {
               data1.setTexture("", 0);
            }

            if(value1 == 2) {
               data1.setTexture("", 1);
            }

            if(value1 == 3) {
               data1.setTexture("tail/tail1", 0);
            }

            if(value1 == 4) {
               data1.setTexture("tail/tail2", 0);
            }

            if(value1 == 5) {
               data1.setTexture("tail/horse1", 2);
            }

            if(value1 == 6) {
               data1.setTexture("tail/dragon1", 1);
            }

            if(value1 == 7) {
               data1.setTexture("tail/squirrel1", 3);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 11) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.legParts, super.npc));
      }

      if(button.field_146127_k == 12) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("tail"), super.npc));
      }

   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }
}
