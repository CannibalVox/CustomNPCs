package foxz.command;

import foxz.commandhelper.ChMcLogger;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.OpOnly;
import foxz.commandhelper.permissions.ParamCheck;
import foxz.commandhelper.permissions.PlayerOnly;
import foxz.utils.Utils;
import java.util.Iterator;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;

@Command(
   name = "clone",
   desc = "Clone operation (server side)"
)
public class CmdClone extends ChMcLogger {

   public CmdClone(Object sender) {
      super(sender);
   }

   @SubCommand(
      desc = "Add NPC(s) to clone storage",
      usage = "<npc> <tab> [clonedname]",
      permissions = {OpOnly.class, PlayerOnly.class, ParamCheck.class}
   )
   public Boolean add(String[] args) {
      EntityPlayerMP player = (EntityPlayerMP)super.pcParam;
      int tab = 0;

      try {
         tab = Integer.parseInt(args[1]);
      } catch (NumberFormatException var9) {
         ;
      }

      List list = Utils.getNearbeEntityFromPlayer(EntityNPCInterface.class, player, 80);
      Iterator var5 = list.iterator();

      EntityNPCInterface npc;
      do {
         if(!var5.hasNext()) {
            return Boolean.valueOf(true);
         }

         npc = (EntityNPCInterface)var5.next();
      } while(!npc.display.name.equalsIgnoreCase(args[0]));

      String name = npc.display.name;
      if(args.length > 2) {
         name = args[2];
      }

      NBTTagCompound compound = new NBTTagCompound();
      if(!npc.writeToNBTOptional(compound)) {
         return Boolean.valueOf(false);
      } else {
         ServerCloneController.Instance.addClone(compound, name, tab);
         return Boolean.valueOf(true);
      }
   }

   @SubCommand(
      desc = "List NPC from clone storage",
      usage = "<tab>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean list(String[] args) {
      this.sendmessage("--- Stored NPCs --- (server side)");
      int tab = 0;

      try {
         tab = Integer.parseInt(args[0]);
      } catch (NumberFormatException var5) {
         ;
      }

      Iterator ex = ServerCloneController.Instance.getClones(tab).iterator();

      while(ex.hasNext()) {
         String name = (String)ex.next();
         this.sendmessage(name);
      }

      this.sendmessage("------------------------------------");
      return Boolean.valueOf(true);
   }

   @SubCommand(
      desc = "Remove NPC from clone storage",
      usage = "<name> <tab>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean del(String[] args) {
      String nametodel = args[0];
      int tab = 0;

      try {
         tab = Integer.parseInt(args[1]);
      } catch (NumberFormatException var7) {
         ;
      }

      boolean deleted = false;
      Iterator var5 = ServerCloneController.Instance.getClones(tab).iterator();

      while(var5.hasNext()) {
         String name = (String)var5.next();
         if(nametodel.equalsIgnoreCase(name)) {
            ServerCloneController.Instance.removeClone(name, tab);
            deleted = true;
            break;
         }
      }

      if(!ServerCloneController.Instance.removeClone(nametodel, tab)) {
         this.sendmessage(String.format("Npc \'%s\' wasn\'t found", new Object[]{nametodel}));
         return Boolean.valueOf(false);
      } else {
         return Boolean.valueOf(true);
      }
   }

   @SubCommand(
      desc = "Spawn cloned NPC",
      usage = "<name> <tab> [[world:]x,y,z]] [newname]",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public boolean spawn(String[] args) {
      String name = args[0].replaceAll("%", " ");
      int tab = 0;

      try {
         tab = Integer.parseInt(args[1]);
      } catch (NumberFormatException var17) {
         ;
      }

      String newname = null;
      NBTTagCompound compound = ServerCloneController.Instance.getCloneData(super.pcParam, name, tab);
      if(compound == null) {
         this.sendmessage("Unknown npc");
         return false;
      } else {
         World world = super.pcParam.getEntityWorld();
         double posX = (double)super.pcParam.getCommandSenderPosition().posX;
         double posY = (double)super.pcParam.getCommandSenderPosition().posY;
         double posZ = (double)super.pcParam.getCommandSenderPosition().posZ;
         if(args.length > 2) {
            String entity = args[2];
            String[] npc;
            if(entity.contains(":")) {
               npc = entity.split(":");
               entity = npc[1];
               world = Utils.getWorld(npc[0]);
               if(world == null) {
                  this.sendmessage(String.format("\'%s\' is an unknown world", new Object[]{npc[0]}));
                  return false;
               }
            }

            if(entity.contains(",")) {
               npc = entity.split(",");
               if(npc.length != 3) {
                  this.sendmessage("Location need be x,y,z");
                  return false;
               }

               try {
                  posX = CommandBase.clamp_coord(super.pcParam, posX, npc[0]);
                  posY = CommandBase.clamp_double(super.pcParam, posY, npc[1].trim(), 0, 0);
                  posZ = CommandBase.clamp_coord(super.pcParam, posZ, npc[2]);
               } catch (NumberFormatException var16) {
                  this.sendmessage("Location should be in numbers");
                  return false;
               }

               if(args.length > 3) {
                  newname = args[3];
               }
            } else {
               newname = entity;
            }
         }

         if(posX == 0.0D && posY == 0.0D && posZ == 0.0D) {
            this.sendmessage("Location needed");
            return false;
         } else {
            Entity entity1 = EntityList.createEntityFromNBT(compound, world);
            entity1.setPosition(posX + 0.5D, posY + 1.0D, posZ + 0.5D);
            if(entity1 instanceof EntityNPCInterface) {
               EntityNPCInterface npc1 = (EntityNPCInterface)entity1;
               npc1.ai.startPos = new int[]{MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)};
               if(newname != null && !newname.isEmpty()) {
                  npc1.display.name = newname.replaceAll("%", " ");
               }
            }

            world.spawnEntityInWorld(entity1);
            return true;
         }
      }
   }
}
