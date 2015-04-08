package noppes.npcs.constants;


public enum EnumAnimation {

   NONE("NONE", 0),
   SITTING("SITTING", 1),
   LYING("LYING", 2),
   SNEAKING("SNEAKING", 3),
   DANCING("DANCING", 4),
   AIMING("AIMING", 5),
   CRAWLING("CRAWLING", 6),
   HUG("HUG", 7),
   CRY("CRY", 8),
   WAVING("WAVING", 9),
   BOW("BOW", 10);
   // $FF: synthetic field
   private static final EnumAnimation[] $VALUES = new EnumAnimation[]{NONE, SITTING, LYING, SNEAKING, DANCING, AIMING, CRAWLING, HUG, CRY, WAVING, BOW};


   private EnumAnimation(String var1, int var2) {}

   public int getWalkingAnimation() {
      return this == SNEAKING?1:(this == AIMING?2:(this == DANCING?3:(this == CRAWLING?4:(this == HUG?5:0))));
   }

}
