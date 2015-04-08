package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;

public class ItemSoulstoneEmpty extends Item {

   public ItemSoulstoneEmpty() {
      this.setMaxStackSize(64);
   }

   public Item setUnlocalizedName(String name) {
      super.setUnlocalizedName(name);
      GameRegistry.registerItem(this, name);
      return this;
   }

   public boolean store(EntityLivingBase entity, ItemStack stack, EntityPlayer player) {
      if(this.hasPermission(entity, player) && !(entity instanceof EntityPlayer)) {
         ItemStack stone = new ItemStack(CustomItems.soulstoneFull);
         NBTTagCompound compound = new NBTTagCompound();
         if(!entity.writeToNBTOptional(compound)) {
            return false;
         } else {
            ServerCloneController.Instance.cleanTags(compound);
            if(stone.stackTagCompound == null) {
               stone.stackTagCompound = new NBTTagCompound();
            }

            stone.stackTagCompound.setTag("Entity", compound);
            String name = EntityList.getEntityString(entity);
            if(name == null) {
               name = "generic";
            }

            stone.stackTagCompound.setString("Name", "entity." + name + ".name");
            if(entity instanceof EntityNPCInterface) {
               EntityNPCInterface npc = (EntityNPCInterface)entity;
               stone.stackTagCompound.setString("DisplayName", entity.getCommandSenderName());
               if(npc.advanced.role == EnumRoleType.Companion) {
                  RoleCompanion role = (RoleCompanion)npc.roleInterface;
                  stone.stackTagCompound.setString("ExtraText", "companion.stage,: ," + role.stage.name);
               }
            } else if(entity instanceof EntityLiving && ((EntityLiving)entity).hasCustomNameTag()) {
               stone.stackTagCompound.setString("DisplayName", ((EntityLiving)entity).getCustomNameTag());
            }

            NoppesUtilServer.GivePlayerItem(player, player, stone);
            if(!player.capabilities.isCreativeMode) {
               stack.splitStack(1);
               if(stack.stackSize <= 0) {
                  player.destroyCurrentEquippedItem();
               }
            }

            entity.isDead = true;
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean hasPermission(EntityLivingBase entity, EntityPlayer player) {
      if(NoppesUtilServer.isOp(player) && player.capabilities.isCreativeMode) {
         return true;
      } else if(CustomNpcsPermissions.hasPermission(player, "customnpcs.soulstone.all")) {
         return true;
      } else if(entity instanceof EntityNPCInterface) {
         EntityNPCInterface npc = (EntityNPCInterface)entity;
         if(npc.advanced.role == EnumRoleType.Companion) {
            RoleCompanion role = (RoleCompanion)npc.roleInterface;
            if(role.getOwner() == player) {
               return true;
            }
         }

         if(npc.advanced.role == EnumRoleType.Follower) {
            RoleFollower role1 = (RoleFollower)npc.roleInterface;
            if(role1.getOwner() == player) {
               return !role1.refuseSoulStone;
            }
         }

         return false;
      } else {
         return entity instanceof EntityAnimal?CustomNpcs.SoulStoneAnimals:false;
      }
   }
}
