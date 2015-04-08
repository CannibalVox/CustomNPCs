package noppes.npcs.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.ScriptController;

public class ScriptContainer {

   public String fullscript = "";
   public String script = "";
   public String console = "";
   public boolean errored = false;
   public List scripts = new ArrayList();
   private long lastCreated = 0L;
   private CompiledScript compScript = null;


   public void readFromNBT(NBTTagCompound compound) {
      this.script = compound.getString("Script");
      this.console = compound.getString("ScriptConsole");
      this.scripts = NBTTags.getStringList(compound.getTagList("ScriptList", 10));
      this.lastCreated = 0L;
   }

   public void writeToNBT(NBTTagCompound compound) {
      compound.setString("Script", this.script);
      compound.setString("ScriptConsole", this.console);
      compound.setTag("ScriptList", NBTTags.nbtStringList(this.scripts));
   }

   public String getCode() {
      if(ScriptController.Instance.lastLoaded > this.lastCreated) {
         this.lastCreated = ScriptController.Instance.lastLoaded;
         this.fullscript = this.script;
         if(!this.fullscript.isEmpty()) {
            this.fullscript = this.fullscript + "\n";
         }

         Iterator var1 = this.scripts.iterator();

         while(var1.hasNext()) {
            String loc = (String)var1.next();
            String code = (String)ScriptController.Instance.scripts.get(loc);
            if(code != null && !code.isEmpty()) {
               this.fullscript = this.fullscript + code + "\n";
            }
         }

         this.compScript = null;
      }

      return this.fullscript;
   }

   public void run(ScriptEngine engine) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      engine.getContext().setWriter(pw);
      engine.getContext().setErrorWriter(pw);

      try {
         if(this.compScript == null && engine instanceof Compilable) {
            this.compScript = ((Compilable)engine).compile(this.getCode());
         }

         if(this.compScript != null) {
            this.compScript.eval(engine.getContext());
         } else {
            engine.eval(this.getCode());
         }
      } catch (Exception var5) {
         this.errored = true;
         this.appandConsole(var5.getMessage());
      }

      this.appandConsole(sw.getBuffer().toString().trim());
   }

   public void appandConsole(String message) {
      if(!message.isEmpty()) {
         this.console = message + "\n" + this.console;
      }
   }

   public boolean hasCode() {
      return !this.getCode().isEmpty();
   }
}
