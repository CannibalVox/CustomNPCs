package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcMenu;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainerNPCInterface2 extends GuiContainerNPCInterface {

   public EntityPlayer player;
   public EntityNPCInterface npc;
   private ResourceLocation background;
   private final ResourceLocation defaultBackground;
   private GuiNpcMenu menu;
   public int menuYOffset;


   public GuiContainerNPCInterface2(EntityNPCInterface npc, Container cont) {
      this(npc, cont, -1);
   }

   public GuiContainerNPCInterface2(EntityNPCInterface npc, Container cont, int activeMenu) {
      super(npc, cont);
      this.background = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
      this.defaultBackground = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
      this.menuYOffset = 0;
      this.player = Minecraft.getMinecraft().thePlayer;
      this.npc = npc;
      super.xSize = 420;
      this.menu = new GuiNpcMenu(this, activeMenu, npc);
      super.title = "";
   }

   public void setBackground(String texture) {
      this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public ResourceLocation getResource(String texture) {
      return new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public void initGui() {
      super.initGui();
      this.menu.initGui(super.field_147003_i, super.field_147009_r + this.menuYOffset, super.xSize);
   }

   protected void mouseClicked(int i, int j, int k) {
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

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.background);
      this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, 256, 256);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.defaultBackground);
      this.drawTexturedModalRect(super.field_147003_i + super.xSize - 200, super.field_147009_r, 26, 0, 200, 220);
      this.menu.drawElements(super.fontRendererObj, i, j, super.mc, f);
      super.drawGuiContainerBackgroundLayer(f, i, j);
   }
}
