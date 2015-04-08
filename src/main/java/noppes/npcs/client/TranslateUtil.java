package noppes.npcs.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateUtil {

   private static final String TranslateUrl = "http://translate.google.com/translate_a/t?client=t&text=%s&hl=en&sl=%s&tl=%s&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1";
   private static final String AudioUrl = "http://translate.google.com/translate_tts?q=%s&tl=%s";


   public static String Translate(String text) {
      try {
         String e = String.format("http://translate.google.com/translate_a/t?client=t&text=%s&hl=en&sl=%s&tl=%s&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1", new Object[]{URLEncoder.encode(text, "utf8"), "auto", "nl"});
         URL url = new URL(e);
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setDoOutput(true);
         connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
         connection.setRequestProperty("X-HTTP-Method-Override", "GET");
         connection.connect();
         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         String line = reader.readLine();
         reader.close();
         connection.disconnect();
         if(line != null) {
            String parsed = parseJson(line);
            if(parsed != null) {
               return parsed;
            }
         }
      } catch (UnsupportedEncodingException var7) {
         var7.printStackTrace();
      } catch (MalformedURLException var8) {
         var8.printStackTrace();
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      return text;
   }

   private static String parseJson(String line) {
      JsonParser parser = new JsonParser();

      JsonElement element;
      JsonArray array;
      for(element = parser.parse(line); element.isJsonArray(); element = array.get(0)) {
         array = (JsonArray)element;
         if(array.size() == 0) {
            return null;
         }
      }

      System.out.println(element.getAsString());
      return element.getAsString();
   }
}
