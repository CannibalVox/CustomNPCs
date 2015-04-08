package noppes.npcs.scripted;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.Line;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.scripted.ScriptFaction;
import noppes.npcs.scripted.ScriptItemStack;
import noppes.npcs.scripted.ScriptLiving;
import noppes.npcs.scripted.ScriptLivingBase;
import noppes.npcs.scripted.ScriptPlayer;
import noppes.npcs.scripted.roles.ScriptJobBard;
import noppes.npcs.scripted.roles.ScriptJobConversation;
import noppes.npcs.scripted.roles.ScriptJobFollower;
import noppes.npcs.scripted.roles.ScriptJobGuard;
import noppes.npcs.scripted.roles.ScriptJobHealer;
import noppes.npcs.scripted.roles.ScriptJobInterface;
import noppes.npcs.scripted.roles.ScriptJobItemGiver;
import noppes.npcs.scripted.roles.ScriptJobPuppet;
import noppes.npcs.scripted.roles.ScriptJobSpawner;
import noppes.npcs.scripted.roles.ScriptRoleBank;
import noppes.npcs.scripted.roles.ScriptRoleFollower;
import noppes.npcs.scripted.roles.ScriptRoleInterface;
import noppes.npcs.scripted.roles.ScriptRoleMailman;
import noppes.npcs.scripted.roles.ScriptRoleTrader;
import noppes.npcs.scripted.roles.ScriptRoleTransporter;
import noppes.npcs.util.ValueUtil;

public class ScriptNpc extends ScriptLiving {

   protected EntityCustomNpc npc;


   public ScriptNpc(EntityCustomNpc npc) {
      super(npc);
      this.npc = npc;
   }

   public int getSize() {
      return this.npc.display.modelSize;
   }

   public void setSize(int size) {
      if(size > 30) {
         size = 30;
      } else if(size < 1) {
         size = 1;
      }

      this.npc.display.modelSize = size;
      this.npc.script.clientNeedsUpdate = true;
   }

   public String getName() {
      return this.npc.display.name;
   }

   public void setRotation(float rotation) {
      this.npc.ai.orientation = (int)rotation;
      super.setRotation(rotation);
   }

   public void setName(String name) {
      this.npc.display.name = name;
      this.npc.script.clientNeedsUpdate = true;
   }

   public String getTitle() {
      return this.npc.display.title;
   }

   public void setTitle(String title) {
      this.npc.display.title = title;
      this.npc.script.clientNeedsUpdate = true;
   }

   public String getTexture() {
      return this.npc.display.texture;
   }

   public void setTexture(String texture) {
      this.npc.display.texture = texture;
      this.npc.script.clientNeedsUpdate = true;
   }

   public int getHomeX() {
      return this.npc.getStartPos()[0];
   }

   public void setHomeX(int x) {
      this.npc.ai.startPos[0] = x;
   }

   public int getHomeY() {
      return this.npc.getStartPos()[1];
   }

   public void setHomeY(int y) {
      this.npc.ai.startPos[1] = y;
   }

   public int getHomeZ() {
      return this.npc.getStartPos()[2];
   }

   public void setHomeZ(int z) {
      this.npc.ai.startPos[2] = z;
   }

   public void setHome(int x, int y, int z) {
      this.npc.ai.startPos = new int[]{x, y, z};
   }

   public void setMaxHealth(int health) {
      this.npc.stats.setMaxHealth(health);
      this.npc.script.clientNeedsUpdate = true;
   }

   public void setReturnToHome(boolean bo) {
      this.npc.ai.returnToStart = bo;
   }

   public boolean getReturnToHome() {
      return this.npc.ai.returnToStart;
   }

   public ScriptFaction getFaction() {
      return new ScriptFaction(this.npc.getFaction());
   }

   public void setFaction(int id) {
      this.npc.setFaction(id);
   }

   public int getType() {
      return 2;
   }

   public boolean typeOf(int type) {
      return type == 2?true:super.typeOf(type);
   }

   public void shootItem(ScriptLivingBase target, ScriptItemStack item, int accuracy) {
      if(item != null) {
         if(accuracy < 0) {
            accuracy = 0;
         } else if(accuracy > 100) {
            accuracy = 100;
         }

         this.npc.shoot(target.entity, accuracy, item.item, false);
      }
   }

   public void say(String message) {
      this.npc.saySurrounding(new Line(message));
   }

   public void say(ScriptPlayer player, String message) {
      if(player != null && message != null && !message.isEmpty()) {
         this.npc.say(player.player, new Line(message));
      }
   }

   public void kill() {
      this.npc.setDead();
   }

   public void reset() {
      this.npc.reset();
   }

   public ScriptRoleInterface getRole() {
      return (ScriptRoleInterface)(this.npc.advanced.role == EnumRoleType.Bank?new ScriptRoleBank(this.npc):(this.npc.advanced.role == EnumRoleType.Follower?new ScriptRoleFollower(this.npc):(this.npc.advanced.role == EnumRoleType.Postman?new ScriptRoleMailman(this.npc):(this.npc.advanced.role == EnumRoleType.Trader?new ScriptRoleTrader(this.npc):(this.npc.advanced.role == EnumRoleType.Transporter?new ScriptRoleTransporter(this.npc):new ScriptRoleInterface(this.npc))))));
   }

   public ScriptJobInterface getJob() {
      return (ScriptJobInterface)(this.npc.advanced.job == EnumJobType.Bard?new ScriptJobBard(this.npc):(this.npc.advanced.job == EnumJobType.Conversation?new ScriptJobConversation(this.npc):(this.npc.advanced.job == EnumJobType.Follower?new ScriptJobFollower(this.npc):(this.npc.advanced.job == EnumJobType.Guard?new ScriptJobGuard(this.npc):(this.npc.advanced.job == EnumJobType.Healer?new ScriptJobHealer(this.npc):(this.npc.advanced.job == EnumJobType.Puppet?new ScriptJobPuppet(this.npc):(this.npc.advanced.job == EnumJobType.ItemGiver?new ScriptJobItemGiver(this.npc):(this.npc.advanced.job == EnumJobType.Spawner?new ScriptJobSpawner(this.npc):new ScriptJobInterface(this.npc)))))))));
   }

   public ScriptItemStack getRightItem() {
      ItemStack item = this.npc.inventory.getWeapon();
      return item != null && item.getItem() != null?new ScriptItemStack(item):null;
   }

   public void setRightItem(ScriptItemStack item) {
      if(item == null) {
         this.npc.inventory.setWeapon((ItemStack)null);
      } else {
         this.npc.inventory.setWeapon(item.item);
      }

      this.npc.script.clientNeedsUpdate = true;
   }

   public ScriptItemStack getLefttItem() {
      ItemStack item = this.npc.getOffHand();
      return item != null && item.getItem() != null?new ScriptItemStack(item):null;
   }

   public void setLeftItem(ScriptItemStack item) {
      if(item == null) {
         this.npc.inventory.setOffHand((ItemStack)null);
      } else {
         this.npc.inventory.setOffHand(item.item);
      }

      this.npc.script.clientNeedsUpdate = true;
   }

   public ScriptItemStack getProjectileItem() {
      ItemStack item = this.npc.inventory.getProjectile();
      return item != null && item.getItem() != null?new ScriptItemStack(item):null;
   }

   public void setProjectileItem(ScriptItemStack item) {
      if(item == null) {
         this.npc.inventory.setProjectile((ItemStack)null);
      } else {
         this.npc.inventory.setProjectile(item.item);
      }

      this.npc.script.aiNeedsUpdate = true;
   }

   public ScriptItemStack getArmor(int slot) {
      ItemStack item = (ItemStack)this.npc.inventory.armor.get(Integer.valueOf(slot));
      return item == null?null:new ScriptItemStack(item);
   }

   public void setArmor(int slot, ScriptItemStack item) {
      if(item == null) {
         this.npc.inventory.armor.put(Integer.valueOf(slot), (Object)null);
      } else {
         this.npc.inventory.armor.put(Integer.valueOf(slot), item.item);
      }

      this.npc.script.clientNeedsUpdate = true;
   }

   public void setAnimation(int type) {
      if(type == 0) {
         this.npc.ai.animationType = EnumAnimation.NONE;
      } else if(type == 1) {
         this.npc.ai.animationType = EnumAnimation.SITTING;
      } else if(type == 5) {
         this.npc.ai.animationType = EnumAnimation.DANCING;
      } else if(type == 4) {
         this.npc.ai.animationType = EnumAnimation.SNEAKING;
      } else if(type == 2) {
         this.npc.ai.animationType = EnumAnimation.LYING;
      } else if(type == 3) {
         this.npc.ai.animationType = EnumAnimation.HUG;
      }

   }

   public void setVisibleType(int type) {
      this.npc.display.visible = type;
      this.npc.script.clientNeedsUpdate = true;
   }

   public int getVisibleType() {
      return this.npc.display.visible;
   }

   public void setShowName(int type) {
      this.npc.display.showName = type;
      this.npc.script.clientNeedsUpdate = true;
   }

   public int getShowName() {
      return this.npc.display.showName;
   }

   public int getShowBossBar() {
      return this.npc.display.showBossBar;
   }

   public void setShowBossBar(int type) {
      this.npc.display.showBossBar = (byte)type;
      this.npc.script.clientNeedsUpdate = true;
   }

   public int getMeleeStrength() {
      return this.npc.stats.getAttackStrength();
   }

   public void setMeleeStrength(int strength) {
      this.npc.stats.setAttackStrength(strength);
   }

   public int getMeleeSpeed() {
      return this.npc.stats.attackSpeed;
   }

   public void setMeleeSpeed(int speed) {
      this.npc.stats.attackSpeed = speed;
   }

   public int getRangedStrength() {
      return this.npc.stats.pDamage;
   }

   public void setRangedStrength(int strength) {
      this.npc.stats.pDamage = strength;
   }

   public int getRangedSpeed() {
      return this.npc.stats.pSpeed;
   }

   public void setRangedSpeed(int speed) {
      this.npc.stats.pSpeed = speed;
   }

   public int getRangedBurst() {
      return this.npc.stats.burstCount;
   }

   public void setRangedBurst(int count) {
      this.npc.stats.burstCount = count;
   }

   public void giveItem(ScriptPlayer player, ScriptItemStack item) {
      this.npc.givePlayerItem(player.player, item.item);
   }

   public void executeCommand(String command) {
      NoppesUtilServer.runCommand(this.npc, this.npc.getCommandSenderName(), command, (EntityPlayer)null);
   }

   public void setHeadScale(float x, float y, float z) {
      this.npc.modelData.head.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
      this.npc.modelData.head.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
      this.npc.modelData.head.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);
      this.npc.script.clientNeedsUpdate = true;
   }

   public void setBodyScale(float x, float y, float z) {
      this.npc.modelData.body.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
      this.npc.modelData.body.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
      this.npc.modelData.body.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);
      this.npc.script.clientNeedsUpdate = true;
   }

   public void setArmsScale(float x, float y, float z) {
      this.npc.modelData.arms.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
      this.npc.modelData.arms.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
      this.npc.modelData.arms.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);
      this.npc.script.clientNeedsUpdate = true;
   }

   public void setLegsScale(float x, float y, float z) {
      this.npc.modelData.legs.scaleX = ValueUtil.correctFloat(x, 0.5F, 1.5F);
      this.npc.modelData.legs.scaleY = ValueUtil.correctFloat(y, 0.5F, 1.5F);
      this.npc.modelData.legs.scaleZ = ValueUtil.correctFloat(z, 0.5F, 1.5F);
      this.npc.script.clientNeedsUpdate = true;
   }

   public void seExplosionResistance(float resistance) {
      this.npc.stats.resistances.explosion = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
   }

   public float getExplosionResistance() {
      return this.npc.stats.resistances.explosion;
   }

   public void setMeleeResistance(float resistance) {
      this.npc.stats.resistances.playermelee = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
   }

   public float getMeleeResistance() {
      return this.npc.stats.resistances.playermelee;
   }

   public void setArrowResistance(float resistance) {
      this.npc.stats.resistances.arrow = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
   }

   public float getArrowResistance() {
      return this.npc.stats.resistances.arrow;
   }

   public void setKnockbackResistance(float resistance) {
      this.npc.stats.resistances.knockback = ValueUtil.correctFloat(resistance, 0.0F, 2.0F);
   }

   public float getKnockbackResistance() {
      return this.npc.stats.resistances.knockback;
   }

   public void setRetaliateType(int type) {
      this.npc.ai.onAttack = type;
      this.npc.setResponse();
   }

   public int getCombatRegen() {
      return this.npc.stats.combatRegen;
   }

   public void setCombatRegen(int regen) {
      this.npc.stats.combatRegen = regen;
   }

   public int getHealthRegen() {
      return this.npc.stats.healthRegen;
   }

   public void setHealthRegen(int regen) {
      this.npc.stats.healthRegen = regen;
   }

   public long getAge() {
      return this.npc.totalTicksAlive;
   }
}
