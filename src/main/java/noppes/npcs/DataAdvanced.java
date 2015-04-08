package noppes.npcs;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.FactionOptions;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.Lines;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobBard;
import noppes.npcs.roles.JobChunkLoader;
import noppes.npcs.roles.JobConversation;
import noppes.npcs.roles.JobFollower;
import noppes.npcs.roles.JobGuard;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.roles.JobItemGiver;
import noppes.npcs.roles.JobPuppet;
import noppes.npcs.roles.JobSpawner;
import noppes.npcs.roles.RoleBank;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.roles.RolePostman;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.roles.RoleTransporter;

public class DataAdvanced {

   public Lines interactLines = new Lines();
   public Lines worldLines = new Lines();
   public Lines attackLines = new Lines();
   public Lines killedLines = new Lines();
   public Lines killLines = new Lines();
   public String idleSound = "";
   public String angrySound = "";
   public String hurtSound = "minecraft:game.player.hurt";
   public String deathSound = "minecraft:game.player.hurt";
   public String stepSound = "";
   private EntityNPCInterface npc;
   public FactionOptions factions = new FactionOptions();
   public EnumRoleType role;
   public EnumJobType job;
   public boolean attackOtherFactions;
   public boolean defendFaction;
   public boolean disablePitch;


   public DataAdvanced(EntityNPCInterface npc) {
      this.role = EnumRoleType.None;
      this.job = EnumJobType.None;
      this.attackOtherFactions = false;
      this.defendFaction = false;
      this.disablePitch = false;
      this.npc = npc;
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setTag("NpcLines", this.worldLines.writeToNBT());
      compound.setTag("NpcKilledLines", this.killedLines.writeToNBT());
      compound.setTag("NpcInteractLines", this.interactLines.writeToNBT());
      compound.setTag("NpcAttackLines", this.attackLines.writeToNBT());
      compound.setTag("NpcKillLines", this.killLines.writeToNBT());
      compound.setString("NpcIdleSound", this.idleSound);
      compound.setString("NpcAngrySound", this.angrySound);
      compound.setString("NpcHurtSound", this.hurtSound);
      compound.setString("NpcDeathSound", this.deathSound);
      compound.setString("NpcStepSound", this.stepSound);
      compound.setInteger("FactionID", this.npc.getFaction().id);
      compound.setBoolean("AttackOtherFactions", this.attackOtherFactions);
      compound.setBoolean("DefendFaction", this.defendFaction);
      compound.setBoolean("DisablePitch", this.disablePitch);
      compound.setInteger("Role", this.role.ordinal());
      compound.setInteger("NpcJob", this.job.ordinal());
      compound.setTag("FactionPoints", this.factions.writeToNBT(new NBTTagCompound()));
      compound.setTag("NPCDialogOptions", this.nbtDialogs(this.npc.dialogs));
      return compound;
   }

   public void readToNBT(NBTTagCompound compound) {
      this.interactLines.readNBT(compound.getCompoundTag("NpcInteractLines"));
      this.worldLines.readNBT(compound.getCompoundTag("NpcLines"));
      this.attackLines.readNBT(compound.getCompoundTag("NpcAttackLines"));
      this.killedLines.readNBT(compound.getCompoundTag("NpcKilledLines"));
      this.killLines.readNBT(compound.getCompoundTag("NpcKillLines"));
      this.idleSound = compound.getString("NpcIdleSound");
      this.angrySound = compound.getString("NpcAngrySound");
      this.hurtSound = compound.getString("NpcHurtSound");
      this.deathSound = compound.getString("NpcDeathSound");
      this.stepSound = compound.getString("NpcStepSound");
      this.npc.setFaction(compound.getInteger("FactionID"));
      this.npc.faction = this.npc.getFaction();
      this.attackOtherFactions = compound.getBoolean("AttackOtherFactions");
      this.defendFaction = compound.getBoolean("DefendFaction");
      this.disablePitch = compound.getBoolean("DisablePitch");
      this.setRole(compound.getInteger("Role"));
      this.setJob(compound.getInteger("NpcJob"));
      this.factions.readFromNBT(compound.getCompoundTag("FactionPoints"));
      this.npc.dialogs = this.getDialogs(compound.getTagList("NPCDialogOptions", 10));
   }

   private HashMap getDialogs(NBTTagList tagList) {
      HashMap map = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         int slot = nbttagcompound.getInteger("DialogSlot");
         DialogOption option = new DialogOption();
         option.readNBT(nbttagcompound.getCompoundTag("NPCDialog"));
         map.put(Integer.valueOf(slot), option);
      }

      return map;
   }

   private NBTTagList nbtDialogs(HashMap dialogs2) {
      NBTTagList nbttaglist = new NBTTagList();
      Iterator var3 = dialogs2.keySet().iterator();

      while(var3.hasNext()) {
         int slot = ((Integer)var3.next()).intValue();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setInteger("DialogSlot", slot);
         nbttagcompound.setTag("NPCDialog", ((DialogOption)dialogs2.get(Integer.valueOf(slot))).writeNBT());
         nbttaglist.appendTag(nbttagcompound);
      }

      return nbttaglist;
   }

   public Line getInteractLine() {
      return this.interactLines.getLine();
   }

   public Line getAttackLine() {
      return this.attackLines.getLine();
   }

   public Line getKilledLine() {
      return this.killedLines.getLine();
   }

   public Line getKillLine() {
      return this.killLines.getLine();
   }

   public Line getWorldLine() {
      return this.worldLines.getLine();
   }

   public void setRole(int i) {
      if(EnumRoleType.values().length <= i) {
         i -= 2;
      }

      this.role = EnumRoleType.values()[i];
      if(this.role == EnumRoleType.None) {
         this.npc.roleInterface = null;
      } else if(this.role == EnumRoleType.Bank && !(this.npc.roleInterface instanceof RoleBank)) {
         this.npc.roleInterface = new RoleBank(this.npc);
      } else if(this.role == EnumRoleType.Follower && !(this.npc.roleInterface instanceof RoleFollower)) {
         this.npc.roleInterface = new RoleFollower(this.npc);
      } else if(this.role == EnumRoleType.Postman && !(this.npc.roleInterface instanceof RolePostman)) {
         this.npc.roleInterface = new RolePostman(this.npc);
      } else if(this.role == EnumRoleType.Trader && !(this.npc.roleInterface instanceof RoleTrader)) {
         this.npc.roleInterface = new RoleTrader(this.npc);
      } else if(this.role == EnumRoleType.Transporter && !(this.npc.roleInterface instanceof RoleTransporter)) {
         this.npc.roleInterface = new RoleTransporter(this.npc);
      } else if(this.role == EnumRoleType.Companion && !(this.npc.roleInterface instanceof RoleCompanion)) {
         this.npc.roleInterface = new RoleCompanion(this.npc);
      }

   }

   public void setJob(int i) {
      if(this.npc.jobInterface != null && !this.npc.worldObj.isRemote) {
         this.npc.jobInterface.reset();
      }

      this.job = EnumJobType.values()[i % EnumJobType.values().length];
      if(this.job == EnumJobType.None) {
         this.npc.jobInterface = null;
      } else if(this.job == EnumJobType.Bard && !(this.npc.jobInterface instanceof JobBard)) {
         this.npc.jobInterface = new JobBard(this.npc);
      } else if(this.job == EnumJobType.Healer && !(this.npc.jobInterface instanceof JobHealer)) {
         this.npc.jobInterface = new JobHealer(this.npc);
      } else if(this.job == EnumJobType.Guard && !(this.npc.jobInterface instanceof JobGuard)) {
         this.npc.jobInterface = new JobGuard(this.npc);
      } else if(this.job == EnumJobType.ItemGiver && !(this.npc.jobInterface instanceof JobItemGiver)) {
         this.npc.jobInterface = new JobItemGiver(this.npc);
      } else if(this.job == EnumJobType.Follower && !(this.npc.jobInterface instanceof JobFollower)) {
         this.npc.jobInterface = new JobFollower(this.npc);
      } else if(this.job == EnumJobType.Spawner && !(this.npc.jobInterface instanceof JobSpawner)) {
         this.npc.jobInterface = new JobSpawner(this.npc);
      } else if(this.job == EnumJobType.Conversation && !(this.npc.jobInterface instanceof JobConversation)) {
         this.npc.jobInterface = new JobConversation(this.npc);
      } else if(this.job == EnumJobType.ChunkLoader && !(this.npc.jobInterface instanceof JobChunkLoader)) {
         this.npc.jobInterface = new JobChunkLoader(this.npc);
      } else if(this.job == EnumJobType.Puppet && !(this.npc.jobInterface instanceof JobPuppet)) {
         this.npc.jobInterface = new JobPuppet(this.npc);
      }

   }

   public boolean hasWorldLines() {
      return !this.worldLines.isEmpty();
   }
}
