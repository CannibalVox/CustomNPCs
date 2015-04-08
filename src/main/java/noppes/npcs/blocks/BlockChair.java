package noppes.npcs.blocks;

import java.util.Iterator;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileChair;
import noppes.npcs.entity.EntityChairMount;
import noppes.npcs.entity.EntityCustomNpc;

public class BlockChair extends BlockRotated {

   public BlockChair() {
      super(Blocks.planks);
      this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
      par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
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

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileChair();
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      return MountBlock(world, x, y, z, player);
   }

   public static boolean MountBlock(World world, int x, int y, int z, EntityPlayer player) {
      if(world.isRemote) {
         return true;
      } else {
         List list = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)));
         Iterator mount = list.iterator();

         Entity entity;
         do {
            if(!mount.hasNext()) {
               EntityChairMount mount1 = new EntityChairMount(world);
               mount1.setPosition((double)((float)x + 0.5F), (double)y, (double)z + 0.5D);
               player.mountEntity(mount1);
               world.spawnEntityInWorld(mount1);
               player.mountEntity(mount1);
               return true;
            }

            entity = (Entity)mount.next();
         } while(!(entity instanceof EntityChairMount) && !(entity instanceof EntityCustomNpc));

         return false;
      }
   }
}
