package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCElfMale extends EntityNPCInterface {

   public EntityNPCElfMale(World world) {
      super(world);
      super.scaleX = 0.85F;
      super.scaleY = 1.07F;
      super.scaleZ = 0.85F;
      super.display.texture = "customnpcs:textures/entity/elfmale/ElfMale.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         data.legs.setScale(0.85F, 1.15F);
         data.arms.setScale(0.85F, 1.15F);
         data.body.setScale(0.85F, 1.15F);
         data.head.setScale(0.85F, 0.95F);
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
