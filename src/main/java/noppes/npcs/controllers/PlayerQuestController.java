package noppes.npcs.controllers;

import java.util.Iterator;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.quests.QuestDialog;

public class PlayerQuestController {

   public static boolean hasActiveQuests(EntityPlayer player) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      return !data.activeQuests.isEmpty();
   }

   public static boolean isQuestActive(EntityPlayer player, int quest) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      return data.activeQuests.containsKey(Integer.valueOf(quest));
   }

   public static boolean isQuestFinished(EntityPlayer player, int questid) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      return data.finishedQuests.containsKey(Integer.valueOf(questid));
   }

   public static void addActiveQuest(Quest quest, EntityPlayer player) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      if(canQuestBeAccepted(quest, player)) {
         data.activeQuests.put(Integer.valueOf(quest.id), new QuestData(quest));
         Server.sendData((EntityPlayerMP)player, EnumPacketClient.MESSAGE, new Object[]{"quest.newquest", quest.title});
         Server.sendData((EntityPlayerMP)player, EnumPacketClient.CHAT, new Object[]{"quest.newquest", ": ", quest.title});
      }

   }

   public static void setQuestFinished(Quest quest, EntityPlayer player) {
      PlayerData playerdata = PlayerDataController.instance.getPlayerData(player);
      PlayerQuestData data = playerdata.questData;
      data.activeQuests.remove(Integer.valueOf(quest.id));
      if(quest.repeat != EnumQuestRepeat.RLDAILY && quest.repeat != EnumQuestRepeat.RLWEEKLY) {
         data.finishedQuests.put(Integer.valueOf(quest.id), Long.valueOf(player.worldObj.getTotalWorldTime()));
      } else {
         data.finishedQuests.put(Integer.valueOf(quest.id), Long.valueOf(System.currentTimeMillis()));
      }

      if(quest.repeat != EnumQuestRepeat.NONE && quest.type == EnumQuestType.Dialog) {
         QuestDialog questdialog = (QuestDialog)quest.questInterface;
         Iterator var5 = questdialog.dialogs.values().iterator();

         while(var5.hasNext()) {
            int dialog = ((Integer)var5.next()).intValue();
            playerdata.dialogData.dialogsRead.remove(Integer.valueOf(dialog));
         }
      }

   }

   public static boolean canQuestBeAccepted(Quest quest, EntityPlayer player) {
      if(quest == null) {
         return false;
      } else {
         PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
         if(data.activeQuests.containsKey(Integer.valueOf(quest.id))) {
            return false;
         } else if(data.finishedQuests.containsKey(Integer.valueOf(quest.id)) && quest.repeat != EnumQuestRepeat.REPEATABLE) {
            if(quest.repeat == EnumQuestRepeat.NONE) {
               return false;
            } else {
               long questTime = ((Long)data.finishedQuests.get(Integer.valueOf(quest.id))).longValue();
               return quest.repeat == EnumQuestRepeat.MCDAILY?player.worldObj.getTotalWorldTime() - questTime >= 24000L:(quest.repeat == EnumQuestRepeat.MCWEEKLY?player.worldObj.getTotalWorldTime() - questTime >= 168000L:(quest.repeat == EnumQuestRepeat.RLDAILY?System.currentTimeMillis() - questTime >= 86400000L:(quest.repeat == EnumQuestRepeat.RLWEEKLY?System.currentTimeMillis() - questTime >= 604800000L:false)));
            }
         } else {
            return true;
         }
      }
   }

   public static Vector getActiveQuests(EntityPlayer player) {
      Vector quests = new Vector();
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      Iterator var3 = data.activeQuests.values().iterator();

      while(var3.hasNext()) {
         QuestData questdata = (QuestData)var3.next();
         if(questdata.quest != null) {
            quests.add(questdata.quest);
         }
      }

      return quests;
   }
}
