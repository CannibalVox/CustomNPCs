package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerFactionData;

public class FactionOptions {

   public boolean decreaseFactionPoints = false;
   public boolean decreaseFaction2Points = false;
   public int factionId = -1;
   public int faction2Id = -1;
   public int factionPoints = 100;
   public int faction2Points = 100;


   public void readFromNBT(NBTTagCompound compound) {
      this.factionId = compound.getInteger("OptionFactions1");
      this.faction2Id = compound.getInteger("OptionFactions2");
      this.decreaseFactionPoints = compound.getBoolean("DecreaseFaction1Points");
      this.decreaseFaction2Points = compound.getBoolean("DecreaseFaction2Points");
      this.factionPoints = compound.getInteger("OptionFaction1Points");
      this.faction2Points = compound.getInteger("OptionFaction2Points");
   }

   public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setInteger("OptionFactions1", this.factionId);
      par1NBTTagCompound.setInteger("OptionFactions2", this.faction2Id);
      par1NBTTagCompound.setInteger("OptionFaction1Points", this.factionPoints);
      par1NBTTagCompound.setInteger("OptionFaction2Points", this.faction2Points);
      par1NBTTagCompound.setBoolean("DecreaseFaction1Points", this.decreaseFactionPoints);
      par1NBTTagCompound.setBoolean("DecreaseFaction2Points", this.decreaseFaction2Points);
      return par1NBTTagCompound;
   }

   public boolean hasFaction(int id) {
      return this.factionId == id || this.faction2Id == id;
   }

   public void addPoints(EntityPlayer player) {
      if(this.factionId >= 0 || this.faction2Id >= 0) {
         PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
         if(this.factionId >= 0 && this.factionPoints > 0) {
            this.addPoints(player, data, this.factionId, this.decreaseFactionPoints, this.factionPoints);
         }

         if(this.faction2Id >= 0 && this.faction2Points > 0) {
            this.addPoints(player, data, this.faction2Id, this.decreaseFaction2Points, this.faction2Points);
         }

      }
   }

   private void addPoints(EntityPlayer player, PlayerFactionData data, int factionId, boolean decrease, int points) {
      Faction faction = FactionController.getInstance().getFaction(factionId);
      if(faction != null) {
         if(!faction.hideFaction) {
            String message = decrease?"faction.decreasepoints":"faction.increasepoints";
            player.addChatMessage(new ChatComponentTranslation(message, new Object[]{faction.name, Integer.valueOf(points)}));
         }

         data.increasePoints(factionId, decrease?-points:points);
      }
   }
}
