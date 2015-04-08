package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.NPCGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.QuestLogData;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.gui.util.GuiButtonNextPage;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITopButtonListener;
import noppes.npcs.constants.EnumPlayerPacket;
import org.lwjgl.opengl.GL11;
import tconstruct.client.tabs.InventoryTabQuests;
import tconstruct.client.tabs.TabRegistry;

public class GuiQuestLog extends GuiNPCInterface implements ITopButtonListener, ICustomScrollListener, IGuiData {

   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/standardbg.png");
   private EntityPlayer player;
   private GuiCustomScroll scroll;
   private HashMap sideButtons = new HashMap();
   private QuestLogData data = new QuestLogData();
   private boolean noQuests = false;
   private boolean questDetails = true;
   private Minecraft mc = Minecraft.getMinecraft();


   public GuiQuestLog(EntityPlayer player) {
      this.player = player;
      super.xSize = 280;
      super.ySize = 180;
      NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestLog, new Object[0]);
      super.drawDefaultBackground = false;
   }

   public void initGui() {
      super.initGui();
      this.sideButtons.clear();
      super.guiTop += 10;
      TabRegistry.updateTabValues(super.guiLeft, super.guiTop, InventoryTabQuests.class);
      TabRegistry.addTabsToList(super.buttonList);
      this.noQuests = false;
      if(this.data.categories.isEmpty()) {
         this.noQuests = true;
      } else {
         ArrayList categories = new ArrayList();
         categories.addAll(this.data.categories.keySet());
         Collections.sort(categories, String.CASE_INSENSITIVE_ORDER);
         int i = 0;

         for(Iterator var3 = categories.iterator(); var3.hasNext(); ++i) {
            String category = (String)var3.next();
            if(this.data.selectedCategory.isEmpty()) {
               this.data.selectedCategory = category;
            }

            this.sideButtons.put(Integer.valueOf(i), new GuiMenuSideButton(i, super.guiLeft - 69, super.guiTop + 2 + i * 21, 70, 22, category));
         }

         ((GuiMenuSideButton)this.sideButtons.get(Integer.valueOf(categories.indexOf(this.data.selectedCategory)))).active = true;
         if(this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
         }

         this.scroll.setList((List)this.data.categories.get(this.data.selectedCategory));
         this.scroll.setSize(134, 174);
         this.scroll.guiLeft = super.guiLeft + 5;
         this.scroll.guiTop = super.guiTop + 15;
         this.addScroll(this.scroll);
         this.addButton(new GuiButtonNextPage(1, super.guiLeft + 286, super.guiTop + 176, true));
         this.addButton(new GuiButtonNextPage(2, super.guiLeft + 144, super.guiTop + 176, false));
         this.getButton(1).visible = this.questDetails && this.data.hasSelectedQuest();
         this.getButton(2).visible = !this.questDetails && this.data.hasSelectedQuest();
      }
   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton instanceof GuiButtonNextPage) {
         if(guibutton.id == 1) {
            this.questDetails = false;
            this.initGui();
         }

         if(guibutton.id == 2) {
            this.questDetails = true;
            this.initGui();
         }

      }
   }

   public void drawScreen(int i, int j, float f) {
      if(this.scroll != null) {
         this.scroll.visible = !this.noQuests;
      }

      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.renderEngine.bindTexture(this.resource);
      this.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, 252, 195);
      this.drawTexturedModalRect(super.guiLeft + 252, super.guiTop, 188, 0, 67, 195);
      super.drawScreen(i, j, f);
      if(this.noQuests) {
         this.mc.fontRendererObj.drawString(StatCollector.translateToLocal("quest.noquests"), super.guiLeft + 84, super.guiTop + 80, CustomNpcResourceListener.DefaultTextColor);
      } else {
         GuiMenuSideButton[] title = (GuiMenuSideButton[])this.sideButtons.values().toArray(new GuiMenuSideButton[this.sideButtons.size()]);
         int var5 = title.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            GuiMenuSideButton button = title[var6];
            button.drawButton(this.mc, i, j);
         }

         this.mc.fontRendererObj.drawString(this.data.selectedCategory, super.guiLeft + 5, super.guiTop + 5, CustomNpcResourceListener.DefaultTextColor);
         if(this.data.hasSelectedQuest()) {
            String var8;
            if(this.questDetails) {
               this.drawProgress();
               var8 = StatCollector.translateToLocal("gui.text");
               this.mc.fontRendererObj.drawString(var8, super.guiLeft + 284 - this.mc.fontRendererObj.getStringWidth(var8), super.guiTop + 179, CustomNpcResourceListener.DefaultTextColor);
            } else {
               this.drawQuestText();
               var8 = StatCollector.translateToLocal("quest.objectives");
               this.mc.fontRendererObj.drawString(var8, super.guiLeft + 168, super.guiTop + 179, CustomNpcResourceListener.DefaultTextColor);
            }

            GL11.glPushMatrix();
            GL11.glTranslatef((float)(super.guiLeft + 148), (float)super.guiTop, 0.0F);
            GL11.glScalef(1.24F, 1.24F, 1.24F);
            super.fontRendererObj.drawString(this.data.selectedQuest, (130 - super.fontRendererObj.getStringWidth(this.data.selectedQuest)) / 2, 4, CustomNpcResourceListener.DefaultTextColor);
            GL11.glPopMatrix();
            this.drawHorizontalLine(super.guiLeft + 142, super.guiLeft + 312, super.guiTop + 17, -16777216 + CustomNpcResourceListener.DefaultTextColor);
         }
      }
   }

   private void drawQuestText() {
      TextBlockClient block = new TextBlockClient(this.data.getQuestText(), 174, new Object[]{this.player});
      int yoffset = super.guiTop + 5;

      for(int i = 0; i < block.lines.size(); ++i) {
         String text = ((IChatComponent)block.lines.get(i)).getFormattedText();
         super.fontRendererObj.drawString(text, super.guiLeft + 142, super.guiTop + 20 + i * super.fontRendererObj.FONT_HEIGHT, CustomNpcResourceListener.DefaultTextColor);
      }

   }

   private void drawProgress() {
      String complete = this.data.getComplete();
      if(complete != null && !complete.isEmpty()) {
         this.mc.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("quest.completewith", new Object[]{complete}), super.guiLeft + 144, super.guiTop + 105, CustomNpcResourceListener.DefaultTextColor);
      }

      int yoffset = super.guiTop + 22;

      for(Iterator var3 = this.data.getQuestStatus().iterator(); var3.hasNext(); yoffset += 10) {
         String process = (String)var3.next();
         int index = process.lastIndexOf(":");
         if(index > 0) {
            String name = process.substring(0, index);
            String trans = StatCollector.translateToLocal(name);
            if(!trans.equals(name)) {
               name = trans;
            }

            trans = StatCollector.translateToLocal("entity." + name + ".name");
            if(!trans.equals("entity." + name + ".name")) {
               name = trans;
            }

            process = name + process.substring(index);
         }

         this.mc.fontRendererObj.drawString("- " + process, super.guiLeft + 144, yoffset, CustomNpcResourceListener.DefaultTextColor);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {}

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      if(k == 0) {
         if(this.scroll != null) {
            this.scroll.mouseClicked(i, j, k);
         }

         Iterator var4 = (new ArrayList(this.sideButtons.values())).iterator();

         while(var4.hasNext()) {
            GuiMenuSideButton button = (GuiMenuSideButton)var4.next();
            if(button.mousePressed(this.mc, i, j)) {
               this.sideButtonPressed(button);
            }
         }
      }

   }

   private void sideButtonPressed(GuiMenuSideButton button) {
      if(!button.active) {
         NPCGuiHelper.clickSound();
         this.data.selectedCategory = button.displayString;
         this.data.selectedQuest = "";
         this.initGui();
      }
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll scroll) {
      if(scroll.hasSelected()) {
         this.data.selectedQuest = scroll.getSelected();
         this.initGui();
      }
   }

   public void keyTyped(char c, int i) {
      if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
         this.mc.displayGuiScreen((GuiScreen)null);
         this.mc.setIngameFocus();
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void setGuiData(NBTTagCompound compound) {
      QuestLogData data = new QuestLogData();
      data.readNBT(compound);
      this.data = data;
      this.initGui();
   }

   public void save() {}
}
