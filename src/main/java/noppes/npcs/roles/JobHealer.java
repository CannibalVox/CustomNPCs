package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobHealer extends JobInterface {

   private long healTicks = 0L;
   public int range = 5;
   public int speed = 5;
   private List toHeal = new ArrayList();


   public JobHealer(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("HealerRange", this.range);
      nbttagcompound.setInteger("HealerSpeed", this.speed);
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.range = nbttagcompound.getInteger("HealerRange");
      this.speed = nbttagcompound.getInteger("HealerSpeed");
   }

   public boolean aiShouldExecute() {
      ++this.healTicks;
      if(this.healTicks < (long)(this.speed * 10)) {
         return false;
      } else {
         Iterator var1 = super.npc.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, super.npc.boundingBox.expand((double)this.range, (double)(this.range / 2), (double)this.range)).iterator();

         while(var1.hasNext()) {
            Object plObj = var1.next();
            EntityLivingBase entity = (EntityLivingBase)plObj;
            if(entity instanceof EntityPlayer) {
               EntityPlayer npc = (EntityPlayer)entity;
               if(npc.getHealth() < npc.getMaxHealth() && !super.npc.faction.isAggressiveToPlayer(npc)) {
                  this.toHeal.add(npc);
               }
            }

            if(entity instanceof EntityNPCInterface) {
               EntityNPCInterface npc1 = (EntityNPCInterface)entity;
               if(npc1.getHealth() < npc1.getMaxHealth() && !super.npc.faction.isAggressiveToNpc(npc1)) {
                  this.toHeal.add(npc1);
               }
            }
         }

         this.healTicks = 0L;
         return !this.toHeal.isEmpty();
      }
   }

   public void aiStartExecuting() {
      Iterator var1 = this.toHeal.iterator();

      while(var1.hasNext()) {
         EntityLivingBase entity = (EntityLivingBase)var1.next();
         float heal = entity.getMaxHealth() / 20.0F;
         this.heal(entity, heal > 0.0F?heal:1.0F);
      }

      this.toHeal.clear();
   }

   public void heal(EntityLivingBase entity, float amount) {
      entity.heal(amount);
      NoppesUtilServer.spawnParticle(entity, "heal", entity.dimension);
   }
}
