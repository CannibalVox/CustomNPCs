package noppes.npcs.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcCrystal extends EntityNPCInterface {

   public EntityNpcCrystal(World world) {
      super(world);
      super.scaleX = 0.7F;
      super.scaleY = 0.7F;
      super.scaleZ = 0.7F;
      super.display.texture = "customnpcs:textures/entity/crystal/EnderCrystal.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.setEntityClass(EntityNpcCrystal.class);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
