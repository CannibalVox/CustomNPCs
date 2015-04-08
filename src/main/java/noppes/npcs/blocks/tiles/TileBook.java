package noppes.npcs.blocks.tiles;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.blocks.tiles.TileColorable;

public class TileBook extends TileColorable {

   public ItemStack book;


   public TileBook() {
      this.book = new ItemStack(Items.writable_book);
   }

   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.book = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Items"));
      if(this.book == null) {
         this.book = new ItemStack(Items.writable_book);
      }

   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setTag("Items", this.book.writeToNBT(new NBTTagCompound()));
   }
}
