package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCDwarfMale extends EntityNPCInterface {

   public EntityNPCDwarfMale(World world) {
      super(world);
      super.scaleX = super.scaleZ = 0.85F;
      super.scaleY = 0.6875F;
      super.display.texture = "customnpcs:textures/entity/dwarfmale/Simon.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.legs.setScale(1.1F, 0.7F, 0.9F);
         data.arms.setScale(0.9F, 0.7F);
         data.body.setScale(1.2F, 0.7F, 1.5F);
         data.head.setScale(0.85F, 0.85F);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
