package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;
import org.apache.commons.lang3.RandomStringUtils;

public class JobSpawner extends JobInterface {

   public NBTTagCompound compound6;
   public NBTTagCompound compound5;
   public NBTTagCompound compound4;
   public NBTTagCompound compound3;
   public NBTTagCompound compound2;
   public NBTTagCompound compound1;
   public String title1;
   public String title2;
   public String title3;
   public String title4;
   public String title5;
   public String title6;
   private int number = 0;
   public List spawned = new ArrayList();
   private Map cooldown = new HashMap();
   private String id = RandomStringUtils.random(8, true, true);
   public boolean doesntDie = false;
   public int spawnType = 0;
   public int xOffset = 0;
   public int yOffset = 0;
   public int zOffset = 0;
   private EntityLivingBase target;
   public boolean despawnOnTargetLost = true;


   public JobSpawner(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.saveCompound(this.compound1, "SpawnerNBT1", compound);
      this.saveCompound(this.compound2, "SpawnerNBT2", compound);
      this.saveCompound(this.compound3, "SpawnerNBT3", compound);
      this.saveCompound(this.compound4, "SpawnerNBT4", compound);
      this.saveCompound(this.compound5, "SpawnerNBT5", compound);
      this.saveCompound(this.compound6, "SpawnerNBT6", compound);
      compound.setString("SpawnerId", this.id);
      compound.setBoolean("SpawnerDoesntDie", this.doesntDie);
      compound.setInteger("SpawnerType", this.spawnType);
      compound.setInteger("SpawnerXOffset", this.xOffset);
      compound.setInteger("SpawnerYOffset", this.yOffset);
      compound.setInteger("SpawnerZOffset", this.zOffset);
      compound.setBoolean("DespawnOnTargetLost", this.despawnOnTargetLost);
      return compound;
   }

   public NBTTagCompound getTitles() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setString("Title1", this.getTitle(this.compound1));
      compound.setString("Title2", this.getTitle(this.compound2));
      compound.setString("Title3", this.getTitle(this.compound3));
      compound.setString("Title4", this.getTitle(this.compound4));
      compound.setString("Title5", this.getTitle(this.compound5));
      compound.setString("Title6", this.getTitle(this.compound6));
      return compound;
   }

   private String getTitle(NBTTagCompound compound) {
      return compound != null && compound.hasKey("ClonedName")?compound.getString("ClonedName"):"gui.selectnpc";
   }

   private void saveCompound(NBTTagCompound save, String name, NBTTagCompound compound) {
      if(save != null) {
         compound.setTag(name, save);
      }

   }

   public void readFromNBT(NBTTagCompound compound) {
      this.compound1 = compound.getCompoundTag("SpawnerNBT1");
      this.compound2 = compound.getCompoundTag("SpawnerNBT2");
      this.compound3 = compound.getCompoundTag("SpawnerNBT3");
      this.compound4 = compound.getCompoundTag("SpawnerNBT4");
      this.compound5 = compound.getCompoundTag("SpawnerNBT5");
      this.compound6 = compound.getCompoundTag("SpawnerNBT6");
      this.id = compound.getString("SpawnerId");
      this.doesntDie = compound.getBoolean("SpawnerDoesntDie");
      this.spawnType = compound.getInteger("SpawnerType");
      this.xOffset = compound.getInteger("SpawnerXOffset");
      this.yOffset = compound.getInteger("SpawnerYOffset");
      this.zOffset = compound.getInteger("SpawnerZOffset");
      this.despawnOnTargetLost = compound.getBoolean("DespawnOnTargetLost");
   }

   public void cleanCompound(NBTTagCompound compound) {
      compound.removeTag("SpawnerNBT1");
      compound.removeTag("SpawnerNBT2");
      compound.removeTag("SpawnerNBT3");
      compound.removeTag("SpawnerNBT4");
      compound.removeTag("SpawnerNBT5");
      compound.removeTag("SpawnerNBT6");
   }

   public void setJobCompound(int i, NBTTagCompound compound) {
      if(i == 1) {
         this.compound1 = compound;
      }

      if(i == 2) {
         this.compound2 = compound;
      }

      if(i == 3) {
         this.compound3 = compound;
      }

      if(i == 4) {
         this.compound4 = compound;
      }

      if(i == 5) {
         this.compound5 = compound;
      }

      if(i == 6) {
         this.compound6 = compound;
      }

   }

   public void aiUpdateTask() {
      if(this.spawned.isEmpty()) {
         if(this.spawnType == 0 && this.spawnEntity(this.number + 1) == null && !this.doesntDie) {
            super.npc.setDead();
         }

         if(this.spawnType == 1) {
            if(this.number >= 6 && !this.doesntDie) {
               super.npc.setDead();
            } else {
               this.spawnEntity(this.compound1);
               this.spawnEntity(this.compound2);
               this.spawnEntity(this.compound3);
               this.spawnEntity(this.compound4);
               this.spawnEntity(this.compound5);
               this.spawnEntity(this.compound6);
               this.number = 6;
            }
         }

         if(this.spawnType == 2) {
            ArrayList list = new ArrayList();
            if(this.compound1 != null && this.compound1.hasKey("id")) {
               list.add(this.compound1);
            }

            if(this.compound2 != null && this.compound2.hasKey("id")) {
               list.add(this.compound2);
            }

            if(this.compound3 != null && this.compound3.hasKey("id")) {
               list.add(this.compound3);
            }

            if(this.compound4 != null && this.compound4.hasKey("id")) {
               list.add(this.compound4);
            }

            if(this.compound5 != null && this.compound5.hasKey("id")) {
               list.add(this.compound5);
            }

            if(this.compound6 != null && this.compound6.hasKey("id")) {
               list.add(this.compound6);
            }

            if(!list.isEmpty()) {
               NBTTagCompound compound = (NBTTagCompound)list.get(super.npc.getRNG().nextInt(list.size()));
               this.spawnEntity(compound);
            } else if(!this.doesntDie) {
               super.npc.setDead();
            }
         }
      } else {
         this.checkSpawns();
      }

   }

   public void checkSpawns() {
      Iterator iterator = this.spawned.iterator();

      while(iterator.hasNext()) {
         EntityLivingBase spawn = (EntityLivingBase)iterator.next();
         if(this.shouldDelete(spawn)) {
            spawn.isDead = true;
            iterator.remove();
         } else {
            this.checkTarget(spawn);
         }
      }

   }

   public void checkTarget(EntityLivingBase entity) {
      if(entity instanceof EntityLiving) {
         EntityLiving liv = (EntityLiving)entity;
         if(liv.getAttackTarget() == null || super.npc.getRNG().nextInt(100) == 1) {
            liv.setAttackTarget(this.target);
         }
      } else if(entity.getAITarget() == null || super.npc.getRNG().nextInt(100) == 1) {
         entity.setRevengeTarget(this.target);
      }

   }

   public boolean shouldDelete(EntityLivingBase entity) {
      return super.npc.getDistanceToEntity(entity) > 60.0F || entity.isDead || entity.getHealth() <= 0.0F || CustomNpcs.PixelMonEnabled && !PixelmonHelper.isBattling(entity) || this.despawnOnTargetLost && this.getTarget(super.npc) == null;
   }

   private EntityLivingBase getTarget() {
      EntityLivingBase target = this.getTarget(super.npc);
      if(target != null) {
         return target;
      } else {
         Iterator var2 = this.spawned.iterator();

         do {
            if(!var2.hasNext()) {
               return null;
            }

            EntityLivingBase entity = (EntityLivingBase)var2.next();
            target = this.getTarget(entity);
         } while(target == null);

         return target;
      }
   }

   private EntityLivingBase getTarget(EntityLivingBase entity) {
      if(entity instanceof EntityLiving) {
         this.target = ((EntityLiving)entity).getAttackTarget();
         if(this.target != null && !this.target.isDead && this.target.getHealth() > 0.0F) {
            return this.target;
         }
      }

      this.target = entity.getAITarget();
      return this.target != null && !this.target.isDead && this.target.getHealth() > 0.0F?this.target:null;
   }

   private boolean isEmpty() {
      return this.compound1 != null && this.compound1.hasKey("id")?false:(this.compound2 != null && this.compound2.hasKey("id")?false:(this.compound3 != null && this.compound3.hasKey("id")?false:(this.compound4 != null && this.compound4.hasKey("id")?false:(this.compound5 != null && this.compound5.hasKey("id")?false:this.compound6 == null || !this.compound6.hasKey("id")))));
   }

   private void setTarget(EntityLivingBase base, EntityLivingBase target) {
      if(PixelmonHelper.isTrainer(base) && target instanceof EntityPlayerMP) {
         EntityPlayerMP player = (EntityPlayerMP)target;
         if(!PixelmonHelper.canBattle(player, super.npc)) {
            return;
         }

         this.cooldown.put(player.getCommandSenderName(), Long.valueOf(System.currentTimeMillis()));
         Iterator ita = this.cooldown.entrySet().iterator();

         while(ita.hasNext()) {
            Entry entry = (Entry)ita.next();
            if(!this.isOnCooldown((String)entry.getKey())) {
               ita.remove();
            }
         }
      } else if(base instanceof EntityLiving) {
         ((EntityLiving)base).setAttackTarget(target);
      } else {
         base.setRevengeTarget(target);
      }

   }

   public boolean aiShouldExecute() {
      if(!this.isEmpty() && !super.npc.isKilled()) {
         this.target = this.getTarget();
         if(super.npc.getRNG().nextInt(30) == 1 && this.spawned.isEmpty()) {
            this.spawned = this.getNearbySpawned();
         }

         if(!this.spawned.isEmpty()) {
            this.checkSpawns();
         }

         return this.target != null;
      } else {
         return false;
      }
   }

   public boolean aiContinueExecute() {
      return this.aiShouldExecute();
   }

   public void resetTask() {
      this.reset();
   }

   public void aiStartExecuting() {
      this.number = 0;

      EntityLivingBase entity;
      for(Iterator var1 = this.spawned.iterator(); var1.hasNext(); this.setTarget(entity, super.npc.getAttackTarget())) {
         entity = (EntityLivingBase)var1.next();
         int i = entity.getEntityData().getInteger("NpcSpawnerNr");
         if(i > this.number) {
            this.number = i;
         }
      }

   }

   public void reset() {
      this.number = 0;
      if(this.spawned.isEmpty()) {
         this.spawned = this.getNearbySpawned();
      }

      EntityLivingBase entity;
      for(Iterator var1 = this.spawned.iterator(); var1.hasNext(); entity.isDead = true) {
         entity = (EntityLivingBase)var1.next();
      }

      this.spawned.clear();
      this.target = null;
   }

   public void killed() {
      this.reset();
   }

   public EntityLivingBase spawnEntity(int i) {
      NBTTagCompound compound = this.getCompound(i);
      return compound == null?null:this.spawnEntity(compound);
   }

   private EntityLivingBase spawnEntity(NBTTagCompound compound) {
      if(compound != null && compound.hasKey("id")) {
         EntityLivingBase entity = (EntityLivingBase)EntityList.createEntityFromNBT(compound, super.npc.worldObj);
         if(entity == null) {
            return null;
         } else {
            entity.getEntityData().setString("NpcSpawnerId", this.id);
            entity.getEntityData().setInteger("NpcSpawnerNr", this.number);
            this.setTarget(entity, super.npc.getAttackTarget());
            entity.setPosition(super.npc.posX + (double)this.xOffset - 0.5D + (double)entity.getRNG().nextFloat(), super.npc.posY + (double)this.yOffset, super.npc.posZ + (double)this.zOffset - 0.5D + (double)entity.getRNG().nextFloat());
            if(entity instanceof EntityNPCInterface) {
               EntityNPCInterface snpc = (EntityNPCInterface)entity;
               snpc.stats.spawnCycle = 3;
               snpc.ai.returnToStart = false;
            }

            super.npc.worldObj.spawnEntityInWorld(entity);
            this.spawned.add(entity);
            return entity;
         }
      } else {
         return null;
      }
   }

   private NBTTagCompound getCompound(int i) {
      if(i <= 1 && this.compound1 != null && this.compound1.hasKey("id")) {
         this.number = 1;
         return this.compound1;
      } else if(i <= 2 && this.compound2 != null && this.compound2.hasKey("id")) {
         this.number = 2;
         return this.compound2;
      } else if(i <= 3 && this.compound3 != null && this.compound3.hasKey("id")) {
         this.number = 3;
         return this.compound3;
      } else if(i <= 4 && this.compound4 != null && this.compound4.hasKey("id")) {
         this.number = 4;
         return this.compound4;
      } else if(i <= 5 && this.compound5 != null && this.compound5.hasKey("id")) {
         this.number = 5;
         return this.compound5;
      } else if(i <= 6 && this.compound6 != null && this.compound6.hasKey("id")) {
         this.number = 6;
         return this.compound6;
      } else {
         return null;
      }
   }

   private List getNearbySpawned() {
      ArrayList spawnList = new ArrayList();
      List list = super.npc.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, super.npc.boundingBox.expand(40.0D, 40.0D, 40.0D));
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         EntityLivingBase entity = (EntityLivingBase)var3.next();
         if(entity.getEntityData().getString("NpcSpawnerId").equals(this.id) && !entity.isDead) {
            spawnList.add(entity);
         }
      }

      return spawnList;
   }

   public boolean isOnCooldown(String name) {
      if(!this.cooldown.containsKey(name)) {
         return false;
      } else {
         long time = ((Long)this.cooldown.get(name)).longValue();
         return System.currentTimeMillis() < time + 1200000L;
      }
   }
}
