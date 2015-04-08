package noppes.npcs.constants;


public enum EnumPacketServer {

   Delete("Delete", 0, "npc.delete", true),
   RemoteMainMenu("RemoteMainMenu", 1, "npc.gui"),
   NpcMenuClose("NpcMenuClose", 2, "npc.gui", true),
   RemoteDelete("RemoteDelete", 3, "npc.delete", true),
   RemoteFreeze("RemoteFreeze", 4, "npc.freeze"),
   RemoteReset("RemoteReset", 5, "npc.reset"),
   SpawnMob("SpawnMob", 6, "spawner.mob"),
   MobSpawner("MobSpawner", 7, "spawner.create"),
   MainmenuAISave("MainmenuAISave", 8, "npc.ai", true),
   MainmenuAIGet("MainmenuAIGet", 9, true),
   MainmenuInvSave("MainmenuInvSave", 10, "npc.inventory", true),
   MainmenuInvGet("MainmenuInvGet", 11, true),
   MainmenuStatsSave("MainmenuStatsSave", 12, "npc.stats", true),
   MainmenuStatsGet("MainmenuStatsGet", 13, true),
   MainmenuDisplaySave("MainmenuDisplaySave", 14, "npc.display", true),
   MainmenuDisplayGet("MainmenuDisplayGet", 15, true),
   ModelDataSave("ModelDataSave", 16, "npc.display", true),
   MainmenuAdvancedSave("MainmenuAdvancedSave", 17, "npc.advanced", true),
   MainmenuAdvancedGet("MainmenuAdvancedGet", 18, true),
   DialogNpcSet("DialogNpcSet", 19, "npc.advanced"),
   DialogNpcRemove("DialogNpcRemove", 20, "npc.advanced", true),
   FactionSet("FactionSet", 21, "npc.advanced", true),
   TransportSave("TransportSave", 22, "npc.advanced", true),
   TransformSave("TransformSave", 23, "npc.advanced", true),
   TransformGet("TransformGet", 24, true),
   TransformLoad("TransformLoad", 25, "npc.advanced", true),
   TraderMarketSave("TraderMarketSave", 26, "npc.advanced", true),
   JobSave("JobSave", 27, "npc.advanced", true),
   JobGet("JobGet", 28, true),
   RoleSave("RoleSave", 29, "npc.advanced", true),
   RoleGet("RoleGet", 30, true),
   JobSpawnerAdd("JobSpawnerAdd", 31, "npc.advanced", true),
   JobSpawnerRemove("JobSpawnerRemove", 32, "npc.advanced", true),
   RoleCompanionUpdate("RoleCompanionUpdate", 33, "npc.advanced", true),
   LinkedSet("LinkedSet", 34, "npc.advanced", true),
   ClonePreSave("ClonePreSave", 35, "npc.clone"),
   CloneSave("CloneSave", 36, "npc.clone"),
   CloneRemove("CloneRemove", 37, "npc.clone"),
   CloneList("CloneList", 38),
   LinkedGetAll("LinkedGetAll", 39),
   LinkedRemove("LinkedRemove", 40, "global.linked"),
   LinkedAdd("LinkedAdd", 41, "global.linked"),
   ScriptDataSave("ScriptDataSave", 42, "customnpcs.scripter", true),
   ScriptDataGet("ScriptDataGet", 43, true),
   PlayerDataRemove("PlayerDataRemove", 44, "global.playerdata"),
   BankSave("BankSave", 45, "global.bank"),
   BanksGet("BanksGet", 46),
   BankGet("BankGet", 47),
   BankRemove("BankRemove", 48, "global.bank"),
   DialogCategorySave("DialogCategorySave", 49, "global.dialog"),
   DialogCategoriesGet("DialogCategoriesGet", 50),
   DialogsGetFromDialog("DialogsGetFromDialog", 51),
   DialogCategoryRemove("DialogCategoryRemove", 52, "global.dialog"),
   DialogCategoryGet("DialogCategoryGet", 53),
   DialogSave("DialogSave", 54, "global.dialog"),
   DialogsGet("DialogsGet", 55),
   DialogGet("DialogGet", 56),
   DialogRemove("DialogRemove", 57, "global.dialog"),
   TransportCategoryRemove("TransportCategoryRemove", 58, "global.transport"),
   TransportGetLocation("TransportGetLocation", 59, true),
   TransportRemove("TransportRemove", 60, "global.transport"),
   TransportsGet("TransportsGet", 61),
   TransportCategorySave("TransportCategorySave", 62, "global.transport"),
   TransportCategoriesGet("TransportCategoriesGet", 63),
   FactionRemove("FactionRemove", 64, "global.faction"),
   FactionSave("FactionSave", 65, "global.faction"),
   FactionsGet("FactionsGet", 66),
   FactionGet("FactionGet", 67),
   QuestCategorySave("QuestCategorySave", 68, "global.quest"),
   QuestCategoriesGet("QuestCategoriesGet", 69),
   QuestRemove("QuestRemove", 70, "global.quest"),
   QuestCategoryRemove("QuestCategoryRemove", 71, "global.quest"),
   QuestRewardSave("QuestRewardSave", 72, "global.quest"),
   QuestSave("QuestSave", 73, "global.quest"),
   QuestsGetFromQuest("QuestsGetFromQuest", 74),
   QuestsGet("QuestsGet", 75),
   QuestDialogGetTitle("QuestDialogGetTitle", 76, "global.quest"),
   RecipeSave("RecipeSave", 77, "global.recipe"),
   RecipeRemove("RecipeRemove", 78, "global.recipe"),
   NaturalSpawnSave("NaturalSpawnSave", 79, "global.naturalspawn"),
   NaturalSpawnGet("NaturalSpawnGet", 80),
   NaturalSpawnRemove("NaturalSpawnRemove", 81, "global.naturalspawn"),
   MerchantUpdate("MerchantUpdate", 82, "villager"),
   PlayerRider("PlayerRider", 83, "mounter"),
   SpawnRider("SpawnRider", 84, "mounter"),
   MovingPathSave("MovingPathSave", 85, "pather", true),
   MovingPathGet("MovingPathGet", 86, true),
   DialogNpcGet("DialogNpcGet", 87),
   RecipesGet("RecipesGet", 88),
   RecipeGet("RecipeGet", 89),
   QuestOpenGui("QuestOpenGui", 90),
   PlayerDataGet("PlayerDataGet", 91),
   RemoteNpcsGet("RemoteNpcsGet", 92),
   RemoteTpToNpc("RemoteTpToNpc", 93),
   QuestGet("QuestGet", 94),
   QuestCategoryGet("QuestCategoryGet", 95),
   SaveTileEntity("SaveTileEntity", 96),
   NaturalSpawnGetAll("NaturalSpawnGetAll", 97),
   MailOpenSetup("MailOpenSetup", 98),
   DimensionsGet("DimensionsGet", 99),
   DimensionTeleport("DimensionTeleport", 100),
   GetTileEntity("GetTileEntity", 101),
   Gui("Gui", 102);
   public String permission;
   public boolean needsNpc;
   // $FF: synthetic field
   private static final EnumPacketServer[] $VALUES = new EnumPacketServer[]{Delete, RemoteMainMenu, NpcMenuClose, RemoteDelete, RemoteFreeze, RemoteReset, SpawnMob, MobSpawner, MainmenuAISave, MainmenuAIGet, MainmenuInvSave, MainmenuInvGet, MainmenuStatsSave, MainmenuStatsGet, MainmenuDisplaySave, MainmenuDisplayGet, ModelDataSave, MainmenuAdvancedSave, MainmenuAdvancedGet, DialogNpcSet, DialogNpcRemove, FactionSet, TransportSave, TransformSave, TransformGet, TransformLoad, TraderMarketSave, JobSave, JobGet, RoleSave, RoleGet, JobSpawnerAdd, JobSpawnerRemove, RoleCompanionUpdate, LinkedSet, ClonePreSave, CloneSave, CloneRemove, CloneList, LinkedGetAll, LinkedRemove, LinkedAdd, ScriptDataSave, ScriptDataGet, PlayerDataRemove, BankSave, BanksGet, BankGet, BankRemove, DialogCategorySave, DialogCategoriesGet, DialogsGetFromDialog, DialogCategoryRemove, DialogCategoryGet, DialogSave, DialogsGet, DialogGet, DialogRemove, TransportCategoryRemove, TransportGetLocation, TransportRemove, TransportsGet, TransportCategorySave, TransportCategoriesGet, FactionRemove, FactionSave, FactionsGet, FactionGet, QuestCategorySave, QuestCategoriesGet, QuestRemove, QuestCategoryRemove, QuestRewardSave, QuestSave, QuestsGetFromQuest, QuestsGet, QuestDialogGetTitle, RecipeSave, RecipeRemove, NaturalSpawnSave, NaturalSpawnGet, NaturalSpawnRemove, MerchantUpdate, PlayerRider, SpawnRider, MovingPathSave, MovingPathGet, DialogNpcGet, RecipesGet, RecipeGet, QuestOpenGui, PlayerDataGet, RemoteNpcsGet, RemoteTpToNpc, QuestGet, QuestCategoryGet, SaveTileEntity, NaturalSpawnGetAll, MailOpenSetup, DimensionsGet, DimensionTeleport, GetTileEntity, Gui};


   private EnumPacketServer(String var1, int var2) {
      this.needsNpc = false;
   }

   private EnumPacketServer(String var1, int var2, String permission, boolean npc) {
      this(var1, var2, permission);
   }

   private EnumPacketServer(String var1, int var2, boolean npc) {
      this.needsNpc = false;
      this.needsNpc = npc;
   }

   private EnumPacketServer(String var1, int var2, String permission) {
      this.needsNpc = false;
      this.permission = "customnpcs." + permission;
   }

   public boolean hasPermission() {
      return this.permission != null && !this.permission.isEmpty();
   }

}
