package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockCouchWood;
import noppes.npcs.blocks.tiles.TileCouchWood;
import noppes.npcs.client.model.blocks.ModelCouchWoodLeft;
import noppes.npcs.client.model.blocks.ModelCouchWoodMiddle;
import noppes.npcs.client.model.blocks.ModelCouchWoodRight;
import noppes.npcs.client.model.blocks.ModelCouchWoodSingle;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockCouchWoodRenderer extends BlockRendererInterface {

   private final ModelBase model = new ModelCouchWoodMiddle();
   private final ModelBase modelLeft = new ModelCouchWoodLeft();
   private final ModelBase modelRight = new ModelCouchWoodRight();
   private final ModelBase modelCorner = new ModelCouchWoodSingle();


   public BlockCouchWoodRenderer() {
      ((BlockCouchWood)CustomItems.couchWood).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileCouchWood tile = (TileCouchWood)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.setWoodTexture(var1.getBlockMetadata());
      if(tile.hasLeft && tile.hasRight) {
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.hasLeft) {
         this.modelLeft.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.hasRight) {
         this.modelRight.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.modelCorner.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.9F, 0.1F);
      GL11.glScalef(0.9F, 0.9F, 0.9F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      this.setWoodTexture(metadata);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.modelCorner.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.couchWood.getRenderType();
   }
}
