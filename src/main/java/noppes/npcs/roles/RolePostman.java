package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RolePostman extends RoleInterface {

   public NpcMiscInventory inventory = new NpcMiscInventory(1);
   private List recentlyChecked = new ArrayList();
   private List toCheck;


   public RolePostman(EntityNPCInterface npc) {
      super(npc);
   }

   public boolean aiShouldExecute() {
      if(super.npc.ticksExisted % 20 != 0) {
         return false;
      } else {
         this.toCheck = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand(10.0D, 10.0D, 10.0D));
         this.toCheck.removeAll(this.recentlyChecked);
         List listMax = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand(20.0D, 20.0D, 20.0D));
         this.recentlyChecked.retainAll(listMax);
         this.recentlyChecked.addAll(this.toCheck);
         Iterator var2 = this.toCheck.iterator();

         while(var2.hasNext()) {
            EntityPlayer player = (EntityPlayer)var2.next();
            if(PlayerDataController.instance.hasMail(player)) {
               player.addChatMessage(new ChatComponentTranslation("You\'ve got mail", new Object[0]));
            }
         }

         return false;
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setTag("PostInv", this.inventory.getToNBT());
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.inventory.setFromNBT(nbttagcompound.getCompoundTag("PostInv"));
   }

   public void interact(EntityPlayer player) {
      player.openGui(CustomNpcs.instance, EnumGuiType.PlayerMailman.ordinal(), player.worldObj, 1, 1, 0);
   }
}
