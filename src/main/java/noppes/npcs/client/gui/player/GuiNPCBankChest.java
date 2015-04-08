package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class GuiNPCBankChest extends GuiContainerNPCInterface implements IGuiData {

   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/bankchest.png");
   private ContainerNPCBankInterface container;
   private int availableSlots = 0;
   private int maxSlots = 1;
   private int unlockedSlots = 1;
   private ItemStack currency;


   public GuiNPCBankChest(EntityNPCInterface npc, ContainerNPCBankInterface container) {
      super(npc, container);
      this.container = container;
      super.title = "";
      super.allowUserInput = false;
      super.ySize = 235;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.availableSlots = 0;
      if(this.maxSlots > 1) {
         for(int i = 0; i < this.maxSlots; ++i) {
            GuiNpcButton button = new GuiNpcButton(i, super.field_147003_i - 50, super.field_147009_r + 10 + i * 24, 50, 20, "Tab " + (i + 1));
            if(i > this.unlockedSlots) {
               button.setEnabled(false);
            }

            this.addButton(button);
            ++this.availableSlots;
         }

         if(this.availableSlots == 1) {
            super.buttonList.clear();
         }
      }

      if(!this.container.isAvailable()) {
         this.addButton(new GuiNpcButton(8, super.field_147003_i + 48, super.field_147009_r + 48, 80, 20, StatCollector.translateToLocal("bank.unlock")));
      } else if(this.container.canBeUpgraded()) {
         this.addButton(new GuiNpcButton(9, super.field_147003_i + 48, super.field_147009_r + 48, 80, 20, StatCollector.translateToLocal("bank.upgrade")));
      }

      if(this.maxSlots > 1) {
         this.getButton(this.container.slot).visible = false;
         this.getButton(this.container.slot).setEnabled(false);
      }

   }

   public void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      int id = guibutton.id;
      if(id < 6) {
         this.close();
         NoppesUtilPlayer.sendData(EnumPlayerPacket.BankSlotOpen, new Object[]{Integer.valueOf(id), Integer.valueOf(this.container.bankid)});
      }

      if(id == 8) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.BankUnlock, new Object[0]);
      }

      if(id == 9) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.BankUpgrade, new Object[0]);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.resource);
      int l = (super.width - super.xSize) / 2;
      int i1 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(l, i1, 0, 0, super.xSize, 6);
      int ii;
      int y;
      if(!this.container.isAvailable()) {
         this.drawTexturedModalRect(l, i1 + 6, 0, 6, super.xSize, 64);
         this.drawTexturedModalRect(l, i1 + 70, 0, 124, super.xSize, 98);
         ii = super.field_147003_i + 30;
         y = super.field_147009_r + 8;
         super.fontRendererObj.drawString(StatCollector.translateToLocal("bank.unlockCosts") + ":", ii, y + 4, CustomNpcResourceListener.DefaultTextColor);
         this.drawItem(ii + 90, y, this.currency, i, j);
      } else if(this.container.isUpgraded()) {
         this.drawTexturedModalRect(l, i1 + 60, 0, 60, super.xSize, 162);
         this.drawTexturedModalRect(l, i1 + 6, 0, 60, super.xSize, 64);
      } else if(this.container.canBeUpgraded()) {
         this.drawTexturedModalRect(l, i1 + 6, 0, 6, super.xSize, 216);
         ii = super.field_147003_i + 30;
         y = super.field_147009_r + 8;
         super.fontRendererObj.drawString(StatCollector.translateToLocal("bank.upgradeCosts") + ":", ii, y + 4, CustomNpcResourceListener.DefaultTextColor);
         this.drawItem(ii + 90, y, this.currency, i, j);
      } else {
         this.drawTexturedModalRect(l, i1 + 6, 0, 60, super.xSize, 162);
      }

      if(this.maxSlots > 1) {
         for(ii = 0; ii < this.maxSlots && this.availableSlots != ii; ++ii) {
            super.fontRendererObj.drawString("Tab " + (ii + 1), super.field_147003_i - 40, super.field_147009_r + 16 + ii * 24, 16777215);
         }
      }

      super.drawGuiContainerBackgroundLayer(f, i, j);
   }

   private void drawItem(int x, int y, ItemStack item, int mouseX, int mouseY) {
      if(item != null) {
         GL11.glEnable('\u803a');
         RenderHelper.enableGUIStandardItemLighting();
         GuiScreen.itemRender.renderItemIntoGUI(super.fontRendererObj, super.mc.renderEngine, item, x, y);
         GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, super.mc.renderEngine, item, x, y);
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable('\u803a');
         if(this.isPointInRegion(x - super.field_147003_i, y - super.field_147009_r, 16, 16, mouseX, mouseY)) {
            this.renderToolTip(item, mouseX, mouseY);
         }

      }
   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      this.maxSlots = compound.getInteger("MaxSlots");
      this.unlockedSlots = compound.getInteger("UnlockedSlots");
      if(compound.hasKey("Currency")) {
         this.currency = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Currency"));
      } else {
         this.currency = null;
      }

      if(this.container.currency != null) {
         this.container.currency.item = this.currency;
      }

      this.initGui();
   }
}
