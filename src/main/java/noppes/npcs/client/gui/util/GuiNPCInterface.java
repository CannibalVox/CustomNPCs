package noppes.npcs.client.gui.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiHoverText;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiNPCInterface extends GuiScreen {

   public EntityClientPlayerMP player;
   public boolean drawDefaultBackground;
   public EntityNPCInterface npc;
   private HashMap buttons;
   private HashMap topbuttons;
   private HashMap sidebuttons;
   private HashMap textfields;
   private HashMap labels;
   private HashMap scrolls;
   private HashMap sliders;
   private HashMap extra;
   public String title;
   private ResourceLocation background;
   public boolean closeOnEsc;
   public int guiLeft;
   public int guiTop;
   public int xSize;
   public int ySize;
   private SubGuiInterface subgui;
   public int mouseX;
   public int mouseY;


   public GuiNPCInterface(EntityNPCInterface npc) {
      this.drawDefaultBackground = true;
      this.buttons = new HashMap();
      this.topbuttons = new HashMap();
      this.sidebuttons = new HashMap();
      this.textfields = new HashMap();
      this.labels = new HashMap();
      this.scrolls = new HashMap();
      this.sliders = new HashMap();
      this.extra = new HashMap();
      this.background = null;
      this.closeOnEsc = false;
      this.player = Minecraft.getMinecraft().thePlayer;
      this.npc = npc;
      this.title = "";
      this.xSize = 200;
      this.ySize = 222;
   }

   public GuiNPCInterface() {
      this((EntityNPCInterface)null);
   }

   public void setBackground(String texture) {
      this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public ResourceLocation getResource(String texture) {
      return new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public void initGui() {
      super.initGui();
      GuiNpcTextField.unfocus();
      if(this.subgui != null) {
         this.subgui.setWorldAndResolution(super.mc, super.width, super.height);
         this.subgui.initGui();
      }

      this.guiLeft = (super.width - this.xSize) / 2;
      this.guiTop = (super.height - this.ySize) / 2;
      super.buttonList.clear();
      this.labels.clear();
      this.textfields.clear();
      this.buttons.clear();
      this.sidebuttons.clear();
      this.topbuttons.clear();
      this.scrolls.clear();
      this.sliders.clear();
      Keyboard.enableRepeatEvents(true);
   }

   public void updateScreen() {
      if(this.subgui != null) {
         this.subgui.updateScreen();
      } else {
         Iterator var1 = this.textfields.values().iterator();

         while(var1.hasNext()) {
            GuiNpcTextField tf = (GuiNpcTextField)var1.next();
            if(tf.enabled) {
               tf.updateCursorCounter();
            }
         }

         super.updateScreen();
      }

   }

   public void addExtra(GuiHoverText gui) {
      gui.setWorldAndResolution(super.mc, 350, 250);
      this.extra.put(Integer.valueOf(gui.id), gui);
   }

   public void mouseClicked(int i, int j, int k) {
      if(this.subgui != null) {
         this.subgui.mouseClicked(i, j, k);
      } else {
         Iterator var4 = (new ArrayList(this.textfields.values())).iterator();

         while(var4.hasNext()) {
            GuiNpcTextField scroll = (GuiNpcTextField)var4.next();
            if(scroll.enabled) {
               scroll.mouseClicked(i, j, k);
            }
         }

         if(k == 0) {
            var4 = (new ArrayList(this.scrolls.values())).iterator();

            while(var4.hasNext()) {
               GuiCustomScroll scroll1 = (GuiCustomScroll)var4.next();
               scroll1.mouseClicked(i, j, k);
            }
         }

         this.mouseEvent(i, j, k);
         super.mouseClicked(i, j, k);
      }

   }

   public void mouseEvent(int i, int j, int k) {}

   protected void actionPerformed(GuiButton guibutton) {
      if(this.subgui != null) {
         this.subgui.buttonEvent(guibutton);
      } else {
         this.buttonEvent(guibutton);
      }

   }

   public void buttonEvent(GuiButton guibutton) {}

   public void keyTyped(char c, int i) {
      if(this.subgui != null) {
         this.subgui.keyTyped(c, i);
      }

      Iterator var3 = this.textfields.values().iterator();

      while(var3.hasNext()) {
         GuiNpcTextField tf = (GuiNpcTextField)var3.next();
         tf.textboxKeyTyped(c, i);
      }

      if(this.closeOnEsc && (i == 1 || !GuiNpcTextField.isActive() && this.isInventoryKey(i))) {
         this.close();
      }

   }

   public void onGuiClosed() {
      GuiNpcTextField.unfocus();
   }

   public void close() {
      this.displayGuiScreen((GuiScreen)null);
      super.mc.setIngameFocus();
      this.save();
   }

   public void addButton(GuiNpcButton button) {
      this.buttons.put(Integer.valueOf(button.field_146127_k), button);
      super.buttonList.add(button);
   }

   public void addTopButton(GuiMenuTopButton button) {
      this.topbuttons.put(Integer.valueOf(button.field_146127_k), button);
      super.buttonList.add(button);
   }

   public void addSideButton(GuiMenuSideButton button) {
      this.sidebuttons.put(Integer.valueOf(button.field_146127_k), button);
      super.buttonList.add(button);
   }

   public GuiNpcButton getButton(int i) {
      return (GuiNpcButton)this.buttons.get(Integer.valueOf(i));
   }

   public GuiMenuSideButton getSideButton(int i) {
      return (GuiMenuSideButton)this.sidebuttons.get(Integer.valueOf(i));
   }

   public GuiMenuTopButton getTopButton(int i) {
      return (GuiMenuTopButton)this.topbuttons.get(Integer.valueOf(i));
   }

   public void addTextField(GuiNpcTextField tf) {
      this.textfields.put(Integer.valueOf(tf.id), tf);
   }

   public GuiNpcTextField getTextField(int i) {
      return (GuiNpcTextField)this.textfields.get(Integer.valueOf(i));
   }

   public void addLabel(GuiNpcLabel label) {
      this.labels.put(Integer.valueOf(label.id), label);
   }

   public GuiNpcLabel getLabel(int i) {
      return (GuiNpcLabel)this.labels.get(Integer.valueOf(i));
   }

   public void addSlider(GuiNpcSlider slider) {
      this.sliders.put(Integer.valueOf(slider.field_146127_k), slider);
      super.buttonList.add(slider);
   }

   public GuiNpcSlider getSlider(int i) {
      return (GuiNpcSlider)this.sliders.get(Integer.valueOf(i));
   }

   public void addScroll(GuiCustomScroll scroll) {
      scroll.setWorldAndResolution(super.mc, 350, 250);
      this.scrolls.put(Integer.valueOf(scroll.id), scroll);
   }

   public GuiCustomScroll getScroll(int id) {
      return (GuiCustomScroll)this.scrolls.get(Integer.valueOf(id));
   }

   public abstract void save();

   public void drawScreen(int i, int j, float f) {
      this.mouseX = i;
      this.mouseY = j;
      if(this.drawDefaultBackground && this.subgui == null) {
         this.drawDefaultBackground();
      }

      if(this.background != null && super.mc.renderEngine != null) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         super.mc.renderEngine.bindTexture(this.background);
         if(this.xSize > 256) {
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 250, this.ySize);
            this.drawTexturedModalRect(this.guiLeft + 250, this.guiTop, 256 - (this.xSize - 250), 0, this.xSize - 250, this.ySize);
         } else {
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
         }
      }

      this.drawCenteredString(super.fontRendererObj, this.title, super.width / 2, this.guiTop + 4, 16777215);
      Iterator var4 = this.labels.values().iterator();

      while(var4.hasNext()) {
         GuiNpcLabel gui = (GuiNpcLabel)var4.next();
         gui.drawLabel(this, super.fontRendererObj);
      }

      var4 = this.textfields.values().iterator();

      while(var4.hasNext()) {
         GuiNpcTextField gui1 = (GuiNpcTextField)var4.next();
         gui1.drawTextBox(i, j);
      }

      var4 = this.scrolls.values().iterator();

      while(var4.hasNext()) {
         GuiCustomScroll gui2 = (GuiCustomScroll)var4.next();
         gui2.drawScreen(i, j, f, this.hasSubGui()?0:Mouse.getDWheel());
      }

      var4 = this.extra.values().iterator();

      while(var4.hasNext()) {
         GuiScreen gui3 = (GuiScreen)var4.next();
         gui3.drawScreen(i, j, f);
      }

      super.drawScreen(i, j, f);
      if(this.subgui != null) {
         this.subgui.drawScreen(i, j, f);
      }

   }

   public FontRenderer getFontRenderer() {
      return super.fontRendererObj;
   }

   public void elementClicked() {
      if(this.subgui != null) {
         this.subgui.elementClicked();
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void doubleClicked() {}

   public boolean isInventoryKey(int i) {
      return i == super.mc.gameSettings.keyBindInventory.getKeyCode();
   }

   public void drawDefaultBackground() {
      super.drawDefaultBackground();
   }

   public void displayGuiScreen(GuiScreen gui) {
      super.mc.displayGuiScreen(gui);
   }

   public void setSubGui(SubGuiInterface gui) {
      this.subgui = gui;
      this.subgui.setWorldAndResolution(super.mc, super.width, super.height);
      this.subgui.parent = this;
      this.initGui();
   }

   public void closeSubGui(SubGuiInterface gui) {
      this.subgui = null;
   }

   public boolean hasSubGui() {
      return this.subgui != null;
   }

   public SubGuiInterface getSubGui() {
      return this.hasSubGui() && this.subgui.hasSubGui()?this.subgui.getSubGui():this.subgui;
   }

   public void drawNpc(int x, int y) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + x), (float)(this.guiTop + y), 50.0F);
      float scale = 1.0F;
      if((double)this.npc.height > 2.4D) {
         scale = 2.0F / this.npc.height;
      }

      GL11.glScalef(-30.0F * scale, 30.0F * scale, 30.0F * scale);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = this.npc.renderYawOffset;
      float f3 = this.npc.rotationYaw;
      float f4 = this.npc.rotationPitch;
      float f7 = this.npc.rotationYawHead;
      float f5 = (float)(this.guiLeft + x) - (float)this.mouseX;
      float f6 = (float)(this.guiTop + y - 50) - (float)this.mouseY;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f6 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      this.npc.renderYawOffset = (float)Math.atan((double)(f5 / 40.0F)) * 20.0F;
      this.npc.rotationYaw = (float)Math.atan((double)(f5 / 40.0F)) * 40.0F;
      this.npc.rotationPitch = -((float)Math.atan((double)(f6 / 40.0F))) * 20.0F;
      this.npc.rotationYawHead = this.npc.rotationYaw;
      GL11.glTranslatef(0.0F, this.npc.yOffset, 0.0F);
      RenderManager.instance.playerViewY = 180.0F;
      RenderManager.instance.renderEntityWithPosYaw(this.npc, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      this.npc.renderYawOffset = f2;
      this.npc.rotationYaw = f3;
      this.npc.rotationPitch = f4;
      this.npc.rotationYawHead = f7;
      GL11.glPopMatrix();
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable('\u803a');
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   public void openLink(String link) {
      try {
         Class throwable = Class.forName("java.awt.Desktop");
         Object object = throwable.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         throwable.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(link)});
      } catch (Throwable var4) {
         ;
      }

   }
}
