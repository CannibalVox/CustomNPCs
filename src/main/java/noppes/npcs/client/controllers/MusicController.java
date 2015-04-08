package noppes.npcs.client.controllers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MusicController {

   public static MusicController Instance;
   public PositionedSoundRecord playing;
   public ResourceLocation playingResource;
   public Entity playingEntity;


   public MusicController() {
      Instance = this;
   }

   public void stopMusic() {
      SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
      if(this.playing != null) {
         handler.stopSound(this.playing);
      }

      handler.stopSounds();
      this.playingResource = null;
      this.playingEntity = null;
      this.playing = null;
   }

   public void playStreaming(String music, Entity entity) {
      if(!this.isPlaying(music)) {
         this.stopMusic();
         this.playingEntity = entity;
         this.playingResource = new ResourceLocation(music);
         SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
         this.playing = PositionedSoundRecord.createRecordSoundAtPosition(this.playingResource, (float)entity.posX, (float)entity.posY, (float)entity.posZ);
         handler.playSound(this.playing);
      }
   }

   public void playMusic(String music, Entity entity) {
      if(!this.isPlaying(music)) {
         this.stopMusic();
         this.playingResource = new ResourceLocation(music);
         this.playingEntity = entity;
         SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
         this.playing = PositionedSoundRecord.createPositionedSoundRecord(this.playingResource);
         handler.playSound(this.playing);
      }
   }

   public boolean isPlaying(String music) {
      ResourceLocation resource = new ResourceLocation(music);
      return this.playingResource != null && this.playingResource.equals(resource)?Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.playing):false;
   }

   public void playSound(String music, float x, float y, float z) {
      Minecraft.getMinecraft().theWorld.playSound((double)x, (double)y, (double)z, music, 1.0F, 1.0F, false);
   }
}
