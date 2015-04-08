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

@Command(
   name = "dialog",
   desc = "dialog operations",
   usage = "help"
)
public class CmdDialog extends ChMcLogger {

   public CmdDialog(Object sender) {
      super(sender);
   }

   @SubCommand(
      desc = "force read",
      usage = "<player> <dialog>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public boolean read(String[] args) {
      String playername = args[0];

      int diagid;
      try {
         diagid = Integer.parseInt(args[1]);
      } catch (NumberFormatException var7) {
         this.sendmessage("DialogID must be an integer");
         return false;
      }

      List data = this.getPlayersData(playername);
      if(data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return false;
      } else {
         Iterator var5 = data.iterator();

         while(var5.hasNext()) {
            PlayerData playerdata = (PlayerData)var5.next();
            playerdata.dialogData.dialogsRead.add(Integer.valueOf(diagid));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         return true;
      }
   }

   @SubCommand(
      desc = "force unread dialog",
      usage = "<player> <dialog>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public boolean unread(String[] args) {
      String playername = args[0];

      int diagid;
      try {
         diagid = Integer.parseInt(args[1]);
      } catch (NumberFormatException var7) {
         this.sendmessage("DialogID must be an integer");
         return false;
      }

      List data = this.getPlayersData(playername);
      if(data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return false;
      } else {
         Iterator var5 = data.iterator();

         while(var5.hasNext()) {
            PlayerData playerdata = (PlayerData)var5.next();
            playerdata.dialogData.dialogsRead.remove(Integer.valueOf(diagid));
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         return true;
      }
   }

   @SubCommand(
      desc = "reload dialogs from disk",
      permissions = {OpOnly.class}
   )
   public boolean reload(String[] args) {
      new DialogController();
      return true;
   }
}
