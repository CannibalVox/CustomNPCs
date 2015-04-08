package noppes.npcs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import noppes.npcs.NBTTags;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumNavType;
import noppes.npcs.constants.EnumStandingType;
import noppes.npcs.entity.EntityNPCInterface;

public class DataAI {

   private EntityNPCInterface npc;
   public int onAttack = 0;
   public int doorInteract = 2;
   public int findShelter = 2;
   public int distanceToMelee = 4;
   public int canFireIndirect = 0;
   public boolean canSwim = true;
   public boolean reactsToFire = false;
   public boolean avoidsWater = false;
   public boolean avoidsSun = false;
   public boolean returnToStart = true;
   public boolean directLOS = true;
   public boolean canLeap = false;
   public boolean canSprint = false;
   public boolean stopAndInteract = true;
   public EnumNavType tacticalVariant;
   public int useRangeMelee;
   public int tacticalRadius;
   public EnumAnimation animationType;
   public EnumStandingType standingType;
   public EnumMovingType movingType;
   public boolean npcInteracting;
   public int orientation;
   public float bodyOffsetX;
   public float bodyOffsetY;
   public float bodyOffsetZ;
   public int walkingRange;
   private int moveSpeed;
   private List movingPath;
   public int[] startPos;
   public int movingPos;
   public int movingPattern;
   public boolean movingPause;
   public boolean ignoreCobweb;


   public DataAI(EntityNPCInterface npc) {
      this.tacticalVariant = EnumNavType.Default;
      this.useRangeMelee = 0;
      this.tacticalRadius = 8;
      this.animationType = EnumAnimation.NONE;
      this.standingType = EnumStandingType.RotateBody;
      this.movingType = EnumMovingType.Standing;
      this.npcInteracting = true;
      this.orientation = 0;
      this.bodyOffsetX = 5.0F;
      this.bodyOffsetY = 5.0F;
      this.bodyOffsetZ = 5.0F;
      this.walkingRange = 5;
      this.moveSpeed = 5;
      this.movingPath = new ArrayList();
      this.movingPos = 0;
      this.movingPattern = 0;
      this.movingPause = true;
      this.ignoreCobweb = false;
      this.npc = npc;
   }

   public void readToNBT(NBTTagCompound compound) {
      this.canSwim = compound.getBoolean("CanSwim");
      this.reactsToFire = compound.getBoolean("ReactsToFire");
      this.avoidsWater = compound.getBoolean("AvoidsWater");
      this.avoidsSun = compound.getBoolean("AvoidsSun");
      this.returnToStart = compound.getBoolean("ReturnToStart");
      this.onAttack = compound.getInteger("OnAttack");
      this.doorInteract = compound.getInteger("DoorInteract");
      this.findShelter = compound.getInteger("FindShelter");
      this.directLOS = compound.getBoolean("DirectLOS");
      this.canLeap = compound.getBoolean("CanLeap");
      this.canSprint = compound.getBoolean("CanSprint");
      this.canFireIndirect = compound.getInteger("FireIndirect");
      this.useRangeMelee = compound.getInteger("RangeAndMelee");
      this.distanceToMelee = compound.getInteger("DistanceToMelee");
      this.tacticalRadius = compound.getInteger("TacticalRadius");
      this.movingPause = compound.getBoolean("MovingPause");
      this.ignoreCobweb = compound.getBoolean("IgnoreCobweb");
      this.npcInteracting = compound.getBoolean("npcInteracting");
      this.stopAndInteract = compound.getBoolean("stopAndInteract");
      this.animationType = EnumAnimation.values()[compound.getInteger("MoveState") % EnumAnimation.values().length];
      this.standingType = EnumStandingType.values()[compound.getInteger("StandingState") % EnumStandingType.values().length];
      this.movingType = EnumMovingType.values()[compound.getInteger("MovingState") % EnumMovingType.values().length];
      this.tacticalVariant = EnumNavType.values()[compound.getInteger("TacticalVariant") % EnumNavType.values().length];
      this.orientation = compound.getInteger("Orientation");
      this.bodyOffsetY = compound.getFloat("PositionOffsetY");
      this.bodyOffsetZ = compound.getFloat("PositionOffsetZ");
      this.bodyOffsetX = compound.getFloat("PositionOffsetX");
      this.walkingRange = compound.getInteger("WalkingRange");
      this.setWalkingSpeed(compound.getInteger("MoveSpeed"));
      this.setMovingPath(NBTTags.getIntegerArraySet(compound.getTagList("MovingPathNew", 10)));
      this.movingPos = compound.getInteger("MovingPos");
      this.movingPattern = compound.getInteger("MovingPatern");
      this.startPos = compound.getIntArray("StartPosNew");
      if(this.startPos == null || this.startPos.length != 3) {
         this.startPos = new int[]{MathHelper.floor_double(this.npc.posX), MathHelper.floor_double(this.npc.posY), MathHelper.floor_double(this.npc.posZ)};
      }

   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setBoolean("CanSwim", this.canSwim);
      compound.setBoolean("ReactsToFire", this.reactsToFire);
      compound.setBoolean("AvoidsWater", this.avoidsWater);
      compound.setBoolean("AvoidsSun", this.avoidsSun);
      compound.setBoolean("ReturnToStart", this.returnToStart);
      compound.setInteger("OnAttack", this.onAttack);
      compound.setInteger("DoorInteract", this.doorInteract);
      compound.setInteger("FindShelter", this.findShelter);
      compound.setBoolean("DirectLOS", this.directLOS);
      compound.setBoolean("CanLeap", this.canLeap);
      compound.setBoolean("CanSprint", this.canSprint);
      compound.setInteger("FireIndirect", this.canFireIndirect);
      compound.setInteger("RangeAndMelee", this.useRangeMelee);
      compound.setInteger("DistanceToMelee", this.distanceToMelee);
      compound.setInteger("TacticalRadius", this.tacticalRadius);
      compound.setBoolean("MovingPause", this.movingPause);
      compound.setBoolean("IgnoreCobweb", this.ignoreCobweb);
      compound.setBoolean("npcInteracting", this.npcInteracting);
      compound.setBoolean("stopAndInteract", this.stopAndInteract);
      compound.setInteger("MoveState", this.animationType.ordinal());
      compound.setInteger("StandingState", this.standingType.ordinal());
      compound.setInteger("MovingState", this.movingType.ordinal());
      compound.setInteger("TacticalVariant", this.tacticalVariant.ordinal());
      compound.setInteger("Orientation", this.orientation);
      compound.setFloat("PositionOffsetX", this.bodyOffsetX);
      compound.setFloat("PositionOffsetY", this.bodyOffsetY);
      compound.setFloat("PositionOffsetZ", this.bodyOffsetZ);
      compound.setInteger("WalkingRange", this.walkingRange);
      compound.setInteger("MoveSpeed", this.moveSpeed);
      compound.setTag("MovingPathNew", NBTTags.nbtIntegerArraySet(this.movingPath));
      compound.setInteger("MovingPos", this.movingPos);
      compound.setInteger("MovingPatern", this.movingPattern);
      this.npc.setAvoidWater(this.avoidsWater);
      compound.setIntArray("StartPosNew", this.npc.getStartPos());
      return compound;
   }

   public List getMovingPath() {
      if(this.movingPath.isEmpty() && this.startPos != null) {
         this.movingPath.add(this.startPos);
      }

      return this.movingPath;
   }

   public void setMovingPath(List list) {
      this.movingPath = list;
      if(!this.movingPath.isEmpty()) {
         this.startPos = (int[])this.movingPath.get(0);
      }

   }

   public int[] getCurrentMovingPath() {
      List list = this.getMovingPath();
      if(list.size() == 1) {
         this.movingPos = 0;
      } else if(this.movingPos >= list.size()) {
         if(this.movingPattern == 0) {
            this.movingPos = 0;
         } else {
            int size = list.size() * 2 - 2;
            if(this.movingPos >= size) {
               this.movingPos = 0;
            } else if(this.movingPos >= list.size()) {
               return (int[])list.get(list.size() - this.movingPos % list.size() - 2);
            }
         }
      }

      return (int[])list.get(this.movingPos);
   }

   public void incrementMovingPath() {
      List list = this.getMovingPath();
      if(list.size() == 1) {
         this.movingPos = 0;
      } else if(this.movingPattern == 0) {
         ++this.movingPos;
         this.movingPos %= list.size();
      } else if(this.movingPattern == 1) {
         ++this.movingPos;
         int size = list.size() * 2 - 2;
         this.movingPos %= size;
      }

   }

   public void decreaseMovingPath() {
      List list = this.getMovingPath();
      if(list.size() == 1) {
         this.movingPos = 0;
      } else if(this.movingPattern == 0) {
         --this.movingPos;
         if(this.movingPos < 0) {
            this.movingPos += list.size();
         }
      } else if(this.movingPattern == 1) {
         --this.movingPos;
         if(this.movingPos < 0) {
            int size = list.size() * 2 - 2;
            this.movingPos += size;
         }
      }

   }

   public double getDistanceSqToPathPoint() {
      int[] pos = this.getCurrentMovingPath();
      return this.npc.getDistanceSq((double)pos[0] + 0.5D, (double)pos[1], (double)pos[2] + 0.5D);
   }

   public void setWalkingSpeed(int speed) {
      this.moveSpeed = speed;
      this.npc.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double)this.npc.getSpeed());
   }

   public int getWalkingSpeed() {
      return this.moveSpeed;
   }
}
