package noppes.npcs.scripted.roles;

import foxz.utils.Market;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.scripted.ScriptItemStack;
import noppes.npcs.scripted.roles.ScriptRoleInterface;

public class ScriptRoleTrader extends ScriptRoleInterface {

   private RoleTrader role;


   public ScriptRoleTrader(EntityNPCInterface npc) {
      super(npc);
      this.role = (RoleTrader)npc.roleInterface;
   }

   public void setSellOption(int slot, ScriptItemStack currency, ScriptItemStack currency2, ScriptItemStack sold) {
      if(sold != null && slot < 18 && slot >= 0) {
         if(currency == null) {
            currency = currency2;
         }

         if(currency != null) {
            this.role.inventoryCurrency.items.put(Integer.valueOf(slot), currency.getMCItemStack());
         } else {
            this.role.inventoryCurrency.items.remove(Integer.valueOf(slot));
         }

         if(currency2 != null) {
            this.role.inventoryCurrency.items.put(Integer.valueOf(slot + 18), currency2.getMCItemStack());
         } else {
            this.role.inventoryCurrency.items.remove(Integer.valueOf(slot + 18));
         }

         this.role.inventorySold.items.put(Integer.valueOf(slot), sold.getMCItemStack());
      }
   }

   public void setSellOption(int slot, ScriptItemStack currency, ScriptItemStack sold) {
      this.setSellOption(slot, currency, (ScriptItemStack)null, sold);
   }

   public void removeSellOption(int slot) {
      if(slot < 18 && slot >= 0) {
         this.role.inventoryCurrency.items.remove(Integer.valueOf(slot));
         this.role.inventoryCurrency.items.remove(Integer.valueOf(slot + 18));
         this.role.inventorySold.items.remove(Integer.valueOf(slot));
      }
   }

   public void setMarket(String name) {
      this.role.marketName = name;
      Market.load(this.role, name);
   }

   public String getMarket() {
      return this.role.marketName;
   }

   public int getType() {
      return 2;
   }
}
