package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.blocks.BlockTrigger;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.blocks.tiles.TileNpcContainer;
import noppes.npcs.blocks.tiles.TilePedestal;

public class BlockPedestal extends BlockTrigger {

   public BlockPedestal() {
      super(Blocks.stone);
   }

   public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
      if(par1World.isRemote) {
         return true;
      } else {
         TilePedestal tile = (TilePedestal)par1World.getTileEntity(i, j, k);
         ItemStack item = player.getCurrentEquippedItem();
         ItemStack weapon = tile.getStackInSlot(0);
         if(item == null && weapon != null) {
            tile.setInventorySlotContents(0, (ItemStack)null);
            player.inventory.setInventorySlotContents(player.inventory.currentItem, weapon);
            par1World.markBlockForUpdate(i, j, k);
            this.updateSurrounding(par1World, i, j, k);
         } else {
            if(item == null || item.getItem() == null || !(item.getItem() instanceof ItemSword)) {
               return true;
            }

            if(item != null && weapon == null) {
               tile.setInventorySlotContents(0, item);
               player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
               par1World.markBlockForUpdate(i, j, k);
               this.updateSurrounding(par1World, i, j, k);
            }
         }

         return true;
      }
   }

   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
      par3List.add(new ItemStack(par1, 1, 3));
      par3List.add(new ItemStack(par1, 1, 4));
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      if(!(tileentity instanceof TileColorable)) {
         super.setBlockBoundsBasedOnState(world, x, y, z);
      } else {
         TileColorable tile = (TileColorable)tileentity;
         if(tile.rotation % 2 == 0) {
            this.setBlockBounds(0.0F, 0.0F, 0.2F, 1.0F, 0.5F, 0.8F);
         } else {
            this.setBlockBounds(0.2F, 0.0F, 0.0F, 0.8F, 0.5F, 1.0F);
         }

      }
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int p_149691_1_, int meta) {
      meta %= 7;
      return meta == 1?Blocks.stone.getIcon(p_149691_1_, 0):(meta == 2?Blocks.iron_block.getIcon(p_149691_1_, 0):(meta == 3?Blocks.gold_block.getIcon(p_149691_1_, 0):(meta == 4?Blocks.diamond_block.getIcon(p_149691_1_, 0):Blocks.planks.getIcon(p_149691_1_, 0))));
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TilePedestal();
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
      TileNpcContainer tile = (TileNpcContainer)world.getTileEntity(x, y, z);
      if(tile != null) {
         tile.dropItems(world, x, y, z);
         world.updateNeighborsAboutBlockChange(x, y, z, block);
         super.breakBlock(world, x, y, z, block, p_149749_6_);
      }
   }
}
