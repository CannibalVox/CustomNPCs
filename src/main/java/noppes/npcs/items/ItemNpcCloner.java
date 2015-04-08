package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumGuiType;

public class ItemNpcCloner extends Item {

   public ItemNpcCloner() {
      super.maxStackSize = 1;
      this.setCreativeTab(CustomItems.tab);
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par3World.isRemote) {
         CustomNpcs.proxy.openGui(par4, par5, par6, EnumGuiType.MobSpawner, par2EntityPlayer);
      }

      return true;
   }

   public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
      return 9127187;
   }

   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      super.itemIcon = Items.iron_axe.getIconFromDamage(0);
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
