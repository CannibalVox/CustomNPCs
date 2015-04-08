package noppes.npcs;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import foxz.command.CommandNoppes;
import java.io.File;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockVine;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.CommonProxy;
import noppes.npcs.CustomItems;
import noppes.npcs.ServerEventsHandler;
import noppes.npcs.ServerTickHandler;
import noppes.npcs.config.ConfigLoader;
import noppes.npcs.config.ConfigProp;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.ChunkController;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.GlobalDataController;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.SpawnController;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.enchants.EnchantInterface;
import noppes.npcs.entity.EntityChairMount;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCGolem;
import noppes.npcs.entity.EntityNpcCrystal;
import noppes.npcs.entity.EntityNpcDragon;
import noppes.npcs.entity.EntityNpcPony;
import noppes.npcs.entity.EntityNpcSlime;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.entity.old.EntityNPCDwarfFemale;
import noppes.npcs.entity.old.EntityNPCDwarfMale;
import noppes.npcs.entity.old.EntityNPCElfFemale;
import noppes.npcs.entity.old.EntityNPCElfMale;
import noppes.npcs.entity.old.EntityNPCEnderman;
import noppes.npcs.entity.old.EntityNPCFurryFemale;
import noppes.npcs.entity.old.EntityNPCFurryMale;
import noppes.npcs.entity.old.EntityNPCHumanFemale;
import noppes.npcs.entity.old.EntityNPCHumanMale;
import noppes.npcs.entity.old.EntityNPCOrcFemale;
import noppes.npcs.entity.old.EntityNPCOrcMale;
import noppes.npcs.entity.old.EntityNPCVillager;
import noppes.npcs.entity.old.EntityNpcEnderchibi;
import noppes.npcs.entity.old.EntityNpcMonsterFemale;
import noppes.npcs.entity.old.EntityNpcMonsterMale;
import noppes.npcs.entity.old.EntityNpcNagaFemale;
import noppes.npcs.entity.old.EntityNpcNagaMale;
import noppes.npcs.entity.old.EntityNpcSkeleton;

@Mod(
   modid = "customnpcs",
   name = "CustomNpcs",
   version = "1.7.10c"
)
public class CustomNpcs {

   @ConfigProp(
      info = "Disable Chat Bubbles"
   )
   public static boolean EnableChatBubbles = true;
   @ConfigProp(
      info = "Uses unique entities ids"
   )
   public static boolean UseUniqueEntities = true;
   @ConfigProp(
      info = "To use this UseUniqueEntities has to be false"
   )
   public static int EntityStartId = 120;
   @ConfigProp(
      info = "Navigation search range for NPCs. Not recommended to increase if you have a slow pc or on a server"
   )
   public static int NpcNavRange = 32;
   @ConfigProp(
      info = "Set to true if you want the dialog command option to be able to use op commands like tp etc"
   )
   public static boolean NpcUseOpCommands = false;
   @ConfigProp
   public static boolean InventoryGuiEnabled = true;
   @ConfigProp
   public static boolean DisableExtraItems = false;
   @ConfigProp
   public static boolean DisableExtraBlock = false;
   public static boolean PixelMonEnabled = false;
   public static long ticks;
   @SidedProxy(
      clientSide = "noppes.npcs.client.ClientProxy",
      serverSide = "noppes.npcs.CommonProxy"
   )
   public static CommonProxy proxy;
   @ConfigProp(
      info = "Enables CustomNpcs startup update message"
   )
   public static boolean EnableUpdateChecker = true;
   public static CustomNpcs instance;
   public static boolean FreezeNPCs = false;
   @ConfigProp(
      info = "Only ops can create and edit npcs"
   )
   public static boolean OpsOnly = false;
   @ConfigProp(
      info = "Default interact line. Leave empty to not have one"
   )
   public static String DefaultInteractLine = "Hello @p";
   @ConfigProp
   public static boolean DisableEnchants = false;
   @ConfigProp(
      info = "Start Id for enchants. IDs can only range from 0-256"
   )
   public static int EnchantStartId = 100;
   @ConfigProp(
      info = "Number of chunk loading npcs that can be active at the same time"
   )
   public static int ChuckLoaders = 20;
   public static File Dir;
   @ConfigProp(
      info = "Set to false if you want to disable guns"
   )
   public static boolean GunsEnabled = true;
   @ConfigProp(
      info = "Enables leaves decay"
   )
   public static boolean LeavesDecayEnabled = true;
   @ConfigProp(
      info = "Enables Vine Growth"
   )
   public static boolean VineGrowthEnabled = true;
   @ConfigProp(
      info = "Enables Ice Melting"
   )
   public static boolean IceMeltsEnabled = true;
   @ConfigProp(
      info = "Normal players can use soulstone on animals"
   )
   public static boolean SoulStoneAnimals = true;
   public static FMLEventChannel Channel;
   public static FMLEventChannel ChannelPlayer;
   public static ConfigLoader Config;


   public CustomNpcs() {
      instance = this;
   }

   @EventHandler
   public void load(FMLPreInitializationEvent ev) {
      Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("CustomNPCs");
      ChannelPlayer = NetworkRegistry.INSTANCE.newEventDrivenChannel("CustomNPCsPlayer");
      MinecraftServer server = MinecraftServer.getServer();
      String dir = "";
      if(server != null) {
         dir = (new File(".")).getAbsolutePath();
      } else {
         dir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
      }

      Dir = new File(dir, "customnpcs");
      Dir.mkdir();
      Config = new ConfigLoader(this.getClass(), new File(dir, "config"), "CustomNpcs");
      Config.loadConfig();
      if(NpcNavRange < 16) {
         NpcNavRange = 16;
      }

      EnchantInterface.load();
      CustomItems.load();
      proxy.load();
      NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
      MinecraftForge.EVENT_BUS.register(new ServerEventsHandler());
      MinecraftForge.EVENT_BUS.register(new ScriptController());
      FMLCommonHandler.instance().bus().register(new ServerTickHandler());
      PixelMonEnabled = Loader.isModLoaded("pixelmon");
      this.registerNpc(EntityNPCHumanMale.class, "npchumanmale", getEntityId());
      this.registerNpc(EntityNPCVillager.class, "npcvillager", getEntityId());
      this.registerNpc(EntityNpcPony.class, "npcpony", getEntityId());
      this.registerNpc(EntityNPCHumanFemale.class, "npchumanfemale", getEntityId());
      this.registerNpc(EntityNPCDwarfMale.class, "npcdwarfmale", getEntityId());
      this.registerNpc(EntityNPCFurryMale.class, "npcfurrymale", getEntityId());
      this.registerNpc(EntityNpcMonsterMale.class, "npczombiemale", getEntityId());
      this.registerNpc(EntityNpcMonsterFemale.class, "npczombiefemale", getEntityId());
      this.registerNpc(EntityNpcSkeleton.class, "npcskeleton", getEntityId());
      this.registerNpc(EntityNPCDwarfFemale.class, "npcdwarffemale", getEntityId());
      this.registerNpc(EntityNPCFurryFemale.class, "npcfurryfemale", getEntityId());
      this.registerNpc(EntityNPCOrcMale.class, "npcorcfmale", getEntityId());
      this.registerNpc(EntityNPCOrcFemale.class, "npcorcfemale", getEntityId());
      this.registerNpc(EntityNPCElfMale.class, "npcelfmale", getEntityId());
      this.registerNpc(EntityNPCElfFemale.class, "npcelffemale", getEntityId());
      this.registerNpc(EntityNpcCrystal.class, "npccrystal", getEntityId());
      this.registerNpc(EntityNpcEnderchibi.class, "npcenderchibi", getEntityId());
      this.registerNpc(EntityNpcNagaMale.class, "npcnagamale", getEntityId());
      this.registerNpc(EntityNpcNagaFemale.class, "npcnagafemale", getEntityId());
      this.registerNpc(EntityNpcSlime.class, "NpcSlime", getEntityId());
      this.registerNpc(EntityNpcDragon.class, "NpcDragon", getEntityId());
      this.registerNpc(EntityNPCEnderman.class, "npcEnderman", getEntityId());
      this.registerNpc(EntityNPCGolem.class, "npcGolem", getEntityId());
      this.registerNpc(EntityCustomNpc.class, "CustomNpc", getEntityId());
      this.registerNpc(EntityChairMount.class, "CustomNpcChairMount", getEntityId());
      int thowid = getEntityId();
      EntityRegistry.registerGlobalEntityID(EntityProjectile.class, "throwableitem", thowid);
      EntityRegistry.registerModEntity(EntityProjectile.class, "throwableitem", thowid, this, 64, 3, true);
      new RecipeController();
      ForgeChunkManager.setForcedChunkLoadingCallback(this, new ChunkController());
   }

   @EventHandler
   public void setAboutToStart(FMLServerAboutToStartEvent event) {
      ChunkController.instance.clear();
      new QuestController();
      new PlayerDataController();
      new FactionController();
      new TransportController();
      new GlobalDataController();
      new SpawnController();
      new LinkedNpcController();
      ScriptController.Instance.loadStoredData();
      ScriptController.HasStart = false;
      Set names = Block.blockRegistry.getKeys();
      Iterator var3 = names.iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Block block = (Block)Block.blockRegistry.getObject(name);
         if(block instanceof BlockLeavesBase) {
            block.setTickRandomly(LeavesDecayEnabled);
         }

         if(block instanceof BlockVine) {
            block.setTickRandomly(VineGrowthEnabled);
         }

         if(block instanceof BlockIce) {
            block.setTickRandomly(IceMeltsEnabled);
         }
      }

   }

   @EventHandler
   public void started(FMLServerStartedEvent event) {
      RecipeController.instance.load();
      new DialogController();
      new BankController();
      QuestController.instance.load();
      ScriptController.HasStart = true;
      ServerCloneController.Instance = new ServerCloneController();
   }

   @EventHandler
   public void stopped(FMLServerStoppedEvent event) {
      ServerCloneController.Instance = null;
   }

   @EventHandler
   public void serverstart(FMLServerStartingEvent event) {
      event.registerServerCommand(new CommandNoppes());
   }

   public static int getEntityId() {
      return UseUniqueEntities?EntityRegistry.findGlobalUniqueEntityId():EntityStartId++;
   }

   private void registerNpc(Class cl, String name, int id) {
      EntityRegistry.registerGlobalEntityID(cl, name, id);
      EntityRegistry.registerModEntity(cl, name, id, this, 80, 5, true);
   }

   public static File getWorldSaveDirectory() {
      MinecraftServer server = MinecraftServer.getServer();
      File saves = new File(".");
      if(server != null && !server.isDedicatedServer()) {
         saves = new File(Minecraft.getMinecraft().mcDataDir, "saves");
      }

      if(server != null) {
         File savedir = new File(new File(saves, server.getFolderName()), "customnpcs");
         if(!savedir.exists()) {
            savedir.mkdir();
         }

         return savedir;
      } else {
         return null;
      }
   }

}
