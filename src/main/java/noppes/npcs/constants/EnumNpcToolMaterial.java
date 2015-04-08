package noppes.npcs.constants;


public enum EnumNpcToolMaterial {

   WOOD("WOOD", 0, 0, 59, 2.0F, 0, 15),
   STONE("STONE", 1, 1, 131, 4.0F, 1, 5),
   BRONZE("BRONZE", 2, 2, 170, 5.0F, 2, 15),
   IRON("IRON", 3, 2, 250, 6.0F, 2, 14),
   DIA("DIA", 4, 3, 1561, 8.0F, 3, 10),
   GOLD("GOLD", 5, 0, 32, 12.0F, 1, 22),
   EMERALD("EMERALD", 6, 3, 1000, 8.0F, 4, 10),
   DEMONIC("DEMONIC", 7, 3, 100, 8.0F, 6, 10),
   FROST("FROST", 8, 2, 59, 6.0F, 3, 5),
   MITHRIL("MITHRIL", 9, 3, 3000, 8.0F, 3, 10);
   private final int harvestLevel;
   private final int maxUses;
   private final float efficiencyOnProperMaterial;
   private final int damageVsEntity;
   private final int enchantability;
   // $FF: synthetic field
   private static final EnumNpcToolMaterial[] $VALUES = new EnumNpcToolMaterial[]{WOOD, STONE, BRONZE, IRON, DIA, GOLD, EMERALD, DEMONIC, FROST, MITHRIL};


   private EnumNpcToolMaterial(String var1, int var2, int par3, int par4, float par5, int par6, int par7) {
      this.harvestLevel = par3;
      this.maxUses = par4;
      this.efficiencyOnProperMaterial = par5;
      this.damageVsEntity = par6;
      this.enchantability = par7;
   }

   public int getMaxUses() {
      return this.maxUses;
   }

   public float getEfficiencyOnProperMaterial() {
      return this.efficiencyOnProperMaterial;
   }

   public int getDamageVsEntity() {
      return this.damageVsEntity;
   }

   public int getHarvestLevel() {
      return this.harvestLevel;
   }

   public int getEnchantability() {
      return this.enchantability;
   }

}
