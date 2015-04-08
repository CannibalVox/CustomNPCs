package noppes.npcs.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.CommonProxy;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.PacketHandlerPlayer;
import noppes.npcs.blocks.tiles.TileBanner;
import noppes.npcs.blocks.tiles.TileBarrel;
import noppes.npcs.blocks.tiles.TileBeam;
import noppes.npcs.blocks.tiles.TileBigSign;
import noppes.npcs.blocks.tiles.TileBlockAnvil;
import noppes.npcs.blocks.tiles.TileBook;
import noppes.npcs.blocks.tiles.TileCampfire;
import noppes.npcs.blocks.tiles.TileCandle;
import noppes.npcs.blocks.tiles.TileChair;
import noppes.npcs.blocks.tiles.TileCouchWood;
import noppes.npcs.blocks.tiles.TileCouchWool;
import noppes.npcs.blocks.tiles.TileCrate;
import noppes.npcs.blocks.tiles.TileLamp;
import noppes.npcs.blocks.tiles.TileMailbox;
import noppes.npcs.blocks.tiles.TilePedestal;
import noppes.npcs.blocks.tiles.TileShelf;
import noppes.npcs.blocks.tiles.TileSign;
import noppes.npcs.blocks.tiles.TileStool;
import noppes.npcs.blocks.tiles.TileTable;
import noppes.npcs.blocks.tiles.TileTallLamp;
import noppes.npcs.blocks.tiles.TileTombstone;
import noppes.npcs.blocks.tiles.TileWallBanner;
import noppes.npcs.blocks.tiles.TileWeaponRack;
import noppes.npcs.client.ClientEventHandler;
import noppes.npcs.client.ClientTickHandler;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.PacketHandlerClient;
import noppes.npcs.client.VersionChecker;
import noppes.npcs.client.controllers.ClientCloneController;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.controllers.PresetController;
import noppes.npcs.client.fx.EntityElementalStaffFX;
import noppes.npcs.client.fx.EntityEnderFX;
import noppes.npcs.client.fx.EntityRainbowFX;
import noppes.npcs.client.gui.GuiBorderBlock;
import noppes.npcs.client.gui.GuiMerchantAdd;
import noppes.npcs.client.gui.GuiNpcDimension;
import noppes.npcs.client.gui.GuiNpcMobSpawner;
import noppes.npcs.client.gui.GuiNpcMobSpawnerMounter;
import noppes.npcs.client.gui.GuiNpcPather;
import noppes.npcs.client.gui.GuiNpcRedstoneBlock;
import noppes.npcs.client.gui.GuiNpcRemoteEditor;
import noppes.npcs.client.gui.GuiNpcWaypoint;
import noppes.npcs.client.gui.GuiScript;
import noppes.npcs.client.gui.global.GuiNPCManageBanks;
import noppes.npcs.client.gui.global.GuiNPCManageDialogs;
import noppes.npcs.client.gui.global.GuiNPCManageFactions;
import noppes.npcs.client.gui.global.GuiNPCManageLinkedNpc;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.global.GuiNPCManageTransporters;
import noppes.npcs.client.gui.global.GuiNpcManageRecipes;
import noppes.npcs.client.gui.global.GuiNpcQuestReward;
import noppes.npcs.client.gui.mainmenu.GuiNPCGlobalMainMenu;
import noppes.npcs.client.gui.mainmenu.GuiNPCInv;
import noppes.npcs.client.gui.mainmenu.GuiNpcAI;
import noppes.npcs.client.gui.mainmenu.GuiNpcAdvanced;
import noppes.npcs.client.gui.mainmenu.GuiNpcDisplay;
import noppes.npcs.client.gui.mainmenu.GuiNpcStats;
import noppes.npcs.client.gui.player.GuiBigSign;
import noppes.npcs.client.gui.player.GuiCrate;
import noppes.npcs.client.gui.player.GuiMailbox;
import noppes.npcs.client.gui.player.GuiMailmanWrite;
import noppes.npcs.client.gui.player.GuiNPCBankChest;
import noppes.npcs.client.gui.player.GuiNPCTrader;
import noppes.npcs.client.gui.player.GuiNpcCarpentryBench;
import noppes.npcs.client.gui.player.GuiNpcFollower;
import noppes.npcs.client.gui.player.GuiNpcFollowerHire;
import noppes.npcs.client.gui.player.GuiTransportSelection;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionInv;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionStats;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionTalents;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeItem;
import noppes.npcs.client.gui.roles.GuiNpcBankSetup;
import noppes.npcs.client.gui.roles.GuiNpcFollowerSetup;
import noppes.npcs.client.gui.roles.GuiNpcItemGiver;
import noppes.npcs.client.gui.roles.GuiNpcTraderSetup;
import noppes.npcs.client.gui.roles.GuiNpcTransporter;
import noppes.npcs.client.model.ModelNPCGolem;
import noppes.npcs.client.model.ModelNpcCrystal;
import noppes.npcs.client.model.ModelNpcDragon;
import noppes.npcs.client.model.ModelNpcSlime;
import noppes.npcs.client.model.ModelSkirtArmor;
import noppes.npcs.client.renderer.NpcItemRenderer;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.client.renderer.RenderNPCHumanMale;
import noppes.npcs.client.renderer.RenderNPCPony;
import noppes.npcs.client.renderer.RenderNpcCrystal;
import noppes.npcs.client.renderer.RenderNpcDragon;
import noppes.npcs.client.renderer.RenderNpcSlime;
import noppes.npcs.client.renderer.RenderProjectile;
import noppes.npcs.client.renderer.blocks.BlockBannerRenderer;
import noppes.npcs.client.renderer.blocks.BlockBarrelRenderer;
import noppes.npcs.client.renderer.blocks.BlockBeamRenderer;
import noppes.npcs.client.renderer.blocks.BlockBigSignRenderer;
import noppes.npcs.client.renderer.blocks.BlockBloodRenderer;
import noppes.npcs.client.renderer.blocks.BlockBookRenderer;
import noppes.npcs.client.renderer.blocks.BlockBorderRenderer;
import noppes.npcs.client.renderer.blocks.BlockCampfireRenderer;
import noppes.npcs.client.renderer.blocks.BlockCandleRenderer;
import noppes.npcs.client.renderer.blocks.BlockCarpentryBenchRenderer;
import noppes.npcs.client.renderer.blocks.BlockChairRenderer;
import noppes.npcs.client.renderer.blocks.BlockCouchWoodRenderer;
import noppes.npcs.client.renderer.blocks.BlockCouchWoolRenderer;
import noppes.npcs.client.renderer.blocks.BlockCrateRenderer;
import noppes.npcs.client.renderer.blocks.BlockLampRenderer;
import noppes.npcs.client.renderer.blocks.BlockMailboxRenderer;
import noppes.npcs.client.renderer.blocks.BlockPedestalRenderer;
import noppes.npcs.client.renderer.blocks.BlockShelfRenderer;
import noppes.npcs.client.renderer.blocks.BlockSignRenderer;
import noppes.npcs.client.renderer.blocks.BlockStoolRenderer;
import noppes.npcs.client.renderer.blocks.BlockTableRenderer;
import noppes.npcs.client.renderer.blocks.BlockTallLampRenderer;
import noppes.npcs.client.renderer.blocks.BlockTombstoneRenderer;
import noppes.npcs.client.renderer.blocks.BlockWallBannerRenderer;
import noppes.npcs.client.renderer.blocks.BlockWeaponRackRenderer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.containers.ContainerCrate;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.containers.ContainerNPCBankInterface;
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
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCGolem;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.EntityNpcCrystal;
import noppes.npcs.entity.EntityNpcDragon;
import noppes.npcs.entity.EntityNpcPony;
import noppes.npcs.entity.EntityNpcSlime;
import noppes.npcs.entity.EntityProjectile;
import tconstruct.client.tabs.InventoryTabFactions;
import tconstruct.client.tabs.InventoryTabQuests;
import tconstruct.client.tabs.InventoryTabVanilla;
import tconstruct.client.tabs.TabRegistry;

public class ClientProxy extends CommonProxy {

   public static KeyBinding QuestLog;
   private ModelSkirtArmor model = new ModelSkirtArmor();


   public void load() {
      this.createFolders();
      CustomNpcs.Channel.register(new PacketHandlerClient());
      CustomNpcs.ChannelPlayer.register(new PacketHandlerPlayer());
      new MusicController();
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcPony.class, new RenderNPCPony());
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcCrystal.class, new RenderNpcCrystal(new ModelNpcCrystal(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcDragon.class, new RenderNpcDragon(new ModelNpcDragon(0.0F), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcSlime.class, new RenderNpcSlime(new ModelNpcSlime(16), new ModelNpcSlime(0), 0.25F));
      RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, new RenderProjectile());
      RenderingRegistry.registerEntityRenderingHandler(EntityCustomNpc.class, new RenderCustomNpc());
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCGolem.class, new RenderNPCHumanMale(new ModelNPCGolem(0.0F), new ModelNPCGolem(1.0F), new ModelNPCGolem(0.5F)));
      FMLCommonHandler.instance().bus().register(new ClientTickHandler());
      ClientRegistry.bindTileEntitySpecialRenderer(TileBlockAnvil.class, new BlockCarpentryBenchRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(TileMailbox.class, new BlockMailboxRenderer());
      RenderingRegistry.registerBlockHandler(new BlockBorderRenderer());
      if(!CustomNpcs.DisableExtraBlock) {
         ClientRegistry.bindTileEntitySpecialRenderer(TileBanner.class, new BlockBannerRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileWallBanner.class, new BlockWallBannerRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileTallLamp.class, new BlockTallLampRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileChair.class, new BlockChairRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileWeaponRack.class, new BlockWeaponRackRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileCrate.class, new BlockCrateRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileCouchWool.class, new BlockCouchWoolRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileCouchWood.class, new BlockCouchWoodRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileTable.class, new BlockTableRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileCandle.class, new BlockCandleRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new BlockLampRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileStool.class, new BlockStoolRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileBigSign.class, new BlockBigSignRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new BlockBarrelRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new BlockCampfireRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileTombstone.class, new BlockTombstoneRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileShelf.class, new BlockShelfRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileSign.class, new BlockSignRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileBeam.class, new BlockBeamRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TileBook.class, new BlockBookRenderer());
         ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new BlockPedestalRenderer());
         RenderingRegistry.registerBlockHandler(new BlockBloodRenderer());
      }

      Minecraft mc = Minecraft.getMinecraft();
      QuestLog = new KeyBinding("Quest Log", 38, "key.categories.gameplay");
      ClientRegistry.registerKeyBinding(QuestLog);
      mc.gameSettings.loadOptions();
      new PresetController(CustomNpcs.Dir);
      if(CustomNpcs.EnableUpdateChecker) {
         VersionChecker checker = new VersionChecker();
         checker.start();
      }

      ClientCloneController.Instance = new ClientCloneController();
      MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
      if(CustomNpcs.InventoryGuiEnabled) {
         MinecraftForge.EVENT_BUS.register(new TabRegistry());
         if(!Loader.isModLoaded("TConstruct") || TabRegistry.getTabList().size() < 3) {
            TabRegistry.registerTab(new InventoryTabVanilla());
         }

         TabRegistry.registerTab(new InventoryTabFactions());
         TabRegistry.registerTab(new InventoryTabQuests());
      }

   }

   private void createFolders() {
      File file = new File(CustomNpcs.Dir, "assets/customnpcs");
      if(!file.exists()) {
         file.mkdirs();
      }

      File check = new File(file, "sounds");
      if(!check.exists()) {
         check.mkdir();
      }

      File json = new File(file, "sounds.json");
      if(!json.exists()) {
         try {
            json.createNewFile();
            BufferedWriter cache = new BufferedWriter(new FileWriter(json));
            cache.write("{\n\n}");
            cache.close();
         } catch (IOException var5) {
            ;
         }
      }

      check = new File(file, "textures");
      if(!check.exists()) {
         check.mkdir();
      }

      File cache1 = new File(check, "cache");
      if(!cache1.exists()) {
         cache1.mkdir();
      }

      ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new CustomNpcResourceListener());
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if(ID > EnumGuiType.values().length) {
         return null;
      } else {
         EnumGuiType gui = EnumGuiType.values()[ID];
         EntityNPCInterface npc = NoppesUtil.getLastNpc();
         Container container = this.getContainer(gui, player, x, y, z, npc);
         return this.getGui(npc, gui, container, x, y, z);
      }
   }

   private GuiScreen getGui(EntityNPCInterface npc, EnumGuiType gui, Container container, int x, int y, int z) {
      if(gui == EnumGuiType.MainMenuDisplay) {
         if(npc != null) {
            return new GuiNpcDisplay(npc);
         }

         Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Unable to find npc"));
      } else {
         if(gui == EnumGuiType.MainMenuStats) {
            return new GuiNpcStats(npc);
         }

         if(gui == EnumGuiType.MainMenuInv) {
            return new GuiNPCInv(npc, (ContainerNPCInv)container);
         }

         if(gui == EnumGuiType.MainMenuAdvanced) {
            return new GuiNpcAdvanced(npc);
         }

         if(gui == EnumGuiType.QuestReward) {
            return new GuiNpcQuestReward(npc, (ContainerNpcQuestReward)container);
         }

         if(gui == EnumGuiType.QuestItem) {
            return new GuiNpcQuestTypeItem(npc, (ContainerNpcQuestTypeItem)container);
         }

         if(gui == EnumGuiType.MovingPath) {
            return new GuiNpcPather(npc);
         }

         if(gui == EnumGuiType.ManageFactions) {
            return new GuiNPCManageFactions(npc);
         }

         if(gui == EnumGuiType.ManageLinked) {
            return new GuiNPCManageLinkedNpc(npc);
         }

         if(gui == EnumGuiType.ManageTransport) {
            return new GuiNPCManageTransporters(npc);
         }

         if(gui == EnumGuiType.ManageRecipes) {
            return new GuiNpcManageRecipes(npc, (ContainerManageRecipes)container);
         }

         if(gui == EnumGuiType.ManageDialogs) {
            return new GuiNPCManageDialogs(npc);
         }

         if(gui == EnumGuiType.ManageQuests) {
            return new GuiNPCManageQuest(npc);
         }

         if(gui == EnumGuiType.ManageBanks) {
            return new GuiNPCManageBanks(npc, (ContainerManageBanks)container);
         }

         if(gui == EnumGuiType.MainMenuGlobal) {
            return new GuiNPCGlobalMainMenu(npc);
         }

         if(gui == EnumGuiType.MainMenuAI) {
            return new GuiNpcAI(npc);
         }

         if(gui == EnumGuiType.PlayerFollowerHire) {
            return new GuiNpcFollowerHire(npc, (ContainerNPCFollowerHire)container);
         }

         if(gui == EnumGuiType.PlayerFollower) {
            return new GuiNpcFollower(npc, (ContainerNPCFollower)container);
         }

         if(gui == EnumGuiType.PlayerTrader) {
            return new GuiNPCTrader(npc, (ContainerNPCTrader)container);
         }

         if(gui == EnumGuiType.PlayerBankSmall || gui == EnumGuiType.PlayerBankUnlock || gui == EnumGuiType.PlayerBankUprade || gui == EnumGuiType.PlayerBankLarge) {
            return new GuiNPCBankChest(npc, (ContainerNPCBankInterface)container);
         }

         if(gui == EnumGuiType.PlayerTransporter) {
            return new GuiTransportSelection(npc);
         }

         if(gui == EnumGuiType.Script) {
            return new GuiScript(npc);
         }

         if(gui == EnumGuiType.PlayerAnvil) {
            return new GuiNpcCarpentryBench((ContainerCarpentryBench)container);
         }

         if(gui == EnumGuiType.SetupFollower) {
            return new GuiNpcFollowerSetup(npc, (ContainerNPCFollowerSetup)container);
         }

         if(gui == EnumGuiType.SetupItemGiver) {
            return new GuiNpcItemGiver(npc, (ContainerNpcItemGiver)container);
         }

         if(gui == EnumGuiType.SetupTrader) {
            return new GuiNpcTraderSetup(npc, (ContainerNPCTraderSetup)container);
         }

         if(gui == EnumGuiType.SetupTransporter) {
            return new GuiNpcTransporter(npc);
         }

         if(gui == EnumGuiType.SetupBank) {
            return new GuiNpcBankSetup(npc);
         }

         if(gui == EnumGuiType.NpcRemote && Minecraft.getMinecraft().currentScreen == null) {
            return new GuiNpcRemoteEditor();
         }

         if(gui == EnumGuiType.PlayerMailman) {
            return new GuiMailmanWrite((ContainerMail)container, x == 1, y == 1);
         }

         if(gui == EnumGuiType.PlayerMailbox) {
            return new GuiMailbox();
         }

         if(gui == EnumGuiType.MerchantAdd) {
            return new GuiMerchantAdd();
         }

         if(gui == EnumGuiType.Crate) {
            return new GuiCrate((ContainerCrate)container);
         }

         if(gui == EnumGuiType.NpcDimensions) {
            return new GuiNpcDimension();
         }

         if(gui == EnumGuiType.Border) {
            return new GuiBorderBlock(x, y, z);
         }

         if(gui == EnumGuiType.BigSign) {
            return new GuiBigSign(x, y, z);
         }

         if(gui == EnumGuiType.RedstoneBlock) {
            return new GuiNpcRedstoneBlock(x, y, z);
         }

         if(gui == EnumGuiType.MobSpawner) {
            return new GuiNpcMobSpawner(x, y, z);
         }

         if(gui == EnumGuiType.MobSpawnerMounter) {
            return new GuiNpcMobSpawnerMounter(x, y, z);
         }

         if(gui == EnumGuiType.Waypoint) {
            return new GuiNpcWaypoint(x, y, z);
         }

         if(gui == EnumGuiType.Companion) {
            return new GuiNpcCompanionStats(npc);
         }

         if(gui == EnumGuiType.CompanionTalent) {
            return new GuiNpcCompanionTalents(npc);
         }

         if(gui == EnumGuiType.CompanionInv) {
            return new GuiNpcCompanionInv(npc, (ContainerNPCCompanion)container);
         }
      }

      return null;
   }

   public void openGui(int i, int j, int k, EnumGuiType gui, EntityPlayer player) {
      Minecraft minecraft = Minecraft.getMinecraft();
      if(minecraft.thePlayer == player) {
         GuiScreen guiscreen = this.getGui((EntityNPCInterface)null, gui, (Container)null, i, j, k);
         if(guiscreen != null) {
            minecraft.displayGuiScreen(guiscreen);
         }

      }
   }

   public void openGui(EntityNPCInterface npc, EnumGuiType gui) {
      this.openGui(npc, gui, 0, 0, 0);
   }

   public void openGui(EntityNPCInterface npc, EnumGuiType gui, int x, int y, int z) {
      Minecraft minecraft = Minecraft.getMinecraft();
      Container container = this.getContainer(gui, minecraft.thePlayer, x, y, z, npc);
      GuiScreen guiscreen = this.getGui(npc, gui, container, x, y, z);
      if(guiscreen != null) {
         minecraft.displayGuiScreen(guiscreen);
      }

   }

   public void openGui(EntityPlayer player, Object guiscreen) {
      Minecraft minecraft = Minecraft.getMinecraft();
      if(player.worldObj.isRemote && guiscreen instanceof GuiScreen) {
         if(guiscreen != null) {
            minecraft.displayGuiScreen((GuiScreen)guiscreen);
         }

      }
   }

   public void spawnParticle(EntityLivingBase player, String string, Object ... ob) {
      double height;
      double x;
      double y;
      double z;
      double f;
      if(string.equals("Spell")) {
         int data = ((Integer)ob[0]).intValue();
         int particles = ((Integer)ob[1]).intValue();

         for(int npc = 0; npc < particles; ++npc) {
            Random minecraft = player.worldObj.rand;
            height = (minecraft.nextDouble() - 0.5D) * (double)player.width;
            double rand = (double)player.getEyeHeight();
            x = (minecraft.nextDouble() - 0.5D) * (double)player.width;
            y = (minecraft.nextDouble() - 0.5D) * 2.0D;
            z = -minecraft.nextDouble();
            f = (minecraft.nextDouble() - 0.5D) * 2.0D;
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityElementalStaffFX(player, height, rand, x, y, z, f, data));
         }
      } else if(string.equals("ModelData")) {
         ModelData var24 = (ModelData)ob[0];
         ModelPartData var25 = (ModelPartData)ob[1];
         EntityCustomNpc var26 = (EntityCustomNpc)player;
         Minecraft var27 = Minecraft.getMinecraft();
         height = var26.getYOffset() + (double)var24.getBodyY();
         Random var28 = var26.getRNG();
         int i;
         if(var25.type == 0) {
            for(i = 0; i < 2; ++i) {
               EntityEnderFX var29 = new EntityEnderFX(var26, (var28.nextDouble() - 0.5D) * (double)player.width, var28.nextDouble() * (double)player.height - height - 0.25D, (var28.nextDouble() - 0.5D) * (double)player.width, (var28.nextDouble() - 0.5D) * 2.0D, -var28.nextDouble(), (var28.nextDouble() - 0.5D) * 2.0D, var25);
               var27.effectRenderer.addEffect(var29);
            }
         } else if(var25.type == 1) {
            for(i = 0; i < 2; ++i) {
               x = player.posX + (var28.nextDouble() - 0.5D) * 0.9D;
               y = player.posY + var28.nextDouble() * 1.9D - 0.25D - height;
               z = player.posZ + (var28.nextDouble() - 0.5D) * 0.9D;
               f = (var28.nextDouble() - 0.5D) * 2.0D;
               double f1 = -var28.nextDouble();
               double f2 = (var28.nextDouble() - 0.5D) * 2.0D;
               var27.effectRenderer.addEffect(new EntityRainbowFX(player.worldObj, x, y, z, f, f1, f2));
            }
         }
      }

   }

   public ModelBiped getSkirtModel() {
      return this.model;
   }

   public boolean hasClient() {
      return true;
   }

   public EntityPlayer getPlayer() {
      return Minecraft.getMinecraft().thePlayer;
   }

   public void registerItem(Item item) {
      MinecraftForgeClient.registerItemRenderer(item, new NpcItemRenderer());
   }

   public static void bindTexture(ResourceLocation location) {
      try {
         if(location == null) {
            return;
         }

         TextureManager ex = Minecraft.getMinecraft().getTextureManager();
         if(location != null) {
            ex.bindTexture(location);
         }
      } catch (NullPointerException var2) {
         ;
      } catch (ReportedException var3) {
         ;
      }

   }

   public void spawnParticle(String particle, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {
      RenderGlobal render = Minecraft.getMinecraft().renderGlobal;
      EntityFX fx = render.doSpawnParticle(particle, x, y, z, motionX, motionY, motionZ);
      if(fx != null) {
         if(particle.equals("flame")) {
            ObfuscationReflectionHelper.setPrivateValue(EntityFlameFX.class, (EntityFlameFX)fx, Float.valueOf(scale), 0);
         } else if(particle.equals("smoke")) {
            ObfuscationReflectionHelper.setPrivateValue(EntitySmokeFX.class, (EntitySmokeFX)fx, Float.valueOf(scale), 0);
         }

      }
   }
}
