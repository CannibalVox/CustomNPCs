package noppes.npcs.client.gui.model;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.gui.model.GuiModelColor;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.entity.EntityCustomNpc;

public class GuiModelArms extends GuiModelInterface {

   private final String[] arrParticles = new String[]{"gui.no", "Both", "Left", "Right"};
   private GuiScreen parent;


   public GuiModelArms(GuiScreen parent, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      super.xOffset = 60;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 20;
      ModelPartData claws = super.playerdata.getPartData("claws");
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(0, var10004, y, 70, 20, this.arrParticles, claws == null?0:claws.type + 1);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(0, "Claws", super.guiLeft, y + 5, 16777215));
      if(claws != null) {
         this.addButton(new GuiNpcButton(10, super.guiLeft + 122, y, 40, 20, claws.getColor()));
      }

   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      GuiNpcButton button = (GuiNpcButton)btn;
      if(button.field_146127_k == 0) {
         if(button.getValue() == 0) {
            super.playerdata.removePart("claws");
         } else {
            ModelPartData data = super.playerdata.getOrCreatePart("claws");
            data.type = (byte)(button.getValue() - 1);
         }

         this.initGui();
      }

      if(button.field_146127_k == 10) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("claws"), super.npc));
      }

   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }
}
