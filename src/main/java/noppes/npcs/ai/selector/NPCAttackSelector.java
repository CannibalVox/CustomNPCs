package noppes.npcs.ai.selector;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobGuard;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.companion.CompanionGuard;

public class NPCAttackSelector implements IEntitySelector {

   private EntityNPCInterface npc;


   public NPCAttackSelector(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean isEntityApplicable(Entity entity) {
      if(entity.isEntityAlive() && entity != this.npc && this.npc.getDistanceToEntity(entity) <= (float)this.npc.stats.aggroRange && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() >= 1.0F) {
         if(this.npc.ai.directLOS && !this.npc.getEntitySenses().canSee(entity)) {
            return false;
         } else if(!this.npc.stats.attackInvisible && ((EntityLivingBase)entity).isPotionActive(Potion.invisibility) && this.npc.getDistanceSqToEntity(entity) < 9.0D) {
            return false;
         } else {
            if(!this.npc.isFollower() && this.npc.ai.returnToStart) {
               int role = this.npc.stats.aggroRange * 2;
               if(this.npc.ai.movingType == EnumMovingType.Wandering) {
                  role += this.npc.ai.walkingRange;
               }

               double distance = entity.getDistanceSq((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos());
               if(this.npc.ai.movingType == EnumMovingType.MovingPath) {
                  int[] arr = this.npc.ai.getCurrentMovingPath();
                  distance = entity.getDistanceSq((double)arr[0], (double)arr[1], (double)arr[2]);
               }

               if(distance > (double)(role * role)) {
                  return false;
               }
            }

            if(this.npc.advanced.job == EnumJobType.Guard && ((JobGuard)this.npc.jobInterface).isEntityApplicable(entity)) {
               return true;
            } else {
               if(this.npc.advanced.role == EnumRoleType.Companion) {
                  RoleCompanion role1 = (RoleCompanion)this.npc.roleInterface;
                  if(role1.job == EnumCompanionJobs.GUARD && ((CompanionGuard)role1.jobInterface).isEntityApplicable(entity)) {
                     return true;
                  }
               }

               if(entity instanceof EntityPlayerMP) {
                  return this.npc.faction.isAggressiveToPlayer((EntityPlayer)entity)?(CustomNpcs.PixelMonEnabled && this.npc.advanced.job == EnumJobType.Spawner?PixelmonHelper.canBattle((EntityPlayerMP)entity, this.npc):!((EntityPlayerMP)entity).capabilities.disableDamage):false;
               } else {
                  if(entity instanceof EntityNPCInterface) {
                     if(((EntityNPCInterface)entity).isKilled()) {
                        return false;
                     }

                     if(this.npc.advanced.attackOtherFactions) {
                        return this.npc.faction.isAggressiveToNpc((EntityNPCInterface)entity);
                     }
                  }

                  return false;
               }
            }
         }
      } else {
         return false;
      }
   }
}
