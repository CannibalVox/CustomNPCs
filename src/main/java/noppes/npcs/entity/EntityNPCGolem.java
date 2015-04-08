package noppes.npcs.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCGolem extends EntityNPCInterface {

   public EntityNPCGolem(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/golem/Iron Golem.png";
      super.width = 1.4F;
      super.height = 2.5F;
   }

   public void updateHitbox() {
      super.currentAnimation = EnumAnimation.values()[super.dataWatcher.getWatchableObjectInt(14)];
      if(super.currentAnimation == EnumAnimation.LYING) {
         super.width = super.height = 0.5F;
      } else if(super.currentAnimation == EnumAnimation.SITTING) {
         super.width = 1.4F;
         super.height = 2.0F;
      } else {
         super.width = 1.4F;
         super.height = 2.5F;
      }

   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.setEntityClass(EntityNPCGolem.class);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
