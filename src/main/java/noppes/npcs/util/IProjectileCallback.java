package noppes.npcs.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import noppes.npcs.entity.EntityProjectile;

public interface IProjectileCallback {

   boolean onImpact(EntityProjectile var1, EntityLivingBase var2, ItemStack var3);
}
