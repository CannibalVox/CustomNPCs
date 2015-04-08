package noppes.npcs.constants;


public enum EnumScriptType {

   INIT("INIT", 0),
   TICK("TICK", 1),
   INTERACT("INTERACT", 2),
   DIALOG("DIALOG", 3),
   DAMAGED("DAMAGED", 4),
   KILLED("KILLED", 5),
   ATTACK("ATTACK", 6),
   TARGET("TARGET", 7),
   COLLIDE("COLLIDE", 8),
   KILLS("KILLS", 9),
   DIALOG_OPTION("DIALOG_OPTION", 10);
   // $FF: synthetic field
   private static final EnumScriptType[] $VALUES = new EnumScriptType[]{INIT, TICK, INTERACT, DIALOG, DAMAGED, KILLED, ATTACK, TARGET, COLLIDE, KILLS, DIALOG_OPTION};


   private EnumScriptType(String var1, int var2) {}

}
