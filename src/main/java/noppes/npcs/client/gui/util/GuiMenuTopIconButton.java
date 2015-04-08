package noppes.npcs.client.gui.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.IButtonListener;
import org.lwjgl.opengl.GL11;

public class GuiMenuTopIconButton extends GuiMenuTopButton {

   private static final ResourceLocation resource = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   protected static RenderItem itemRender = new RenderItem();
   private ItemStack item;


   public GuiMenuTopIconButton(int i, int x, int y, String s, ItemStack item) {
      super(i, x, y, s);
      super.width = 28;
      super.field_146121_g = 28;
      this.item = item;
   }

   public GuiMenuTopIconButton(int i, GuiButton parent, String s, ItemStack item) {
      super(i, parent, s);
      super.width = 28;
      super.field_146121_g = 28;
      this.item = item;
   }

   public GuiMenuTopIconButton(int i, int x, int y, String s, IButtonListener listener, ItemStack item) {
      super(i, x, y, s);
      super.width = 28;
      super.field_146121_g = 28;
      this.item = item;
      super.listener = listener;
   }

   public GuiMenuTopIconButton(int i, GuiButton parent, String s, IButtonListener listener, ItemStack item) {
      super(i, parent, s, listener);
      super.width = 28;
      super.field_146121_g = 28;
      this.item = item;
   }

   public void drawButton(Minecraft minecraft, int i, int j) {
      if(this.getVisible()) {
         if(this.item.getItem() == null) {
            this.item = new ItemStack(Blocks.dirt);
         }

         super.hover = i >= super.xPosition && j >= super.yPosition && i < super.xPosition + this.getWidth() && j < super.yPosition + super.field_146121_g;
         Minecraft mc = Minecraft.getMinecraft();
         if(super.hover && !super.active) {
            int x = i + mc.fontRendererObj.getStringWidth(super.displayString);
            GL11.glTranslatef((float)x, (float)(super.yPosition + 2), 0.0F);
            this.drawHoveringText(Arrays.asList(new String[]{super.displayString}), 0, 0, mc.fontRendererObj);
            GL11.glTranslatef((float)(-x), (float)(-(super.yPosition + 2)), 0.0F);
         }

         mc.getTextureManager().bindTexture(resource);
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3042);
         GL11.glDisable(2896);
         this.drawTexturedModalRect(super.xPosition, super.yPosition + (super.active?2:0), 0, super.active?32:0, 28, 28);
         super.zLevel = 100.0F;
         itemRender.zLevel = 100.0F;
         GL11.glEnable(2896);
         GL11.glEnable('\u803a');
         RenderHelper.enableGUIStandardItemLighting();
         itemRender.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.getTextureManager(), this.item, super.xPosition + 6, super.yPosition + 10);
         itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.getTextureManager(), this.item, super.xPosition + 6, super.yPosition + 10);
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable(2896);
         itemRender.zLevel = 0.0F;
         super.zLevel = 0.0F;
         GL11.glPopMatrix();
      }
   }

   protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font) {
      if(!p_146283_1_.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = p_146283_1_.iterator();

         int k2;
         while(iterator.hasNext()) {
            String j2 = (String)iterator.next();
            k2 = font.getStringWidth(j2);
            if(k2 > k) {
               k = k2;
            }
         }

         int var15 = p_146283_2_ + 12;
         k2 = p_146283_3_ - 12;
         int i1 = 8;
         if(p_146283_1_.size() > 1) {
            i1 += 2 + (p_146283_1_.size() - 1) * 10;
         }

         if(var15 + k > super.width) {
            var15 -= 28 + k;
         }

         if(k2 + i1 + 6 > super.field_146121_g) {
            k2 = super.field_146121_g - i1 - 6;
         }

         super.zLevel = 300.0F;
         itemRender.zLevel = 300.0F;
         int j1 = -267386864;
         this.drawGradientRect(var15 - 3, k2 - 4, var15 + k + 3, k2 - 3, j1, j1);
         this.drawGradientRect(var15 - 3, k2 + i1 + 3, var15 + k + 3, k2 + i1 + 4, j1, j1);
         this.drawGradientRect(var15 - 3, k2 - 3, var15 + k + 3, k2 + i1 + 3, j1, j1);
         this.drawGradientRect(var15 - 4, k2 - 3, var15 - 3, k2 + i1 + 3, j1, j1);
         this.drawGradientRect(var15 + k + 3, k2 - 3, var15 + k + 4, k2 + i1 + 3, j1, j1);
         int k1 = 1347420415;
         int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
         this.drawGradientRect(var15 - 3, k2 - 3 + 1, var15 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
         this.drawGradientRect(var15 + k + 2, k2 - 3 + 1, var15 + k + 3, k2 + i1 + 3 - 1, k1, l1);
         this.drawGradientRect(var15 - 3, k2 - 3, var15 + k + 3, k2 - 3 + 1, k1, k1);
         this.drawGradientRect(var15 - 3, k2 + i1 + 2, var15 + k + 3, k2 + i1 + 3, l1, l1);

         for(int i2 = 0; i2 < p_146283_1_.size(); ++i2) {
            String s1 = (String)p_146283_1_.get(i2);
            font.drawStringWithShadow(s1, var15, k2, -1);
            if(i2 == 0) {
               k2 += 2;
            }

            k2 += 10;
         }

         super.zLevel = 0.0F;
         itemRender.zLevel = 0.0F;
         GL11.glEnable(2896);
         GL11.glEnable(2929);
         RenderHelper.enableStandardItemLighting();
         GL11.glEnable('\u803a');
      }

   }

}
