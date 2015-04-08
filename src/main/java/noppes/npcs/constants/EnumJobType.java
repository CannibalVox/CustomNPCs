package noppes.npcs.constants;


public enum EnumJobType {

   None("None", 0),
   Bard("Bard", 1),
   Healer("Healer", 2),
   Guard("Guard", 3),
   ItemGiver("ItemGiver", 4),
   Follower("Follower", 5),
   Spawner("Spawner", 6),
   Conversation("Conversation", 7),
   ChunkLoader("ChunkLoader", 8),
   Puppet("Puppet", 9);
   // $FF: synthetic field
   private static final EnumJobType[] $VALUES = new EnumJobType[]{None, Bard, Healer, Guard, ItemGiver, Follower, Spawner, Conversation, ChunkLoader, Puppet};


   private EnumJobType(String var1, int var2) {}

}
