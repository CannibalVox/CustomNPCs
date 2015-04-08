package noppes.npcs.client.gui.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.containers.ContainerCrate;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCrate extends GuiContainer {

   private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
   private IInventory upperChestInventory;
   private IInventory lowerChestInventory;
   private int inventoryRows;


   public GuiCrate(ContainerCrate container) {
      super(container);
      this.upperChestInventory = container.upperChestInventory;
      this.lowerChestInventory = container.lowerChestInventory;
      super.allowUserInput = false;
      short short1 = 222;
      int i = short1 - 108;
      this.inventoryRows = this.lowerChestInventory.getSizeInventory() / 9;
      super.ySize = i + this.inventoryRows * 18;
   }

   protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
      super.fontRendererObj.drawString(this.lowerChestInventory.isCustomInventoryName()?this.lowerChestInventory.getInventoryName():I18n.format(this.lowerChestInventory.getInventoryName(), new Object[0]), 8, 6, CustomNpcResourceListener.DefaultTextColor);
      super.fontRendererObj.drawString(this.upperChestInventory.isCustomInventoryName()?this.upperChestInventory.getInventoryName():I18n.format(this.upperChestInventory.getInventoryName(), new Object[0]), 8, super.ySize - 96 + 2, CustomNpcResourceListener.DefaultTextColor);
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.getTextureManager().bindTexture(field_147017_u);
      int k = (super.width - super.xSize) / 2;
      int l = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(k, l, 0, 0, super.xSize, this.inventoryRows * 18 + 17);
      this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, super.xSize, 96);
   }

}
