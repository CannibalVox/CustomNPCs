package noppes.npcs.client;

import io.netty.buffer.ByteBuf;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.Server;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.player.GuiDialogInteract;
import noppes.npcs.client.gui.player.GuiQuestCompletion;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.Sys;

public class NoppesUtil {

   private static EntityNPCInterface lastNpc;
   private static HashMap data = new HashMap();


   public static void requestOpenGUI(EnumGuiType gui) {
      requestOpenGUI(gui, 0, 0, 0);
   }

   public static void requestOpenGUI(EnumGuiType gui, int i, int j, int k) {
      Client.sendData(EnumPacketServer.Gui, new Object[]{Integer.valueOf(gui.ordinal()), Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)});
   }

   public static void spawnParticle(ByteBuf buffer) throws IOException {
      double posX = buffer.readDouble();
      double posY = buffer.readDouble();
      double posZ = buffer.readDouble();
      float height = buffer.readFloat();
      float width = buffer.readFloat();
      float yOffset = buffer.readFloat();
      String particle = Server.readString(buffer);
      WorldClient worldObj = Minecraft.getMinecraft().theWorld;
      Random rand = worldObj.rand;
      if(particle.equals("heal")) {
         for(int k = 0; k < 6; ++k) {
            worldObj.spawnParticle("instantSpell", posX + (rand.nextDouble() - 0.5D) * (double)width, posY + rand.nextDouble() * (double)height - (double)yOffset, posZ + (rand.nextDouble() - 0.5D) * (double)width, 0.0D, 0.0D, 0.0D);
            worldObj.spawnParticle("spell", posX + (rand.nextDouble() - 0.5D) * (double)width, posY + rand.nextDouble() * (double)height - (double)yOffset, posZ + (rand.nextDouble() - 0.5D) * (double)width, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public static EntityNPCInterface getLastNpc() {
      return lastNpc;
   }

   public static void setLastNpc(EntityNPCInterface npc) {
      lastNpc = npc;
   }

   public static void openGUI(EntityPlayer player, Object guiscreen) {
      CustomNpcs.proxy.openGui(player, guiscreen);
   }

   public static void openFolder(File dir) {
      String s = dir.getAbsolutePath();
      if(Util.getOSType() == EnumOS.OSX) {
         try {
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", s});
            return;
         } catch (IOException var7) {
            ;
         }
      } else if(Util.getOSType() == EnumOS.WINDOWS) {
         String flag = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{s});

         try {
            Runtime.getRuntime().exec(flag);
            return;
         } catch (IOException var6) {
            ;
         }
      }

      boolean flag1 = false;

      try {
         Class throwable = Class.forName("java.awt.Desktop");
         Object object = throwable.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         throwable.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{dir.toURI()});
      } catch (Throwable var5) {
         flag1 = true;
      }

      if(flag1) {
         Sys.openURL("file://" + s);
      }

   }

   public static void setScrollList(ByteBuf buffer) {
      Object gui = Minecraft.getMinecraft().currentScreen;
      if(gui instanceof GuiNPCInterface && ((GuiNPCInterface)gui).hasSubGui()) {
         gui = ((GuiNPCInterface)gui).getSubGui();
      }

      if(gui != null && gui instanceof IScrollData) {
         Vector data = new Vector();

         try {
            int e = buffer.readInt();

            for(int i = 0; i < e; ++i) {
               data.add(Server.readString(buffer));
            }
         } catch (Exception var6) {
            ;
         }

         ((IScrollData)gui).setData(data, (HashMap)null);
      }
   }

   public static void addScrollData(ByteBuf buffer) {
      try {
         int e = buffer.readInt();

         for(int i = 0; i < e; ++i) {
            int id = buffer.readInt();
            String name = Server.readString(buffer);
            data.put(name, Integer.valueOf(id));
         }
      } catch (Exception var5) {
         ;
      }

   }

   public static void setScrollData(ByteBuf buffer) {
      Object gui = Minecraft.getMinecraft().currentScreen;
      if(gui != null) {
         try {
            int e = buffer.readInt();

            for(int i = 0; i < e; ++i) {
               int id = buffer.readInt();
               String name = Server.readString(buffer);
               data.put(name, Integer.valueOf(id));
            }
         } catch (Exception var6) {
            ;
         }

         if(gui instanceof GuiNPCInterface && ((GuiNPCInterface)gui).hasSubGui()) {
            gui = ((GuiNPCInterface)gui).getSubGui();
         }

         if(gui instanceof GuiContainerNPCInterface && ((GuiContainerNPCInterface)gui).hasSubGui()) {
            gui = ((GuiContainerNPCInterface)gui).getSubGui();
         }

         if(gui instanceof IScrollData) {
            ((IScrollData)gui).setData(new Vector(data.keySet()), data);
         }

         data = new HashMap();
      }
   }

   public static void guiQuestCompletion(EntityPlayer player, NBTTagCompound read) {
      Quest quest = new Quest();
      quest.readNBT(read);
      if(!quest.completeText.equals("")) {
         openGUI(player, new GuiQuestCompletion(quest));
      } else {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestCompletion, new Object[]{Integer.valueOf(quest.id)});
      }

   }

   public static void openDialog(NBTTagCompound compound, EntityNPCInterface npc, EntityPlayer player) {
      if(DialogController.instance == null) {
         DialogController.instance = new DialogController();
      }

      Dialog dialog = new Dialog();
      dialog.readNBT(compound);
      GuiScreen gui = Minecraft.getMinecraft().currentScreen;
      if(gui != null && gui instanceof GuiDialogInteract) {
         GuiDialogInteract dia = (GuiDialogInteract)gui;
         dia.appendDialog(dialog);
      } else {
         CustomNpcs.proxy.openGui(player, (Object)(new GuiDialogInteract(npc, dialog)));
      }

   }

   public static void saveRedstoneBlock(EntityPlayer player, NBTTagCompound compound) {
      int x = compound.getInteger("x");
      int y = compound.getInteger("y");
      int z = compound.getInteger("z");
      TileEntity tile = player.worldObj.getTileEntity(x, y, z);
      tile.readFromNBT(compound);
      CustomNpcs.proxy.openGui(x, y, z, EnumGuiType.RedstoneBlock, player);
   }

   public static void saveWayPointBlock(EntityPlayer player, NBTTagCompound compound) {
      int x = compound.getInteger("x");
      int y = compound.getInteger("y");
      int z = compound.getInteger("z");
      TileEntity tile = player.worldObj.getTileEntity(x, y, z);
      tile.readFromNBT(compound);
      CustomNpcs.proxy.openGui(x, y, z, EnumGuiType.Waypoint, player);
   }

}
