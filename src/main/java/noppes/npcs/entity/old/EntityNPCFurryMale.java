package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCFurryMale extends EntityNPCInterface {

   public EntityNPCFurryMale(World world) {
      super(world);
      super.display.texture = "customnpcs:textures/entity/furrymale/WolfGrey.png";
   }

   public void onUpdate() {
      super.isDead = true;
      if(!super.worldObj.isRemote) {
         NBTTagCompound compound = new NBTTagCompound();
         this.writeToNBT(compound);
         EntityCustomNpc npc = new EntityCustomNpc(super.worldObj);
         npc.readFromNBT(compound);
         ModelData data = npc.modelData;
         ModelPartData hair = data.getOrCreatePart("ears");
         hair.playerTexture = true;
         ModelPartData snout = data.getOrCreatePart("snout");
         snout.playerTexture = true;
         snout.type = 1;
         ModelPartData tail = data.getOrCreatePart("tail");
         tail.playerTexture = true;
         super.worldObj.spawnEntityInWorld(npc);
      }

      super.onUpdate();
   }
}
