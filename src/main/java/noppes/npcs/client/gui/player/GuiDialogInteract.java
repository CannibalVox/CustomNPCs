package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NPCGuiHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiDialogInteract extends GuiNPCInterface {

   private Dialog dialog;
   private int selected = 0;
   private List lines = new ArrayList();
   private List options = new ArrayList();
   private int rowStart = 0;
   private int rowTotal = 0;
   private int dialogHeight = 180;
   private ResourceLocation wheel;
   private ResourceLocation[] wheelparts;
   private ResourceLocation indicator;
   private boolean isGrabbed = false;
   private int selectedX = 0;
   private int selectedY = 0;


   public GuiDialogInteract(EntityNPCInterface npc, Dialog dialog) {
      super(npc);
      this.dialog = dialog;
      this.appendDialog(dialog);
      super.ySize = 238;
      this.wheel = this.getResource("wheel.png");
      this.indicator = this.getResource("indicator.png");
      this.wheelparts = new ResourceLocation[]{this.getResource("wheel1.png"), this.getResource("wheel2.png"), this.getResource("wheel3.png"), this.getResource("wheel4.png"), this.getResource("wheel5.png"), this.getResource("wheel6.png")};
   }

   public void initGui() {
      super.initGui();
      this.isGrabbed = false;
      this.grabMouse(this.dialog.showWheel);
      super.guiTop = super.height - super.ySize;
   }

   public void grabMouse(boolean grab) {
      if(grab && !this.isGrabbed) {
         Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
         this.isGrabbed = true;
      } else if(!grab && this.isGrabbed) {
         Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
         this.isGrabbed = false;
      }

   }

   public void drawScreen(int i, int j, float f) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if(!this.dialog.hideNPC) {
         float count = (float)(super.guiLeft - 60);
         float i1 = (float)(super.guiTop + 50 + this.dialogHeight);
         GL11.glEnable(2903);
         GL11.glPushMatrix();
         GL11.glTranslatef(count, i1, 50.0F);
         float block = super.npc.height;
         if(super.npc.width * 2.0F > block) {
            block = super.npc.width * 2.0F;
         }

         block = 2.0F / block * 40.0F;
         GL11.glScalef(-block, block, block);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         float size = super.npc.renderYawOffset;
         float f3 = super.npc.rotationYaw;
         float line = super.npc.rotationPitch;
         float f7 = super.npc.rotationYawHead;
         float f5 = count - (float)i;
         float f6 = i1 - 50.0F - (float)j;
         GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
         RenderHelper.enableStandardItemLighting();
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-((float)Math.atan((double)(f6 / 80.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
         super.npc.renderYawOffset = 0.0F;
         super.npc.rotationYaw = (float)Math.atan((double)(f5 / 80.0F)) * 40.0F;
         super.npc.rotationPitch = -((float)Math.atan((double)(f6 / 80.0F))) * 20.0F;
         super.npc.prevRotationYawHead = super.npc.rotationYawHead = super.npc.rotationYaw;
         GL11.glTranslatef(0.0F, super.npc.yOffset, 0.0F);
         RenderManager.instance.playerViewY = 180.0F;

         try {
            RenderManager.instance.renderEntityWithPosYaw(super.npc, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
         } catch (Exception var14) {
            ;
         }

         super.npc.renderYawOffset = size;
         super.npc.rotationYaw = f3;
         super.npc.rotationPitch = line;
         super.npc.prevRotationYawHead = super.npc.rotationYawHead = f7;
         GL11.glPopMatrix();
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable('\u803a');
         OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glDisable(3553);
         OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      }

      super.drawScreen(i, j, f);
      GL11.glEnable(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glEnable(3008);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.5F, 100.065F);
      int var15 = 0;

      for(Iterator var16 = this.lines.iterator(); var16.hasNext(); ++var15) {
         TextBlockClient var17 = (TextBlockClient)var16.next();
         int var18 = super.fontRendererObj.getStringWidth(var17.name + ": ");
         this.drawString(var17.name + ": ", -4 - var18, var17.color, var15);

         for(Iterator var19 = var17.lines.iterator(); var19.hasNext(); ++var15) {
            IChatComponent var20 = (IChatComponent)var19.next();
            this.drawString(var20.getFormattedText(), 0, var17.color, var15);
         }
      }

      if(!this.options.isEmpty()) {
         if(!this.dialog.showWheel) {
            this.drawLinedOptions(j);
         } else {
            this.drawWheel();
         }
      }

      GL11.glPopMatrix();
   }

   private void drawWheel() {
      int yoffset = super.guiTop + this.dialogHeight + 14;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.wheel);
      this.drawTexturedModalRect(super.width / 2 - 31, yoffset, 0, 0, 63, 40);
      this.selectedX += Mouse.getDX();
      this.selectedY += Mouse.getDY();
      byte limit = 80;
      if(this.selectedX > limit) {
         this.selectedX = limit;
      }

      if(this.selectedX < -limit) {
         this.selectedX = -limit;
      }

      if(this.selectedY > limit) {
         this.selectedY = limit;
      }

      if(this.selectedY < -limit) {
         this.selectedY = -limit;
      }

      this.selected = 1;
      if(this.selectedY < -20) {
         ++this.selected;
      }

      if(this.selectedY > 54) {
         --this.selected;
      }

      if(this.selectedX < 0) {
         this.selected += 3;
      }

      super.mc.renderEngine.bindTexture(this.wheelparts[this.selected]);
      this.drawTexturedModalRect(super.width / 2 - 31, yoffset, 0, 0, 85, 55);
      Iterator var3 = this.dialog.options.keySet().iterator();

      while(var3.hasNext()) {
         int slot = ((Integer)var3.next()).intValue();
         DialogOption option = (DialogOption)this.dialog.options.get(Integer.valueOf(slot));
         if(option != null && option.optionType != EnumOptionType.Disabled) {
            int color = option.optionColor;
            if(slot == this.selected) {
               color = 8622040;
            }

            if(slot == 0) {
               this.drawString(super.fontRendererObj, option.title, super.width / 2 + 13, yoffset - 6, color);
            }

            if(slot == 1) {
               this.drawString(super.fontRendererObj, option.title, super.width / 2 + 33, yoffset + 12, color);
            }

            if(slot == 2) {
               this.drawString(super.fontRendererObj, option.title, super.width / 2 + 27, yoffset + 32, color);
            }

            if(slot == 3) {
               this.drawString(super.fontRendererObj, option.title, super.width / 2 - 13 - super.fontRendererObj.getStringWidth(option.title), yoffset - 6, color);
            }

            if(slot == 4) {
               this.drawString(super.fontRendererObj, option.title, super.width / 2 - 33 - super.fontRendererObj.getStringWidth(option.title), yoffset + 12, color);
            }

            if(slot == 5) {
               this.drawString(super.fontRendererObj, option.title, super.width / 2 - 27 - super.fontRendererObj.getStringWidth(option.title), yoffset + 32, color);
            }
         }
      }

      super.mc.renderEngine.bindTexture(this.indicator);
      this.drawTexturedModalRect(super.width / 2 + this.selectedX / 4 - 2, yoffset + 16 - this.selectedY / 6, 0, 0, 8, 8);
   }

   private void drawLinedOptions(int j) {
      this.drawHorizontalLine(super.guiLeft - 60, super.guiLeft + super.xSize + 120, super.guiTop + this.dialogHeight, -1);
      int offset = this.dialogHeight + 4;
      int count;
      if(j >= super.guiTop + offset) {
         count = (j - (super.guiTop + offset)) / super.fontRendererObj.FONT_HEIGHT;
         if(count < this.options.size()) {
            this.selected = count;
         }
      }

      if(this.selected >= this.options.size()) {
         this.selected = 0;
      }

      if(this.selected < 0) {
         this.selected = 0;
      }

      count = 0;

      for(int k = 0; k < this.options.size(); ++k) {
         int id = ((Integer)this.options.get(k)).intValue();
         DialogOption option = (DialogOption)this.dialog.options.get(Integer.valueOf(id));
         int y = super.guiTop + offset + count * super.fontRendererObj.FONT_HEIGHT;
         if(this.selected == k) {
            this.drawString(super.fontRendererObj, ">", super.guiLeft - 60, y, 14737632);
         }

         this.drawString(super.fontRendererObj, NoppesStringUtils.formatText(option.title, new Object[]{super.player, super.npc}), super.guiLeft - 30, y, option.optionColor);
         ++count;
      }

   }

   private void drawString(String text, int left, int color, int count) {
      int height = count - this.rowStart;
      super.drawString(super.fontRendererObj, text, super.guiLeft + left, super.guiTop + height * super.fontRendererObj.FONT_HEIGHT, color);
   }

   private int getSelected() {
      return this.selected <= 0?0:(this.selected < this.options.size()?this.selected:this.options.size() - 1);
   }

   public void keyTyped(char c, int i) {
      if(i == super.mc.gameSettings.keyBindForward.getKeyCode() || i == 200) {
         --this.selected;
      }

      if(i == super.mc.gameSettings.keyBindBack.getKeyCode() || i == 208) {
         ++this.selected;
      }

      if(i == 28) {
         this.handleDialogSelection();
      }

      if(super.closeOnEsc && (i == 1 || this.isInventoryKey(i))) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.Dialog, new Object[]{Integer.valueOf(this.dialog.id), Integer.valueOf(-1)});
         this.closed();
         this.close();
      }

      super.keyTyped(c, i);
   }

   public void mouseClicked(int i, int j, int k) {
      if(this.selected == -1 && this.options.isEmpty() || this.selected >= 0) {
         this.handleDialogSelection();
      }

   }

   private void handleDialogSelection() {
      int optionId = -1;
      if(this.dialog.showWheel) {
         optionId = this.selected;
      } else if(!this.options.isEmpty()) {
         optionId = ((Integer)this.options.get(this.selected)).intValue();
      }

      NoppesUtilPlayer.sendData(EnumPlayerPacket.Dialog, new Object[]{Integer.valueOf(this.dialog.id), Integer.valueOf(optionId)});
      if(this.dialog != null && this.dialog.hasOtherOptions() && !this.options.isEmpty()) {
         DialogOption option = (DialogOption)this.dialog.options.get(Integer.valueOf(optionId));
         if(option != null && option.optionType != EnumOptionType.QuitOption && option.optionType != EnumOptionType.Disabled) {
            this.lines.add(new TextBlockClient(super.player.getDisplayName(), option.title, 280, option.optionColor, new Object[]{super.player, super.npc}));
            this.calculateRowHeight();
            NPCGuiHelper.clickSound();
         } else {
            this.close();
            this.closed();
         }
      } else {
         this.close();
         this.closed();
      }
   }

   private void closed() {
      NoppesUtilPlayer.sendData(EnumPlayerPacket.CheckQuestCompletion, new Object[0]);
   }

   public void save() {}

   public void appendDialog(Dialog dialog) {
      super.closeOnEsc = !dialog.disableEsc;
      this.dialog = dialog;
      this.options = new ArrayList();
      if(dialog.sound != null && !dialog.sound.isEmpty()) {
         MusicController.Instance.stopMusic();
         MusicController.Instance.playSound(dialog.sound, (float)super.npc.posX, (float)super.npc.posY, (float)super.npc.posZ);
      }

      this.lines.add(new TextBlockClient(super.npc.getCommandSenderName(), dialog.text, 280, 14737632, new Object[]{super.player, super.npc}));
      Iterator var2 = dialog.options.keySet().iterator();

      while(var2.hasNext()) {
         int slot = ((Integer)var2.next()).intValue();
         DialogOption option = (DialogOption)dialog.options.get(Integer.valueOf(slot));
         if(option != null && option.optionType != EnumOptionType.Disabled) {
            this.options.add(Integer.valueOf(slot));
         }
      }

      this.calculateRowHeight();
      this.grabMouse(dialog.showWheel);
   }

   private void calculateRowHeight() {
      this.rowTotal = 0;

      TextBlockClient block;
      for(Iterator max = this.lines.iterator(); max.hasNext(); this.rowTotal += block.lines.size() + 1) {
         block = (TextBlockClient)max.next();
      }

      int max1 = this.dialogHeight / Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
      this.rowStart = this.rowTotal - max1;
      if(this.rowStart < 0) {
         this.rowStart = 0;
      }

   }
}
