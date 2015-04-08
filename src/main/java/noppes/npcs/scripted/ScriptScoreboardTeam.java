package noppes.npcs.scripted;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

public class ScriptScoreboardTeam {

   private ScorePlayerTeam team;
   private Scoreboard board;


   protected ScriptScoreboardTeam(ScorePlayerTeam team, Scoreboard board) {
      this.team = team;
      this.board = board;
   }

   public String getName() {
      return this.team.getRegisteredName();
   }

   public String getDisplayName() {
      return this.team.func_96669_c();
   }

   public void setDisplayName(String name) {
      if(name.length() > 0 && name.length() <= 32) {
         this.team.setTeamName(name);
      }

   }

   public void addPlayer(String player) {
      this.board.func_151392_a(player, this.getName());
   }

   public void removePlayer(String player) {
      this.board.removePlayerFromTeam(player, this.team);
   }

   public String[] getPlayers() {
      ArrayList list = new ArrayList(this.team.getMembershipCollection());
      return (String[])list.toArray(new String[list.size()]);
   }

   public void clearPlayers() {
      ArrayList list = new ArrayList(this.team.getMembershipCollection());
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         String player = (String)var2.next();
         this.board.removePlayerFromTeam(player, this.team);
      }

   }

   public boolean getFriendlyFire() {
      return this.team.getAllowFriendlyFire();
   }

   public void setFriendlyFire(boolean bo) {
      this.team.setAllowFriendlyFire(bo);
   }

   public void setColor(String color) {
      EnumChatFormatting enumchatformatting = EnumChatFormatting.getValueByName(color);
      if(enumchatformatting != null && !enumchatformatting.isFancyStyling()) {
         this.team.setNamePrefix(enumchatformatting.toString());
         this.team.setNameSuffix(EnumChatFormatting.RESET.toString());
      }
   }

   public String getColor() {
      String prefix = this.team.getColorPrefix();
      if(prefix != null && !prefix.isEmpty()) {
         EnumChatFormatting[] var2 = EnumChatFormatting.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EnumChatFormatting format = var2[var4];
            if(prefix.equals(format.toString()) && format != EnumChatFormatting.RESET) {
               return format.getFriendlyName();
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public void setSeeInvisibleTeamPlayers(boolean bo) {
      this.team.setSeeFriendlyInvisiblesEnabled(bo);
   }

   public boolean getSeeInvisibleTeamPlayers() {
      return this.team.func_98297_h();
   }
}
