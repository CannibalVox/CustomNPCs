package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobGuard extends JobInterface {

   public boolean attacksAnimals = false;
   public boolean attackHostileMobs = true;
   public boolean attackCreepers = false;
   public List targets = new ArrayList();
   public boolean specific = false;


   public JobGuard(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setBoolean("GuardAttackAnimals", this.attacksAnimals);
      nbttagcompound.setBoolean("GuardAttackMobs", this.attackHostileMobs);
      nbttagcompound.setBoolean("GuardAttackCreepers", this.attackCreepers);
      nbttagcompound.setBoolean("GuardSpecific", this.specific);
      nbttagcompound.setTag("GuardTargets", NBTTags.nbtStringList(this.targets));
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.attacksAnimals = nbttagcompound.getBoolean("GuardAttackAnimals");
      this.attackHostileMobs = nbttagcompound.getBoolean("GuardAttackMobs");
      this.attackCreepers = nbttagcompound.getBoolean("GuardAttackCreepers");
      this.specific = nbttagcompound.getBoolean("GuardSpecific");
      this.targets = NBTTags.getStringList(nbttagcompound.getTagList("GuardTargets", 10));
   }

   public boolean isEntityApplicable(Entity entity) {
      return !(entity instanceof EntityPlayer) && !(entity instanceof EntityNPCInterface)?(this.specific && this.targets.contains("entity." + EntityList.getEntityString(entity) + ".name")?true:(!(entity instanceof EntityAnimal)?(entity instanceof EntityCreeper?this.attackCreepers:(!(entity instanceof IMob) && !(entity instanceof EntityDragon)?false:this.attackHostileMobs)):this.attacksAnimals && (!(entity instanceof EntityTameable) || ((EntityTameable)entity).getOwner() == null))):false;
   }
}
