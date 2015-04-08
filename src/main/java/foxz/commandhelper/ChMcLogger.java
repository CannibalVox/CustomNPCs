package foxz.commandhelper;

import foxz.commandhelper.AbstractCommandHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ChMcLogger extends AbstractCommandHelper {

   public ChMcLogger(Object sender) {
      super(sender);
   }

   public void sendmessage(String msg) {
      ICommandSender sender = super.pcParam;
      sender.addChatMessage(new ChatComponentText(msg));
   }

   public void help(String cmd, String desc, String usa) {
      if(usa.isEmpty()) {
         this.sendmessage(String.format("%s = %s", new Object[]{cmd, desc}));
      } else {
         this.sendmessage(String.format("%s %s = %s", new Object[]{cmd, usa, desc}));
      }

   }

   public void cmdError(String cmd) {
      this.sendmessage(String.format("Unknow command \'%s\'", new Object[]{cmd}));
   }

   public void error(String err) {
      this.sendmessage(String.format("Error: %s", new Object[]{err}));
   }
}
