package noppes.npcs.blocks.tiles;

import net.minecraft.util.AxisAlignedBB;
import noppes.npcs.blocks.tiles.TileColorable;

public class TileTallLamp extends TileColorable {

   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 2), (double)(super.zCoord + 1));
   }
}
