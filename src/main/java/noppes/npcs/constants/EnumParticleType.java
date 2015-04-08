package noppes.npcs.constants;


public enum EnumParticleType {

   None("None", 0, ""),
   Smoke("Smoke", 1, "smoke"),
   Portal("Portal", 2, "portal"),
   Redstone("Redstone", 3, "reddust"),
   Lightning("Lightning", 4, "magicCrit"),
   LargeSmoke("LargeSmoke", 5, "largesmoke"),
   Magic("Magic", 6, "witchMagic"),
   Enchant("Enchant", 7, "enchantmenttable"),
   Crit("Crit", 8, "crit");
   public String particleName;
   // $FF: synthetic field
   private static final EnumParticleType[] $VALUES = new EnumParticleType[]{None, Smoke, Portal, Redstone, Lightning, LargeSmoke, Magic, Enchant, Crit};


   private EnumParticleType(String var1, int var2, String name) {
      this.particleName = name;
   }

}
