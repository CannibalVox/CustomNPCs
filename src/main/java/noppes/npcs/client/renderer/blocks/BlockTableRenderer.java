package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockTable;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.client.model.blocks.ModelTable;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockTableRenderer extends BlockRendererInterface {

   private final ModelTable model = new ModelTable();
   private static final ResourceLocation resource1 = new ResourceLocation("customnpcs", "textures/cache/planks_oak.png");
   private static final ResourceLocation resource2 = new ResourceLocation("customnpcs", "textures/cache/planks_big_oak.png");
   private static final ResourceLocation resource3 = new ResourceLocation("customnpcs", "textures/cache/planks_spruce.png");
   private static final ResourceLocation resource4 = new ResourceLocation("customnpcs", "textures/cache/planks_birch.png");
   private static final ResourceLocation resource5 = new ResourceLocation("customnpcs", "textures/cache/planks_acacia.png");
   private static final ResourceLocation resource6 = new ResourceLocation("customnpcs", "textures/cache/planks_jungle.png");


   public BlockTableRenderer() {
      ((BlockTable)CustomItems.table).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileColorable tile = (TileColorable)var1;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      boolean south = var1.getWorld().getBlock(var1.xCoord + 1, var1.yCoord, var1.zCoord) == CustomItems.table;
      boolean north = var1.getWorld().getBlock(var1.xCoord - 1, var1.yCoord, var1.zCoord) == CustomItems.table;
      boolean east = var1.getWorld().getBlock(var1.xCoord, var1.yCoord, var1.zCoord + 1) == CustomItems.table;
      boolean west = var1.getWorld().getBlock(var1.xCoord, var1.yCoord, var1.zCoord - 1) == CustomItems.table;
      this.model.Shape1.showModel = !south && !east;
      this.model.Shape3.showModel = !north && !west;
      this.model.Shape4.showModel = !north && !east;
      this.model.Shape5.showModel = !south && !west;
      this.setWoodTexture(var1.getBlockMetadata());
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      this.model.Table.render(0.0625F);
      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.9F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      this.setWoodTexture(metadata);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.Table.render(0.0625F);
      this.model.Shape1.showModel = true;
      this.model.Shape3.showModel = true;
      this.model.Shape4.showModel = true;
      this.model.Shape5.showModel = true;
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.table.getRenderType();
   }

}
