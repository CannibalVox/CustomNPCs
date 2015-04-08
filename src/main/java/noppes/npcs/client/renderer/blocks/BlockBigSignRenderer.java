package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockBigSign;
import noppes.npcs.blocks.tiles.TileBigSign;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.model.blocks.ModelBigSign;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockBigSignRenderer extends BlockRendererInterface {

   private final ModelBigSign model = new ModelBigSign();
   private static final ResourceLocation resource = new ResourceLocation("customnpcs:textures/models/BigSign.png");


   public BlockBigSignRenderer() {
      ((BlockBigSign)CustomItems.bigsign).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      Block block = var1.getBlockType();
      TileBigSign tile = (TileBigSign)var1;
      Minecraft mc = Minecraft.getMinecraft();
      if(tile.block == null || tile.hasChanged) {
         tile.block = new TextBlockClient(tile.getText(), 112, new Object[]{mc.thePlayer});
         tile.hasChanged = false;
      }

      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      float xOffset = 0.0F;
      float yOffset = 0.0F;
      if(tile.rotation == 1) {
         xOffset = -0.44F;
      } else if(tile.rotation == 3) {
         xOffset = 0.44F;
      } else if(tile.rotation == 2) {
         yOffset = -0.44F;
      } else if(tile.rotation == 0) {
         yOffset = 0.44F;
      }

      GL11.glTranslatef((float)var2 + 0.5F + xOffset, (float)var4 + 0.5F, (float)var6 + 0.5F + yOffset);
      float f1 = 0.6666667F;
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if(tile.rotation % 2 == 0) {
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      }

      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      TextureManager manager = Minecraft.getMinecraft().getTextureManager();
      manager.bindTexture(resource);
      this.model.renderSign();
      GL11.glPopMatrix();
      if(!tile.block.lines.isEmpty() && !this.playerTooFar(tile)) {
         float f3 = 0.0133F * f1;
         GL11.glTranslatef(0.0F, 0.5F, 0.065F);
         GL11.glScalef(f3, -f3, f3);
         GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
         GL11.glDepthMask(false);
         FontRenderer fontrenderer = this.func_147498_b();
         float lineOffset = 0.0F;
         if(tile.block.lines.size() < 14) {
            lineOffset = (14.0F - (float)tile.block.lines.size()) / 2.0F;
         }

         for(int i = 0; i < tile.block.lines.size(); ++i) {
            String text = ((IChatComponent)tile.block.lines.get(i)).getFormattedText();
            fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, (int)((double)(lineOffset + (float)i) * ((double)fontrenderer.FONT_HEIGHT - 0.3D)), 0);
            if(i == 12) {
               break;
            }
         }

         GL11.glDepthMask(true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      TextureManager manager = Minecraft.getMinecraft().getTextureManager();
      manager.bindTexture(resource);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.renderSign();
      GL11.glPopMatrix();
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return CustomItems.bigsign.getRenderType();
   }

}
