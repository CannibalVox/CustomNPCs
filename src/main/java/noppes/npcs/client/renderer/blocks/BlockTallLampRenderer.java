package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockTallLamp;
import noppes.npcs.blocks.tiles.TileTallLamp;
import noppes.npcs.client.model.blocks.ModelTallLamp;
import noppes.npcs.client.model.blocks.ModelTallLampTop;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockTallLampRenderer extends BlockRendererInterface {

   private final ModelTallLamp model = new ModelTallLamp();
   private final ModelTallLampTop top = new ModelTallLampTop();
   public static final ResourceLocation resourceTop = new ResourceLocation("customnpcs", "textures/cache/wool_colored_white.png");


   public BlockTallLampRenderer() {
      ((BlockTallLamp)CustomItems.tallLamp).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileTallLamp tile = (TileTallLamp)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      setMaterialTexture(var1.getBlockMetadata());
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.bindTexture(resourceTop);
      float[] color = BlockRendererInterface.colorTable[tile.color];
      GL11.glColor3f(color[0], color[1], color[2]);
      this.top.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.42F, 0.0F);
      GL11.glScalef(0.76F, 0.66F, 0.76F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      setMaterialTexture(metadata);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.bindTexture(resourceTop);
      float[] color = BlockRendererInterface.colorTable[15 - metadata];
      GL11.glColor3f(color[0], color[1], color[2]);
      this.top.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.tallLamp.getRenderType();
   }

}
