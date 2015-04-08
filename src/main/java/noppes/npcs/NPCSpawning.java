package noppes.npcs;

import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;
import noppes.npcs.controllers.SpawnController;
import noppes.npcs.controllers.SpawnData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class NPCSpawning {

   public static void findChunksForSpawning(WorldServer world) {
      HashMap eligibleChunksForSpawning = new HashMap();

      int k1;
      int l1;
      for(int tmp = 0; tmp < world.playerEntities.size(); ++tmp) {
         EntityPlayer iterator = (EntityPlayer)world.playerEntities.get(tmp);
         int chunkcoordintpair1 = MathHelper.floor_double(iterator.posX / 16.0D);
         int chunkposition = MathHelper.floor_double(iterator.posZ / 16.0D);
         byte j1 = 8;

         for(k1 = -j1; k1 <= j1; ++k1) {
            for(l1 = -j1; l1 <= j1; ++l1) {
               boolean i = k1 == -j1 || k1 == j1 || l1 == -j1 || l1 == j1;
               ChunkCoordIntPair x = new ChunkCoordIntPair(k1 + chunkcoordintpair1, l1 + chunkposition);
               if(!i) {
                  eligibleChunksForSpawning.put(x, Boolean.valueOf(false));
               } else if(!eligibleChunksForSpawning.containsKey(x)) {
                  eligibleChunksForSpawning.put(x, Boolean.valueOf(true));
               }
            }
         }
      }

      if(countNPCs(world) <= 5 * eligibleChunksForSpawning.size()) {
         ArrayList var17 = new ArrayList(eligibleChunksForSpawning.keySet());
         Collections.shuffle(var17);
         Iterator var18 = var17.iterator();

         while(var18.hasNext()) {
            ChunkCoordIntPair var19 = (ChunkCoordIntPair)var18.next();
            if(!((Boolean)eligibleChunksForSpawning.get(var19)).booleanValue()) {
               ChunkPosition var20 = getChunk(world, var19.chunkXPos, var19.chunkZPos);
               int var21 = var20.chunkPosX;
               k1 = var20.chunkPosY;
               l1 = var20.chunkPosZ;

               for(int var22 = 0; var22 < 3; ++var22) {
                  byte b1 = 6;
                  int var23 = var21 + (world.rand.nextInt(b1) - world.rand.nextInt(b1));
                  int y = k1 + (world.rand.nextInt(1) - world.rand.nextInt(1));
                  int z = l1 + (world.rand.nextInt(b1) - world.rand.nextInt(b1));
                  Block block = world.getBlock(var23, y, z);
                  String name = world.getBiomeGenForCoords(var23, z).biomeName;
                  SpawnData data = SpawnController.instance.getRandomSpawnData(name, block.getMaterial() == Material.air);
                  if(data != null && canCreatureTypeSpawnAtLocation(data, world, var23, y, z) && world.getClosestPlayer((double)var23, (double)y, (double)z, 24.0D) == null) {
                     spawnData(data, world, var23, y, z);
                  }
               }
            }
         }

      }
   }

   public static int countNPCs(World world) {
      int count = 0;
      List list = world.loadedEntityList;
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         Entity entity = (Entity)var3.next();
         if(entity instanceof EntityNPCInterface) {
            ++count;
         }
      }

      return count;
   }

   protected static ChunkPosition getChunk(World world, int x, int z) {
      Chunk chunk = world.getChunkFromChunkCoords(x, z);
      int k = x * 16 + world.rand.nextInt(16);
      int l = z * 16 + world.rand.nextInt(16);
      int i1 = world.rand.nextInt(chunk == null?world.getActualHeight():chunk.getTopFilledSegment() + 16 - 1);
      return new ChunkPosition(k, i1, l);
   }

   public static void performWorldGenSpawning(World world, int x, int z, Random rand) {
      BiomeGenBase biome = world.getBiomeGenForCoords(x + 8, z + 8);

      while(rand.nextFloat() < biome.getSpawningChance()) {
         SpawnData data = SpawnController.instance.getRandomSpawnData(biome.biomeName, true);
         if(data != null) {
            byte size = 16;
            int j1 = x + rand.nextInt(size);
            int k1 = z + rand.nextInt(size);
            int l1 = j1;
            int i2 = k1;

            for(int k2 = 0; k2 < 4; ++k2) {
               int l2 = world.getTopSolidOrLiquidBlock(j1, k1);
               if(!canCreatureTypeSpawnAtLocation(data, world, j1, l2, k1)) {
                  j1 += rand.nextInt(5) - rand.nextInt(5);

                  for(k1 += rand.nextInt(5) - rand.nextInt(5); j1 < x || j1 >= x + size || k1 < z || k1 >= z + size; k1 = i2 + rand.nextInt(5) - rand.nextInt(5)) {
                     j1 = l1 + rand.nextInt(5) - rand.nextInt(5);
                  }
               } else if(spawnData(data, world, j1, l2, k1)) {
                  break;
               }
            }
         }
      }

   }

   private static boolean spawnData(SpawnData data, World world, int x, int y, int z) {
      EntityLiving entityliving;
      try {
         Entity canSpawn = EntityList.createEntityFromNBT(data.compound1, world);
         if(canSpawn == null || !(canSpawn instanceof EntityLiving)) {
            return false;
         }

         entityliving = (EntityLiving)canSpawn;
         if(canSpawn instanceof EntityCustomNpc) {
            EntityCustomNpc npc = (EntityCustomNpc)canSpawn;
            npc.stats.spawnCycle = 3;
            npc.ai.returnToStart = false;
            npc.ai.startPos = new int[]{x, y, z};
         }

         canSpawn.setLocationAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, world.rand.nextFloat() * 360.0F, 0.0F);
      } catch (Exception var8) {
         var8.printStackTrace();
         return false;
      }

      Result canSpawn1 = ForgeEventFactory.canEntitySpawn(entityliving, world, (float)x + 0.5F, (float)y, (float)z + 0.5F);
      if(canSpawn1 != Result.DENY && (canSpawn1 != Result.DEFAULT || entityliving.getCanSpawnHere())) {
         world.spawnEntityInWorld(entityliving);
         return true;
      } else {
         return false;
      }
   }

   public static boolean canCreatureTypeSpawnAtLocation(SpawnData data, World world, int x, int y, int z) {
      if(data.liquid) {
         return world.getBlock(x, y, z).getMaterial().isLiquid() && world.getBlock(x, y - 1, z).getMaterial().isLiquid() && !world.getBlock(x, y + 1, z).isNormalCube();
      } else if(!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
         return false;
      } else {
         Block block = world.getBlock(x, y - 1, z);
         boolean spawnBlock = block.canCreatureSpawn(EnumCreatureType.creature, world, x, y - 1, z);
         return spawnBlock && !world.getBlock(x, y, z).isNormalCube() && !world.getBlock(x, y, z).getMaterial().isLiquid() && !world.getBlock(x, y + 1, z).isNormalCube();
      }
   }
}
