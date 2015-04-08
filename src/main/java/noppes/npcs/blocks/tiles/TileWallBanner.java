package noppes.npcs.blocks.tiles;

import net.minecraft.util.AxisAlignedBB;
import noppes.npcs.blocks.tiles.TileBanner;

public class TileWallBanner extends TileBanner {

   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)(super.yCoord - 1), (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1));
   }
}
