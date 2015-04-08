package noppes.npcs.items;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.items.MoneyBagContents;

public class ItemMoneyBag extends Item {

   public ItemMoneyBag(int i) {
      super.maxStackSize = 1;
      this.setCreativeTab(CustomItems.tab);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(par2World.isRemote) {
         return par1ItemStack;
      } else {
         if(par1ItemStack.stackTagCompound == null) {
            par1ItemStack.stackTagCompound = new NBTTagCompound();
         }

         new MoneyBagContents(par3EntityPlayer);
         NoppesUtil.openGUI(par3EntityPlayer, new GuiScreen());
         return par1ItemStack;
      }
   }
}
