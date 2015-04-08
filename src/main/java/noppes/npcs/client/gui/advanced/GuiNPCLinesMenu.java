package noppes.npcs.client.gui.advanced;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCLinesEdit;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCLinesMenu extends GuiNPCInterface2 {

   public GuiNPCLinesMenu(EntityNPCInterface npc) {
      super(npc);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(0, super.guiLeft + 85, super.guiTop + 20, "World Lines"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 85, super.guiTop + 43, "Attack Lines"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 85, super.guiTop + 66, "Interact Lines"));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 85, super.guiTop + 89, "Killed Lines"));
      this.addButton(new GuiNpcButton(6, super.guiLeft + 85, super.guiTop + 112, "Kill Lines"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         NoppesUtil.openGUI(super.player, new GuiNPCLinesEdit(super.npc, super.npc.advanced.worldLines));
      }

      if(id == 1) {
         NoppesUtil.openGUI(super.player, new GuiNPCLinesEdit(super.npc, super.npc.advanced.attackLines));
      }

      if(id == 2) {
         NoppesUtil.openGUI(super.player, new GuiNPCLinesEdit(super.npc, super.npc.advanced.interactLines));
      }

      if(id == 5) {
         NoppesUtil.openGUI(super.player, new GuiNPCLinesEdit(super.npc, super.npc.advanced.killedLines));
      }

      if(id == 6) {
         NoppesUtil.openGUI(super.player, new GuiNPCLinesEdit(super.npc, super.npc.advanced.killLines));
      }

   }

   public void save() {}
}
