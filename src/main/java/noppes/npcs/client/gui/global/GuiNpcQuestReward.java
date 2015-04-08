package noppes.npcs.client.gui.global;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.containers.ContainerNpcQuestReward;
import noppes.npcs.controllers.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class GuiNpcQuestReward extends GuiContainerNPCInterface implements ITextfieldListener {

   private Quest quest;
   private ResourceLocation resource;


   public GuiNpcQuestReward(EntityNPCInterface npc, ContainerNpcQuestReward container) {
      super(npc, container);
      this.quest = GuiNPCManageQuest.quest;
      this.resource = this.getResource("questreward.png");
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "quest.randomitem", super.field_147003_i + 4, super.field_147009_r + 4));
      this.addButton(new GuiNpcButton(0, super.field_147003_i + 4, super.field_147009_r + 14, 60, 20, new String[]{"gui.no", "gui.yes"}, this.quest.randomReward?1:0));
      this.addButton(new GuiNpcButton(5, super.field_147003_i, super.field_147009_r + super.ySize, 98, 20, "gui.back"));
      this.addLabel(new GuiNpcLabel(1, "quest.exp", super.field_147003_i + 4, super.field_147009_r + 45));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.field_147003_i + 4, super.field_147009_r + 55, 60, 20, this.quest.rewardExp + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(0, 99999, 0);
   }

   public void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 5) {
         NoppesUtil.openGUI(super.player, GuiNPCManageQuest.Instance);
      }

      if(id == 0) {
         this.quest.randomReward = ((GuiNpcButton)guibutton).getValue() == 1;
      }

   }

   public void onGuiClosed() {}

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.resource);
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
