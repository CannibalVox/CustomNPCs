package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;

public class ItemTeleporter extends Item {

   public ItemTeleporter() {
      super.maxStackSize = 1;
      this.setCreativeTab(CustomItems.tab);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(!par2World.isRemote) {
         return par1ItemStack;
      } else {
         CustomNpcs.proxy.openGui((EntityNPCInterface)null, EnumGuiType.NpcDimensions);
         return par1ItemStack;
      }
   }

   public boolean onEntitySwing(EntityLivingBase par3EntityPlayer, ItemStack stack) {
      if(par3EntityPlayer.worldObj.isRemote) {
         return false;
      } else {
         float f = 1.0F;
         float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
         float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
         double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)f;
         double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)f + 1.62D - (double)par3EntityPlayer.yOffset;
         double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)f;
         Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
         float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
         float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
         float f5 = -MathHelper.cos(-f1 * 0.017453292F);
         float f6 = MathHelper.sin(-f1 * 0.017453292F);
         float f7 = f4 * f5;
         float f8 = f3 * f5;
         double d3 = 80.0D;
         Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
         MovingObjectPosition movingobjectposition = par3EntityPlayer.worldObj.rayTraceBlocks(vec3, vec31, true);
         if(movingobjectposition == null) {
            return false;
         } else {
            Vec3 vec32 = par3EntityPlayer.getLook(f);
            boolean flag = false;
            float f9 = 1.0F;
            List list = par3EntityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand((double)f9, (double)f9, (double)f9));

            int i;
            for(i = 0; i < list.size(); ++i) {
               Entity j = (Entity)list.get(i);
               if(j.canBeCollidedWith()) {
                  float k = j.getCollisionBorderSize();
                  AxisAlignedBB axisalignedbb = j.boundingBox.expand((double)k, (double)k, (double)k);
                  if(axisalignedbb.isVecInside(vec3)) {
                     flag = true;
                  }
               }
            }

            if(flag) {
               return false;
            } else {
               if(movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
                  i = movingobjectposition.blockX;
                  int var31 = movingobjectposition.blockY;

                  int var32;
                  for(var32 = movingobjectposition.blockZ; par3EntityPlayer.worldObj.getBlock(i, var31, var32) != Blocks.air; ++var31) {
                     ;
                  }

                  par3EntityPlayer.setPositionAndUpdate((double)((float)i + 0.5F), (double)((float)var31 + 1.0F), (double)((float)var32 + 0.5F));
               }

               return true;
            }
         }
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
      super.itemIcon = Items.feather.getIconFromDamage(0);
   }

   public Item setUnlocalizedName(String name) {
      GameRegistry.registerItem(this, name);
      return super.setUnlocalizedName(name);
   }
}
