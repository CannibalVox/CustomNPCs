package noppes.npcs.util;

import com.google.common.io.Files;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.util.JsonException;
import org.apache.commons.io.Charsets;

public class NBTJsonUtil {

   public static String Convert(NBTTagCompound compound) {
      ArrayList list = new ArrayList();
      NBTJsonUtil.JsonLine line = ReadTag("", compound, list);
      line.removeComma();
      return ConvertList(list);
   }

   public static NBTTagCompound Convert(String json) throws JsonException {
      json = json.trim();
      NBTJsonUtil.JsonFile file = new NBTJsonUtil.JsonFile(json);
      if(json.startsWith("{") && json.endsWith("}")) {
         NBTTagCompound compound = new NBTTagCompound();
         FillCompound(compound, file);
         return compound;
      } else {
         throw new JsonException("Not properly incapsulated between { }", file);
      }
   }

   public static void FillCompound(NBTTagCompound compound, NBTJsonUtil.JsonFile json) throws JsonException {
      if(json.startsWith(new String[]{"{"}) || json.startsWith(new String[]{","})) {
         json.cut(1);
      }

      if(!json.startsWith(new String[]{"}"})) {
         int index = json.indexOf(":");
         if(index < 1) {
            throw new JsonException("Expected key after ,", json);
         } else {
            String key = json.substring(0, index);
            json.cut(index + 1);
            Object base = ReadValue(json);
            if(base == null) {
               base = new NBTTagString();
            }

            compound.setTag(key, (NBTBase)base);
            if(json.startsWith(new String[]{","})) {
               FillCompound(compound, json);
            }

         }
      }
   }

   public static NBTBase ReadValue(NBTJsonUtil.JsonFile json) throws JsonException {
      if(json.startsWith(new String[]{"{"})) {
         NBTTagCompound var7 = new NBTTagCompound();
         FillCompound(var7, json);
         if(!json.startsWith(new String[]{"}"})) {
            throw new JsonException("Expected }", json);
         } else {
            json.cut(1);
            return var7;
         }
      } else if(json.startsWith(new String[]{"["})) {
         json.cut(1);
         NBTTagList var6 = new NBTTagList();

         for(NBTBase var8 = ReadValue(json); var8 != null; var8 = ReadValue(json)) {
            var6.appendTag(var8);
            if(!json.startsWith(new String[]{","})) {
               break;
            }

            json.cut(1);
         }

         if(!json.startsWith(new String[]{"]"})) {
            throw new JsonException("Expected ]", json);
         } else {
            json.cut(1);
            int i;
            if(var6.getTagType() == 3) {
               int[] var10 = new int[var6.tagCount()];

               for(i = 0; var6.tagCount() > 0; ++i) {
                  var10[i] = ((NBTTagInt)var6.removeTag(0)).getInt();
               }

               return new NBTTagIntArray(var10);
            } else if(var6.getTagType() != 1) {
               return var6;
            } else {
               byte[] var9 = new byte[var6.tagCount()];

               for(i = 0; var6.tagCount() > 0; ++i) {
                  var9[i] = ((NBTTagByte)var6.removeTag(0)).getByte();
               }

               return new NBTTagByteArray(var9);
            }
         }
      } else {
         String s;
         if(json.startsWith(new String[]{"\""})) {
            json.cut(1);
            s = "";

            String cut;
            for(boolean ex = false; !json.startsWith(new String[]{"\""}) || ex; s = s + cut) {
               cut = json.cutDirty(1);
               ex = cut.equals("\\");
            }

            json.cut(1);
            return new NBTTagString(s.replace("\\\"", "\""));
         } else {
            for(s = ""; !json.startsWith(new String[]{",", "]", "}"}); s = s + json.cut(1)) {
               ;
            }

            s = s.trim().toLowerCase();
            if(s.isEmpty()) {
               return null;
            } else {
               try {
                  return (NBTBase)(s.endsWith("d")?new NBTTagDouble(Double.parseDouble(s.substring(0, s.length() - 1))):(s.endsWith("f")?new NBTTagFloat(Float.parseFloat(s.substring(0, s.length() - 1))):(s.endsWith("b")?new NBTTagByte(Byte.parseByte(s.substring(0, s.length() - 1))):(s.endsWith("s")?new NBTTagShort(Short.parseShort(s.substring(0, s.length() - 1))):(s.endsWith("l")?new NBTTagLong(Long.parseLong(s.substring(0, s.length() - 1))):(s.contains(".")?new NBTTagDouble(Double.parseDouble(s)):new NBTTagInt(Integer.parseInt(s))))))));
               } catch (NumberFormatException var5) {
                  throw new JsonException("Unable to convert: " + s + " to a number", json);
               }
            }
         }
      }
   }

   private static List getListData(NBTTagList list) {
      return (List)ObfuscationReflectionHelper.getPrivateValue(NBTTagList.class, list, 0);
   }

   private static NBTJsonUtil.JsonLine ReadTag(String name, NBTBase base, List list) {
      if(!name.isEmpty()) {
         name = name + ": ";
      }

      if(base.getId() == 8) {
         String line = ((NBTTagString)base).getString();
         line = line.replace("\"", "\\\"");
         list.add(new NBTJsonUtil.JsonLine(name + "\"" + line + "\""));
      } else {
         NBTJsonUtil.JsonLine line1;
         if(base.getId() == 9) {
            list.add(new NBTJsonUtil.JsonLine(name + "["));
            NBTTagList line2 = (NBTTagList)base;
            line1 = null;
            List data = getListData(line2);

            NBTBase b;
            for(Iterator key = data.iterator(); key.hasNext(); line1 = ReadTag("", b, list)) {
               b = (NBTBase)key.next();
            }

            if(line1 != null) {
               line1.removeComma();
            }

            list.add(new NBTJsonUtil.JsonLine("]"));
         } else if(base.getId() == 10) {
            list.add(new NBTJsonUtil.JsonLine(name + "{"));
            NBTTagCompound line3 = (NBTTagCompound)base;
            line1 = null;

            Object key1;
            for(Iterator data1 = line3.getKeySet().iterator(); data1.hasNext(); line1 = ReadTag(key1.toString(), line3.getTag(key1.toString()), list)) {
               key1 = data1.next();
            }

            if(line1 != null) {
               line1.removeComma();
            }

            list.add(new NBTJsonUtil.JsonLine("}"));
         } else if(base.getId() == 11) {
            list.add(new NBTJsonUtil.JsonLine(name + base.toString().replaceFirst(",]", "]")));
         } else {
            list.add(new NBTJsonUtil.JsonLine(name + base));
         }
      }

      NBTJsonUtil.JsonLine line4 = (NBTJsonUtil.JsonLine)list.get(list.size() - 1);
      line4.line = line4.line + ",";
      return line4;
   }

   private static String ConvertList(List list) {
      String json = "";
      int tab = 0;
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         NBTJsonUtil.JsonLine tag = (NBTJsonUtil.JsonLine)var3.next();
         if(tag.reduceTab()) {
            --tab;
         }

         for(int i = 0; i < tab; ++i) {
            json = json + "    ";
         }

         json = json + tag + "\n";
         if(tag.increaseTab()) {
            ++tab;
         }
      }

      return json;
   }

   public static NBTTagCompound LoadFile(File file) throws JsonException, IOException {
      return Convert(Files.toString(file, Charsets.UTF_8));
   }

   public static void SaveFile(File file, NBTTagCompound compound) throws JsonException, IOException {
      String json = Convert(compound);
      OutputStreamWriter writer = null;

      try {
         writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
         writer.write(json);
      } finally {
         if(writer != null) {
            writer.close();
         }

      }

   }

   static class JsonFile {

      private String original;
      private String text;


      public JsonFile(String text) {
         this.text = text;
         this.original = text;
      }

      public String cutDirty(int i) {
         String s = this.text.substring(0, i);
         this.text = this.text.substring(i);
         return s;
      }

      public String cut(int i) {
         String s = this.text.substring(0, i);
         this.text = this.text.substring(i).trim();
         return s;
      }

      public String substring(int beginIndex, int endIndex) {
         return this.text.substring(beginIndex, endIndex);
      }

      public int indexOf(String s) {
         return this.text.indexOf(s);
      }

      public String getCurrentPos() {
         int lengthOr = this.original.length();
         int lengthCur = this.text.length();
         int currentPos = lengthOr - lengthCur;
         String done = this.original.substring(0, currentPos);
         String[] lines = done.split("\r\n|\r|\n");
         int pos = 0;
         String line = "";
         if(lines.length > 0) {
            pos = lines[lines.length - 1].length();
            line = this.original.split("\r\n|\r|\n")[lines.length - 1].trim();
         }

         int nextNewLine = NoppesStringUtils.nextNewLine(this.text);
         return "Line: " + lines.length + ", Pos: " + pos + ", Text: " + line;
      }

      public boolean startsWith(String ... ss) {
         String[] var2 = ss;
         int var3 = ss.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            if(this.text.startsWith(s)) {
               return true;
            }
         }

         return false;
      }

      public boolean endsWith(String s) {
         return this.text.endsWith(s);
      }
   }

   static class JsonLine {

      private String line;


      public JsonLine(String line) {
         this.line = line;
      }

      public void removeComma() {
         if(this.line.endsWith(",")) {
            this.line = this.line.substring(0, this.line.length() - 1);
         }

      }

      public boolean reduceTab() {
         int length = this.line.length();
         return length == 1 && (this.line.endsWith("}") || this.line.endsWith("]")) || length == 2 && (this.line.endsWith("},") || this.line.endsWith("],"));
      }

      public boolean increaseTab() {
         return this.line.endsWith("{") || this.line.endsWith("[");
      }

      public String toString() {
         return this.line;
      }
   }
}
