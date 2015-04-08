package tconstruct.client.tabs;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import tconstruct.client.tabs.AbstractTab;
import tconstruct.client.tabs.TabRegistry;

public class InventoryTabVanilla extends AbstractTab {

   public InventoryTabVanilla() {
      super(0, 0, 0, new ItemStack(Blocks.crafting_table));
   }

   public void onTabClicked() {
      TabRegistry.openInventoryGui();
   }

   public boolean shouldAddToList() {
      return true;
   }
}
