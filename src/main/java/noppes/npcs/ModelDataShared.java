package noppes.npcs;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.ModelPartData;

public class ModelDataShared {

   public ModelPartConfig arms = new ModelPartConfig();
   public ModelPartConfig body = new ModelPartConfig();
   public ModelPartConfig legs = new ModelPartConfig();
   public ModelPartConfig head = new ModelPartConfig();
   public ModelPartData legParts = new ModelPartData();
   public Class entityClass;
   public EntityLivingBase entity;
   public NBTTagCompound extra = new NBTTagCompound();
   private HashMap parts = new HashMap();
   public byte breasts = 0;
   public byte headwear = 2;


   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      if(this.entityClass != null) {
         compound.setString("EntityClass", this.entityClass.getCanonicalName());
      }

      compound.setTag("ArmsConfig", this.arms.writeToNBT());
      compound.setTag("BodyConfig", this.body.writeToNBT());
      compound.setTag("LegsConfig", this.legs.writeToNBT());
      compound.setTag("HeadConfig", this.head.writeToNBT());
      compound.setTag("LegParts", this.legParts.writeToNBT());
      compound.setByte("Headwear", this.headwear);
      compound.setByte("Breasts", this.breasts);
      compound.setTag("ExtraData", this.extra);
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.parts.keySet().iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         NBTTagCompound item = ((ModelPartData)this.parts.get(name)).writeToNBT();
         item.setString("PartName", name);
         list.appendTag(item);
      }

      compound.setTag("Parts", list);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.setEntityClass(compound.getString("EntityClass"));
      this.arms.readFromNBT(compound.getCompoundTag("ArmsConfig"));
      this.body.readFromNBT(compound.getCompoundTag("BodyConfig"));
      this.legs.readFromNBT(compound.getCompoundTag("LegsConfig"));
      this.head.readFromNBT(compound.getCompoundTag("HeadConfig"));
      this.legParts.readFromNBT(compound.getCompoundTag("LegParts"));
      this.headwear = compound.getByte("Headwear");
      this.breasts = compound.getByte("Breasts");
      this.extra = compound.getCompoundTag("ExtraData");
      HashMap parts = new HashMap();
      NBTTagList list = compound.getTagList("Parts", 10);

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound item = list.getCompoundTagAt(i);
         ModelPartData part = new ModelPartData();
         part.readFromNBT(item);
         parts.put(item.getString("PartName"), part);
      }

      this.parts = parts;
   }

   private void setEntityClass(String string) {
      this.entityClass = null;
      this.entity = null;

      try {
         Class e = Class.forName(string);
         if(EntityLivingBase.class.isAssignableFrom(e)) {
            this.entityClass = e.asSubclass(EntityLivingBase.class);
         }
      } catch (ClassNotFoundException var3) {
         ;
      }

   }

   public void setEntityClass(Class entityClass) {
      this.entityClass = entityClass;
      this.entity = null;
      this.extra = new NBTTagCompound();
      if(entityClass == EntityHorse.class) {
         this.extra.setInteger("Type", -1);
      }

   }

   public Class getEntityClass() {
      return this.entityClass;
   }

   public float offsetY() {
      return this.entity == null?-this.getBodyY():this.entity.height - 1.8F;
   }

   public void clearEntity() {
      this.entity = null;
   }

   public ModelPartData getPartData(String type) {
      return (ModelPartData)this.parts.get(type);
   }

   public void removePart(String type) {
      this.parts.remove(type);
   }

   public ModelPartData getOrCreatePart(String type) {
      ModelPartData part = (ModelPartData)this.parts.get(type);
      if(part == null) {
         this.parts.put(type, part = new ModelPartData());
      }

      return part;
   }

   public float getBodyY() {
      return this.legParts.type == 3?(0.9F - this.body.scaleY) * 0.75F + this.getLegsY():(this.legParts.type == 3?(0.5F - this.body.scaleY) * 0.75F + this.getLegsY():(1.0F - this.body.scaleY) * 0.75F + this.getLegsY());
   }

   public float getLegsY() {
      return this.legParts.type == 3?(0.87F - this.legs.scaleY) * 1.0F:(1.0F - this.legs.scaleY) * 0.75F;
   }
}
