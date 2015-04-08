package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;

public class BlockBigSign extends BlockContainer {

   public int renderId = -1;


   public BlockBigSign() {
      super(Material.wood);
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
      return null;
   }

   public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return false;
      } else {
         ItemStack currentItem = player.inventory.getCurrentItem();
         if(currentItem != null && currentItem.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, "customnpcs.editblocks")) {
            TileBigSign tile = (TileBigSign)par1World.getTileEntity(i, j, k);
            tile.canEdit = true;
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.BigSign, (EntityNPCInterface)null, i, j, k);
            return true;
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
      if(par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote) {
         CustomNpcs.proxy.openGui(par2, par3, par4, EnumGuiType.BigSign, (EntityPlayer)par5EntityLivingBase);
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      if(!(tileentity instanceof TileColorable)) {
         super.setBlockBoundsBasedOnState(world, x, y, z);
      } else {
         TileColorable tile = (TileColorable)tileentity;
         int meta = tile.getBlockMetadata();
         float xStart = 0.0F;
         float zStart = 0.0F;
         float xEnd = 1.0F;
         float zEnd = 1.0F;
         if(tile.rotation == 0) {
            zStart = 0.87F;
         } else if(tile.rotation == 2) {
            zEnd = 0.13F;
         } else if(tile.rotation == 3) {
            xStart = 0.87F;
         } else if(tile.rotation == 1) {
            xEnd = 0.13F;
         }

         this.setBlockBounds(xStart, 0.0F, zStart, xEnd, 1.0F, zEnd);
      }
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
      return Blocks.planks.getIcon(p_149691_1_, meta);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileBigSign();
   }
}
