package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import noppes.npcs.CustomItems;

public class BlockBloodRenderer implements ISimpleBlockRenderingHandler {

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      renderer.renderStandardBlock(block, 0, 0, 0);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      if(!this.shouldDraw(world, x, y, z, block)) {
         return false;
      } else {
         renderer.setRenderFromInside(true);
         renderer.renderStandardBlock(block, x, y, z);
         renderer.setRenderFromInside(false);
         return true;
      }
   }

   private boolean shouldDraw(IBlockAccess world, int x, int y, int z, Block block) {
      return block.shouldSideBeRendered(world, x + 1, y, z, 0) || block.shouldSideBeRendered(world, x - 1, y, z, 0) || block.shouldSideBeRendered(world, x, y + 1, z, 0) || block.shouldSideBeRendered(world, x, y - 1, z, 0) || block.shouldSideBeRendered(world, x, y, z + 1, 0) || block.shouldSideBeRendered(world, x, y, z - 1, 0);
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return false;
   }

   public int getRenderId() {
      return CustomItems.blood.getRenderType();
   }
}
