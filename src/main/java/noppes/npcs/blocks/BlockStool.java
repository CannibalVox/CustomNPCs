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
import net.minecraft.world.World;
import noppes.npcs.blocks.BlockChair;
import noppes.npcs.blocks.tiles.TileColorable;
import noppes.npcs.blocks.tiles.TileStool;

public class BlockStool extends BlockContainer {

   public int renderId = -1;


   public BlockStool() {
      super(Material.wood);
      this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.6F, 0.9F);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int x, int y, int z) {
      return AxisAlignedBB.getBoundingBox((double)((float)x + 0.1F), (double)y, (double)((float)z + 0.1F), (double)((float)x + 0.9F), (double)((float)y + 0.5F), (double)((float)z + 0.9F));
   }

   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
      par3List.add(new ItemStack(par1, 1, 3));
      par3List.add(new ItemStack(par1, 1, 4));
      par3List.add(new ItemStack(par1, 1, 5));
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      return BlockChair.MountBlock(world, x, y, z, player);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      l %= 4;
      TileColorable tile = (TileColorable)par1World.getTileEntity(par2, par3, par4);
      tile.rotation = l;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
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
      return new TileStool();
   }
}
