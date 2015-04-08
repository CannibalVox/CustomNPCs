package noppes.npcs.roles;

import java.util.HashMap;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RoleFollower extends RoleInterface {

   private String ownerUUID;
   public boolean isFollowing = true;
   public HashMap rates = new HashMap();
   public NpcMiscInventory inventory = new NpcMiscInventory(3);
   public String dialogHire = StatCollector.translateToLocal("follower.hireText") + " {days} " + StatCollector.translateToLocal("follower.days");
   public String dialogFarewell = StatCollector.translateToLocal("follower.farewellText") + " {player}";
   public int daysHired;
   public long hiredTime;
   public boolean disableGui = false;
   public boolean infiniteDays = false;
   public boolean refuseSoulStone = false;
   public EntityPlayer owner = null;


   public RoleFollower(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("MercenaryDaysHired", this.daysHired);
      nbttagcompound.setLong("MercenaryHiredTime", this.hiredTime);
      nbttagcompound.setString("MercenaryDialogHired", this.dialogHire);
      nbttagcompound.setString("MercenaryDialogFarewell", this.dialogFarewell);
      if(this.hasOwner()) {
         nbttagcompound.setString("MercenaryOwner", this.ownerUUID);
      }

      nbttagcompound.setTag("MercenaryDayRates", NBTTags.nbtIntegerIntegerMap(this.rates));
      nbttagcompound.setTag("MercenaryInv", this.inventory.getToNBT());
      nbttagcompound.setBoolean("MercenaryIsFollowing", this.isFollowing);
      nbttagcompound.setBoolean("MercenaryDisableGui", this.disableGui);
      nbttagcompound.setBoolean("MercenaryInfiniteDays", this.infiniteDays);
      nbttagcompound.setBoolean("MercenaryRefuseSoulstone", this.refuseSoulStone);
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.ownerUUID = nbttagcompound.getString("MercenaryOwner");
      this.daysHired = nbttagcompound.getInteger("MercenaryDaysHired");
      this.hiredTime = nbttagcompound.getLong("MercenaryHiredTime");
      this.dialogHire = nbttagcompound.getString("MercenaryDialogHired");
      this.dialogFarewell = nbttagcompound.getString("MercenaryDialogFarewell");
      this.rates = NBTTags.getIntegerIntegerMap(nbttagcompound.getTagList("MercenaryDayRates", 10));
      this.inventory.setFromNBT(nbttagcompound.getCompoundTag("MercenaryInv"));
      this.isFollowing = nbttagcompound.getBoolean("MercenaryIsFollowing");
      this.disableGui = nbttagcompound.getBoolean("MercenaryDisableGui");
      this.infiniteDays = nbttagcompound.getBoolean("MercenaryInfiniteDays");
      this.refuseSoulStone = nbttagcompound.getBoolean("MercenaryRefuseSoulstone");
   }

   public boolean aiShouldExecute() {
      this.owner = this.getOwner();
      if(!this.infiniteDays && this.owner != null && this.getDaysLeft() <= 0) {
         this.owner.addChatMessage(new ChatComponentTranslation(NoppesStringUtils.formatText(this.dialogFarewell, new Object[]{this.owner, super.npc}), new Object[0]));
         this.killed();
      }

      return false;
   }

   public EntityPlayer getOwner() {
      if(this.ownerUUID != null && !this.ownerUUID.isEmpty()) {
         try {
            UUID ex = UUID.fromString(this.ownerUUID);
            if(ex != null) {
               return super.npc.worldObj.getPlayerEntityByUUID(ex);
            }
         } catch (IllegalArgumentException var2) {
            ;
         }

         return super.npc.worldObj.getPlayerEntityByName(this.ownerUUID);
      } else {
         return null;
      }
   }

   public boolean hasOwner() {
      return this.daysHired <= 0?false:this.ownerUUID != null && !this.ownerUUID.isEmpty();
   }

   public void killed() {
      this.ownerUUID = null;
      this.daysHired = 0;
      this.hiredTime = 0L;
      this.isFollowing = true;
   }

   public int getDaysLeft() {
      if(this.infiniteDays) {
         return 100;
      } else if(this.daysHired <= 0) {
         return 0;
      } else {
         int days = (int)((super.npc.worldObj.getTotalWorldTime() - this.hiredTime) / 24000L);
         return this.daysHired - days;
      }
   }

   public void addDays(int days) {
      this.daysHired = days + this.getDaysLeft();
      this.hiredTime = super.npc.worldObj.getTotalWorldTime();
   }

   public void interact(EntityPlayer player) {
      if(this.ownerUUID != null && !this.ownerUUID.isEmpty()) {
         if(player == this.owner && !this.disableGui) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollower, super.npc);
         }
      } else {
         super.npc.say(player, super.npc.advanced.getInteractLine());
         NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollowerHire, super.npc);
      }

   }

   public boolean defendOwner() {
      return this.isFollowing() && super.npc.advanced.job == EnumJobType.Guard;
   }

   public void delete() {}

   public boolean isFollowing() {
      return this.owner != null && this.isFollowing && this.getDaysLeft() > 0;
   }

   public void setOwner(EntityPlayer player) {
      String id = player.getUniqueID().toString();
      if(this.ownerUUID == null || id == null || !this.ownerUUID.equals(id)) {
         this.killed();
      }

      this.ownerUUID = id;
   }
}
