package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;

public interface ICompatibilty {

   int getVersion();

   void setVersion(int var1);

   NBTTagCompound writeToNBT(NBTTagCompound var1);
}
