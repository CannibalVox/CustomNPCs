package noppes.npcs.blocks;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileBanner;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.blocks.tiles.TileSign;

public class BlockSign extends BlockRotated {

   public BlockSign() {
      super(Blocks.planks);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      l %= 4;
      TileSign tile = (TileSign)par1World.getTileEntity(par2, par3, par4);
      tile.rotation = l;
      tile.time = System.currentTimeMillis();
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
      if(par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote) {
         ((EntityPlayer)par5EntityLivingBase).addChatComponentMessage(new ChatComponentTranslation("availability.editIcon", new Object[0]));
      }

   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
      TileBanner tile = (TileBanner)world.getTileEntity(x, y, z);
      return tile.canEdit();
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
         if(tile.rotation % 2 == 1) {
            this.setBlockBounds(0.0F, 0.3F, 0.3F, 1.0F, 1.0F, 0.7F);
         } else {
            this.setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 1.0F, 1.0F);
         }

      }
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileSign();
   }
}
