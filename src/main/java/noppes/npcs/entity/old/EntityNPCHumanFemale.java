package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCHumanFemale extends EntityNPCInterface {

   public EntityNPCHumanFemale(World world) {
      super(world);
      super.scaleX = super.scaleY = super.scaleZ = 0.9075F;
      super.display.texture = "customnpcs:textures/entity/humanfemale/Stephanie.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.breasts = 2;
         data.head.setScale(0.95F, 0.95F);
         data.legs.setScale(0.92F, 0.92F);
         data.arms.setScale(0.8F, 0.92F);
         data.body.setScale(0.92F, 0.92F);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
