package noppes.npcs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.Server;
import noppes.npcs.blocks.tiles.TileBigSign;
import noppes.npcs.blocks.tiles.TileBook;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.controllers.BankData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.PlayerMailData;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;

public class PacketHandlerPlayer {

   @SubscribeEvent
   public void onServerPacket(ServerCustomPacketEvent event) {
      EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
      ByteBuf buffer = event.packet.payload();

      try {
         this.player(buffer, player, EnumPlayerPacket.values()[buffer.readInt()]);
      } catch (IOException var5) {
         LogWriter.except(var5);
      }

   }

   private void player(ByteBuf buffer, EntityPlayerMP player, EnumPlayerPacket type) throws IOException {
      EntityNPCInterface x;
      int y;
      int z;
      if(type == EnumPlayerPacket.CompanionTalentExp) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Companion || player != x.getOwner()) {
            return;
         }

         y = buffer.readInt();
         z = buffer.readInt();
         RoleCompanion tileentity = (RoleCompanion)x.roleInterface;
         if(z <= 0 || !tileentity.canAddExp(-z) || y < 0 || y >= EnumCompanionTalent.values().length) {
            return;
         }

         EnumCompanionTalent tile = EnumCompanionTalent.values()[y];
         tileentity.addExp(-z);
         tileentity.addTalentExp(tile, z);
      } else if(type == EnumPlayerPacket.CompanionOpenInv) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Companion || player != x.getOwner()) {
            return;
         }

         NoppesUtilServer.sendOpenGui(player, EnumGuiType.CompanionInv, x);
      } else if(type == EnumPlayerPacket.FollowerHire) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Follower) {
            return;
         }

         NoppesUtilPlayer.hireFollower(player, x);
      } else if(type == EnumPlayerPacket.FollowerExtend) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Follower) {
            return;
         }

         NoppesUtilPlayer.extendFollower(player, x);
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{x.roleInterface.writeToNBT(new NBTTagCompound())});
      } else if(type == EnumPlayerPacket.FollowerState) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Follower) {
            return;
         }

         NoppesUtilPlayer.changeFollowerState(player, x);
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{x.roleInterface.writeToNBT(new NBTTagCompound())});
      } else if(type == EnumPlayerPacket.RoleGet) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role == EnumRoleType.None) {
            return;
         }

         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{x.roleInterface.writeToNBT(new NBTTagCompound())});
      } else if(type == EnumPlayerPacket.Transport) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Transporter) {
            return;
         }

         NoppesUtilPlayer.transport(player, x, Server.readString(buffer));
      } else if(type == EnumPlayerPacket.BankUpgrade) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Bank) {
            return;
         }

         NoppesUtilPlayer.bankUpgrade(player, x);
      } else if(type == EnumPlayerPacket.BankUnlock) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Bank) {
            return;
         }

         NoppesUtilPlayer.bankUnlock(player, x);
      } else if(type == EnumPlayerPacket.BankSlotOpen) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null || x.advanced.role != EnumRoleType.Bank) {
            return;
         }

         y = buffer.readInt();
         z = buffer.readInt();
         BankData tileentity1 = PlayerDataController.instance.getBankData(player, z).getBankOrDefault(z);
         tileentity1.openBankGui(player, x, z, y);
      } else if(type == EnumPlayerPacket.Dialog) {
         x = NoppesUtilServer.getEditingNpc(player);
         if(x == null) {
            return;
         }

         NoppesUtilPlayer.dialogSelected(buffer.readInt(), buffer.readInt(), player, x);
      } else if(type == EnumPlayerPacket.CheckQuestCompletion) {
         PlayerQuestData x1 = PlayerDataController.instance.getPlayerData(player).questData;
         x1.checkQuestCompletion(player, (EnumQuestType)null);
      } else if(type == EnumPlayerPacket.QuestLog) {
         NoppesUtilPlayer.sendQuestLogData(player);
      } else if(type == EnumPlayerPacket.QuestCompletion) {
         NoppesUtilPlayer.questCompletion(player, buffer.readInt());
      } else if(type == EnumPlayerPacket.FactionsGet) {
         PlayerFactionData x2 = PlayerDataController.instance.getPlayerData(player).factionData;
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{x2.getPlayerGuiData()});
      } else if(type == EnumPlayerPacket.MailGet) {
         PlayerMailData x3 = PlayerDataController.instance.getPlayerData(player).mailData;
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{x3.saveNBTData(new NBTTagCompound())});
      } else {
         PlayerMail sign;
         long x4;
         String z1;
         PlayerMailData tileentity2;
         Iterator tile1;
         if(type == EnumPlayerPacket.MailDelete) {
            x4 = buffer.readLong();
            z1 = Server.readString(buffer);
            tileentity2 = PlayerDataController.instance.getPlayerData(player).mailData;
            tile1 = tileentity2.playermail.iterator();

            while(tile1.hasNext()) {
               sign = (PlayerMail)tile1.next();
               if(sign.time == x4 && sign.sender.equals(z1)) {
                  tile1.remove();
               }
            }

            Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{tileentity2.saveNBTData(new NBTTagCompound())});
         } else if(type == EnumPlayerPacket.MailSend) {
            if(!(player.openContainer instanceof ContainerMail)) {
               return;
            }

            String x5 = PlayerDataController.instance.hasPlayer(Server.readString(buffer));
            if(x5.isEmpty()) {
               NoppesUtilServer.sendGuiError(player, 0);
               return;
            }

            PlayerMail y1 = new PlayerMail();
            z1 = player.getDisplayName();
            if(!z1.equals(player.getCommandSenderName())) {
               z1 = z1 + "(" + player.getCommandSenderName() + ")";
            }

            y1.readNBT(Server.readNBT(buffer));
            y1.sender = z1;
            y1.items = ((ContainerMail)player.openContainer).mail.items;
            if(y1.subject.isEmpty()) {
               NoppesUtilServer.sendGuiError(player, 1);
               return;
            }

            PlayerDataController.instance.addPlayerMessage(x5, y1);
            NBTTagCompound tileentity3 = new NBTTagCompound();
            tileentity3.setString("username", x5);
            NoppesUtilServer.sendGuiClose(player, 1, tileentity3);
         } else if(type == EnumPlayerPacket.MailboxOpenMail) {
            x4 = buffer.readLong();
            z1 = Server.readString(buffer);
            player.closeContainer();
            tileentity2 = PlayerDataController.instance.getPlayerData(player).mailData;
            tile1 = tileentity2.playermail.iterator();

            while(tile1.hasNext()) {
               sign = (PlayerMail)tile1.next();
               if(sign.time == x4 && sign.sender.equals(z1)) {
                  ContainerMail.staticmail = sign;
                  player.openGui(CustomNpcs.instance, EnumGuiType.PlayerMailman.ordinal(), player.worldObj, 0, 0, 0);
                  break;
               }
            }
         } else if(type == EnumPlayerPacket.MailRead) {
            x4 = buffer.readLong();
            z1 = Server.readString(buffer);
            tileentity2 = PlayerDataController.instance.getPlayerData(player).mailData;
            tile1 = tileentity2.playermail.iterator();

            while(tile1.hasNext()) {
               sign = (PlayerMail)tile1.next();
               if(sign.time == x4 && sign.sender.equals(z1)) {
                  sign.beenRead = true;
                  if(sign.hasQuest()) {
                     PlayerQuestController.addActiveQuest(sign.getQuest(), player);
                  }
               }
            }
         } else {
            int x6;
            TileEntity tileentity4;
            if(type == EnumPlayerPacket.SignSave) {
               x6 = buffer.readInt();
               y = buffer.readInt();
               z = buffer.readInt();
               tileentity4 = player.worldObj.getTileEntity(x6, y, z);
               if(tileentity4 == null || !(tileentity4 instanceof TileBigSign)) {
                  return;
               }

               TileBigSign tile2 = (TileBigSign)tileentity4;
               if(tile2.canEdit) {
                  tile2.setText(Server.readString(buffer));
                  tile2.canEdit = false;
                  player.worldObj.markBlockForUpdate(x6, y, z);
               }
            } else if(type == EnumPlayerPacket.SaveBook) {
               x6 = buffer.readInt();
               y = buffer.readInt();
               z = buffer.readInt();
               tileentity4 = player.worldObj.getTileEntity(x6, y, z);
               if(!(tileentity4 instanceof TileBook)) {
                  return;
               }

               TileBook tile3 = (TileBook)tileentity4;
               if(tile3.book.getItem() == Items.written_book) {
                  return;
               }

               boolean sign1 = buffer.readBoolean();
               ItemStack book = ItemStack.loadItemStackFromNBT(Server.readNBT(buffer));
               if(book == null) {
                  return;
               }

               if(book.getItem() == Items.writable_book && !sign1 && ItemWritableBook.validBookPageTagContents(book.getTagCompound())) {
                  tile3.book.setTagInfo("pages", book.getTagCompound().getTagList("pages", 8));
               }

               if(book.getItem() == Items.written_book && sign1 && ItemEditableBook.validBookTagContents(book.getTagCompound())) {
                  tile3.book.setTagInfo("author", new NBTTagString(player.getCommandSenderName()));
                  tile3.book.setTagInfo("title", new NBTTagString(book.getTagCompound().getString("title")));
                  tile3.book.setTagInfo("pages", book.getTagCompound().getTagList("pages", 8));
                  tile3.book.setItem(Items.written_book);
               }
            }
         }
      }

   }
}
