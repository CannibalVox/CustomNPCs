package noppes.npcs.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemNpcBlock extends ItemBlock {

   public String[] names;


   public ItemNpcBlock(Block block) {
      super(block);
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return this.names != null && par1ItemStack.getMetadata() < this.names.length?this.names[par1ItemStack.getMetadata()]:super.blockInstance.getUnlocalizedName();
   }
}
