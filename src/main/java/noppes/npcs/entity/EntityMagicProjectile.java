package noppes.npcs.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import noppes.npcs.entity.EntityProjectile;

public class EntityMagicProjectile extends EntityProjectile {

   private EntityPlayer player;
   private ItemStack equiped;


   public EntityMagicProjectile(World par1World, EntityPlayer player, ItemStack item, boolean isNPC) {
      super(par1World, player, item, isNPC);
      this.player = player;
      this.equiped = player.inventory.getCurrentItem();
   }

   public void onUpdate() {
      if(this.player.inventory.getCurrentItem() != this.equiped) {
         this.setDead();
      }

      super.onUpdate();
   }

   public String getCommandSenderName() {
      return StatCollector.translateToLocal("entity.throwableitem.name");
   }
}
