package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCDwarfFemale extends EntityNPCInterface {

   public EntityNPCDwarfFemale(World world) {
      super(world);
      super.scaleX = super.scaleZ = 0.75F;
      super.scaleY = 0.6275F;
      super.display.texture = "customnpcs:textures/entity/dwarffemale/Simone.png";
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
         data.legs.setScale(0.8F, 1.05F);
         data.arms.setScale(0.8F, 1.05F);
         data.body.setScale(0.8F, 1.05F);
         data.head.setScale(0.8F, 0.85F);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
