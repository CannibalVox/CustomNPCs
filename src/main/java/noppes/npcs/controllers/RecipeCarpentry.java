package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.controllers.Availability;

public class RecipeCarpentry extends ShapedRecipes {

   public int id = -1;
   public String name = "";
   public Availability availability = new Availability();
   public boolean isGlobal = false;
   public boolean ignoreDamage = false;
   public boolean ignoreNBT = false;


   public RecipeCarpentry(int width, int height, ItemStack[] recipe, ItemStack result) {
      super(width, height, recipe, result);
   }

   public RecipeCarpentry(String name) {
      super(0, 0, new ItemStack[0], (ItemStack)null);
      this.name = name;
   }

   public static RecipeCarpentry read(NBTTagCompound compound) {
      RecipeCarpentry recipe = new RecipeCarpentry(compound.getInteger("Width"), compound.getInteger("Height"), NBTTags.getItemStackArray(compound.getTagList("Materials", 10)), ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item")));
      recipe.name = compound.getString("Name");
      recipe.id = compound.getInteger("ID");
      recipe.availability.readFromNBT(compound.getCompoundTag("Availability"));
      recipe.ignoreDamage = compound.getBoolean("IgnoreDamage");
      recipe.ignoreNBT = compound.getBoolean("IgnoreNBT");
      recipe.isGlobal = compound.getBoolean("Global");
      return recipe;
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setInteger("ID", this.id);
      compound.setInteger("Width", super.recipeWidth);
      compound.setInteger("Height", super.recipeHeight);
      if(this.getRecipeOutput() != null) {
         compound.setTag("Item", this.getRecipeOutput().writeToNBT(new NBTTagCompound()));
      }

      compound.setTag("Materials", NBTTags.nbtItemStackArray(super.recipeItems));
      compound.setTag("Availability", this.availability.writeToNBT(new NBTTagCompound()));
      compound.setString("Name", this.name);
      compound.setBoolean("Global", this.isGlobal);
      compound.setBoolean("IgnoreDamage", this.ignoreDamage);
      compound.setBoolean("IgnoreNBT", this.ignoreNBT);
      return compound;
   }

   public boolean matches(InventoryCrafting par1InventoryCrafting, World world) {
      for(int i = 0; i <= 4 - super.recipeWidth; ++i) {
         for(int j = 0; j <= 4 - super.recipeHeight; ++j) {
            if(this.checkMatch(par1InventoryCrafting, i, j, true)) {
               return true;
            }

            if(this.checkMatch(par1InventoryCrafting, i, j, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4) {
      for(int i = 0; i < 4; ++i) {
         for(int j = 0; j < 4; ++j) {
            int var7 = i - par2;
            int var8 = j - par3;
            ItemStack var9 = null;
            if(var7 >= 0 && var8 >= 0 && var7 < super.recipeWidth && var8 < super.recipeHeight) {
               if(par4) {
                  var9 = super.recipeItems[super.recipeWidth - var7 - 1 + var8 * super.recipeWidth];
               } else {
                  var9 = super.recipeItems[var7 + var8 * super.recipeWidth];
               }
            }

            ItemStack var10 = par1InventoryCrafting.getStackInRowAndColumn(i, j);
            if((var10 != null || var9 != null) && !NoppesUtilPlayer.compareItems(var9, var10, this.ignoreDamage, this.ignoreNBT)) {
               return false;
            }
         }
      }

      return true;
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      return this.getRecipeOutput() == null?null:this.getRecipeOutput().copy();
   }

   public int getRecipeSize() {
      return 16;
   }

   public static RecipeCarpentry saveRecipe(RecipeCarpentry recipe, ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
      String var3 = "";
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      int var9;
      if(par2ArrayOfObj[var4] instanceof String[]) {
         String[] var14 = (String[])((String[])((String[])par2ArrayOfObj[var4++]));
         String[] var15 = var14;
         var9 = var14.length;

         for(int newrecipe = 0; newrecipe < var9; ++newrecipe) {
            String var11 = var15[newrecipe];
            ++var6;
            var5 = var11.length();
            var3 = var3 + var11;
         }
      } else {
         while(par2ArrayOfObj[var4] instanceof String) {
            String var12 = (String)par2ArrayOfObj[var4++];
            ++var6;
            var5 = var12.length();
            var3 = var3 + var12;
         }
      }

      HashMap var13;
      for(var13 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2) {
         Character var141 = (Character)par2ArrayOfObj[var4];
         ItemStack var16 = null;
         if(par2ArrayOfObj[var4 + 1] instanceof Item) {
            var16 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
         } else if(par2ArrayOfObj[var4 + 1] instanceof Block) {
            var16 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
         } else if(par2ArrayOfObj[var4 + 1] instanceof ItemStack) {
            var16 = (ItemStack)par2ArrayOfObj[var4 + 1];
         }

         var13.put(var141, var16);
      }

      ItemStack[] var151 = new ItemStack[var5 * var6];

      for(var9 = 0; var9 < var5 * var6; ++var9) {
         char var17 = var3.charAt(var9);
         if(var13.containsKey(Character.valueOf(var17))) {
            var151[var9] = ((ItemStack)var13.get(Character.valueOf(var17))).copy();
         } else {
            var151[var9] = null;
         }
      }

      RecipeCarpentry var18 = new RecipeCarpentry(var5, var6, var151, par1ItemStack);
      var18.copy(recipe);
      if(var5 == 4 || var6 == 4) {
         var18.isGlobal = false;
      }

      return var18;
   }

   public void copy(RecipeCarpentry recipe) {
      this.id = recipe.id;
      this.name = recipe.name;
      this.availability = recipe.availability;
      this.isGlobal = recipe.isGlobal;
      this.ignoreDamage = recipe.ignoreDamage;
      this.ignoreNBT = recipe.ignoreNBT;
   }

   public ItemStack getCraftingItem(int i) {
      return super.recipeItems != null && i < super.recipeItems.length?super.recipeItems[i]:null;
   }

   public void setCraftingItem(int i, ItemStack item) {
      if(i < super.recipeItems.length) {
         super.recipeItems[i] = item;
      }

   }

   public boolean isValid() {
      if(super.recipeItems.length != 0 && this.getRecipeOutput() != null) {
         ItemStack[] var1 = super.recipeItems;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            ItemStack item = var1[var3];
            if(item != null) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
