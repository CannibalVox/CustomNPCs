package noppes.npcs.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipeList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.PacketHandlerServer;
import noppes.npcs.Server;
import noppes.npcs.ServerEventsHandler;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.QuestAchievement;
import noppes.npcs.client.RenderChatMessages;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.GuiNpcMobSpawnerAdd;
import noppes.npcs.client.gui.player.GuiBook;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.IGuiClose;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IGuiError;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.entity.EntityNPCInterface;

public class PacketHandlerClient extends PacketHandlerServer {

   @SubscribeEvent
   public void onPacketData(ClientCustomPacketEvent event) {
      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
      ByteBuf buffer = event.packet.payload();

      try {
         this.client(buffer, player, EnumPacketClient.values()[buffer.readInt()]);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private void client(ByteBuf buffer, EntityPlayer player, EnumPacketClient type) throws IOException {
      Entity x;
      if(type == EnumPacketClient.CHATBUBBLE) {
         x = Minecraft.getMinecraft().theWorld.getEntityByID(buffer.readInt());
         if(x == null || !(x instanceof EntityNPCInterface)) {
            return;
         }

         EntityNPCInterface y = (EntityNPCInterface)x;
         if(y.messages == null) {
            y.messages = new RenderChatMessages();
         }

         String z = NoppesStringUtils.formatText(Server.readString(buffer), new Object[]{player, y});
         y.messages.addMessage(z, y);
         if(buffer.readBoolean()) {
            player.addChatMessage(new ChatComponentTranslation(y.getCommandSenderName() + ": " + z, new Object[0]));
         }
      } else {
         String var7;
         String var15;
         if(type == EnumPacketClient.CHAT) {
            for(var7 = ""; (var15 = Server.readString(buffer)) != null && !var15.isEmpty(); var7 = var7 + StatCollector.translateToLocal(var15)) {
               ;
            }

            player.addChatMessage(new ChatComponentTranslation(var7, new Object[0]));
         } else if(type == EnumPacketClient.MESSAGE) {
            var7 = StatCollector.translateToLocal(Server.readString(buffer));
            var15 = Server.readString(buffer);
            QuestAchievement var19 = new QuestAchievement(var15, var7);
            Minecraft.getMinecraft().guiAchievement.displayAchievement(var19);
            ObfuscationReflectionHelper.setPrivateValue(GuiAchievement.class, Minecraft.getMinecraft().guiAchievement, var19.getDescription(), 4);
         } else {
            int var16;
            if(type == EnumPacketClient.SYNCRECIPES_ADD) {
               NBTTagList var8 = Server.readNBT(buffer).getTagList("recipes", 10);
               if(var8 == null) {
                  return;
               }

               for(var16 = 0; var16 < var8.tagCount(); ++var16) {
                  RecipeCarpentry var20 = RecipeCarpentry.read(var8.getCompoundTagAt(var16));
                  RecipeController.syncRecipes.put(Integer.valueOf(var20.id), var20);
               }
            } else if(type == EnumPacketClient.SYNCRECIPES_WORKBENCH) {
               RecipeController.reloadGlobalRecipes(RecipeController.syncRecipes);
               RecipeController.syncRecipes = new HashMap();
            } else if(type == EnumPacketClient.SYNCRECIPES_CARPENTRYBENCH) {
               RecipeController.instance.anvilRecipes = RecipeController.syncRecipes;
               RecipeController.syncRecipes = new HashMap();
            } else if(type == EnumPacketClient.DIALOG) {
               x = Minecraft.getMinecraft().theWorld.getEntityByID(buffer.readInt());
               if(x == null || !(x instanceof EntityNPCInterface)) {
                  return;
               }

               NoppesUtil.openDialog(Server.readNBT(buffer), (EntityNPCInterface)x, player);
            } else if(type == EnumPacketClient.QUEST_COMPLETION) {
               NoppesUtil.guiQuestCompletion(player, Server.readNBT(buffer));
            } else if(type == EnumPacketClient.EDIT_NPC) {
               x = Minecraft.getMinecraft().theWorld.getEntityByID(buffer.readInt());
               if(x == null || !(x instanceof EntityNPCInterface)) {
                  return;
               }

               NoppesUtil.setLastNpc((EntityNPCInterface)x);
            } else if(type == EnumPacketClient.PLAY_MUSIC) {
               MusicController.Instance.playMusic(Server.readString(buffer), player);
            } else if(type == EnumPacketClient.PLAY_SOUND) {
               MusicController.Instance.playSound(Server.readString(buffer), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
            } else {
               NBTTagCompound var9;
               Entity var17;
               if(type == EnumPacketClient.UPDATE_NPC) {
                  var9 = Server.readNBT(buffer);
                  var17 = Minecraft.getMinecraft().theWorld.getEntityByID(var9.getInteger("EntityId"));
                  if(var17 == null || !(var17 instanceof EntityNPCInterface)) {
                     return;
                  }

                  ((EntityNPCInterface)var17).readSpawnData(var9);
               } else if(type == EnumPacketClient.ROLE) {
                  var9 = Server.readNBT(buffer);
                  var17 = Minecraft.getMinecraft().theWorld.getEntityByID(var9.getInteger("EntityId"));
                  if(var17 == null || !(var17 instanceof EntityNPCInterface)) {
                     return;
                  }

                  ((EntityNPCInterface)var17).advanced.setRole(var9.getInteger("Role"));
                  ((EntityNPCInterface)var17).roleInterface.readFromNBT(var9);
                  NoppesUtil.setLastNpc((EntityNPCInterface)var17);
               } else if(type == EnumPacketClient.GUI) {
                  EnumGuiType var10 = EnumGuiType.values()[buffer.readInt()];
                  CustomNpcs.proxy.openGui(NoppesUtil.getLastNpc(), var10, buffer.readInt(), buffer.readInt(), buffer.readInt());
               } else if(type == EnumPacketClient.PARTICLE) {
                  NoppesUtil.spawnParticle(buffer);
               } else if(type == EnumPacketClient.DELETE_NPC) {
                  x = Minecraft.getMinecraft().theWorld.getEntityByID(buffer.readInt());
                  if(x == null || !(x instanceof EntityNPCInterface)) {
                     return;
                  }

                  ((EntityNPCInterface)x).delete();
               } else if(type == EnumPacketClient.SCROLL_LIST) {
                  NoppesUtil.setScrollList(buffer);
               } else if(type == EnumPacketClient.SCROLL_DATA) {
                  NoppesUtil.setScrollData(buffer);
               } else if(type == EnumPacketClient.SCROLL_DATA_PART) {
                  NoppesUtil.addScrollData(buffer);
               } else {
                  GuiScreen var11;
                  if(type == EnumPacketClient.SCROLL_SELECTED) {
                     var11 = Minecraft.getMinecraft().currentScreen;
                     if(var11 == null || !(var11 instanceof IScrollData)) {
                        return;
                     }

                     var15 = Server.readString(buffer);
                     ((IScrollData)var11).setSelected(var15);
                  } else if(type == EnumPacketClient.GUI_REDSTONE) {
                     NoppesUtil.saveRedstoneBlock(player, Server.readNBT(buffer));
                  } else if(type == EnumPacketClient.GUI_WAYPOINT) {
                     NoppesUtil.saveWayPointBlock(player, Server.readNBT(buffer));
                  } else if(type == EnumPacketClient.CLONE) {
                     var9 = Server.readNBT(buffer);
                     NoppesUtil.openGUI(player, new GuiNpcMobSpawnerAdd(var9));
                  } else if(type == EnumPacketClient.GUI_DATA) {
                     Object var12 = Minecraft.getMinecraft().currentScreen;
                     if(var12 == null) {
                        return;
                     }

                     if(var12 instanceof GuiNPCInterface && ((GuiNPCInterface)var12).hasSubGui()) {
                        var12 = ((GuiNPCInterface)var12).getSubGui();
                     } else if(var12 instanceof GuiContainerNPCInterface && ((GuiContainerNPCInterface)var12).hasSubGui()) {
                        var12 = ((GuiContainerNPCInterface)var12).getSubGui();
                     }

                     if(var12 instanceof IGuiData) {
                        ((IGuiData)var12).setGuiData(Server.readNBT(buffer));
                     }
                  } else {
                     NBTTagCompound var21;
                     if(type == EnumPacketClient.GUI_ERROR) {
                        var11 = Minecraft.getMinecraft().currentScreen;
                        if(var11 == null || !(var11 instanceof IGuiError)) {
                           return;
                        }

                        var16 = buffer.readInt();
                        var21 = Server.readNBT(buffer);
                        ((IGuiError)var11).setError(var16, var21);
                     } else if(type == EnumPacketClient.GUI_CLOSE) {
                        var11 = Minecraft.getMinecraft().currentScreen;
                        if(var11 == null) {
                           return;
                        }

                        if(var11 instanceof IGuiClose) {
                           var16 = buffer.readInt();
                           var21 = Server.readNBT(buffer);
                           ((IGuiClose)var11).setClose(var16, var21);
                        }

                        Minecraft var18 = Minecraft.getMinecraft();
                        var18.displayGuiScreen((GuiScreen)null);
                        var18.setIngameFocus();
                     } else if(type == EnumPacketClient.VILLAGER_LIST) {
                        MerchantRecipeList var13 = MerchantRecipeList.func_151390_b(new PacketBuffer(buffer));
                        ServerEventsHandler.Merchant.setRecipes(var13);
                     } else if(type == EnumPacketClient.OPEN_BOOK) {
                        int var14 = buffer.readInt();
                        var16 = buffer.readInt();
                        int var22 = buffer.readInt();
                        NoppesUtil.openGUI(player, new GuiBook(player, ItemStack.loadItemStackFromNBT(Server.readNBT(buffer)), var14, var16, var22));
                     }
                  }
               }
            }
         }
      }

   }
}
