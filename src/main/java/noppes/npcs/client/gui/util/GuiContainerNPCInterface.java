package noppes.npcs.client.gui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.containers.ContainerEmpty;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainerNPCInterface extends GuiContainer {

   public boolean drawDefaultBackground = false;
   public int field_147003_i;
   public int field_147009_r;
   public EntityClientPlayerMP player;
   public EntityNPCInterface npc;
   private HashMap buttons = new HashMap();
   private HashMap topbuttons = new HashMap();
   private HashMap textfields = new HashMap();
   private HashMap labels = new HashMap();
   private HashMap scrolls = new HashMap();
   private HashMap sliders = new HashMap();
   public String title;
   public boolean closeOnEsc = false;
   private SubGuiInterface subgui;
   public int mouseX;
   public int mouseY;


   public GuiContainerNPCInterface(EntityNPCInterface npc, Container cont) {
      super(cont);
      this.player = Minecraft.getMinecraft().thePlayer;
      this.npc = npc;
      this.title = "Npc Mainmenu";
   }

   public void initGui() {
      super.initGui();
      GuiNpcTextField.unfocus();
      super.buttonList.clear();
      this.buttons.clear();
      this.topbuttons.clear();
      this.scrolls.clear();
      this.sliders.clear();
      this.labels.clear();
      this.textfields.clear();
      Keyboard.enableRepeatEvents(true);
      if(this.subgui != null) {
         this.subgui.setWorldAndResolution(super.mc, super.width, super.height);
         this.subgui.initGui();
      }

      super.buttonList.clear();
      this.field_147003_i = (super.width - super.xSize) / 2;
      this.field_147009_r = (super.height - super.ySize) / 2;
   }

   public ResourceLocation getResource(String texture) {
      return new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public void updateScreen() {
      Iterator var1 = (new ArrayList(this.textfields.values())).iterator();

      while(var1.hasNext()) {
         GuiNpcTextField tf = (GuiNpcTextField)var1.next();
         if(tf.enabled) {
            tf.updateCursorCounter();
         }
      }

      super.updateScreen();
   }

   protected void mouseClicked(int i, int j, int k) {
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

   protected void keyTyped(char c, int i) {
      if(this.subgui != null) {
         this.subgui.keyTyped(c, i);
      } else {
         Iterator var3 = (new ArrayList(this.textfields.values())).iterator();

         while(var3.hasNext()) {
            GuiNpcTextField tf = (GuiNpcTextField)var3.next();
            tf.textboxKeyTyped(c, i);
         }

         if(this.closeOnEsc && (i == 1 || i == super.mc.gameSettings.keyBindInventory.getKeyCode() && !GuiNpcTextField.isActive())) {
            this.close();
         }
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      if(this.subgui != null) {
         this.subgui.buttonEvent(guibutton);
      } else {
         this.buttonEvent(guibutton);
      }

   }

   public void buttonEvent(GuiButton guibutton) {}

   public void close() {
      GuiNpcTextField.unfocus();
      this.save();
      this.player.closeScreen();
      this.displayGuiScreen((GuiScreen)null);
      super.mc.setIngameFocus();
   }

   public void addButton(GuiNpcButton button) {
      this.buttons.put(Integer.valueOf(button.field_146127_k), button);
      super.buttonList.add(button);
   }

   public void addTopButton(GuiMenuTopButton button) {
      this.topbuttons.put(Integer.valueOf(button.field_146127_k), button);
      super.buttonList.add(button);
   }

   public GuiNpcButton getButton(int i) {
      return (GuiNpcButton)this.buttons.get(Integer.valueOf(i));
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

   public GuiMenuTopButton getTopButton(int i) {
      return (GuiMenuTopButton)this.topbuttons.get(Integer.valueOf(i));
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

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      this.drawCenteredString(super.fontRendererObj, StatCollector.translateToLocal(this.title), super.width / 2, this.field_147009_r - 8, 16777215);
      Iterator var4 = (new ArrayList(this.labels.values())).iterator();

      while(var4.hasNext()) {
         GuiNpcLabel scroll = (GuiNpcLabel)var4.next();
         scroll.drawLabel(this, super.fontRendererObj);
      }

      var4 = (new ArrayList(this.textfields.values())).iterator();

      while(var4.hasNext()) {
         GuiNpcTextField scroll1 = (GuiNpcTextField)var4.next();
         scroll1.drawTextBox(i, j);
      }

      var4 = (new ArrayList(this.scrolls.values())).iterator();

      while(var4.hasNext()) {
         GuiCustomScroll scroll2 = (GuiCustomScroll)var4.next();
         scroll2.drawScreen(i, j, f, this.hasSubGui()?0:Mouse.getDWheel());
      }

   }

   public abstract void save();

   public void drawScreen(int i, int j, float f) {
      this.mouseX = i;
      this.mouseY = j;
      Container container = super.inventorySlots;
      if(this.subgui != null) {
         super.inventorySlots = new ContainerEmpty();
      }

      super.drawScreen(i, j, f);
      super.zLevel = 0.0F;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if(this.subgui != null) {
         super.inventorySlots = container;
         RenderHelper.disableStandardItemLighting();
         this.subgui.drawScreen(i, j, f);
      }

   }

   public void drawDefaultBackground() {
      if(this.drawDefaultBackground && this.subgui == null) {
         super.drawDefaultBackground();
      }

   }

   public FontRenderer getFontRenderer() {
      return super.fontRendererObj;
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

   public void displayGuiScreen(GuiScreen gui) {
      super.mc.displayGuiScreen(gui);
   }

   public void setSubGui(SubGuiInterface gui) {
      this.subgui = gui;
      this.subgui.setWorldAndResolution(super.mc, super.width, super.height);
      this.subgui.parent = this;
      this.initGui();
   }

   public void drawNpc(int x, int y) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.field_147003_i + x), (float)(this.field_147009_r + y), 50.0F);
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
      float f5 = (float)(this.field_147003_i + x) - (float)this.mouseX;
      float f6 = (float)(this.field_147009_r + y - 50) - (float)this.mouseY;
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
}
