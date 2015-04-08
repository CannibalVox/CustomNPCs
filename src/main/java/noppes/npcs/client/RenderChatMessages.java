package noppes.npcs.client;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.IChatComponent;
import noppes.npcs.CustomNpcs;
import noppes.npcs.IChatMessages;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderChatMessages implements IChatMessages {

   private Map messages = new TreeMap();
   private int boxLength = 46;
   private float scale = 0.5F;
   private String lastMessage = "";
   private long lastMessageTime = 0L;


   public void addMessage(String message, EntityNPCInterface npc) {
      if(CustomNpcs.EnableChatBubbles) {
         long time = System.currentTimeMillis();
         if(!message.equals(this.lastMessage) || this.lastMessageTime + 1000L <= time) {
            TreeMap messages = new TreeMap(this.messages);
            messages.put(Long.valueOf(time), new TextBlockClient(message, this.boxLength * 4, new Object[]{Minecraft.getMinecraft().thePlayer, npc}));
            if(messages.size() > 3) {
               messages.remove(messages.keySet().iterator().next());
            }

            this.messages = messages;
            this.lastMessage = message;
            this.lastMessageTime = time;
         }
      }
   }

   public void renderMessages(double par3, double par5, double par7, float textscale) {
      Map messages = this.getMessages();
      if(!messages.isEmpty()) {
         FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
         float var13 = 1.6F;
         float var14 = 0.016666668F * var13;
         GL11.glPushMatrix();
         int size = 0;

         TextBlockClient index;
         for(Iterator textYSize = messages.values().iterator(); textYSize.hasNext(); size += index.lines.size()) {
            index = (TextBlockClient)textYSize.next();
         }

         int var20 = (int)((float)(size * font.FONT_HEIGHT) * this.scale);
         GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + (float)var20 * textscale * var14, (float)par7);
         GL11.glScalef(textscale, textscale, textscale);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-var14, -var14, var14);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable(3008);
         GL11.glDepthMask(true);
         GL11.glEnable(3042);
         GL11.glDepthFunc(515);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         GL11.glDisable(3553);
         this.drawRect(-this.boxLength - 2, -2, this.boxLength + 2, var20 + 1, -1140850689, 0.11D);
         this.drawRect(-this.boxLength - 1, -3, this.boxLength + 1, -2, -16777216, 0.1D);
         this.drawRect(-this.boxLength - 1, var20 + 2, -1, var20 + 1, -16777216, 0.1D);
         this.drawRect(3, var20 + 2, this.boxLength + 1, var20 + 1, -16777216, 0.1D);
         this.drawRect(-this.boxLength - 3, -1, -this.boxLength - 2, var20, -16777216, 0.1D);
         this.drawRect(this.boxLength + 3, -1, this.boxLength + 2, var20, -16777216, 0.1D);
         this.drawRect(-this.boxLength - 2, -2, -this.boxLength - 1, -1, -16777216, 0.1D);
         this.drawRect(this.boxLength + 2, -2, this.boxLength + 1, -1, -16777216, 0.1D);
         this.drawRect(-this.boxLength - 2, var20 + 1, -this.boxLength - 1, var20, -16777216, 0.1D);
         this.drawRect(this.boxLength + 2, var20 + 1, this.boxLength + 1, var20, -16777216, 0.1D);
         this.drawRect(0, var20 + 1, 3, var20 + 4, -1140850689, 0.11D);
         this.drawRect(-1, var20 + 4, 1, var20 + 5, -1140850689, 0.11D);
         this.drawRect(-1, var20 + 1, 0, var20 + 4, -16777216, 0.1D);
         this.drawRect(3, var20 + 1, 4, var20 + 3, -16777216, 0.1D);
         this.drawRect(2, var20 + 3, 3, var20 + 4, -16777216, 0.1D);
         this.drawRect(1, var20 + 4, 2, var20 + 5, -16777216, 0.1D);
         this.drawRect(-2, var20 + 4, -1, var20 + 5, -16777216, 0.1D);
         this.drawRect(-2, var20 + 5, 1, var20 + 6, -16777216, 0.1D);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glScalef(this.scale, this.scale, this.scale);
         int var21 = 0;
         Iterator var15 = messages.values().iterator();

         while(var15.hasNext()) {
            TextBlockClient block = (TextBlockClient)var15.next();

            for(Iterator var17 = block.lines.iterator(); var17.hasNext(); ++var21) {
               IChatComponent chat = (IChatComponent)var17.next();
               String message = chat.getFormattedText();
               font.drawString(message, -font.getStringWidth(message) / 2, var21 * font.FONT_HEIGHT, 0);
            }
         }

         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glEnable(3008);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
         RenderHelper.enableStandardItemLighting();
      }
   }

   private void drawRect(int par0, int par1, int par2, int par3, int par4, double par5) {
      int j1;
      if(par0 < par2) {
         j1 = par0;
         par0 = par2;
         par2 = j1;
      }

      if(par1 < par3) {
         j1 = par1;
         par1 = par3;
         par3 = j1;
      }

      float f = (float)(par4 >> 24 & 255) / 255.0F;
      float f1 = (float)(par4 >> 16 & 255) / 255.0F;
      float f2 = (float)(par4 >> 8 & 255) / 255.0F;
      float f3 = (float)(par4 & 255) / 255.0F;
      Tessellator tessellator = Tessellator.instance;
      GL11.glColor4f(f1, f2, f3, f);
      tessellator.startDrawingQuads();
      tessellator.addVertex((double)par0, (double)par3, par5);
      tessellator.addVertex((double)par2, (double)par3, par5);
      tessellator.addVertex((double)par2, (double)par1, par5);
      tessellator.addVertex((double)par0, (double)par1, par5);
      tessellator.draw();
   }

   private Map getMessages() {
      TreeMap messages = new TreeMap();
      long time = System.currentTimeMillis();
      Iterator var4 = this.messages.keySet().iterator();

      while(var4.hasNext()) {
         long timestamp = ((Long)var4.next()).longValue();
         if(time <= timestamp + 10000L) {
            TextBlockClient message = (TextBlockClient)this.messages.get(Long.valueOf(timestamp));
            messages.put(Long.valueOf(timestamp), message);
         }
      }

      this.messages = messages;
      return messages;
   }
}
