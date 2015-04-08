package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCOrcMale extends EntityNPCInterface {

   public EntityNPCOrcMale(World world) {
      super(world);
      super.scaleY = 1.0F;
      super.scaleX = super.scaleZ = 1.2F;
      super.display.texture = "customnpcs:textures/entity/orcmale/StrandedOrc.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.legs.setScale(1.2F, 1.05F);
         data.arms.setScale(1.2F, 1.05F);
         data.body.setScale(1.4F, 1.1F, 1.5F);
         data.head.setScale(1.2F, 1.1F);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
