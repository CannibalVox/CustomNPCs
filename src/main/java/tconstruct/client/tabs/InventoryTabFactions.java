package tconstruct.client.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.client.gui.player.GuiFaction;
import tconstruct.client.tabs.AbstractTab;

public class InventoryTabFactions extends AbstractTab {

   public InventoryTabFactions() {
      super(0, 0, 0, new ItemStack(CustomItems.wallBanner, 1, 1));
   }

   public void onTabClicked() {
      Minecraft mc = Minecraft.getMinecraft();
      mc.displayGuiScreen(new GuiFaction());
   }

   public boolean shouldAddToList() {
      return true;
   }
}
