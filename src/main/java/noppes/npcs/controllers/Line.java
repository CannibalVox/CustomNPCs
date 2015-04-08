package noppes.npcs.controllers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Line {

   public String text = "";
   public String sound = "";
   public boolean hideText = false;


   public Line() {}

   public Line(String text) {
      this.text = text;
   }

   public Line copy() {
      Line line = new Line(this.text);
      line.sound = this.sound;
      line.hideText = this.hideText;
      return line;
   }

   public Line formatTarget(EntityLivingBase entity) {
      if(entity == null) {
         return this;
      } else {
         Line line = this.copy();
         if(entity instanceof EntityPlayer) {
            line.text = line.text.replace("@target", ((EntityPlayer)entity).getDisplayName());
         } else {
            line.text = line.text.replace("@target", entity.getCommandSenderName());
         }

         return line;
      }
   }
}
