package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileSign;
import noppes.npcs.client.model.blocks.ModelSign;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockSignRenderer extends BlockRendererInterface {

   private final ModelSign model = new ModelSign();


   public BlockSignRenderer() {
      ((BlockRotated)CustomItems.sign).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileSign tile = (TileSign)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.62F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation + 90), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.Steel);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.setWoodTexture(tile.getBlockMetadata());
      this.model.Sign.render(0.0625F);
      if(tile.icon != null && !this.playerTooFar(tile)) {
         this.doRender(var2, var4, var6, tile.rotation, tile.icon);
      }

      GL11.glPopMatrix();
   }

   public void doRender(double par2, double par4, double par6, int meta, ItemStack iicon) {
      if(iicon.getItemSpriteNumber() != 0 || !RenderBlocks.renderItemIn3d(Block.getBlockFromItem(iicon.getItem()).getRenderType())) {
         GL11.glPushMatrix();
         this.bindTexture(TextureMap.locationItemsTexture);
         GL11.glTranslatef(0.0F, 1.02F, -0.03F);
         GL11.glDepthMask(false);
         float f2 = 0.024F;
         Minecraft mc = Minecraft.getMinecraft();
         GL11.glScalef(f2, f2, f2);
         BlockRendererInterface.renderer.renderItemIntoGUI(this.func_147498_b(), mc.renderEngine, iicon, -8, -8);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, 0.0F, -2.9F);
         BlockRendererInterface.renderer.renderItemIntoGUI(this.func_147498_b(), mc.renderEngine, iicon, -8, -8);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.6F, 0.0F);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.Steel);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.setWoodTexture(metadata);
      this.model.Sign.render(0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.sign.getRenderType();
   }
}
