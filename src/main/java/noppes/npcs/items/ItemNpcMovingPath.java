package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;

public class ItemNpcMovingPath extends Item {

   public ItemNpcMovingPath() {
      super.maxStackSize = 1;
      this.setCreativeTab(CustomItems.tab);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(!par2World.isRemote) {
         CustomNpcsPermissions var10000 = CustomNpcsPermissions.Instance;
         if(CustomNpcsPermissions.hasPermission(par3EntityPlayer, "customnpcs.mounter")) {
            EntityNPCInterface npc = this.getNpc(par1ItemStack, par2World);
            if(npc != null) {
               NoppesUtilServer.sendOpenGui(par3EntityPlayer, EnumGuiType.MovingPath, npc);
            }

            return par1ItemStack;
         }
      }

      return par1ItemStack;
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World par3World, int x, int y, int z, int par7, float par8, float par9, float par10) {
      if(!par3World.isRemote) {
         CustomNpcsPermissions var10000 = CustomNpcsPermissions.Instance;
         if(CustomNpcsPermissions.hasPermission(player, "customnpcs.mounter")) {
            EntityNPCInterface npc = this.getNpc(par1ItemStack, par3World);
            if(npc == null) {
               return true;
            }

            List list = npc.ai.getMovingPath();
            int[] pos = (int[])list.get(list.size() - 1);
            list.add(new int[]{x, y, z});
            double d3 = (double)(x - pos[0]);
            double d4 = (double)(y - pos[1]);
            double d5 = (double)(z - pos[2]);
            double distance = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
            player.addChatMessage(new ChatComponentText("Added point x:" + x + " y:" + y + " z:" + z + " to npc " + npc.getCommandSenderName()));
            if(distance > (double)CustomNpcs.NpcNavRange) {
               player.addChatMessage(new ChatComponentText("Warning: point is too far away from previous point. Max block walk distance = " + CustomNpcs.NpcNavRange));
            }

            return true;
         }
      }

      return false;
   }

   private EntityNPCInterface getNpc(ItemStack item, World world) {
      if(!world.isRemote && item.stackTagCompound != null) {
         Entity entity = world.getEntityByID(item.stackTagCompound.getInteger("NPCID"));
         return entity != null && entity instanceof EntityNPCInterface?(EntityNPCInterface)entity:null;
      } else {
         return null;
      }
   }

   public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
      return 9127187;
   }

   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      super.itemIcon = Items.iron_sword.getIconFromDamage(0);
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
