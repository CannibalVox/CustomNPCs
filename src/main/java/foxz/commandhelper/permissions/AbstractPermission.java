package foxz.commandhelper.permissions;

import foxz.commandhelper.AbstractCommandHelper;

public abstract class AbstractPermission {

   public abstract String errorMsg();

   public abstract boolean delegate(AbstractCommandHelper var1, String[] var2);
}
