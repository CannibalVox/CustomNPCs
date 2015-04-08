package noppes.npcs.blocks;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.blocks.tiles.TileShelf;

public class BlockShelf extends BlockRotated {

   public BlockShelf() {
      super(Blocks.planks);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int x, int y, int z) {
      this.setBlockBoundsBasedOnState(p_149668_1_, x, y, z);
      return AxisAlignedBB.getBoundingBox((double)x + super.minX, (double)((float)y + 0.9F), (double)z + super.minZ, (double)x + super.maxX, (double)(y + 1), (double)z + super.maxZ);
   }

   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
      par3List.add(new ItemStack(par1, 1, 3));
      par3List.add(new ItemStack(par1, 1, 4));
      par3List.add(new ItemStack(par1, 1, 5));
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      if(!(tileentity instanceof TileColorable)) {
         super.setBlockBoundsBasedOnState(world, x, y, z);
      } else {
         TileColorable tile = (TileColorable)tileentity;
         float xStart = 0.0F;
         float zStart = 0.0F;
         float xEnd = 1.0F;
         float zEnd = 1.0F;
         if(tile.rotation == 0) {
            zStart = 0.3F;
         } else if(tile.rotation == 2) {
            zEnd = 0.7F;
         } else if(tile.rotation == 3) {
            xStart = 0.3F;
         } else if(tile.rotation == 1) {
            xEnd = 0.7F;
         }

         this.setBlockBounds(xStart, 0.44F, zStart, xEnd, 1.0F, zEnd);
      }
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileShelf();
   }
}
