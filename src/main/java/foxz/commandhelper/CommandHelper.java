package foxz.commandhelper;

import java.util.List;
import net.minecraft.command.ICommandSender;

public abstract class CommandHelper {

   public CommandHelper.Helper commandHelper = new CommandHelper.Helper();


   public List addTabCompletion(ICommandSender par1, String[] args) {
      return null;
   }

   public class Helper {

      public String name;
      public String usage;
      public String desc;
      public boolean hasEmptyCall;


   }
}
