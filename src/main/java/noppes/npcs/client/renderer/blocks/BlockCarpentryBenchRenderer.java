package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockCarpentryBench;
import noppes.npcs.client.model.blocks.ModelAnvil;
import noppes.npcs.client.model.blocks.ModelCarpentryBench;
import org.lwjgl.opengl.GL11;

public class BlockCarpentryBenchRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

   private final ModelCarpentryBench model = new ModelCarpentryBench();
   private final ModelAnvil anvil = new ModelAnvil();
   private static final ResourceLocation resource3 = new ResourceLocation("customnpcs", "textures/models/Steel.png");
   private static final ResourceLocation field_110631_g = new ResourceLocation("customnpcs", "textures/models/CarpentryBench.png");


   public BlockCarpentryBenchRenderer() {
      ((BlockCarpentryBench)CustomItems.carpentyBench).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      int meta = var1.getBlockMetadata();
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.4F, (float)var6 + 0.5F);
      GL11.glScalef(0.95F, 0.95F, 0.95F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * (meta % 4)), 0.0F, 1.0F, 0.0F);
      if(meta >= 4) {
         this.bindTexture(resource3);
         this.anvil.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.bindTexture(field_110631_g);
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.85F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if(metadata == 0) {
         this.bindTexture(field_110631_g);
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.bindTexture(resource3);
         this.anvil.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return CustomItems.carpentyBench.getRenderType();
   }

}
