package foxz.command;

import foxz.commandhelper.ChMcLogger;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.OpOnly;
import foxz.commandhelper.permissions.ParamCheck;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.QuestData;

@Command(
   name = "quest",
   usage = "help",
   desc = "Quest operations"
)
public class CmdQuest extends ChMcLogger {

   public CmdQuest(Object sender) {
      super(sender);
   }

   @SubCommand(
      desc = "Start a quest",
      usage = "<player> <quest>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean start(String[] args) {
      String playername = args[0];

      int questid;
      try {
         questid = Integer.parseInt(args[1]);
      } catch (NumberFormatException var9) {
         this.sendmessage("QuestID must be an integer");
         return Boolean.valueOf(false);
      }

      List data = this.getPlayersData(playername);
      if(data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return Boolean.valueOf(false);
      } else {
         Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(questid));
         if(quest == null) {
            this.sendmessage("Unknown QuestID");
            return Boolean.valueOf(false);
         } else {
            Iterator var6 = data.iterator();

            while(var6.hasNext()) {
               PlayerData playerdata = (PlayerData)var6.next();
               QuestData questdata = new QuestData(quest);
               playerdata.questData.activeQuests.put(Integer.valueOf(questid), questdata);
               playerdata.saveNBTData((NBTTagCompound)null);
            }

            return Boolean.valueOf(true);
         }
      }
   }

   @SubCommand(
      desc = "Finish a quest",
      usage = "<player> <quest>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean finish(String[] args) {
      String playername = args[0];

      int questid;
      try {
         questid = Integer.parseInt(args[1]);
      } catch (NumberFormatException var8) {
         this.sendmessage("QuestID must be an integer");
         return Boolean.valueOf(false);
      }

      List data = this.getPlayersData(playername);
      if(data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return Boolean.valueOf(false);
      } else {
         Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(questid));
         if(quest == null) {
            this.sendmessage("Unknown QuestID");
            return Boolean.valueOf(false);
         } else {
            Iterator var6 = data.iterator();

            while(var6.hasNext()) {
               PlayerData playerdata = (PlayerData)var6.next();
               playerdata.questData.finishedQuests.put(Integer.valueOf(questid), Long.valueOf(System.currentTimeMillis()));
               playerdata.saveNBTData((NBTTagCompound)null);
            }

            return Boolean.valueOf(true);
         }
      }
   }

   @SubCommand(
      desc = "Stop a started quest",
      usage = "<player> <quest>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean stop(String[] args) {
      String playername = args[0];

      int questid;
      try {
         questid = Integer.parseInt(args[1]);
      } catch (NumberFormatException var8) {
         this.sendmessage("QuestID must be an integer");
         return Boolean.valueOf(false);
      }

      List data = this.getPlayersData(playername);
      if(data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return Boolean.valueOf(false);
      } else {
         Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(questid));
         if(quest == null) {
            this.sendmessage("Unknown QuestID");
            return Boolean.valueOf(false);
         } else {
            Iterator var6 = data.iterator();

            while(var6.hasNext()) {
               PlayerData playerdata = (PlayerData)var6.next();
               playerdata.questData.activeQuests.remove(Integer.valueOf(questid));
               playerdata.saveNBTData((NBTTagCompound)null);
            }

            return Boolean.valueOf(true);
         }
      }
   }

   @SubCommand(
      desc = "Removes a quest from finished and active quests",
      usage = "<player> <quest>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean remove(String[] args) {
      String playername = args[0];

      int questid;
      try {
         questid = Integer.parseInt(args[1]);
      } catch (NumberFormatException var8) {
         this.sendmessage("QuestID must be an integer");
         return Boolean.valueOf(false);
      }

      List data = this.getPlayersData(playername);
      if(data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return Boolean.valueOf(false);
      } else {
         Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(questid));
         if(quest == null) {
            this.sendmessage("Unknown QuestID");
            return Boolean.valueOf(false);
         } else {
            Iterator var6 = data.iterator();

            while(var6.hasNext()) {
               PlayerData playerdata = (PlayerData)var6.next();
               playerdata.questData.activeQuests.remove(Integer.valueOf(questid));
               playerdata.questData.finishedQuests.remove(Integer.valueOf(questid));
               playerdata.saveNBTData((NBTTagCompound)null);
            }

            return Boolean.valueOf(true);
         }
      }
   }

   @SubCommand(
      desc = "reload quests from disk",
      permissions = {OpOnly.class}
   )
   public boolean reload(String[] args) {
      new DialogController();
      return true;
   }
}
