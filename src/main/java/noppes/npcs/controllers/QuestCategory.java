package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.Quest;

public class QuestCategory {

   public HashMap quests = new HashMap();
   public int id = -1;
   public String title = "";


   public void readNBT(NBTTagCompound nbttagcompound) {
      this.id = nbttagcompound.getInteger("Slot");
      this.title = nbttagcompound.getString("Title");
      NBTTagList dialogsList = nbttagcompound.getTagList("Dialogs", 10);
      if(dialogsList != null) {
         for(int ii = 0; ii < dialogsList.tagCount(); ++ii) {
            NBTTagCompound nbttagcompound2 = dialogsList.getCompoundTagAt(ii);
            Quest quest = new Quest();
            quest.readNBT(nbttagcompound2);
            quest.category = this;
            this.quests.put(Integer.valueOf(quest.id), quest);
         }
      }

   }

   public NBTTagCompound writeNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("Slot", this.id);
      nbttagcompound.setString("Title", this.title);
      NBTTagList dialogs = new NBTTagList();
      Iterator var3 = this.quests.keySet().iterator();

      while(var3.hasNext()) {
         int dialogId = ((Integer)var3.next()).intValue();
         Quest quest = (Quest)this.quests.get(Integer.valueOf(dialogId));
         dialogs.appendTag(quest.writeToNBT(new NBTTagCompound()));
      }

      nbttagcompound.setTag("Dialogs", dialogs);
      return nbttagcompound;
   }
}
