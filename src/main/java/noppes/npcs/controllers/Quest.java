package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.ICompatibilty;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.Server;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.FactionOptions;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.quests.QuestDialog;
import noppes.npcs.quests.QuestInterface;
import noppes.npcs.quests.QuestItem;
import noppes.npcs.quests.QuestKill;
import noppes.npcs.quests.QuestLocation;

public class Quest implements ICompatibilty {

   public int version;
   public int id;
   public EnumQuestType type;
   public EnumQuestRepeat repeat;
   public EnumQuestCompletion completion;
   public String title;
   public QuestCategory category;
   public String logText;
   public String completeText;
   public String completerNpc;
   public int nextQuestid;
   public String nextQuestTitle;
   public PlayerMail mail;
   public String command;
   public QuestInterface questInterface;
   public int rewardExp;
   public NpcMiscInventory rewardItems;
   public boolean randomReward;
   public FactionOptions factionOptions;


   public Quest() {
      this.version = VersionCompatibility.ModRev;
      this.id = -1;
      this.type = EnumQuestType.Item;
      this.repeat = EnumQuestRepeat.NONE;
      this.completion = EnumQuestCompletion.Npc;
      this.title = "default";
      this.logText = "";
      this.completeText = "";
      this.completerNpc = "";
      this.nextQuestid = -1;
      this.nextQuestTitle = "";
      this.mail = new PlayerMail();
      this.command = "";
      this.questInterface = new QuestItem();
      this.rewardExp = 0;
      this.rewardItems = new NpcMiscInventory(9);
      this.randomReward = false;
      this.factionOptions = new FactionOptions();
   }

   public void readNBT(NBTTagCompound compound) {
      this.id = compound.getInteger("Id");
      this.readNBTPartial(compound);
   }

   public void readNBTPartial(NBTTagCompound compound) {
      this.version = compound.getInteger("ModRev");
      VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
      this.setType(EnumQuestType.values()[compound.getInteger("Type")]);
      this.title = compound.getString("Title");
      this.logText = compound.getString("Text");
      this.completeText = compound.getString("CompleteText");
      this.completerNpc = compound.getString("CompleterNpc");
      this.command = compound.getString("QuestCommand");
      this.nextQuestid = compound.getInteger("NextQuestId");
      this.nextQuestTitle = compound.getString("NextQuestTitle");
      if(this.hasNewQuest()) {
         this.nextQuestTitle = this.getNextQuest().title;
      } else {
         this.nextQuestTitle = "";
      }

      this.randomReward = compound.getBoolean("RandomReward");
      this.rewardExp = compound.getInteger("RewardExp");
      this.rewardItems.setFromNBT(compound.getCompoundTag("Rewards"));
      this.completion = EnumQuestCompletion.values()[compound.getInteger("QuestCompletion")];
      this.repeat = EnumQuestRepeat.values()[compound.getInteger("QuestRepeat")];
      this.questInterface.readEntityFromNBT(compound);
      this.factionOptions.readFromNBT(compound.getCompoundTag("QuestFactionPoints"));
      this.mail.readNBT(compound.getCompoundTag("QuestMail"));
   }

   public void setType(EnumQuestType questType) {
      this.type = questType;
      if(this.type == EnumQuestType.Item) {
         this.questInterface = new QuestItem();
      } else if(this.type == EnumQuestType.Dialog) {
         this.questInterface = new QuestDialog();
      } else if(this.type != EnumQuestType.Kill && this.type != EnumQuestType.AreaKill) {
         if(this.type == EnumQuestType.Location) {
            this.questInterface = new QuestLocation();
         }
      } else {
         this.questInterface = new QuestKill();
      }

      if(this.questInterface != null) {
         this.questInterface.questId = this.id;
      }

   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("Id", this.id);
      return this.writeToNBTPartial(compound);
   }

   public NBTTagCompound writeToNBTPartial(NBTTagCompound compound) {
      compound.setInteger("ModRev", this.version);
      compound.setInteger("Type", this.type.ordinal());
      compound.setString("Title", this.title);
      compound.setString("Text", this.logText);
      compound.setString("CompleteText", this.completeText);
      compound.setString("CompleterNpc", this.completerNpc);
      compound.setInteger("NextQuestId", this.nextQuestid);
      compound.setString("NextQuestTitle", this.nextQuestTitle);
      compound.setInteger("RewardExp", this.rewardExp);
      compound.setTag("Rewards", this.rewardItems.getToNBT());
      compound.setString("QuestCommand", this.command);
      compound.setBoolean("RandomReward", this.randomReward);
      compound.setInteger("QuestCompletion", this.completion.ordinal());
      compound.setInteger("QuestRepeat", this.repeat.ordinal());
      this.questInterface.writeEntityToNBT(compound);
      compound.setTag("QuestFactionPoints", this.factionOptions.writeToNBT(new NBTTagCompound()));
      compound.setTag("QuestMail", this.mail.writeNBT());
      return compound;
   }

   public boolean hasNewQuest() {
      return this.getNextQuest() != null;
   }

   public Quest getNextQuest() {
      return QuestController.instance == null?null:(Quest)QuestController.instance.quests.get(Integer.valueOf(this.nextQuestid));
   }

   public boolean complete(EntityPlayer player, QuestData data) {
      if(this.completion == EnumQuestCompletion.Instant) {
         Server.sendData((EntityPlayerMP)player, EnumPacketClient.QUEST_COMPLETION, new Object[]{data.quest.writeToNBT(new NBTTagCompound())});
         return true;
      } else {
         return false;
      }
   }

   public Quest copy() {
      Quest quest = new Quest();
      quest.readNBT(this.writeToNBT(new NBTTagCompound()));
      return quest;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }
}
