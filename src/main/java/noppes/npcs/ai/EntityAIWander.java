package noppes.npcs.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ai.RandomPositionGeneratorAlt;
import noppes.npcs.ai.selector.NPCInteractSelector;
import noppes.npcs.constants.AiMutex;
import noppes.npcs.controllers.Line;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIWander extends EntityAIBase {

   private EntityNPCInterface entity;
   public final IEntitySelector selector;
   private double xPosition;
   private double yPosition;
   private double zPosition;
   private EntityNPCInterface nearbyNPC;


   public EntityAIWander(EntityNPCInterface npc) {
      this.entity = npc;
      this.setMutexBits(AiMutex.PASSIVE);
      this.selector = new NPCInteractSelector(npc);
   }

   public boolean shouldExecute() {
      if(this.entity.getAge() < 100 && this.entity.getNavigator().noPath() && !this.entity.isInteracting() && this.entity.getRNG().nextInt(80) == 0) {
         if(this.entity.ai.npcInteracting && this.entity.getRNG().nextInt(4) == 1) {
            this.nearbyNPC = this.getNearbyNPC();
         }

         if(this.nearbyNPC != null) {
            this.xPosition = (double)MathHelper.floor_double(this.nearbyNPC.posX);
            this.yPosition = (double)MathHelper.floor_double(this.nearbyNPC.posY);
            this.zPosition = (double)MathHelper.floor_double(this.nearbyNPC.posZ);
            this.nearbyNPC.addInteract(this.entity);
         } else {
            Vec3 vec = this.getVec();
            if(vec == null) {
               return false;
            }

            this.xPosition = vec.xCoord;
            this.yPosition = vec.yCoord;
            this.zPosition = vec.zCoord;
         }

         return true;
      } else {
         return false;
      }
   }

   public void updateTask() {
      if(this.nearbyNPC != null) {
         this.nearbyNPC.getNavigator().clearPathEntity();
      }

   }

   private EntityNPCInterface getNearbyNPC() {
      List list = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand((double)this.entity.ai.walkingRange, this.entity.ai.walkingRange > 7?7.0D:(double)this.entity.ai.walkingRange, (double)this.entity.ai.walkingRange), this.selector);
      Iterator ita = list.iterator();

      while(ita.hasNext()) {
         EntityNPCInterface npc = (EntityNPCInterface)ita.next();
         if(!npc.ai.stopAndInteract || npc.isAttacking() || !npc.isEntityAlive() || this.entity.faction.isAggressiveToNpc(npc)) {
            ita.remove();
         }
      }

      if(list.isEmpty()) {
         return null;
      } else {
         return (EntityNPCInterface)list.get(this.entity.getRNG().nextInt(list.size()));
      }
   }

   private Vec3 getVec() {
      if(this.entity.ai.walkingRange > 0) {
         double distance = this.entity.getDistanceSq((double)this.entity.getStartXPos(), this.entity.getStartYPos(), (double)this.entity.getStartZPos());
         int range = (int)MathHelper.sqrt_double((double)(this.entity.ai.walkingRange * this.entity.ai.walkingRange) - distance);
         if(range > CustomNpcs.NpcNavRange) {
            range = CustomNpcs.NpcNavRange;
         }

         if(range < 3) {
            range = this.entity.ai.walkingRange;
            if(range > CustomNpcs.NpcNavRange) {
               range = CustomNpcs.NpcNavRange;
            }

            Vec3 start = Vec3.createVectorHelper((double)this.entity.getStartXPos(), this.entity.getStartYPos(), (double)this.entity.getStartZPos());
            return RandomPositionGeneratorAlt.findRandomTargetBlockTowards(this.entity, range / 2, range / 2 > 7?7:range / 2, start);
         } else {
            return RandomPositionGeneratorAlt.findRandomTarget(this.entity, range, range / 2 > 7?7:range / 2);
         }
      } else {
         return RandomPositionGeneratorAlt.findRandomTarget(this.entity, CustomNpcs.NpcNavRange, 7);
      }
   }

   public boolean continueExecuting() {
      return this.nearbyNPC != null && !this.selector.isEntityApplicable(this.nearbyNPC)?false:!this.entity.getNavigator().noPath() && this.entity.isEntityAlive() && !this.entity.isInteracting();
   }

   public void startExecuting() {
      this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1.0D);
   }

   public void resetTask() {
      if(this.nearbyNPC != null && this.entity.getDistanceSqToEntity(this.nearbyNPC) < 12.0D) {
         Line line = new Line(".........");
         line.hideText = true;
         if(this.entity.getRNG().nextBoolean()) {
            this.entity.saySurrounding(line);
         } else {
            this.nearbyNPC.saySurrounding(line);
         }

         this.entity.addInteract(this.nearbyNPC);
         this.nearbyNPC.addInteract(this.entity);
      }

      this.nearbyNPC = null;
   }
}
