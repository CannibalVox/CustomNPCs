package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class BlockCrystal extends BlockBreakable {

   public BlockCrystal() {
      super("customnpcs:npcCrystal", Material.glass, false);
      this.setLightLevel(0.8F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderBlockPass() {
      return 1;
   }

   public int damageDropped(int meta) {
      return meta;
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < 16; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
      return this.getRenderColor(world.getBlockMetadata(x, y, z));
   }

   @SideOnly(Side.CLIENT)
   public int getRenderColor(int meta) {
      return MapColor.getMapColorForBlockColored(meta).colorValue;
   }

   public MapColor getMapColor(int p_149728_1_) {
      return MapColor.getMapColorForBlockColored(p_149728_1_);
   }

   public String getUnlocalizedName() {
      return "item.npcCrystal";
   }
}
