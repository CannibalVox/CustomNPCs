package noppes.npcs.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import noppes.npcs.client.model.ModelNPCMale;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.items.ItemClaw;
import noppes.npcs.items.ItemShield;
import org.lwjgl.opengl.GL11;

public class RenderNPCHumanMale extends RenderNPCInterface {

   private ModelNPCMale modelBipedMain;
   protected ModelNPCMale modelArmorChestplate;
   protected ModelNPCMale modelArmor;


   public RenderNPCHumanMale(ModelNPCMale mainmodel, ModelNPCMale armorChest, ModelNPCMale armor) {
      super(mainmodel, 0.5F);
      this.modelBipedMain = mainmodel;
      this.modelArmorChestplate = armorChest;
      this.modelArmor = armor;
   }

   protected int func_130006_a(EntityLiving par1EntityLiving, int par2, float par3) {
      ItemStack itemstack = par1EntityLiving.func_130225_q(3 - par2);
      if(itemstack != null) {
         Item item = itemstack.getItem();
         if(item instanceof ItemArmor) {
            ItemArmor itemarmor = (ItemArmor)item;
            this.bindTexture(RenderBiped.getArmorResource(par1EntityLiving, itemstack, par2, (String)null));
            ModelNPCMale modelbiped = par2 == 2?this.modelArmor:this.modelArmorChestplate;
            modelbiped.bipedHead.showModel = par2 == 0;
            modelbiped.bipedHeadwear.showModel = par2 == 0;
            modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
            modelbiped.bipedRightArm.showModel = par2 == 1;
            modelbiped.bipedLeftArm.showModel = par2 == 1;
            modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
            modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
            ModelBiped modelbiped1 = ForgeHooksClient.getArmorModel(par1EntityLiving, itemstack, par2, modelbiped);
            this.setRenderPassModel(modelbiped1);
            modelbiped1.swingProgress = super.mainModel.swingProgress;
            modelbiped1.isRiding = super.mainModel.isRiding;
            modelbiped1.isChild = super.mainModel.isChild;
            float f1 = 1.0F;
            int j = itemarmor.getColor(itemstack);
            if(j != -1) {
               float f2 = (float)(j >> 16 & 255) / 255.0F;
               float f3 = (float)(j >> 8 & 255) / 255.0F;
               float f4 = (float)(j & 255) / 255.0F;
               GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);
               if(itemstack.isItemEnchanted()) {
                  return 31;
               }

               return 16;
            }

            GL11.glColor3f(f1, f1, f1);
            if(itemstack.isItemEnchanted()) {
               return 15;
            }

            return 1;
         }
      }

      return -1;
   }

   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.func_130006_a((EntityLiving)par1EntityLivingBase, par2, par3);
   }

   public void renderPlayer(EntityNPCInterface npc, double d, double d1, double d2, float f, float f1) {
      ItemStack itemstack = npc.getHeldItem();
      this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack == null?0:(npc.hurtResistantTime > 0?3:1);
      this.modelArmorChestplate.heldItemLeft = this.modelArmor.heldItemLeft = this.modelBipedMain.heldItemLeft = npc.getOffHand() == null?0:(npc.hurtResistantTime > 0?3:1);
      this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = npc.isSneaking();
      this.modelArmorChestplate.isSleeping = this.modelArmor.isSleeping = this.modelBipedMain.isSleeping = npc.isPlayerSleeping();
      this.modelArmorChestplate.isDancing = this.modelArmor.isDancing = this.modelBipedMain.isDancing = npc.currentAnimation == EnumAnimation.DANCING;
      this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = npc.currentAnimation == EnumAnimation.AIMING;
      this.modelArmorChestplate.isRiding = this.modelArmor.isRiding = this.modelBipedMain.isRiding = npc.isRiding();
      double d3 = d1 - (double)npc.yOffset;
      if(npc.isSneaking()) {
         d3 -= 0.125D;
      }

      super.doRender(npc, d, d3, d2, f, f1);
      this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
      this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
      this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
      this.modelArmorChestplate.heldItemLeft = this.modelArmor.heldItemLeft = this.modelBipedMain.heldItemLeft = 0;
   }

   protected void renderSpecials(EntityNPCInterface npc, float f) {
      super.renderEquippedItems(npc, f);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      int i = npc.getBrightnessForRender(f);
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
      float var10;
      if(!npc.display.cloakTexture.isEmpty()) {
         if(npc.textureCloakLocation == null) {
            npc.textureCloakLocation = new ResourceLocation(npc.display.cloakTexture);
         }

         this.bindTexture(npc.textureCloakLocation);
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.0F, 0.125F);
         double itemstack = npc.field_20066_r + (npc.field_20063_u - npc.field_20066_r) * (double)f - (npc.prevPosX + (npc.posX - npc.prevPosX) * (double)f);
         double y = npc.field_20065_s + (npc.field_20062_v - npc.field_20065_s) * (double)f - (npc.prevPosY + (npc.posY - npc.prevPosY) * (double)f);
         double var6 = npc.field_20064_t + (npc.field_20061_w - npc.field_20064_t) * (double)f - (npc.prevPosZ + (npc.posZ - npc.prevPosZ) * (double)f);
         float is3D = npc.prevRenderYawOffset + (npc.renderYawOffset - npc.prevRenderYawOffset) * f;
         double var25 = (double)MathHelper.sin(is3D * 3.141593F / 180.0F);
         double var26 = (double)(-MathHelper.cos(is3D * 3.141593F / 180.0F));
         var10 = (float)(itemstack * var25 + var6 * var26) * 100.0F;
         float f15 = (float)(itemstack * var26 - var6 * var25) * 100.0F;
         if(var10 < 0.0F) {
            var10 = 0.0F;
         }

         float var10000 = npc.prevRotationYaw + (npc.rotationYaw - npc.prevRotationYaw) * f;
         float f13 = 5.0F;
         if(npc.isSneaking()) {
            f13 += 25.0F;
         }

         GL11.glRotatef(6.0F + var10 / 2.0F + f13, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(f15 / 2.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-f15 / 2.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         this.modelBipedMain.renderCloak(0.0625F);
         GL11.glPopMatrix();
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack var21 = npc.inventory.armorItemInSlot(0);
      float x;
      if(var21 != null) {
         GL11.glPushMatrix();
         if(npc instanceof EntityCustomNpc) {
            EntityCustomNpc itemstack2 = (EntityCustomNpc)npc;
            GL11.glTranslatef(0.0F, itemstack2.modelData.getBodyY(), 0.0F);
            this.modelBipedMain.bipedHead.postRender(0.0625F);
            GL11.glScalef(itemstack2.modelData.head.scaleX, itemstack2.modelData.head.scaleY, itemstack2.modelData.head.scaleZ);
         } else {
            this.modelBipedMain.bipedHead.postRender(0.0625F);
         }

         IItemRenderer var22 = MinecraftForgeClient.getItemRenderer(var21, ItemRenderType.EQUIPPED);
         boolean var241 = var22 != null && var22.shouldUseRenderHelper(ItemRenderType.EQUIPPED, var21, ItemRendererHelper.BLOCK_3D);
         if(var21.getItem() instanceof ItemBlock) {
            if(var241 || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var21.getItem()).getRenderType())) {
               x = 0.625F;
               GL11.glTranslatef(0.0F, -0.25F, 0.0F);
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(x, -x, -x);
            }

            super.renderManager.itemRenderer.renderItem(npc, var21, 0);
         }

         GL11.glPopMatrix();
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack var23 = npc.getHeldItem();
      int var24;
      float var9;
      float var251;
      float var261;
      IItemRenderer var28;
      boolean var29;
      int var30;
      float var31;
      if(var23 != null) {
         GL11.glPushMatrix();
         x = 0.0F;
         var261 = 0.0F;
         if(npc instanceof EntityCustomNpc) {
            EntityCustomNpc customRenderer = (EntityCustomNpc)npc;
            x = (customRenderer.modelData.arms.scaleY - 1.0F) * 0.7F;
            var261 = (1.0F - customRenderer.modelData.body.scaleX) * 0.28F + (1.0F - customRenderer.modelData.arms.scaleX) * 0.075F;
            GL11.glTranslatef(var261, customRenderer.modelData.getBodyY(), 0.0F);
         }

         this.modelBipedMain.bipedRightArm.postRender(0.0625F);
         GL11.glTranslatef(-0.0625F, 0.4375F + x, 0.0625F);
         var28 = MinecraftForgeClient.getItemRenderer(var23, ItemRenderType.EQUIPPED);
         var29 = var28 != null && var28.shouldUseRenderHelper(ItemRenderType.EQUIPPED, var23, ItemRendererHelper.BLOCK_3D);
         if(var23.getItem() instanceof ItemBlock && (var29 || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var23.getItem()).getRenderType()))) {
            var251 = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            var251 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-var251, -var251, var251);
         } else if(var23.getItem() == Items.bow) {
            var251 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(var251, -var251, var251);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if(var23.getItem().isFull3D()) {
            var251 = 0.625F;
            if(var23.getItem().shouldRotateAroundWhenRendering()) {
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -0.125F, 0.0F);
            }

            if(npc.hurtResistantTime > 0 && npc.stats.resistances.playermelee > 1.0F) {
               GL11.glTranslatef(0.05F, 0.0F, -0.1F);
               GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
            GL11.glScalef(var251, -var251, var251);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            var251 = 0.375F;
            GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
            GL11.glScalef(var251, var251, var251);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
         }

         if(var23.getItem().requiresMultipleRenderPasses()) {
            for(var30 = 0; var30 < var23.getItem().getRenderPasses(var23.getMetadata()); ++var30) {
               var24 = var23.getItem().getColorFromItemStack(var23, var30);
               var31 = (float)(var24 >> 16 & 255) / 255.0F;
               var9 = (float)(var24 >> 8 & 255) / 255.0F;
               var10 = (float)(var24 & 255) / 255.0F;
               GL11.glColor4f(var31, var9, var10, 1.0F);
               super.renderManager.itemRenderer.renderItem(npc, var23, var30);
            }
         } else {
            super.renderManager.itemRenderer.renderItem(npc, var23, 0);
         }

         GL11.glPopMatrix();
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      var23 = npc.getOffHand();
      if(var23 != null) {
         GL11.glPushMatrix();
         var251 = 0.0F;
         x = 0.0F;
         if(npc instanceof EntityCustomNpc) {
            EntityCustomNpc var27 = (EntityCustomNpc)npc;
            var251 = (var27.modelData.arms.scaleY - 1.0F) * 0.7F;
            x = (1.0F - var27.modelData.body.scaleX) * 0.28F + (1.0F - var27.modelData.arms.scaleX) * 0.075F;
            GL11.glTranslatef(x, var27.modelData.getBodyY(), 0.0F);
         }

         this.modelBipedMain.bipedLeftArm.postRender(0.0625F);
         GL11.glTranslatef(0.0625F, 0.4375F + var251, 0.0625F);
         var28 = MinecraftForgeClient.getItemRenderer(var23, ItemRenderType.EQUIPPED);
         var29 = var28 != null && var28.shouldUseRenderHelper(ItemRenderType.EQUIPPED, var23, ItemRendererHelper.BLOCK_3D);
         if(var23.getItem() instanceof ItemShield || var23.getItem() instanceof ItemClaw) {
            GL11.glTranslatef(0.3F, 0.0F, 0.0F);
         }

         if(var23.getItem() instanceof ItemBlock && (var29 || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var23.getItem()).getRenderType()))) {
            var261 = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            var261 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(var261, -var261, var261);
         } else if(var23.getItem() == Items.bow) {
            var261 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(var261, -var261, var261);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if(var23.getItem().isFull3D()) {
            var261 = 0.625F;
            if(var23.getItem().shouldRotateAroundWhenRendering()) {
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -0.125F, 0.0F);
            }

            if(npc.hurtResistantTime > 0 && npc.stats.resistances.arrow > 1.0F) {
               GL11.glTranslatef(0.05F, 0.0F, -0.1F);
               GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
            GL11.glScalef(var261, -var261, var261);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            var261 = 0.375F;
            GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
            GL11.glScalef(var261, var261, var261);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
         }

         if(var23.getItem().requiresMultipleRenderPasses()) {
            for(var30 = 0; var30 < var23.getItem().getRenderPasses(var23.getMetadata()); ++var30) {
               var24 = var23.getItem().getColorFromItemStack(var23, var30);
               var31 = (float)(var24 >> 16 & 255) / 255.0F;
               var9 = (float)(var24 >> 8 & 255) / 255.0F;
               var10 = (float)(var24 & 255) / 255.0F;
               GL11.glColor4f(var31, var9, var10, 1.0F);
               super.renderManager.itemRenderer.renderItem(npc, var23, var30);
            }
         } else {
            super.renderManager.itemRenderer.renderItem(npc, var23, 0);
         }

         GL11.glPopMatrix();
      }

   }

   protected void func_77029_c(EntityLivingBase entityliving, float f) {
      this.renderSpecials((EntityNPCInterface)entityliving, f);
   }

   public void func_76986_a(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      this.renderPlayer((EntityNPCInterface)entityliving, d, d1, d2, f, f1);
   }

   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.renderPlayer((EntityNPCInterface)entity, d, d1, d2, f, f1);
   }
}
