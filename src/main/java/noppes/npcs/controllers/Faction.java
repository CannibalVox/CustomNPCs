package noppes.npcs.controllers;

import java.util.HashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.entity.EntityNPCInterface;

public class Faction {

   public String name = "";
   public int color = Integer.parseInt("FF00", 16);
   public HashSet attackFactions;
   public int id = -1;
   public int neutralPoints = 500;
   public int friendlyPoints = 1500;
   public int defaultPoints = 1000;
   public boolean hideFaction = false;
   public boolean getsAttacked = false;


   public Faction() {
      this.attackFactions = new HashSet();
   }

   public Faction(int id, String name, int color, int defaultPoints) {
      this.name = name;
      this.color = color;
      this.defaultPoints = defaultPoints;
      this.id = id;
      this.attackFactions = new HashSet();
   }

   public static String formatName(String name) {
      name = name.toLowerCase().trim();
      return name.substring(0, 1).toUpperCase() + name.substring(1);
   }

   public void readNBT(NBTTagCompound compound) {
      this.name = compound.getString("Name");
      this.color = compound.getInteger("Color");
      this.id = compound.getInteger("Slot");
      this.neutralPoints = compound.getInteger("NeutralPoints");
      this.friendlyPoints = compound.getInteger("FriendlyPoints");
      this.defaultPoints = compound.getInteger("DefaultPoints");
      this.hideFaction = compound.getBoolean("HideFaction");
      this.getsAttacked = compound.getBoolean("GetsAttacked");
      this.attackFactions = NBTTags.getIntegerSet(compound.getTagList("AttackFactions", 10));
   }

   public void writeNBT(NBTTagCompound compound) {
      compound.setInteger("Slot", this.id);
      compound.setString("Name", this.name);
      compound.setInteger("Color", this.color);
      compound.setInteger("NeutralPoints", this.neutralPoints);
      compound.setInteger("FriendlyPoints", this.friendlyPoints);
      compound.setInteger("DefaultPoints", this.defaultPoints);
      compound.setBoolean("HideFaction", this.hideFaction);
      compound.setBoolean("GetsAttacked", this.getsAttacked);
      compound.setTag("AttackFactions", NBTTags.nbtIntegerSet(this.attackFactions));
   }

   public boolean isFriendlyToPlayer(EntityPlayer player) {
      PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
      return data.getFactionPoints(this.id) >= this.friendlyPoints;
   }

   public boolean isAggressiveToPlayer(EntityPlayer player) {
      PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
      return data.getFactionPoints(this.id) < this.neutralPoints;
   }

   public boolean isNeutralToPlayer(EntityPlayer player) {
      PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
      int points = data.getFactionPoints(this.id);
      return points >= this.neutralPoints && points < this.friendlyPoints;
   }

   public boolean isAggressiveToNpc(EntityNPCInterface entity) {
      return this.attackFactions.contains(Integer.valueOf(entity.faction.id));
   }
}
