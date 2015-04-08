package noppes.npcs;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.ICompatibilty;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.Lines;
import noppes.npcs.entity.EntityNPCInterface;

public class VersionCompatibility {

   public static int ModRev = 16;


   public static void CheckNpcCompatibility(EntityNPCInterface npc, NBTTagCompound compound) {
      if(npc.npcVersion != ModRev) {
         if(npc.npcVersion < 12) {
            CompatabilityFix(compound, npc.advanced.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.ai.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.stats.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.display.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.inventory.writeEntityToNBT(new NBTTagCompound()));
         }

         if(npc.npcVersion < 5) {
            String bo = compound.getString("Texture");
            bo = bo.replace("/mob/customnpcs/", "customnpcs:textures/entity/");
            bo = bo.replace("/mob/", "customnpcs:textures/entity/");
            compound.setString("Texture", bo);
         }

         int y;
         int var15;
         if(npc.npcVersion < 6 && compound.getTag("NpcInteractLines") instanceof NBTTagList) {
            List var9 = NBTTags.getStringList(compound.getTagList("NpcInteractLines", 10));
            Lines comp = new Lines();

            for(y = 0; y < var9.size(); ++y) {
               Line x = new Line();
               x.text = (String)var9.toArray()[y];
               comp.lines.put(Integer.valueOf(y), x);
            }

            compound.setTag("NpcInteractLines", comp.writeToNBT());
            List var14 = NBTTags.getStringList(compound.getTagList("NpcLines", 10));
            comp = new Lines();

            for(var15 = 0; var15 < var14.size(); ++var15) {
               Line killedLines = new Line();
               killedLines.text = (String)var14.toArray()[var15];
               comp.lines.put(Integer.valueOf(var15), killedLines);
            }

            compound.setTag("NpcLines", comp.writeToNBT());
            List var16 = NBTTags.getStringList(compound.getTagList("NpcAttackLines", 10));
            comp = new Lines();

            for(int var17 = 0; var17 < var16.size(); ++var17) {
               Line i = new Line();
               i.text = (String)var16.toArray()[var17];
               comp.lines.put(Integer.valueOf(var17), i);
            }

            compound.setTag("NpcAttackLines", comp.writeToNBT());
            List var18 = NBTTags.getStringList(compound.getTagList("NpcKilledLines", 10));
            comp = new Lines();

            for(int var19 = 0; var19 < var18.size(); ++var19) {
               Line line = new Line();
               line.text = (String)var18.toArray()[var19];
               comp.lines.put(Integer.valueOf(var19), line);
            }

            compound.setTag("NpcKilledLines", comp.writeToNBT());
         }

         if(npc.npcVersion == 12) {
            NBTTagList var10 = compound.getTagList("StartPos", 3);
            if(var10.tagCount() == 3) {
               int var12 = ((NBTTagInt)var10.removeTag(2)).getInt();
               y = ((NBTTagInt)var10.removeTag(1)).getInt();
               var15 = ((NBTTagInt)var10.removeTag(0)).getInt();
               compound.setIntArray("StartPosNew", new int[]{var15, y, var12});
            }
         }

         if(npc.npcVersion == 13) {
            boolean var11 = compound.getBoolean("HealthRegen");
            compound.setInteger("HealthRegen", var11?1:0);
            NBTTagCompound var13 = compound.getCompoundTag("TransformStats");
            var11 = var13.getBoolean("HealthRegen");
            var13.setInteger("HealthRegen", var11?1:0);
            compound.setTag("TransformStats", var13);
         }

         npc.npcVersion = ModRev;
      }
   }

   public static void CheckAvailabilityCompatibility(ICompatibilty compatibilty, NBTTagCompound compound) {
      if(compatibilty.getVersion() != ModRev) {
         CompatabilityFix(compound, compatibilty.writeToNBT(new NBTTagCompound()));
         compatibilty.setVersion(ModRev);
      }
   }

   private static void CompatabilityFix(NBTTagCompound compound, NBTTagCompound check) {
      Set tags = check.getKeySet();
      Iterator var3 = tags.iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         NBTBase nbt = check.getTag(name);
         if(!compound.hasKey(name)) {
            compound.setTag(name, nbt);
         } else if(nbt instanceof NBTTagCompound && compound.getTag(name) instanceof NBTTagCompound) {
            CompatabilityFix(compound.getCompoundTag(name), (NBTTagCompound)nbt);
         }
      }

   }

}
