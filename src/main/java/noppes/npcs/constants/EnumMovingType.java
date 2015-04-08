package noppes.npcs.constants;

import java.util.ArrayList;

public enum EnumMovingType {

   Standing("Standing", 0, "ai.standing"),
   Wandering("Wandering", 1, "ai.wandering"),
   MovingPath("MovingPath", 2, "ai.movingpath");
   String name;
   // $FF: synthetic field
   private static final EnumMovingType[] $VALUES = new EnumMovingType[]{Standing, Wandering, MovingPath};


   private EnumMovingType(String var1, int var2, String name) {
      this.name = name;
   }

   public static String[] names() {
      ArrayList list = new ArrayList();
      EnumMovingType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumMovingType e = var1[var3];
         list.add(e.name);
      }

      return (String[])list.toArray(new String[list.size()]);
   }

}
