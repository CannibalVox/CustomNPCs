package noppes.npcs.roles;

import foxz.utils.Market;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RoleTrader extends RoleInterface {

   public String marketName = "";
   public NpcMiscInventory inventoryCurrency = new NpcMiscInventory(36);
   public NpcMiscInventory inventorySold = new NpcMiscInventory(18);
   public boolean ignoreDamage = false;
   public boolean ignoreNBT = false;
   public boolean toSave = false;


   public RoleTrader(EntityNPCInterface npc) {
      super(npc);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setTag("TraderCurrency", this.inventoryCurrency.getToNBT());
      nbttagcompound.setTag("TraderSold", this.inventorySold.getToNBT());
      nbttagcompound.setString("TraderMarket", this.marketName);
      nbttagcompound.setBoolean("TraderIgnoreDamage", this.ignoreDamage);
      nbttagcompound.setBoolean("TraderIgnoreNBT", this.ignoreNBT);
      if(this.toSave && !super.npc.isRemote()) {
         Market.save(this, this.marketName);
      }

      this.toSave = false;
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.inventoryCurrency.setFromNBT(nbttagcompound.getCompoundTag("TraderCurrency"));
      this.inventorySold.setFromNBT(nbttagcompound.getCompoundTag("TraderSold"));
      this.marketName = nbttagcompound.getString("TraderMarket");
      this.ignoreDamage = nbttagcompound.getBoolean("TraderIgnoreDamage");
      this.ignoreNBT = nbttagcompound.getBoolean("TraderIgnoreNBT");

      try {
         Market.load(this, this.marketName);
      } catch (Exception var3) {
         Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, (String)null, var3);
      }

   }

   public void interact(EntityPlayer player) {
      super.npc.say(player, super.npc.advanced.getInteractLine());

      try {
         Market.load(this, this.marketName);
      } catch (Exception var3) {
         Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, (String)null, var3);
      }

      NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTrader, super.npc);
   }

   public boolean hasCurrency(ItemStack itemstack) {
      if(itemstack == null) {
         return false;
      } else {
         Iterator var2 = this.inventoryCurrency.items.values().iterator();

         ItemStack item;
         do {
            if(!var2.hasNext()) {
               return false;
            }

            item = (ItemStack)var2.next();
         } while(item == null || !NoppesUtilPlayer.compareItems(item, itemstack, this.ignoreDamage, this.ignoreNBT));

         return true;
      }
   }
}
