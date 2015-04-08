package noppes.npcs.client.gui.questtypes;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcButtonYesNo;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.containers.ContainerNpcQuestTypeItem;
import noppes.npcs.controllers.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.quests.QuestItem;
import org.lwjgl.opengl.GL11;

public class GuiNpcQuestTypeItem extends GuiContainerNPCInterface implements ITextfieldListener {

   private Quest quest;
   private static final ResourceLocation field_110422_t = new ResourceLocation("customnpcs", "textures/gui/followersetup.png");


   public GuiNpcQuestTypeItem(EntityNPCInterface npc, ContainerNpcQuestTypeItem container) {
      super(npc, container);
      this.quest = GuiNPCManageQuest.quest;
      super.title = "";
      super.ySize = 202;
      super.closeOnEsc = false;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "quest.takeitems", super.field_147003_i + 4, super.field_147009_r + 8));
      this.addButton(new GuiNpcButton(0, super.field_147003_i + 90, super.field_147009_r + 3, 60, 20, new String[]{"gui.yes", "gui.no"}, ((QuestItem)this.quest.questInterface).leaveItems?1:0));
      this.addLabel(new GuiNpcLabel(1, "gui.ignoreDamage", super.field_147003_i + 4, super.field_147009_r + 29));
      this.addButton(new GuiNpcButtonYesNo(1, super.field_147003_i + 90, super.field_147009_r + 24, 50, 20, ((QuestItem)this.quest.questInterface).ignoreDamage));
      this.addLabel(new GuiNpcLabel(2, "gui.ignoreNBT", super.field_147003_i + 62, super.field_147009_r + 51));
      this.addButton(new GuiNpcButtonYesNo(2, super.field_147003_i + 120, super.field_147009_r + 46, 50, 20, ((QuestItem)this.quest.questInterface).ignoreNBT));
      this.addButton(new GuiNpcButton(5, super.field_147003_i, super.field_147009_r + super.ySize, 98, 20, "gui.back"));
   }

   public void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 0) {
         ((QuestItem)this.quest.questInterface).leaveItems = ((GuiNpcButton)guibutton).getValue() == 1;
      }

      if(guibutton.id == 1) {
         ((QuestItem)this.quest.questInterface).ignoreDamage = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

      if(guibutton.id == 2) {
         ((QuestItem)this.quest.questInterface).ignoreNBT = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

      if(guibutton.id == 5) {
         NoppesUtil.openGUI(super.player, GuiNPCManageQuest.Instance);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      this.drawWorldBackground(0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(field_110422_t);
      int l = (super.width - super.xSize) / 2;
      int i1 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(l, i1, 0, 0, super.xSize, super.ySize);
      super.drawGuiContainerBackgroundLayer(f, i, j);
   }

   public void save() {}

   public void unFocused(GuiNpcTextField textfield) {
      this.quest.rewardExp = textfield.getInteger();
   }

}
