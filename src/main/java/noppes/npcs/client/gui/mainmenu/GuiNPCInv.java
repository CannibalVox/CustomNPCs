package noppes.npcs.client.gui.mainmenu;

import java.util.HashMap;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.containers.ContainerNPCInv;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class GuiNPCInv extends GuiContainerNPCInterface2 implements ISliderListener, IGuiData {

   private HashMap chances = new HashMap();
   private ContainerNPCInv container;
   private ResourceLocation slot;


   public GuiNPCInv(EntityNPCInterface npc, ContainerNPCInv container) {
      super(npc, container, 3);
      this.setBackground("npcinv.png");
      this.container = container;
      super.ySize = 200;
      this.slot = this.getResource("slot.png");
      Client.sendData(EnumPacketServer.MainmenuInvGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(0, "inv.minExp", super.field_147003_i + 118, super.field_147009_r + 18));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.field_147003_i + 108, super.field_147009_r + 29, 60, 20, super.npc.inventory.minExp + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(0, 32767, 0);
      this.addLabel(new GuiNpcLabel(1, "inv.maxExp", super.field_147003_i + 118, super.field_147009_r + 52));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.field_147003_i + 108, super.field_147009_r + 63, 60, 20, super.npc.inventory.maxExp + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(0, 32767, 0);
      this.addLabel(new GuiNpcLabel(2, "inv.npcInventory", super.field_147003_i + 191, super.field_147009_r + 5));
      this.addLabel(new GuiNpcLabel(3, "inv.inventory", super.field_147003_i + 8, super.field_147009_r + 101));

      for(int i = 0; i < 9; ++i) {
         int chance = 100;
         if(super.npc.inventory.dropchance.containsKey(Integer.valueOf(i))) {
            chance = ((Integer)super.npc.inventory.dropchance.get(Integer.valueOf(i))).intValue();
         }

         if(chance <= 0 || chance > 100) {
            chance = 100;
         }

         this.chances.put(Integer.valueOf(i), Integer.valueOf(chance));
         GuiNpcSlider slider = new GuiNpcSlider(this, i, super.field_147003_i + 211, super.field_147009_r + 14 + i * 21, (float)chance / 100.0F);
         this.addSlider(slider);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      super.drawGuiContainerBackgroundLayer(f, i, j);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.slot);

      for(int id = 4; id <= 6; ++id) {
         Slot slot = this.container.getSlot(id);
         if(slot.getHasStack()) {
            this.drawTexturedModalRect(super.field_147003_i + slot.xDisplayPosition - 1, super.field_147009_r + slot.yDisplayPosition - 1, 0, 0, 18, 18);
         }
      }

   }

   public void drawScreen(int i, int j, float f) {
      int showname = super.npc.display.showName;
      super.npc.display.showName = 1;
      int l = super.field_147003_i + 20;
      int i1 = super.height / 2 - 145;
      GL11.glEnable('\u803a');
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), 50.0F);
      float f1 = 150.0F / (float)super.npc.display.modelSize;
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
      super.npc.display.showName = showname;
      super.drawScreen(i, j, f);
   }

   public void save() {
      super.npc.inventory.dropchance = this.chances;
      super.npc.inventory.minExp = this.getTextField(0).getInteger();
      super.npc.inventory.maxExp = this.getTextField(1).getInteger();
      Client.sendData(EnumPacketServer.MainmenuInvSave, new Object[]{super.npc.inventory.writeEntityToNBT(new NBTTagCompound())});
   }

   public void setGuiData(NBTTagCompound compound) {
      super.npc.inventory.readEntityFromNBT(compound);
      this.initGui();
   }

   public void mouseDragged(GuiNpcSlider guiNpcSlider) {
      guiNpcSlider.displayString = StatCollector.translateToLocal("inv.dropChance") + ": " + (int)(guiNpcSlider.sliderValue * 100.0F) + "%";
   }

   public void mousePressed(GuiNpcSlider guiNpcSlider) {}

   public void mouseReleased(GuiNpcSlider guiNpcSlider) {
      this.chances.put(Integer.valueOf(guiNpcSlider.field_146127_k), Integer.valueOf((int)(guiNpcSlider.sliderValue * 100.0F)));
   }
}
