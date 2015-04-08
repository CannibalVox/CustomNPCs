package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNpcManagePlayerData;
import noppes.npcs.client.gui.global.GuiNpcNaturalSpawns;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCGlobalMainMenu extends GuiNPCInterface2 {

   public GuiNPCGlobalMainMenu(EntityNPCInterface npc) {
      super(npc, 5);
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 10;
      this.addButton(new GuiNpcButton(2, super.guiLeft + 85, y, "global.banks"));
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(3, var10004, y, "menu.factions");
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(4, var10004, y, "dialog.dialogs");
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(11, var10004, y, "quest.quests");
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(12, var10004, y, "global.transport");
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(13, var10004, y, "global.playerdata");
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(14, var10004, y, "global.recipes");
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(15, var10004, y, NoppesStringUtils.translate(new Object[]{"global.naturalspawn", "(WIP)"}));
      this.addButton(var10001);
      var10004 = super.guiLeft + 85;
      y += 22;
      var10001 = new GuiNpcButton(16, var10004, y, "global.linked");
      this.addButton(var10001);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 11) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageQuests);
      }

      if(id == 2) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageBanks);
      }

      if(id == 3) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageFactions);
      }

      if(id == 4) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageDialogs);
      }

      if(id == 12) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageTransport);
      }

      if(id == 13) {
         NoppesUtil.openGUI(super.player, new GuiNpcManagePlayerData(super.npc, this));
      }

      if(id == 14) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 4, 0, 0);
      }

      if(id == 15) {
         NoppesUtil.openGUI(super.player, new GuiNpcNaturalSpawns(super.npc));
      }

      if(id == 16) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageLinked);
      }

   }

   public void save() {}
}
