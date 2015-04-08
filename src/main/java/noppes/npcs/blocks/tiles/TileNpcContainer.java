package noppes.npcs.blocks.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import noppes.npcs.blocks.tiles.TileColorable;

public abstract class TileNpcContainer extends TileColorable implements IInventory {

   private ItemStack[] chestContents = new ItemStack[this.getSizeInventory()];
   public String customName = "";
   public int playerUsing = 0;


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      NBTTagList nbttaglist = compound.getTagList("Items", 10);
      this.chestContents = new ItemStack[this.getSizeInventory()];
      if(compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }

      for(int i = 0; i < nbttaglist.tagCount(); ++i) {
         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
         int j = nbttagcompound1.getByte("Slot") & 255;
         if(j >= 0 && j < this.chestContents.length) {
            this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
         }
      }

   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.chestContents.length; ++i) {
         if(this.chestContents[i] != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)i);
            this.chestContents[i].writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
         }
      }

      compound.setTag("Items", nbttaglist);
      if(this.isCustomInventoryName()) {
         compound.setString("CustomName", this.customName);
      }

   }

   public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
      if(p_145842_1_ == 1) {
         this.playerUsing = p_145842_2_;
         return true;
      } else {
         return super.receiveClientEvent(p_145842_1_, p_145842_2_);
      }
   }

   public int getSizeInventory() {
      return 54;
   }

   public ItemStack getStackInSlot(int var1) {
      return this.chestContents[var1];
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.chestContents[par1] != null) {
         ItemStack itemstack;
         if(this.chestContents[par1].stackSize <= par2) {
            itemstack = this.chestContents[par1];
            this.chestContents[par1] = null;
            this.markDirty();
            return itemstack;
         } else {
            itemstack = this.chestContents[par1].splitStack(par2);
            if(this.chestContents[par1].stackSize == 0) {
               this.chestContents[par1] = null;
            }

            this.markDirty();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int par1) {
      if(this.chestContents[par1] != null) {
         ItemStack itemstack = this.chestContents[par1];
         this.chestContents[par1] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      this.chestContents[par1] = par2ItemStack;
      if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
         par2ItemStack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   public String getInventoryName() {
      return this.isCustomInventoryName()?this.customName:this.getName();
   }

   public abstract String getName();

   public boolean isCustomInventoryName() {
      return !this.customName.isEmpty();
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer player) {
      return !player.isDead && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) == this?player.getDistanceSq((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D) <= 64.0D:false;
   }

   public void openChest() {
      ++this.playerUsing;
   }

   public void closeChest() {
      --this.playerUsing;
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public void dropItems(World world, int x, int y, int z) {
      for(int i1 = 0; i1 < this.getSizeInventory(); ++i1) {
         ItemStack itemstack = this.getStackInSlot(i1);
         if(itemstack != null) {
            float f = world.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = world.rand.nextFloat() * 0.8F + 0.1F;

            EntityItem entityitem;
            for(float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
               int j1 = world.rand.nextInt(21) + 10;
               if(j1 > itemstack.stackSize) {
                  j1 = itemstack.stackSize;
               }

               itemstack.stackSize -= j1;
               entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getMetadata()));
               float f3 = 0.05F;
               entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
               entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
               entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
               if(itemstack.hasTagCompound()) {
                  entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
               }
            }
         }
      }

   }
}
