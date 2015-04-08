package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerNPCTrader;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleTrader;
import org.lwjgl.opengl.GL11;

public class GuiNPCTrader extends GuiContainerNPCInterface {

   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/trader.png");
   private final ResourceLocation slot = new ResourceLocation("customnpcs", "textures/gui/slot.png");
   private RoleTrader role;
   private ContainerNPCTrader container;


   public GuiNPCTrader(EntityNPCInterface npc, ContainerNPCTrader container) {
      super(npc, container);
      this.container = container;
      this.role = (RoleTrader)npc.roleInterface;
      super.closeOnEsc = true;
      super.ySize = 224;
      super.xSize = 223;
      super.title = "role.trader";
   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      this.drawWorldBackground(0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.resource);
      this.drawTexturedModalRect(super.field_147003_i, super.field_147009_r, 0, 0, super.xSize, super.ySize);
      GL11.glEnable('\u803a');
      super.mc.renderEngine.bindTexture(this.slot);

      for(int slot = 0; slot < 18; ++slot) {
         int x = super.field_147003_i + slot % 3 * 72 + 10;
         int y = super.field_147009_r + slot / 3 * 21 + 6;
         ItemStack item = (ItemStack)this.role.inventoryCurrency.items.get(Integer.valueOf(slot));
         ItemStack item2 = (ItemStack)this.role.inventoryCurrency.items.get(Integer.valueOf(slot + 18));
         if(item == null) {
            item = item2;
            item2 = null;
         }

         if(NoppesUtilPlayer.compareItems(item, item2, false, false)) {
            item = item.copy();
            item.stackSize += item2.stackSize;
            item2 = null;
         }

         ItemStack sold = (ItemStack)this.role.inventorySold.items.get(Integer.valueOf(slot));
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         super.mc.renderEngine.bindTexture(this.slot);
         this.drawTexturedModalRect(x + 42, y, 0, 0, 18, 18);
         if(item != null && sold != null) {
            RenderHelper.enableGUIStandardItemLighting();
            if(item2 != null) {
               GuiScreen.itemRender.renderItemIntoGUI(super.fontRendererObj, super.mc.renderEngine, item2, x, y + 1);
               GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, super.mc.renderEngine, item2, x, y + 1);
            }

            GuiScreen.itemRender.renderItemIntoGUI(super.fontRendererObj, super.mc.renderEngine, item, x + 18, y + 1);
            GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, super.mc.renderEngine, item, x + 18, y + 1);
            RenderHelper.disableStandardItemLighting();
            super.fontRendererObj.drawString("=", x + 36, y + 5, CustomNpcResourceListener.DefaultTextColor);
         }
      }

      GL11.glDisable('\u803a');
      super.drawGuiContainerBackgroundLayer(f, i, j);
   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
      for(int slot = 0; slot < 18; ++slot) {
         int x = slot % 3 * 72 + 10;
         int y = slot / 3 * 21 + 6;
         ItemStack item = (ItemStack)this.role.inventoryCurrency.items.get(Integer.valueOf(slot));
         ItemStack item2 = (ItemStack)this.role.inventoryCurrency.items.get(Integer.valueOf(slot + 18));
         if(item == null) {
            item = item2;
            item2 = null;
         }

         if(NoppesUtilPlayer.compareItems(item, item2, false, false)) {
            item = item.copy();
            item.stackSize += item2.stackSize;
            item2 = null;
         }

         ItemStack sold = (ItemStack)this.role.inventorySold.items.get(Integer.valueOf(slot));
         if(item != null && sold != null) {
            if(this.isPointInRegion(x, y, 70, 19, par1, par2)) {
               String title;
               if(!this.container.canBuy(slot, super.player)) {
                  GL11.glTranslatef(0.0F, 0.0F, 300.0F);
                  if(item != null && !NoppesUtilPlayer.compareItems(super.player, item, false)) {
                     this.drawGradientRect(x + 17, y, x + 35, y + 18, 1886851088, 1886851088);
                  }

                  if(item2 != null && !NoppesUtilPlayer.compareItems(super.player, item2, false)) {
                     this.drawGradientRect(x - 1, y, x + 17, y + 18, 1886851088, 1886851088);
                  }

                  title = StatCollector.translateToLocal("trader.insufficient");
                  super.fontRendererObj.drawString(title, (super.xSize - super.fontRendererObj.getStringWidth(title)) / 2, 131, 14483456);
                  GL11.glTranslatef(0.0F, 0.0F, -300.0F);
               } else {
                  title = StatCollector.translateToLocal("trader.sufficient");
                  super.fontRendererObj.drawString(title, (super.xSize - super.fontRendererObj.getStringWidth(title)) / 2, 131, '\udd00');
               }
            }

            if(this.isPointInRegion(x, y, 16, 16, par1, par2) && item2 != null) {
               this.renderToolTip(item2, par1 - super.field_147003_i, par2 - super.field_147009_r);
            }

            if(this.isPointInRegion(x + 18, y, 16, 16, par1, par2)) {
               this.renderToolTip(item, par1 - super.field_147003_i, par2 - super.field_147009_r);
            }
         }
      }

   }

   public void save() {}
}
