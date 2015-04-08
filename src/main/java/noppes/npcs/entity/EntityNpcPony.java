package noppes.npcs.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcPony extends EntityNPCInterface {

   public boolean isPegasus = false;
   public boolean isUnicorn = false;
   public boolean isFlying = false;
   public ResourceLocation checked = null;


   public EntityNpcPony(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/ponies/MineLP Derpy Hooves.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.setEntityClass(EntityNpcPony.class);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
