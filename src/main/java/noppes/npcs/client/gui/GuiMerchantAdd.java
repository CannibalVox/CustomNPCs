package noppes.npcs.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import noppes.npcs.ServerEventsHandler;
import noppes.npcs.client.Client;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.containers.ContainerMerchantAdd;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMerchantAdd extends GuiContainer {

   private static final ResourceLocation merchantGuiTextures = new ResourceLocation("textures/gui/container/villager.png");
   private IMerchant theIMerchant;
   private GuiMerchantAdd.MerchantButton nextRecipeButtonIndex;
   private GuiMerchantAdd.MerchantButton previousRecipeButtonIndex;
   private int currentRecipeIndex;
   private String field_94082_v;


   public GuiMerchantAdd() {
      super(new ContainerMerchantAdd(Minecraft.getMinecraft().thePlayer, ServerEventsHandler.Merchant, Minecraft.getMinecraft().theWorld));
      this.theIMerchant = ServerEventsHandler.Merchant;
      this.field_94082_v = I18n.format("entity.Villager.name", new Object[0]);
   }

   public void initGui() {
      super.initGui();
      int i = (super.width - super.xSize) / 2;
      int j = (super.height - super.ySize) / 2;
      super.buttonList.add(this.nextRecipeButtonIndex = new GuiMerchantAdd.MerchantButton(1, i + 120 + 27, j + 24 - 1, true));
      super.buttonList.add(this.previousRecipeButtonIndex = new GuiMerchantAdd.MerchantButton(2, i + 36 - 19, j + 24 - 1, false));
      super.buttonList.add(new GuiNpcButton(4, i + super.xSize, j + 20, 60, 20, "gui.remove"));
      super.buttonList.add(new GuiNpcButton(5, i + super.xSize, j + 50, 60, 20, "gui.add"));
      this.nextRecipeButtonIndex.enabled = false;
      this.previousRecipeButtonIndex.enabled = false;
   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
      super.fontRendererObj.drawString(this.field_94082_v, super.xSize / 2 - super.fontRendererObj.getStringWidth(this.field_94082_v) / 2, 6, CustomNpcResourceListener.DefaultTextColor);
      super.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, super.ySize - 96 + 2, CustomNpcResourceListener.DefaultTextColor);
   }

   public void updateScreen() {
      super.updateScreen();
      Minecraft mc = Minecraft.getMinecraft();
      MerchantRecipeList merchantrecipelist = this.theIMerchant.getRecipes(mc.thePlayer);
      if(merchantrecipelist != null) {
         this.nextRecipeButtonIndex.enabled = this.currentRecipeIndex < merchantrecipelist.size() - 1;
         this.previousRecipeButtonIndex.enabled = this.currentRecipeIndex > 0;
      }

   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      boolean flag = false;
      Minecraft mc = Minecraft.getMinecraft();
      if(par1GuiButton == this.nextRecipeButtonIndex) {
         ++this.currentRecipeIndex;
         flag = true;
      } else if(par1GuiButton == this.previousRecipeButtonIndex) {
         --this.currentRecipeIndex;
         flag = true;
      }

      if(par1GuiButton.id == 4) {
         MerchantRecipeList bytebuf = this.theIMerchant.getRecipes(mc.thePlayer);
         if(this.currentRecipeIndex < bytebuf.size()) {
            bytebuf.remove(this.currentRecipeIndex);
            if(this.currentRecipeIndex > 0) {
               --this.currentRecipeIndex;
            }

            Client.sendData(EnumPacketServer.MerchantUpdate, new Object[]{Integer.valueOf(ServerEventsHandler.Merchant.getEntityId()), bytebuf});
         }
      }

      if(par1GuiButton.id == 5) {
         ItemStack var14 = super.inventorySlots.getSlot(0).getStack();
         ItemStack exception = super.inventorySlots.getSlot(1).getStack();
         ItemStack sold = super.inventorySlots.getSlot(2).getStack();
         if(var14 == null && exception != null) {
            var14 = exception;
            exception = null;
         }

         if(var14 != null && sold != null) {
            var14 = var14.copy();
            sold = sold.copy();
            if(exception != null) {
               exception = exception.copy();
            }

            MerchantRecipe recipe = new MerchantRecipe(var14, exception, sold);
            recipe.func_82783_a(2147483639);
            MerchantRecipeList merchantrecipelist = this.theIMerchant.getRecipes(mc.thePlayer);
            merchantrecipelist.add(recipe);
            Client.sendData(EnumPacketServer.MerchantUpdate, new Object[]{Integer.valueOf(ServerEventsHandler.Merchant.getEntityId()), merchantrecipelist});
         }
      }

      if(flag) {
         ((ContainerMerchantAdd)super.inventorySlots).setCurrentRecipeIndex(this.currentRecipeIndex);
         ByteBuf var15 = Unpooled.buffer();

         try {
            var15.writeInt(this.currentRecipeIndex);
            super.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", var15));
         } catch (Exception var12) {
            ;
         } finally {
            var15.release();
         }
      }

   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      Minecraft mc = Minecraft.getMinecraft();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.getTextureManager().bindTexture(merchantGuiTextures);
      int k = (super.width - super.xSize) / 2;
      int l = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(k, l, 0, 0, super.xSize, super.ySize);
      MerchantRecipeList merchantrecipelist = this.theIMerchant.getRecipes(mc.thePlayer);
      if(merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
         int i1 = this.currentRecipeIndex;
         MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(i1);
         if(merchantrecipe.isRecipeDisabled()) {
            mc.getTextureManager().bindTexture(merchantGuiTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            this.drawTexturedModalRect(super.guiLeft + 83, super.guiTop + 21, 212, 0, 28, 21);
            this.drawTexturedModalRect(super.guiLeft + 83, super.guiTop + 51, 212, 0, 28, 21);
         }
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      Minecraft mc = Minecraft.getMinecraft();
      MerchantRecipeList merchantrecipelist = this.theIMerchant.getRecipes(mc.thePlayer);
      if(merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
         int k = (super.width - super.xSize) / 2;
         int l = (super.height - super.ySize) / 2;
         int i1 = this.currentRecipeIndex;
         MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(i1);
         GL11.glPushMatrix();
         ItemStack itemstack = merchantrecipe.getItemToBuy();
         ItemStack itemstack1 = merchantrecipe.getSecondItemToBuy();
         ItemStack itemstack2 = merchantrecipe.getItemToSell();
         RenderHelper.enableGUIStandardItemLighting();
         GL11.glDisable(2896);
         GL11.glEnable('\u803a');
         GL11.glEnable(2903);
         GL11.glEnable(2896);
         GuiScreen.itemRender.zLevel = 100.0F;
         GuiScreen.itemRender.renderItemAndEffectIntoGUI(super.fontRendererObj, mc.getTextureManager(), itemstack, k + 36, l + 24);
         GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, mc.getTextureManager(), itemstack, k + 36, l + 24);
         if(itemstack1 != null) {
            GuiScreen.itemRender.renderItemAndEffectIntoGUI(super.fontRendererObj, mc.getTextureManager(), itemstack1, k + 62, l + 24);
            GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, mc.getTextureManager(), itemstack1, k + 62, l + 24);
         }

         GuiScreen.itemRender.renderItemAndEffectIntoGUI(super.fontRendererObj, mc.getTextureManager(), itemstack2, k + 120, l + 24);
         GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, mc.getTextureManager(), itemstack2, k + 120, l + 24);
         GuiScreen.itemRender.zLevel = 0.0F;
         GL11.glDisable(2896);
         if(this.isPointInRegion(36, 24, 16, 16, par1, par2)) {
            this.renderToolTip(itemstack, par1, par2);
         } else if(itemstack1 != null && this.isPointInRegion(62, 24, 16, 16, par1, par2)) {
            this.renderToolTip(itemstack1, par1, par2);
         } else if(this.isPointInRegion(120, 24, 16, 16, par1, par2)) {
            this.renderToolTip(itemstack2, par1, par2);
         }

         GL11.glPopMatrix();
         GL11.glEnable(2896);
         GL11.glEnable(2929);
         RenderHelper.enableStandardItemLighting();
      }

   }

   public IMerchant getIMerchant() {
      return this.theIMerchant;
   }

   static ResourceLocation func_110417_h() {
      return merchantGuiTextures;
   }


   @SideOnly(Side.CLIENT)
   static class MerchantButton extends GuiButton {

      private final boolean field_146157_o;
      private static final String __OBFID = "CL_00000763";


      public MerchantButton(int par1, int par2, int par3, boolean par4) {
         super(par1, par2, par3, 12, 19, "");
         this.field_146157_o = par4;
      }

      public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
         if(super.visible) {
            p_146112_1_.getTextureManager().bindTexture(GuiMerchantAdd.merchantGuiTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = p_146112_2_ >= super.xPosition && p_146112_3_ >= super.yPosition && p_146112_2_ < super.xPosition + super.width && p_146112_3_ < super.yPosition + super.height;
            int k = 0;
            int l = 176;
            if(!super.enabled) {
               l += super.width * 2;
            } else if(flag) {
               l += super.width;
            }

            if(!this.field_146157_o) {
               k += super.height;
            }

            this.drawTexturedModalRect(super.xPosition, super.yPosition, l, k, super.width, super.height);
         }

      }
   }
}
