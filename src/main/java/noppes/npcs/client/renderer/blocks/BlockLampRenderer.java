package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockLamp;
import noppes.npcs.blocks.tiles.TileLamp;
import noppes.npcs.client.model.blocks.ModelLamp;
import noppes.npcs.client.model.blocks.ModelLampCeiling;
import noppes.npcs.client.model.blocks.ModelLampWall;
import org.lwjgl.opengl.GL11;

public class BlockLampRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

   private final ModelLamp model = new ModelLamp();
   private final ModelLampCeiling model2 = new ModelLampCeiling();
   private final ModelLampWall model3 = new ModelLampWall();
   private static final ResourceLocation resource1 = new ResourceLocation("customnpcs", "textures/models/Lamp.png");


   public BlockLampRenderer() {
      ((BlockLamp)CustomItems.lamp).renderId = RenderingRegistry.getNextAvailableRenderId();
      ((BlockLamp)CustomItems.lamp_unlit).renderId = ((BlockLamp)CustomItems.lamp).renderId;
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileLamp tile = (TileLamp)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(45 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(resource1);
      if(tile.color == 0) {
         this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else if(tile.color == 1) {
         this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      } else {
         this.model3.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 2.2F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(resource1);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return CustomItems.lamp.getRenderType();
   }

}
