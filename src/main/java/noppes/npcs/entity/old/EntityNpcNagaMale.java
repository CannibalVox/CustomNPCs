package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcNagaMale extends EntityNPCInterface {

   public EntityNpcNagaMale(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/nagamale/Cobra.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         ModelPartData legs = data.legParts;
         legs.playerTexture = true;
         legs.type = 1;
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
