package noppes.npcs.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderProjectile extends Render {

   public boolean renderWithColor = true;
   private static final ResourceLocation field_110780_a = new ResourceLocation("textures/entity/arrow.png");
   private static final ResourceLocation field_110798_h = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private RenderBlocks itemRenderBlocks = new RenderBlocks();


   public void doRenderProjectile(EntityProjectile par1EntityProjectile, double par2, double par4, double par6, float par8, float par9) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)par2, (float)par4, (float)par6);
      GL11.glEnable('\u803a');
      float f = (float)par1EntityProjectile.getDataWatcher().getWatchableObjectInt(23) / 10.0F;
      ItemStack item = par1EntityProjectile.getItemDisplay();
      GL11.glScalef(f, f, f);
      Tessellator tessellator = Tessellator.instance;
      float var12;
      float var13;
      float var14;
      float var15;
      float f6;
      float f9;
      if(par1EntityProjectile.isArrow()) {
         this.bindEntityTexture(par1EntityProjectile);
         GL11.glRotatef(par1EntityProjectile.prevRotationYaw + (par1EntityProjectile.rotationYaw - par1EntityProjectile.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(par1EntityProjectile.prevRotationPitch + (par1EntityProjectile.rotationPitch - par1EntityProjectile.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
         byte icon = 0;
         var12 = 0.0F;
         var13 = 0.5F;
         var14 = (float)(0 + icon * 10) / 32.0F;
         var15 = (float)(5 + icon * 10) / 32.0F;
         f6 = 0.0F;
         f9 = 0.15625F;
         float f8 = (float)(5 + icon * 10) / 32.0F;
         float f91 = (float)(10 + icon * 10) / 32.0F;
         float f10 = 0.05625F;
         GL11.glEnable('\u803a');
         float f11 = (float)par1EntityProjectile.arrowShake - par9;
         if(f11 > 0.0F) {
            float i = -MathHelper.sin(f11 * 3.0F) * f11;
            GL11.glRotatef(i, 0.0F, 0.0F, 1.0F);
         }

         GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(f10, f10, f10);
         GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
         GL11.glNormal3f(f10, 0.0F, 0.0F);
         tessellator.startDrawingQuads();
         tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f8);
         tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f9, (double)f8);
         tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f9, (double)f91);
         tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f91);
         tessellator.draw();
         GL11.glNormal3f(-f10, 0.0F, 0.0F);
         tessellator.startDrawingQuads();
         tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f8);
         tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f9, (double)f8);
         tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f9, (double)f91);
         tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f91);
         tessellator.draw();

         for(int var34 = 0; var34 < 4; ++var34) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)var12, (double)var14);
            tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)var13, (double)var14);
            tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)var13, (double)var15);
            tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)var12, (double)var15);
            tessellator.draw();
         }
      } else {
         int var29;
         if(par1EntityProjectile.is3D()) {
            GL11.glRotatef(par1EntityProjectile.prevRotationYaw + (par1EntityProjectile.rotationYaw - par1EntityProjectile.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(par1EntityProjectile.prevRotationPitch + (par1EntityProjectile.rotationPitch - par1EntityProjectile.prevRotationPitch) * par9 - 180.0F, 0.0F, 0.0F, 1.0F);
            if(item.getItemSpriteNumber() == 0 && item.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item.getItem()).getRenderType())) {
               Block var26 = Block.getBlockFromItem(item.getItem());
               this.bindTexture(TextureMap.locationBlocksTexture);
               var12 = 0.25F;
               var29 = var26.getRenderType();
               if(var29 == 1 || var29 == 19 || var29 == 12 || var29 == 2) {
                  var12 = 0.5F;
               }

               var14 = 1.0F;
               this.itemRenderBlocks.renderBlockAsItem(var26, item.getMetadata(), 1.0F);
            } else {
               GL11.glTranslatef(-0.6F, -0.6F, 0.0F);
               float var25;
               if(item.getItem().requiresMultipleRenderPasses()) {
                  for(int var30 = 0; var30 < item.getItem().getRenderPasses(item.getMetadata()); ++var30) {
                     item.getItem().getIcon(item, var30);
                     var14 = 1.0F;
                     if(this.renderWithColor) {
                        int var33 = item.getItem().getColorFromItemStack(item, var30);
                        var12 = (float)(var33 >> 16 & 255) / 255.0F;
                        var25 = (float)(var33 >> 8 & 255) / 255.0F;
                        var13 = (float)(var33 & 255) / 255.0F;
                        GL11.glColor4f(var12 * var14, var25 * var14, var13 * var14, 1.0F);
                        super.renderManager.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, item, 0);
                     } else {
                        super.renderManager.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, item, 0);
                     }
                  }
               } else {
                  IIcon var31 = item.getIconIndex();
                  if(this.renderWithColor) {
                     int var32 = item.getItem().getColorFromItemStack(item, 0);
                     var14 = (float)(var32 >> 16 & 255) / 255.0F;
                     f9 = (float)(var32 >> 8 & 255) / 255.0F;
                     var12 = (float)(var32 & 255) / 255.0F;
                     var25 = 1.0F;
                     this.renderDroppedItem(item, var31, par9, var14 * var25, f9 * var25, var12 * var25, f);
                  } else {
                     this.renderDroppedItem(item, var31, par9, 1.0F, 1.0F, 1.0F, f);
                  }
               }
            }
         } else {
            IIcon var27 = item.getItem().getIconFromDamage(item.getMetadata());
            this.bindTexture(TextureMap.locationItemsTexture);
            int var28;
            if(item.getItem().requiresMultipleRenderPasses()) {
               for(var28 = 0; var28 < item.getItem().getRenderPasses(item.getMetadata()); ++var28) {
                  var29 = item.getItem().getColorFromItemStack(item, var28);
                  var14 = (float)(var29 >> 16 & 255) / 255.0F;
                  var15 = (float)(var29 >> 8 & 255) / 255.0F;
                  f6 = (float)(var29 & 255) / 255.0F;
                  GL11.glColor4f(var14, var15, f6, 1.0F);
               }
            }

            if(var27 == ItemPotion.func_94589_d("bottle_splash") || var27 == ItemPotion.func_94589_d("bottle_drinkable")) {
               var28 = PotionHelper.func_77915_a(item.getMetadata(), false);
               var13 = (float)(var28 >> 16 & 255) / 255.0F;
               var14 = (float)(var28 >> 8 & 255) / 255.0F;
               var15 = (float)(var28 & 255) / 255.0F;
               GL11.glColor3f(var13, var14, var15);
               GL11.glPushMatrix();
               this.renderSprite(tessellator, ItemPotion.func_94589_d("overlay"));
               GL11.glPopMatrix();
               GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }

            this.renderSprite(tessellator, var27);
         }
      }

      if(par1EntityProjectile.is3D() && par1EntityProjectile.glows()) {
         GL11.glDisable(2896);
      }

      GL11.glDisable('\u803a');
      GL11.glPopMatrix();
      GL11.glEnable(2896);
   }

   private void renderSprite(Tessellator par1Tessellator, IIcon par2Icon) {
      float f = par2Icon.getMinU();
      float f1 = par2Icon.getMaxU();
      float f2 = par2Icon.getMinV();
      float f3 = par2Icon.getMaxV();
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      GL11.glRotatef(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      par1Tessellator.startDrawingQuads();
      par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
      par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
      par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
      par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
      par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
      par1Tessellator.draw();
   }

   private void renderDroppedItem(ItemStack item, IIcon par2Icon, float par4, float par5, float par6, float par7, float par8) {
      Tessellator tessellator = Tessellator.instance;
      if(par2Icon == null) {
         TextureManager f4 = Minecraft.getMinecraft().getTextureManager();
         ResourceLocation f5 = f4.getResourceLocation(item.getItemSpriteNumber());
         par2Icon = ((TextureMap)f4.getTexture(f5)).registerIcon("missingno");
      }

      float f41 = par2Icon.getMinU();
      float f51 = par2Icon.getMaxU();
      float f6 = par2Icon.getMinV();
      float f7 = par2Icon.getMaxV();
      float f8 = 1.0F;
      float f9 = 0.5F;
      float f10 = 0.25F;
      float f12 = 0.0625F;
      if(item.getItemSpriteNumber() == 0) {
         this.bindTexture(TextureMap.locationBlocksTexture);
      } else {
         this.bindTexture(TextureMap.locationItemsTexture);
      }

      GL11.glColor4f(par5, par6, par7, 1.0F);
      ItemRenderer.renderItemIn2D(tessellator, f51, f6, f41, f7, par2Icon.getIconWidth(), par2Icon.getIconHeight(), f12);
      if(item != null && item.hasEffect(0)) {
         GL11.glDepthFunc(514);
         GL11.glDisable(2896);
         super.renderManager.renderEngine.bindTexture(field_110798_h);
         GL11.glEnable(3042);
         GL11.glBlendFunc(768, 1);
         float f13 = 0.76F;
         GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
         GL11.glMatrixMode(5890);
         GL11.glPushMatrix();
         GL11.glScalef(par8, par8, par8);
         float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
         GL11.glTranslatef(f15, 0.0F, 0.0F);
         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
         ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(par8, par8, par8);
         f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
         GL11.glTranslatef(-f15, 0.0F, 0.0F);
         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
         ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glDepthFunc(515);
      }

   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.doRenderProjectile((EntityProjectile)par1Entity, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation func_110779_a(EntityProjectile par1EntityProjectile) {
      return par1EntityProjectile.isArrow()?field_110780_a:super.renderManager.renderEngine.getResourceLocation(par1EntityProjectile.getItemDisplay().getItemSpriteNumber());
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.func_110779_a((EntityProjectile)par1Entity);
   }

}
