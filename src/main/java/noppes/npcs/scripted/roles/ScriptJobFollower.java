package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobFollower;
import noppes.npcs.scripted.ScriptNpc;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobFollower extends ScriptJobInterface {

   private JobFollower job;


   public ScriptJobFollower(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobFollower)npc.jobInterface;
   }

   public String getFollowingName() {
      return this.job.name;
   }

   public void setFollowingName(String name) {
      this.job.name = name;
   }

   public ScriptNpc getFollowingNpc() {
      return !this.isFollowing()?null:this.job.following.script.dummyNpc;
   }

   public boolean isFollowing() {
      return this.job.isFollowing();
   }

   public int getType() {
      return 4;
   }
}
