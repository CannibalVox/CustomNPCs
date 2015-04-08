package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.items.ItemRenderInterface;
import org.lwjgl.opengl.GL11;

public class ItemNpcInterface extends Item implements ItemRenderInterface {

   public ItemNpcInterface(int par1) {
      this();
   }

   public ItemNpcInterface() {
      this.setCreativeTab(CustomItems.tab);
      CustomNpcs.proxy.registerItem(this);
   }

   public void renderSpecial() {
      GL11.glScalef(0.66F, 0.66F, 0.66F);
      GL11.glTranslatef(0.0F, 0.3F, 0.0F);
   }

   public int getItemEnchantability() {
      return super.getItemEnchantability();
   }

   public Item setUnlocalizedName(String name) {
      super.setUnlocalizedName(name);
      GameRegistry.registerItem(this, name);
      return this;
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      if(par2EntityLiving.getHealth() <= 0.0F) {
         return false;
      } else {
         par1ItemStack.damageItem(1, par3EntityLiving);
         return true;
      }
   }

   public boolean hasItem(EntityPlayer player, Item item) {
      return player.inventory.hasItem(item);
   }

   public boolean consumeItem(EntityPlayer player, Item item) {
      return player.inventory.consumeInventoryItem(item);
   }
}
