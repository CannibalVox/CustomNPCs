package noppes.npcs.constants;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;

public enum EnumCompanionTalent {

   INVENTORY("INVENTORY", 0, CustomItems.satchel),
   ARMOR("ARMOR", 1, Items.iron_chestplate),
   SWORD("SWORD", 2, Items.diamond_sword),
   RANGED("RANGED", 3, Items.bow),
   ACROBATS("ACROBATS", 4, Items.leather_boots),
   INTEL("INTEL", 5, CustomItems.letter);
   public ItemStack item;
   // $FF: synthetic field
   private static final EnumCompanionTalent[] $VALUES = new EnumCompanionTalent[]{INVENTORY, ARMOR, SWORD, RANGED, ACROBATS, INTEL};


   private EnumCompanionTalent(String var1, int var2, Item item) {
      this.item = new ItemStack(item);
   }

}
