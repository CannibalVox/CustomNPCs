package tconstruct.client.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class AbstractTab extends GuiButton {

   ResourceLocation texture = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   ItemStack renderStack;
   RenderItem itemRenderer = new RenderItem();


   public AbstractTab(int id, int posX, int posY, ItemStack renderStack) {
      super(id, posX, posY, 28, 32, "");
      this.renderStack = renderStack;
   }

   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if(super.visible) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int yTexPos = super.enabled?3:32;
         int ySize = super.enabled?25:32;
         int xOffset = super.id == 2?0:1;
         int yPos = super.yPosition + (super.enabled?3:0);
         mc.renderEngine.bindTexture(this.texture);
         this.drawTexturedModalRect(super.xPosition, yPos, xOffset * 28, yTexPos, 28, ySize);
         RenderHelper.enableGUIStandardItemLighting();
         super.zLevel = 100.0F;
         this.itemRenderer.zLevel = 100.0F;
         GL11.glEnable(2896);
         GL11.glEnable('\u803a');
         this.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.renderEngine, this.renderStack, super.xPosition + 6, super.yPosition + 8);
         this.itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.renderEngine, this.renderStack, super.xPosition + 6, super.yPosition + 8);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         this.itemRenderer.zLevel = 0.0F;
         super.zLevel = 0.0F;
         RenderHelper.disableStandardItemLighting();
      }

   }

   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
      boolean inWindow = super.enabled && super.visible && mouseX >= super.xPosition && mouseY >= super.yPosition && mouseX < super.xPosition + super.width && mouseY < super.yPosition + super.height;
      if(inWindow) {
         this.onTabClicked();
      }

      return inWindow;
   }

   public abstract void onTabClicked();

   public abstract boolean shouldAddToList();
}
