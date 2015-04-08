package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import noppes.npcs.client.gui.GuiNpcSelectionInterface;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class GuiNPCTextures extends GuiNpcSelectionInterface {

   public GuiNPCTextures(EntityNPCInterface npc, GuiScreen parent) {
      super(npc, parent, npc.display.texture);
      super.title = "Select Texture";
      super.parent = parent;
   }

   public void initGui() {
      super.initGui();
      int index = super.npc.display.texture.lastIndexOf("/");
      if(index > 0) {
         String asset = super.npc.display.texture.substring(index + 1);
         if(super.npc.display.texture.equals(super.assets.getAsset(asset))) {
            super.slot.selected = asset;
         }
      }

   }

   public void drawScreen(int i, int j, float f) {
      int l = super.width / 2 - 180;
      int i1 = super.height / 2 - 90;
      GL11.glEnable('\u803a');
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), 50.0F);
      float f1 = 250.0F / (float)super.npc.display.modelSize;
      GL11.glScalef(-f1, f1, f1);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = super.npc.renderYawOffset;
      float f3 = super.npc.rotationYaw;
      float f4 = super.npc.rotationPitch;
      float f7 = super.npc.rotationYawHead;
      float f5 = (float)(l + 33) - (float)i;
      float f6 = (float)(i1 + 131 - 50) - (float)j;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f6 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      super.npc.renderYawOffset = (float)Math.atan((double)(f5 / 40.0F)) * 20.0F;
      super.npc.rotationYaw = (float)Math.atan((double)(f5 / 40.0F)) * 40.0F;
      super.npc.rotationPitch = -((float)Math.atan((double)(f6 / 40.0F))) * 20.0F;
      super.npc.rotationYawHead = super.npc.rotationYaw;
      super.npc.cloakUpdate();
      GL11.glTranslatef(0.0F, super.npc.yOffset, 0.0F);
      RenderManager.instance.playerViewY = 180.0F;
      RenderManager.instance.renderEntityWithPosYaw(super.npc, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      super.npc.renderYawOffset = f2;
      super.npc.rotationYaw = f3;
      super.npc.rotationPitch = f4;
      super.npc.rotationYawHead = f7;
      GL11.glPopMatrix();
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable('\u803a');
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      super.drawScreen(i, j, f);
   }

   public void elementClicked() {
      if(super.dataTextures.contains(super.slot.selected) && super.slot.selected != null) {
         super.npc.display.texture = super.assets.getAsset(super.slot.selected);
         super.npc.textureLocation = null;
      }

   }

   public void save() {}

   public String[] getExtension() {
      return new String[]{"png"};
   }
}
