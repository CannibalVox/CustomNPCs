package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.quests.QuestInterface;

public class PlayerQuestData {

   public HashMap activeQuests = new HashMap();
   public HashMap finishedQuests = new HashMap();


   public void loadNBTData(NBTTagCompound mainCompound) {
      if(mainCompound != null) {
         NBTTagCompound compound = mainCompound.getCompoundTag("QuestData");
         NBTTagList list = compound.getTagList("CompletedQuests", 10);
         if(list != null) {
            HashMap list2 = new HashMap();

            for(int activeQuests = 0; activeQuests < list.tagCount(); ++activeQuests) {
               NBTTagCompound i = list.getCompoundTagAt(activeQuests);
               list2.put(Integer.valueOf(i.getInteger("Quest")), Long.valueOf(i.getLong("Date")));
            }

            this.finishedQuests = list2;
         }

         NBTTagList var11 = compound.getTagList("ActiveQuests", 10);
         if(var11 != null) {
            HashMap var12 = new HashMap();

            for(int var13 = 0; var13 < var11.tagCount(); ++var13) {
               NBTTagCompound nbttagcompound = var11.getCompoundTagAt(var13);
               int id = nbttagcompound.getInteger("Quest");
               Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(id));
               if(quest != null) {
                  QuestData data = new QuestData(quest);
                  data.readEntityFromNBT(nbttagcompound);
                  var12.put(Integer.valueOf(id), data);
               }
            }

            this.activeQuests = var12;
         }

      }
   }

   public void saveNBTData(NBTTagCompound maincompound) {
      NBTTagCompound compound = new NBTTagCompound();
      NBTTagList list = new NBTTagList();
      Iterator list2 = this.finishedQuests.keySet().iterator();

      while(list2.hasNext()) {
         int quest = ((Integer)list2.next()).intValue();
         NBTTagCompound quest1 = new NBTTagCompound();
         quest1.setInteger("Quest", quest);
         quest1.setLong("Date", ((Long)this.finishedQuests.get(Integer.valueOf(quest))).longValue());
         list.appendTag(quest1);
      }

      compound.setTag("CompletedQuests", list);
      NBTTagList list21 = new NBTTagList();
      Iterator quest2 = this.activeQuests.keySet().iterator();

      while(quest2.hasNext()) {
         int quest3 = ((Integer)quest2.next()).intValue();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setInteger("Quest", quest3);
         ((QuestData)this.activeQuests.get(Integer.valueOf(quest3))).writeEntityToNBT(nbttagcompound);
         list21.appendTag(nbttagcompound);
      }

      compound.setTag("ActiveQuests", list21);
      maincompound.setTag("QuestData", compound);
   }

   public QuestData getQuestCompletion(EntityPlayer player, EntityNPCInterface npc) {
      Iterator var3 = this.activeQuests.values().iterator();

      QuestData data;
      Quest quest;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         data = (QuestData)var3.next();
         quest = data.quest;
      } while(quest == null || quest.completion != EnumQuestCompletion.Npc || !quest.completerNpc.equals(npc.getCommandSenderName()) || !quest.questInterface.isCompleted(player));

      return data;
   }

   public boolean checkQuestCompletion(EntityPlayer player, EnumQuestType type) {
      boolean bo = false;
      Iterator var4 = this.activeQuests.values().iterator();

      while(var4.hasNext()) {
         QuestData data = (QuestData)var4.next();
         if(data.quest.type == type || type == null) {
            QuestInterface inter = data.quest.questInterface;
            if(inter.isCompleted(player)) {
               if(!data.isCompleted) {
                  if(!data.quest.complete(player, data)) {
                     Server.sendData((EntityPlayerMP)player, EnumPacketClient.MESSAGE, new Object[]{"quest.completed", data.quest.title});
                     Server.sendData((EntityPlayerMP)player, EnumPacketClient.CHAT, new Object[]{"quest.completed", ": ", data.quest.title});
                  }

                  data.isCompleted = true;
                  bo = true;
               }
            } else {
               data.isCompleted = false;
            }
         }
      }

      return bo;
   }
}
