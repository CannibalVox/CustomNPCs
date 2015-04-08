package foxz.command;

import foxz.commandhelper.ChMcLogger;
import foxz.commandhelper.annotations.Command;
import foxz.commandhelper.annotations.SubCommand;
import foxz.commandhelper.permissions.OpOnly;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockVine;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.ChunkController;

@Command(
   name = "config",
   desc = "Some config things you can set ",
   usage = ""
)
public class CmdConfig extends ChMcLogger {

   public CmdConfig(Object sender) {
      super(sender);
   }

   @SubCommand(
      desc = "Disable/Enable the natural leaves decay",
      usage = "<true/false>",
      permissions = {OpOnly.class},
      hasEmptyCall = true
   )
   public boolean leavesdecay(String[] args) {
      if(args.length == 0) {
         this.sendmessage("LeavesDecay: " + CustomNpcs.LeavesDecayEnabled);
      } else {
         CustomNpcs.LeavesDecayEnabled = Boolean.parseBoolean(args[0]);
         CustomNpcs.Config.updateConfig();
         Set names = Block.blockRegistry.getKeys();
         Iterator var3 = names.iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            Block block = (Block)Block.blockRegistry.getObject(name);
            if(block instanceof BlockLeavesBase) {
               block.setTickRandomly(CustomNpcs.LeavesDecayEnabled);
            }
         }

         this.sendmessage("LeavesDecay is now " + CustomNpcs.LeavesDecayEnabled);
      }

      return true;
   }

   @SubCommand(
      desc = "Disable/Enable the vines growing",
      usage = "<true/false>",
      permissions = {OpOnly.class},
      hasEmptyCall = true
   )
   public boolean vinegrowth(String[] args) {
      if(args.length == 0) {
         this.sendmessage("VineGrowth: " + CustomNpcs.VineGrowthEnabled);
      } else {
         CustomNpcs.VineGrowthEnabled = Boolean.parseBoolean(args[0]);
         CustomNpcs.Config.updateConfig();
         Set names = Block.blockRegistry.getKeys();
         Iterator var3 = names.iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            Block block = (Block)Block.blockRegistry.getObject(name);
            if(block instanceof BlockVine) {
               block.setTickRandomly(CustomNpcs.VineGrowthEnabled);
            }
         }

         this.sendmessage("VineGrowth is now " + CustomNpcs.VineGrowthEnabled);
      }

      return true;
   }

   @SubCommand(
      desc = "Disable/Enable the ice melting",
      usage = "<true/false>",
      permissions = {OpOnly.class},
      hasEmptyCall = true
   )
   public boolean icemelts(String[] args) {
      if(args.length == 0) {
         this.sendmessage("IceMelts: " + CustomNpcs.IceMeltsEnabled);
      } else {
         CustomNpcs.IceMeltsEnabled = Boolean.parseBoolean(args[0]);
         CustomNpcs.Config.updateConfig();
         Set names = Block.blockRegistry.getKeys();
         Iterator var3 = names.iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            Block block = (Block)Block.blockRegistry.getObject(name);
            if(block instanceof BlockIce) {
               block.setTickRandomly(CustomNpcs.IceMeltsEnabled);
            }
         }

         this.sendmessage("IceMelts is now " + CustomNpcs.IceMeltsEnabled);
      }

      return true;
   }

   @SubCommand(
      desc = "Disable/Enable guns shooting",
      usage = "<true/false>",
      permissions = {OpOnly.class},
      hasEmptyCall = true
   )
   public boolean guns(String[] args) {
      if(args.length == 0) {
         this.sendmessage("GunsEnabled: " + CustomNpcs.GunsEnabled);
      } else {
         CustomNpcs.GunsEnabled = Boolean.parseBoolean(args[0]);
         CustomNpcs.Config.updateConfig();
         this.sendmessage("GunsEnabled is now " + CustomNpcs.GunsEnabled);
      }

      return true;
   }

   @SubCommand(
      desc = "Freezes/Unfreezes npcs",
      usage = "<true/false>",
      permissions = {OpOnly.class},
      hasEmptyCall = true
   )
   public boolean freezenpcs(String[] args) {
      if(args.length == 0) {
         this.sendmessage("Frozen NPCs: " + CustomNpcs.FreezeNPCs);
      } else {
         CustomNpcs.FreezeNPCs = Boolean.parseBoolean(args[0]);
         this.sendmessage("FrozenNPCs is now " + CustomNpcs.FreezeNPCs);
      }

      return true;
   }

   @SubCommand(
      desc = "Set how many active chunkloaders you can have",
      usage = "<number>",
      permissions = {OpOnly.class},
      hasEmptyCall = true
   )
   public boolean chunkloaders(String[] args) {
      if(args.length == 0) {
         this.sendmessage("ChunkLoaders: " + ChunkController.instance.size() + "/" + CustomNpcs.ChuckLoaders);
      } else {
         try {
            CustomNpcs.ChuckLoaders = Integer.parseInt(args[0]);
         } catch (NumberFormatException var3) {
            this.sendmessage("Didnt get a number");
            return false;
         }

         CustomNpcs.Config.updateConfig();
         int size = ChunkController.instance.size();
         if(size > CustomNpcs.ChuckLoaders) {
            ChunkController.instance.unload(size - CustomNpcs.ChuckLoaders);
            this.sendmessage(size - CustomNpcs.ChuckLoaders + " chunksloaders unloaded");
         }

         this.sendmessage("ChunkLoaders: " + ChunkController.instance.size() + "/" + CustomNpcs.ChuckLoaders);
      }

      return true;
   }
}
