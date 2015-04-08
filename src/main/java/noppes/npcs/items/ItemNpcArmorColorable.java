package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import noppes.npcs.CustomItems;

public class ItemNpcArmorColorable extends ItemArmor {

   private String texture;


   public ItemNpcArmorColorable(int par1, ArmorMaterial par2EnumArmorMaterial, int par4, String texture) {
      super(par2EnumArmorMaterial, 0, par4);
      this.texture = texture;
      this.setCreativeTab(CustomItems.tabArmor);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
      return type != null?"customnpcs:textures/gui/invisible.png":(super.armorType == 2?"customnpcs:textures/armor/" + this.texture + "_2.png":"customnpcs:textures/armor/" + this.texture + "_1.png");
   }

   public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
      int j = this.getColor(par1ItemStack);
      return j < 0?16777215:j;
   }

   public int getColor(ItemStack par1ItemStack) {
      NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
      if(nbttagcompound == null) {
         return 10511680;
      } else {
         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
         return nbttagcompound1 == null?10511680:(nbttagcompound1.hasKey("color", 3)?nbttagcompound1.getInteger("color"):10511680);
      }
   }

   public void removeColor(ItemStack par1ItemStack) {
      NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
      if(nbttagcompound != null) {
         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
         if(nbttagcompound1.hasKey("color")) {
            nbttagcompound1.removeTag("color");
         }
      }

   }

   public void func_82813_b(ItemStack par1ItemStack, int par2) {
      NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
      if(nbttagcompound == null) {
         nbttagcompound = new NBTTagCompound();
         par1ItemStack.setTagCompound(nbttagcompound);
      }

      NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
      if(!nbttagcompound.hasKey("display", 10)) {
         nbttagcompound.setTag("display", nbttagcompound1);
      }

      nbttagcompound1.setInteger("color", par2);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
      return this.getIconFromDamage(par1);
   }

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      super.itemIcon = par1IconRegister.registerIcon(this.getIconString());
   }

   public boolean hasColor(ItemStack par1ItemStack) {
      return !par1ItemStack.hasTagCompound()?false:(!par1ItemStack.getTagCompound().hasKey("display", 10)?false:par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color", 3));
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
