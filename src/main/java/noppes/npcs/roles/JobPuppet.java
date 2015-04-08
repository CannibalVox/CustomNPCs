package noppes.npcs.roles;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.util.ValueUtil;

public class JobPuppet extends JobInterface {

   public JobPuppet.PartConfig head = new JobPuppet.PartConfig();
   public JobPuppet.PartConfig larm = new JobPuppet.PartConfig();
   public JobPuppet.PartConfig rarm = new JobPuppet.PartConfig();
   public JobPuppet.PartConfig body = new JobPuppet.PartConfig();
   public JobPuppet.PartConfig lleg = new JobPuppet.PartConfig();
   public JobPuppet.PartConfig rleg = new JobPuppet.PartConfig();
   public boolean whileStanding = true;
   public boolean whileAttacking = false;
   public boolean whileMoving = false;


   public JobPuppet(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setTag("PuppetHead", this.head.writeNBT());
      compound.setTag("PuppetLArm", this.larm.writeNBT());
      compound.setTag("PuppetRArm", this.rarm.writeNBT());
      compound.setTag("PuppetBody", this.body.writeNBT());
      compound.setTag("PuppetLLeg", this.lleg.writeNBT());
      compound.setTag("PuppetRLeg", this.rleg.writeNBT());
      compound.setBoolean("PuppetStanding", this.whileStanding);
      compound.setBoolean("PuppetAttacking", this.whileAttacking);
      compound.setBoolean("PuppetMoving", this.whileMoving);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.head.readNBT(compound.getCompoundTag("PuppetHead"));
      this.larm.readNBT(compound.getCompoundTag("PuppetLArm"));
      this.rarm.readNBT(compound.getCompoundTag("PuppetRArm"));
      this.body.readNBT(compound.getCompoundTag("PuppetBody"));
      this.lleg.readNBT(compound.getCompoundTag("PuppetLLeg"));
      this.rleg.readNBT(compound.getCompoundTag("PuppetRLeg"));
      this.whileStanding = compound.getBoolean("PuppetStanding");
      this.whileAttacking = compound.getBoolean("PuppetAttacking");
      this.whileMoving = compound.getBoolean("PuppetMoving");
   }

   public boolean aiShouldExecute() {
      return false;
   }

   public void reset() {}

   public void delete() {}

   public boolean isActive() {
      return !super.npc.isEntityAlive()?false:this.whileAttacking && super.npc.isAttacking() || this.whileMoving && super.npc.isWalking() || this.whileStanding && !super.npc.isWalking();
   }

   public static class PartConfig {

      public float rotationX = 0.0F;
      public float rotationY = 0.0F;
      public float rotationZ = 0.0F;
      public boolean disabled = false;


      public NBTTagCompound writeNBT() {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setFloat("RotationX", this.rotationX);
         compound.setFloat("RotationY", this.rotationY);
         compound.setFloat("RotationZ", this.rotationZ);
         compound.setBoolean("Disabled", this.disabled);
         return compound;
      }

      public void readNBT(NBTTagCompound compound) {
         this.rotationX = ValueUtil.correctFloat(compound.getFloat("RotationX"), -0.5F, 0.5F);
         this.rotationY = ValueUtil.correctFloat(compound.getFloat("RotationY"), -0.5F, 0.5F);
         this.rotationZ = ValueUtil.correctFloat(compound.getFloat("RotationZ"), -0.5F, 0.5F);
         this.disabled = compound.getBoolean("Disabled");
      }
   }
}
