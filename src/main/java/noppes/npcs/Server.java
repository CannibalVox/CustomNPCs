package noppes.npcs;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.village.MerchantRecipeList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.constants.EnumPacketClient;

public class Server {

   public static boolean sendData(EntityPlayerMP player, EnumPacketClient enu, Object ... obs) {
      ByteBuf buffer = Unpooled.buffer();

      try {
         if(!fillBuffer(buffer, enu, obs)) {
            return false;
         }

         CustomNpcs.Channel.sendTo(new FMLProxyPacket(buffer, "CustomNPCs"), player);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return true;
   }

   public static void sendAssociatedData(Entity entity, EnumPacketClient enu, Object ... obs) {
      ByteBuf buffer = Unpooled.buffer();

      try {
         if(!fillBuffer(buffer, enu, obs)) {
            return;
         }

         TargetPoint e = new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 60.0D);
         CustomNpcs.Channel.sendToAllAround(new FMLProxyPacket(buffer, "CustomNPCs"), e);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static void sendToAll(EnumPacketClient enu, Object ... obs) {
      ByteBuf buffer = Unpooled.buffer();

      try {
         if(!fillBuffer(buffer, enu, obs)) {
            return;
         }

         CustomNpcs.Channel.sendToAll(new FMLProxyPacket(buffer, "CustomNPCs"));
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static boolean fillBuffer(ByteBuf buffer, Enum enu, Object ... obs) throws IOException {
      buffer.writeInt(enu.ordinal());
      Object[] var3 = obs;
      int var4 = obs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object ob = var3[var5];
         if(ob != null) {
            Iterator var8;
            String s;
            if(ob instanceof Map) {
               Map var11 = (Map)ob;
               buffer.writeInt(var11.size());
               var8 = var11.keySet().iterator();

               while(var8.hasNext()) {
                  s = (String)var8.next();
                  int value = ((Integer)var11.get(s)).intValue();
                  buffer.writeInt(value);
                  writeString(buffer, s);
               }
            } else if(ob instanceof MerchantRecipeList) {
               ((MerchantRecipeList)ob).func_151391_a(new PacketBuffer(buffer));
            } else if(ob instanceof List) {
               List list = (List)ob;
               buffer.writeInt(list.size());
               var8 = list.iterator();

               while(var8.hasNext()) {
                  s = (String)var8.next();
                  writeString(buffer, s);
               }
            } else if(ob instanceof Enum) {
               buffer.writeInt(((Enum)ob).ordinal());
            } else if(ob instanceof Integer) {
               buffer.writeInt(((Integer)ob).intValue());
            } else if(ob instanceof Boolean) {
               buffer.writeBoolean(((Boolean)ob).booleanValue());
            } else if(ob instanceof String) {
               writeString(buffer, (String)ob);
            } else if(ob instanceof Float) {
               buffer.writeFloat(((Float)ob).floatValue());
            } else if(ob instanceof Long) {
               buffer.writeLong(((Long)ob).longValue());
            } else if(ob instanceof Double) {
               buffer.writeDouble(((Double)ob).doubleValue());
            } else if(ob instanceof NBTTagCompound) {
               writeNBT(buffer, (NBTTagCompound)ob);
            }
         }
      }

      if(buffer.array().length >= 32767) {
         LogWriter.error("Packet " + enu + " was too big to be send");
         return false;
      } else {
         return true;
      }
   }

   public static void writeNBT(ByteBuf buffer, NBTTagCompound compound) throws IOException {
      byte[] bytes = CompressedStreamTools.compress(compound);
      buffer.writeShort((short)bytes.length);
      buffer.writeBytes(bytes);
   }

   public static NBTTagCompound readNBT(ByteBuf buffer) throws IOException {
      byte[] bytes = new byte[buffer.readShort()];
      buffer.readBytes(bytes);
      return CompressedStreamTools.decompress(bytes, new NBTSizeTracker(2097152L));
   }

   public static void writeString(ByteBuf buffer, String s) {
      byte[] bytes = s.getBytes(Charsets.UTF_8);
      buffer.writeShort((short)bytes.length);
      buffer.writeBytes(bytes);
   }

   public static String readString(ByteBuf buffer) {
      try {
         byte[] ex = new byte[buffer.readShort()];
         buffer.readBytes(ex);
         return new String(ex, Charsets.UTF_8);
      } catch (IndexOutOfBoundsException var2) {
         return null;
      }
   }
}
