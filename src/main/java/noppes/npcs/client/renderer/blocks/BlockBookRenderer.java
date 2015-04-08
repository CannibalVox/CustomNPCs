package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.client.model.blocks.ModelInk;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockBookRenderer extends BlockRendererInterface {

   private final ModelInk ink = new ModelInk();
   private final ResourceLocation resource = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private final ResourceLocation resource2 = new ResourceLocation("customnpcs:textures/models/Ink.png");
   private final ModelBook book = new ModelBook();


   public BlockBookRenderer() {
      ((BlockRotated)CustomItems.book).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileColorable tile = (TileColorable)var1;
      GL11.glDisable('\u803a');
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation - 90), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      TextureManager manager = Minecraft.getMinecraft().getTextureManager();
      manager.bindTexture(this.resource2);
      if(!this.playerTooFar(tile)) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
      }

      this.ink.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      manager.bindTexture(this.resource);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-1.49F, -0.18F, 0.0F);
      this.book.render((Entity)null, 0.0F, 0.0F, 1.0F, 1.24F, 1.0F, 0.0625F);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.2F, 1.7F, 0.0F);
      GL11.glScalef(1.4F, 1.4F, 1.4F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glEnable(2884);
      TextureManager manager = Minecraft.getMinecraft().getTextureManager();
      manager.bindTexture(this.resource2);
      this.ink.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      manager.bindTexture(this.resource);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-1.45F, -0.18F, 0.0F);
      this.book.render((Entity)null, 0.0F, 0.0F, 1.0F, 1.24F, 1.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.book.getRenderType();
   }
}
