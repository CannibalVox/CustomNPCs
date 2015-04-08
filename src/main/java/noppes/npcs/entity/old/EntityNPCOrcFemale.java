package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCOrcFemale extends EntityNPCInterface {

   public EntityNPCOrcFemale(World world) {
      super(world);
      super.scaleX = super.scaleY = super.scaleZ = 0.9375F;
      super.display.texture = "customnpcs:textures/entity/orcfemale/StrandedFemaleOrc.png";
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
         data.legs.setScale(0.9F, 0.65F);
         data.arms.setScale(0.9F, 0.65F);
         data.body.setScale(1.0F, 0.65F, 1.1F);
         data.head.setScale(0.85F, 0.85F);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
