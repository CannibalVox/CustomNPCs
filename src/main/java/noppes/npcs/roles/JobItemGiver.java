package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.NBTTags;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.GlobalDataController;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerItemGiverData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobItemGiver extends JobInterface {

   public int cooldownType = 0;
   public int givingMethod = 0;
   public int cooldown = 10;
   public NpcMiscInventory inventory = new NpcMiscInventory(9);
   public int itemGiverId = 0;
   public List lines = new ArrayList();
   private int ticks = 10;
   private List recentlyChecked = new ArrayList();
   private List toCheck;
   public Availability availability = new Availability();


   public JobItemGiver(EntityNPCInterface npc) {
      super(npc);
      this.lines.add("Have these items {player}");
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("igCooldownType", this.cooldownType);
      nbttagcompound.setInteger("igGivingMethod", this.givingMethod);
      nbttagcompound.setInteger("igCooldown", this.cooldown);
      nbttagcompound.setInteger("ItemGiverId", this.itemGiverId);
      nbttagcompound.setTag("igLines", NBTTags.nbtStringList(this.lines));
      nbttagcompound.setTag("igJobInventory", this.inventory.getToNBT());
      nbttagcompound.setTag("igAvailability", this.availability.writeToNBT(new NBTTagCompound()));
      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.itemGiverId = nbttagcompound.getInteger("ItemGiverId");
      this.cooldownType = nbttagcompound.getInteger("igCooldownType");
      this.givingMethod = nbttagcompound.getInteger("igGivingMethod");
      this.cooldown = nbttagcompound.getInteger("igCooldown");
      this.lines = NBTTags.getStringList(nbttagcompound.getTagList("igLines", 10));
      this.inventory.setFromNBT(nbttagcompound.getCompoundTag("igJobInventory"));
      if(this.itemGiverId == 0 && GlobalDataController.instance != null) {
         this.itemGiverId = GlobalDataController.instance.incrementItemGiverId();
      }

      this.availability.readFromNBT(nbttagcompound.getCompoundTag("igAvailability"));
   }

   public NBTTagList newHashMapNBTList(HashMap lines) {
      NBTTagList nbttaglist = new NBTTagList();
      Iterator var4 = lines.keySet().iterator();

      while(var4.hasNext()) {
         String s = (String)var4.next();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setString("Line", s);
         nbttagcompound.setLong("Time", ((Long)lines.get(s)).longValue());
         nbttaglist.appendTag(nbttagcompound);
      }

      return nbttaglist;
   }

   public HashMap getNBTLines(NBTTagList tagList) {
      HashMap map = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         String line = nbttagcompound.getString("Line");
         long time = nbttagcompound.getLong("Time");
         map.put(line, Long.valueOf(time));
      }

      return map;
   }

   private boolean giveItems(EntityPlayer player) {
      PlayerItemGiverData data = PlayerDataController.instance.getPlayerData(player).itemgiverData;
      if(!this.canPlayerInteract(data)) {
         return false;
      } else {
         Vector items = new Vector();
         Vector toGive = new Vector();
         Iterator itemIndex = this.inventory.items.values().iterator();

         ItemStack i;
         while(itemIndex.hasNext()) {
            i = (ItemStack)itemIndex.next();
            if(i != null) {
               items.add(i.copy());
            }
         }

         if(items.isEmpty()) {
            return false;
         } else {
            if(this.isAllGiver()) {
               toGive = items;
            } else if(this.isRemainingGiver()) {
               itemIndex = items.iterator();

               while(itemIndex.hasNext()) {
                  i = (ItemStack)itemIndex.next();
                  if(!this.playerHasItem(player, i.getItem())) {
                     toGive.add(i);
                  }
               }
            } else if(this.isRandomGiver()) {
               toGive.add(((ItemStack)items.get(super.npc.worldObj.rand.nextInt(items.size()))).copy());
            } else if(this.isGiverWhenNotOwnedAny()) {
               boolean var9 = false;
               Iterator var11 = items.iterator();

               while(var11.hasNext()) {
                  ItemStack is = (ItemStack)var11.next();
                  if(this.playerHasItem(player, is.getItem())) {
                     var9 = true;
                     break;
                  }
               }

               if(var9) {
                  return false;
               }

               toGive = items;
            } else if(this.isChainedGiver()) {
               int var10 = data.getItemIndex(this);
               int var12 = 0;

               for(Iterator var13 = this.inventory.items.values().iterator(); var13.hasNext(); ++var12) {
                  ItemStack item = (ItemStack)var13.next();
                  if(var12 == var10) {
                     toGive.add(item);
                     break;
                  }
               }
            }

            if(toGive.isEmpty()) {
               return false;
            } else if(this.givePlayerItems(player, toGive)) {
               if(!this.lines.isEmpty()) {
                  super.npc.say(player, new Line((String)this.lines.get(super.npc.getRNG().nextInt(this.lines.size()))));
               }

               if(this.isDaily()) {
                  data.setTime(this, (long)this.getDay());
               } else {
                  data.setTime(this, System.currentTimeMillis());
               }

               if(this.isChainedGiver()) {
                  data.setItemIndex(this, (data.getItemIndex(this) + 1) % this.inventory.items.size());
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   private int getDay() {
      return (int)(super.npc.worldObj.getTotalWorldTime() / 24000L);
   }

   private boolean canPlayerInteract(PlayerItemGiverData data) {
      return this.inventory.items.isEmpty()?false:(this.isOnTimer()?(!data.hasInteractedBefore(this)?true:data.getTime(this) + (long)(this.cooldown * 1000) < System.currentTimeMillis()):(this.isGiveOnce()?!data.hasInteractedBefore(this):(this.isDaily()?(!data.hasInteractedBefore(this)?true:(long)this.getDay() > data.getTime(this)):false)));
   }

   private boolean givePlayerItems(EntityPlayer player, Vector toGive) {
      if(toGive.isEmpty()) {
         return false;
      } else if(this.freeInventorySlots(player) < toGive.size()) {
         return false;
      } else {
         Iterator var3 = toGive.iterator();

         while(var3.hasNext()) {
            ItemStack is = (ItemStack)var3.next();
            super.npc.givePlayerItem(player, is);
         }

         return true;
      }
   }

   private boolean playerHasItem(EntityPlayer player, Item item) {
      ItemStack[] var3 = player.inventory.mainInventory;
      int var4 = var3.length;

      int var5;
      ItemStack is;
      for(var5 = 0; var5 < var4; ++var5) {
         is = var3[var5];
         if(is != null && is.getItem() == item) {
            return true;
         }
      }

      var3 = player.inventory.armorInventory;
      var4 = var3.length;

      for(var5 = 0; var5 < var4; ++var5) {
         is = var3[var5];
         if(is != null && is.getItem() == item) {
            return true;
         }
      }

      return false;
   }

   private int freeInventorySlots(EntityPlayer player) {
      int i = 0;
      ItemStack[] var3 = player.inventory.mainInventory;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack is = var3[var5];
         if(is == null) {
            ++i;
         }
      }

      return i;
   }

   private boolean isRandomGiver() {
      return this.givingMethod == 0;
   }

   private boolean isAllGiver() {
      return this.givingMethod == 1;
   }

   private boolean isRemainingGiver() {
      return this.givingMethod == 2;
   }

   private boolean isGiverWhenNotOwnedAny() {
      return this.givingMethod == 3;
   }

   private boolean isChainedGiver() {
      return this.givingMethod == 4;
   }

   public boolean isOnTimer() {
      return this.cooldownType == 0;
   }

   private boolean isGiveOnce() {
      return this.cooldownType == 1;
   }

   private boolean isDaily() {
      return this.cooldownType == 2;
   }

   public boolean aiShouldExecute() {
      if(super.npc.isAttacking()) {
         return false;
      } else {
         --this.ticks;
         if(this.ticks > 0) {
            return false;
         } else {
            this.ticks = 10;
            this.toCheck = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand(3.0D, 3.0D, 3.0D));
            this.toCheck.removeAll(this.recentlyChecked);
            List listMax = super.npc.worldObj.getEntitiesWithinAABB(EntityPlayer.class, super.npc.boundingBox.expand(10.0D, 10.0D, 10.0D));
            this.recentlyChecked.retainAll(listMax);
            this.recentlyChecked.addAll(this.toCheck);
            return this.toCheck.size() > 0;
         }
      }
   }

   public void aiStartExecuting() {
      Iterator var1 = this.toCheck.iterator();

      while(var1.hasNext()) {
         EntityPlayer player = (EntityPlayer)var1.next();
         if(super.npc.canSee(player) && this.availability.isAvailable(player)) {
            this.recentlyChecked.add(player);
            this.interact(player);
         }
      }

   }

   public void killed() {}

   private boolean interact(EntityPlayer player) {
      if(!this.giveItems(player)) {
         super.npc.say(player, super.npc.advanced.getInteractLine());
      }

      return true;
   }

   public void delete() {}
}
