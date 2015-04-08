package noppes.npcs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import noppes.npcs.NPCSpawning;
import noppes.npcs.client.AnalyticsTracking;

public class ServerTickHandler {

   @SubscribeEvent
   public void onServerTick(WorldTickEvent event) {
      if(event.phase == Phase.START) {
         NPCSpawning.findChunksForSpawning((WorldServer)event.world);
      }

   }

   @SubscribeEvent
   public void playerLogin(PlayerLoggedInEvent event) {
      String e = "local";
      if(MinecraftServer.getServer().isDedicatedServer()) {
         e = MinecraftServer.getServer().getServerHostname();
      }

      AnalyticsTracking.sendData(event.player, "join", e);
   }
}
