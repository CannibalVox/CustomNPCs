package noppes.npcs.entity.old;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcSkeleton extends EntityNPCInterface {

   public EntityNpcSkeleton(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/skeleton/Skeleton.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.setEntityClass(EntitySkeleton.class);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
