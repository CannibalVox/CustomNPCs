package noppes.npcs.blocks.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileColorable extends TileEntity {

   public int color = 14;
   public int rotation;


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.color = compound.getInteger("BannerColor");
      this.rotation = compound.getInteger("BannerRotation");
   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setInteger("BannerColor", this.color);
      compound.setInteger("BannerRotation", this.rotation);
   }

   public boolean canUpdate() {
      return false;
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      NBTTagCompound compound = pkt.getNbtCompound();
      this.readFromNBT(compound);
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.writeToNBT(compound);
      compound.removeTag("Items");
      S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, compound);
      return packet;
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1));
   }

   public int powerProvided() {
      return 0;
   }
}
