package noppes.npcs;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.PacketHandlerPlayer;
import noppes.npcs.PacketHandlerServer;
import noppes.npcs.ServerEventsHandler;
import noppes.npcs.blocks.tiles.TileNpcContainer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.containers.ContainerCrate;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.containers.ContainerMerchantAdd;
import noppes.npcs.containers.ContainerNPCBankLarge;
import noppes.npcs.containers.ContainerNPCBankSmall;
import noppes.npcs.containers.ContainerNPCBankUnlock;
import noppes.npcs.containers.ContainerNPCBankUpgrade;
import noppes.npcs.containers.ContainerNPCCompanion;
import noppes.npcs.containers.ContainerNPCFollower;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.containers.ContainerNPCFollowerSetup;
import noppes.npcs.containers.ContainerNPCInv;
import noppes.npcs.containers.ContainerNPCTrader;
import noppes.npcs.containers.ContainerNPCTraderSetup;
import noppes.npcs.containers.ContainerNpcItemGiver;
import noppes.npcs.containers.ContainerNpcQuestReward;
import noppes.npcs.containers.ContainerNpcQuestTypeItem;
import noppes.npcs.entity.EntityNPCInterface;

public class CommonProxy implements IGuiHandler {

   public boolean newVersionAvailable = false;
   public int revision = 4;


   public void load() {
      CustomNpcs.Channel.register(new PacketHandlerServer());
      CustomNpcs.ChannelPlayer.register(new PacketHandlerPlayer());
   }

   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if(ID > EnumGuiType.values().length) {
         return null;
      } else {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         EnumGuiType gui = EnumGuiType.values()[ID];
         return this.getContainer(gui, player, x, y, z, npc);
      }
   }

   public Container getContainer(EnumGuiType gui, EntityPlayer player, int x, int y, int z, EntityNPCInterface npc) {
      return (Container)(gui == EnumGuiType.MainMenuInv?new ContainerNPCInv(npc, player):(gui == EnumGuiType.PlayerBankSmall?new ContainerNPCBankSmall(player, x, y):(gui == EnumGuiType.PlayerBankUnlock?new ContainerNPCBankUnlock(player, x, y):(gui == EnumGuiType.PlayerBankUprade?new ContainerNPCBankUpgrade(player, x, y):(gui == EnumGuiType.PlayerBankLarge?new ContainerNPCBankLarge(player, x, y):(gui == EnumGuiType.PlayerFollowerHire?new ContainerNPCFollowerHire(npc, player):(gui == EnumGuiType.PlayerFollower?new ContainerNPCFollower(npc, player):(gui == EnumGuiType.PlayerTrader?new ContainerNPCTrader(npc, player):(gui == EnumGuiType.PlayerAnvil?new ContainerCarpentryBench(player.inventory, player.worldObj, x, y, z):(gui == EnumGuiType.SetupItemGiver?new ContainerNpcItemGiver(npc, player):(gui == EnumGuiType.SetupTrader?new ContainerNPCTraderSetup(npc, player):(gui == EnumGuiType.SetupFollower?new ContainerNPCFollowerSetup(npc, player):(gui == EnumGuiType.QuestReward?new ContainerNpcQuestReward(player):(gui == EnumGuiType.QuestItem?new ContainerNpcQuestTypeItem(player):(gui == EnumGuiType.ManageRecipes?new ContainerManageRecipes(player, x):(gui == EnumGuiType.ManageBanks?new ContainerManageBanks(player):(gui == EnumGuiType.MerchantAdd?new ContainerMerchantAdd(player, ServerEventsHandler.Merchant, player.worldObj):(gui == EnumGuiType.Crate?new ContainerCrate(player.inventory, (TileNpcContainer)player.worldObj.getTileEntity(x, y, z)):(gui == EnumGuiType.PlayerMailman?new ContainerMail(player, x == 1, y == 1):(gui == EnumGuiType.CompanionInv?new ContainerNPCCompanion(npc, player):null))))))))))))))))))));
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      return null;
   }

   public void openGui(EntityNPCInterface npc, EnumGuiType gui) {}

   public void openGui(EntityNPCInterface npc, EnumGuiType gui, int x, int y, int z) {}

   public void openGui(int i, int j, int k, EnumGuiType gui, EntityPlayer player) {}

   public void openGui(EntityPlayer player, Object guiscreen) {}

   public void spawnParticle(EntityLivingBase player, String string, Object ... ob) {}

   public boolean hasClient() {
      return false;
   }

   public EntityPlayer getPlayer() {
      return null;
   }

   public void registerItem(Item item) {}

   public ModelBiped getSkirtModel() {
      return null;
   }

   public void spawnParticle(String particle, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {}
}
