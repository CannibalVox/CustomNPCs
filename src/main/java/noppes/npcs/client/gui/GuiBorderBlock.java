package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.blocks.tiles.TileBorder;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiNpcAvailability;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketServer;

public class GuiBorderBlock extends GuiNPCInterface implements IGuiData {

   private TileBorder tile;


   public GuiBorderBlock(int x, int y, int z) {
      this.tile = (TileBorder)super.player.worldObj.getTileEntity(x, y, z);
      Client.sendData(EnumPacketServer.GetTileEntity, new Object[]{Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)});
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(4, super.guiLeft + 40, super.guiTop + 40, 120, 20, "Availability Options"));
      this.addLabel(new GuiNpcLabel(0, "Height", super.guiLeft + 1, super.guiTop + 76, 16777215));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 71, 40, 20, this.tile.height + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(0, 500, 6);
      this.addLabel(new GuiNpcLabel(1, "Message", super.guiLeft + 1, super.guiTop + 100, 16777215));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 95, 200, 20, this.tile.message));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 40, super.guiTop + 190, 120, 20, "Done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.close();
      }

      if(id == 4) {
         this.save();
         this.setSubGui(new SubGuiNpcAvailability(this.tile.availability));
      }

   }

   public void save() {
      if(this.tile != null) {
         this.tile.height = this.getTextField(0).getInteger();
         this.tile.message = this.getTextField(1).getText();
         NBTTagCompound compound = new NBTTagCompound();
         this.tile.writeToNBT(compound);
         Client.sendData(EnumPacketServer.SaveTileEntity, new Object[]{compound});
      }
   }

   public void setGuiData(NBTTagCompound compound) {
      this.tile.readFromNBT(compound);
   }
}
