package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcMonsterMale extends EntityNPCInterface {

   public EntityNpcMonsterMale(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/monstermale/ZombieSteve.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         npc.ai.animationType = EnumAnimation.HUG;
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
