package noppes.npcs.roles;

import com.google.common.collect.HashMultimap;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.constants.EnumCompanionStage;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.roles.companion.CompanionFarmer;
import noppes.npcs.roles.companion.CompanionFoodStats;
import noppes.npcs.roles.companion.CompanionGuard;
import noppes.npcs.roles.companion.CompanionJobInterface;
import noppes.npcs.roles.companion.CompanionTrader;

public class RoleCompanion extends RoleInterface {

   public NpcMiscInventory inventory;
   public String uuid = "";
   public String ownerName = "";
   public Map talents = new TreeMap();
   public boolean canAge = true;
   public long ticksActive = 0L;
   public EnumCompanionStage stage;
   public EntityPlayer owner;
   public int companionID;
   public EnumCompanionJobs job;
   public CompanionJobInterface jobInterface;
   public boolean hasInv;
   public boolean defendOwner;
   public CompanionFoodStats foodstats;
   private int eatingTicks;
   private ItemStack eating;
   private int eatingDelay;
   public int currentExp;


   public RoleCompanion(EntityNPCInterface npc) {
      super(npc);
      this.stage = EnumCompanionStage.FULLGROWN;
      this.owner = null;
      this.job = EnumCompanionJobs.NONE;
      this.jobInterface = null;
      this.hasInv = true;
      this.defendOwner = true;
      this.foodstats = new CompanionFoodStats();
      this.eatingTicks = 20;
      this.eating = null;
      this.eatingDelay = 0;
      this.currentExp = 0;
      this.inventory = new NpcMiscInventory(12);
   }

   public boolean aiShouldExecute() {
      EntityPlayer prev = this.owner;
      this.owner = this.getOwner();
      if(this.jobInterface != null && this.jobInterface.isSelfSufficient()) {
         return true;
      } else {
         if(this.owner == null && !this.uuid.isEmpty()) {
            super.npc.isDead = true;
         } else if(prev != this.owner && this.owner != null) {
            this.ownerName = this.owner.getDisplayName();
            PlayerData data = PlayerDataController.instance.getPlayerData(this.owner);
            if(data.companionID != this.companionID) {
               super.npc.isDead = true;
            }
         }

         return this.owner != null;
      }
   }

   public void aiUpdateTask() {
      if(this.owner != null && (this.jobInterface == null || !this.jobInterface.isSelfSufficient())) {
         this.foodstats.onUpdate(super.npc);
      }

      if(this.foodstats.getFoodLevel() >= 18) {
         super.npc.stats.healthRegen = 0;
         super.npc.stats.combatRegen = 0;
      }

      if(this.foodstats.needFood() && this.isSitting()) {
         if(this.eatingDelay > 0) {
            --this.eatingDelay;
            return;
         }

         ItemStack prev = this.eating;
         this.eating = this.getFood();
         if(prev != null && this.eating == null) {
            super.npc.setRoleDataWatcher("");
         }

         if(prev == null && this.eating != null) {
            super.npc.setRoleDataWatcher("eating");
            this.eatingTicks = 20;
         }

         if(this.isEating()) {
            this.doEating();
         }
      } else if(this.eating != null && !this.isSitting()) {
         this.eating = null;
         this.eatingDelay = 20;
         super.npc.setRoleDataWatcher("");
      }

      ++this.ticksActive;
      if(this.canAge && this.stage != EnumCompanionStage.FULLGROWN) {
         if(this.stage == EnumCompanionStage.BABY && this.ticksActive > (long)EnumCompanionStage.CHILD.matureAge) {
            this.matureTo(EnumCompanionStage.CHILD);
         } else if(this.stage == EnumCompanionStage.CHILD && this.ticksActive > (long)EnumCompanionStage.TEEN.matureAge) {
            this.matureTo(EnumCompanionStage.TEEN);
         } else if(this.stage == EnumCompanionStage.TEEN && this.ticksActive > (long)EnumCompanionStage.ADULT.matureAge) {
            this.matureTo(EnumCompanionStage.ADULT);
         } else if(this.stage == EnumCompanionStage.ADULT && this.ticksActive > (long)EnumCompanionStage.FULLGROWN.matureAge) {
            this.matureTo(EnumCompanionStage.FULLGROWN);
         }
      }

   }

   public void clientUpdate() {
      if(super.npc.getRoleDataWatcher().equals("eating")) {
         this.eating = this.getFood();
         if(this.isEating()) {
            this.doEating();
         }
      } else if(this.eating != null) {
         this.eating = null;
      }

   }

   private void doEating() {
      Random rand;
      if(super.npc.worldObj.isRemote) {
         rand = super.npc.getRNG();

         for(int j = 0; j < 2; ++j) {
            Vec3 vec3 = Vec3.createVectorHelper(((double)rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3.rotateAroundX(-super.npc.rotationPitch * 3.1415927F / 180.0F);
            vec3.rotateAroundY(-super.npc.renderYawOffset * 3.1415927F / 180.0F);
            Vec3 vec31 = Vec3.createVectorHelper(((double)rand.nextFloat() - 0.5D) * 0.3D, (double)(-rand.nextFloat()) * 0.6D - 0.3D, (double)(super.npc.width / 2.0F) + 0.1D);
            vec31.rotateAroundX(-super.npc.rotationPitch * 3.1415927F / 180.0F);
            vec31.rotateAroundY(-super.npc.renderYawOffset * 3.1415927F / 180.0F);
            vec31 = vec31.addVector(super.npc.posX, super.npc.posY + (double)super.npc.height + 0.1D, super.npc.posZ);
            String s = "iconcrack_" + Item.getIdFromItem(this.eating.getItem());
            if(this.eating.getHasSubtypes()) {
               s = s + "_" + this.eating.getMetadata();
            }

            super.npc.worldObj.spawnParticle(s, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.0D, vec3.zCoord);
         }
      } else {
         --this.eatingTicks;
         if(this.eatingTicks <= 0) {
            if(this.inventory.decrStackSize(this.eating, 1)) {
               ItemFood var6 = (ItemFood)this.eating.getItem();
               this.foodstats.onFoodEaten(var6, this.eating);
               super.npc.playSound("random.burp", 0.5F, super.npc.getRNG().nextFloat() * 0.1F + 0.9F);
            }

            this.eatingDelay = 20;
            super.npc.setRoleDataWatcher("");
            this.eating = null;
         } else if(this.eatingTicks > 3 && this.eatingTicks % 2 == 0) {
            rand = super.npc.getRNG();
            super.npc.playSound("random.eat", 0.5F + 0.5F * (float)rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
         }
      }

   }

   public void matureTo(EnumCompanionStage stage) {
      this.stage = stage;
      EntityCustomNpc npc = (EntityCustomNpc)super.npc;
      npc.ai.animationType = stage.animation;
      if(stage == EnumCompanionStage.BABY) {
         npc.modelData.arms.setScale(0.5F, 0.5F, 0.5F);
         npc.modelData.legs.setScale(0.5F, 0.5F, 0.5F);
         npc.modelData.body.setScale(0.5F, 0.5F, 0.5F);
         npc.modelData.head.setScale(0.7F, 0.7F, 0.7F);
         npc.ai.onAttack = 1;
         npc.ai.setWalkingSpeed(3);
         if(!this.talents.containsKey(EnumCompanionTalent.INVENTORY)) {
            this.talents.put(EnumCompanionTalent.INVENTORY, Integer.valueOf(0));
         }
      }

      if(stage == EnumCompanionStage.CHILD) {
         npc.modelData.arms.setScale(0.6F, 0.6F, 0.6F);
         npc.modelData.legs.setScale(0.6F, 0.6F, 0.6F);
         npc.modelData.body.setScale(0.6F, 0.6F, 0.6F);
         npc.modelData.head.setScale(0.8F, 0.8F, 0.8F);
         npc.ai.onAttack = 0;
         npc.ai.setWalkingSpeed(4);
         if(!this.talents.containsKey(EnumCompanionTalent.SWORD)) {
            this.talents.put(EnumCompanionTalent.SWORD, Integer.valueOf(0));
         }
      }

      if(stage == EnumCompanionStage.TEEN) {
         npc.modelData.arms.setScale(0.8F, 0.8F, 0.8F);
         npc.modelData.legs.setScale(0.8F, 0.8F, 0.8F);
         npc.modelData.body.setScale(0.8F, 0.8F, 0.8F);
         npc.modelData.head.setScale(0.9F, 0.9F, 0.9F);
         npc.ai.onAttack = 0;
         npc.ai.setWalkingSpeed(5);
         if(!this.talents.containsKey(EnumCompanionTalent.ARMOR)) {
            this.talents.put(EnumCompanionTalent.ARMOR, Integer.valueOf(0));
         }
      }

      if(stage == EnumCompanionStage.ADULT || stage == EnumCompanionStage.FULLGROWN) {
         npc.modelData.arms.setScale(1.0F, 1.0F, 1.0F);
         npc.modelData.legs.setScale(1.0F, 1.0F, 1.0F);
         npc.modelData.body.setScale(1.0F, 1.0F, 1.0F);
         npc.modelData.head.setScale(1.0F, 1.0F, 1.0F);
         npc.ai.onAttack = 0;
         npc.ai.setWalkingSpeed(5);
      }

   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setTag("CompanionInventory", this.inventory.getToNBT());
      compound.setString("CompanionOwner", this.uuid);
      compound.setString("CompanionOwnerName", this.ownerName);
      compound.setInteger("CompanionStage", this.stage.ordinal());
      compound.setInteger("CompanionExp", this.currentExp);
      compound.setBoolean("CompanionCanAge", this.canAge);
      compound.setLong("CompanionAge", this.ticksActive);
      compound.setBoolean("CompanionHasInv", this.hasInv);
      compound.setBoolean("CompanionDefendOwner", this.defendOwner);
      this.foodstats.writeNBT(compound);
      compound.setInteger("CompanionJob", this.job.ordinal());
      if(this.jobInterface != null) {
         compound.setTag("CompanionJobData", this.jobInterface.getNBT());
      }

      NBTTagList list = new NBTTagList();
      Iterator var3 = this.talents.keySet().iterator();

      while(var3.hasNext()) {
         EnumCompanionTalent talent = (EnumCompanionTalent)var3.next();
         NBTTagCompound c = new NBTTagCompound();
         c.setInteger("Talent", talent.ordinal());
         c.setInteger("Exp", ((Integer)this.talents.get(talent)).intValue());
         list.appendTag(c);
      }

      compound.setTag("CompanionTalents", list);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.inventory.setFromNBT(compound.getCompoundTag("CompanionInventory"));
      this.uuid = compound.getString("CompanionOwner");
      this.ownerName = compound.getString("CompanionOwnerName");
      this.stage = EnumCompanionStage.values()[compound.getInteger("CompanionStage")];
      this.currentExp = compound.getInteger("CompanionExp");
      this.canAge = compound.getBoolean("CompanionCanAge");
      this.ticksActive = compound.getLong("CompanionAge");
      this.hasInv = compound.getBoolean("CompanionHasInv");
      this.defendOwner = compound.getBoolean("CompanionDefendOwner");
      this.foodstats.readNBT(compound);
      NBTTagList list = compound.getTagList("CompanionTalents", 10);
      TreeMap talents = new TreeMap();

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound c = list.getCompoundTagAt(i);
         EnumCompanionTalent talent = EnumCompanionTalent.values()[c.getInteger("Talent")];
         talents.put(talent, Integer.valueOf(c.getInteger("Exp")));
      }

      this.talents = talents;
      this.setJob(compound.getInteger("CompanionJob"));
      if(this.jobInterface != null) {
         this.jobInterface.setNBT(compound.getCompoundTag("CompanionJobData"));
      }

      this.setStats();
   }

   private void setJob(int i) {
      this.job = EnumCompanionJobs.values()[i];
      if(this.job == EnumCompanionJobs.SHOP) {
         this.jobInterface = new CompanionTrader();
      } else if(this.job == EnumCompanionJobs.FARMER) {
         this.jobInterface = new CompanionFarmer();
      } else if(this.job == EnumCompanionJobs.GUARD) {
         this.jobInterface = new CompanionGuard();
      } else {
         this.jobInterface = null;
      }

      if(this.jobInterface != null) {
         this.jobInterface.npc = super.npc;
      }

   }

   public void interact(EntityPlayer player) {
      if(player != null && this.job == EnumCompanionJobs.SHOP) {
         ((CompanionTrader)this.jobInterface).interact(player);
      }

      if(player == this.owner && super.npc.isEntityAlive() && !super.npc.isAttacking()) {
         if(player.isSneaking()) {
            this.openGui(player);
         } else {
            this.setSitting(!this.isSitting());
         }

      }
   }

   public int getTotalLevel() {
      int level = 0;

      EnumCompanionTalent talent;
      for(Iterator var2 = this.talents.keySet().iterator(); var2.hasNext(); level += this.getTalentLevel(talent)) {
         talent = (EnumCompanionTalent)var2.next();
      }

      return level;
   }

   public int getMaxExp() {
      return 500 + this.getTotalLevel() * 200;
   }

   public void addExp(int exp) {
      if(this.canAddExp(exp)) {
         this.currentExp += exp;
      }

   }

   public boolean canAddExp(int exp) {
      int newExp = this.currentExp + exp;
      return newExp >= 0 && newExp < this.getMaxExp();
   }

   public void gainExp(int chance) {
      if(super.npc.getRNG().nextInt(chance) == 0) {
         this.addExp(1);
      }

   }

   private void openGui(EntityPlayer player) {
      NoppesUtilServer.sendOpenGui(player, EnumGuiType.Companion, super.npc);
   }

   public EntityPlayer getOwner() {
      if(this.uuid != null && !this.uuid.isEmpty()) {
         try {
            UUID ex = UUID.fromString(this.uuid);
            if(ex != null) {
               return super.npc.worldObj.getPlayerEntityByUUID(ex);
            }
         } catch (IllegalArgumentException var2) {
            ;
         }

         return null;
      } else {
         return null;
      }
   }

   public void setOwner(EntityPlayer player) {
      this.uuid = player.getUniqueID().toString();
   }

   public boolean hasTalent(EnumCompanionTalent talent) {
      return this.getTalentLevel(talent) > 0;
   }

   public int getTalentLevel(EnumCompanionTalent talent) {
      if(!this.talents.containsKey(talent)) {
         return 0;
      } else {
         int exp = ((Integer)this.talents.get(talent)).intValue();
         return exp >= 5000?5:(exp >= 3000?4:(exp >= 1700?3:(exp >= 1000?2:(exp >= 400?1:0))));
      }
   }

   public Integer getNextLevel(EnumCompanionTalent talent) {
      if(!this.talents.containsKey(talent)) {
         return Integer.valueOf(0);
      } else {
         int exp = ((Integer)this.talents.get(talent)).intValue();
         return exp < 400?Integer.valueOf(400):(exp < 1000?Integer.valueOf(700):(exp < 1700?Integer.valueOf(1700):(exp < 3000?Integer.valueOf(3000):Integer.valueOf(5000))));
      }
   }

   public void levelSword() {
      if(this.talents.containsKey(EnumCompanionTalent.SWORD)) {
         ;
      }
   }

   public void levelTalent(EnumCompanionTalent talent, int exp) {
      if(this.talents.containsKey(EnumCompanionTalent.SWORD)) {
         this.talents.put(talent, Integer.valueOf(exp + ((Integer)this.talents.get(talent)).intValue()));
      }
   }

   public int getExp(EnumCompanionTalent talent) {
      return this.talents.containsKey(talent)?((Integer)this.talents.get(talent)).intValue():-1;
   }

   public void setExp(EnumCompanionTalent talent, int exp) {
      this.talents.put(talent, Integer.valueOf(exp));
   }

   private boolean isWeapon(ItemStack item) {
      return item != null && item.getItem() != null?item.getItem() instanceof ItemSword || item.getItem() instanceof ItemBow || item.getItem() == Item.getItemFromBlock(Blocks.cobblestone):false;
   }

   public boolean canWearWeapon(ItemStack item) {
      return item != null && item.getItem() != null?(item.getItem() instanceof ItemSword?this.canWearSword(item):(item.getItem() instanceof ItemBow?this.getTalentLevel(EnumCompanionTalent.RANGED) > 2:(item.getItem() == Item.getItemFromBlock(Blocks.cobblestone)?this.getTalentLevel(EnumCompanionTalent.RANGED) > 1:false))):false;
   }

   public boolean canWearArmor(ItemStack item) {
      int level = this.getTalentLevel(EnumCompanionTalent.ARMOR);
      if(item != null && item.getItem() instanceof ItemArmor && level > 0) {
         if(level >= 5) {
            return true;
         } else {
            ItemArmor armor = (ItemArmor)item.getItem();
            int reduction = ((Integer)ObfuscationReflectionHelper.getPrivateValue(ArmorMaterial.class, armor.getArmorMaterial(), 5)).intValue();
            return reduction <= 5 && level >= 1?true:(reduction <= 7 && level >= 2?true:(reduction <= 15 && level >= 3?true:reduction <= 33 && level >= 4));
         }
      } else {
         return false;
      }
   }

   public boolean canWearSword(ItemStack item) {
      int level = this.getTalentLevel(EnumCompanionTalent.SWORD);
      return item != null && item.getItem() instanceof ItemSword && level > 0?(level >= 5?true:this.getSwordDamage(item) - (double)level < 4.0D):false;
   }

   private double getSwordDamage(ItemStack item) {
      if(item != null && item.getItem() instanceof ItemSword) {
         HashMultimap map = (HashMultimap)item.getAttributeModifiers();
         Iterator iterator = map.entries().iterator();

         Entry entry;
         do {
            if(!iterator.hasNext()) {
               return 0.0D;
            }

            entry = (Entry)iterator.next();
         } while(!entry.getKey().equals(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName()));

         AttributeModifier mod = (AttributeModifier)entry.getValue();
         return mod.getAmount();
      } else {
         return 0.0D;
      }
   }

   public void setStats() {
      ItemStack weapon = super.npc.inventory.getWeapon();
      super.npc.stats.setAttackStrength((int)(1.0D + this.getSwordDamage(weapon)));
      super.npc.stats.healthRegen = 0;
      super.npc.stats.combatRegen = 0;
      int ranged = this.getTalentLevel(EnumCompanionTalent.RANGED);
      if(ranged > 0 && weapon != null) {
         if(ranged > 0 && weapon.getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
            super.npc.inventory.setProjectile(weapon);
         }

         if(ranged > 0 && weapon.getItem() instanceof ItemBow) {
            super.npc.inventory.setProjectile(new ItemStack(Items.arrow));
         }
      }

      this.inventory.setSize(2 + this.getTalentLevel(EnumCompanionTalent.INVENTORY) * 2);
   }

   public void setSelfsuficient(boolean bo) {
      if(this.owner != null && (this.jobInterface == null || bo != this.jobInterface.isSelfSufficient())) {
         PlayerData data = PlayerDataController.instance.getPlayerData(this.owner);
         if(bo || !data.hasCompanion()) {
            data.setCompanion(bo?null:super.npc);
            if(this.job == EnumCompanionJobs.GUARD) {
               ((CompanionGuard)this.jobInterface).isStanding = bo;
            } else if(this.job == EnumCompanionJobs.FARMER) {
               ((CompanionFarmer)this.jobInterface).isStanding = bo;
            }

         }
      }
   }

   public void setSitting(boolean sit) {
      if(sit) {
         super.npc.ai.animationType = EnumAnimation.SITTING;
         super.npc.ai.onAttack = 3;
         super.npc.ai.startPos = new int[]{MathHelper.floor_double(super.npc.posX), MathHelper.floor_double(super.npc.posY), MathHelper.floor_double(super.npc.posZ)};
         super.npc.getNavigator().clearPathEntity();
         super.npc.setPositionAndUpdate((double)super.npc.ai.startPos[0] + 0.5D, super.npc.posY, (double)super.npc.ai.startPos[2] + 0.5D);
      } else {
         super.npc.ai.animationType = this.stage.animation;
         super.npc.ai.onAttack = 0;
      }

      super.npc.setResponse();
   }

   public boolean isSitting() {
      return super.npc.ai.animationType == EnumAnimation.SITTING;
   }

   public float applyArmorCalculations(DamageSource source, float damage) {
      if(this.hasInv && this.getTalentLevel(EnumCompanionTalent.ARMOR) > 0) {
         if(!source.isUnblockable()) {
            this.damageArmor(damage);
            int i = 25 - this.getTotalArmorValue();
            float f1 = damage * (float)i;
            damage = f1 / 25.0F;
         }

         return damage;
      } else {
         return damage;
      }
   }

   private void damageArmor(float damage) {
      damage /= 4.0F;
      if(damage < 1.0F) {
         damage = 1.0F;
      }

      boolean hasArmor = false;
      Iterator var3 = super.npc.inventory.armor.keySet().iterator();

      while(var3.hasNext()) {
         int slot = ((Integer)var3.next()).intValue();
         ItemStack item = (ItemStack)super.npc.inventory.armor.get(Integer.valueOf(slot));
         if(item != null && item.getItem() instanceof ItemArmor) {
            hasArmor = true;
            item.damageItem((int)damage, super.npc);
            if(item.stackSize <= 0) {
               super.npc.inventory.armor.remove(Integer.valueOf(slot));
            }
         }
      }

      this.gainExp(hasArmor?4:8);
   }

   public int getTotalArmorValue() {
      int armorValue = 0;
      Iterator var2 = super.npc.inventory.getArmor().values().iterator();

      while(var2.hasNext()) {
         ItemStack armor = (ItemStack)var2.next();
         if(armor != null && armor.getItem() instanceof ItemArmor) {
            armorValue += ((ItemArmor)armor.getItem()).damageReduceAmount;
         }
      }

      return armorValue;
   }

   public boolean isFollowing() {
      return this.jobInterface != null && this.jobInterface.isSelfSufficient()?false:this.owner != null && !this.isSitting();
   }

   public boolean defendOwner() {
      return this.defendOwner && this.owner != null && this.stage != EnumCompanionStage.BABY && (this.jobInterface == null || !this.jobInterface.isSelfSufficient());
   }

   public int followRange() {
      return 9 + 12 * this.stage.ordinal();
   }

   public boolean hasOwner() {
      return !this.uuid.isEmpty();
   }

   public void addMovementStat(double x, double y, double z) {
      int i = Math.round(MathHelper.sqrt_double(x * x + y * y + z * z) * 100.0F);
      if(super.npc.isAttacking()) {
         this.foodstats.addExhaustion(0.04F * (float)i * 0.01F);
      } else {
         this.foodstats.addExhaustion(0.02F * (float)i * 0.01F);
      }

   }

   private ItemStack getFood() {
      ArrayList food = new ArrayList(this.inventory.items.values());
      Iterator ite = food.iterator();
      int i = -1;

      while(ite.hasNext()) {
         ItemStack is = (ItemStack)ite.next();
         if(is != null && is.getItem() instanceof ItemFood) {
            int is1 = ((ItemFood)is.getItem()).getHealAmount(is);
            if(i == -1 || is1 < i) {
               i = is1;
            }
         } else {
            ite.remove();
         }
      }

      Iterator is2 = food.iterator();

      ItemStack is3;
      do {
         if(!is2.hasNext()) {
            return null;
         }

         is3 = (ItemStack)is2.next();
      } while(((ItemFood)is3.getItem()).getHealAmount(is3) != i);

      return is3;
   }

   public ItemStack getHeldItem() {
      return this.eating != null?this.eating:super.npc.inventory.getWeapon();
   }

   public boolean isEating() {
      return this.eating != null;
   }

   public boolean hasInv() {
      return !this.hasInv?false:this.hasTalent(EnumCompanionTalent.INVENTORY) || this.hasTalent(EnumCompanionTalent.ARMOR) || this.hasTalent(EnumCompanionTalent.SWORD);
   }

   public void attackedEntity(Entity entity) {
      ItemStack weapon = super.npc.inventory.getWeapon();
      this.gainExp(weapon == null?8:4);
      if(weapon != null) {
         weapon.damageItem(1, super.npc);
         if(weapon.stackSize <= 0) {
            super.npc.inventory.setWeapon((ItemStack)null);
         }

      }
   }

   public void addTalentExp(EnumCompanionTalent talent, int exp) {
      if(this.talents.containsKey(talent)) {
         exp += ((Integer)this.talents.get(talent)).intValue();
      }

      this.talents.put(talent, Integer.valueOf(exp));
   }
}
