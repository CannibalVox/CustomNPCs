package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.client.model.blocks.ModelCampfire;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockCampfireRenderer extends BlockRendererInterface {

   private final ModelCampfire model = new ModelCampfire();


   public BlockCampfireRenderer() {
      ((BlockRotated)CustomItems.campfire).renderId = RenderingRegistry.getNextAvailableRenderId();
      ((BlockRotated)CustomItems.campfire_unlit).renderId = ((BlockRotated)CustomItems.campfire).renderId;
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileColorable tile = (TileColorable)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(45 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.PlanksOak);
      this.model.renderLog(0.0625F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.Stone);
      this.model.renderRock(0.0625F);
      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 1.2F, 0.0F);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.PlanksOak);
      this.model.renderLog(0.0625F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(BlockRendererInterface.Stone);
      this.model.renderRock(0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.campfire.getRenderType();
   }
}
