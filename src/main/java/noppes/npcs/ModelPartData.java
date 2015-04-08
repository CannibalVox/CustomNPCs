package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ModelPartData {

   public int color = 16777215;
   public String texture;
   public byte type = 0;
   public boolean playerTexture;
   private ResourceLocation location;


   public ModelPartData() {
      this.playerTexture = true;
   }

   public ModelPartData(String texture) {
      this.texture = texture;
      this.playerTexture = false;
   }

   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setByte("Type", this.type);
      compound.setInteger("Color", this.color);
      if(this.texture != null && !this.texture.isEmpty()) {
         compound.setString("Texture", this.texture);
      }

      compound.setBoolean("PlayerTexture", this.playerTexture);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.type = compound.getByte("Type");
      this.color = compound.getInteger("Color");
      this.texture = compound.getString("Texture");
      this.playerTexture = compound.getBoolean("PlayerTexture");
      this.location = null;
   }

   public ResourceLocation getResource() {
      if(this.texture.isEmpty()) {
         return null;
      } else if(this.location != null) {
         return this.location;
      } else {
         this.location = new ResourceLocation(this.texture);
         return this.location;
      }
   }

   public void setTexture(String texture, int type) {
      this.type = (byte)type;
      this.location = null;
      if(texture.isEmpty()) {
         this.playerTexture = true;
         this.texture = texture;
      } else {
         this.texture = "moreplayermodels:textures/" + texture + ".png";
         this.playerTexture = false;
      }

   }

   public String toString() {
      return "Color: " + this.color + " Type: " + this.type;
   }

   public String getColor() {
      String str;
      for(str = Integer.toHexString(this.color); str.length() < 6; str = "0" + str) {
         ;
      }

      return str;
   }
}
