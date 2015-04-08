package noppes.npcs.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.Server;
import noppes.npcs.blocks.BlockRotated;
import noppes.npcs.blocks.tiles.TileBook;
import noppes.npcs.constants.EnumPacketClient;

public class BlockBook extends BlockRotated {

   public BlockBook() {
      super(Blocks.planks);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
   }

   public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         TileEntity tile = par1World.getTileEntity(i, j, k);
         if(!(tile instanceof TileBook)) {
            return false;
         } else {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if(currentItem != null && currentItem.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, "customnpcs.editblocks")) {
               ((TileBook)tile).book.setItem(Items.writable_book);
            }

            Server.sendData((EntityPlayerMP)player, EnumPacketClient.OPEN_BOOK, new Object[]{Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), ((TileBook)tile).book.writeToNBT(new NBTTagCompound())});
            return true;
         }
      }
   }

   public String getUnlocalizedName() {
      return "item.book";
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileBook();
   }
}
