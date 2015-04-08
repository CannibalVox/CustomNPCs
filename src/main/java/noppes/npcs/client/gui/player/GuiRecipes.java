package noppes.npcs.client.gui.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.gui.util.GuiButtonNextPage;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiRecipes extends GuiNPCInterface {

   private static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/slot.png");
   private int page = 0;
   private boolean npcRecipes = true;
   private GuiNpcLabel label;
   private GuiNpcButton left;
   private GuiNpcButton right;
   private List recipes = new ArrayList();


   public GuiRecipes() {
      super.ySize = 182;
      super.xSize = 256;
      this.setBackground("recipes.png");
      super.closeOnEsc = true;
      this.recipes.addAll(RecipeController.instance.anvilRecipes.values());
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "Recipe List", super.guiLeft + 5, super.guiTop + 5));
      this.addLabel(this.label = new GuiNpcLabel(1, "", super.guiLeft + 5, super.guiTop + 168));
      this.addButton(this.left = new GuiButtonNextPage(1, super.guiLeft + 150, super.guiTop + 164, true));
      this.addButton(this.right = new GuiButtonNextPage(2, super.guiLeft + 80, super.guiTop + 164, false));
      this.updateButton();
   }

   private void updateButton() {
      this.right.visible = this.right.enabled = this.page > 0;
      this.left.visible = this.left.enabled = this.page + 1 < MathHelper.ceiling_float_int((float)this.recipes.size() / 4.0F);
   }

   protected void actionPerformed(GuiButton button) {
      if(button.enabled) {
         if(button == this.right) {
            --this.page;
         }

         if(button == this.left) {
            ++this.page;
         }

         this.updateButton();
      }
   }

   public void drawScreen(int xMouse, int yMouse, float f) {
      super.drawScreen(xMouse, yMouse, f);
      super.mc.renderEngine.bindTexture(resource);
      this.label.label = this.page + 1 + "/" + MathHelper.ceiling_float_int((float)this.recipes.size() / 4.0F);
      this.label.x = super.guiLeft + (256 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.label.label)) / 2;

      int i;
      int index;
      IRecipe irecipe;
      int x;
      int j;
      int k;
      ItemStack item;
      for(i = 0; i < 4; ++i) {
         index = i + this.page * 4;
         if(index >= this.recipes.size()) {
            break;
         }

         irecipe = (IRecipe)this.recipes.get(index);
         if(irecipe.getRecipeOutput() != null) {
            int recipe = super.guiLeft + 5 + i / 2 * 126;
            x = super.guiTop + 15 + i % 2 * 76;
            this.drawItem(irecipe.getRecipeOutput(), recipe + 98, x + 28, xMouse, yMouse);
            if(irecipe instanceof RecipeCarpentry) {
               RecipeCarpentry y = (RecipeCarpentry)irecipe;
               recipe += (72 - y.recipeWidth * 18) / 2;
               x += (72 - y.recipeHeight * 18) / 2;

               for(j = 0; j < y.recipeWidth; ++j) {
                  for(k = 0; k < y.recipeHeight; ++k) {
                     super.mc.renderEngine.bindTexture(resource);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     this.drawTexturedModalRect(recipe + j * 18, x + k * 18, 0, 0, 18, 18);
                     item = y.getCraftingItem(j + k * y.recipeWidth);
                     if(item != null) {
                        this.drawItem(item, recipe + j * 18 + 1, x + k * 18 + 1, xMouse, yMouse);
                     }
                  }
               }
            }
         }
      }

      for(i = 0; i < 4; ++i) {
         index = i + this.page * 4;
         if(index >= this.recipes.size()) {
            break;
         }

         irecipe = (IRecipe)this.recipes.get(index);
         if(irecipe instanceof RecipeCarpentry) {
            RecipeCarpentry var13 = (RecipeCarpentry)irecipe;
            if(var13.getRecipeOutput() != null) {
               x = super.guiLeft + 5 + i / 2 * 126;
               int var14 = super.guiTop + 15 + i % 2 * 76;
               this.drawOverlay(var13.getRecipeOutput(), x + 98, var14 + 22, xMouse, yMouse);
               x += (72 - var13.recipeWidth * 18) / 2;
               var14 += (72 - var13.recipeHeight * 18) / 2;

               for(j = 0; j < var13.recipeWidth; ++j) {
                  for(k = 0; k < var13.recipeHeight; ++k) {
                     item = var13.getCraftingItem(j + k * var13.recipeWidth);
                     if(item != null) {
                        this.drawOverlay(item, x + j * 18 + 1, var14 + k * 18 + 1, xMouse, yMouse);
                     }
                  }
               }
            }
         }
      }

   }

   private void drawItem(ItemStack item, int x, int y, int xMouse, int yMouse) {
      GL11.glPushMatrix();
      GL11.glEnable('\u803a');
      RenderHelper.enableGUIStandardItemLighting();
      GuiScreen.itemRender.zLevel = 100.0F;
      GuiScreen.itemRender.renderItemIntoGUI(super.fontRendererObj, super.mc.renderEngine, item, x, y);
      GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, super.mc.renderEngine, item, x, y);
      GuiScreen.itemRender.zLevel = 0.0F;
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable('\u803a');
      GL11.glPopMatrix();
   }

   private void drawOverlay(ItemStack item, int x, int y, int xMouse, int yMouse) {
      if(this.func_146978_c(x - super.guiLeft, y - super.guiTop, 16, 16, xMouse, yMouse)) {
         this.renderToolTip(item, xMouse, yMouse);
      }

   }

   protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_) {
      int k1 = super.guiLeft;
      int l1 = super.guiTop;
      p_146978_5_ -= k1;
      p_146978_6_ -= l1;
      return p_146978_5_ >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && p_146978_6_ >= p_146978_2_ - 1 && p_146978_6_ < p_146978_2_ + p_146978_4_ + 1;
   }

   public void save() {}

}
