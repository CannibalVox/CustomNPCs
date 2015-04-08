package foxz.utils;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Utils {

   public static List getNearbeEntityFromPlayer(Class cls, EntityPlayerMP player, int dis) {
      AxisAlignedBB range = player.boundingBox.expand((double)dis, (double)dis, (double)dis);
      List list = player.worldObj.getEntitiesWithinAABB(cls, range);
      return list;
   }

   public static EntityPlayer getOnlinePlayer(String playername) {
      return MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(playername);
   }

   public static World getWorld(String t) {
      WorldServer[] ws = MinecraftServer.getServer().worldServers;
      WorldServer[] var2 = ws;
      int var3 = ws.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WorldServer w = var2[var4];
         if(w != null && w.provider.getDimensionName().equalsIgnoreCase(t)) {
            return w;
         }
      }

      return null;
   }
}
