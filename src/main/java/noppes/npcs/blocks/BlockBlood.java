package noppes.npcs.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;

public class BlockBlood extends Block {

   @SideOnly(Side.CLIENT)
   private IIcon blockIcon2;
   @SideOnly(Side.CLIENT)
   private IIcon blockIcon3;
   private final int renderId = RenderingRegistry.getNextAvailableRenderId();


   public BlockBlood() {
      super(Material.rock);
      this.setBlockUnbreakable();
      this.setCreativeTab(CustomItems.tabMisc);
      this.setBlockBounds(0.01F, 0.01F, 0.01F, 0.99F, 0.99F, 0.99F);
      this.setLightLevel(0.08F);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int metadata) {
      metadata += side;
      return metadata % 3 == 1?this.blockIcon2:(metadata % 3 == 2?this.blockIcon3:super.blockIcon);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
      return null;
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return AxisAlignedBB.getBoundingBox((double)par2, (double)par3, (double)par4, (double)par2, (double)par3, (double)par4);
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      super.blockIcon = par1IconRegister.registerIcon(this.getTextureName());
      this.blockIcon2 = par1IconRegister.registerIcon(this.getTextureName() + "2");
      this.blockIcon3 = par1IconRegister.registerIcon(this.getTextureName() + "3");
   }

   public boolean shouldSideBeRendered(IBlockAccess world, int par2, int par3, int par4, int par5) {
      Block block = world.getBlock(par2, par3, par4);
      return block != Blocks.air && block.renderAsNormalBlock();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderBlockPass() {
      return 1;
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack item) {
      int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw / 90.0F) + 0.5D) & 3;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
   }

   public int getRenderType() {
      return this.renderId;
   }
}
