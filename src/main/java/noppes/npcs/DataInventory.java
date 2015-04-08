package noppes.npcs;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.entity.EntityNPCInterface;

public class DataInventory implements IInventory {

   public HashMap items = new HashMap();
   public HashMap dropchance = new HashMap();
   public HashMap weapons = new HashMap();
   public HashMap armor = new HashMap();
   public int minExp = 0;
   public int maxExp = 0;
   private EntityNPCInterface npc;


   public DataInventory(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public NBTTagCompound writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("MinExp", this.minExp);
      nbttagcompound.setInteger("MaxExp", this.maxExp);
      nbttagcompound.setTag("NpcInv", NBTTags.nbtItemStackList(this.items));
      nbttagcompound.setTag("Armor", NBTTags.nbtItemStackList(this.getArmor()));
      nbttagcompound.setTag("Weapons", NBTTags.nbtItemStackList(this.getWeapons()));
      nbttagcompound.setTag("DropChance", NBTTags.nbtIntegerIntegerMap(this.dropchance));
      return nbttagcompound;
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.minExp = nbttagcompound.getInteger("MinExp");
      this.maxExp = nbttagcompound.getInteger("MaxExp");
      this.items = NBTTags.getItemStackList(nbttagcompound.getTagList("NpcInv", 10));
      this.setArmor(NBTTags.getItemStackList(nbttagcompound.getTagList("Armor", 10)));
      this.setWeapons(NBTTags.getItemStackList(nbttagcompound.getTagList("Weapons", 10)));
      this.dropchance = NBTTags.getIntegerIntegerMap(nbttagcompound.getTagList("DropChance", 10));
   }

   public HashMap getWeapons() {
      return this.weapons;
   }

   public void setWeapons(HashMap list) {
      this.weapons = list;
   }

   public HashMap getArmor() {
      return this.armor;
   }

   public void setArmor(HashMap list) {
      this.armor = list;
   }

   public ItemStack getWeapon() {
      return (ItemStack)this.weapons.get(Integer.valueOf(0));
   }

   public void setWeapon(ItemStack item) {
      this.weapons.put(Integer.valueOf(0), item);
   }

   public ItemStack getProjectile() {
      return (ItemStack)this.weapons.get(Integer.valueOf(1));
   }

   public void setProjectile(ItemStack item) {
      this.weapons.put(Integer.valueOf(1), item);
   }

   public ItemStack getOffHand() {
      return (ItemStack)this.weapons.get(Integer.valueOf(2));
   }

   public void setOffHand(ItemStack item) {
      this.weapons.put(Integer.valueOf(2), item);
   }

   public void dropStuff() {
      Iterator var1 = this.items.keySet().iterator();

      int var2;
      while(var1.hasNext()) {
         var2 = ((Integer)var1.next()).intValue();
         ItemStack item = (ItemStack)this.items.get(Integer.valueOf(var2));
         if(item != null) {
            int dchance = 100;
            if(this.dropchance.containsKey(Integer.valueOf(var2))) {
               dchance = ((Integer)this.dropchance.get(Integer.valueOf(var2))).intValue();
            }

            int chance = this.npc.worldObj.rand.nextInt(100) + dchance;
            if(chance >= 100) {
               this.npc.dropPlayerItemWithRandomChoice(item.copy(), true);
            }
         }
      }

      int var11 = this.minExp;
      if(this.maxExp - this.minExp > 0) {
         var11 += this.npc.worldObj.rand.nextInt(this.maxExp - this.minExp);
      }

      while(var11 > 0) {
         var2 = EntityXPOrb.getXPSplit(var11);
         var11 -= var2;
         this.npc.worldObj.spawnEntityInWorld(new EntityXPOrb(this.npc.worldObj, this.npc.posX, this.npc.posY, this.npc.posZ, var2));
      }

   }

   public ItemStack armorItemInSlot(int i) {
      return (ItemStack)this.getArmor().get(Integer.valueOf(i));
   }

   public int getSizeInventory() {
      return 15;
   }

   public ItemStack getStackInSlot(int i) {
      return i < 4?this.armorItemInSlot(i):(i < 7?(ItemStack)this.getWeapons().get(Integer.valueOf(i - 4)):(ItemStack)this.items.get(Integer.valueOf(i - 7)));
   }

   public ItemStack decrStackSize(int par1, int par2) {
      byte i = 0;
      HashMap var3;
      if(par1 >= 7) {
         var3 = this.items;
         par1 -= 7;
      } else if(par1 >= 4) {
         var3 = this.getWeapons();
         par1 -= 4;
         i = 1;
      } else {
         var3 = this.getArmor();
         i = 2;
      }

      ItemStack var4 = null;
      if(var3.get(Integer.valueOf(par1)) != null) {
         if(((ItemStack)var3.get(Integer.valueOf(par1))).stackSize <= par2) {
            var4 = (ItemStack)var3.get(Integer.valueOf(par1));
            var3.put(Integer.valueOf(par1), (Object)null);
         } else {
            var4 = ((ItemStack)var3.get(Integer.valueOf(par1))).splitStack(par2);
            if(((ItemStack)var3.get(Integer.valueOf(par1))).stackSize == 0) {
               var3.put(Integer.valueOf(par1), (Object)null);
            }
         }
      }

      if(i == 1) {
         this.setWeapons(var3);
      }

      if(i == 2) {
         this.setArmor(var3);
      }

      return var4;
   }

   public ItemStack getStackInSlotOnClosing(int par1) {
      byte i = 0;
      HashMap var2;
      if(par1 >= 7) {
         var2 = this.items;
         par1 -= 7;
      } else if(par1 >= 4) {
         var2 = this.getWeapons();
         par1 -= 4;
         i = 1;
      } else {
         var2 = this.getArmor();
         i = 2;
      }

      if(var2.get(Integer.valueOf(par1)) != null) {
         ItemStack var3 = (ItemStack)var2.get(Integer.valueOf(par1));
         var2.put(Integer.valueOf(par1), (Object)null);
         if(i == 1) {
            this.setWeapons(var2);
         }

         if(i == 2) {
            this.setArmor(var2);
         }

         return var3;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      byte i = 0;
      HashMap var3;
      if(par1 >= 7) {
         var3 = this.items;
         par1 -= 7;
      } else if(par1 >= 4) {
         var3 = this.getWeapons();
         par1 -= 4;
         i = 1;
      } else {
         var3 = this.getArmor();
         i = 2;
      }

      var3.put(Integer.valueOf(par1), par2ItemStack);
      if(i == 1) {
         this.setWeapons(var3);
      }

      if(i == 2) {
         this.setArmor(var3);
      }

   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return true;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return true;
   }

   public String getInventoryName() {
      return "NPC Inventory";
   }

   public boolean isCustomInventoryName() {
      return true;
   }

   public void markDirty() {}

   public void openChest() {}

   public void closeChest() {}
}
