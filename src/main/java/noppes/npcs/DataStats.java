package noppes.npcs;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.Resistances;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.constants.EnumPotionType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.ValueUtil;

public class DataStats {

   private int attackStrength = 5;
   public int attackSpeed = 20;
   public int attackRange = 2;
   public int knockback = 0;
   public int minDelay = 20;
   public int maxDelay = 40;
   public int rangedRange = 15;
   public int fireRate = 5;
   public int burstCount = 1;
   public int shotCount = 1;
   public int accuracy = 60;
   public int aggroRange = 16;
   public EnumPotionType potionType;
   public int potionDuration;
   public int potionAmp;
   public int maxHealth;
   public int respawnTime;
   public int spawnCycle;
   public boolean hideKilledBody;
   public boolean canDespawn;
   public Resistances resistances;
   public boolean immuneToFire;
   public boolean potionImmune;
   public boolean canDrown;
   public boolean burnInSun;
   public boolean noFallDamage;
   public int healthRegen;
   public int combatRegen;
   public int pDamage;
   public int pImpact;
   public int pSize;
   public int pSpeed;
   public int pArea;
   public int pDur;
   public boolean pPhysics;
   public boolean pXlr8;
   public boolean pGlows;
   public boolean pExplode;
   public boolean pRender3D;
   public boolean pSpin;
   public boolean pStick;
   public EnumPotionType pEffect;
   public EnumParticleType pTrail;
   public int pEffAmp;
   public String fireSound;
   public boolean aimWhileShooting;
   public EnumCreatureAttribute creatureType;
   private EntityNPCInterface npc;
   public boolean attackInvisible;


   public DataStats(EntityNPCInterface npc) {
      this.potionType = EnumPotionType.None;
      this.potionDuration = 5;
      this.potionAmp = 0;
      this.maxHealth = 20;
      this.respawnTime = 20;
      this.spawnCycle = 0;
      this.hideKilledBody = false;
      this.canDespawn = false;
      this.resistances = new Resistances();
      this.immuneToFire = false;
      this.potionImmune = false;
      this.canDrown = true;
      this.burnInSun = false;
      this.noFallDamage = false;
      this.healthRegen = 1;
      this.combatRegen = 0;
      this.pDamage = 4;
      this.pImpact = 0;
      this.pSize = 5;
      this.pSpeed = 10;
      this.pArea = 0;
      this.pDur = 5;
      this.pPhysics = true;
      this.pXlr8 = false;
      this.pGlows = false;
      this.pExplode = false;
      this.pRender3D = false;
      this.pSpin = false;
      this.pStick = false;
      this.pEffect = EnumPotionType.None;
      this.pTrail = EnumParticleType.None;
      this.pEffAmp = 0;
      this.fireSound = "random.bow";
      this.aimWhileShooting = false;
      this.creatureType = EnumCreatureAttribute.UNDEFINED;
      this.attackInvisible = false;
      this.npc = npc;
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setTag("Resistances", this.resistances.writeToNBT());
      compound.setInteger("MaxHealth", this.maxHealth);
      compound.setInteger("AggroRange", this.aggroRange);
      compound.setBoolean("HideBodyWhenKilled", this.hideKilledBody);
      compound.setInteger("RespawnTime", this.respawnTime);
      compound.setInteger("SpawnCycle", this.spawnCycle);
      compound.setInteger("CreatureType", this.creatureType.ordinal());
      compound.setInteger("HealthRegen", this.healthRegen);
      compound.setInteger("CombatRegen", this.combatRegen);
      compound.setInteger("AttackStrenght", this.attackStrength);
      compound.setInteger("AttackRange", this.attackRange);
      compound.setInteger("AttackSpeed", this.attackSpeed);
      compound.setInteger("KnockBack", this.knockback);
      compound.setInteger("PotionEffect", this.potionType.ordinal());
      compound.setInteger("PotionDuration", this.potionDuration);
      compound.setInteger("PotionAmp", this.potionAmp);
      compound.setInteger("MaxFiringRange", this.rangedRange);
      compound.setInteger("FireRate", this.fireRate);
      compound.setInteger("minDelay", this.minDelay);
      compound.setInteger("maxDelay", this.maxDelay);
      compound.setInteger("BurstCount", this.burstCount);
      compound.setInteger("ShotCount", this.shotCount);
      compound.setInteger("Accuracy", this.accuracy);
      compound.setInteger("pDamage", this.pDamage);
      compound.setInteger("pImpact", this.pImpact);
      compound.setInteger("pSize", this.pSize);
      compound.setInteger("pSpeed", this.pSpeed);
      compound.setInteger("pArea", this.pArea);
      compound.setInteger("pDur", this.pDur);
      compound.setBoolean("pPhysics", this.pPhysics);
      compound.setBoolean("pXlr8", this.pXlr8);
      compound.setBoolean("pGlows", this.pGlows);
      compound.setBoolean("pExplode", this.pExplode);
      compound.setBoolean("pRender3D", this.pRender3D);
      compound.setBoolean("pSpin", this.pSpin);
      compound.setBoolean("pStick", this.pStick);
      compound.setInteger("pEffect", this.pEffect.ordinal());
      compound.setInteger("pTrail", this.pTrail.ordinal());
      compound.setInteger("pEffAmp", this.pEffAmp);
      compound.setString("FiringSound", this.fireSound);
      compound.setBoolean("AimWhileShooting", this.aimWhileShooting);
      compound.setBoolean("ImmuneToFire", this.immuneToFire);
      compound.setBoolean("PotionImmune", this.potionImmune);
      compound.setBoolean("CanDrown", this.canDrown);
      compound.setBoolean("BurnInSun", this.burnInSun);
      compound.setBoolean("NoFallDamage", this.noFallDamage);
      compound.setBoolean("CanDespawn", this.canDespawn);
      compound.setBoolean("AttackInvisible", this.attackInvisible);
      return compound;
   }

   public void readToNBT(NBTTagCompound compound) {
      this.resistances.readToNBT(compound.getCompoundTag("Resistances"));
      this.setMaxHealth(compound.getInteger("MaxHealth"));
      this.hideKilledBody = compound.getBoolean("HideBodyWhenKilled");
      this.aggroRange = compound.getInteger("AggroRange");
      this.respawnTime = compound.getInteger("RespawnTime");

      if (this.npc != null && this.npc.linkedName != null && this.npc.linkedName.equals("TaintedHero"))
         this.respawnTime = 0;

      this.spawnCycle = compound.getInteger("SpawnCycle");
      this.creatureType = EnumCreatureAttribute.values()[compound.getInteger("CreatureType") % EnumPotionType.values().length];
      this.healthRegen = compound.getInteger("HealthRegen");
      this.combatRegen = compound.getInteger("CombatRegen");
      this.setAttackStrength(compound.getInteger("AttackStrenght"));
      this.attackSpeed = compound.getInteger("AttackSpeed");
      this.attackRange = compound.getInteger("AttackRange");
      this.knockback = compound.getInteger("KnockBack");
      this.potionType = EnumPotionType.values()[compound.getInteger("PotionEffect") % EnumPotionType.values().length];
      this.potionDuration = compound.getInteger("PotionDuration");
      this.potionAmp = compound.getInteger("PotionAmp");
      this.rangedRange = compound.getInteger("MaxFiringRange");
      this.fireRate = compound.getInteger("FireRate");
      this.minDelay = ValueUtil.CorrectInt(compound.getInteger("minDelay"), 1, 9999);
      this.maxDelay = ValueUtil.CorrectInt(compound.getInteger("maxDelay"), 1, 9999);
      this.burstCount = compound.getInteger("BurstCount");
      this.shotCount = ValueUtil.CorrectInt(compound.getInteger("ShotCount"), 1, 10);
      this.accuracy = compound.getInteger("Accuracy");
      this.pDamage = compound.getInteger("pDamage");
      this.pImpact = compound.getInteger("pImpact");
      this.pSize = compound.getInteger("pSize");
      this.pSpeed = compound.getInteger("pSpeed");
      this.pArea = compound.getInteger("pArea");
      this.pDur = compound.getInteger("pDur");
      this.pPhysics = compound.getBoolean("pPhysics");
      this.pXlr8 = compound.getBoolean("pXlr8");
      this.pGlows = compound.getBoolean("pGlows");
      this.pExplode = compound.getBoolean("pExplode");
      this.pRender3D = compound.getBoolean("pRender3D");
      this.pSpin = compound.getBoolean("pSpin");
      this.pStick = compound.getBoolean("pStick");
      this.pEffect = EnumPotionType.values()[compound.getInteger("pEffect") % EnumPotionType.values().length];
      this.pTrail = EnumParticleType.values()[compound.getInteger("pTrail") % EnumParticleType.values().length];
      this.pEffAmp = compound.getInteger("pEffAmp");
      this.fireSound = compound.getString("FiringSound");
      this.aimWhileShooting = compound.getBoolean("AimWhileShooting");
      this.immuneToFire = compound.getBoolean("ImmuneToFire");
      this.potionImmune = compound.getBoolean("PotionImmune");
      this.canDrown = compound.getBoolean("CanDrown");
      this.burnInSun = compound.getBoolean("BurnInSun");
      this.noFallDamage = compound.getBoolean("NoFallDamage");
      this.canDespawn = compound.getBoolean("CanDespawn");
      this.attackInvisible = compound.getBoolean("AttackInvisible");
      this.npc.setImmuneToFire(this.immuneToFire);
   }

   public int getAttackStrength() {
      return this.attackStrength;
   }

   public void setAttackStrength(int strength) {
      this.attackStrength = strength;
      this.npc.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)this.attackStrength);
   }

   public void setMaxHealth(int maxHealth) {
      this.maxHealth = maxHealth;
      this.npc.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)maxHealth);
   }
}
