package noppes.npcs.entity.old;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.old.EntityNpcEnderchibi;

public class EntityNPCEnderman extends EntityNpcEnderchibi {

   public EntityNPCEnderman(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/enderman/enderman.png";
      super.display.glowTexture = "customnpcs:textures/overlays/ender_eyes.png";
      super.width = 0.6F;
      super.height = 2.9F;
   }

   public void updateHitbox() {
      if(super.currentAnimation == EnumAnimation.LYING) {
         super.width = super.height = 0.2F;
      } else if(super.currentAnimation == EnumAnimation.SITTING) {
         super.width = 0.6F;
         super.height = 2.3F;
      } else {
         super.width = 0.6F;
         super.height = 2.9F;
      }

      super.width = super.width / 5.0F * (float)super.display.modelSize;
      super.height = super.height / 5.0F * (float)super.display.modelSize;
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.setEntityClass(EntityEnderman.class);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
