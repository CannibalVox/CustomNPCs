package foxz.utils;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.util.NBTJsonUtil;

public class Market {

   public static void save(RoleTrader r, String name) {
      if(!name.isEmpty()) {
         File file = getFile(name + "_new");
         File file1 = getFile(name);

         try {
            NBTJsonUtil.SaveFile(file, r.writeToNBT(new NBTTagCompound()));
            if(file1.exists()) {
               file1.delete();
            }

            file.renameTo(file1);
         } catch (Exception var5) {
            ;
         }

      }
   }

   public static void load(RoleTrader role, String name) {
      if(!role.npc.worldObj.isRemote) {
         File file = getFile(name);
         if(file.exists()) {
            try {
               role.readFromNBT(NBTJsonUtil.LoadFile(file));
            } catch (Exception var4) {
               ;
            }

         }
      }
   }

   private static File getFile(String name) {
      File dir = new File(CustomNpcs.getWorldSaveDirectory(), "markets");
      if(!dir.exists()) {
         dir.mkdir();
      }

      return new File(dir, name.toLowerCase() + ".json");
   }

   public static void setMarket(EntityNPCInterface npc, String marketName) {
      if(!marketName.isEmpty()) {
         if(!getFile(marketName).exists()) {
            save((RoleTrader)npc.roleInterface, marketName);
         }

         load((RoleTrader)npc.roleInterface, marketName);
      }
   }
}
