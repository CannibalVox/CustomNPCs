package noppes.npcs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.script.ScriptEngine;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.ScriptEvent;
import noppes.npcs.scripted.ScriptNpc;
import noppes.npcs.scripted.ScriptWorld;
import noppes.npcs.scripted.constants.EntityType;
import noppes.npcs.scripted.constants.JobType;
import noppes.npcs.scripted.constants.RoleType;

public class DataScript {

   public Map scripts = new HashMap();
   private static final EntityType entities = new EntityType();
   private static final JobType jobs = new JobType();
   private static final RoleType roles = new RoleType();
   public String scriptLanguage = "ECMAScript";
   private EntityNPCInterface npc;
   public boolean enabled = false;
   public ScriptNpc dummyNpc;
   public ScriptWorld dummyWorld;
   public boolean clientNeedsUpdate = false;
   public boolean aiNeedsUpdate = false;
   public boolean hasInited = false;


   public DataScript(EntityNPCInterface npc) {
      this.npc = npc;
      if(npc instanceof EntityCustomNpc) {
         this.dummyNpc = new ScriptNpc((EntityCustomNpc)npc);
      }

      if(npc.worldObj instanceof WorldServer) {
         this.dummyWorld = new ScriptWorld((WorldServer)npc.worldObj);
      }

   }

   public void readFromNBT(NBTTagCompound compound) {
      this.scripts = this.readScript(compound.getTagList("ScriptsContainers", 10));
      this.scriptLanguage = compound.getString("ScriptLanguage");
      this.enabled = compound.getBoolean("ScriptEnabled");
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setTag("ScriptsContainers", this.writeScript(this.scripts));
      compound.setString("ScriptLanguage", this.scriptLanguage);
      compound.setBoolean("ScriptEnabled", this.enabled);
      return compound;
   }

   private Map readScript(NBTTagList list) {
      HashMap scripts = new HashMap();

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound compoundd = list.getCompoundTagAt(i);
         ScriptContainer script = new ScriptContainer();
         script.readFromNBT(compoundd);
         scripts.put(Integer.valueOf(compoundd.getInteger("Type")), script);
      }

      return scripts;
   }

   private NBTTagList writeScript(Map scripts) {
      NBTTagList list = new NBTTagList();
      Iterator var3 = scripts.keySet().iterator();

      while(var3.hasNext()) {
         Integer type = (Integer)var3.next();
         NBTTagCompound compoundd = new NBTTagCompound();
         compoundd.setInteger("Type", type.intValue());
         ScriptContainer script = (ScriptContainer)scripts.get(type);
         script.writeToNBT(compoundd);
         list.appendTag(compoundd);
      }

      return list;
   }

   public boolean callScript(EnumScriptType type, Object ... obs) {
      if(this.aiNeedsUpdate) {
         this.npc.updateTasks();
         this.aiNeedsUpdate = false;
      }

      if(this.clientNeedsUpdate) {
         NoppesUtilServer.updateNpc(this.npc);
         this.clientNeedsUpdate = false;
      }

      if(!this.isEnabled()) {
         return false;
      } else {
         if(!this.hasInited) {
            this.hasInited = true;
            this.callScript(EnumScriptType.INIT, new Object[0]);
         }

         ScriptContainer script = (ScriptContainer)this.scripts.get(Integer.valueOf(type.ordinal()));
         if(script != null && !script.errored) {
            ScriptEngine engine = ScriptController.Instance.getEngineByName(this.scriptLanguage);
            if(engine == null) {
               return false;
            } else {
               for(int i = 0; i + 1 < obs.length; i += 2) {
                  Object ob = obs[i + 1];
                  if(ob instanceof Entity) {
                     ob = ScriptController.Instance.getScriptForEntity((Entity)ob);
                  }

                  engine.put(obs[i].toString(), ob);
               }

               return this.callScript(engine, script);
            }
         } else {
            return false;
         }
      }
   }

   public boolean isEnabled() {
      return this.enabled && ScriptController.HasStart && !this.npc.worldObj.isRemote;
   }

   private boolean callScript(ScriptEngine engine, ScriptContainer script) {
      if(!script.hasCode()) {
         return false;
      } else {
         engine.put("npc", this.dummyNpc);
         engine.put("world", this.dummyWorld);
         ScriptEvent result = (ScriptEvent)engine.get("event");
         if(result == null) {
            engine.put("event", result = new ScriptEvent());
         }

         engine.put("EntityType", entities);
         engine.put("RoleType", roles);
         engine.put("JobType", jobs);
         script.run(engine);
         if(this.clientNeedsUpdate) {
            NoppesUtilServer.updateNpc(this.npc);
            this.clientNeedsUpdate = false;
         }

         if(this.aiNeedsUpdate) {
            this.npc.updateTasks();
            this.aiNeedsUpdate = false;
         }

         return result.isCancelled();
      }
   }

   public void setWorld(World world) {
      if(world instanceof WorldServer) {
         this.dummyWorld = new ScriptWorld((WorldServer)world);
      }

   }

}
