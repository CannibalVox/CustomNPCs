package noppes.npcs.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcSlime extends EntityNPCInterface {

   public EntityNpcSlime(World world) {
      super(world);
      super.scaleX = 2.0F;
      super.scaleY = 2.0F;
      super.scaleZ = 2.0F;
      super.display.texture = "customnpcs:textures/entity/slime/Slime.png";
      super.width = 0.8F;
      super.height = 0.8F;
   }

   public void updateHitbox() {
      super.width = 0.8F;
      super.height = 0.8F;
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.setEntityClass(EntityNpcSlime.class);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
