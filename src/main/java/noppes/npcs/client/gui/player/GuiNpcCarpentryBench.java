package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.player.GuiRecipes;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class GuiNpcCarpentryBench extends GuiContainerNPCInterface {

   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/carpentry.png");
   private ContainerCarpentryBench container;
   private GuiNpcButton button;


   public GuiNpcCarpentryBench(ContainerCarpentryBench container) {
      super((EntityNPCInterface)null, container);
      this.container = container;
      super.title = "";
      super.allowUserInput = false;
      super.closeOnEsc = true;
      super.ySize = 180;
   }

   public void initGui() {
      super.initGui();
      this.addButton(this.button = new GuiNpcButton(0, super.field_147003_i + 158, super.field_147009_r + 4, 12, 20, "..."));
   }

   public void buttonEvent(GuiButton guibutton) {
      this.displayGuiScreen(new GuiRecipes());
   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      this.button.enabled = RecipeController.instance != null && !RecipeController.instance.anvilRecipes.isEmpty();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.resource);
      int l = (super.width - super.xSize) / 2;
      int i1 = (super.height - super.ySize) / 2;
      String title = StatCollector.translateToLocal("tile.npcCarpentyBench.name");
      if(this.container.getMetadata() >= 4) {
         title = StatCollector.translateToLocal("tile.anvil.name");
      }

      this.drawTexturedModalRect(l, i1, 0, 0, super.xSize, super.ySize);
      super.drawGuiContainerBackgroundLayer(f, i, j);
      super.fontRendererObj.drawString(title, super.field_147003_i + 4, super.field_147009_r + 4, CustomNpcResourceListener.DefaultTextColor);
      super.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), super.field_147003_i + 4, super.field_147009_r + 87, CustomNpcResourceListener.DefaultTextColor);
   }

   public void save() {}
}
