package noppes.npcs.blocks.tiles;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.blocks.tiles.TileColorable;

public class TileCouchWool extends TileColorable {

   public boolean hasLeft = false;
   public boolean hasRight = false;
   public boolean hasCornerLeft = false;
   public boolean hasCornerRight = false;


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.hasLeft = compound.getBoolean("CouchLeft");
      this.hasRight = compound.getBoolean("CouchRight");
      this.hasCornerLeft = compound.getBoolean("CouchCornerLeft");
      this.hasCornerRight = compound.getBoolean("CouchCornerRight");
   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setBoolean("CouchLeft", this.hasLeft);
      compound.setBoolean("CouchRight", this.hasRight);
      compound.setBoolean("CouchCornerLeft", this.hasCornerLeft);
      compound.setBoolean("CouchCornerRight", this.hasCornerRight);
   }
}
