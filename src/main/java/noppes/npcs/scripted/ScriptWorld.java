package noppes.npcs.scripted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.scripted.ScriptEntity;
import noppes.npcs.scripted.ScriptItemStack;
import noppes.npcs.scripted.ScriptPlayer;
import noppes.npcs.scripted.ScriptScoreboard;

public class ScriptWorld {

   private static Map tempData = new HashMap();
   protected WorldServer world;


   public ScriptWorld(WorldServer world) {
      this.world = world;
   }

   public long getTime() {
      return this.world.getWorldTime();
   }

   public long getTotalTime() {
      return this.world.getTotalWorldTime();
   }

   public ScriptItemStack getBlock(int x, int y, int z) {
      Block block = this.world.getBlock(x, y, z);
      return block != null && !block.isAir(this.world, x, y, z)?new ScriptItemStack(new ItemStack(block)):null;
   }

   public void setBlock(int x, int y, int z, ScriptItemStack item) {
      if(item == null) {
         this.removeBlock(x, y, z);
      } else {
         Block block = Block.getBlockFromItem(item.item.getItem());
         if(block != null && block != Blocks.air) {
            this.world.setBlock(x, y, z, block);
         }
      }
   }

   public void removeBlock(int x, int y, int z) {
      this.world.setBlock(x, y, z, Blocks.air);
   }

   public ScriptPlayer getPlayer(String name) {
      EntityPlayer player = this.world.getPlayerEntityByName(name);
      return player == null?null:(ScriptPlayer)ScriptController.Instance.getScriptForEntity(player);
   }

   public void setTime(long time) {
      this.world.setWorldTime(time);
   }

   public boolean isDay() {
      return this.world.getWorldTime() % 24000L < 12000L;
   }

   public boolean isRaining() {
      return this.world.getWorldInfo().isRaining();
   }

   public void setRaining(boolean bo) {
      this.world.getWorldInfo().setRaining(bo);
   }

   public void thunderStrike(double x, double y, double z) {
      this.world.addWeatherEffect(new EntityLightningBolt(this.world, x, y, z));
   }

   public void spawnParticle(String particle, double x, double y, double z, double dx, double dy, double dz, double speed, int count) {
      this.world.func_147487_a(particle, x, y, z, count, dx, dy, dz, speed);
   }

   public ScriptItemStack createItem(String id, int damage, int size) {
      Item item = (Item)Item.itemRegistry.getObject(id);
      return item == null?null:new ScriptItemStack(new ItemStack(item, size, damage));
   }

   public Object getTempData(String key) {
      return tempData.get(key);
   }

   public void setTempData(String key, Object value) {
      tempData.put(key, value);
   }

   public boolean hasTempData(String key) {
      return tempData.containsKey(key);
   }

   public void removeTempData(String key) {
      tempData.remove(key);
   }

   public void clearTempData() {
      tempData.clear();
   }

   public Object getStoredData(String key) {
      NBTTagCompound compound = ScriptController.Instance.compound;
      if(!compound.hasKey(key)) {
         return null;
      } else {
         NBTBase base = compound.getTag(key);
         return base instanceof NBTPrimitive?Double.valueOf(((NBTPrimitive)base).getDouble()):((NBTTagString)base).getString();
      }
   }

   public void setStoredData(String key, Object value) {
      NBTTagCompound compound = ScriptController.Instance.compound;
      if(value instanceof Number) {
         compound.setDouble(key, ((Number)value).doubleValue());
      } else if(value instanceof String) {
         compound.setString(key, (String)value);
      }

      ScriptController.Instance.shouldSave = true;
   }

   public boolean hasStoredData(String key) {
      return ScriptController.Instance.compound.hasKey(key);
   }

   public void removeStoredData(String key) {
      ScriptController.Instance.compound.removeTag(key);
      ScriptController.Instance.shouldSave = true;
   }

   public void clearStoredData() {
      ScriptController.Instance.compound = new NBTTagCompound();
      ScriptController.Instance.shouldSave = true;
   }

   public void explode(double x, double y, double z, float range, boolean fire, boolean grief) {
      this.world.newExplosion((Entity)null, x, y, z, range, fire, grief);
   }

   public ScriptPlayer[] getAllServerPlayers() {
      List list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
      ScriptPlayer[] arr = new ScriptPlayer[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         arr[i] = (ScriptPlayer)ScriptController.Instance.getScriptForEntity((Entity)list.get(i));
      }

      return arr;
   }

   public String getBiomeName(int x, int z) {
      return this.world.getBiomeGenForCoords(x, z).biomeName;
   }

   public ScriptEntity spawnClone(int x, int y, int z, int tab, String name) {
      NBTTagCompound compound = ServerCloneController.Instance.getCloneData((ICommandSender)null, name, tab);
      if(compound == null) {
         return null;
      } else {
         Entity entity = NoppesUtilServer.spawnClone(compound, x, y, z, this.world);
         return entity == null?null:ScriptController.Instance.getScriptForEntity(entity);
      }
   }

   public ScriptScoreboard getScoreboard() {
      return new ScriptScoreboard();
   }

   public World getMCWorld() {
      return this.world;
   }

}
