package noppes.npcs.scripted;


public class ScriptEvent {

   private boolean isCancelled = false;


   public void setCancelled(boolean bo) {
      this.isCancelled = bo;
   }

   public boolean isCancelled() {
      return this.isCancelled;
   }
}
