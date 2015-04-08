package noppes.npcs.quests;

import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class QuestInterface {

   public int questId;


   public abstract void writeEntityToNBT(NBTTagCompound var1);

   public abstract void readEntityFromNBT(NBTTagCompound var1);

   public abstract boolean isCompleted(EntityPlayer var1);

   public abstract void handleComplete(EntityPlayer var1);

   public abstract Vector getQuestLogStatus(EntityPlayer var1);
}
