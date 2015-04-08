package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;

public class ItemSoulstoneFilled extends Item {

   public ItemSoulstoneFilled() {
      this.setMaxStackSize(1);
   }

   public Item setUnlocalizedName(String name) {
      super.setUnlocalizedName(name);
      GameRegistry.registerItem(this, name);
      return this;
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bo) {
      if(stack.stackTagCompound != null && stack.stackTagCompound.hasKey("Entity", 10)) {
         String name = StatCollector.translateToLocal(stack.stackTagCompound.getString("Name"));
         if(stack.stackTagCompound.hasKey("DisplayName")) {
            name = stack.stackTagCompound.getString("DisplayName") + " (" + name + ")";
         }

         list.add(EnumChatFormatting.BLUE + name);
         if(stack.stackTagCompound.hasKey("ExtraText")) {
            String text = "";
            String[] split = stack.stackTagCompound.getString("ExtraText").split(",");
            String[] var8 = split;
            int var9 = split.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String s = var8[var10];
               text = text + StatCollector.translateToLocal(s);
            }

            list.add(text);
         }

      } else {
         list.add(EnumChatFormatting.RED + "Error");
      }
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      if(!this.spawn(player, stack, world, x, y, z)) {
         return false;
      } else {
         if(!player.capabilities.isCreativeMode) {
            stack.splitStack(1);
         }

         return true;
      }
   }

   public boolean spawn(EntityPlayer player, ItemStack stack, World world, int x, int y, int z) {
      if(world.isRemote) {
         return true;
      } else if(stack.stackTagCompound != null && stack.stackTagCompound.hasKey("Entity", 10)) {
         NBTTagCompound compound = stack.stackTagCompound.getCompoundTag("Entity");
         Entity entity = EntityList.createEntityFromNBT(compound, world);
         if(entity == null) {
            return false;
         } else {
            entity.setPosition((double)x + 0.5D, (double)((float)(y + 1) + 0.2F), (double)z + 0.5D);
            if(entity instanceof EntityNPCInterface) {
               EntityNPCInterface npc = (EntityNPCInterface)entity;
               npc.ai.startPos = new int[]{x, y, z};
               npc.setHealth(npc.getMaxHealth());
               npc.setPosition((double)((float)x + 0.5F), npc.getStartYPos(), (double)((float)z + 0.5F));
               if(npc.advanced.role == EnumRoleType.Companion && player != null) {
                  PlayerData data = PlayerDataController.instance.getPlayerData(player);
                  if(data.hasCompanion()) {
                     return false;
                  }

                  ((RoleCompanion)npc.roleInterface).setOwner(player);
                  data.setCompanion(npc);
               }

               if(npc.advanced.role == EnumRoleType.Follower && player != null) {
                  ((RoleFollower)npc.roleInterface).setOwner(player);
               }
            }

            world.spawnEntityInWorld(entity);
            return true;
         }
      } else {
         return false;
      }
   }
}
