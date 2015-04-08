package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.util.ValueUtil;

public class ModelPartConfig {

   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   public float transX = 0.0F;
   public float transY = 0.0F;
   public float transZ = 0.0F;


   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setFloat("ScaleX", this.scaleX);
      compound.setFloat("ScaleY", this.scaleY);
      compound.setFloat("ScaleZ", this.scaleZ);
      compound.setFloat("TransX", this.transX);
      compound.setFloat("TransY", this.transY);
      compound.setFloat("TransZ", this.transZ);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.scaleX = ValueUtil.correctFloat(compound.getFloat("ScaleX"), 0.5F, 1.5F);
      this.scaleY = ValueUtil.correctFloat(compound.getFloat("ScaleY"), 0.5F, 1.5F);
      this.scaleZ = ValueUtil.correctFloat(compound.getFloat("ScaleZ"), 0.5F, 1.5F);
      this.transX = compound.getFloat("TransX");
      this.transY = compound.getFloat("TransY");
      this.transZ = compound.getFloat("TransZ");
   }

   public String toString() {
      return "ScaleX: " + this.scaleX + " - ScaleY: " + this.scaleY + " - ScaleZ: " + this.scaleZ;
   }

   public void setScale(float x, float y, float z) {
      this.scaleX = x;
      this.scaleY = y;
      this.scaleZ = z;
   }

   public void setScale(float x, float y) {
      this.scaleZ = this.scaleX = x;
      this.scaleY = y;
   }
}
