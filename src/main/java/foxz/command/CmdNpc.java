package foxz.command;

import foxz.commandhelper.ChMcLogger;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.OpOnly;
import foxz.commandhelper.permissions.PlayerOnly;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

@Command(
   name = "npc",
   desc = "NPC manipulation",
   usage = "<name> <command>"
)
public class CmdNpc extends ChMcLogger {

   public EntityNPCInterface selectedNpc;


   CmdNpc(Object ctorParm) {
      super(ctorParm);
   }

   @SubCommand(
      desc = "Set Home (respawn place)",
      usage = ""
   )
   public boolean home(String[] args) {
      EntityPlayer player = (EntityPlayer)super.pcParam;
      int posX = MathHelper.floor_double(player.posX);
      int posY = MathHelper.floor_double(player.posY);
      int posZ = MathHelper.floor_double(player.posZ);
      this.selectedNpc.ai.startPos = new int[]{posX, posY, posZ};
      return true;
   }

   @SubCommand(
      desc = "Creates an NPC",
      usage = "[name]",
      permissions = {PlayerOnly.class, OpOnly.class}
   )
   public Boolean create(String[] args) {
      EntityPlayerMP player = (EntityPlayerMP)super.pcParam;
      World pw = player.getEntityWorld();
      EntityCustomNpc npc = new EntityCustomNpc(pw);
      if(args.length > 0) {
         npc.display.name = args[0];
      }

      npc.setPositionAndRotation(player.posX, player.posY, player.posZ, player.cameraYaw, player.cameraPitch);
      npc.ai.startPos = new int[]{MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)};
      pw.spawnEntityInWorld(npc);
      npc.setHealth(npc.getMaxHealth());
      NoppesUtilServer.sendOpenGui(player, EnumGuiType.MainMenuDisplay, npc);
      return Boolean.valueOf(true);
   }

   public List addTabCompletion(ICommandSender par1, String[] args) {
      return super.addTabCompletion(par1, args);
   }
}
