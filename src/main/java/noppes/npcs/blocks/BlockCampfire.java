package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.blocks.BlockLightable;
import noppes.npcs.blocks.tiles.TileCampfire;
import org.lwjgl.opengl.GL11;

public class BlockCampfire extends BlockLightable {

   public BlockCampfire(boolean lit) {
      super(Blocks.cobblestone, lit);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileCampfire();
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
      ItemStack item = player.inventory.getCurrentItem();
      if(item == null) {
         return true;
      } else {
         world.getBlockMetadata(x, y, z);
         if((item.getItem() == Items.flint || item.getItem() == Items.flint_and_steel) && this.unlitBlock() == this) {
            if(world.rand.nextInt(3) == 0 && !world.isRemote) {
               super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
            }

            CustomNpcs.proxy.spawnParticle("largesmoke", (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), 0.0D, 0.0D, 0.0D, 2.0F);
            if(item.getItem() == Items.flint) {
               NoppesUtilServer.consumeItemStack(1, player);
            } else {
               item.damageItem(1, player);
            }

            return true;
         } else {
            if(item.getItem() == Item.getItemFromBlock(Blocks.sand) && this.litBlock() == this) {
               super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
            }

            return true;
         }
      }
   }

   public int maxRotation() {
      return 8;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World world, int x, int y, int z, Random random) {
      int meta = world.getBlockMetadata(x, y, z);
      if(meta != 1) {
         if(random.nextInt(36) == 0) {
            world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "fire.fire", 1.0F + random.nextFloat(), 0.3F + random.nextFloat() * 0.7F, false);
         }

         TileCampfire tile = (TileCampfire)world.getTileEntity(x, y, z);
         float xOffset = 0.5F;
         float yOffset = 0.7F;
         float zOffset = 0.5F;
         double d0 = (double)((float)x + xOffset);
         double d1 = (double)((float)y + yOffset);
         double d2 = (double)((float)z + zOffset);
         GL11.glPushMatrix();
         CustomNpcs.proxy.spawnParticle("largesmoke", d0, d1, d2, 0.0D, 0.0D, 0.0D, 2.0F);
         CustomNpcs.proxy.spawnParticle("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D, 4.0F);
         GL11.glPopMatrix();
      }
   }

   public int getLightValue(IBlockAccess world, int x, int y, int z) {
      int meta = world.getBlockMetadata(x, y, z);
      return meta == 0?14:0;
   }

   public Block unlitBlock() {
      return CustomItems.campfire_unlit;
   }

   public Block litBlock() {
      return CustomItems.campfire;
   }
}
