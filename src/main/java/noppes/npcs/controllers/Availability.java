package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.ICompatibilty;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumAvailabilityDialog;
import noppes.npcs.constants.EnumAvailabilityFaction;
import noppes.npcs.constants.EnumAvailabilityFactionType;
import noppes.npcs.constants.EnumAvailabilityQuest;
import noppes.npcs.constants.EnumDayTime;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.controllers.PlayerQuestController;

public class Availability implements ICompatibilty {

   public int version;
   public EnumAvailabilityDialog dialogAvailable;
   public EnumAvailabilityDialog dialog2Available;
   public EnumAvailabilityDialog dialog3Available;
   public EnumAvailabilityDialog dialog4Available;
   public int dialogId;
   public int dialog2Id;
   public int dialog3Id;
   public int dialog4Id;
   public EnumAvailabilityQuest questAvailable;
   public EnumAvailabilityQuest quest2Available;
   public EnumAvailabilityQuest quest3Available;
   public EnumAvailabilityQuest quest4Available;
   public int questId;
   public int quest2Id;
   public int quest3Id;
   public int quest4Id;
   public EnumDayTime daytime;
   public int factionId;
   public int faction2Id;
   public EnumAvailabilityFactionType factionAvailable;
   public EnumAvailabilityFactionType faction2Available;
   public EnumAvailabilityFaction factionStance;
   public EnumAvailabilityFaction faction2Stance;
   public int minPlayerLevel;


   public Availability() {
      this.version = VersionCompatibility.ModRev;
      this.dialogAvailable = EnumAvailabilityDialog.Always;
      this.dialog2Available = EnumAvailabilityDialog.Always;
      this.dialog3Available = EnumAvailabilityDialog.Always;
      this.dialog4Available = EnumAvailabilityDialog.Always;
      this.dialogId = -1;
      this.dialog2Id = -1;
      this.dialog3Id = -1;
      this.dialog4Id = -1;
      this.questAvailable = EnumAvailabilityQuest.Always;
      this.quest2Available = EnumAvailabilityQuest.Always;
      this.quest3Available = EnumAvailabilityQuest.Always;
      this.quest4Available = EnumAvailabilityQuest.Always;
      this.questId = -1;
      this.quest2Id = -1;
      this.quest3Id = -1;
      this.quest4Id = -1;
      this.daytime = EnumDayTime.Always;
      this.factionId = -1;
      this.faction2Id = -1;
      this.factionAvailable = EnumAvailabilityFactionType.Always;
      this.faction2Available = EnumAvailabilityFactionType.Always;
      this.factionStance = EnumAvailabilityFaction.Friendly;
      this.faction2Stance = EnumAvailabilityFaction.Friendly;
      this.minPlayerLevel = 0;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.version = compound.getInteger("ModRev");
      VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
      this.dialogAvailable = EnumAvailabilityDialog.values()[compound.getInteger("AvailabilityDialog")];
      this.dialog2Available = EnumAvailabilityDialog.values()[compound.getInteger("AvailabilityDialog2")];
      this.dialog3Available = EnumAvailabilityDialog.values()[compound.getInteger("AvailabilityDialog3")];
      this.dialog4Available = EnumAvailabilityDialog.values()[compound.getInteger("AvailabilityDialog4")];
      this.dialogId = compound.getInteger("AvailabilityDialogId");
      this.dialog2Id = compound.getInteger("AvailabilityDialog2Id");
      this.dialog3Id = compound.getInteger("AvailabilityDialog3Id");
      this.dialog4Id = compound.getInteger("AvailabilityDialog4Id");
      this.questAvailable = EnumAvailabilityQuest.values()[compound.getInteger("AvailabilityQuest")];
      this.quest2Available = EnumAvailabilityQuest.values()[compound.getInteger("AvailabilityQuest2")];
      this.quest3Available = EnumAvailabilityQuest.values()[compound.getInteger("AvailabilityQuest3")];
      this.quest4Available = EnumAvailabilityQuest.values()[compound.getInteger("AvailabilityQuest4")];
      this.questId = compound.getInteger("AvailabilityQuestId");
      this.quest2Id = compound.getInteger("AvailabilityQuest2Id");
      this.quest3Id = compound.getInteger("AvailabilityQuest3Id");
      this.quest4Id = compound.getInteger("AvailabilityQuest4Id");
      this.setFactionAvailability(compound.getInteger("AvailabilityFaction"));
      this.setFactionAvailabilityStance(compound.getInteger("AvailabilityFactionStance"));
      this.setFaction2Availability(compound.getInteger("AvailabilityFaction2"));
      this.setFaction2AvailabilityStance(compound.getInteger("AvailabilityFaction2Stance"));
      this.factionId = compound.getInteger("AvailabilityFactionId");
      this.faction2Id = compound.getInteger("AvailabilityFaction2Id");
      this.daytime = EnumDayTime.values()[compound.getInteger("AvailabilityDayTime")];
      this.minPlayerLevel = compound.getInteger("AvailabilityMinPlayerLevel");
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("ModRev", this.version);
      compound.setInteger("AvailabilityDialog", this.dialogAvailable.ordinal());
      compound.setInteger("AvailabilityDialog2", this.dialog2Available.ordinal());
      compound.setInteger("AvailabilityDialog3", this.dialog3Available.ordinal());
      compound.setInteger("AvailabilityDialog4", this.dialog4Available.ordinal());
      compound.setInteger("AvailabilityDialogId", this.dialogId);
      compound.setInteger("AvailabilityDialog2Id", this.dialog2Id);
      compound.setInteger("AvailabilityDialog3Id", this.dialog3Id);
      compound.setInteger("AvailabilityDialog4Id", this.dialog4Id);
      compound.setInteger("AvailabilityQuest", this.questAvailable.ordinal());
      compound.setInteger("AvailabilityQuest2", this.quest2Available.ordinal());
      compound.setInteger("AvailabilityQuest3", this.quest3Available.ordinal());
      compound.setInteger("AvailabilityQuest4", this.quest4Available.ordinal());
      compound.setInteger("AvailabilityQuestId", this.questId);
      compound.setInteger("AvailabilityQuest2Id", this.quest2Id);
      compound.setInteger("AvailabilityQuest3Id", this.quest3Id);
      compound.setInteger("AvailabilityQuest4Id", this.quest4Id);
      compound.setInteger("AvailabilityFaction", this.factionAvailable.ordinal());
      compound.setInteger("AvailabilityFactionStance", this.factionStance.ordinal());
      compound.setInteger("AvailabilityFactionId", this.factionId);
      compound.setInteger("AvailabilityFaction2", this.faction2Available.ordinal());
      compound.setInteger("AvailabilityFaction2Stance", this.faction2Stance.ordinal());
      compound.setInteger("AvailabilityFaction2Id", this.faction2Id);
      compound.setInteger("AvailabilityDayTime", this.daytime.ordinal());
      compound.setInteger("AvailabilityMinPlayerLevel", this.minPlayerLevel);
      return compound;
   }

   public void setFactionAvailability(int value) {
      this.factionAvailable = EnumAvailabilityFactionType.values()[value];
   }

   public void setFaction2Availability(int value) {
      this.faction2Available = EnumAvailabilityFactionType.values()[value];
   }

   public void setFactionAvailabilityStance(int integer) {
      this.factionStance = EnumAvailabilityFaction.values()[integer];
   }

   public void setFaction2AvailabilityStance(int integer) {
      this.faction2Stance = EnumAvailabilityFaction.values()[integer];
   }

   public boolean isAvailable(EntityPlayer player) {
      long time;
      if(this.daytime == EnumDayTime.Day) {
         time = player.worldObj.getWorldTime() % 24000L;
         if(time > 12000L) {
            return false;
         }
      }

      if(this.daytime == EnumDayTime.Night) {
         time = player.worldObj.getWorldTime() % 24000L;
         if(time < 12000L) {
            return false;
         }
      }

      return !this.dialogAvailable(this.dialogId, this.dialogAvailable, player)?false:(!this.dialogAvailable(this.dialog2Id, this.dialog2Available, player)?false:(!this.dialogAvailable(this.dialog3Id, this.dialog3Available, player)?false:(!this.dialogAvailable(this.dialog4Id, this.dialog4Available, player)?false:(!this.questAvailable(this.questId, this.questAvailable, player)?false:(!this.questAvailable(this.quest2Id, this.quest2Available, player)?false:(!this.questAvailable(this.quest3Id, this.quest3Available, player)?false:(!this.questAvailable(this.quest4Id, this.quest4Available, player)?false:(!this.factionAvailable(this.factionId, this.factionStance, this.factionAvailable, player)?false:(!this.factionAvailable(this.faction2Id, this.faction2Stance, this.faction2Available, player)?false:player.experienceLevel >= this.minPlayerLevel)))))))));
   }

   private boolean factionAvailable(int id, EnumAvailabilityFaction stance, EnumAvailabilityFactionType available, EntityPlayer player) {
      if(available == EnumAvailabilityFactionType.Always) {
         return true;
      } else {
         Faction faction = FactionController.getInstance().getFaction(id);
         if(faction == null) {
            return true;
         } else {
            PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
            int points = data.getFactionPoints(id);
            EnumAvailabilityFaction current = EnumAvailabilityFaction.Neutral;
            if(faction.neutralPoints >= points) {
               current = EnumAvailabilityFaction.Hostile;
            }

            if(faction.friendlyPoints < points) {
               current = EnumAvailabilityFaction.Friendly;
            }

            return available == EnumAvailabilityFactionType.Is && stance == current?true:available == EnumAvailabilityFactionType.IsNot && stance != current;
         }
      }
   }

   public boolean dialogAvailable(int id, EnumAvailabilityDialog en, EntityPlayer player) {
      if(en == EnumAvailabilityDialog.Always) {
         return true;
      } else {
         boolean hasRead = PlayerDataController.instance.getPlayerData(player).dialogData.dialogsRead.contains(Integer.valueOf(id));
         return hasRead && en == EnumAvailabilityDialog.After?true:!hasRead && en == EnumAvailabilityDialog.Before;
      }
   }

   public boolean questAvailable(int id, EnumAvailabilityQuest en, EntityPlayer player) {
      return en == EnumAvailabilityQuest.Always?true:(en == EnumAvailabilityQuest.After && PlayerQuestController.isQuestFinished(player, id)?true:(en == EnumAvailabilityQuest.Before && !PlayerQuestController.isQuestFinished(player, id)?true:(en == EnumAvailabilityQuest.Active && PlayerQuestController.isQuestActive(player, id)?true:en == EnumAvailabilityQuest.NotActive && !PlayerQuestController.isQuestActive(player, id))));
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }
}
