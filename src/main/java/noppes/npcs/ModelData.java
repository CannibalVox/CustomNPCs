package noppes.npcs;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import noppes.npcs.ModelDataShared;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityNPCInterface;

public class ModelData extends ModelDataShared {

   public EntityLivingBase getEntity(EntityNPCInterface npc) {
      if(super.entityClass == null) {
         return null;
      } else {
         if(super.entity == null) {
            try {
               super.entity = (EntityLivingBase)super.entityClass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{npc.worldObj});
               super.entity.readEntityFromNBT(super.extra);
               if(super.entity instanceof EntityLiving) {
                  EntityLiving e = (EntityLiving)super.entity;
                  e.setCurrentItemOrArmor(0, npc.getHeldItem() != null?npc.getHeldItem():npc.getOffHand());
                  e.setCurrentItemOrArmor(1, npc.inventory.armorItemInSlot(3));
                  e.setCurrentItemOrArmor(2, npc.inventory.armorItemInSlot(2));
                  e.setCurrentItemOrArmor(3, npc.inventory.armorItemInSlot(1));
                  e.setCurrentItemOrArmor(4, npc.inventory.armorItemInSlot(0));
               }

               if(PixelmonHelper.isPixelmon(super.entity) && npc.worldObj.isRemote) {
                  if(super.extra.hasKey("Name")) {
                     PixelmonHelper.setName(super.entity, super.extra.getString("Name"));
                  } else {
                     PixelmonHelper.setName(super.entity, "Abra");
                  }
               }
            } catch (Exception var3) {
               ;
            }
         }

         return super.entity;
      }
   }

   public ModelData copy() {
      ModelData data = new ModelData();
      data.readFromNBT(this.writeToNBT());
      return data;
   }
}
