package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.Dialog;

public class DialogCategory {

   public int id = -1;
   public String title = "";
   public HashMap dialogs = new HashMap();


   public void readNBT(NBTTagCompound compound) {
      this.id = compound.getInteger("Slot");
      this.title = compound.getString("Title");
      NBTTagList dialogsList = compound.getTagList("Dialogs", 10);
      if(dialogsList != null) {
         for(int ii = 0; ii < dialogsList.tagCount(); ++ii) {
            Dialog dialog = new Dialog();
            dialog.category = this;
            NBTTagCompound comp = dialogsList.getCompoundTagAt(ii);
            dialog.readNBT(comp);
            dialog.id = comp.getInteger("DialogId");
            this.dialogs.put(Integer.valueOf(dialog.id), dialog);
         }
      }

   }

   public NBTTagCompound writeNBT(NBTTagCompound nbtfactions) {
      nbtfactions.setInteger("Slot", this.id);
      nbtfactions.setString("Title", this.title);
      NBTTagList dialogs = new NBTTagList();
      Iterator var3 = this.dialogs.values().iterator();

      while(var3.hasNext()) {
         Dialog dialog = (Dialog)var3.next();
         dialogs.appendTag(dialog.writeToNBT(new NBTTagCompound()));
      }

      nbtfactions.setTag("Dialogs", dialogs);
      return nbtfactions;
   }
}
