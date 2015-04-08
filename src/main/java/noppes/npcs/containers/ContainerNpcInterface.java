package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;

public class ContainerNpcInterface extends Container {

   private int posX;
   private int posZ;


   public ContainerNpcInterface(EntityPlayer player) {
      this.posX = MathHelper.floor_double(player.posX);
      this.posZ = MathHelper.floor_double(player.posZ);
      player.motionX = 0.0D;
      player.motionZ = 0.0D;
   }

   public boolean canInteractWith(EntityPlayer player) {
      return !player.isDead && this.posX == MathHelper.floor_double(player.posX) && this.posZ == MathHelper.floor_double(player.posZ);
   }
}
