package noppes.npcs.client.gui.player.companion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionStats;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNpcCompanionTalents extends GuiNPCInterface {

   private RoleCompanion role;
   private Map talents = new HashMap();
   private GuiNpcButton selected;
   private long lastPressedTime = 0L;
   private long startPressedTime = 0L;


   public GuiNpcCompanionTalents(EntityNPCInterface npc) {
      super(npc);
      this.role = (RoleCompanion)npc.roleInterface;
      super.closeOnEsc = true;
      this.setBackground("companion_empty.png");
      super.xSize = 171;
      super.ySize = 166;
   }

   public void initGui() {
      super.initGui();
      this.talents.clear();
      int y = super.guiTop + 12;
      this.addLabel(new GuiNpcLabel(0, NoppesStringUtils.translate(new Object[]{"quest.exp", ": "}), super.guiLeft + 4, super.guiTop + 10));
      GuiNpcCompanionStats.addTopMenu(this.role, this, 2);
      int i = 0;
      Iterator var3 = this.role.talents.keySet().iterator();

      while(var3.hasNext()) {
         EnumCompanionTalent e = (EnumCompanionTalent)var3.next();
         this.addTalent(i++, e);
      }

   }

   private void addTalent(int i, EnumCompanionTalent talent) {
      int y = super.guiTop + 28 + i / 2 * 26;
      int x = super.guiLeft + 4 + i % 2 * 84;
      this.talents.put(Integer.valueOf(i), new GuiNpcCompanionTalents.GuiTalent(this.role, talent, x, y));
      if(this.role.getTalentLevel(talent) < 5) {
         this.addButton(new GuiNpcButton(i + 10, x + 26, y, 14, 14, "+"));
         y += 8;
      }

      this.addLabel(new GuiNpcLabel(i, this.role.talents.get(talent) + "/" + this.role.getNextLevel(talent), x + 26, y + 8));
   }

   public void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      int id = guibutton.id;
      if(id == 1) {
         CustomNpcs.proxy.openGui(super.npc, EnumGuiType.Companion);
      }

      if(id == 3) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.CompanionOpenInv, new Object[0]);
      }

      if(id >= 10) {
         this.selected = (GuiNpcButton)guibutton;
         this.lastPressedTime = this.startPressedTime = super.mc.theWorld.getWorldTime();
         this.addExperience(1);
      }

   }

   private void addExperience(int exp) {
      EnumCompanionTalent talent = ((GuiNpcCompanionTalents.GuiTalent)this.talents.get(Integer.valueOf(this.selected.field_146127_k - 10))).talent;
      if(this.role.canAddExp(-exp) || this.role.currentExp > 0) {
         if(exp > this.role.currentExp) {
            exp = this.role.currentExp;
         }

         NoppesUtilPlayer.sendData(EnumPlayerPacket.CompanionTalentExp, new Object[]{Integer.valueOf(talent.ordinal()), Integer.valueOf(exp)});
         this.role.talents.put(talent, Integer.valueOf(((Integer)this.role.talents.get(talent)).intValue() + exp));
         this.role.addExp(-exp);
         this.getLabel(this.selected.field_146127_k - 10).label = this.role.talents.get(talent) + "/" + this.role.getNextLevel(talent);
      }
   }

   public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);
      if(this.selected != null && super.mc.theWorld.getWorldTime() - this.startPressedTime > 4L && this.lastPressedTime < super.mc.theWorld.getWorldTime() && super.mc.theWorld.getWorldTime() % 4L == 0L) {
         if(this.selected.mousePressed(super.mc, i, j) && Mouse.isButtonDown(0)) {
            this.lastPressedTime = super.mc.theWorld.getWorldTime();
            if(this.lastPressedTime - this.startPressedTime < 20L) {
               this.addExperience(1);
            } else if(this.lastPressedTime - this.startPressedTime < 40L) {
               this.addExperience(2);
            } else if(this.lastPressedTime - this.startPressedTime < 60L) {
               this.addExperience(4);
            } else if(this.lastPressedTime - this.startPressedTime < 90L) {
               this.addExperience(8);
            } else if(this.lastPressedTime - this.startPressedTime < 140L) {
               this.addExperience(14);
            } else {
               this.addExperience(28);
            }
         } else {
            this.lastPressedTime = 0L;
            this.selected = null;
         }
      }

      super.mc.getTextureManager().bindTexture(Gui.icons);
      this.drawTexturedModalRect(super.guiLeft + 4, super.guiTop + 20, 10, 64, 162, 5);
      if(this.role.currentExp > 0) {
         float s = 1.0F * (float)this.role.currentExp / (float)this.role.getMaxExp();
         if(s > 1.0F) {
            s = 1.0F;
         }

         this.drawTexturedModalRect(super.guiLeft + 4, super.guiTop + 20, 10, 69, (int)(s * 162.0F), 5);
      }

      String s1 = this.role.currentExp + "\\" + this.role.getMaxExp();
      super.mc.fontRendererObj.drawString(s1, super.guiLeft + super.xSize / 2 - super.mc.fontRendererObj.getStringWidth(s1) / 2, super.guiTop + 10, CustomNpcResourceListener.DefaultTextColor);
      Iterator var5 = this.talents.values().iterator();

      while(var5.hasNext()) {
         GuiNpcCompanionTalents.GuiTalent talent = (GuiNpcCompanionTalents.GuiTalent)var5.next();
         talent.drawScreen(i, j, f);
      }

   }

   public void save() {}

   public static class GuiTalent extends GuiScreen {

      private EnumCompanionTalent talent;
      private int x;
      private int y;
      private RoleCompanion role;
      private static final ResourceLocation resource = new ResourceLocation("customnpcs:textures/gui/talent.png");


      public GuiTalent(RoleCompanion role, EnumCompanionTalent talent, int x, int y) {
         this.talent = talent;
         this.x = x;
         this.y = y;
         this.role = role;
      }

      public void drawScreen(int i, int j, float f) {
         Minecraft mc = Minecraft.getMinecraft();
         mc.getTextureManager().bindTexture(resource);
         ItemStack item = this.talent.item;
         if(item.getItem() == null) {
            item = new ItemStack(Blocks.dirt);
         }

         GL11.glPushMatrix();
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         GL11.glEnable(3042);
         boolean hover = this.x < i && this.x + 24 > i && this.y < j && this.y + 24 > j;
         this.drawTexturedModalRect(this.x, this.y, 0, hover?24:0, 24, 24);
         super.zLevel = 100.0F;
         GuiScreen.itemRender.zLevel = 100.0F;
         GL11.glEnable(2896);
         GL11.glEnable('\u803a');
         RenderHelper.enableGUIStandardItemLighting();
         GuiScreen.itemRender.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.getTextureManager(), item, this.x + 4, this.y + 4);
         GuiScreen.itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.getTextureManager(), item, this.x + 4, this.y + 4);
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable(2896);
         GL11.glTranslatef(0.0F, 0.0F, 200.0F);
         this.drawCenteredString(mc.fontRendererObj, this.role.getTalentLevel(this.talent) + "", this.x + 20, this.y + 16, 16777215);
         GuiScreen.itemRender.zLevel = 0.0F;
         super.zLevel = 0.0F;
         GL11.glPopMatrix();
      }

   }
}
