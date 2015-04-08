package noppes.npcs.blocks.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import noppes.npcs.blocks.tiles.TileNpcContainer;

public class TilePedestal extends TileNpcContainer {

   public String getName() {
      return "tile.npcPedestal.name";
   }

   public int getSizeInventory() {
      return 1;
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 2), (double)(super.zCoord + 1));
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.writeToNBT(compound);
      S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, compound);
      return packet;
   }

   public int powerProvided() {
      return this.getStackInSlot(0) == null?0:15;
   }
}
