package noppes.npcs.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.player.GuiQuestLog;
import noppes.npcs.constants.EnumPlayerPacket;

public class ClientTickHandler {

   private World prevWorld;
   private boolean otherContainer = false;


   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onClientTick(ClientTickEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      if(mc.thePlayer != null && mc.thePlayer.openContainer instanceof ContainerPlayer) {
         if(this.otherContainer) {
            NoppesUtilPlayer.sendData(EnumPlayerPacket.CheckQuestCompletion, new Object[0]);
            this.otherContainer = false;
         }
      } else {
         this.otherContainer = true;
      }

      ++CustomNpcs.ticks;
      if(this.prevWorld != mc.theWorld) {
         this.prevWorld = mc.theWorld;
         MusicController.Instance.stopMusic();
      }

   }

   @SubscribeEvent
   public void onKey(KeyInputEvent event) {
      if(ClientProxy.QuestLog.isPressed()) {
         Minecraft mc = Minecraft.getMinecraft();
         if(mc.currentScreen == null) {
            NoppesUtil.openGUI(mc.thePlayer, new GuiQuestLog(mc.thePlayer));
         } else if(mc.currentScreen instanceof GuiQuestLog) {
            mc.setIngameFocus();
         }
      }

   }
}
