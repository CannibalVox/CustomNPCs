package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.blocks.tiles.TileShelf;
import noppes.npcs.client.model.blocks.ModelBeam;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockBeamRenderer extends BlockRendererInterface {

   private final ModelBeam model = new ModelBeam();


   public BlockBeamRenderer() {
      ((BlockRotated)CustomItems.beam).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileColorable tile = (TileColorable)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.setWoodTexture(var1.getBlockMetadata());
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   private boolean shouldDraw(World world, int x, int y, int z, int rotation) {
      TileEntity tile = world.getTileEntity(x, y, z);
      return tile != null && tile instanceof TileShelf?((TileShelf)tile).rotation != rotation:true;
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.9F, 0.0F);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      this.setWoodTexture(metadata);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.beam.getRenderType();
   }
}
