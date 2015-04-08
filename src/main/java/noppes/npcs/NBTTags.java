package noppes.npcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;

public class NBTTags {

   public static HashMap getItemStackList(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);

         try {
            list.put(Integer.valueOf(nbttagcompound.getByte("Slot") & 255), ItemStack.loadItemStackFromNBT(nbttagcompound));
         } catch (ClassCastException var5) {
            list.put(Integer.valueOf(nbttagcompound.getInteger("Slot")), ItemStack.loadItemStackFromNBT(nbttagcompound));
         }
      }

      return list;
   }

   public static ItemStack[] getItemStackArray(NBTTagList tagList) {
      ItemStack[] list = new ItemStack[tagList.tagCount()];

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list[nbttagcompound.getByte("Slot") & 255] = ItemStack.loadItemStackFromNBT(nbttagcompound);
      }

      return list;
   }

   public static ArrayList getIntegerArraySet(NBTTagList tagList) {
      ArrayList set = new ArrayList();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound compound = tagList.getCompoundTagAt(i);
         set.add(compound.getIntArray("Array"));
      }

      return set;
   }

   public static HashMap getBooleanList(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.put(Integer.valueOf(nbttagcompound.getInteger("Slot")), Boolean.valueOf(nbttagcompound.getBoolean("Boolean")));
      }

      return list;
   }

   public static HashMap getIntegerIntegerMap(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.put(Integer.valueOf(nbttagcompound.getInteger("Slot")), Integer.valueOf(nbttagcompound.getInteger("Integer")));
      }

      return list;
   }

   public static HashMap getIntegerLongMap(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.put(Integer.valueOf(nbttagcompound.getInteger("Slot")), Long.valueOf(nbttagcompound.getLong("Long")));
      }

      return list;
   }

   public static HashSet getIntegerSet(NBTTagList tagList) {
      HashSet list = new HashSet();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.add(Integer.valueOf(nbttagcompound.getInteger("Integer")));
      }

      return list;
   }

   public static HashMap getStringStringMap(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.put(nbttagcompound.getString("Slot"), nbttagcompound.getString("Value"));
      }

      return list;
   }

   public static HashMap getIntegerStringMap(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.put(Integer.valueOf(nbttagcompound.getInteger("Slot")), nbttagcompound.getString("Value"));
      }

      return list;
   }

   public static HashMap getStringIntegerMap(NBTTagList tagList) {
      HashMap list = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         list.put(nbttagcompound.getString("Slot"), Integer.valueOf(nbttagcompound.getInteger("Value")));
      }

      return list;
   }

   public static HashMap getVectorMap(NBTTagList tagList) {
      HashMap map = new HashMap();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         Vector values = new Vector();
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         NBTTagList list = nbttagcompound.getTagList("Values", 10);

         for(int j = 0; j < list.tagCount(); ++j) {
            NBTTagCompound value = list.getCompoundTagAt(j);
            values.add(value.getString("Value"));
         }

         map.put(nbttagcompound.getString("Key"), values);
      }

      return map;
   }

   public static List getStringList(NBTTagList tagList) {
      ArrayList list = new ArrayList();

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         String line = nbttagcompound.getString("Line");
         list.add(line);
      }

      return list;
   }

   public static String[] getStringArray(NBTTagList tagList, int size) {
      String[] arr = new String[size];

      for(int i = 0; i < tagList.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
         String line = nbttagcompound.getString("Value");
         int slot = nbttagcompound.getInteger("Slot");
         arr[slot] = line;
      }

      return arr;
   }

   public static NBTTagList nbtIntegerArraySet(List set) {
      NBTTagList nbttaglist = new NBTTagList();
      if(set == null) {
         return nbttaglist;
      } else {
         Iterator var2 = set.iterator();

         while(var2.hasNext()) {
            int[] arr = (int[])var2.next();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setIntArray("Array", arr);
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtItemStackList(HashMap inventory) {
      NBTTagList nbttaglist = new NBTTagList();
      if(inventory == null) {
         return nbttaglist;
      } else {
         Iterator var2 = inventory.keySet().iterator();

         while(var2.hasNext()) {
            int slot = ((Integer)var2.next()).intValue();
            ItemStack item = (ItemStack)inventory.get(Integer.valueOf(slot));
            if(item != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.setByte("Slot", (byte)slot);
               item.writeToNBT(nbttagcompound);
               nbttaglist.appendTag(nbttagcompound);
            }
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtItemStackArray(ItemStack[] inventory) {
      NBTTagList nbttaglist = new NBTTagList();
      if(inventory == null) {
         return nbttaglist;
      } else {
         for(int slot = 0; slot < inventory.length; ++slot) {
            ItemStack item = inventory[slot];
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)slot);
            if(item != null) {
               item.writeToNBT(nbttagcompound);
            }

            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtBooleanList(HashMap updatedSlots) {
      NBTTagList nbttaglist = new NBTTagList();
      if(updatedSlots == null) {
         return nbttaglist;
      } else {
         HashMap inventory2 = updatedSlots;
         Iterator var3 = updatedSlots.keySet().iterator();

         while(var3.hasNext()) {
            Integer slot = (Integer)var3.next();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Slot", slot.intValue());
            nbttagcompound.setBoolean("Boolean", ((Boolean)inventory2.get(slot)).booleanValue());
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntegerIntegerMap(HashMap lines) {
      NBTTagList nbttaglist = new NBTTagList();
      if(lines == null) {
         return nbttaglist;
      } else {
         Iterator var2 = lines.keySet().iterator();

         while(var2.hasNext()) {
            int slot = ((Integer)var2.next()).intValue();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Slot", slot);
            nbttagcompound.setInteger("Integer", ((Integer)lines.get(Integer.valueOf(slot))).intValue());
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntegerLongMap(HashMap lines) {
      NBTTagList nbttaglist = new NBTTagList();
      if(lines == null) {
         return nbttaglist;
      } else {
         Iterator var2 = lines.keySet().iterator();

         while(var2.hasNext()) {
            int slot = ((Integer)var2.next()).intValue();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Slot", slot);
            nbttagcompound.setLong("Long", ((Long)lines.get(Integer.valueOf(slot))).longValue());
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntegerSet(HashSet set) {
      NBTTagList nbttaglist = new NBTTagList();
      if(set == null) {
         return nbttaglist;
      } else {
         Iterator var2 = set.iterator();

         while(var2.hasNext()) {
            int slot = ((Integer)var2.next()).intValue();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Integer", slot);
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtVectorMap(HashMap map) {
      NBTTagList list = new NBTTagList();
      if(map == null) {
         return list;
      } else {
         Iterator var2 = map.keySet().iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("Key", key);
            NBTTagList values = new NBTTagList();
            Iterator var6 = ((Vector)map.get(key)).iterator();

            while(var6.hasNext()) {
               String value = (String)var6.next();
               NBTTagCompound comp = new NBTTagCompound();
               comp.setString("Value", value);
               values.appendTag(comp);
            }

            compound.setTag("Values", values);
            list.appendTag(compound);
         }

         return list;
      }
   }

   public static NBTTagList nbtStringStringMap(HashMap map) {
      NBTTagList nbttaglist = new NBTTagList();
      if(map == null) {
         return nbttaglist;
      } else {
         Iterator var2 = map.keySet().iterator();

         while(var2.hasNext()) {
            String slot = (String)var2.next();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Slot", slot);
            nbttagcompound.setString("Value", (String)map.get(slot));
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtStringIntegerMap(HashMap map) {
      NBTTagList nbttaglist = new NBTTagList();
      if(map == null) {
         return nbttaglist;
      } else {
         Iterator var2 = map.keySet().iterator();

         while(var2.hasNext()) {
            String slot = (String)var2.next();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Slot", slot);
            nbttagcompound.setInteger("Value", ((Integer)map.get(slot)).intValue());
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTBase nbtIntegerStringMap(HashMap map) {
      NBTTagList nbttaglist = new NBTTagList();
      if(map == null) {
         return nbttaglist;
      } else {
         Iterator var2 = map.keySet().iterator();

         while(var2.hasNext()) {
            int slot = ((Integer)var2.next()).intValue();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Slot", slot);
            nbttagcompound.setString("Value", (String)map.get(Integer.valueOf(slot)));
            nbttaglist.appendTag(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtStringArray(String[] list) {
      NBTTagList nbttaglist = new NBTTagList();
      if(list == null) {
         return nbttaglist;
      } else {
         for(int i = 0; i < list.length; ++i) {
            if(list[i] != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.setString("Value", list[i]);
               nbttagcompound.setInteger("Slot", i);
               nbttaglist.appendTag(nbttagcompound);
            }
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtStringList(List list) {
      NBTTagList nbttaglist = new NBTTagList();
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setString("Line", s);
         nbttaglist.appendTag(nbttagcompound);
      }

      return nbttaglist;
   }

   public static NBTTagList nbtDoubleList(double ... par1ArrayOfDouble) {
      NBTTagList nbttaglist = new NBTTagList();
      double[] adouble = par1ArrayOfDouble;
      int i = par1ArrayOfDouble.length;

      for(int j = 0; j < i; ++j) {
         double d1 = adouble[j];
         nbttaglist.appendTag(new NBTTagDouble(d1));
      }

      return nbttaglist;
   }

   public static NBTTagCompound NBTMerge(NBTTagCompound data, NBTTagCompound merge) {
      NBTTagCompound compound = (NBTTagCompound)data.copy();
      Set names = merge.getKeySet();

      String name;
      Object base;
      for(Iterator var4 = names.iterator(); var4.hasNext(); compound.setTag(name, (NBTBase)base)) {
         name = (String)var4.next();
         base = merge.getTag(name);
         if(((NBTBase)base).getId() == 10) {
            base = NBTMerge(compound.getCompoundTag(name), (NBTTagCompound)base);
         }
      }

      return compound;
   }
}
