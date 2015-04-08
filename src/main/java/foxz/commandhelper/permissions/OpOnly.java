package foxz.commandhelper.permissions;

import foxz.commandhelper.AbstractCommandHelper;
import foxz.commandhelper.permissions.AbstractPermission;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class OpOnly extends AbstractPermission {

   public String errorMsg() {
      return "Op Only";
   }

   public boolean delegate(AbstractCommandHelper parent, String[] args) {
      return !(parent.pcParam instanceof EntityPlayer)?true:MinecraftServer.getServer().getConfigurationManager().canSendCommands(((EntityPlayer)parent.pcParam).getGameProfile());
   }
}
