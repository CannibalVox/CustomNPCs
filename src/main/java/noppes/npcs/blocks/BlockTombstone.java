package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.blocks.tiles.TileBigSign;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.blocks.tiles.TileTombstone;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;

public class BlockTombstone extends BlockContainer {

   public int renderId = -1;


   public BlockTombstone() {
      super(Material.rock);
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return false;
      } else {
         ItemStack currentItem = player.inventory.getCurrentItem();
         if(currentItem != null && currentItem.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, "customnpcs.editblocks")) {
            TileBigSign tile = (TileBigSign)par1World.getTileEntity(i, j, k);
            if(tile.getBlockMetadata() >= 2) {
               return false;
            } else {
               tile.canEdit = true;
               NoppesUtilServer.sendOpenGui(player, EnumGuiType.BigSign, (EntityNPCInterface)null, i, j, k);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      l %= 4;
      TileBigSign tile = (TileBigSign)par1World.getTileEntity(par2, par3, par4);
      tile.rotation = l;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
      if(par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote && par6ItemStack.getMetadata() < 2) {
         CustomNpcs.proxy.openGui(par2, par3, par4, EnumGuiType.BigSign, (EntityPlayer)par5EntityLivingBase);
      }

   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      this.setBlockBoundsBasedOnState(world, x, y, z);
      return super.getCollisionBoundingBoxFromPool(world, x, y, z);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      if(!(tileentity instanceof TileColorable)) {
         super.setBlockBoundsBasedOnState(world, x, y, z);
      } else {
         TileColorable tile = (TileColorable)tileentity;
         if(tile.rotation % 2 == 0) {
            this.setBlockBounds(0.0F, 0.0F, 0.3F, 1.0F, 1.0F, 0.7F);
         } else {
            this.setBlockBounds(0.3F, 0.0F, 0.0F, 0.7F, 1.0F, 1.0F);
         }

      }
   }

   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return this.renderId;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {}

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int p_149691_1_, int meta) {
      return Blocks.stone.getIcon(p_149691_1_, meta);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileTombstone();
   }
}
