package noppes.npcs.scripted.roles;

import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobPuppet;
import noppes.npcs.scripted.roles.ScriptJobInterface;

public class ScriptJobPuppet extends ScriptJobInterface {

   private JobPuppet job;


   public ScriptJobPuppet(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobPuppet)npc.jobInterface;
   }

   public int getRotationX(int part) {
      return part == 0?this.floatToInt(this.job.head.rotationX):(part == 1?this.floatToInt(this.job.body.rotationX):(part == 2?this.floatToInt(this.job.larm.rotationX):(part == 3?this.floatToInt(this.job.rarm.rotationX):(part == 4?this.floatToInt(this.job.lleg.rotationX):(part == 5?this.floatToInt(this.job.rleg.rotationX):0)))));
   }

   public int getRotationY(int part) {
      return part == 0?this.floatToInt(this.job.head.rotationY):(part == 1?this.floatToInt(this.job.body.rotationY):(part == 2?this.floatToInt(this.job.larm.rotationY):(part == 3?this.floatToInt(this.job.rarm.rotationY):(part == 4?this.floatToInt(this.job.lleg.rotationY):(part == 5?this.floatToInt(this.job.rleg.rotationY):0)))));
   }

   public int getRotationZ(int part) {
      return part == 0?this.floatToInt(this.job.head.rotationZ):(part == 1?this.floatToInt(this.job.body.rotationZ):(part == 2?this.floatToInt(this.job.larm.rotationZ):(part == 3?this.floatToInt(this.job.rarm.rotationZ):(part == 4?this.floatToInt(this.job.lleg.rotationZ):(part == 5?this.floatToInt(this.job.rleg.rotationZ):0)))));
   }

   public void setRotationX(int part, int rotation) {
      float f = (float)rotation / 360.0F - 0.5F;
      if((float)this.getRotationX(part) != f) {
         super.npc.script.clientNeedsUpdate = true;
      }

      if(part == 0) {
         this.job.head.rotationX = f;
      }

      if(part == 1) {
         this.job.body.rotationX = f;
      }

      if(part == 2) {
         this.job.larm.rotationX = f;
      }

      if(part == 3) {
         this.job.rarm.rotationX = f;
      }

      if(part == 4) {
         this.job.lleg.rotationX = f;
      }

      if(part == 5) {
         this.job.rleg.rotationX = f;
      }

   }

   public void setRotationY(int part, int rotation) {
      float f = (float)rotation / 360.0F - 0.5F;
      if((float)this.getRotationY(part) != f) {
         super.npc.script.clientNeedsUpdate = true;
      }

      if(part == 0) {
         this.job.head.rotationY = f;
      }

      if(part == 1) {
         this.job.body.rotationY = f;
      }

      if(part == 2) {
         this.job.larm.rotationY = f;
      }

      if(part == 3) {
         this.job.rarm.rotationY = f;
      }

      if(part == 4) {
         this.job.lleg.rotationY = f;
      }

      if(part == 5) {
         this.job.rleg.rotationY = f;
      }

   }

   public void setRotationZ(int part, int rotation) {
      float f = (float)rotation / 360.0F - 0.5F;
      if((float)this.getRotationZ(part) != f) {
         super.npc.script.clientNeedsUpdate = true;
      }

      if(part == 0) {
         this.job.head.rotationZ = f;
      }

      if(part == 1) {
         this.job.body.rotationZ = f;
      }

      if(part == 2) {
         this.job.larm.rotationZ = f;
      }

      if(part == 3) {
         this.job.rarm.rotationZ = f;
      }

      if(part == 4) {
         this.job.lleg.rotationZ = f;
      }

      if(part == 5) {
         this.job.rleg.rotationZ = f;
      }

   }

   public boolean isEnabled(int part) {
      return part == 0?!this.job.head.disabled:(part == 1?!this.job.body.disabled:(part == 2?!this.job.larm.disabled:(part == 3?!this.job.rarm.disabled:(part == 4?!this.job.lleg.disabled:(part == 5?!this.job.rleg.disabled:false)))));
   }

   public void setEnabled(int part, boolean bo) {
      if(this.isEnabled(part) != bo) {
         super.npc.script.clientNeedsUpdate = true;
      }

      if(part == 0) {
         this.job.head.disabled = !bo;
      }

      if(part == 1) {
         this.job.body.disabled = !bo;
      }

      if(part == 2) {
         this.job.larm.disabled = !bo;
      }

      if(part == 3) {
         this.job.rarm.disabled = !bo;
      }

      if(part == 4) {
         this.job.lleg.disabled = !bo;
      }

      if(part == 5) {
         this.job.rleg.disabled = !bo;
      }

   }

   private int floatToInt(float f) {
      return (int)(((double)f + 0.5D) * 360.0D);
   }

   public int getType() {
      return 8;
   }
}
