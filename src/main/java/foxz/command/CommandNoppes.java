package foxz.command;

import foxz.command.CmdNoppes;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandNoppes extends CommandBase {

   public CmdNoppes noppes = new CmdNoppes(this);


   public String getCommandName() {
      return this.noppes.commandHelper.name;
   }

   public String getCommandUsage(ICommandSender var1) {
      return this.noppes.commandHelper.usage;
   }

   public void processCommand(ICommandSender var1, String[] var2) {
      this.noppes.processCommand(var1, var2);
   }

   public List addTabCompletionOptions(ICommandSender par1, String[] par2) {
      return this.noppes.addTabCompletion(par1, par2);
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }
}
