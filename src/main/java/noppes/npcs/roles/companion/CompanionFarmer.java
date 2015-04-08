package noppes.npcs.roles.companion;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.roles.companion.CompanionJobInterface;

public class CompanionFarmer extends CompanionJobInterface {

   public boolean isStanding = false;


   public NBTTagCompound getNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setBoolean("CompanionFarmerStanding", this.isStanding);
      return compound;
   }

   public void setNBT(NBTTagCompound compound) {
      this.isStanding = compound.getBoolean("CompanionFarmerStanding");
   }

   public boolean isSelfSufficient() {
      return this.isStanding;
   }

   public void onUpdate() {}
}
