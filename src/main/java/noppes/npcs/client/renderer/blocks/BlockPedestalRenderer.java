package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TilePedestal;
import noppes.npcs.client.model.blocks.ModelPedestal;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockPedestalRenderer extends BlockRendererInterface {

   private final ModelPedestal model = new ModelPedestal();
   private static final ResourceLocation resource = new ResourceLocation("customnpcs:textures/models/npcPedestal.png");


   public BlockPedestalRenderer() {
      ((BlockRotated)CustomItems.pedestal).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TilePedestal tile = (TilePedestal)var1;
      GL11.glDisable('\u803a');
      GL11.glEnable(3008);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      setMaterialTexture(var1.getBlockMetadata());
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glScalef(1.0F, 0.99F, 1.0F);
      TextureManager manager = Minecraft.getMinecraft().getTextureManager();
      manager.bindTexture(resource);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      if(!this.playerTooFar(tile)) {
         this.doRender(tile.getStackInSlot(0));
      }

      GL11.glPopMatrix();
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
   }

   private void doRender(ItemStack item) {
      if(item != null && item.getItem() != null && !(item.getItem() instanceof ItemBlock)) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.06F, 0.3F, 0.02F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.6F, 0.6F, 0.6F);
         if(item.getItem().shouldRotateAroundWhenRendering()) {
            GL11.glTranslatef(0.14F, 0.0F, 0.5F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         }

         GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
         int k;
         float f12;
         float f4;
         if(item.getItem().requiresMultipleRenderPasses()) {
            for(k = 0; k <= item.getItem().getRenderPasses(item.getMetadata()); ++k) {
               int f11 = item.getItem().getColorFromItemStack(item, k);
               f12 = (float)(f11 >> 16 & 255) / 255.0F;
               f4 = (float)(f11 >> 8 & 255) / 255.0F;
               float f5 = (float)(f11 & 255) / 255.0F;
               GL11.glColor4f(f12, f4, f5, 1.0F);
               RenderManager.instance.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, item, k);
            }
         } else {
            k = item.getItem().getColorFromItemStack(item, 0);
            float var7 = (float)(k >> 16 & 255) / 255.0F;
            f12 = (float)(k >> 8 & 255) / 255.0F;
            f4 = (float)(k & 255) / 255.0F;
            GL11.glColor4f(var7, f12, f4, 1.0F);
            RenderManager.instance.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, item, 0);
         }

         GL11.glPopMatrix();
      }
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.44F, 0.0F);
      GL11.glScalef(0.76F, 0.66F, 0.76F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      setMaterialTexture(metadata);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.pedestal.getRenderType();
   }

   public int specialRenderDistance() {
      return 40;
   }

}
