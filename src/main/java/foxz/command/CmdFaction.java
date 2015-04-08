package foxz.command;

import foxz.commandhelper.ChMcLogger;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.OpOnly;
import foxz.commandhelper.permissions.ParamCheck;
import java.util.Iterator;
import java.util.List;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerFactionData;

@Command(
   name = "faction",
   desc = "operations about relationship between player and faction"
)
public class CmdFaction extends ChMcLogger {

   public String playername;
   public Faction selectedFaction;
   public List data;


   public CmdFaction(Object sender) {
      super(sender);
   }

   @SubCommand(
      desc = "Add points",
      usage = "<points>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean add(String[] args) {
      int points;
      try {
         points = Integer.parseInt(args[0]);
      } catch (NumberFormatException var7) {
         this.sendmessage("Must be an integer");
         return Boolean.valueOf(false);
      }

      int factionid = this.selectedFaction.id;
      Iterator var4 = this.data.iterator();

      while(var4.hasNext()) {
         PlayerData playerdata = (PlayerData)var4.next();
         PlayerFactionData playerfactiondata = playerdata.factionData;
         playerfactiondata.increasePoints(factionid, points);
      }

      return Boolean.valueOf(true);
   }

   @SubCommand(
      desc = "Substract points",
      usage = "<points>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean substract(String[] args) {
      int points;
      try {
         points = Integer.parseInt(args[0]);
      } catch (NumberFormatException var7) {
         this.sendmessage("Must be an integer");
         return Boolean.valueOf(false);
      }

      int factionid = this.selectedFaction.id;
      Iterator var4 = this.data.iterator();

      while(var4.hasNext()) {
         PlayerData playerdata = (PlayerData)var4.next();
         PlayerFactionData playerfactiondata = playerdata.factionData;
         playerfactiondata.increasePoints(factionid, -points);
      }

      return Boolean.valueOf(true);
   }

   @SubCommand(
      desc = "Reset points to default",
      usage = "",
      permissions = {OpOnly.class}
   )
   public Boolean reset(String[] args) {
      Iterator var2 = this.data.iterator();

      while(var2.hasNext()) {
         PlayerData playerdata = (PlayerData)var2.next();
         playerdata.factionData.factionData.put(Integer.valueOf(this.selectedFaction.id), Integer.valueOf(this.selectedFaction.defaultPoints));
      }

      return Boolean.valueOf(true);
   }

   @SubCommand(
      desc = "Set points",
      usage = "<points>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean set(String[] args) {
      int points;
      try {
         points = Integer.parseInt(args[0]);
      } catch (NumberFormatException var6) {
         this.sendmessage("Must be an integer");
         return Boolean.valueOf(false);
      }

      Iterator ex = this.data.iterator();

      while(ex.hasNext()) {
         PlayerData playerdata = (PlayerData)ex.next();
         PlayerFactionData playerfactiondata = playerdata.factionData;
         playerfactiondata.factionData.put(Integer.valueOf(this.selectedFaction.id), Integer.valueOf(points));
      }

      return Boolean.valueOf(true);
   }

   @SubCommand(
      desc = "Drop relationship",
      usage = "",
      permissions = {OpOnly.class}
   )
   public Boolean drop(String[] args) {
      Iterator var2 = this.data.iterator();

      while(var2.hasNext()) {
         PlayerData playerdata = (PlayerData)var2.next();
         playerdata.factionData.factionData.remove(Integer.valueOf(this.selectedFaction.id));
      }

      return Boolean.valueOf(true);
   }
}
