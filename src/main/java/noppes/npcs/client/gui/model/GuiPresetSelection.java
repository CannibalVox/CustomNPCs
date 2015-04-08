package noppes.npcs.client.gui.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.ModelData;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.controllers.Preset;
import noppes.npcs.client.controllers.PresetController;
import noppes.npcs.client.gui.model.GuiCreationScreen;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.opengl.GL11;

public class GuiPresetSelection extends GuiNPCInterface {

   private GuiNPCStringSlot slot;
   private GuiCreationScreen parent;
   private NBTTagCompound prevData;
   private ModelData playerdata;
   private EntityCustomNpc npc;


   public GuiPresetSelection(GuiCreationScreen parent, ModelData playerdata) {
      this.parent = parent;
      this.playerdata = playerdata;
      this.prevData = playerdata.writeToNBT();
      super.drawDefaultBackground = false;
      this.npc = new EntityCustomNpc(Minecraft.getMinecraft().theWorld);
      this.npc.modelData = playerdata.copy();
      PresetController.instance.load();
   }

   public void initGui() {
      super.initGui();
      Vector list = new Vector();
      Iterator var2 = PresetController.instance.presets.values().iterator();

      while(var2.hasNext()) {
         Preset preset = (Preset)var2.next();
         list.add(preset.name);
      }

      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.slot = new GuiNPCStringSlot(list, this, false, 18);
      this.slot.registerScrollButtons(4, 5);
      super.buttonList.add(new GuiNpcButton(2, super.width / 2 - 100, super.height - 44, 98, 20, "Back"));
      super.buttonList.add(new GuiNpcButton(3, super.width / 2 + 2, super.height - 44, 98, 20, "Load"));
      super.buttonList.add(new GuiNpcButton(4, super.width / 2 - 49, super.height - 22, 98, 20, "Remove"));
   }

   public void drawScreen(int i, int j, float f) {
      Object entity = this.npc.modelData.getEntity(this.npc);
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
      GL11.glScalef(-50.0F, 50.0F, 50.0F);
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
      RenderManager.instance.renderEntityWithPosYaw((Entity)entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
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
      Preset preset = PresetController.instance.getPreset(this.slot.selected);
      this.npc.modelData.readFromNBT(preset.data.writeToNBT());
   }

   public void doubleClicked() {
      this.playerdata.readFromNBT(this.npc.modelData.writeToNBT());
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

   public FontRenderer getFontRenderer() {
      return super.fontRendererObj;
   }

   protected void actionPerformed(GuiButton button) {
      GuiNpcButton guibutton = (GuiNpcButton)button;
      if(guibutton.field_146127_k == 2) {
         this.close();
      }

      if(guibutton.field_146127_k == 3) {
         this.playerdata.readFromNBT(this.npc.modelData.writeToNBT());
         this.close();
      }

      if(guibutton.field_146127_k == 4) {
         PresetController.instance.removePreset(this.slot.selected);
         Vector list = new Vector();
         Iterator var4 = PresetController.instance.presets.values().iterator();

         while(var4.hasNext()) {
            Preset preset = (Preset)var4.next();
            list.add(preset.name);
         }

         Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
         this.slot.setList(list);
         this.slot.selected = "";
      }

   }

   public void save() {}
}
