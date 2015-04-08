package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockCouchWool;
import noppes.npcs.blocks.tiles.TileCouchWool;
import noppes.npcs.client.model.blocks.ModelCouchCorner;
import noppes.npcs.client.model.blocks.ModelCouchCornerWool;
import noppes.npcs.client.model.blocks.ModelCouchLeft;
import noppes.npcs.client.model.blocks.ModelCouchLeftWool;
import noppes.npcs.client.model.blocks.ModelCouchMiddle;
import noppes.npcs.client.model.blocks.ModelCouchMiddleWool;
import noppes.npcs.client.model.blocks.ModelCouchRight;
import noppes.npcs.client.model.blocks.ModelCouchRightWool;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import noppes.npcs.client.renderer.blocks.BlockTallLampRenderer;
import org.lwjgl.opengl.GL11;

public class BlockCouchWoolRenderer extends BlockRendererInterface {

   private final ModelBase model = new ModelCouchMiddle();
   private final ModelBase model2 = new ModelCouchMiddleWool();
   private final ModelBase modelLeft = new ModelCouchLeft();
   private final ModelBase modelLeft2 = new ModelCouchLeftWool();
   private final ModelBase modelRight = new ModelCouchRight();
   private final ModelBase modelRight2 = new ModelCouchRightWool();
   private final ModelBase modelCorner = new ModelCouchCorner();
   private final ModelBase modelCorner2 = new ModelCouchCornerWool();


   public BlockCouchWoolRenderer() {
      ((BlockCouchWool)CustomItems.couchWool).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileCouchWool tile = (TileCouchWool)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.setWoodTexture(var1.getBlockMetadata());
      if(tile.hasCornerLeft) {
         this.modelCorner.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.hasCornerRight) {
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         this.modelCorner.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.hasLeft && tile.hasRight) {
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.hasLeft) {
         this.modelLeft.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.hasRight) {
         this.modelRight.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      this.bindTexture(BlockTallLampRenderer.resourceTop);
      float[] color = BlockRendererInterface.colorTable[tile.color];
      GL11.glColor3f(color[0], color[1], color[2]);
      if(!tile.hasCornerLeft && !tile.hasCornerRight) {
         if(tile.hasLeft && tile.hasRight) {
            this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         } else if(tile.hasLeft) {
            this.modelLeft2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         } else if(tile.hasRight) {
            this.modelRight2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         } else {
            this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         }
      } else {
         this.modelCorner2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.bindTexture(BlockTallLampRenderer.resourceTop);
      float[] color = BlockRendererInterface.colorTable[15 - metadata];
      GL11.glColor3f(color[0], color[1], color[2]);
      this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.couchWool.getRenderType();
   }
}
