package noppes.npcs.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import noppes.npcs.LogWriter;
import noppes.npcs.config.ConfigProp;

public class ConfigLoader {

   private boolean updateFile = false;
   private File dir;
   private String fileName;
   private Class configClass;
   private LinkedList configFields;


   public ConfigLoader(Class clss, File dir, String fileName) {
      if(!dir.exists()) {
         dir.mkdir();
      }

      this.dir = dir;
      this.configClass = clss;
      this.configFields = new LinkedList();
      this.fileName = fileName + ".cfg";
      Field[] fields = this.configClass.getDeclaredFields();
      Field[] var5 = fields;
      int var6 = fields.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Field field = var5[var7];
         if(field.isAnnotationPresent(ConfigProp.class)) {
            this.configFields.add(field);
         }
      }

   }

   public void loadConfig() {
      try {
         File e = new File(this.dir, this.fileName);
         HashMap types = new HashMap();
         Iterator properties = this.configFields.iterator();

         while(properties.hasNext()) {
            Field field = (Field)properties.next();
            ConfigProp type = (ConfigProp)field.getAnnotation(ConfigProp.class);
            types.put(!type.name().isEmpty()?type.name():field.getName(), field);
         }

         if(e.exists()) {
            HashMap properties1 = this.parseConfig(e, types);
            Iterator field2 = properties1.keySet().iterator();

            String type1;
            while(field2.hasNext()) {
               type1 = (String)field2.next();
               Field field1 = (Field)types.get(type1);
               Object obj = properties1.get(type1);
               if(!obj.equals(field1.get((Object)null))) {
                  field1.set((Object)null, obj);
               }
            }

            field2 = types.keySet().iterator();

            while(field2.hasNext()) {
               type1 = (String)field2.next();
               if(!properties1.containsKey(type1)) {
                  this.updateFile = true;
               }
            }
         } else {
            this.updateFile = true;
         }
      } catch (Exception var8) {
         this.updateFile = true;
         LogWriter.except(var8);
      }

      if(this.updateFile) {
         this.updateConfig();
      }

      this.updateFile = false;
   }

   private HashMap parseConfig(File file, HashMap types) throws Exception {
      HashMap config = new HashMap();
      BufferedReader reader = new BufferedReader(new FileReader(file));

      String strLine;
      while((strLine = reader.readLine()) != null) {
         if(!strLine.startsWith("#") && strLine.length() != 0) {
            int index = strLine.indexOf("=");
            if(index > 0 && index != strLine.length()) {
               String name = strLine.substring(0, index);
               String prop = strLine.substring(index + 1);
               if(!types.containsKey(name)) {
                  this.updateFile = true;
               } else {
                  Object obj = null;
                  Class class2 = ((Field)types.get(name)).getType();
                  if(class2.isAssignableFrom(String.class)) {
                     obj = prop;
                  } else if(class2.isAssignableFrom(Integer.TYPE)) {
                     obj = Integer.valueOf(Integer.parseInt(prop));
                  } else if(class2.isAssignableFrom(Short.TYPE)) {
                     obj = Short.valueOf(Short.parseShort(prop));
                  } else if(class2.isAssignableFrom(Byte.TYPE)) {
                     obj = Byte.valueOf(Byte.parseByte(prop));
                  } else if(class2.isAssignableFrom(Boolean.TYPE)) {
                     obj = Boolean.valueOf(Boolean.parseBoolean(prop));
                  } else if(class2.isAssignableFrom(Float.TYPE)) {
                     obj = Float.valueOf(Float.parseFloat(prop));
                  } else if(class2.isAssignableFrom(Double.TYPE)) {
                     obj = Double.valueOf(Double.parseDouble(prop));
                  }

                  if(obj != null) {
                     config.put(name, obj);
                  }
               }
            } else {
               this.updateFile = true;
            }
         }
      }

      reader.close();
      return config;
   }

   public void updateConfig() {
      File file = new File(this.dir, this.fileName);

      try {
         if(!file.exists()) {
            file.createNewFile();
         }

         BufferedWriter e = new BufferedWriter(new FileWriter(file));
         Iterator var3 = this.configFields.iterator();

         while(var3.hasNext()) {
            Field field = (Field)var3.next();
            ConfigProp prop = (ConfigProp)field.getAnnotation(ConfigProp.class);
            if(prop.info().length() != 0) {
               e.write("#" + prop.info() + System.getProperty("line.separator"));
            }

            String name = !prop.name().isEmpty()?prop.name():field.getName();

            try {
               e.write(name + "=" + field.get((Object)null).toString() + System.getProperty("line.separator"));
               e.write(System.getProperty("line.separator"));
            } catch (IllegalArgumentException var8) {
               var8.printStackTrace();
            } catch (IllegalAccessException var9) {
               var9.printStackTrace();
            }
         }

         e.close();
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }
}
