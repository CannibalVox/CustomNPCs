package noppes.npcs.client.gui.model;

import java.util.Collections;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.NPCRendererHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.ModelData;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.gui.model.GuiCreationScreen;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.opengl.GL11;

public class GuiEntitySelection extends GuiNPCInterface {

   private GuiNPCStringSlot slot;
   private GuiCreationScreen parent;
   private Class prevModel;
   private ModelData playerdata;
   private EntityCustomNpc npc;


   public GuiEntitySelection(GuiCreationScreen parent, ModelData playerdata, EntityCustomNpc npc) {
      this.parent = parent;
      this.playerdata = playerdata;
      this.npc = npc;
      super.drawDefaultBackground = false;
      this.prevModel = playerdata.getEntityClass();
   }

   public void initGui() {
      super.initGui();
      Vector list = new Vector(this.parent.data.keySet());
      list.add("CustomNPC");
      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.slot = new GuiNPCStringSlot(list, this, false, 18);
      if(this.playerdata.getEntityClass() != null) {
         this.slot.selected = (String)EntityList.classToStringMapping.get(this.playerdata.getEntityClass());
      } else {
         this.slot.selected = "CustomNPC";
      }

      this.slot.registerScrollButtons(4, 5);
      super.buttonList.add(new GuiNpcButton(2, super.width / 2 - 100, super.height - 44, 98, 20, "gui.back"));
   }

   public void drawScreen(int i, int j, float f) {
      Object entity = this.playerdata.getEntity(this.npc);
      if(entity == null) {
         entity = this.npc;
      } else {
         EntityUtil.Copy(this.npc, (EntityLivingBase)entity);
      }

      int l = super.width / 2 - 180;
      int i1 = super.height / 2 - 90;
      GL11.glEnable('\u803a');
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), 50.0F);
      float scale = 1.0F;
      if((double)((Entity)entity).height > 2.4D) {
         scale = 2.0F / ((Entity)entity).height;
      }

      GL11.glScalef(-50.0F * scale, 50.0F * scale, 50.0F * scale);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = ((EntityLivingBase)entity).renderYawOffset;
      float f3 = ((Entity)entity).rotationYaw;
      float f4 = ((Entity)entity).rotationPitch;
      float f7 = ((EntityLivingBase)entity).rotationYawHead;
      float f5 = (float)(l + 33) - (float)i;
      float f6 = (float)(i1 + 131 - 50) - (float)j;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f6 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      ((EntityLivingBase)entity).renderYawOffset = (float)Math.atan((double)(f5 / 40.0F)) * 20.0F;
      ((Entity)entity).rotationYaw = (float)Math.atan((double)(f5 / 40.0F)) * 40.0F;
      ((Entity)entity).rotationPitch = -((float)Math.atan((double)(f6 / 40.0F))) * 20.0F;
      ((EntityLivingBase)entity).rotationYawHead = ((Entity)entity).rotationYaw;
      GL11.glTranslatef(0.0F, ((Entity)entity).yOffset, 0.0F);
      RenderManager.instance.playerViewY = 180.0F;

      try {
         RenderManager.instance.renderEntityWithPosYaw((Entity)entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      } catch (Exception var15) {
         this.playerdata.setEntityClass((Class)null);
      }

      ((EntityLivingBase)entity).renderYawOffset = f2;
      ((Entity)entity).rotationYaw = f3;
      ((Entity)entity).rotationPitch = f4;
      ((EntityLivingBase)entity).rotationYawHead = f7;
      GL11.glPopMatrix();
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable('\u803a');
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      this.slot.drawScreen(i, j, f);
      super.drawScreen(i, j, f);
   }

   public void elementClicked() {
      try {
         this.playerdata.setEntityClass((Class)this.parent.data.get(this.slot.selected));
         EntityLivingBase ex = this.playerdata.getEntity(this.npc);
         if(ex != null) {
            RendererLivingEntity render = (RendererLivingEntity)RenderManager.instance.getEntityRenderObject(ex);
            this.npc.display.texture = NPCRendererHelper.getTexture(render, ex);
         } else {
            this.npc.display.texture = "customnpcs:textures/entity/humanmale/Steve.png";
         }

         this.npc.display.glowTexture = "";
         this.npc.textureLocation = null;
         this.npc.textureGlowLocation = null;
         this.npc.updateHitbox();
      } catch (Exception var3) {
         this.npc.display.texture = "customnpcs:textures/entity/humanmale/Steve.png";
      }

   }

   public void doubleClicked() {
      this.close();
   }

   public void keyTyped(char par1, int par2) {
      if(par2 == 1) {
         this.close();
      }

   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }

   protected void actionPerformed(GuiButton guibutton) {
      this.close();
   }

   public void save() {}
}
