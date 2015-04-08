package foxz.command;

import foxz.command.CmdClone;
import foxz.command.CmdConfig;
import foxz.command.CmdDialog;
import foxz.command.CmdFaction;
import foxz.command.CmdNpc;
import foxz.command.CmdQuest;
import foxz.command.CmdScript;
import foxz.commandhelper.ChMcLogger;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.OpOnly;
import foxz.commandhelper.permissions.ParamCheck;
import foxz.commandhelper.permissions.PlayerOnly;
import foxz.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;

@Command(
   name = "noppes",
   desc = "noppes root command",
   sub = {CmdClone.class, CmdScript.class, CmdQuest.class, CmdDialog.class, CmdConfig.class}
)
public class CmdNoppes extends ChMcLogger {

   public CmdFaction cmdfaction;
   public CmdNpc cmdnpc;
   public static Map SlayMap = new LinkedHashMap();


   public CmdNoppes(Object sender) {
      super(sender);
      this.cmdfaction = new CmdFaction(super.ctorParm);
      this.cmdnpc = new CmdNpc(super.ctorParm);
      SlayMap.clear();
      SlayMap.put("all", EntityLivingBase.class);
      SlayMap.put("mobs", EntityMob.class);
      SlayMap.put("animals", EntityAnimal.class);
      SlayMap.put("items", EntityItem.class);
      SlayMap.put("xporbs", EntityXPOrb.class);
      HashMap list = new HashMap(EntityList.stringToClassMapping);
      Iterator var3 = list.keySet().iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Class cls = (Class)list.get(name);
         if(!EntityNPCInterface.class.isAssignableFrom(cls) && EntityLivingBase.class.isAssignableFrom(cls)) {
            SlayMap.put(name.toLowerCase(), list.get(name));
         }
      }

      SlayMap.remove("monster");
      SlayMap.remove("mob");
   }

   @SubCommand(
      name = "faction",
      desc = "Faction operations",
      usage = "<player> <faction> <command>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public Boolean faction(String[] args) {
      String playername = args[0];
      String factionname = args[1];
      this.cmdfaction.data = this.getPlayersData(playername);
      if(this.cmdfaction.data.isEmpty()) {
         this.sendmessage(String.format("Unknow player \'%s\'", new Object[]{playername}));
         return Boolean.valueOf(false);
      } else {
         this.cmdfaction.selectedFaction = FactionController.getInstance().getFactionFromName(factionname);
         if(this.cmdfaction.selectedFaction == null) {
            this.sendmessage(String.format("Unknow facion \'%s", new Object[]{factionname}));
            return Boolean.valueOf(false);
         } else {
            args = (String[])Arrays.copyOfRange(args, 2, args.length);
            this.cmdfaction.processCommand(super.pcParam, args);
            Iterator var4 = this.cmdfaction.data.iterator();

            while(var4.hasNext()) {
               PlayerData playerdata = (PlayerData)var4.next();
               playerdata.saveNBTData((NBTTagCompound)null);
            }

            return Boolean.valueOf(true);
         }
      }
   }

   @SubCommand(
      desc = "NPC manipulations",
      usage = "<npc> <command>",
      permissions = {OpOnly.class, ParamCheck.class}
   )
   public boolean npc(String[] args) {
      String npcname = args[0].replace("%", " ");
      args = (String[])Arrays.copyOfRange(args, 1, args.length);
      if(args[0].equalsIgnoreCase("create")) {
         this.cmdnpc.processCommand(super.pcParam, new String[]{args[0], npcname});
         return true;
      } else {
         EntityPlayerMP player = (EntityPlayerMP)super.pcParam;
         List list = Utils.getNearbeEntityFromPlayer(EntityNPCInterface.class, player, 80);
         Iterator var5 = list.iterator();

         EntityNPCInterface npc;
         do {
            if(!var5.hasNext()) {
               this.sendmessage(String.format("Npc \'%s\' was not found", new Object[]{npcname}));
               return true;
            }

            npc = (EntityNPCInterface)var5.next();
         } while(!npc.display.name.equalsIgnoreCase(npcname));

         this.cmdnpc.selectedNpc = npc;
         this.cmdnpc.processCommand(super.pcParam, args);
         this.cmdnpc.selectedNpc = null;
         return true;
      }
   }

   @SubCommand(
      name = "slay",
      desc = "Kills given entity within range. Also has all, mobs, animal options. Can have multiple types",
      usage = "<type>.. [range]",
      permissions = {PlayerOnly.class, OpOnly.class, ParamCheck.class}
   )
   public Boolean slay(String[] args) {
      EntityPlayerMP player = (EntityPlayerMP)super.pcParam;
      ArrayList toDelete = new ArrayList();
      String[] count = args;
      int range = args.length;

      for(int box = 0; box < range; ++box) {
         String list = count[box];
         Class cls = (Class)SlayMap.get(list.toLowerCase());
         if(cls != null) {
            toDelete.add(cls);
         }

         if(list.equals("mobs")) {
            toDelete.add(EntityGhast.class);
            toDelete.add(EntityDragon.class);
         }
      }

      int var11 = 0;
      range = 120;

      try {
         range = Integer.parseInt(args[args.length - 1]);
      } catch (NumberFormatException var10) {
         ;
      }

      AxisAlignedBB var12 = player.boundingBox.expand((double)range, (double)range, (double)range);
      List var13 = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var12);
      Iterator var14 = var13.iterator();

      Entity entity;
      while(var14.hasNext()) {
         entity = (Entity)var14.next();
         if(!(entity instanceof EntityPlayer) && (!(entity instanceof EntityTameable) || !((EntityTameable)entity).isTamed()) && !(entity instanceof EntityNPCInterface) && this.delete(entity, toDelete)) {
            ++var11;
         }
      }

      if(toDelete.contains(EntityXPOrb.class)) {
         var13 = player.worldObj.getEntitiesWithinAABB(EntityXPOrb.class, var12);

         for(var14 = var13.iterator(); var14.hasNext(); ++var11) {
            entity = (Entity)var14.next();
            entity.isDead = true;
         }
      }

      if(toDelete.contains(EntityItem.class)) {
         var13 = player.worldObj.getEntitiesWithinAABB(EntityItem.class, var12);

         for(var14 = var13.iterator(); var14.hasNext(); ++var11) {
            entity = (Entity)var14.next();
            entity.isDead = true;
         }
      }

      player.addChatMessage(new ChatComponentTranslation(var11 + " entities deleted", new Object[0]));
      return Boolean.valueOf(true);
   }

   private boolean delete(Entity entity, ArrayList toDelete) {
      Iterator var3 = toDelete.iterator();

      Class delete;
      do {
         if(!var3.hasNext()) {
            return false;
         }

         delete = (Class)var3.next();
      } while(delete == EntityAnimal.class && entity instanceof EntityHorse || !delete.isAssignableFrom(entity.getClass()));

      entity.isDead = true;
      return true;
   }

   public List addTabCompletion(ICommandSender par1, String[] args) {
      return args[0].equalsIgnoreCase("slay")?CommandBase.getListOfStringsMatchingLastWord(args, (String[])SlayMap.keySet().toArray(new String[SlayMap.size()])):(args[0].equalsIgnoreCase("npc") && args.length == 3?CommandBase.getListOfStringsMatchingLastWord(args, new String[]{"create", "home"}):(args[0].equalsIgnoreCase("faction") && args.length == 4?CommandBase.getListOfStringsMatchingLastWord(args, new String[]{"add", "subtract", "set", "reset", "drop"}):super.addTabCompletion(par1, args)));
   }

}
