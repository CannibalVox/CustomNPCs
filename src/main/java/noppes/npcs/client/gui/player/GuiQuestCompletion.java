package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.ITopButtonListener;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Quest;
import org.lwjgl.opengl.GL11;

public class GuiQuestCompletion extends GuiNPCInterface implements ITopButtonListener {

   private Quest quest;
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/smallbg.png");


   public GuiQuestCompletion(Quest quest) {
      super.xSize = 176;
      super.ySize = 222;
      this.quest = quest;
      super.drawDefaultBackground = false;
      super.title = "";
   }

   public void initGui() {
      super.initGui();
      String questTitle = this.quest.title;
      int left = (super.xSize - super.fontRendererObj.getStringWidth(questTitle)) / 2;
      this.addLabel(new GuiNpcLabel(0, questTitle, super.guiLeft + left, super.guiTop + 4));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 38, super.guiTop + super.ySize - 24, 100, 20, StatCollector.translateToLocal("quest.complete")));
   }

   public void drawScreen(int i, int j, float f) {
      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.resource);
      this.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, super.xSize, super.ySize);
      this.drawHorizontalLine(super.guiLeft + 4, super.guiLeft + 170, super.guiTop + 13, -16777216 + CustomNpcResourceListener.DefaultTextColor);
      this.drawQuestText();
      super.drawScreen(i, j, f);
   }

   private void drawQuestText() {
      int xoffset = super.guiLeft + 4;
      TextBlockClient block = new TextBlockClient(this.quest.completeText, 172, new Object[]{super.player});
      int yoffset = super.guiTop + 20;

      for(int i = 0; i < block.lines.size(); ++i) {
         String text = ((IChatComponent)block.lines.get(i)).getFormattedText();
         super.fontRendererObj.drawString(text, super.guiLeft + 4, super.guiTop + 16 + i * super.fontRendererObj.FONT_HEIGHT, CustomNpcResourceListener.DefaultTextColor);
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 0) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestCompletion, new Object[]{Integer.valueOf(this.quest.id)});
         this.close();
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {}

   public void keyTyped(char c, int i) {
      if(i == 1 || this.isInventoryKey(i)) {
         this.close();
      }

   }

   public void save() {
      NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestCompletion, new Object[]{Integer.valueOf(this.quest.id)});
   }
}
