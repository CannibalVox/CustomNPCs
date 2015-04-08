package foxz.commandhelper.permissions;

import foxz.commandhelper.AbstractCommandHelper;
import foxz.commandhelper.permissions.AbstractPermission;

public class ParamCheck extends AbstractPermission {

   String err;


   public String errorMsg() {
      return this.err;
   }

   public boolean delegate(AbstractCommandHelper parent, String[] args) {
      String[] np = parent.currentHelper.usage.split(" ");
      int countRequired = 0;
      String[] var5 = np;
      int var6 = np.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String command = var5[var7];
         if(command.startsWith("<")) {
            ++countRequired;
         }
      }

      if(args.length < countRequired) {
         this.err = np[args.length] + " missing";
         return false;
      } else {
         return true;
      }
   }
}
