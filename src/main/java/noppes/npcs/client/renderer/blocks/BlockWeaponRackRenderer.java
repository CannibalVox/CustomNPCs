package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockWeaponRack;
import noppes.npcs.blocks.tiles.TileWeaponRack;
import noppes.npcs.client.model.blocks.ModelWeaponRack;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import org.lwjgl.opengl.GL11;

public class BlockWeaponRackRenderer extends BlockRendererInterface {

   private final ModelWeaponRack model = new ModelWeaponRack();


   public BlockWeaponRackRenderer() {
      ((BlockWeaponRack)CustomItems.weaponsRack).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this);
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
      TileWeaponRack tile = (TileWeaponRack)var1;
      GL11.glDisable('\u803a');
      GL11.glEnable(3008);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.34F, (float)var6 + 0.5F);
      GL11.glScalef(0.9F, 0.9F, 0.9F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * tile.rotation), 0.0F, 1.0F, 0.0F);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.setWoodTexture(var1.getBlockMetadata());
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      if(!this.playerTooFar(tile)) {
         for(int i = 0; i < 3; ++i) {
            this.doRender(tile.getStackInSlot(i), i);
         }
      }

      GL11.glPopMatrix();
   }

   private void doRender(ItemStack item, int pos) {
      if(item != null && item.getItem() != null && !(item.getItem() instanceof ItemBlock)) {
         GL11.glPushMatrix();
         GL11.glTranslatef(-0.4F + (float)pos * 0.37F, 0.8F, 0.23F);
         GL11.glScalef(0.5F, 0.5F, 0.5F);
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
            float var8 = (float)(k >> 16 & 255) / 255.0F;
            f12 = (float)(k >> 8 & 255) / 255.0F;
            f4 = (float)(k & 255) / 255.0F;
            GL11.glColor4f(var8, f12, f4, 1.0F);
            RenderManager.instance.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, item, 0);
         }

         GL11.glPopMatrix();
      }
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(-0.3F, 0.15F, 0.0F);
      GL11.glScalef(0.9F, 0.7F, 0.9F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      this.setWoodTexture(metadata);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public int getRenderId() {
      return CustomItems.weaponsRack.getRenderType();
   }

   public int specialRenderDistance() {
      return 26;
   }
}
