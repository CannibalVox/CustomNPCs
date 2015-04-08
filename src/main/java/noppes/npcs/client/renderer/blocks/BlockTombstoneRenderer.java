package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockTombstone;
import noppes.npcs.blocks.tiles.TileTombstone;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.model.blocks.ModelTombstone1;
import noppes.npcs.client.model.blocks.ModelTombstone2;
import noppes.npcs.client.model.blocks.ModelTombstone3;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockTombstoneRenderer extends BlockRendererInterface {

   private final ModelTombstone1 model = new ModelTombstone1();
   private final ModelTombstone2 model2 = new ModelTombstone2();
   private final ModelTombstone3 model3 = new ModelTombstone3();


   public BlockTombstoneRenderer() {
      ((BlockTombstone)CustomItems.tombstone).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileTombstone tile = (TileTombstone)var1;
      int meta = tile.getBlockMetadata();
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      if(meta == 2) {
         GL11.glScalef(1.0F, 1.0F, 1.14F);
      }

      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.Stone);
      if(meta == 0) {
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(meta == 1) {
         this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.model3.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      if(meta < 2 && !this.playerTooFar(tile)) {
         this.renderText(tile, meta);
      }

      GL11.glPopMatrix();
   }

   private void renderText(TileTombstone tile, int meta) {
      if(tile.block == null || tile.hasChanged) {
         tile.block = new TextBlockClient(tile.getText(), 94, new Object[]{Minecraft.getMinecraft().thePlayer});
         tile.hasChanged = false;
      }

      if(!tile.block.lines.isEmpty()) {
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         float f3 = 0.00665F;
         GL11.glTranslatef(0.0F, -0.64F, meta == 0?0.095F:0.126F);
         GL11.glScalef(f3, -f3, f3);
         GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
         GL11.glDepthMask(false);
         FontRenderer fontrenderer = this.func_147498_b();
         float lineOffset = 0.0F;
         if(tile.block.lines.size() < 11) {
            lineOffset = (11.0F - (float)tile.block.lines.size()) / 2.0F;
         }

         for(int i = 0; i < tile.block.lines.size(); ++i) {
            String text = ((IChatComponent)tile.block.lines.get(i)).getFormattedText();
            fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, (int)((double)(lineOffset + (float)i) * ((double)fontrenderer.FONT_HEIGHT - 0.3D)), 16777215);
            if(i == 13) {
               break;
            }
         }

         GL11.glDepthMask(true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 1.0F, 0.0F);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.Stone);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if(meta == 0) {
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(meta == 1) {
         this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.model3.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.tombstone.getRenderType();
   }
}
