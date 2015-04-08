package noppes.npcs.controllers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.PlayerBankData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerDialogData;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.controllers.PlayerItemGiverData;
import noppes.npcs.controllers.PlayerMailData;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.PlayerTransportData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;

public class PlayerData implements IExtendedEntityProperties {

   public PlayerDialogData dialogData = new PlayerDialogData();
   public PlayerBankData bankData = new PlayerBankData();
   public PlayerQuestData questData = new PlayerQuestData();
   public PlayerTransportData transportData = new PlayerTransportData();
   public PlayerFactionData factionData = new PlayerFactionData();
   public PlayerItemGiverData itemgiverData = new PlayerItemGiverData();
   public PlayerMailData mailData = new PlayerMailData();
   public EntityNPCInterface editingNpc;
   public NBTTagCompound cloned;
   public EntityPlayer player;
   public String playername = "";
   public String uuid = "";
   private EntityNPCInterface activeCompanion = null;
   public int companionID = 0;


   public void saveNBTData(NBTTagCompound compound) {
      PlayerDataController.instance.savePlayerData(this);
   }

   public void loadNBTData(NBTTagCompound compound) {
      NBTTagCompound data = PlayerDataController.instance.loadPlayerData(this.player.getPersistentID().toString());
      if(data.hasNoTags()) {
         data = PlayerDataController.instance.loadPlayerDataOld(this.player.getCommandSenderName());
      }

      this.setNBT(data);
   }

   public void setNBT(NBTTagCompound data) {
      this.dialogData.loadNBTData(data);
      this.bankData.loadNBTData(data);
      this.questData.loadNBTData(data);
      this.transportData.loadNBTData(data);
      this.factionData.loadNBTData(data);
      this.itemgiverData.loadNBTData(data);
      this.mailData.loadNBTData(data);
      this.playername = data.getString("PlayerName");
      this.uuid = data.getString("UUID");
      this.companionID = data.getInteger("PlayerCompanionId");
      if(data.hasKey("PlayerCompanion") && !this.hasCompanion()) {
         EntityCustomNpc npc = new EntityCustomNpc(this.player.worldObj);
         npc.readEntityFromNBT(data.getCompoundTag("PlayerCompanion"));
         npc.setPosition(this.player.posX, this.player.posY, this.player.posZ);
         this.setCompanion(npc);
         ((RoleCompanion)npc.roleInterface).setSitting(false);
         this.player.worldObj.spawnEntityInWorld(npc);
      }

   }

   public NBTTagCompound getNBT() {
      if(this.player != null) {
         this.playername = this.player.getCommandSenderName();
         this.uuid = this.player.getPersistentID().toString();
      }

      NBTTagCompound compound = new NBTTagCompound();
      this.dialogData.saveNBTData(compound);
      this.bankData.saveNBTData(compound);
      this.questData.saveNBTData(compound);
      this.transportData.saveNBTData(compound);
      this.factionData.saveNBTData(compound);
      this.itemgiverData.saveNBTData(compound);
      this.mailData.saveNBTData(compound);
      compound.setString("PlayerName", this.playername);
      compound.setString("UUID", this.uuid);
      compound.setInteger("PlayerCompanionId", this.companionID);
      if(this.hasCompanion()) {
         NBTTagCompound nbt = new NBTTagCompound();
         if(this.activeCompanion.writeToNBTOptional(nbt)) {
            compound.setTag("PlayerCompanion", nbt);
         }
      }

      return compound;
   }

   public void init(Entity entity, World world) {}

   public boolean hasCompanion() {
      return this.activeCompanion != null && !this.activeCompanion.isDead;
   }

   public void setCompanion(EntityNPCInterface npc) {
      if(npc == null || npc.advanced.role == EnumRoleType.Companion) {
         ++this.companionID;
         this.activeCompanion = npc;
         if(npc != null) {
            ((RoleCompanion)npc.roleInterface).companionID = this.companionID;
         }

         this.saveNBTData((NBTTagCompound)null);
      }
   }
}
