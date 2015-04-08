package noppes.npcs.client.gui.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.opengl.GL11;

public class GuiModelColor extends GuiModelInterface implements ITextfieldListener {

   private GuiScreen parent;
   private static final ResourceLocation color = new ResourceLocation("customnpcs:textures/gui/color.png");
   private int colorX;
   private int colorY;
   private GuiNpcTextField textfield;
   private ModelPartData data;


   public GuiModelColor(GuiScreen parent, ModelPartData data, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      this.data = data;
      super.xOffset = 60;
      super.ySize = 230;
   }

   public void initGui() {
      super.initGui();
      this.colorX = super.guiLeft + 4;
      this.colorY = super.guiTop + 50;
      this.addTextField(this.textfield = new GuiNpcTextField(0, this, super.guiLeft + 25, super.guiTop + 20, 70, 20, this.data.getColor()));
      this.textfield.setTextColor(this.data.color);
   }

   public void keyTyped(char c, int i) {
      String prev = this.textfield.getText();
      super.keyTyped(c, i);
      String newText = this.textfield.getText();
      if(!newText.equals(prev)) {
         try {
            int e = Integer.parseInt(this.textfield.getText(), 16);
            this.data.color = e;
            this.textfield.setTextColor(e);
         } catch (NumberFormatException var6) {
            this.textfield.setText(prev);
         }

      }
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      super.mc.getTextureManager().bindTexture(color);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawTexturedModalRect(this.colorX, this.colorY, 0, 0, 120, 120);
   }

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      if(i >= this.colorX && i <= this.colorX + 120 && j >= this.colorY && j <= this.colorY + 120) {
         InputStream stream = null;

         try {
            IResource e = super.mc.getResourceManager().getResource(color);
            BufferedImage bufferedimage = ImageIO.read(stream = e.getInputStream());
            int color = bufferedimage.getRGB((i - super.guiLeft - 4) * 4, (j - super.guiTop - 50) * 4) & 16777215;
            if(color != 0) {
               this.data.color = color;
               this.textfield.setTextColor(color);
               this.textfield.setText(this.data.getColor());
            }
         } catch (IOException var16) {
            ;
         } finally {
            if(stream != null) {
               try {
                  stream.close();
               } catch (IOException var15) {
                  ;
               }
            }

         }

      }
   }

   public void unFocused(GuiNpcTextField textfield) {
      boolean color = false;

      int color1;
      try {
         color1 = Integer.parseInt(textfield.getText(), 16);
      } catch (NumberFormatException var4) {
         color1 = 0;
      }

      this.data.color = color1;
      textfield.setTextColor(color1);
   }

}
