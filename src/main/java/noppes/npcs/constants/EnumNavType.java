package noppes.npcs.constants;

import java.util.ArrayList;

public enum EnumNavType {

   Default("Default", 0, "aitactics.rush"),
   Dodge("Dodge", 1, "aitactics.stagger"),
   Surround("Surround", 2, "aitactics.orbit"),
   HitNRun("HitNRun", 3, "aitactics.hitandrun"),
   Ambush("Ambush", 4, "aitactics.ambush"),
   Stalk("Stalk", 5, "aitactics.stalk"),
   None("None", 6, "gui.none");
   String name;
   // $FF: synthetic field
   private static final EnumNavType[] $VALUES = new EnumNavType[]{Default, Dodge, Surround, HitNRun, Ambush, Stalk, None};


   private EnumNavType(String var1, int var2, String name) {
      this.name = name;
   }

   public static String[] names() {
      ArrayList list = new ArrayList();
      EnumNavType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumNavType e = var1[var3];
         list.add(e.name);
      }

      return (String[])list.toArray(new String[list.size()]);
   }

}
