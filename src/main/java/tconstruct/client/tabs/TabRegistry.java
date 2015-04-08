package tconstruct.client.tabs;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NPCGuiHelper;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post;
import tconstruct.client.tabs.AbstractTab;
import tconstruct.client.tabs.InventoryTabVanilla;

public class TabRegistry {

   private static ArrayList tabList = new ArrayList();
   private static Minecraft mc = FMLClientHandler.instance().getClient();


   public static void registerTab(AbstractTab tab) {
      tabList.add(tab);
   }

   public static ArrayList getTabList() {
      return tabList;
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void guiPostInit(Post event) {
      if(event.gui instanceof GuiInventory) {
         short xSize = 176;
         short ySize = 166;
         int guiLeft = (event.gui.width - xSize) / 2;
         int guiTop = (event.gui.height - ySize) / 2;
         updateTabValues(guiLeft, guiTop, InventoryTabVanilla.class);
         addTabsToList(NPCGuiHelper.getButtonList(event.gui));
      }

   }

   public static void openInventoryGui() {
      mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
      GuiInventory inventory = new GuiInventory(mc.thePlayer);
      mc.displayGuiScreen(inventory);
   }

   public static void updateTabValues(int cornerX, int cornerY, Class selectedButton) {
      int count = 2;

      for(int i = 0; i < tabList.size(); ++i) {
         AbstractTab t = (AbstractTab)tabList.get(i);
         if(t.shouldAddToList()) {
            t.id = count;
            t.xPosition = cornerX + (count - 2) * 28;
            t.yPosition = cornerY - 28;
            t.enabled = !t.getClass().equals(selectedButton);
            ++count;
         }
      }

   }

   public static void addTabsToList(List buttonList) {
      Iterator var1 = tabList.iterator();

      while(var1.hasNext()) {
         AbstractTab tab = (AbstractTab)var1.next();
         if(tab.shouldAddToList()) {
            buttonList.add(tab);
         }
      }

   }

}
