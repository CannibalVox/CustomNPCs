package foxz.commandhelper;

import foxz.commandhelper.CommandHelper;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.AbstractPermission;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;

public abstract class AbstractCommandHelper extends CommandHelper {

   public Object ctorParm;
   public ICommandSender pcParam;
   public Object xParam;
   public AbstractCommandHelper parentCmdHelper;
   public AbstractCommandHelper rootCmdHelper;
   public Map commands = new HashMap();
   public CommandHelper.Helper currentHelper;


   public AbstractCommandHelper(Object sender) {
      this.ctorParm = sender;
      this.ctor();
   }

   public void ctor() {
      super.commandHelper.name = ((Command)this.getClass().getAnnotation(Command.class)).name();
      super.commandHelper.usage = ((Command)this.getClass().getAnnotation(Command.class)).usage();
      super.commandHelper.desc = ((Command)this.getClass().getAnnotation(Command.class)).desc();
      Class[] var1 = ((Command)this.getClass().getAnnotation(Command.class)).sub();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         Class m = var1[var3];

         try {
            String sc = ((Command)m.getAnnotation(Command.class)).name().toUpperCase();
            Constructor name = m.getConstructor(new Class[]{Object.class});
            name.setAccessible(true);
            AbstractCommandHelper sc1 = (AbstractCommandHelper)name.newInstance(new Object[]{this.ctorParm});
            this.commands.put(sc, sc1);
         } catch (Exception var8) {
            Logger.getLogger(AbstractCommandHelper.class.getName()).log(Level.SEVERE, (String)null, var8);
         }
      }

      Method[] var9 = this.getClass().getDeclaredMethods();
      var2 = var9.length;

      for(var3 = 0; var3 < var2; ++var3) {
         Method var10 = var9[var3];
         SubCommand var11 = (SubCommand)var10.getAnnotation(SubCommand.class);
         if(var11 != null) {
            String var12 = var11.name();
            if(var12.equals("")) {
               var12 = var10.getName();
            }

            this.commands.put(var12.toUpperCase(), new AbstractCommandHelper.MethodSubCmd(this, var10));
         }
      }

   }

   public abstract void help(String var1, String var2, String var3);

   public abstract void cmdError(String var1);

   public abstract void error(String var1);

   public void allHelp() {
      Iterator var1 = this.commands.values().iterator();

      while(var1.hasNext()) {
         CommandHelper cur = (CommandHelper)var1.next();
         this.help(cur.commandHelper.name, cur.commandHelper.desc, "");
      }

   }

   public Boolean processCommand(ICommandSender param, String[] args) {
      this.pcParam = param;
      if(this.parentCmdHelper == null) {
         this.rootCmdHelper = this;
      }

      if(args.length == 0) {
         this.allHelp();
         return Boolean.valueOf(true);
      } else {
         String cmd = args[0].toUpperCase();
         args = (String[])Arrays.copyOfRange(args, 1, args.length);
         if((cmd.equals("HELP") || args.length == 0) && this.doHelp(param, args, cmd)) {
            return Boolean.valueOf(true);
         } else {
            CommandHelper ch = (CommandHelper)this.commands.get(cmd);
            if(ch == null) {
               this.cmdError(cmd);
               return Boolean.valueOf(false);
            } else if(ch instanceof AbstractCommandHelper) {
               AbstractCommandHelper m1 = (AbstractCommandHelper)ch;
               m1.parentCmdHelper = this;
               m1.rootCmdHelper = this.rootCmdHelper;
               return m1.processCommand(param, args);
            } else if(ch instanceof AbstractCommandHelper.MethodSubCmd) {
               AbstractCommandHelper.MethodSubCmd m = (AbstractCommandHelper.MethodSubCmd)ch;
               m.method.setAccessible(true);
               this.currentHelper = ch.commandHelper;

               try {
                  Iterator ex = m.permissions.iterator();

                  AbstractPermission p;
                  do {
                     if(!ex.hasNext()) {
                        return (Boolean)m.method.invoke(this, new Object[]{args});
                     }

                     p = (AbstractPermission)ex.next();
                  } while(p.delegate(this, args));

                  this.error(p.errorMsg());
                  return Boolean.valueOf(false);
               } catch (Exception var8) {
                  Logger.getLogger(AbstractCommandHelper.class.getName()).log(Level.SEVERE, m.commandHelper.name, var8);
                  return Boolean.valueOf(true);
               }
            } else {
               this.cmdError(cmd);
               return Boolean.valueOf(false);
            }
         }
      }
   }

   private boolean doHelp(ICommandSender param, String[] args, String cmd) {
      boolean isHelp = cmd.equals("HELP");
      if(args.length > 0) {
         cmd = args[0];
      }

      CommandHelper ch = (CommandHelper)this.commands.get(cmd.toUpperCase());
      if(ch != null) {
         if(ch.commandHelper.hasEmptyCall && !isHelp) {
            return false;
         }

         if(ch instanceof AbstractCommandHelper) {
            ((AbstractCommandHelper)ch).pcParam = param;
            ((AbstractCommandHelper)ch).allHelp();
         } else {
            if(ch instanceof AbstractCommandHelper.MethodSubCmd && ((AbstractCommandHelper.MethodSubCmd)ch).commandHelper.usage.isEmpty()) {
               return false;
            }

            this.help(ch.commandHelper.name, ch.commandHelper.desc, ch.commandHelper.usage);
         }
      } else {
         this.allHelp();
      }

      return true;
   }

   public List addTabCompletion(ICommandSender par1, String[] args) {
      if(args.length > 1) {
         CommandHelper ch1 = (CommandHelper)this.commands.get(args[0].toUpperCase());
         if(ch1 == null) {
            return null;
         } else {
            args = (String[])Arrays.copyOfRange(args, 1, args.length);
            this.currentHelper = ch1.commandHelper;
            return ch1.addTabCompletion(par1, args);
         }
      } else {
         ArrayList ch = new ArrayList();
         Iterator var4 = this.commands.keySet().iterator();

         while(var4.hasNext()) {
            String command = (String)var4.next();
            ch.add(command.toLowerCase());
         }

         ch.add("help");
         return CommandBase.getListOfStringsMatchingLastWord(args, (String[])ch.toArray(new String[ch.size()]));
      }
   }

   public List getPlayersData(String username) {
      ArrayList list = new ArrayList();
      EntityPlayerMP[] players = PlayerSelector.matchPlayers(this.pcParam, username);
      if(players != null && players.length != 0) {
         EntityPlayerMP[] var8 = players;
         int var5 = players.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EntityPlayerMP player = var8[var6];
            list.add(PlayerDataController.instance.getPlayerData(player));
         }
      } else {
         PlayerData data = PlayerDataController.instance.getDataFromUsername(username);
         if(data != null) {
            list.add(data);
         }
      }

      return list;
   }

   protected class MethodSubCmd extends CommandHelper {

      public List permissions = new ArrayList();
      public Method method;


      public MethodSubCmd(AbstractCommandHelper ch, Method m) {
         SubCommand s = (SubCommand)m.getAnnotation(SubCommand.class);
         super.commandHelper.name = s.name();
         if(super.commandHelper.name.equals("")) {
            super.commandHelper.name = m.getName();
         }

         super.commandHelper.usage = s.usage();
         super.commandHelper.desc = s.desc();
         super.commandHelper.hasEmptyCall = s.hasEmptyCall();
         this.method = m;
         Class[] var5 = s.permissions();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class c = var5[var7];

            try {
               Constructor ex = c.getDeclaredConstructor(new Class[0]);
               ex.setAccessible(true);
               AbstractPermission i = (AbstractPermission)ex.newInstance(new Object[0]);
               this.permissions.add(i);
            } catch (Exception var11) {
               Logger.getLogger(AbstractCommandHelper.class.getName()).log(Level.SEVERE, (String)null, var11);
            }
         }

      }

      public List addTabCompletion(ICommandSender par1, String[] args) {
         String[] np = AbstractCommandHelper.this.currentHelper.usage.split(" ");
         if(np.length < args.length) {
            return null;
         } else {
            String parameter = np[args.length - 1];
            return parameter.equals("<player>")?CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()):null;
         }
      }
   }
}
