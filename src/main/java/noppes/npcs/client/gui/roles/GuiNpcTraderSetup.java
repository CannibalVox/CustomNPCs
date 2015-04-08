package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.Client;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButtonYesNo;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.containers.ContainerNPCTraderSetup;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleTrader;
import org.lwjgl.opengl.GL11;

public class GuiNpcTraderSetup extends GuiContainerNPCInterface2 implements ITextfieldListener {

   private final ResourceLocation slot = new ResourceLocation("customnpcs", "textures/gui/slot.png");
   private RoleTrader role;


   public GuiNpcTraderSetup(EntityNPCInterface npc, ContainerNPCTraderSetup container) {
      super(npc, container);
      super.ySize = 220;
      super.menuYOffset = 10;
      this.role = container.role;
   }

   public void initGui() {
      super.initGui();
      super.buttonList.clear();
      this.setBackground("tradersetup.png");
      this.addLabel(new GuiNpcLabel(0, "role.marketname", super.field_147003_i + 214, super.field_147009_r + 150));
      this.addTextField(new GuiNpcTextField(0, this, super.field_147003_i + 214, super.field_147009_r + 160, 180, 20, this.role.marketName));
      this.addLabel(new GuiNpcLabel(1, "gui.ignoreDamage", super.field_147003_i + 260, super.field_147009_r + 29));
      this.addButton(new GuiNpcButtonYesNo(1, super.field_147003_i + 340, super.field_147009_r + 24, this.role.ignoreDamage));
      this.addLabel(new GuiNpcLabel(2, "gui.ignoreNBT", super.field_147003_i + 260, super.field_147009_r + 51));
      this.addButton(new GuiNpcButtonYesNo(2, super.field_147003_i + 340, super.field_147009_r + 46, this.role.ignoreNBT));
   }

   public void drawScreen(int i, int j, float f) {
      super.field_147009_r += 10;
      super.drawScreen(i, j, f);
      super.field_147009_r -= 10;
   }

   public void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 1) {
         this.role.ignoreDamage = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

      if(guibutton.id == 2) {
         this.role.ignoreNBT = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      super.drawGuiContainerBackgroundLayer(f, i, j);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

      for(int slot = 0; slot < 18; ++slot) {
         int x = super.field_147003_i + slot % 3 * 94 + 7;
         int y = super.field_147009_r + slot / 3 * 22 + 4;
         super.mc.renderEngine.bindTexture(this.slot);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(x - 1, y, 0, 0, 18, 18);
         this.drawTexturedModalRect(x + 17, y, 0, 0, 18, 18);
         super.fontRendererObj.drawString("=", x + 36, y + 5, CustomNpcResourceListener.DefaultTextColor);
         super.mc.renderEngine.bindTexture(this.slot);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(x + 42, y, 0, 0, 18, 18);
      }

   }

   public void save() {
      Client.sendData(EnumPacketServer.TraderMarketSave, new Object[]{this.role.marketName, Boolean.valueOf(false)});
      Client.sendData(EnumPacketServer.RoleSave, new Object[]{this.role.writeToNBT(new NBTTagCompound())});
   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      String name = guiNpcTextField.getText();
      if(!name.equalsIgnoreCase(this.role.marketName)) {
         this.role.marketName = name;
         Client.sendData(EnumPacketServer.TraderMarketSave, new Object[]{this.role.marketName, Boolean.valueOf(true)});
      }

   }
}
