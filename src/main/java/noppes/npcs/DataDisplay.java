package noppes.npcs;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Random;
import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.ValueUtil;

public class DataDisplay {

   EntityNPCInterface npc;
   public String name;
   public String title = "";
   public byte skinType = 0;
   public String url = "";
   public GameProfile playerProfile;
   public String texture = "customnpcs:textures/entity/humanmale/Steve.png";
   public String cloakTexture = "";
   public String glowTexture = "";
   public int visible = 0;
   public int modelSize = 5;
   public int showName = 0;
   public boolean NoLivingAnimation = false;
   public byte showBossBar = 0;


   public DataDisplay(EntityNPCInterface npc) {
      this.npc = npc;
      String[] names = new String[]{"Noppes", "Noppes", "Noppes", "Noppes", "Atesson", "Rothcersul", "Achdranys", "Pegato", "Chald", "Gareld", "Nalworche", "Ineald", "Tia\'kim", "Torerod", "Turturdar", "Ranler", "Dyntan", "Oldrake", "Gharis", "Elmn", "Tanal", "Waran-ess", "Ach-aldhat", "Athi", "Itageray", "Tasr", "Ightech", "Gakih", "Adkal", "Qua\'an", "Sieq", "Urnp", "Rods", "Vorbani", "Smaik", "Fian", "Hir", "Ristai", "Kineth", "Naif", "Issraya", "Arisotura", "Honf", "Rilfom", "Estz", "Ghatroth", "Yosil", "Darage", "Aldny", "Tyltran", "Armos", "Loxiku", "Burhat", "Tinlt", "Ightyd", "Mia", "Ken", "Karla", "Lily", "Carina", "Daniel", "Slater", "Zidane", "Valentine", "Eirina", "Carnow", "Grave", "Shadow", "Drakken", "Kaoz", "Silk", "Drake", "Oldam", "Lynxx", "Lenyx", "Winter", "Seth", "Apolitho", "Amethyst", "Ankin", "Seinkan", "Ayumu", "Sakamoto", "Divina", "Div", "Magia", "Magnus", "Tiakono", "Ruin", "Hailinx", "Ethan", "Wate", "Carter", "William", "Brion", "Sparrow", "Basrrelen", "Gyaku", "Claire", "Crowfeather", "Blackwell", "Raven", "Farcri", "Lucas", "Bangheart", "Kamoku", "Kyoukan", "Blaze", "Benjamin", "Larianne", "Kakaragon", "Melancholy", "Epodyno", "Thanato", "Mika", "Dacks", "Ylander", "Neve", "Meadow", "Cuero", "Embrera", "Eldamore", "Faolan", "Chim", "Nasu", "Kathrine", "Ariel", "Arei", "Demytrix", "Kora", "Ava", "Larson", "Leonardo", "Wyrl", "Sakiama", "Lambton", "Kederath", "Malus", "Riplette", "Andern", "Ezall", "Lucien", "Droco", "Cray", "Tymen", "Zenix", "Entranger", "Saenorath", "Chris", "Christine", "Marble", "Mable", "Ross", "Rose", "Xalgan ", "Kennet"};
      this.name = names[(new Random()).nextInt(names.length)];
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setString("Name", this.name);
      nbttagcompound.setString("Title", this.title);
      nbttagcompound.setString("SkinUrl", this.url);
      nbttagcompound.setString("Texture", this.texture);
      nbttagcompound.setString("CloakTexture", this.cloakTexture);
      nbttagcompound.setString("GlowTexture", this.glowTexture);
      nbttagcompound.setByte("UsingSkinUrl", this.skinType);
      if(this.playerProfile != null) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         NBTUtil.writeGameProfileToNBT(nbttagcompound1, this.playerProfile);
         nbttagcompound.setTag("SkinUsername", nbttagcompound1);
      }

      nbttagcompound.setInteger("Size", this.modelSize);
      nbttagcompound.setInteger("ShowName", this.showName);
      nbttagcompound.setInteger("NpcVisible", this.visible);
      nbttagcompound.setBoolean("NoLivingAnimation", this.NoLivingAnimation);
      nbttagcompound.setByte("BossBar", this.showBossBar);
      return nbttagcompound;
   }

   public void readToNBT(NBTTagCompound nbttagcompound) {
      this.name = nbttagcompound.getString("Name");
      this.title = nbttagcompound.getString("Title");
      this.url = nbttagcompound.getString("SkinUrl");
      if(nbttagcompound.hasKey("SkinUsername", 10)) {
         this.playerProfile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkinUsername"));
      } else if(nbttagcompound.hasKey("SkinUsername", 8) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkinUsername"))) {
         this.playerProfile = new GameProfile((UUID)null, nbttagcompound.getString("SkinUsername"));
         this.loadProfile();
      }

      this.texture = nbttagcompound.getString("Texture");
      this.cloakTexture = nbttagcompound.getString("CloakTexture");
      this.glowTexture = nbttagcompound.getString("GlowTexture");
      this.skinType = nbttagcompound.getByte("UsingSkinUrl");
      this.modelSize = ValueUtil.CorrectInt(nbttagcompound.getInteger("Size"), 1, 30);
      this.showName = nbttagcompound.getInteger("ShowName");
      this.visible = nbttagcompound.getInteger("NpcVisible");
      this.NoLivingAnimation = nbttagcompound.getBoolean("NoLivingAnimation");
      this.showBossBar = nbttagcompound.getByte("BossBar");
      this.npc.textureLocation = null;
      this.npc.textureGlowLocation = null;
      this.npc.textureCloakLocation = null;
      this.npc.updateHitbox();
   }

   public void loadProfile() {
      if(this.playerProfile != null && !StringUtils.isNullOrEmpty(this.playerProfile.getName()) && MinecraftServer.getServer() != null && (!this.playerProfile.isComplete() || !this.playerProfile.getProperties().containsKey("textures"))) {
         GameProfile gameprofile = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(this.playerProfile.getName());
         if(gameprofile != null) {
            Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object)null);
            if(property == null) {
               gameprofile = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(gameprofile, true);
            }

            this.playerProfile = gameprofile;
         }
      }

   }

   public boolean showName() {
      return this.npc.isKilled()?false:this.showName == 0 || this.showName == 2 && this.npc.isAttacking();
   }
}
