package noppes.npcs.scripted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.ScriptItemStack;
import noppes.npcs.scripted.ScriptLivingBase;

public class ScriptEntity {

   protected Entity entity;
   private Map tempData = new HashMap();


   public ScriptEntity(Entity entity) {
      this.entity = entity;
   }

   public double getX() {
      return this.entity.posX;
   }

   public void setX(double x) {
      this.entity.posX = x;
   }

   public double getY() {
      return this.entity.posY;
   }

   public void setY(double y) {
      this.entity.posY = y;
   }

   public double getZ() {
      return this.entity.posZ;
   }

   public void setZ(double z) {
      this.entity.posZ = z;
   }

   public int getBlockX() {
      return MathHelper.floor_double(this.entity.posX);
   }

   public int getBlockY() {
      return MathHelper.floor_double(this.entity.posY);
   }

   public int getBlockZ() {
      return MathHelper.floor_double(this.entity.posZ);
   }

   public void setPosition(double x, double y, double z) {
      this.entity.setPosition(x, y, z);
   }

   public ScriptEntity[] getSurroundingEntities(int range) {
      List entities = this.entity.worldObj.getEntitiesWithinAABB(Entity.class, this.entity.boundingBox.expand((double)range, (double)range, (double)range));
      ArrayList list = new ArrayList();
      Iterator var4 = entities.iterator();

      while(var4.hasNext()) {
         Entity living = (Entity)var4.next();
         if(living != this.entity) {
            list.add(ScriptController.Instance.getScriptForEntity(living));
         }
      }

      return (ScriptEntity[])list.toArray(new ScriptLivingBase[list.size()]);
   }

   public ScriptEntity[] getSurroundingEntities(int range, int type) {
      Class cls = Entity.class;
      if(type == 5) {
         cls = EntityLivingBase.class;
      } else if(type == 1) {
         cls = EntityPlayer.class;
      } else if(type == 4) {
         cls = EntityAnimal.class;
      } else if(type == 3) {
         cls = EntityMob.class;
      } else if(type == 2) {
         cls = EntityNPCInterface.class;
      }

      List entities = this.entity.worldObj.getEntitiesWithinAABB(cls, this.entity.boundingBox.expand((double)range, (double)range, (double)range));
      ArrayList list = new ArrayList();
      Iterator var6 = entities.iterator();

      while(var6.hasNext()) {
         Entity living = (Entity)var6.next();
         if(living != this.entity) {
            list.add(ScriptController.Instance.getScriptForEntity(living));
         }
      }

      return (ScriptEntity[])list.toArray(new ScriptLivingBase[list.size()]);
   }

   public boolean isAlive() {
      return this.entity.isEntityAlive();
   }

   public Object getTempData(String key) {
      return this.tempData.get(key);
   }

   public void setTempData(String key, Object value) {
      this.tempData.put(key, value);
   }

   public boolean hasTempData(String key) {
      return this.tempData.containsKey(key);
   }

   public void removeTempData(String key) {
      this.tempData.remove(key);
   }

   public void clearTempData() {
      this.tempData.clear();
   }

   public Object getStoredData(String key) {
      NBTTagCompound compound = this.getStoredCompound();
      if(!compound.hasKey(key)) {
         return null;
      } else {
         NBTBase base = compound.getTag(key);
         return base instanceof NBTPrimitive?Double.valueOf(((NBTPrimitive)base).getDouble()):((NBTTagString)base).getString();
      }
   }

   public void setStoredData(String key, Object value) {
      NBTTagCompound compound = this.getStoredCompound();
      if(value instanceof Number) {
         compound.setDouble(key, ((Number)value).doubleValue());
      } else if(value instanceof String) {
         compound.setString(key, (String)value);
      }

      this.saveStoredCompound(compound);
   }

   public boolean hasStoredData(String key) {
      return this.getStoredCompound().hasKey(key);
   }

   public void removeStoredData(String key) {
      NBTTagCompound compound = this.getStoredCompound();
      compound.removeTag(key);
      this.saveStoredCompound(compound);
   }

   public void clearStoredData() {
      this.entity.getEntityData().removeTag("CNPCStoredData");
   }

   private NBTTagCompound getStoredCompound() {
      NBTTagCompound compound = this.entity.getEntityData().getCompoundTag("CNPCStoredData");
      if(compound == null) {
         this.entity.getEntityData().setTag("CNPCStoredData", compound = new NBTTagCompound());
      }

      return compound;
   }

   private void saveStoredCompound(NBTTagCompound compound) {
      this.entity.getEntityData().setTag("CNPCStoredData", compound);
   }

   public long getAge() {
      return (long)this.entity.ticksExisted;
   }

   public void despawn() {
      this.entity.isDead = true;
   }

   public boolean inWater() {
      return this.entity.isInsideOfMaterial(Material.water);
   }

   public boolean inLava() {
      return this.entity.isInsideOfMaterial(Material.lava);
   }

   public boolean inFire() {
      return this.entity.isInsideOfMaterial(Material.fire);
   }

   public boolean isBurning() {
      return this.entity.isBurning();
   }

   public void setBurning(int ticks) {
      this.entity.setFire(ticks);
   }

   public void extinguish() {
      this.entity.extinguish();
   }

   public String getTypeName() {
      return EntityList.getEntityString(this.entity);
   }

   public void dropItem(ScriptItemStack item) {
      this.entity.entityDropItem(item.item, 0.0F);
   }

   public ScriptEntity getRider() {
      return ScriptController.Instance.getScriptForEntity(this.entity.riddenByEntity);
   }

   public void setRider(ScriptEntity entity) {
      if(entity == null) {
         this.entity.riddenByEntity = null;
      } else {
         this.entity.riddenByEntity = entity.entity;
      }

   }

   public ScriptEntity getMount() {
      return ScriptController.Instance.getScriptForEntity(this.entity.ridingEntity);
   }

   public void setMount(ScriptEntity entity) {
      if(entity == null) {
         this.entity.ridingEntity = null;
      } else {
         this.entity.ridingEntity = entity.entity;
      }

   }

   public int getType() {
      return 0;
   }

   public boolean typeOf(int type) {
      return type == 0;
   }

   public void setRotation(float rotation) {
      this.entity.rotationYaw = rotation;
   }

   public float getRotation() {
      return this.entity.rotationYaw;
   }

   public void knockback(int power, float direction) {
      float v = direction * 3.1415927F / 180.0F;
      this.entity.addVelocity((double)(-MathHelper.sin(v) * (float)power), 0.1D + (double)((float)power * 0.04F), (double)(MathHelper.cos(v) * (float)power));
      this.entity.motionX *= 0.6D;
      this.entity.motionZ *= 0.6D;
      this.entity.attackEntityFrom(DamageSource.outOfWorld, 1.0E-4F);
   }

   public boolean isSneaking() {
      return this.entity.isSneaking();
   }

   public boolean isSprinting() {
      return this.entity.isSprinting();
   }

   public Entity getMCEntity() {
      return this.entity;
   }
}
