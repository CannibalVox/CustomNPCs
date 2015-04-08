package noppes.npcs.scripted;

import noppes.npcs.controllers.Faction;
import noppes.npcs.scripted.ScriptNpc;
import noppes.npcs.scripted.ScriptPlayer;

public class ScriptFaction {

   private Faction faction;


   public ScriptFaction(Faction faction) {
      this.faction = faction;
   }

   public int getId() {
      return this.faction.id;
   }

   public String getName() {
      return this.faction.name;
   }

   public int getDefaultPoints() {
      return this.faction.defaultPoints;
   }

   public int getColor() {
      return this.faction.color;
   }

   public boolean isFriendlyToPlayer(ScriptPlayer player) {
      return this.faction.isFriendlyToPlayer(player.player);
   }

   public boolean isNeutralToPlayer(ScriptPlayer player) {
      return this.faction.isNeutralToPlayer(player.player);
   }

   public boolean isAggressiveToPlayer(ScriptPlayer player) {
      return this.faction.isAggressiveToPlayer(player.player);
   }

   public boolean isAggressiveToNpc(ScriptNpc npc) {
      return this.faction.isAggressiveToNpc(npc.npc);
   }
}
