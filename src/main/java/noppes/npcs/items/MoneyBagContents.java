package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MoneyBagContents {

   private EntityPlayer player;
   private int[] coinData = new int[]{0, 0, 0, 0, 0, 0, 0};


   public MoneyBagContents(EntityPlayer player) {
      this.player = player;
   }

   public void readNBT(NBTTagCompound compound) {
      this.coinData = compound.getIntArray("coins");
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setIntArray("coins", this.coinData);
      return compound;
   }

   public void AddCurrency(MoneyBagContents.CoinType coinType, byte stackSize, ItemStack theBag) {
      this.coinData[coinType.ordinal()] += stackSize;
      theBag.stackTagCompound.setTag("contents", this.writeNBT());
   }

   public void WithdrawCurrencyByVal(MoneyBagContents.CoinType coinType, short amount, ItemStack theBag) {
      byte amtAdded = 0;
      this.coinData[coinType.ordinal()] -= amtAdded;
      theBag.stackTagCompound.setTag("contents", this.writeNBT());
   }

   public void WithdrawCurrencyByStack(MoneyBagContents.CoinType coinType, byte numStacks, ItemStack theBag) {
      byte amtAdded = 0;
      this.coinData[coinType.ordinal()] -= amtAdded;
      theBag.stackTagCompound.setTag("contents", this.writeNBT());
   }

   public static enum CoinType {

      WOOD("WOOD", 0),
      STONE("STONE", 1),
      IRON("IRON", 2),
      GOLD("GOLD", 3),
      DIAMOND("DIAMOND", 4),
      BRONZE("BRONZE", 5),
      EMERALD("EMERALD", 6);
      // $FF: synthetic field
      private static final MoneyBagContents.CoinType[] $VALUES = new MoneyBagContents.CoinType[]{WOOD, STONE, IRON, GOLD, DIAMOND, BRONZE, EMERALD};


      private CoinType(String var1, int var2) {}

   }
}
