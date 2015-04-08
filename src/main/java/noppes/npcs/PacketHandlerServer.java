package noppes.npcs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import foxz.utils.Market;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.Server;
import noppes.npcs.ServerEventsHandler;
import noppes.npcs.constants.EnumCompanionStage;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.constants.EnumPlayerData;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.SpawnController;
import noppes.npcs.controllers.SpawnData;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.TransportLocation;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobSpawner;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.roles.RoleTransporter;

public class PacketHandlerServer {

   @SubscribeEvent
   public void onServerPacket(ServerCustomPacketEvent event) {
      EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
      if(CustomNpcs.OpsOnly && !NoppesUtilServer.isOp(player)) {
         this.warn(player, "tried to use custom npcs without being an op");
      } else {
         ByteBuf buffer = event.packet.payload();

         try {
            EnumPacketServer e = EnumPacketServer.values()[buffer.readInt()];
            ItemStack item = player.inventory.getCurrentItem();
            EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
            if(!e.needsNpc || npc != null) {
               if(e.hasPermission()) {
                  CustomNpcsPermissions var10000 = CustomNpcsPermissions.Instance;
                  if(!CustomNpcsPermissions.hasPermission(player, e.permission)) {
                     return;
                  }
               }

               if(item == null) {
                  this.warn(player, "tried to use custom npcs without a tool in hand, probably a hacker");
               } else if(item.getItem() == CustomItems.wand) {
                  this.wandPackets(e, buffer, player, npc);
               } else if(item.getItem() == CustomItems.moving) {
                  this.movingPackets(e, buffer, player, npc);
               } else if(item.getItem() == CustomItems.mount) {
                  this.mountPackets(e, buffer, player);
               } else if(item.getItem() == CustomItems.cloner) {
                  this.clonePackets(e, buffer, player);
               } else if(item.getItem() == CustomItems.teleporter) {
                  this.featherPackets(e, buffer, player);
               } else if(item.getItem() == CustomItems.scripter) {
                  this.scriptPackets(e, buffer, player, npc);
               } else if(item.getItem() == Item.getItemFromBlock(CustomItems.waypoint) || item.getItem() == Item.getItemFromBlock(CustomItems.border) || item.getItem() == Item.getItemFromBlock(CustomItems.redstoneBlock)) {
                  this.blockPackets(e, buffer, player);
               }
            }
         } catch (IOException var7) {
            var7.printStackTrace();
         }

      }
   }

   private void scriptPackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player, EntityNPCInterface npc) throws IOException {
      if(type == EnumPacketServer.ScriptDataSave) {
         npc.script.readFromNBT(Server.readNBT(buffer));
         npc.updateTasks();
         npc.script.hasInited = false;
      } else if(type == EnumPacketServer.ScriptDataGet) {
         NBTTagCompound compound = npc.script.writeToNBT(new NBTTagCompound());
         compound.setTag("Languages", ScriptController.Instance.nbtLanguages());
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound});
      }

   }

   private void featherPackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player) throws IOException {
      if(type == EnumPacketServer.DimensionsGet) {
         HashMap dimension = new HashMap();
         Integer[] world = DimensionManager.getStaticDimensionIDs();
         int coords = world.length;

         for(int var7 = 0; var7 < coords; ++var7) {
            int id = world[var7].intValue();
            WorldProvider provider = DimensionManager.createProviderFor(id);
            dimension.put(provider.getDimensionName(), Integer.valueOf(id));
         }

         NoppesUtilServer.sendScrollData(player, dimension);
      } else if(type == EnumPacketServer.DimensionTeleport) {
         int var10 = buffer.readInt();
         WorldServer var11 = MinecraftServer.getServer().worldServerForDimension(var10);
         ChunkCoordinates var12 = var11.getEntrancePortalLocation();
         if(var12 == null) {
            var12 = var11.getSpawnPoint();
            if(!var11.isAirBlock(var12.posX, var12.posY, var12.posZ)) {
               var12.posY = var11.getTopSolidOrLiquidBlock(var12.posX, var12.posZ);
            } else {
               while(var11.isAirBlock(var12.posX, var12.posY - 1, var12.posZ) && var12.posY > 0) {
                  --var12.posY;
               }

               if(var12.posY == 0) {
                  var12.posY = var11.getTopSolidOrLiquidBlock(var12.posX, var12.posZ);
               }
            }
         }

         NoppesUtilPlayer.teleportPlayer(player, (double)var12.posX, (double)var12.posY, (double)var12.posZ, var10);
      }

   }

   private void movingPackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player, EntityNPCInterface npc) throws IOException {
      if(type == EnumPacketServer.MovingPathGet) {
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.ai.writeToNBT(new NBTTagCompound())});
      } else if(type == EnumPacketServer.MovingPathSave) {
         npc.ai.setMovingPath(NBTTags.getIntegerArraySet(Server.readNBT(buffer).getTagList("MovingPathNew", 10)));
      }

   }

   private void blockPackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player) throws IOException {
      if(type == EnumPacketServer.SaveTileEntity) {
         NoppesUtilServer.saveTileEntity(player, Server.readNBT(buffer));
      } else {
         NBTTagCompound faction;
         if(type == EnumPacketServer.GetTileEntity) {
            TileEntity compound = player.worldObj.getTileEntity(buffer.readInt(), buffer.readInt(), buffer.readInt());
            faction = new NBTTagCompound();
            compound.writeToNBT(faction);
            Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{faction});
         } else if(type == EnumPacketServer.DialogCategoriesGet) {
            NoppesUtilServer.sendScrollData(player, DialogController.instance.getScroll());
         } else {
            Dialog compound1;
            if(type == EnumPacketServer.DialogsGetFromDialog) {
               compound1 = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(buffer.readInt()));
               if(compound1 == null) {
                  return;
               }

               NoppesUtilServer.sendDialogData(player, compound1.category);
            } else if(type == EnumPacketServer.DialogsGet) {
               NoppesUtilServer.sendDialogData(player, (DialogCategory)DialogController.instance.categories.get(Integer.valueOf(buffer.readInt())));
            } else {
               Quest compound2;
               if(type == EnumPacketServer.QuestsGetFromQuest) {
                  compound2 = (Quest)QuestController.instance.quests.get(Integer.valueOf(buffer.readInt()));
                  if(compound2 == null) {
                     return;
                  }

                  NoppesUtilServer.sendQuestData(player, compound2.category);
               } else if(type == EnumPacketServer.QuestCategoriesGet) {
                  NoppesUtilServer.sendQuestCategoryData(player);
               } else if(type == EnumPacketServer.QuestsGet) {
                  QuestCategory compound3 = (QuestCategory)QuestController.instance.categories.get(Integer.valueOf(buffer.readInt()));
                  NoppesUtilServer.sendQuestData(player, compound3);
               } else if(type == EnumPacketServer.FactionsGet) {
                  NoppesUtilServer.sendFactionDataAll(player);
               } else if(type == EnumPacketServer.DialogGet) {
                  compound1 = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(buffer.readInt()));
                  if(compound1 != null) {
                     faction = compound1.writeToNBT(new NBTTagCompound());
                     Quest quest = (Quest)QuestController.instance.quests.get(Integer.valueOf(compound1.quest));
                     if(quest != null) {
                        faction.setString("DialogQuestName", quest.title);
                     }

                     Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{faction});
                  }
               } else if(type == EnumPacketServer.QuestGet) {
                  compound2 = (Quest)QuestController.instance.quests.get(Integer.valueOf(buffer.readInt()));
                  if(compound2 != null) {
                     faction = new NBTTagCompound();
                     if(compound2.hasNewQuest()) {
                        faction.setString("NextQuestTitle", compound2.getNextQuest().title);
                     }

                     Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound2.writeToNBT(faction)});
                  }
               } else if(type == EnumPacketServer.FactionGet) {
                  NBTTagCompound compound4 = new NBTTagCompound();
                  Faction faction1 = FactionController.getInstance().getFaction(buffer.readInt());
                  faction1.writeNBT(compound4);
                  Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound4});
               }
            }
         }
      }

   }

   private void wandPackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player, EntityNPCInterface npc) throws IOException {
      if(type == EnumPacketServer.Delete) {
         npc.delete();
         NoppesUtilServer.deleteNpc(npc, player);
      } else {
         ArrayList market;
         Iterator bo;
         LinkedNpcController.LinkedData names;
         if(type == EnumPacketServer.LinkedAdd) {
            LinkedNpcController.Instance.addData(Server.readString(buffer));
            market = new ArrayList();
            bo = LinkedNpcController.Instance.list.iterator();

            while(bo.hasNext()) {
               names = (LinkedNpcController.LinkedData)bo.next();
               market.add(names.name);
            }

            Server.sendData(player, EnumPacketClient.SCROLL_LIST, new Object[]{market});
         } else if(type == EnumPacketServer.LinkedRemove) {
            LinkedNpcController.Instance.removeData(Server.readString(buffer));
            market = new ArrayList();
            bo = LinkedNpcController.Instance.list.iterator();

            while(bo.hasNext()) {
               names = (LinkedNpcController.LinkedData)bo.next();
               market.add(names.name);
            }

            Server.sendData(player, EnumPacketClient.SCROLL_LIST, new Object[]{market});
         } else if(type == EnumPacketServer.LinkedGetAll) {
            market = new ArrayList();
            bo = LinkedNpcController.Instance.list.iterator();

            while(bo.hasNext()) {
               names = (LinkedNpcController.LinkedData)bo.next();
               market.add(names.name);
            }

            Server.sendData(player, EnumPacketClient.SCROLL_LIST, new Object[]{market});
            if(npc != null) {
               Server.sendData(player, EnumPacketClient.SCROLL_SELECTED, new Object[]{npc.linkedName});
            }
         } else if(type == EnumPacketServer.LinkedSet) {
            npc.linkedName = Server.readString(buffer);
            LinkedNpcController.Instance.loadNpcData(npc);
         } else if(type == EnumPacketServer.NpcMenuClose) {
            npc.reset();
            if(npc.linkedData != null) {
               LinkedNpcController.Instance.saveNpcData(npc);
            }

            NoppesUtilServer.setEditingNpc(player, (EntityNPCInterface)null);
         } else if(type == EnumPacketServer.BanksGet) {
            NoppesUtilServer.sendBankDataAll(player);
         } else {
            Bank market1;
            if(type == EnumPacketServer.BankGet) {
               market1 = BankController.getInstance().getBank(buffer.readInt());
               NoppesUtilServer.sendBank(player, market1);
            } else if(type == EnumPacketServer.BankSave) {
               market1 = new Bank();
               market1.readEntityFromNBT(Server.readNBT(buffer));
               BankController.getInstance().saveBank(market1);
               NoppesUtilServer.sendBankDataAll(player);
               NoppesUtilServer.sendBank(player, market1);
            } else if(type == EnumPacketServer.BankRemove) {
               BankController.getInstance().removeBank(buffer.readInt());
               NoppesUtilServer.sendBankDataAll(player);
               NoppesUtilServer.sendBank(player, new Bank());
            } else {
               Entity market2;
               if(type == EnumPacketServer.RemoteMainMenu) {
                  market2 = player.worldObj.getEntityByID(buffer.readInt());
                  if(market2 == null || !(market2 instanceof EntityNPCInterface)) {
                     return;
                  }

                  NoppesUtilServer.sendOpenGui(player, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)market2);
               } else if(type == EnumPacketServer.RemoteDelete) {
                  market2 = player.worldObj.getEntityByID(buffer.readInt());
                  if(market2 == null || !(market2 instanceof EntityNPCInterface)) {
                     return;
                  }

                  npc = (EntityNPCInterface)market2;
                  npc.delete();
                  NoppesUtilServer.deleteNpc(npc, player);
                  NoppesUtilServer.sendNearbyNpcs(player);
               } else if(type == EnumPacketServer.RemoteNpcsGet) {
                  NoppesUtilServer.sendNearbyNpcs(player);
                  Server.sendData(player, EnumPacketClient.SCROLL_SELECTED, new Object[]{CustomNpcs.FreezeNPCs?"Unfreeze Npcs":"Freeze Npcs"});
               } else if(type == EnumPacketServer.RemoteFreeze) {
                  CustomNpcs.FreezeNPCs = !CustomNpcs.FreezeNPCs;
                  Server.sendData(player, EnumPacketClient.SCROLL_SELECTED, new Object[]{CustomNpcs.FreezeNPCs?"Unfreeze Npcs":"Freeze Npcs"});
               } else if(type == EnumPacketServer.RemoteReset) {
                  market2 = player.worldObj.getEntityByID(buffer.readInt());
                  if(market2 == null || !(market2 instanceof EntityNPCInterface)) {
                     return;
                  }

                  npc = (EntityNPCInterface)market2;
                  npc.reset();
               } else if(type == EnumPacketServer.RemoteTpToNpc) {
                  market2 = player.worldObj.getEntityByID(buffer.readInt());
                  if(market2 == null || !(market2 instanceof EntityNPCInterface)) {
                     return;
                  }

                  npc = (EntityNPCInterface)market2;
                  player.playerNetServerHandler.setPlayerLocation(npc.posX, npc.posY, npc.posZ, 0.0F, 0.0F);
               } else {
                  int bo1;
                  if(type == EnumPacketServer.Gui) {
                     EnumGuiType market3 = EnumGuiType.values()[buffer.readInt()];
                     bo1 = buffer.readInt();
                     int names1 = buffer.readInt();
                     int compound = buffer.readInt();
                     NoppesUtilServer.sendOpenGui(player, market3, npc, bo1, names1, compound);
                  } else if(type == EnumPacketServer.RecipesGet) {
                     NoppesUtilServer.sendRecipeData(player, buffer.readInt());
                  } else {
                     RecipeCarpentry market4;
                     if(type == EnumPacketServer.RecipeGet) {
                        market4 = RecipeController.instance.getRecipe(buffer.readInt());
                        NoppesUtilServer.setRecipeGui(player, market4);
                     } else if(type == EnumPacketServer.RecipeRemove) {
                        market4 = RecipeController.instance.removeRecipe(buffer.readInt());
                        NoppesUtilServer.sendRecipeData(player, market4.isGlobal?3:4);
                        NoppesUtilServer.setRecipeGui(player, new RecipeCarpentry(""));
                     } else if(type == EnumPacketServer.RecipeSave) {
                        market4 = RecipeController.instance.saveRecipe(Server.readNBT(buffer));
                        NoppesUtilServer.sendRecipeData(player, market4.isGlobal?3:4);
                        NoppesUtilServer.setRecipeGui(player, market4);
                     } else if(type == EnumPacketServer.NaturalSpawnGetAll) {
                        NoppesUtilServer.sendScrollData(player, SpawnController.instance.getScroll());
                     } else {
                        SpawnData market5;
                        if(type == EnumPacketServer.NaturalSpawnGet) {
                           market5 = SpawnController.instance.getSpawnData(buffer.readInt());
                           if(market5 != null) {
                              Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{market5.writeNBT(new NBTTagCompound())});
                           }
                        } else if(type == EnumPacketServer.NaturalSpawnSave) {
                           market5 = new SpawnData();
                           market5.readNBT(Server.readNBT(buffer));
                           SpawnController.instance.saveSpawnData(market5);
                           NoppesUtilServer.sendScrollData(player, SpawnController.instance.getScroll());
                        } else if(type == EnumPacketServer.NaturalSpawnRemove) {
                           SpawnController.instance.removeSpawnData(buffer.readInt());
                           NoppesUtilServer.sendScrollData(player, SpawnController.instance.getScroll());
                        } else {
                           DialogCategory market6;
                           if(type == EnumPacketServer.DialogCategorySave) {
                              market6 = new DialogCategory();
                              market6.readNBT(Server.readNBT(buffer));
                              DialogController.instance.saveCategory(market6);
                              NoppesUtilServer.sendScrollData(player, DialogController.instance.getScroll());
                           } else if(type == EnumPacketServer.DialogCategoryRemove) {
                              DialogController.instance.removeCategory(buffer.readInt());
                              NoppesUtilServer.sendScrollData(player, DialogController.instance.getScroll());
                           } else {
                              NBTTagCompound bo2;
                              if(type == EnumPacketServer.DialogCategoryGet) {
                                 market6 = (DialogCategory)DialogController.instance.categories.get(Integer.valueOf(buffer.readInt()));
                                 if(market6 != null) {
                                    bo2 = market6.writeNBT(new NBTTagCompound());
                                    bo2.removeTag("Dialogs");
                                    Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{bo2});
                                 }
                              } else {
                                 int market7;
                                 Dialog bo3;
                                 if(type == EnumPacketServer.DialogSave) {
                                    market7 = buffer.readInt();
                                    bo3 = new Dialog();
                                    bo3.readNBT(Server.readNBT(buffer));
                                    DialogController.instance.saveDialog(market7, bo3);
                                    if(bo3.category != null) {
                                       NoppesUtilServer.sendDialogData(player, bo3.category);
                                    }
                                 } else {
                                    Quest market8;
                                    if(type == EnumPacketServer.QuestOpenGui) {
                                       market8 = new Quest();
                                       bo1 = buffer.readInt();
                                       market8.readNBT(Server.readNBT(buffer));
                                       NoppesUtilServer.setEditingQuest(player, market8);
                                       player.openGui(CustomNpcs.instance, bo1, player.worldObj, 0, 0, 0);
                                    } else {
                                       Dialog market9;
                                       if(type == EnumPacketServer.DialogRemove) {
                                          market9 = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(buffer.readInt()));
                                          if(market9 != null && market9.category != null) {
                                             DialogController.instance.removeDialog(market9);
                                             NoppesUtilServer.sendDialogData(player, market9.category);
                                          }
                                       } else if(type == EnumPacketServer.DialogNpcGet) {
                                          NoppesUtilServer.sendNpcDialogs(player);
                                       } else {
                                          NBTTagCompound compound1;
                                          if(type == EnumPacketServer.DialogNpcSet) {
                                             market7 = buffer.readInt();
                                             bo1 = buffer.readInt();
                                             DialogOption names2 = NoppesUtilServer.setNpcDialog(market7, bo1, player);
                                             if(names2 != null && names2.hasDialog()) {
                                                compound1 = names2.writeNBT();
                                                compound1.setInteger("Position", market7);
                                                Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound1});
                                             }
                                          } else if(type == EnumPacketServer.DialogNpcRemove) {
                                             npc.dialogs.remove(Integer.valueOf(buffer.readInt()));
                                          } else {
                                             QuestCategory market10;
                                             if(type == EnumPacketServer.QuestCategoryGet) {
                                                market10 = (QuestCategory)QuestController.instance.categories.get(Integer.valueOf(buffer.readInt()));
                                                if(market10 != null) {
                                                   bo2 = market10.writeNBT(new NBTTagCompound());
                                                   bo2.removeTag("Dialogs");
                                                   Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{bo2});
                                                }
                                             } else if(type == EnumPacketServer.QuestCategorySave) {
                                                market10 = new QuestCategory();
                                                market10.readNBT(Server.readNBT(buffer));
                                                QuestController.instance.saveCategory(market10);
                                                NoppesUtilServer.sendQuestCategoryData(player);
                                             } else if(type == EnumPacketServer.QuestCategoryRemove) {
                                                QuestController.instance.removeCategory(buffer.readInt());
                                                NoppesUtilServer.sendQuestCategoryData(player);
                                             } else if(type == EnumPacketServer.QuestSave) {
                                                market7 = buffer.readInt();
                                                Quest bo4 = new Quest();
                                                bo4.readNBT(Server.readNBT(buffer));
                                                QuestController.instance.saveQuest(market7, bo4);
                                                if(bo4.category != null) {
                                                   NoppesUtilServer.sendQuestData(player, bo4.category);
                                                }
                                             } else if(type == EnumPacketServer.QuestDialogGetTitle) {
                                                market9 = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(buffer.readInt()));
                                                bo3 = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(buffer.readInt()));
                                                Dialog names3 = (Dialog)DialogController.instance.dialogs.get(Integer.valueOf(buffer.readInt()));
                                                compound1 = new NBTTagCompound();
                                                if(market9 != null) {
                                                   compound1.setString("1", market9.title);
                                                }

                                                if(bo3 != null) {
                                                   compound1.setString("2", bo3.title);
                                                }

                                                if(names3 != null) {
                                                   compound1.setString("3", names3.title);
                                                }

                                                Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound1});
                                             } else if(type == EnumPacketServer.QuestRemove) {
                                                market8 = (Quest)QuestController.instance.quests.get(Integer.valueOf(buffer.readInt()));
                                                if(market8 != null) {
                                                   QuestController.instance.removeQuest(market8);
                                                   NoppesUtilServer.sendQuestData(player, market8.category);
                                                }
                                             } else if(type == EnumPacketServer.TransportCategoriesGet) {
                                                NoppesUtilServer.sendTransportCategoryData(player);
                                             } else if(type == EnumPacketServer.TransportCategorySave) {
                                                TransportController.getInstance().saveCategory(Server.readString(buffer), buffer.readInt());
                                             } else if(type == EnumPacketServer.TransportCategoryRemove) {
                                                TransportController.getInstance().removeCategory(buffer.readInt());
                                                NoppesUtilServer.sendTransportCategoryData(player);
                                             } else {
                                                TransportLocation bo5;
                                                if(type == EnumPacketServer.TransportRemove) {
                                                   market7 = buffer.readInt();
                                                   bo5 = TransportController.getInstance().removeLocation(market7);
                                                   if(bo5 != null) {
                                                      NoppesUtilServer.sendTransportData(player, bo5.category.id);
                                                   }
                                                } else if(type == EnumPacketServer.TransportsGet) {
                                                   NoppesUtilServer.sendTransportData(player, buffer.readInt());
                                                } else if(type == EnumPacketServer.TransportSave) {
                                                   market7 = buffer.readInt();
                                                   bo5 = TransportController.getInstance().saveLocation(market7, Server.readNBT(buffer), player, npc);
                                                   if(bo5 != null) {
                                                      if(npc.advanced.role != EnumRoleType.Transporter) {
                                                         return;
                                                      }

                                                      RoleTransporter names4 = (RoleTransporter)npc.roleInterface;
                                                      names4.setTransport(bo5);
                                                   }
                                                } else if(type == EnumPacketServer.TransportGetLocation) {
                                                   if(npc.advanced.role != EnumRoleType.Transporter) {
                                                      return;
                                                   }

                                                   RoleTransporter market11 = (RoleTransporter)npc.roleInterface;
                                                   if(market11.hasTransport()) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{market11.getLocation().writeNBT()});
                                                      Server.sendData(player, EnumPacketClient.SCROLL_SELECTED, new Object[]{market11.getLocation().category.title});
                                                   }
                                                } else if(type == EnumPacketServer.FactionSet) {
                                                   npc.setFaction(buffer.readInt());
                                                } else if(type == EnumPacketServer.FactionSave) {
                                                   Faction market12 = new Faction();
                                                   market12.readNBT(Server.readNBT(buffer));
                                                   FactionController.getInstance().saveFaction(market12);
                                                   NoppesUtilServer.sendFactionDataAll(player);
                                                   bo2 = new NBTTagCompound();
                                                   market12.writeNBT(bo2);
                                                   Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{bo2});
                                                } else {
                                                   NBTTagCompound market13;
                                                   if(type == EnumPacketServer.FactionRemove) {
                                                      FactionController.getInstance().removeFaction(buffer.readInt());
                                                      NoppesUtilServer.sendFactionDataAll(player);
                                                      market13 = new NBTTagCompound();
                                                      (new Faction()).writeNBT(market13);
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{market13});
                                                   } else if(type == EnumPacketServer.PlayerDataGet) {
                                                      market7 = buffer.readInt();
                                                      if(EnumPlayerData.values().length <= market7) {
                                                         return;
                                                      }

                                                      String bo6 = null;
                                                      EnumPlayerData names5 = EnumPlayerData.values()[market7];
                                                      if(names5 != EnumPlayerData.Players) {
                                                         bo6 = Server.readString(buffer);
                                                      }

                                                      NoppesUtilServer.sendPlayerData(names5, player, bo6);
                                                   } else if(type == EnumPacketServer.PlayerDataRemove) {
                                                      NoppesUtilServer.removePlayerData(buffer, player);
                                                   } else if(type == EnumPacketServer.MainmenuDisplayGet) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.display.writeToNBT(new NBTTagCompound())});
                                                   } else if(type == EnumPacketServer.MainmenuDisplaySave) {
                                                      npc.display.readToNBT(Server.readNBT(buffer));
                                                      market13 = npc.writeSpawnData();
                                                      market13.setInteger("EntityId", npc.getEntityId());
                                                      Server.sendAssociatedData(npc, EnumPacketClient.UPDATE_NPC, new Object[]{market13});
                                                   } else if(type == EnumPacketServer.MainmenuStatsGet) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.stats.writeToNBT(new NBTTagCompound())});
                                                   } else if(type == EnumPacketServer.MainmenuStatsSave) {
                                                      npc.stats.readToNBT(Server.readNBT(buffer));
                                                      market13 = npc.writeSpawnData();
                                                      market13.setInteger("EntityId", npc.getEntityId());
                                                      Server.sendAssociatedData(npc, EnumPacketClient.UPDATE_NPC, new Object[]{market13});
                                                   } else if(type == EnumPacketServer.MainmenuInvGet) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.inventory.writeEntityToNBT(new NBTTagCompound())});
                                                   } else if(type == EnumPacketServer.MainmenuInvSave) {
                                                      npc.inventory.readEntityFromNBT(Server.readNBT(buffer));
                                                      npc.updateTasks();
                                                      NoppesUtilServer.updateNpc(npc);
                                                   } else if(type == EnumPacketServer.MainmenuAIGet) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.ai.writeToNBT(new NBTTagCompound())});
                                                   } else if(type == EnumPacketServer.MainmenuAISave) {
                                                      npc.ai.readToNBT(Server.readNBT(buffer));
                                                      npc.updateTasks();
                                                      market13 = npc.writeSpawnData();
                                                      market13.setInteger("EntityId", npc.getEntityId());
                                                      npc.setHealth(npc.getMaxHealth());
                                                      Server.sendAssociatedData(npc, EnumPacketClient.UPDATE_NPC, new Object[]{market13});
                                                   } else if(type == EnumPacketServer.MainmenuAdvancedGet) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.advanced.writeToNBT(new NBTTagCompound())});
                                                   } else if(type == EnumPacketServer.MainmenuAdvancedSave) {
                                                      npc.advanced.readToNBT(Server.readNBT(buffer));
                                                      npc.updateTasks();
                                                      NoppesUtilServer.updateNpc(npc);
                                                   } else if(type == EnumPacketServer.JobSave) {
                                                      market13 = npc.jobInterface.writeToNBT(new NBTTagCompound());
                                                      bo2 = Server.readNBT(buffer);
                                                      Set names6 = bo2.getKeySet();
                                                      Iterator compound2 = names6.iterator();

                                                      while(compound2.hasNext()) {
                                                         String name = (String)compound2.next();
                                                         market13.setTag(name, bo2.getTag(name));
                                                      }

                                                      npc.jobInterface.readFromNBT(market13);
                                                      NoppesUtilServer.updateNpc(npc);
                                                   } else if(type == EnumPacketServer.JobGet) {
                                                      if(npc.jobInterface == null) {
                                                         return;
                                                      }

                                                      market13 = new NBTTagCompound();
                                                      market13.setBoolean("JobData", true);
                                                      npc.jobInterface.writeToNBT(market13);
                                                      if(npc.advanced.job == EnumJobType.Spawner) {
                                                         ((JobSpawner)npc.jobInterface).cleanCompound(market13);
                                                      }

                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{market13});
                                                      if(npc.advanced.job == EnumJobType.Spawner) {
                                                         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{((JobSpawner)npc.jobInterface).getTitles()});
                                                      }
                                                   } else if(type == EnumPacketServer.JobSpawnerAdd) {
                                                      if(npc.advanced.job != EnumJobType.Spawner) {
                                                         return;
                                                      }

                                                      JobSpawner market14 = (JobSpawner)npc.jobInterface;
                                                      if(buffer.readBoolean()) {
                                                         bo2 = ServerCloneController.Instance.getCloneData((ICommandSender)null, Server.readString(buffer), buffer.readInt());
                                                         market14.setJobCompound(buffer.readInt(), bo2);
                                                      } else {
                                                         market14.setJobCompound(buffer.readInt(), Server.readNBT(buffer));
                                                      }

                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{market14.getTitles()});
                                                   } else if(type == EnumPacketServer.RoleCompanionUpdate) {
                                                      if(npc.advanced.role != EnumRoleType.Companion) {
                                                         return;
                                                      }

                                                      ((RoleCompanion)npc.roleInterface).matureTo(EnumCompanionStage.values()[buffer.readInt()]);
                                                      NoppesUtilServer.updateNpc(npc);
                                                   } else if(type == EnumPacketServer.JobSpawnerRemove) {
                                                      if(npc.advanced.job != EnumJobType.Spawner) {
                                                         return;
                                                      }
                                                   } else if(type == EnumPacketServer.RoleSave) {
                                                      npc.roleInterface.readFromNBT(Server.readNBT(buffer));
                                                      NoppesUtilServer.updateNpc(npc);
                                                   } else if(type == EnumPacketServer.RoleGet) {
                                                      if(npc.roleInterface == null) {
                                                         return;
                                                      }

                                                      market13 = new NBTTagCompound();
                                                      market13.setBoolean("RoleData", true);
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.roleInterface.writeToNBT(market13)});
                                                   } else if(type == EnumPacketServer.MerchantUpdate) {
                                                      market2 = player.worldObj.getEntityByID(buffer.readInt());
                                                      if(market2 == null || !(market2 instanceof EntityVillager)) {
                                                         return;
                                                      }

                                                      MerchantRecipeList bo7 = MerchantRecipeList.func_151390_b(new PacketBuffer(buffer));
                                                      ((EntityVillager)market2).setRecipes(bo7);
                                                   } else if(type == EnumPacketServer.ModelDataSave) {
                                                      if(npc instanceof EntityCustomNpc) {
                                                         ((EntityCustomNpc)npc).modelData.readFromNBT(Server.readNBT(buffer));
                                                      }
                                                   } else if(type == EnumPacketServer.MailOpenSetup) {
                                                      PlayerMail market15 = new PlayerMail();
                                                      market15.readNBT(Server.readNBT(buffer));
                                                      ContainerMail.staticmail = market15;
                                                      player.openGui(CustomNpcs.instance, EnumGuiType.PlayerMailman.ordinal(), player.worldObj, 1, 0, 0);
                                                   } else if(type == EnumPacketServer.TransformSave) {
                                                      boolean market16 = npc.transform.isValid();
                                                      npc.transform.readOptions(Server.readNBT(buffer));
                                                      if(market16 != npc.transform.isValid()) {
                                                         npc.updateTasks();
                                                      }
                                                   } else if(type == EnumPacketServer.TransformGet) {
                                                      Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{npc.transform.writeOptions(new NBTTagCompound())});
                                                   } else if(type == EnumPacketServer.TransformLoad) {
                                                      if(npc.transform.isValid()) {
                                                         npc.transform.transform(buffer.readBoolean());
                                                      }
                                                   } else if(type == EnumPacketServer.TraderMarketSave) {
                                                      String market17 = Server.readString(buffer);
                                                      boolean bo8 = buffer.readBoolean();
                                                      if(npc.roleInterface instanceof RoleTrader) {
                                                         if(bo8) {
                                                            Market.setMarket(npc, market17);
                                                         } else {
                                                            Market.save((RoleTrader)npc.roleInterface, market17);
                                                         }
                                                      }
                                                   } else {
                                                      this.blockPackets(type, buffer, player);
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void mountPackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player) throws IOException {
      if(type == EnumPacketServer.SpawnRider) {
         Entity list = EntityList.createEntityFromNBT(Server.readNBT(buffer), player.worldObj);
         player.worldObj.spawnEntityInWorld(list);
         list.mountEntity(ServerEventsHandler.mounted);
      } else if(type == EnumPacketServer.PlayerRider) {
         player.mountEntity(ServerEventsHandler.mounted);
      } else if(type == EnumPacketServer.CloneList) {
         NBTTagList list1 = new NBTTagList();
         Iterator compound = ServerCloneController.Instance.getClones(buffer.readInt()).iterator();

         while(compound.hasNext()) {
            String name = (String)compound.next();
            list1.appendTag(new NBTTagString(name));
         }

         NBTTagCompound compound1 = new NBTTagCompound();
         compound1.setTag("List", list1);
         Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound1});
      } else {
         this.warn(player, "tried todo something with the wrong tool, probably a hacker");
      }

   }

   private void clonePackets(EnumPacketServer type, ByteBuf buffer, EntityPlayerMP player) throws IOException {
      boolean list;
      int compound;
      int name;
      int name1;
      NBTTagCompound compound1;
      if(type == EnumPacketServer.SpawnMob) {
         list = buffer.readBoolean();
         compound = buffer.readInt();
         name = buffer.readInt();
         name1 = buffer.readInt();
         if(list) {
            compound1 = ServerCloneController.Instance.getCloneData(player, Server.readString(buffer), buffer.readInt());
         } else {
            compound1 = Server.readNBT(buffer);
         }

         if(compound1 == null) {
            return;
         }

         Entity entity = NoppesUtilServer.spawnClone(compound1, compound, name, name1, player.worldObj);
         if(entity == null) {
            player.addChatMessage(new ChatComponentText("Failed to create an entity out of your clone"));
            return;
         }
      } else if(type == EnumPacketServer.MobSpawner) {
         list = buffer.readBoolean();
         compound = buffer.readInt();
         name = buffer.readInt();
         name1 = buffer.readInt();
         if(list) {
            compound1 = ServerCloneController.Instance.getCloneData(player, Server.readString(buffer), buffer.readInt());
         } else {
            compound1 = Server.readNBT(buffer);
         }

         if(compound1 != null) {
            NoppesUtilServer.createMobSpawner(compound, name, name1, compound1, player);
         }
      } else {
         NBTTagCompound compound2;
         if(type == EnumPacketServer.ClonePreSave) {
            list = ServerCloneController.Instance.getCloneData((ICommandSender)null, Server.readString(buffer), buffer.readInt()) != null;
            compound2 = new NBTTagCompound();
            compound2.setBoolean("NameExists", list);
            Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound2});
         } else if(type == EnumPacketServer.CloneSave) {
            PlayerData list1 = PlayerDataController.instance.getPlayerData(player);
            if(list1.cloned == null) {
               return;
            }

            ServerCloneController.Instance.addClone(list1.cloned, Server.readString(buffer), buffer.readInt());
         } else if(type == EnumPacketServer.CloneRemove) {
            int list2 = buffer.readInt();
            ServerCloneController.Instance.removeClone(Server.readString(buffer), list2);
            NBTTagList compound3 = new NBTTagList();
            Iterator name2 = ServerCloneController.Instance.getClones(list2).iterator();

            while(name2.hasNext()) {
               String name5 = (String)name2.next();
               compound3.appendTag(new NBTTagString(name5));
            }

            NBTTagCompound name3 = new NBTTagCompound();
            name3.setTag("List", compound3);
            Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{name3});
         } else if(type == EnumPacketServer.CloneList) {
            NBTTagList list3 = new NBTTagList();
            Iterator compound4 = ServerCloneController.Instance.getClones(buffer.readInt()).iterator();

            while(compound4.hasNext()) {
               String name4 = (String)compound4.next();
               list3.appendTag(new NBTTagString(name4));
            }

            compound2 = new NBTTagCompound();
            compound2.setTag("List", list3);
            Server.sendData(player, EnumPacketClient.GUI_DATA, new Object[]{compound2});
         } else {
            this.warn(player, "tried todo something with the wrong tool, probably a hacker");
         }
      }

   }

   private void warn(EntityPlayer player, String warning) {
      MinecraftServer.getServer().logWarning(player.getCommandSenderName() + ": " + warning);
   }
}
