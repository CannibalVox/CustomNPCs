package noppes.npcs.util;


public class ValueUtil {

   public static float correctFloat(float given, float min, float max) {
      return given < min?min:(given > max?max:given);
   }

   public static int CorrectInt(int given, int min, int max) {
      return given < min?min:(given > max?max:given);
   }
}
