package noppes.npcs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.Quest;

public class QuestLogData {

   public HashMap categories = new HashMap();
   public String selectedQuest = "";
   public String selectedCategory = "";
   public HashMap questText = new HashMap();
   public HashMap questStatus = new HashMap();
   public HashMap finish = new HashMap();


   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setTag("Categories", NBTTags.nbtVectorMap(this.categories));
      compound.setTag("Logs", NBTTags.nbtStringStringMap(this.questText));
      compound.setTag("Status", NBTTags.nbtVectorMap(this.questStatus));
      compound.setTag("QuestFinisher", NBTTags.nbtStringStringMap(this.finish));
      return compound;
   }

   public void readNBT(NBTTagCompound compound) {
      this.categories = NBTTags.getVectorMap(compound.getTagList("Categories", 10));
      this.questText = NBTTags.getStringStringMap(compound.getTagList("Logs", 10));
      this.questStatus = NBTTags.getVectorMap(compound.getTagList("Status", 10));
      this.finish = NBTTags.getStringStringMap(compound.getTagList("QuestFinisher", 10));
   }

   public void setData(EntityPlayer player) {
      Iterator var2 = PlayerQuestController.getActiveQuests(player).iterator();

      while(var2.hasNext()) {
         Quest quest = (Quest)var2.next();
         String category = quest.category.title;
         if(!this.categories.containsKey(category)) {
            this.categories.put(category, new Vector());
         }

         Vector list = (Vector)this.categories.get(category);
         list.add(quest.title);
         this.questText.put(category + ":" + quest.title, quest.logText);
         this.questStatus.put(category + ":" + quest.title, quest.questInterface.getQuestLogStatus(player));
         if(quest.completion == EnumQuestCompletion.Npc && quest.questInterface.isCompleted(player)) {
            this.finish.put(category + ":" + quest.title, quest.completerNpc);
         }
      }

   }

   public boolean hasSelectedQuest() {
      return !this.selectedQuest.isEmpty();
   }

   public String getQuestText() {
      return (String)this.questText.get(this.selectedCategory + ":" + this.selectedQuest);
   }

   public Vector getQuestStatus() {
      return (Vector)this.questStatus.get(this.selectedCategory + ":" + this.selectedQuest);
   }

   public String getComplete() {
      return (String)this.finish.get(this.selectedCategory + ":" + this.selectedQuest);
   }
}
