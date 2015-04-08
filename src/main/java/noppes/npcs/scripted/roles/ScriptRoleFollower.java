package noppes.npcs.scripted.roles;

import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.scripted.ScriptPlayer;
import noppes.npcs.scripted.roles.ScriptRoleInterface;

public class ScriptRoleFollower extends ScriptRoleInterface {

   private RoleFollower role;


   public ScriptRoleFollower(EntityNPCInterface npc) {
      super(npc);
      this.role = (RoleFollower)npc.roleInterface;
   }

   public void setOwner(ScriptPlayer player) {
      if(player != null && player.getMinecraftEntity() != null) {
         EntityPlayer mcplayer = (EntityPlayer)player.getMinecraftEntity();
         this.role.setOwner(mcplayer);
      } else {
         this.role.setOwner((EntityPlayer)null);
      }
   }

   public ScriptPlayer getOwner() {
      return this.role.owner == null?null:(ScriptPlayer)ScriptController.Instance.getScriptForEntity(this.role.owner);
   }

   public boolean hasOwner() {
      return this.role.owner != null;
   }

   public int getDaysLeft() {
      return this.role.getDaysLeft();
   }

   public void addDaysLeft(int days) {
      this.role.addDays(days);
   }

   public boolean getInfiniteDays() {
      return this.role.infiniteDays;
   }

   public void setInfiniteDays(boolean infinite) {
      this.role.infiniteDays = infinite;
   }

   public boolean getGuiDisabled() {
      return this.role.disableGui;
   }

   public void setGuiDisabled(boolean disabled) {
      this.role.disableGui = disabled;
   }

   public int getType() {
      return 3;
   }
}
