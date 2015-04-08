package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.blocks.tiles.TileWallBanner;

public class BlockWallBanner extends BlockContainer {

   public int renderId = -1;


   public BlockWallBanner() {
      super(Material.rock);
   }

   public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      ItemStack item = player.inventory.getCurrentItem();
      if(item == null) {
         return false;
      } else {
         TileWallBanner tile = (TileWallBanner)par1World.getTileEntity(i, j, k);
         if(tile.canEdit()) {
            return true;
         } else if(item.getItem() != Items.dye) {
            return false;
         } else {
            int color = BlockColored.func_150031_c(item.getMetadata());
            if(tile.color != color) {
               NoppesUtilServer.consumeItemStack(1, player);
               tile.color = color;
               par1World.markBlockForUpdate(i, j, k);
            }

            return true;
         }
      }
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      l %= 4;
      TileWallBanner tile = (TileWallBanner)par1World.getTileEntity(par2, par3, par4);
      tile.rotation = l;
      tile.color = 15 - par6ItemStack.getMetadata();
      tile.time = System.currentTimeMillis();
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
      if(par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote) {
         ((EntityPlayer)par5EntityLivingBase).addChatComponentMessage(new ChatComponentTranslation("availability.editIcon", new Object[0]));
      }

   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
      return null;
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
      meta %= 7;
      return meta == 1?Blocks.stone.getIcon(p_149691_1_, 0):(meta == 2?Blocks.iron_block.getIcon(p_149691_1_, 0):(meta == 3?Blocks.gold_block.getIcon(p_149691_1_, 0):(meta == 4?Blocks.diamond_block.getIcon(p_149691_1_, 0):Blocks.planks.getIcon(p_149691_1_, 0))));
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileWallBanner();
   }
}
