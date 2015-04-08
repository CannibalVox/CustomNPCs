package noppes.npcs.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import noppes.npcs.items.ItemNpcWeaponInterface;
import org.lwjgl.opengl.GL11;

public class ItemGunChainsaw extends ItemNpcWeaponInterface {

   public ItemGunChainsaw(int par1, ToolMaterial tool) {
      super(par1, tool);
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      if(par2EntityLiving.getHealth() <= 0.0F) {
         return false;
      } else {
         double x = par2EntityLiving.posX;
         double y = par2EntityLiving.posY + (double)(par2EntityLiving.height / 2.0F);
         double z = par2EntityLiving.posZ;
         par3EntityLiving.worldObj.playSoundEffect(x, y, z, "random.explode", 0.8F, (1.0F + (par3EntityLiving.worldObj.rand.nextFloat() - par3EntityLiving.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
         par3EntityLiving.worldObj.spawnParticle("largeexplode", x, y, z, 0.0D, 0.0D, 0.0D);
         return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
      }
   }

   public void renderSpecial() {
      super.renderSpecial();
      GL11.glTranslatef(-0.1F, 0.0F, -0.16F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-16.0F, 0.0F, 0.0F, 1.0F);
   }
}
