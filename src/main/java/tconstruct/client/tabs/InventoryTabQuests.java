package tconstruct.client.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.client.gui.player.GuiQuestLog;
import tconstruct.client.tabs.AbstractTab;

public class InventoryTabQuests extends AbstractTab {

   public InventoryTabQuests() {
      super(0, 0, 0, new ItemStack(CustomItems.letter));
   }

   public void onTabClicked() {
      Minecraft mc = Minecraft.getMinecraft();
      mc.displayGuiScreen(new GuiQuestLog(mc.thePlayer));
   }

   public boolean shouldAddToList() {
      return true;
   }
}
