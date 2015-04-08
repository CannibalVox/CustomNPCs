package noppes.npcs.constants;

import noppes.npcs.constants.EnumAnimation;

public enum EnumCompanionStage {

   BABY("BABY", 0, 0, EnumAnimation.CRAWLING, "companion.baby"),
   CHILD("CHILD", 1, 72000, EnumAnimation.NONE, "companion.child"),
   TEEN("TEEN", 2, 180000, EnumAnimation.NONE, "companion.teenager"),
   ADULT("ADULT", 3, 324000, EnumAnimation.NONE, "companion.adult"),
   FULLGROWN("FULLGROWN", 4, 450000, EnumAnimation.NONE, "companion.fullgrown");
   public int matureAge;
   public EnumAnimation animation;
   public String name;
   // $FF: synthetic field
   private static final EnumCompanionStage[] $VALUES = new EnumCompanionStage[]{BABY, CHILD, TEEN, ADULT, FULLGROWN};


   private EnumCompanionStage(String var1, int var2, int age, EnumAnimation animation, String name) {
      this.matureAge = age;
      this.animation = animation;
      this.name = name;
   }

}
