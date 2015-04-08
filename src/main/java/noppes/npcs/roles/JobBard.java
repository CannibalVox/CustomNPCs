package noppes.npcs.roles;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.constants.EnumBardInstrument;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobBard extends JobInterface {

   public int minRange = 2;
   public int maxRange = 64;
   public boolean isStreamer = true;
   public boolean hasOffRange = true;
   public String song = "";
   private EnumBardInstrument instrument;
   private long ticks;


   public JobBard(EntityNPCInterface npc) {
      super(npc);
      this.instrument = EnumBardInstrument.Banjo;
      this.ticks = 0L;
      if(CustomItems.banjo != null) {
         super.mainhand = new ItemStack(CustomItems.banjo);
         super.overrideMainHand = super.overrideOffHand = true;
      }

   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setString("BardSong", this.song);
      nbttagcompound.setInteger("BardMinRange", this.minRange);
      nbttagcompound.setInteger("BardMaxRange", this.maxRange);
      nbttagcompound.setInteger("BardInstrument", this.instrument.ordinal());
      nbttagcompound.setBoolean("BardStreamer", this.isStreamer);
      nbttagcompound.setBoolean("BardHasOff", this.hasOffRange);
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.song = nbttagcompound.getString("BardSong");
      this.minRange = nbttagcompound.getInteger("BardMinRange");
      this.maxRange = nbttagcompound.getInteger("BardMaxRange");
      this.setInstrument(nbttagcompound.getInteger("BardInstrument"));
      this.isStreamer = nbttagcompound.getBoolean("BardStreamer");
      this.hasOffRange = nbttagcompound.getBoolean("BardHasOff");
   }

   public void setInstrument(int i) {
      if(CustomItems.banjo != null) {
         this.instrument = EnumBardInstrument.values()[i];
         super.overrideMainHand = super.overrideOffHand = this.instrument != EnumBardInstrument.None;
         switch(this.instrument) {
         case None:
            super.mainhand = null;
            super.offhand = null;
            break;
         case Banjo:
            super.mainhand = new ItemStack(CustomItems.banjo);
            super.offhand = null;
            break;
         case Violin:
            super.mainhand = new ItemStack(CustomItems.violin);
            super.offhand = new ItemStack(CustomItems.violinbow);
            break;
         case Guitar:
            super.mainhand = new ItemStack(CustomItems.guitar);
            super.offhand = null;
            break;
         case Harp:
            super.mainhand = new ItemStack(CustomItems.harp);
            super.offhand = null;
            break;
         case FrenchHorn:
            super.mainhand = new ItemStack(CustomItems.frenchHorn);
            super.offhand = null;
         }

      }
   }

   public EnumBardInstrument getInstrument() {
      return this.instrument;
   }

   public void onLivingUpdate() {
      if(super.npc.isRemote()) {
         ++this.ticks;
         if(this.ticks % 10L == 0L) {
            if(!this.song.isEmpty()) {
               List list;
               if(!MusicController.Instance.isPlaying(this.song)) {
                  list = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand((double)this.minRange, (double)(this.minRange / 2), (double)this.minRange));
                  if(!list.contains(CustomNpcs.proxy.getPlayer())) {
                     return;
                  }

                  if(this.isStreamer) {
                     MusicController.Instance.playStreaming(this.song, super.npc);
                  } else {
                     MusicController.Instance.playMusic(this.song, super.npc);
                  }
               } else if(MusicController.Instance.playingEntity != super.npc) {
                  EntityPlayer list1 = CustomNpcs.proxy.getPlayer();
                  if(super.npc.getDistanceSqToEntity(list1) < MusicController.Instance.playingEntity.getDistanceSqToEntity(list1)) {
                     MusicController.Instance.playingEntity = super.npc;
                  }
               } else if(this.hasOffRange) {
                  list = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand((double)this.maxRange, (double)(this.maxRange / 2), (double)this.maxRange));
                  if(!list.contains(CustomNpcs.proxy.getPlayer())) {
                     MusicController.Instance.stopMusic();
                  }
               }

            }
         }
      }
   }

   public void killed() {
      this.delete();
   }

   public void delete() {
      if(super.npc.worldObj.isRemote && this.hasOffRange && MusicController.Instance.isPlaying(this.song)) {
         MusicController.Instance.stopMusic();
      }

   }
}
