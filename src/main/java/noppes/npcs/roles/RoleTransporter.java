package noppes.npcs.roles;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerTransportData;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.TransportLocation;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RoleTransporter extends RoleInterface {

   public int transportId = -1;
   public String name;
   private int ticks = 10;


   public RoleTransporter(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("TransporterId", this.transportId);
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.transportId = nbttagcompound.getInteger("TransporterId");
      TransportLocation loc = this.getLocation();
      if(loc != null) {
         this.name = loc.name;
      }

   }

   public boolean aiShouldExecute() {
      --this.ticks;
      if(this.ticks > 0) {
         return false;
      } else {
         this.ticks = 10;
         if(!this.hasTransport()) {
            return false;
         } else {
            TransportLocation loc = this.getLocation();
            if(loc.type != 0) {
               return false;
            } else {
               List inRange = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand(6.0D, 6.0D, 6.0D));
               Iterator var3 = inRange.iterator();

               while(var3.hasNext()) {
                  EntityPlayer player = (EntityPlayer)var3.next();
                  if(super.npc.canSee(player)) {
                     this.unlock(player, loc);
                  }
               }

               return false;
            }
         }
      }
   }

   public void aiStartExecuting() {}

   public void interact(EntityPlayer player) {
      if(this.hasTransport()) {
         TransportLocation loc = this.getLocation();
         if(loc.type == 2) {
            this.unlock(player, loc);
         }

         NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTransporter, super.npc);
      }

   }

   private void unlock(EntityPlayer player, TransportLocation loc) {
      PlayerTransportData data = PlayerDataController.instance.getPlayerData(player).transportData;
      if(!data.transports.contains(Integer.valueOf(this.transportId))) {
         data.transports.add(Integer.valueOf(this.transportId));
         player.addChatMessage(new ChatComponentTranslation("transporter.unlock", new Object[]{loc.name}));
      }
   }

   public TransportLocation getLocation() {
      return super.npc.isRemote()?null:TransportController.getInstance().getTransport(this.transportId);
   }

   public boolean hasTransport() {
      TransportLocation loc = this.getLocation();
      return loc != null && loc.id == this.transportId;
   }

   public void setTransport(TransportLocation location) {
      this.transportId = location.id;
      this.name = location.name;
   }
}
