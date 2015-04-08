package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockBanner;
import noppes.npcs.blocks.tiles.TileBanner;
import noppes.npcs.client.model.blocks.ModelBanner;
import noppes.npcs.client.model.blocks.ModelBannerFlag;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockBannerRenderer extends BlockRendererInterface {

   private final ModelBanner model = new ModelBanner();
   private final ModelBannerFlag flag = new ModelBannerFlag();
   public static final ResourceLocation resourceFlag = new ResourceLocation("customnpcs", "textures/models/BannerFlag.png");


   public BlockBannerRenderer() {
      ((BlockBanner)CustomItems.banner).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileBanner tile = (TileBanner)var1;
      GL11.glDisable('\u803a');
      GL11.glEnable(3008);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      setMaterialTexture(var1.getBlockMetadata());
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.bindTexture(resourceFlag);
      float[] color = BlockRendererInterface.colorTable[tile.color];
      GL11.glColor3f(color[0], color[1], color[2]);
      this.flag.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if(tile.icon != null && !this.playerTooFar(tile)) {
         this.doRender(var2, var4, var6, tile.rotation, tile.icon);
      }

   }

   public void doRender(double par2, double par4, double par6, int meta, ItemStack iicon) {
      if(iicon.getItemSpriteNumber() != 0 || !RenderBlocks.renderItemIn3d(Block.getBlockFromItem(iicon.getItem()).getRenderType())) {
         GL11.glPushMatrix();
         this.bindTexture(TextureMap.locationItemsTexture);
         GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 1.3F, (float)par6 + 0.5F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef((float)(90 * meta), 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, 0.0F, -0.14F);
         GL11.glDepthMask(false);
         float f2 = 0.05F;
         Minecraft mc = Minecraft.getMinecraft();
         GL11.glScalef(f2, f2, f2);
         BlockRendererInterface.renderer.renderItemIntoGUI(this.func_147498_b(), mc.renderEngine, iicon, -8, -8);
         GL11.glDepthMask(true);
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
      this.bindTexture(resourceFlag);
      float[] color = BlockRendererInterface.colorTable[15 - metadata];
      GL11.glColor3f(color[0], color[1], color[2]);
      this.flag.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.banner.getRenderType();
   }

   public int specialRenderDistance() {
      return 26;
   }

}
