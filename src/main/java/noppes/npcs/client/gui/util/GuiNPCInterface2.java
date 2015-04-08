package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcMenu;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public abstract class GuiNPCInterface2 extends GuiNPCInterface {

   public EntityPlayer player;
   public EntityNPCInterface npc;
   private ResourceLocation background;
   private GuiNpcMenu menu;


   public GuiNPCInterface2(EntityNPCInterface npc) {
      this(npc, -1);
   }

   public GuiNPCInterface2(EntityNPCInterface npc, int activeMenu) {
      this.background = new ResourceLocation("customnpcs:textures/gui/menubg.png");
      this.player = Minecraft.getMinecraft().thePlayer;
      this.npc = npc;
      super.xSize = 420;
      super.ySize = 200;
      this.menu = new GuiNpcMenu(this, activeMenu, npc);
   }

   public void initGui() {
      super.initGui();
      this.menu.initGui(super.guiLeft, super.guiTop, super.xSize);
   }

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      if(!this.hasSubGui()) {
         this.menu.mouseClicked(i, j, k);
      }

   }

   public void delete() {
      this.npc.delete();
      this.displayGuiScreen((GuiScreen)null);
      super.mc.setIngameFocus();
   }

   public abstract void save();

   public void drawScreen(int i, int j, float f) {
      if(super.drawDefaultBackground) {
         this.drawDefaultBackground();
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.background);
      this.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, 200, 220);
      this.drawTexturedModalRect(super.guiLeft + super.xSize - 230, super.guiTop, 26, 0, 230, 220);
      this.menu.drawElements(this.getFontRenderer(), i, j, super.mc, f);
      boolean bo = super.drawDefaultBackground;
      super.drawDefaultBackground = false;
      super.drawScreen(i, j, f);
      super.drawDefaultBackground = bo;
   }
}
