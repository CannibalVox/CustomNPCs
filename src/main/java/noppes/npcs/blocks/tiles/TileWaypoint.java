package noppes.npcs.blocks.tiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.quests.QuestLocation;

public class TileWaypoint extends TileEntity {

   public String name = "";
   private int ticks = 10;
   private List recentlyChecked = new ArrayList();
   private List toCheck;
   public int range = 10;


   public void updateEntity() {
      if(!super.worldObj.isRemote && !this.name.isEmpty()) {
         --this.ticks;
         if(this.ticks <= 0) {
            this.ticks = 10;
            this.toCheck = this.getPlayerList(this.range, this.range, this.range);
            this.toCheck.removeAll(this.recentlyChecked);
            List listMax = this.getPlayerList(this.range + 10, this.range + 10, this.range + 10);
            this.recentlyChecked.retainAll(listMax);
            this.recentlyChecked.addAll(this.toCheck);
            if(!this.toCheck.isEmpty()) {
               Iterator var2 = this.toCheck.iterator();

               while(var2.hasNext()) {
                  EntityPlayer player = (EntityPlayer)var2.next();
                  PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
                  Iterator var5 = playerdata.activeQuests.values().iterator();

                  while(var5.hasNext()) {
                     QuestData data = (QuestData)var5.next();
                     if(data.quest.type == EnumQuestType.Location) {
                        QuestLocation quest = (QuestLocation)data.quest.questInterface;
                        if(quest.setFound(data, this.name)) {
                           player.addChatMessage(new ChatComponentTranslation(this.name + " " + StatCollector.translateToLocal("quest.found"), new Object[0]));
                           playerdata.checkQuestCompletion(player, EnumQuestType.Location);
                        }
                     }
                  }
               }

            }
         }
      }
   }

   private List getPlayerList(int x, int y, int z) {
      return super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand((double)x, (double)y, (double)z));
   }

   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.name = compound.getString("LocationName");
      this.range = compound.getInteger("LocationRange");
      if(this.range < 2) {
         this.range = 2;
      }

   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      if(!this.name.isEmpty()) {
         compound.setString("LocationName", this.name);
      }

      compound.setInteger("LocationRange", this.range);
   }
}
