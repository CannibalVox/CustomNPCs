package foxz.commandhelper.permissions;

import foxz.commandhelper.AbstractCommandHelper;
import foxz.commandhelper.permissions.AbstractPermission;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerOnly extends AbstractPermission {

   public String errorMsg() {
      return "Player Only";
   }

   public boolean delegate(AbstractCommandHelper parent, String[] args) {
      return parent.pcParam instanceof EntityPlayer;
   }
}
