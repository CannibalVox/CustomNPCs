package noppes.npcs.scripted;

import java.util.ArrayList;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.scripted.ScriptScoreboardObjective;
import noppes.npcs.scripted.ScriptScoreboardTeam;

public class ScriptScoreboard {

   private Scoreboard board = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();


   public ScriptScoreboardObjective[] getObjectives() {
      ArrayList collection = new ArrayList(this.board.getScoreObjectives());
      ScriptScoreboardObjective[] objectives = new ScriptScoreboardObjective[collection.size()];

      for(int i = 0; i < collection.size(); ++i) {
         objectives[i] = new ScriptScoreboardObjective((ScoreObjective)collection.get(i));
      }

      return objectives;
   }

   public ScriptScoreboardObjective getObjective(String name) {
      ScoreObjective obj = this.board.getObjective(name);
      return obj == null?null:new ScriptScoreboardObjective(obj);
   }

   public boolean hasObjective(String objective) {
      return this.board.getObjective(objective) != null;
   }

   public void removeObjective(String objective) {
      ScoreObjective obj = this.board.getObjective(objective);
      if(obj != null) {
         this.board.func_96519_k(obj);
      }

   }

   public ScriptScoreboardObjective addObjective(String objective, String criteria) {
      if(this.hasObjective(objective)) {
         return null;
      } else {
         IScoreObjectiveCriteria icriteria = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(criteria);
         if(icriteria == null) {
            return null;
         } else if(objective.length() != 0 && objective.length() <= 16) {
            ScoreObjective obj = this.board.addScoreObjective(objective, icriteria);
            return new ScriptScoreboardObjective(obj);
         } else {
            return null;
         }
      }
   }

   public void setPlayerScore(String player, String objective, int score, String datatag) {
      ScoreObjective objec = this.board.getObjective(objective);
      if(objec != null && !objec.getCriteria().isReadOnly() && score >= Integer.MIN_VALUE && score <= Integer.MAX_VALUE) {
         Score sco = this.board.getValueFromObjective(player, objec);
         sco.setScorePoints(score);
      }
   }

   public int getPlayerScore(String player, String objective, String datatag) {
      ScoreObjective objec = this.board.getObjective(objective);
      return objec != null && !objec.getCriteria().isReadOnly()?this.board.getValueFromObjective(player, objec).getScorePoints():0;
   }

   public boolean hasPlayerObjective(String player, String objective, String datatag) {
      ScoreObjective objec = this.board.getObjective(objective);
      return objec == null?false:this.board.func_96510_d(player).get(objec) != null;
   }

   public void deletePlayerScore(String player, String objective, String datatag) {
      ScoreObjective objec = this.board.getObjective(objective);
      if(objec != null) {
         if(this.board.func_96510_d(player).remove(objec) != null) {
            this.board.func_96516_a(player);
         }

      }
   }

   public ScriptScoreboardTeam[] getTeams() {
      ArrayList list = new ArrayList(this.board.getTeams());
      ScriptScoreboardTeam[] teams = new ScriptScoreboardTeam[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         teams[i] = new ScriptScoreboardTeam((ScorePlayerTeam)list.get(i), this.board);
      }

      return teams;
   }

   public boolean hasTeam(String name) {
      return this.board.getPlayersTeam(name) != null;
   }

   public ScriptScoreboardTeam addTeam(String name) {
      return this.hasTeam(name)?null:new ScriptScoreboardTeam(this.board.createTeam(name), this.board);
   }

   public ScriptScoreboardTeam getTeam(String name) {
      ScorePlayerTeam team = this.board.getPlayersTeam(name);
      return team == null?null:new ScriptScoreboardTeam(team, this.board);
   }

   public void removeTeam(String name) {
      ScorePlayerTeam team = this.board.getPlayersTeam(name);
      if(team != null) {
         this.board.removeTeam(team);
      }

   }
}
