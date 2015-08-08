package noppes.npcs.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import noppes.npcs.*;
import noppes.npcs.ai.*;
import noppes.npcs.ai.EntityAIMoveIndoors;
import noppes.npcs.ai.EntityAIPanic;
import noppes.npcs.ai.EntityAIWander;
import noppes.npcs.ai.EntityAIWatchClosest;
import noppes.npcs.ai.selector.NPCAttackSelector;
import noppes.npcs.ai.target.EntityAIClearTarget;
import noppes.npcs.ai.target.EntityAIClosestTarget;
import noppes.npcs.ai.target.EntityAIOwnerHurtByTarget;
import noppes.npcs.ai.target.EntityAIOwnerHurtTarget;
import noppes.npcs.constants.*;
import noppes.npcs.controllers.*;
import noppes.npcs.roles.*;
import noppes.npcs.scripted.ScriptEventAttack;
import noppes.npcs.scripted.ScriptEventDamaged;
import noppes.npcs.scripted.ScriptEventKilled;
import noppes.npcs.scripted.ScriptEventTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class EntityNPCInterface extends EntityCreature implements IEntityAdditionalSpawnData, ICommandSender, IRangedAttackMob, IBossDisplayData {

    public DataDisplay display;
    public DataStats stats;
    public DataAI ai;
    public DataAdvanced advanced;
    public DataInventory inventory;
    public DataScript script;
    public TransformData transform;
    public String linkedName = "";
    public long linkedLast = 0L;
    public LinkedNpcController.LinkedData linkedData;
    public float baseHeight = 1.8F;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    private boolean wasKilled = false;
    public RoleInterface roleInterface;
    public JobInterface jobInterface;
    public HashMap dialogs;
    public boolean hasDied = false;
    public long killedtime = 0L;
    public long totalTicksAlive = 0L;
    private int taskCount = 1;
    public int lastInteract = 0;
    public Faction faction;
    private EntityAIRangedAttack aiRange;
    private EntityAIBase aiResponse;
    private EntityAIBase aiLeap;
    private EntityAIBase aiSprint;
    private EntityAIBase aiAttackTarget;
    public List interactingEntities = new ArrayList();
    public ResourceLocation textureLocation = null;
    public ResourceLocation textureGlowLocation = null;
    public ResourceLocation textureCloakLocation = null;
    public EnumAnimation currentAnimation;
    public int npcVersion;
    public IChatMessages messages;
    public double field_20066_r;
    public double field_20065_s;
    public double field_20064_t;
    public double field_20063_u;
    public double field_20062_v;
    public double field_20061_w;


    public EntityNPCInterface(World world) {
        super(world);
        this.currentAnimation = EnumAnimation.NONE;
        this.npcVersion = VersionCompatibility.ModRev;
        this.dialogs = new HashMap();
        if (!CustomNpcs.DefaultInteractLine.isEmpty()) {
            this.advanced.interactLines.lines.put(Integer.valueOf(0), new Line(CustomNpcs.DefaultInteractLine));
        }

        super.experienceValue = 0;
        this.scaleX = this.scaleY = this.scaleZ = 0.9375F;
        this.faction = this.getFaction();
        this.setFaction(this.faction.id);
        this.setSize(1.0F, 1.0F);
        this.updateTasks();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.display = new DataDisplay(this);
        this.stats = new DataStats(this);
        this.ai = new DataAI(this);
        this.advanced = new DataAdvanced(this);
        this.inventory = new DataInventory(this);
        this.transform = new TransformData(this);
        this.script = new DataScript(this);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double) this.stats.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue((double) CustomNpcs.NpcNavRange);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double) this.getSpeed());
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double) this.stats.getAttackStrength());
    }

    protected void entityInit() {
        super.entityInit();
        super.dataWatcher.addObject(13, String.valueOf(""));
        super.dataWatcher.addObject(14, Integer.valueOf(0));
        super.dataWatcher.addObject(15, Integer.valueOf(0));
        super.dataWatcher.addObject(16, String.valueOf(""));
        super.dataWatcher.addObject(23, Byte.valueOf((byte) 0));
        super.dataWatcher.addObject(24, Integer.valueOf(0));
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public boolean getLeashed() {
        return false;
    }

    public boolean isEntityAlive() {
        return super.isEntityAlive() && !this.isKilled();
    }

    public void onUpdate() {
        super.onUpdate();
        if (super.ticksExisted % 10 == 0) {
            this.script.callScript(EnumScriptType.TICK, new Object[0]);
        }

    }

    public void setWorld(World world) {
        super.setWorld(world);
        this.script.setWorld(world);
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        if (this.stats.attackSpeed < 10) {
            par1Entity.hurtResistantTime = 0;
        }

        if (par1Entity instanceof EntityLivingBase) {
            ScriptEventAttack var4 = new ScriptEventAttack(f, (EntityLivingBase) par1Entity, false);
            if (this.script.callScript(EnumScriptType.ATTACK, new Object[]{"event", var4, "target", par1Entity})) {
                return false;
            }

            f = var4.getDamage();
        }

        boolean var41 = par1Entity.attackEntityFrom(new NpcDamageSource("mob", this), f);
        if (var41) {
            if (this.getOwner() instanceof EntityPlayer) {
                NPCEntityHelper.setRecentlyHit((EntityLivingBase) par1Entity);
            }

            if (this.stats.knockback > 0) {
                par1Entity.addVelocity((double) (-MathHelper.sin(super.rotationYaw * 3.1415927F / 180.0F) * (float) this.stats.knockback * 0.5F), 0.1D, (double) (MathHelper.cos(super.rotationYaw * 3.1415927F / 180.0F) * (float) this.stats.knockback * 0.5F));
                super.motionX *= 0.6D;
                super.motionZ *= 0.6D;
            }

            if (this.advanced.role == EnumRoleType.Companion) {
                ((RoleCompanion) this.roleInterface).attackedEntity(par1Entity);
            }
        }

        if (this.stats.potionType != EnumPotionType.None) {
            if (this.stats.potionType != EnumPotionType.Fire) {
                ((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(this.getPotionEffect(this.stats.potionType), this.stats.potionDuration * 20, this.stats.potionAmp));
            } else {
                par1Entity.setFire(this.stats.potionDuration);
            }
        }

        return var41;
    }

    public void onLivingUpdate() {
        if (!CustomNpcs.FreezeNPCs) {
            ++this.totalTicksAlive;
            this.updateArmSwingProgress();
            if (super.ticksExisted % 20 == 0) {
                this.faction = this.getFaction();
            }

            if (!super.worldObj.isRemote) {
                if (!this.isKilled() && super.ticksExisted % 20 == 0) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        if (this.stats.healthRegen > 0 && !this.isAttacking()) {
                            this.heal((float) this.stats.healthRegen);
                        }

                        if (this.stats.combatRegen > 0 && this.isAttacking()) {
                            this.heal((float) this.stats.combatRegen);
                        }
                    }

                    if (this.faction.getsAttacked && !this.isAttacking()) {
                        List f = super.worldObj.getEntitiesWithinAABB(EntityMob.class, super.boundingBox.expand(16.0D, 16.0D, 16.0D));
                        Iterator var2 = f.iterator();

                        while (var2.hasNext()) {
                            EntityMob mob = (EntityMob) var2.next();
                            if (mob.getAttackTarget() == null && this.canSee(mob)) {
                                if (mob instanceof EntityZombie && !mob.getEntityData().hasKey("AttackNpcs")) {
                                    mob.tasks.addTask(2, new EntityAIAttackOnCollide(mob, EntityLivingBase.class, 1.0D, false));
                                    mob.getEntityData().setBoolean("AttackNpcs", true);
                                }

                                mob.setAttackTarget(this);
                            }
                        }
                    }

                    if (this.linkedData != null && this.linkedData.time > this.linkedLast) {
                        LinkedNpcController.Instance.loadNpcData(this);
                    }
                }

                if (this.getHealth() <= 0.0F) {
                    this.clearActivePotions();
                    super.dataWatcher.updateObject(24, Integer.valueOf(1));
                }

                super.dataWatcher.updateObject(23, Byte.valueOf((byte) (this.getAttackTarget() != null ? 1 : 0)));
                super.dataWatcher.updateObject(15, Integer.valueOf(this.getNavigator().noPath() ? 0 : 1));
                this.onCollide();
            }

            if (this.wasKilled != this.isKilled() && this.wasKilled) {
                this.reset();
            }

            this.wasKilled = this.isKilled();
            if (super.worldObj.isDaytime() && !super.worldObj.isRemote && this.stats.burnInSun) {
                float f1 = this.getBrightness(1.0F);
                if (f1 > 0.5F && super.rand.nextFloat() * 30.0F < (f1 - 0.4F) * 2.0F && super.worldObj.canBlockSeeTheSky(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY), MathHelper.floor_double(super.posZ))) {
                    this.setFire(8);
                }
            }

            super.onLivingUpdate();
            if (super.worldObj.isRemote) {
                if (this.roleInterface != null) {
                    this.roleInterface.clientUpdate();
                }

                if (!this.display.cloakTexture.isEmpty()) {
                    this.cloakUpdate();
                }

                if (this.currentAnimation.ordinal() != super.dataWatcher.getWatchableObjectInt(14)) {
                    this.currentAnimation = EnumAnimation.values()[super.dataWatcher.getWatchableObjectInt(14)];
                    this.updateHitbox();
                }

                if (this.advanced.job == EnumJobType.Bard) {
                    ((JobBard) this.jobInterface).onLivingUpdate();
                }
            }

        }
    }

    public boolean interact(EntityPlayer player) {
        if (super.worldObj.isRemote) {

            if (player.inventory.getCurrentItem() != null) {
                Item item = player.inventory.getCurrentItem().getItem();
                if (item == CustomItems.cloner || item == CustomItems.wand || item == CustomItems.mount || item == CustomItems.scripter || item == CustomItems.moving)
                    return true;
            }

            if (this.isAttacking() || this.isKilled() || this.faction.isAggressiveToPlayer(player)) {
                return false;
            }

            if (this.script.isEnabled() && this.script.scripts.containsKey(EnumScriptType.INTERACT.ordinal()) && !((ScriptContainer)this.script.scripts.get(EnumScriptType.INTERACT.ordinal())).errored)
                return false;

            return true;
        } else {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if (currentItem != null) {
                Item dialog = currentItem.getItem();
                if (dialog == CustomItems.cloner || dialog == CustomItems.wand || dialog == CustomItems.mount || dialog == CustomItems.scripter) {
                    this.setAttackTarget((EntityLivingBase) null);
                    this.setRevengeTarget((EntityLivingBase) null);
                    return true;
                }

                if (dialog == CustomItems.moving) {
                    this.setAttackTarget((EntityLivingBase) null);
                    if (currentItem.stackTagCompound == null) {
                        currentItem.stackTagCompound = new NBTTagCompound();
                    }

                    currentItem.stackTagCompound.setInteger("NPCID", this.getEntityId());
                    player.addChatMessage(new ChatComponentTranslation("Registered " + this.getCommandSenderName() + " to your NPC Pather", new Object[0]));
                    return true;
                }
            }

            if (!this.script.callScript(EnumScriptType.INTERACT, new Object[]{"player", player}) && !this.isAttacking() && !this.isKilled() && !this.faction.isAggressiveToPlayer(player)) {
                this.addInteract(player);
                Dialog dialog1 = this.getDialog(player);
                PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
                QuestData data = playerdata.getQuestCompletion(player, this);
                if (data != null) {
                    Server.sendData((EntityPlayerMP) player, EnumPacketClient.QUEST_COMPLETION, new Object[]{data.quest.writeToNBT(new NBTTagCompound())});
                } else if (dialog1 != null) {
                    NoppesUtilServer.openDialog(player, this, dialog1);
                } else if (this.roleInterface != null) {
                    this.roleInterface.interact(player);
                } else {
                    this.say(player, this.advanced.getInteractLine());
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public void addInteract(EntityLivingBase entity) {
        if (this.ai.stopAndInteract && !this.isAttacking() && entity.isEntityAlive()) {
            if (super.ticksExisted - this.lastInteract < 180) {
                this.interactingEntities.clear();
            }

            this.getNavigator().clearPathEntity();
            this.lastInteract = super.ticksExisted;
            if (!this.interactingEntities.contains(entity)) {
                this.interactingEntities.add(entity);
            }

        }
    }

    public boolean isInteracting() {
        return super.ticksExisted - this.lastInteract < 40 ? true : this.ai.stopAndInteract && !this.interactingEntities.isEmpty() && super.ticksExisted - this.lastInteract < 180;
    }

    private Dialog getDialog(EntityPlayer player) {
        Iterator var2 = this.dialogs.values().iterator();

        while (var2.hasNext()) {
            DialogOption option = (DialogOption) var2.next();
            if (option != null && option.hasDialog()) {
                Dialog dialog = option.getDialog();
                if (dialog.availability.isAvailable(player)) {
                    return dialog;
                }
            }
        }

        return null;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (!super.worldObj.isRemote && !CustomNpcs.FreezeNPCs && !damagesource.damageType.equals("inWall")) {
            if (damagesource.damageType.equals("outOfWorld") && this.isKilled()) {
                this.reset();
            }

            i = this.stats.resistances.applyResistance(damagesource, i);
            if ((float) super.hurtResistantTime > (float) super.maxHurtResistantTime / 2.0F && i <= super.lastDamage) {
                return false;
            } else {
                Entity entity = damagesource.getEntity();
                EntityLivingBase attackingEntity = null;
                if (entity instanceof EntityLivingBase) {
                    attackingEntity = (EntityLivingBase) entity;
                }

                if (entity instanceof EntityArrow && ((EntityArrow) entity).shootingEntity instanceof EntityLivingBase) {
                    attackingEntity = (EntityLivingBase) ((EntityArrow) entity).shootingEntity;
                } else if (entity instanceof EntityThrowable) {
                    attackingEntity = ((EntityThrowable) entity).getThrower();
                }

                if (attackingEntity != null && attackingEntity == this.getOwner()) {
                    return false;
                } else {
                    if (attackingEntity instanceof EntityNPCInterface) {
                        EntityNPCInterface result = (EntityNPCInterface) attackingEntity;
                        if (result.faction.id == this.faction.id) {
                            return false;
                        }

                        if (result.getOwner() instanceof EntityPlayer) {
                            super.recentlyHit = 100;
                        }
                    } else if (attackingEntity instanceof EntityPlayer && this.faction.isFriendlyToPlayer((EntityPlayer) attackingEntity)) {
                        return false;
                    }

                    ScriptEventDamaged result1 = new ScriptEventDamaged(i, attackingEntity, damagesource);
                    if (!this.script.callScript(EnumScriptType.DAMAGED, new Object[]{"event", result1}) && !this.isKilled()) {
                        i = result1.getDamage();
                        if (this.isKilled()) {
                            return false;
                        } else if (attackingEntity == null) {
                            return super.attackEntityFrom(damagesource, i);
                        } else {
                            boolean inRange1;
                            try {
                                if (this.isAttacking()) {
                                    if (this.getAttackTarget() != null && attackingEntity != null && this.getDistanceSqToEntity(this.getAttackTarget()) > this.getDistanceSqToEntity(attackingEntity)) {
                                        this.setAttackTarget(attackingEntity);
                                    }

                                    inRange1 = super.attackEntityFrom(damagesource, i);
                                    return inRange1;
                                }

                                if (i > 0.0F) {
                                    List inRange = super.worldObj.getEntitiesWithinAABB(EntityNPCInterface.class, super.boundingBox.expand(32.0D, 16.0D, 32.0D));
                                    Iterator var7 = inRange.iterator();

                                    while (var7.hasNext()) {
                                        EntityNPCInterface npc = (EntityNPCInterface) var7.next();
                                        if (!npc.isKilled() && npc.advanced.defendFaction && npc.faction.id == this.faction.id && (npc.canSee(this) || npc.ai.directLOS || npc.canSee(attackingEntity))) {
                                            npc.onAttack(attackingEntity);
                                        }
                                    }

                                    this.setAttackTarget(attackingEntity);
                                }

                                inRange1 = super.attackEntityFrom(damagesource, i);
                            } finally {
                                if (result1.getClearTarget()) {
                                    this.setAttackTarget((EntityLivingBase) null);
                                    this.setRevengeTarget((EntityLivingBase) null);
                                }

                            }

                            return inRange1;
                        }
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    public void onAttack(EntityLivingBase entity) {
        if (entity != null && entity != this && !this.isAttacking() && this.ai.onAttack != 3 && entity != this.getOwner()) {
            super.setAttackTarget(entity);
        }
    }

    public void setAttackTarget(EntityLivingBase entity) {
        if ((!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.disableDamage) && (entity == null || entity != this.getOwner())) {
            if (this.getAttackTarget() != entity) {
                ScriptEventTarget line = new ScriptEventTarget(entity);
                if (this.script.callScript(EnumScriptType.TARGET, new Object[]{"event", line})) {
                    return;
                }

                if (line.getTarget() == null) {
                    entity = null;
                } else {
                    entity = line.getTarget().getMinecraftEntity();
                }
            }

            if (entity != null && entity != this && this.ai.onAttack != 3 && !this.isAttacking() && !this.isRemote()) {
                Line line1 = this.advanced.getAttackLine();
                if (line1 != null) {
                    this.saySurrounding(line1.formatTarget(entity));
                }
            }

            super.setAttackTarget(entity);
        }
    }

    public void attackEntityWithRangedAttack(EntityLivingBase entity, float f) {
        ItemStack proj = this.inventory.getProjectile();
        if (proj == null) {
            this.updateTasks();
        } else {
            ScriptEventAttack event = new ScriptEventAttack((float) this.stats.pDamage, entity, true);
            if (!this.script.callScript(EnumScriptType.ATTACK, new Object[]{"event", event, "target", entity})) {
                for (int i = 0; i < this.stats.shotCount; ++i) {
                    EntityProjectile projectile = this.shoot(entity, this.stats.accuracy, proj, f == 1.0F);
                    projectile.damage = event.getDamage();
                }

                this.playSound(this.stats.fireSound, 2.0F, 1.0F);
            }
        }
    }

    public EntityProjectile shoot(EntityLivingBase entity, int accuracy, ItemStack proj, boolean indirect) {
        return this.shoot(entity.posX, entity.boundingBox.minY + (double) (entity.height / 2.0F), entity.posZ, accuracy, proj, indirect);
    }

    public EntityProjectile shoot(double x, double y, double z, int accuracy, ItemStack proj, boolean indirect) {
        EntityProjectile projectile = new EntityProjectile(super.worldObj, this, proj.copy(), true);
        double varX = x - super.posX;
        double varY = y - (super.posY + (double) this.getEyeHeight());
        double varZ = z - super.posZ;
        float varF = projectile.hasGravity() ? MathHelper.sqrt_double(varX * varX + varZ * varZ) : 0.0F;
        float angle = projectile.getAngleForXYZ(varX, varY, varZ, (double) varF, indirect);
        float acc = 20.0F - (float) MathHelper.floor_float((float) accuracy / 5.0F);
        projectile.setThrowableHeading(varX, varY, varZ, angle, acc);
        super.worldObj.spawnEntityInWorld(projectile);
        return projectile;
    }

    private void clearTasks(EntityAITasks tasks) {
        Iterator iterator = tasks.taskEntries.iterator();
        ArrayList list = new ArrayList(tasks.taskEntries);
        Iterator var4 = list.iterator();

        while (var4.hasNext()) {
            EntityAITaskEntry entityaitaskentry = (EntityAITaskEntry) var4.next();
            super.tasks.removeTask(entityaitaskentry.action);
        }

        tasks.taskEntries = new ArrayList();
    }

    public void updateTasks() {
        if (super.worldObj != null && !super.worldObj.isRemote) {
            this.aiLeap = this.aiAttackTarget = this.aiResponse = this.aiSprint = this.aiRange = null;
            this.clearTasks(super.tasks);
            this.clearTasks(super.targetTasks);
            NPCAttackSelector attackEntitySelector = new NPCAttackSelector(this);
            super.targetTasks.addTask(0, new EntityAIClearTarget(this));
            super.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
            super.targetTasks.addTask(2, new EntityAIClosestTarget(this, EntityLivingBase.class, 4, this.ai.directLOS, false, attackEntitySelector));
            super.targetTasks.addTask(3, new EntityAIOwnerHurtByTarget(this));
            super.targetTasks.addTask(4, new EntityAIOwnerHurtTarget(this));
            super.tasks.addTask(0, new EntityAIWaterNav(this));
            this.taskCount = 1;
            this.doorInteractType();
            this.seekShelter();
            this.setResponse();
            this.setMoveType();
            this.addRegularEntries();
        }
    }

    private void removeTask(EntityAIBase task) {
        if (task != null) {
            super.tasks.removeTask(task);
        }

    }

    public void setResponse() {
        this.removeTask(this.aiLeap);
        this.removeTask(this.aiResponse);
        this.removeTask(this.aiSprint);
        this.removeTask(this.aiAttackTarget);
        this.removeTask(this.aiRange);
        this.aiLeap = this.aiAttackTarget = this.aiResponse = this.aiSprint = this.aiRange = null;
        if (this.ai.onAttack == 1) {
            super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIPanic(this, 1.2F));
        } else if (this.ai.onAttack == 2) {
            super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIAvoidTarget(this));
            this.setCanSprint();
        } else if (this.ai.onAttack == 0) {
            this.setCanLeap();
            this.setCanSprint();
            if (this.inventory.getProjectile() != null && this.ai.useRangeMelee != 2) {
                switch (this.ai.tacticalVariant) {
                    case Dodge:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIDodgeShoot(this));
                        break;
                    case Surround:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIOrbitTarget(this, 1.0D, (float) this.stats.rangedRange, false));
                        break;
                    case HitNRun:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIAvoidTarget(this));
                        break;
                    case Ambush:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIAmbushTarget(this, 1.2D, (double) this.ai.tacticalRadius, false));
                        break;
                    case Stalk:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIStalkTarget(this, (double) this.ai.tacticalRadius));
                }
            } else {
                switch (this.ai.tacticalVariant) {
                    case Dodge:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIZigZagTarget(this, 1.0D, (float) this.ai.tacticalRadius));
                        break;
                    case Surround:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIOrbitTarget(this, 1.0D, (float) this.ai.tacticalRadius, true));
                        break;
                    case HitNRun:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIAvoidTarget(this));
                        break;
                    case Ambush:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIAmbushTarget(this, 1.2D, (double) this.ai.tacticalRadius, false));
                        break;
                    case Stalk:
                        super.tasks.addTask(this.taskCount++, this.aiResponse = new EntityAIStalkTarget(this, (double) this.ai.tacticalRadius));
                }
            }

            super.tasks.addTask(this.taskCount, this.aiAttackTarget = new EntityAIAttackTarget(this));
            ((EntityAIAttackTarget) this.aiAttackTarget).navOverride(this.ai.tacticalVariant == EnumNavType.None);
            if (this.inventory.getProjectile() != null) {
                super.tasks.addTask(this.taskCount++, this.aiRange = new EntityAIRangedAttack(this));
                this.aiRange.navOverride(this.ai.tacticalVariant == EnumNavType.None);
            }
        } else if (this.ai.onAttack == 3) {
            ;
        }

    }

    public void setMoveType() {
        if (this.ai.movingType == EnumMovingType.Wandering) {
            super.tasks.addTask(this.taskCount++, new EntityAIWander(this));
        }

        if (this.ai.movingType == EnumMovingType.MovingPath) {
            super.tasks.addTask(this.taskCount++, new EntityAIMovingPath(this));
        }

    }

    public void doorInteractType() {
        EntityAIBase aiDoor = null;
        if (this.ai.doorInteract == 1) {
            super.tasks.addTask(this.taskCount++, aiDoor = new EntityAIOpenDoor(this, true));
        } else if (this.ai.doorInteract == 0) {
            super.tasks.addTask(this.taskCount++, aiDoor = new EntityAIBustDoor(this));
        }

        this.getNavigator().setBreakDoors(aiDoor != null);
    }

    public void seekShelter() {
        if (this.ai.findShelter == 0) {
            super.tasks.addTask(this.taskCount++, new EntityAIMoveIndoors(this));
        } else if (this.ai.findShelter == 1) {
            super.tasks.addTask(this.taskCount++, new EntityAIRestrictSun(this));
            super.tasks.addTask(this.taskCount++, new EntityAIFindShade(this));
        }

    }

    public void setCanLeap() {
        if (this.ai.canLeap) {
            super.tasks.addTask(this.taskCount++, this.aiLeap = new EntityAILeapAtTarget(this, 0.4F));
        }

    }

    public void setCanSprint() {
        if (this.ai.canSprint) {
            super.tasks.addTask(this.taskCount++, this.aiSprint = new EntityAISprintToTarget(this));
        }

    }

    public void addRegularEntries() {
        super.tasks.addTask(this.taskCount++, new EntityAIReturn(this));
        super.tasks.addTask(this.taskCount++, new EntityAIFollow(this));
        if (this.ai.standingType != EnumStandingType.NoRotation && this.ai.standingType != EnumStandingType.HeadRotation) {
            super.tasks.addTask(this.taskCount++, new EntityAIWatchClosest(this, EntityLivingBase.class, 5.0F));
        }

        super.tasks.addTask(this.taskCount++, new EntityAILook(this));
        super.tasks.addTask(this.taskCount++, new EntityAIWorldLines(this));
        super.tasks.addTask(this.taskCount++, new EntityAIJob(this));
        super.tasks.addTask(this.taskCount++, new EntityAIRole(this));
        super.tasks.addTask(this.taskCount++, new EntityAIAnimation(this));
        if (this.transform.isValid()) {
            super.tasks.addTask(this.taskCount++, new EntityAITransform(this));
        }

    }

    public float getSpeed() {
        return (float) this.ai.getWalkingSpeed() / 20.0F;
    }

    public float getBlockPathWeight(int par1, int par2, int par3) {
        float weight = super.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
        Block block = super.worldObj.getBlock(par1, par2, par3);
        if (block.isOpaqueCube()) {
            weight += 10.0F;
        }

        return weight;
    }

    private int getPotionEffect(EnumPotionType p) {
        switch (p) {
            case Poison:
                return Potion.poison.id;
            case Hunger:
                return Potion.hunger.id;
            case Weakness:
                return Potion.weakness.id;
            case Slowness:
                return Potion.moveSlowdown.id;
            case Nausea:
                return Potion.confusion.id;
            case Blindness:
                return Potion.blindness.id;
            case Wither:
                return Potion.wither.id;
            default:
                return 0;
        }
    }

    protected int decreaseAirSupply(int par1) {
        return !this.stats.canDrown ? par1 : super.decreaseAirSupply(par1);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return this.stats.creatureType;
    }

    protected String getLivingSound() {
        return !this.isEntityAlive() ? null : (this.getAttackTarget() != null ? (this.advanced.angrySound.isEmpty() ? null : this.advanced.angrySound) : (this.advanced.idleSound.isEmpty() ? null : this.advanced.idleSound));
    }

    public int getTalkInterval() {
        return 160;
    }

    protected String getHurtSound() {
        return this.advanced.hurtSound.isEmpty() ? null : this.advanced.hurtSound;
    }

    protected String getDeathSound() {
        return this.advanced.deathSound.isEmpty() ? null : this.advanced.deathSound;
    }

    protected float getSoundPitch() {
        return this.advanced.disablePitch ? 1.0F : super.getSoundPitch();
    }

    protected void playStepSound(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        if (!this.advanced.stepSound.equals("")) {
            this.playSound(this.advanced.stepSound, 0.15F, 1.0F);
        } else {
            super.playStepSound(p_145780_1_, p_145780_2_, p_145780_3_, p_145780_4_);
        }

    }

    public void saySurrounding(Line line) {
        if (line != null) {
            List inRange = super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.boundingBox.expand(20.0D, 20.0D, 20.0D));
            Iterator var3 = inRange.iterator();

            while (var3.hasNext()) {
                EntityPlayer player = (EntityPlayer) var3.next();
                this.say(player, line);
            }

        }
    }

    public void say(EntityPlayer player, Line line) {
        if (line != null && this.canSee(player) && line.text != null) {
            if (!line.sound.isEmpty()) {
                Server.sendData((EntityPlayerMP) player, EnumPacketClient.PLAY_SOUND, new Object[]{line.sound, Float.valueOf((float) super.posX), Float.valueOf((float) super.posY), Float.valueOf((float) super.posZ)});
            }

            Server.sendData((EntityPlayerMP) player, EnumPacketClient.CHATBUBBLE, new Object[]{Integer.valueOf(this.getEntityId()), line.text, Boolean.valueOf(!line.hideText)});
        }
    }

    public boolean getAlwaysRenderNameTagForRender() {
        return true;
    }

    public void addVelocity(double d, double d1, double d2) {
        if (this.isWalking() && !this.isKilled()) {
            super.addVelocity(d, d1, d2);
        }

    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.npcVersion = compound.getInteger("ModRev");
        VersionCompatibility.CheckNpcCompatibility(this, compound);
        this.display.readToNBT(compound);
        this.stats.readToNBT(compound);
        this.ai.readToNBT(compound);
        this.script.readFromNBT(compound);
        this.advanced.readToNBT(compound);
        if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
            this.roleInterface.readFromNBT(compound);
        }

        if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
            this.jobInterface.readFromNBT(compound);
        }

        this.inventory.readEntityFromNBT(compound);
        this.transform.readToNBT(compound);
        this.killedtime = compound.getLong("KilledTime");
        this.totalTicksAlive = compound.getLong("TotalTicksAlive");
        this.linkedName = compound.getString("LinkedNpcName");
        if (!this.isRemote()) {
            LinkedNpcController.Instance.loadNpcData(this);
        }

        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue((double) CustomNpcs.NpcNavRange);
        this.updateTasks();
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        this.display.writeToNBT(compound);
        this.stats.writeToNBT(compound);
        this.ai.writeToNBT(compound);
        this.script.writeToNBT(compound);
        this.advanced.writeToNBT(compound);
        if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
            this.roleInterface.writeToNBT(compound);
        }

        if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
            this.jobInterface.writeToNBT(compound);
        }

        this.inventory.writeEntityToNBT(compound);
        this.transform.writeToNBT(compound);
        compound.setLong("KilledTime", this.killedtime);
        compound.setLong("TotalTicksAlive", this.totalTicksAlive);
        compound.setInteger("ModRev", this.npcVersion);
        compound.setString("LinkedNpcName", this.linkedName);
    }

    public void updateHitbox() {
        if (this.currentAnimation != EnumAnimation.LYING && this.currentAnimation != EnumAnimation.CRAWLING) {
            if (this.isRiding()) {
                super.width = 0.6F;
                super.height = this.baseHeight * 0.77F;
            } else {
                super.width = 0.6F;
                super.height = this.baseHeight;
            }
        } else {
            super.width = 0.8F;
            super.height = 0.4F;
        }

        super.width = super.width / 5.0F * (float) this.display.modelSize;
        super.height = super.height / 5.0F * (float) this.display.modelSize;
        this.setPosition(super.posX, super.posY, super.posZ);
    }

    public void dropPlayerItemWithRandomChoice(ItemStack itemstack, boolean flag) {
        if (itemstack != null) {
            EntityItem entityitem = new EntityItem(super.worldObj, super.posX, super.posY - 0.30000001192092896D + (double) this.getEyeHeight(), super.posZ, itemstack);
            entityitem.delayBeforeCanPickup = 40;
            float f1;
            float f3;
            if (flag) {
                f1 = super.rand.nextFloat() * 0.5F;
                f3 = super.rand.nextFloat() * 3.141593F * 2.0F;
                entityitem.motionX = (double) (-MathHelper.sin(f3) * f1);
                entityitem.motionZ = (double) (MathHelper.cos(f3) * f1);
                entityitem.motionY = 0.20000000298023224D;
            } else {
                f1 = 0.3F;
                entityitem.motionX = (double) (-MathHelper.sin(super.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.141593F) * f1);
                entityitem.motionZ = (double) (MathHelper.cos(super.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.141593F) * f1);
                entityitem.motionY = (double) (-MathHelper.sin(super.rotationPitch / 180.0F * 3.141593F) * f1 + 0.1F);
                f1 = 0.02F;
                f3 = super.rand.nextFloat() * 3.141593F * 2.0F;
                f1 *= super.rand.nextFloat();
                entityitem.motionX += Math.cos((double) f3) * (double) f1;
                entityitem.motionY += (double) ((super.rand.nextFloat() - super.rand.nextFloat()) * 0.1F);
                entityitem.motionZ += Math.sin((double) f3) * (double) f1;
            }

            super.worldObj.spawnEntityInWorld(entityitem);
        }
    }

    public void onDeathUpdate() {
        if (this.stats.spawnCycle == 3) {
            super.onDeathUpdate();
        } else {
            ++super.deathTime;
            if (!super.worldObj.isRemote) {
                if (!this.hasDied) {
                    this.setDead();
                }

                if (this.killedtime < System.currentTimeMillis() && (this.stats.spawnCycle == 0 || super.worldObj.isDaytime() && this.stats.spawnCycle == 1 || !super.worldObj.isDaytime() && this.stats.spawnCycle == 2)) {
                    this.reset();
                }

            }
        }
    }

    public void reset() {
        this.hasDied = false;
        super.isDead = false;
        this.setSprinting(false);
        this.setHealth(this.getMaxHealth());
        super.dataWatcher.updateObject(24, Integer.valueOf(0));
        super.dataWatcher.updateObject(14, Integer.valueOf(0));
        super.dataWatcher.updateObject(15, Integer.valueOf(0));
        this.setAttackTarget((EntityLivingBase) null);
        this.setRevengeTarget((EntityLivingBase) null);
        super.deathTime = 0;
        if (this.ai.returnToStart && !this.hasOwner()) {
            this.setLocationAndAngles((double) this.getStartXPos(), this.getStartYPos(), (double) this.getStartZPos(), super.rotationYaw, super.rotationPitch);
        }

        this.killedtime = 0L;
        this.extinguish();
        this.clearActivePotions();
        this.moveEntityWithHeading(0.0F, 0.0F);
        super.distanceWalkedModified = 0.0F;
        this.getNavigator().clearPathEntity();
        this.currentAnimation = EnumAnimation.NONE;
        this.updateHitbox();
        this.updateTasks();
        this.ai.movingPos = 0;
        if (this.getOwner() != null) {
            this.getOwner().setLastAttacker((Entity) null);
        }

        if (this.jobInterface != null) {
            this.jobInterface.reset();
        }

        this.script.callScript(EnumScriptType.INIT, new Object[0]);
    }

    public void onCollide() {
        if (this.isEntityAlive() && super.ticksExisted % 4 == 0) {
            AxisAlignedBB axisalignedbb = null;
            if (super.ridingEntity != null && super.ridingEntity.isEntityAlive()) {
                axisalignedbb = super.boundingBox.union(super.ridingEntity.boundingBox).expand(1.0D, 0.0D, 1.0D);
            } else {
                axisalignedbb = super.boundingBox.expand(1.0D, 0.5D, 1.0D);
            }

            List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
            if (list != null) {
                for (int i = 0; i < list.size(); ++i) {
                    Entity entity = (Entity) list.get(i);
                    if (entity.isEntityAlive()) {
                        this.script.callScript(EnumScriptType.COLLIDE, new Object[]{"entity", entity});
                    }
                }

            }
        }
    }

    public void setInPortal() {
    }

    public void cloakUpdate() {
        this.field_20066_r = this.field_20063_u;
        this.field_20065_s = this.field_20062_v;
        this.field_20064_t = this.field_20061_w;
        double d = super.posX - this.field_20063_u;
        double d1 = super.posY - this.field_20062_v;
        double d2 = super.posZ - this.field_20061_w;
        double d3 = 10.0D;
        if (d > d3) {
            this.field_20066_r = this.field_20063_u = super.posX;
        }

        if (d2 > d3) {
            this.field_20064_t = this.field_20061_w = super.posZ;
        }

        if (d1 > d3) {
            this.field_20065_s = this.field_20062_v = super.posY;
        }

        if (d < -d3) {
            this.field_20066_r = this.field_20063_u = super.posX;
        }

        if (d2 < -d3) {
            this.field_20064_t = this.field_20061_w = super.posZ;
        }

        if (d1 < -d3) {
            this.field_20065_s = this.field_20062_v = super.posY;
        }

        this.field_20063_u += d * 0.25D;
        this.field_20061_w += d2 * 0.25D;
        this.field_20062_v += d1 * 0.25D;
    }

    protected boolean canDespawn() {
        return this.stats.canDespawn;
    }

    public ItemStack getHeldItem() {
        return this.isAttacking() ? this.inventory.getWeapon() : (this.advanced.role == EnumRoleType.Companion ? ((RoleCompanion) this.roleInterface).getHeldItem() : (this.jobInterface != null && this.jobInterface.overrideMainHand ? this.jobInterface.mainhand : this.inventory.getWeapon()));
    }

    public ItemStack getEquipmentInSlot(int slot) {
        return slot == 0 ? (ItemStack) this.inventory.weapons.get(Integer.valueOf(0)) : this.inventory.armorItemInSlot(4 - slot);
    }

    public ItemStack func_130225_q(int slot) {
        return this.inventory.armorItemInSlot(3 - slot);
    }

    public void setCurrentItemOrArmor(int slot, ItemStack item) {
        if (slot == 0) {
            this.inventory.setWeapon(item);
        } else {
            this.inventory.armor.put(Integer.valueOf(4 - slot), item);
        }

    }

    public ItemStack[] getInventory() {
        ItemStack[] inv = new ItemStack[5];

        for (int i = 0; i < 5; ++i) {
            inv[i] = this.getEquipmentInSlot(i);
        }

        return inv;
    }

    public ItemStack getOffHand() {
        return this.isAttacking() ? this.inventory.getOffHand() : (this.jobInterface != null && this.jobInterface.overrideOffHand ? this.jobInterface.offhand : this.inventory.getOffHand());
    }

    public void onDeath(DamageSource damagesource) {
        this.setSprinting(false);
        this.getNavigator().clearPathEntity();
        this.extinguish();
        this.clearActivePotions();
        Entity entity = damagesource.getEntity();
        EntityLivingBase attackingEntity = null;
        if (entity instanceof EntityLivingBase) {
            attackingEntity = (EntityLivingBase) entity;
        }

        if (entity instanceof EntityArrow && ((EntityArrow) entity).shootingEntity instanceof EntityLivingBase) {
            attackingEntity = (EntityLivingBase) ((EntityArrow) entity).shootingEntity;
        } else if (entity instanceof EntityThrowable) {
            attackingEntity = ((EntityThrowable) entity).getThrower();
        }

        ScriptEventKilled result = new ScriptEventKilled(attackingEntity, damagesource);
        if (!this.script.callScript(EnumScriptType.KILLED, new Object[]{"event", result})) {
            if (!this.isRemote()) {
                if (super.recentlyHit > 0) {
                    this.inventory.dropStuff();
                }

                Line line = this.advanced.getKilledLine();
                if (line != null) {
                    this.saySurrounding(line.formatTarget(attackingEntity));
                }
            }

            super.onDeath(damagesource);
        }
    }

    public void setDead() {
        this.hasDied = true;
        if (!super.worldObj.isRemote && this.stats.spawnCycle != 3) {
            if (super.riddenByEntity != null) {
                super.riddenByEntity.mountEntity((Entity) null);
            }

            if (super.ridingEntity != null) {
                this.mountEntity((Entity) null);
            }

            this.setHealth(-1.0F);
            this.setSprinting(false);
            this.getNavigator().clearPathEntity();
            this.killedtime = (long) (this.stats.respawnTime * 1000) + System.currentTimeMillis();
            if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
                this.roleInterface.killed();
            }

            if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
                this.jobInterface.killed();
            }
        } else {
            this.spawnExplosionParticle();
            this.delete();
        }

    }

    public void delete() {
        if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
            this.roleInterface.delete();
        }

        if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
            this.jobInterface.delete();
        }

        super.setDead();
    }

    public float getStartXPos() {
        return (float) this.getStartPos()[0] + this.ai.bodyOffsetX / 10.0F;
    }

    public float getStartZPos() {
        return (float) this.getStartPos()[2] + this.ai.bodyOffsetZ / 10.0F;
    }

    public int[] getStartPos() {
        if (this.ai.startPos == null || this.ai.startPos.length != 3) {
            this.ai.startPos = new int[]{MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY), MathHelper.floor_double(super.posZ)};
        }

        return this.ai.startPos;
    }

    public boolean isVeryNearAssignedPlace() {
        double xx = super.posX - (double) this.getStartXPos();
        double zz = super.posZ - (double) this.getStartZPos();
        return xx >= -0.2D && xx <= 0.2D ? zz >= -0.2D && zz <= 0.2D : false;
    }

    public IIcon getItemIcon(ItemStack par1ItemStack, int par2) {
        if (par1ItemStack.getItem() == Items.bow) {
            return Items.bow.getIcon(par1ItemStack, par2);
        } else {
            EntityPlayer player = CustomNpcs.proxy.getPlayer();
            return player == null ? super.getItemIcon(par1ItemStack, par2) : player.getItemIcon(par1ItemStack, par2);
        }
    }

    public double getStartYPos() {
        int i = this.getStartPos()[0];
        int j = this.getStartPos()[1];
        int k = this.getStartPos()[2];
        double yy = 0.0D;

        for (int ii = j; ii >= 0; --ii) {
            Block block = super.worldObj.getBlock(i, ii, k);
            if (block != null) {
                AxisAlignedBB bb = block.getCollisionBoundingBoxFromPool(super.worldObj, i, ii, k);
                if (bb != null) {
                    yy = bb.maxY;
                    break;
                }
            }
        }

        if (yy == 0.0D) {
            this.setDead();
        }

        yy += 0.5D;
        return yy;
    }

    public void givePlayerItem(EntityPlayer player, ItemStack item) {
        if (!super.worldObj.isRemote) {
            item = item.copy();
            float f = 0.7F;
            double d = (double) (super.worldObj.rand.nextFloat() * f) + (double) (1.0F - f);
            double d1 = (double) (super.worldObj.rand.nextFloat() * f) + (double) (1.0F - f);
            double d2 = (double) (super.worldObj.rand.nextFloat() * f) + (double) (1.0F - f);
            EntityItem entityitem = new EntityItem(super.worldObj, super.posX + d, super.posY + d1, super.posZ + d2, item);
            entityitem.delayBeforeCanPickup = 2;
            super.worldObj.spawnEntityInWorld(entityitem);
            int i = item.stackSize;
            if (player.inventory.addItemStackToInventory(item)) {
                super.worldObj.playSoundAtEntity(entityitem, "random.pop", 0.2F, ((super.rand.nextFloat() - super.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(entityitem, i);
                if (item.stackSize <= 0) {
                    entityitem.setDead();
                }
            }

        }
    }

    public boolean isPlayerSleeping() {
        return this.currentAnimation == EnumAnimation.LYING && !this.isAttacking();
    }

    public boolean isRiding() {
        return this.currentAnimation == EnumAnimation.SITTING && !this.isAttacking() || super.ridingEntity != null;
    }

    public boolean isWalking() {
        return this.ai.movingType != EnumMovingType.Standing || this.isAttacking() || this.isFollower() || super.dataWatcher.getWatchableObjectInt(15) == 1;
    }

    public boolean isSneaking() {
        return this.currentAnimation == EnumAnimation.SNEAKING;
    }

    public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
        if (this.stats.resistances.knockback > 0.0F) {
            super.isAirBorne = true;
            float f1 = MathHelper.sqrt_double(par3 * par3 + par5 * par5);
            float f2 = 0.5F * (2.0F - this.stats.resistances.knockback);
            super.motionX /= 2.0D;
            super.motionY /= 2.0D;
            super.motionZ /= 2.0D;
            super.motionX -= par3 / (double) f1 * (double) f2;
            super.motionY += 0.2D + (double) (f2 / 2.0F);
            super.motionZ -= par5 / (double) f1 * (double) f2;
            if (super.motionY > 0.4000000059604645D) {
                super.motionY = 0.4000000059604645D;
            }

        }
    }

    public Faction getFaction() {
        String[] split = super.dataWatcher.getWatchableObjectString(13).split(":");
        int faction = 0;
        if (super.worldObj != null && (split.length > 1 || !super.worldObj.isRemote)) {
            if (split.length > 1) {
                faction = Integer.parseInt(split[0]);
            }

            Faction fac;
            if (super.worldObj.isRemote) {
                fac = new Faction();
                fac.id = faction;
                fac.color = Integer.parseInt(split[1]);
                fac.name = split[2];
                return fac;
            } else {
                fac = FactionController.getInstance().getFaction(faction);
                if (fac == null) {
                    faction = FactionController.getInstance().getFirstFactionId();
                    fac = FactionController.getInstance().getFaction(faction);
                }

                return fac;
            }
        } else {
            return new Faction();
        }
    }

    public boolean isRemote() {
        return super.worldObj == null || super.worldObj.isRemote;
    }

    public void setFaction(int integer) {
        if (integer >= 0 && !this.isRemote()) {
            Faction faction = FactionController.getInstance().getFaction(integer);
            if (faction != null) {
                String str = faction.id + ":" + faction.color + ":" + faction.name;
                if (str.length() > 64) {
                    str = str.substring(0, 64);
                }

                super.dataWatcher.updateObject(13, str);
            }
        }
    }

    public boolean isPotionApplicable(PotionEffect effect) {
        return this.stats.potionImmune ? false : (this.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD && effect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(effect));
    }

    public boolean isAttacking() {
        return super.dataWatcher.getWatchableObjectByte(23) == 1;
    }

    public boolean isKilled() {
        return super.dataWatcher.getWatchableObjectInt(24) == 1 || super.isDead;
    }

    public void writeSpawnData(ByteBuf buffer) {
        try {
            Server.writeNBT(buffer, this.writeSpawnData());
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public NBTTagCompound writeSpawnData() {
        NBTTagCompound compound = new NBTTagCompound();
        this.display.writeToNBT(compound);
        compound.setInteger("MaxHealth", this.stats.maxHealth);
        compound.setTag("Armor", NBTTags.nbtItemStackList(this.inventory.getArmor()));
        compound.setTag("Weapons", NBTTags.nbtItemStackList(this.inventory.getWeapons()));
        compound.setInteger("Speed", this.ai.getWalkingSpeed());
        compound.setBoolean("DeadBody", this.stats.hideKilledBody);
        compound.setInteger("StandingState", this.ai.standingType.ordinal());
        compound.setInteger("MovingState", this.ai.movingType.ordinal());
        compound.setInteger("Orientation", this.ai.orientation);
        compound.setInteger("Role", this.advanced.role.ordinal());
        compound.setInteger("Job", this.advanced.job.ordinal());
        NBTTagCompound bard;
        if (this.advanced.job == EnumJobType.Bard) {
            bard = new NBTTagCompound();
            this.jobInterface.writeToNBT(bard);
            compound.setTag("Bard", bard);
        }

        if (this.advanced.job == EnumJobType.Puppet) {
            bard = new NBTTagCompound();
            this.jobInterface.writeToNBT(bard);
            compound.setTag("Puppet", bard);
        }

        if (this.advanced.role == EnumRoleType.Companion) {
            bard = new NBTTagCompound();
            this.roleInterface.writeToNBT(bard);
            compound.setTag("Companion", bard);
        }

        if (this instanceof EntityCustomNpc) {
            compound.setTag("ModelData", ((EntityCustomNpc) this).modelData.writeToNBT());
        }

        return compound;
    }

    public void readSpawnData(ByteBuf buf) {
        try {
            this.readSpawnData(Server.readNBT(buf));
        } catch (IOException var3) {
            ;
        }

    }

    public void readSpawnData(NBTTagCompound compound) {
        this.stats.maxHealth = compound.getInteger("MaxHealth");
        this.ai.setWalkingSpeed(compound.getInteger("Speed"));
        this.stats.hideKilledBody = compound.getBoolean("DeadBody");
        this.ai.standingType = EnumStandingType.values()[compound.getInteger("StandingState") % EnumStandingType.values().length];
        this.ai.movingType = EnumMovingType.values()[compound.getInteger("MovingState") % EnumMovingType.values().length];
        this.ai.orientation = compound.getInteger("Orientation");
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double) this.stats.maxHealth);
        this.inventory.setArmor(NBTTags.getItemStackList(compound.getTagList("Armor", 10)));
        this.inventory.setWeapons(NBTTags.getItemStackList(compound.getTagList("Weapons", 10)));
        this.advanced.setRole(compound.getInteger("Role"));
        this.advanced.setJob(compound.getInteger("Job"));
        NBTTagCompound puppet;
        if (this.advanced.job == EnumJobType.Bard) {
            puppet = compound.getCompoundTag("Bard");
            this.jobInterface.readFromNBT(puppet);
        }

        if (this.advanced.job == EnumJobType.Puppet) {
            puppet = compound.getCompoundTag("Puppet");
            this.jobInterface.readFromNBT(puppet);
        }

        if (this.advanced.role == EnumRoleType.Companion) {
            puppet = compound.getCompoundTag("Companion");
            this.roleInterface.readFromNBT(puppet);
        }

        if (this instanceof EntityCustomNpc) {
            ((EntityCustomNpc) this).modelData.readFromNBT(compound.getCompoundTag("ModelData"));
        }

        this.display.readToNBT(compound);
    }

    public String getCommandSenderName() {
        return this.display.name;
    }

    public boolean canCommandSenderUseCommand(int var1, String var2) {
        return CustomNpcs.NpcUseOpCommands ? true : var1 <= 2;
    }

    public ChunkCoordinates getCommandSenderPosition() {
        return new ChunkCoordinates(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY), MathHelper.floor_double(super.posZ));
    }

    public boolean canAttackClass(Class par1Class) {
        return EntityBat.class != par1Class;
    }

    public void setImmuneToFire(boolean immuneToFire) {
        super.isImmuneToFire = immuneToFire;
        this.stats.immuneToFire = immuneToFire;
    }

    public void setAvoidWater(boolean avoidWater) {
        this.getNavigator().setAvoidsWater(avoidWater);
        this.ai.avoidsWater = avoidWater;
    }

    protected void fall(float par1) {
        if (!this.stats.noFallDamage) {
            super.fall(par1);
        }

    }

    public void setInWeb() {
        if (!this.ai.ignoreCobweb) {
            super.setInWeb();
        }

    }

    public boolean canBeCollidedWith() {
        return !this.isKilled();
    }

    public EntityAIRangedAttack getRangedTask() {
        return this.aiRange;
    }

    public String getRoleDataWatcher() {
        return super.dataWatcher.getWatchableObjectString(16);
    }

    public void setRoleDataWatcher(String s) {
        super.dataWatcher.updateObject(16, s);
    }

    public World getEntityWorld() {
        return super.worldObj;
    }

    public boolean isInvisibleToPlayer(EntityPlayer player) {
        return this.display.visible == 1 && (player.getHeldItem() == null || player.getHeldItem().getItem() != CustomItems.wand);
    }

    public boolean isInvisible() {
        return this.display.visible != 0;
    }

    public void addChatMessage(IChatComponent var1) {
    }

    public void setCurrentAnimation(EnumAnimation animation) {
        this.currentAnimation = animation;
        super.dataWatcher.updateObject(14, Integer.valueOf(animation.ordinal()));
    }

    public boolean canSee(Entity entity) {
        return this.getEntitySenses().canSee(entity);
    }

    public boolean isFollower() {
        return this.advanced.role == EnumRoleType.Follower && ((RoleFollower) this.roleInterface).isFollowing() || this.advanced.role == EnumRoleType.Companion && ((RoleCompanion) this.roleInterface).isFollowing() || this.advanced.job == EnumJobType.Follower && ((JobFollower) this.jobInterface).isFollowing();
    }

    public EntityLivingBase getOwner() {
        return (EntityLivingBase) (this.roleInterface instanceof RoleFollower ? ((RoleFollower) this.roleInterface).owner : (this.roleInterface instanceof RoleCompanion ? ((RoleCompanion) this.roleInterface).owner : (this.jobInterface instanceof JobFollower ? ((JobFollower) this.jobInterface).following : null)));
    }

    public boolean hasOwner() {
        return this.advanced.role == EnumRoleType.Follower && ((RoleFollower) this.roleInterface).hasOwner() || this.advanced.role == EnumRoleType.Companion && ((RoleCompanion) this.roleInterface).hasOwner() || this.advanced.job == EnumJobType.Follower && ((JobFollower) this.jobInterface).hasOwner();
    }

    public int followRange() {
        return this.advanced.role == EnumRoleType.Follower && ((RoleFollower) this.roleInterface).isFollowing() ? 36 : (this.advanced.role == EnumRoleType.Companion && ((RoleCompanion) this.roleInterface).isFollowing() ? ((RoleCompanion) this.roleInterface).followRange() : (this.advanced.job == EnumJobType.Follower && ((JobFollower) this.jobInterface).isFollowing() ? 16 : 225));
    }

    public void setHomeArea(int x, int y, int z, int range) {
        super.setHomeArea(x, y, z, range);
        this.ai.startPos = new int[]{x, y, z};
    }

    protected float applyArmorCalculations(DamageSource source, float damage) {
        if (this.advanced.role == EnumRoleType.Companion) {
            damage = ((RoleCompanion) this.roleInterface).applyArmorCalculations(source, damage);
        }

        return super.applyArmorCalculations(source, damage);
    }

    public boolean isOnSameTeam(EntityLivingBase entity) {
        return entity instanceof EntityPlayer && this.getFaction().isFriendlyToPlayer((EntityPlayer) entity) ? true : this.isOnSameTeam(entity);
    }

    public void setDataWatcher(DataWatcher dataWatcher) {
        super.dataWatcher = dataWatcher;
    }

    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
        double d0 = super.posX;
        double d1 = super.posY;
        double d2 = super.posZ;
        super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
        if (this.advanced.role == EnumRoleType.Companion && !this.isRemote()) {
            ((RoleCompanion) this.roleInterface).addMovementStat(super.posX - d0, super.posY - d1, super.posZ - d2);
        }

    }
}
