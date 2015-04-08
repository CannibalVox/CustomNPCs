package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.blocks.tiles.TileWaypoint;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketServer;

public class GuiNpcWaypoint extends GuiNPCInterface {

   private TileWaypoint tile;


   public GuiNpcWaypoint(int x, int y, int z) {
      this.tile = (TileWaypoint)super.player.worldObj.getTileEntity(x, y, z);
      super.xSize = 265;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "gui.name", super.guiLeft + 1, super.guiTop + 76, 16777215));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 71, 200, 20, this.tile.name));
      this.addLabel(new GuiNpcLabel(1, "gui.range", super.guiLeft + 1, super.guiTop + 97, 16777215));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 92, 200, 20, this.tile.range + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(2, 60, 10);
      this.addButton(new GuiNpcButton(0, super.guiLeft + 40, super.guiTop + 190, 120, 20, "Done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.close();
      }

   }

   public void save() {
      this.tile.name = this.getTextField(0).getText();
      this.tile.range = this.getTextField(1).getInteger();
      NBTTagCompound compound = new NBTTagCompound();
      this.tile.writeToNBT(compound);
      Client.sendData(EnumPacketServer.SaveTileEntity, new Object[]{compound});
   }
}
