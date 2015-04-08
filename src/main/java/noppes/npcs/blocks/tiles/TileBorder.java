package noppes.npcs.blocks.tiles;

import java.util.Iterator;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import noppes.npcs.controllers.Availability;

public class TileBorder extends TileEntity implements IEntitySelector {

   public Availability availability = new Availability();
   public AxisAlignedBB boundingbox;
   public int rotation = 0;
   public int height = 10;
   public String message = "availability.areaNotAvailble";


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.readExtraNBT(compound);
   }

   public void readExtraNBT(NBTTagCompound compound) {
      this.availability.readFromNBT(compound.getCompoundTag("BorderAvailability"));
      this.rotation = compound.getInteger("BorderRotation");
      this.height = compound.getInteger("BorderHeight");
      this.message = compound.getString("BorderMessage");
   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      this.writeExtraNBT(compound);
   }

   public void writeExtraNBT(NBTTagCompound compound) {
      compound.setTag("BorderAvailability", this.availability.writeToNBT(new NBTTagCompound()));
      compound.setInteger("BorderRotation", this.rotation);
      compound.setInteger("BorderHeight", this.height);
      compound.setString("BorderMessage", this.message);
   }

   public void updateEntity() {
      if(!super.worldObj.isRemote) {
         AxisAlignedBB box = AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + this.height + 1), (double)(super.zCoord + 1));
         List list = super.worldObj.selectEntitiesWithinAABB(Entity.class, box, this);
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            Entity entity = (Entity)var3.next();
            if(entity instanceof EntityEnderPearl) {
               EntityEnderPearl var9 = (EntityEnderPearl)entity;
               if(var9.getThrower() instanceof EntityPlayer && !this.availability.isAvailable((EntityPlayer)var9.getThrower())) {
                  entity.isDead = true;
               }
            } else {
               EntityPlayer player = (EntityPlayer)entity;
               if(!this.availability.isAvailable(player)) {
                  int posX = super.xCoord;
                  int posZ = super.zCoord;
                  int posY = super.yCoord;
                  if(this.rotation == 0) {
                     --posZ;
                  } else if(this.rotation == 2) {
                     ++posZ;
                  } else if(this.rotation == 1) {
                     ++posX;
                  } else if(this.rotation == 3) {
                     --posX;
                  }

                  while(!super.worldObj.isAirBlock(posX, posY, posZ)) {
                     ++posY;
                  }

                  player.setPositionAndUpdate((double)posX + 0.5D, (double)posY, (double)posZ + 0.5D);
                  if(!this.message.isEmpty()) {
                     player.addChatComponentMessage(new ChatComponentTranslation(this.message, new Object[0]));
                  }
               }
            }
         }

      }
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      NBTTagCompound compound = pkt.getNbtCompound();
      this.rotation = compound.getInteger("Rotation");
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setInteger("Rotation", this.rotation);
      S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, compound);
      return packet;
   }

   public boolean canUpdate() {
      return true;
   }

   public boolean isEntityApplicable(Entity var1) {
      return var1 instanceof EntityPlayerMP || var1 instanceof EntityEnderPearl;
   }
}
