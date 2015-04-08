package noppes.npcs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityCustomNpc extends EntityNPCInterface {

   public ModelData modelData = new ModelData();


   public EntityCustomNpc(World world) {
      super(world);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      if(compound.hasKey("NpcModelData")) {
         this.modelData.readFromNBT(compound.getCompoundTag("NpcModelData"));
      }

      super.readEntityFromNBT(compound);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setTag("NpcModelData", this.modelData.writeToNBT());
   }

   public void onUpdate() {
      super.onUpdate();
      if(this.isRemote()) {
         ModelPartData particles = this.modelData.getPartData("particles");
         if(particles != null && !this.isKilled()) {
            CustomNpcs.proxy.spawnParticle(this, "ModelData", new Object[]{this.modelData, particles});
         }

         EntityLivingBase entity = this.modelData.getEntity(this);
         if(entity != null) {
            try {
               entity.onUpdate();
            } catch (Exception var4) {
               ;
            }

            EntityUtil.Copy(this, entity);
         }
      }

   }

   public void mountEntity(Entity par1Entity) {
      super.mountEntity(par1Entity);
      this.updateHitbox();
   }

   public void updateHitbox() {
      EntityLivingBase entity = this.modelData.getEntity(this);
      if(this.modelData != null && entity != null) {
         if(entity instanceof EntityNPCInterface) {
            ((EntityNPCInterface)entity).updateHitbox();
         }

         super.width = entity.width / 5.0F * (float)super.display.modelSize;
         super.height = entity.height / 5.0F * (float)super.display.modelSize;
         if(super.width < 0.1F) {
            super.width = 0.1F;
         }

         if(super.height < 0.1F) {
            super.height = 0.1F;
         }

         this.setPosition(super.posX, super.posY, super.posZ);
      } else {
         super.baseHeight = 1.9F - this.modelData.getBodyY() + (this.modelData.head.scaleY - 1.0F) / 2.0F;
         super.updateHitbox();
      }

   }
}
