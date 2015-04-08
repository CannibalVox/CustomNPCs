package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class ItemNpcWand extends Item {

   public ItemNpcWand() {
      super.maxStackSize = 1;
      this.setCreativeTab(CustomItems.tab);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(!par2World.isRemote) {
         return par1ItemStack;
      } else {
         CustomNpcsPermissions var10000 = CustomNpcsPermissions.Instance;
         if(CustomNpcsPermissions.hasPermission(par3EntityPlayer, "customnpcs.npc.gui")) {
            CustomNpcs.proxy.openGui((EntityNPCInterface)null, EnumGuiType.NpcRemote);
         }

         return par1ItemStack;
      }
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par3World.isRemote) {
         return true;
      } else {
         if(CustomNpcs.OpsOnly && !MinecraftServer.getServer().getConfigurationManager().canSendCommands(player.getGameProfile())) {
            player.addChatMessage(new ChatComponentTranslation("availability.permission", new Object[0]));
         } else {
            CustomNpcsPermissions var10000 = CustomNpcsPermissions.Instance;
            if(CustomNpcsPermissions.hasPermission(player, "customnpcs.npc.create")) {
               EntityCustomNpc npc = new EntityCustomNpc(par3World);
               npc.ai.startPos = new int[]{par4, par5, par6};
               npc.setLocationAndAngles((double)((float)par4 + 0.5F), npc.getStartYPos(), (double)((float)par6 + 0.5F), player.rotationYaw, player.rotationPitch);
               par3World.spawnEntityInWorld(npc);
               npc.setHealth(npc.getMaxHealth());
               NoppesUtilServer.sendOpenGui(player, EnumGuiType.MainMenuDisplay, npc);
            } else {
               player.addChatMessage(new ChatComponentTranslation("availability.permission", new Object[0]));
            }
         }

         return true;
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
      super.itemIcon = Items.iron_hoe.getIconFromDamage(0);
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
