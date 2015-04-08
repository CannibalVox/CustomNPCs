package noppes.npcs.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import noppes.npcs.blocks.tiles.TileColorable;

public class TileBanner extends TileColorable {

   public ItemStack icon;
   public long time = 0L;


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.icon = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("BannerIcon"));
   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      if(this.icon != null) {
         compound.setTag("BannerIcon", this.icon.writeToNBT(new NBTTagCompound()));
      }

   }

   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 2), (double)(super.zCoord + 1));
   }

   public boolean canEdit() {
      return System.currentTimeMillis() - this.time < 10000L;
   }
}
