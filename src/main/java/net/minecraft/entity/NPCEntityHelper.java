package net.minecraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

public class NPCEntityHelper {

   public static Item getDropItem(EntityLiving entity) {
      return entity.getDropItem();
   }

   public static void setRecentlyHit(EntityLivingBase entity) {
      entity.recentlyHit = 100;
   }
}
