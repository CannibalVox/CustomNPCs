package noppes.npcs.controllers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobSpawner;
import org.apache.logging.log4j.LogManager;

public class PixelmonHelper {

   public static List getPixelmonList() {
      ArrayList list = new ArrayList();
      if(!CustomNpcs.PixelMonEnabled) {
         return list;
      } else {
         try {
            Class e = Class.forName("com.pixelmonmod.pixelmon.enums.EnumPokemon");
            Object[] array = e.getEnumConstants();
            Object[] var3 = array;
            int var4 = array.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Object ob = var3[var5];
               list.add(ob.toString());
            }
         } catch (Exception var7) {
            LogManager.getLogger().error("getPixelmonList", var7);
         }

         return list;
      }
   }

   public static boolean isPixelmon(EntityLivingBase entity) {
      return !CustomNpcs.PixelMonEnabled?false:EntityList.getEntityString(entity).equals("pixelmon.Pixelmon");
   }

   public static void setName(EntityLivingBase entity, String name) {
      if(CustomNpcs.PixelMonEnabled && isPixelmon(entity)) {
         try {
            Method e = entity.getClass().getMethod("init", new Class[]{String.class});
            e.invoke(entity, new Object[]{name});
            Class c = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.Entity2HasModel");
            e = c.getDeclaredMethod("loadModel", new Class[0]);
            e.setAccessible(true);
            e.invoke(entity, new Object[0]);
         } catch (Exception var4) {
            LogManager.getLogger().error("setName", var4);
         }

      }
   }

   public static String getName(EntityLivingBase entity) {
      if(CustomNpcs.PixelMonEnabled && isPixelmon(entity)) {
         try {
            Method e = entity.getClass().getMethod("getName", new Class[0]);
            return e.invoke(entity, new Object[0]).toString();
         } catch (Exception var2) {
            LogManager.getLogger().error("getName", var2);
            return "";
         }
      } else {
         return "";
      }
   }

   public static void debug(EntityLivingBase entity) {
      if(CustomNpcs.PixelMonEnabled && isPixelmon(entity)) {
         try {
            Method e = entity.getClass().getMethod("getModel", new Class[0]);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText((String)e.invoke(entity, new Object[0])));
         } catch (Exception var2) {
            var2.printStackTrace();
         }

      }
   }

   public static boolean isTrainer(EntityLivingBase entity) {
      return !CustomNpcs.PixelMonEnabled?false:EntityList.getEntityString(entity).equals("pixelmon.Trainer");
   }

   public static boolean isBattling(EntityPlayerMP player) {
      if(!CustomNpcs.PixelMonEnabled) {
         return false;
      } else {
         try {
            Class e = Class.forName("com.pixelmonmod.pixelmon.battles.BattleRegistry");
            Method m = e.getMethod("getBattle", new Class[]{EntityPlayer.class});
            return m.invoke((Object)null, new Object[]{player}) == null;
         } catch (Exception var3) {
            LogManager.getLogger().error("canBattle", var3);
            return false;
         }
      }
   }

   public static boolean isBattling(EntityLivingBase trainer) {
      if(CustomNpcs.PixelMonEnabled && isTrainer(trainer)) {
         try {
            Field e = trainer.getClass().getField("battleController");
            return e.get(trainer) != null;
         } catch (Exception var2) {
            LogManager.getLogger().error("canBattle", var2);
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean canBattle(EntityPlayerMP player, EntityNPCInterface npc) {
      if(CustomNpcs.PixelMonEnabled && npc.advanced.job == EnumJobType.Spawner && !isBattling(player)) {
         try {
            JobSpawner e = (JobSpawner)npc.jobInterface;
            if(e.isOnCooldown(player.getCommandSenderName())) {
               return false;
            } else {
               Class c = Class.forName("com.pixelmonmod.pixelmon.storage.PixelmonStorage");
               Field f = c.getField("PokeballManager");
               Object ob = f.get((Object)null);
               c = Class.forName("com.pixelmonmod.pixelmon.storage.PokeballManager");
               Method m = c.getMethod("getPlayerStorage", new Class[]{EntityPlayerMP.class});
               ob = m.invoke(ob, new Object[]{player});
               c = Class.forName("com.pixelmonmod.pixelmon.storage.PlayerStorage");
               m = c.getMethod("countAblePokemon", new Class[0]);
               return ((Integer)m.invoke(ob, new Object[0])).intValue() != 0;
            }
         } catch (Exception var7) {
            LogManager.getLogger().error("canBattle", var7);
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean startBattle(EntityPlayerMP player, EntityLivingBase trainer) {
      if(!CustomNpcs.PixelMonEnabled) {
         return false;
      } else {
         try {
            Class e = Class.forName("com.pixelmonmod.pixelmon.storage.PixelmonStorage");
            Field f = e.getField("PokeballManager");
            Object ob = f.get((Object)null);
            e = Class.forName("com.pixelmonmod.pixelmon.storage.PokeballManager");
            Method m = e.getMethod("getPlayerStorage", new Class[]{EntityPlayerMP.class});
            ob = m.invoke(ob, new Object[]{player});
            e = Class.forName("com.pixelmonmod.pixelmon.storage.PlayerStorage");
            m = e.getMethod("getFirstAblePokemon", new Class[]{World.class});
            Entity pixelmon = (Entity)m.invoke(ob, new Object[]{player.worldObj});
            Class cEntity = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon");
            m = e.getMethod("EntityAlreadyExists", new Class[]{cEntity});
            if(!((Boolean)m.invoke(ob, new Object[]{pixelmon})).booleanValue()) {
               m = cEntity.getMethod("releaseFromPokeball", new Class[0]);
               pixelmon.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, 0.0F);
            }

            e = Class.forName("com.pixelmonmod.pixelmon.battles.controller.participants.TrainerParticipant");
            Object parTrainer = e.getConstructor(new Class[]{trainer.getClass(), EntityPlayer.class, Integer.TYPE}).newInstance(new Object[]{trainer, player, Integer.valueOf(1)});
            Object[] pixelmonArray = (Object[])((Object[])Array.newInstance(cEntity, 1));
            pixelmonArray[0] = pixelmon;
            e = Class.forName("com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant");
            Object parPlayer = e.getConstructor(new Class[]{EntityPlayerMP.class, pixelmonArray.getClass()}).newInstance(new Object[]{player, pixelmonArray});
            cEntity = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.Entity6CanBattle");
            e = Class.forName("com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant");
            m = cEntity.getMethod("StartBattle", new Class[]{e, e});
            m.invoke(pixelmon, new Object[]{parTrainer, parPlayer});
            return true;
         } catch (Exception var11) {
            LogManager.getLogger().error("startBattle", var11);
            return false;
         }
      }
   }
}
