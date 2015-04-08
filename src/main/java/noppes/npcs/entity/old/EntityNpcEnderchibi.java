package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcEnderchibi extends EntityNPCInterface {

   public EntityNpcEnderchibi(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/enderchibi/MrEnderchibi.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.legs.setScale(0.65F, 0.75F);
         data.arms.setScale(0.5F, 1.45F);
         ModelPartData part = data.getOrCreatePart("particles");
         part.playerTexture = true;
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
