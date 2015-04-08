package noppes.npcs.entity.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNPCFurryFemale extends EntityNPCInterface {

   public EntityNPCFurryFemale(World world) {
      super(world);
      super.scaleX = super.scaleY = super.scaleZ = 0.9075F;
      super.display.texture = "customnpcs:textures/entity/furryfemale/WolfBlack.png";
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
