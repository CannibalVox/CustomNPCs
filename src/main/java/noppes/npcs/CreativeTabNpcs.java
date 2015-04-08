package noppes.npcs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabNpcs extends CreativeTabs {

   public Item item;
   public int meta;


   public CreativeTabNpcs(String label) {
      super(label);
      this.item = Items.bowl;
      this.meta = 0;
   }

   public Item getTabIconItem() {
      return this.item;
   }

   public int getIconItemDamage() {
      return this.meta;
   }
}
