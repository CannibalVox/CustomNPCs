package noppes.npcs.blocks.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.TextBlock;

public class TileBigSign extends TileEntity {

   public int rotation;
   public boolean canEdit = true;
   public boolean hasChanged = true;
   private String signText = "";
   public TextBlock block;


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.rotation = compound.getInteger("SignRotation");
      this.setText(compound.getString("SignText"));
   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setInteger("SignRotation", this.rotation);
      compound.setString("SignText", this.signText);
   }

   public boolean canUpdate() {
      return false;
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      NBTTagCompound compound = pkt.getNbtCompound();
      this.readFromNBT(compound);
   }

   public void setText(String text) {
      this.signText = text;
      this.hasChanged = true;
   }

   public String getText() {
      return this.signText;
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.writeToNBT(compound);
      S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, compound);
      return packet;
   }
}
