package noppes.npcs.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.PlayerMail;

public class PlayerMailData {

   public ArrayList playermail = new ArrayList();


   public void loadNBTData(NBTTagCompound compound) {
      ArrayList newmail = new ArrayList();
      NBTTagList list = compound.getTagList("MailData", 10);
      if(list != null) {
         for(int i = 0; i < list.tagCount(); ++i) {
            PlayerMail mail = new PlayerMail();
            mail.readNBT(list.getCompoundTagAt(i));
            newmail.add(mail);
         }

         this.playermail = newmail;
      }
   }

   public NBTTagCompound saveNBTData(NBTTagCompound compound) {
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.playermail.iterator();

      while(var3.hasNext()) {
         PlayerMail mail = (PlayerMail)var3.next();
         list.appendTag(mail.writeNBT());
      }

      compound.setTag("MailData", list);
      return compound;
   }

   public boolean hasMail() {
      Iterator var1 = this.playermail.iterator();

      PlayerMail mail;
      do {
         if(!var1.hasNext()) {
            return false;
         }

         mail = (PlayerMail)var1.next();
      } while(mail.beenRead);

      return true;
   }
}
