package noppes.npcs.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.controllers.RecipeCarpentry;

public class ContainerManageRecipes extends Container {

   private InventoryBasic craftingMatrix;
   public RecipeCarpentry recipe;
   public int size;
   public int width;
   private boolean init = false;


   public ContainerManageRecipes(EntityPlayer player, int size) {
      this.size = size * size;
      this.width = size;
      this.craftingMatrix = new InventoryBasic("crafting", false, this.size + 1);
      this.recipe = new RecipeCarpentry("");
      this.addSlotToContainer(new Slot(this.craftingMatrix, 0, 87, 61));

      int j1;
      int l1;
      for(j1 = 0; j1 < size; ++j1) {
         for(l1 = 0; l1 < size; ++l1) {
            this.addSlotToContainer(new Slot(this.craftingMatrix, j1 * this.width + l1 + 1, l1 * 18 + 8, j1 * 18 + 35));
         }
      }

      for(j1 = 0; j1 < 3; ++j1) {
         for(l1 = 0; l1 < 9; ++l1) {
            this.addSlotToContainer(new Slot(player.inventory, l1 + j1 * 9 + 9, 8 + l1 * 18, 113 + j1 * 18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.addSlotToContainer(new Slot(player.inventory, j1, 8 + j1 * 18, 171));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean canInteractWith(EntityPlayer entityplayer) {
      return true;
   }

   public void setRecipe(RecipeCarpentry recipe) {
      this.craftingMatrix.setInventorySlotContents(0, recipe.getRecipeOutput());

      for(int i = 0; i < this.width; ++i) {
         for(int j = 0; j < this.width; ++j) {
            if(j >= recipe.recipeWidth) {
               this.craftingMatrix.setInventorySlotContents(i * this.width + j + 1, (ItemStack)null);
            } else {
               this.craftingMatrix.setInventorySlotContents(i * this.width + j + 1, recipe.getCraftingItem(i * recipe.recipeWidth + j));
            }
         }
      }

      this.recipe = recipe;
   }

   public void saveRecipe() {
      int nextChar = 0;
      char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
      HashMap nameMapping = new HashMap();
      int firstRow = this.width;
      int lastRow = 0;
      int firstColumn = this.width;
      int lastColumn = 0;
      boolean seenRow = false;

      Iterator var14;
      ItemStack mapped1;
      for(int recipe = 0; recipe < this.width; ++recipe) {
         boolean r = false;

         for(int mapped = 0; mapped < this.width; ++mapped) {
            ItemStack letter = this.craftingMatrix.getStackInSlot(recipe * this.width + mapped + 1);
            if(letter != null) {
               if(!r && mapped < firstColumn) {
                  firstColumn = mapped;
               }

               if(mapped > lastColumn) {
                  lastColumn = mapped;
               }

               r = true;
               Character item = null;
               var14 = nameMapping.keySet().iterator();

               while(var14.hasNext()) {
                  mapped1 = (ItemStack)var14.next();
                  if(NoppesUtilPlayer.compareItems(mapped1, letter, this.recipe.ignoreDamage, this.recipe.ignoreNBT)) {
                     item = (Character)nameMapping.get(mapped1);
                  }
               }

               if(item == null) {
                  item = Character.valueOf(chars[nextChar]);
                  ++nextChar;
                  nameMapping.put(letter, item);
               }
            }
         }

         if(r) {
            if(!seenRow) {
               firstRow = recipe;
               lastRow = recipe;
               seenRow = true;
            } else {
               lastRow = recipe;
            }
         }
      }

      ArrayList var16 = new ArrayList();

      for(int var17 = 0; var17 < this.width; ++var17) {
         if(var17 >= firstRow && var17 <= lastRow) {
            String var20 = "";

            for(int var22 = 0; var22 < this.width; ++var22) {
               if(var22 >= firstColumn && var22 <= lastColumn) {
                  ItemStack var24 = this.craftingMatrix.getStackInSlot(var17 * this.width + var22 + 1);
                  if(var24 == null) {
                     var20 = var20 + " ";
                  } else {
                     var14 = nameMapping.keySet().iterator();

                     while(var14.hasNext()) {
                        mapped1 = (ItemStack)var14.next();
                        if(NoppesUtilPlayer.compareItems(mapped1, var24, false, false)) {
                           var20 = var20 + nameMapping.get(mapped1);
                        }
                     }
                  }
               }
            }

            var16.add(var20);
         }
      }

      if(nameMapping.isEmpty()) {
         RecipeCarpentry var19 = new RecipeCarpentry(this.recipe.name);
         var19.copy(this.recipe);
         this.recipe = var19;
      } else {
         Iterator var18 = nameMapping.keySet().iterator();

         while(var18.hasNext()) {
            ItemStack var21 = (ItemStack)var18.next();
            Character var23 = (Character)nameMapping.get(var21);
            var16.add(var23);
            var16.add(var21);
         }

         this.recipe = RecipeCarpentry.saveRecipe(this.recipe, this.craftingMatrix.getStackInSlot(0), var16.toArray());
      }
   }
}
