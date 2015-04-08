package noppes.npcs.client.gui.player.companion;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiMenuTopIconButton;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;

public class GuiNpcCompanionStats extends GuiNPCInterface implements IGuiData {

   private RoleCompanion role;
   private boolean isEating = false;


   public GuiNpcCompanionStats(EntityNPCInterface npc) {
      super(npc);
      this.role = (RoleCompanion)npc.roleInterface;
      super.closeOnEsc = true;
      this.setBackground("companion.png");
      super.xSize = 171;
      super.ySize = 166;
      NoppesUtilPlayer.sendData(EnumPlayerPacket.RoleGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 10;
      this.addLabel(new GuiNpcLabel(0, NoppesStringUtils.translate(new Object[]{"gui.name", ": ", super.npc.display.name}), super.guiLeft + 4, y));
      GuiNpcLabel var10001;
      String var10004 = NoppesStringUtils.translate(new Object[]{"companion.owner", ": ", this.role.ownerName});
      int var10005 = super.guiLeft + 4;
      y += 12;
      var10001 = new GuiNpcLabel(1, var10004, var10005, y);
      this.addLabel(var10001);
      var10004 = NoppesStringUtils.translate(new Object[]{"companion.age", ": ", this.role.ticksActive / 18000L + " (", this.role.stage.name, ")"});
      var10005 = super.guiLeft + 4;
      y += 12;
      var10001 = new GuiNpcLabel(2, var10004, var10005, y);
      this.addLabel(var10001);
      var10004 = NoppesStringUtils.translate(new Object[]{"companion.strength", ": ", Integer.valueOf(super.npc.stats.getAttackStrength())});
      var10005 = super.guiLeft + 4;
      y += 12;
      var10001 = new GuiNpcLabel(3, var10004, var10005, y);
      this.addLabel(var10001);
      var10004 = NoppesStringUtils.translate(new Object[]{"companion.level", ": ", Integer.valueOf(this.role.getTotalLevel())});
      var10005 = super.guiLeft + 4;
      y += 12;
      var10001 = new GuiNpcLabel(4, var10004, var10005, y);
      this.addLabel(var10001);
      var10004 = NoppesStringUtils.translate(new Object[]{"job.name", ": ", "gui.none"});
      var10005 = super.guiLeft + 4;
      y += 12;
      var10001 = new GuiNpcLabel(5, var10004, var10005, y);
      this.addLabel(var10001);
      addTopMenu(this.role, this, 1);
   }

   public static void addTopMenu(RoleCompanion role, GuiScreen screen, int active) {
      GuiMenuTopIconButton button;
      if(screen instanceof GuiNPCInterface) {
         GuiNPCInterface gui = (GuiNPCInterface)screen;
         gui.addTopButton(button = new GuiMenuTopIconButton(1, gui.guiLeft + 4, gui.guiTop - 27, "menu.stats", new ItemStack(CustomItems.letter)));
         gui.addTopButton(button = new GuiMenuTopIconButton(2, button, "companion.talent", new ItemStack(CustomItems.spellHoly)));
         if(role.hasInv()) {
            gui.addTopButton(button = new GuiMenuTopIconButton(3, button, "inv.inventory", new ItemStack(CustomItems.bag)));
         }

         if(role.job != EnumCompanionJobs.NONE) {
            gui.addTopButton(new GuiMenuTopIconButton(4, button, "job.name", new ItemStack(CustomItems.bag)));
         }

         gui.getTopButton(active).active = true;
      }

      if(screen instanceof GuiContainerNPCInterface) {
         GuiContainerNPCInterface gui1 = (GuiContainerNPCInterface)screen;
         gui1.addTopButton(button = new GuiMenuTopIconButton(1, gui1.field_147003_i + 4, gui1.field_147009_r - 27, "menu.stats", new ItemStack(CustomItems.letter)));
         gui1.addTopButton(button = new GuiMenuTopIconButton(2, button, "companion.talent", new ItemStack(CustomItems.spellHoly)));
         if(role.hasInv()) {
            gui1.addTopButton(button = new GuiMenuTopIconButton(3, button, "inv.inventory", new ItemStack(CustomItems.bag)));
         }

         if(role.job != EnumCompanionJobs.NONE) {
            gui1.addTopButton(new GuiMenuTopIconButton(4, button, "job.name", new ItemStack(CustomItems.bag)));
         }

         gui1.getTopButton(active).active = true;
      }

   }

   public void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      int id = guibutton.id;
      if(id == 2) {
         CustomNpcs.proxy.openGui(super.npc, EnumGuiType.CompanionTalent);
      }

      if(id == 3) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.CompanionOpenInv, new Object[0]);
      }

   }

   public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);
      if(this.isEating && !this.role.isEating()) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.RoleGet, new Object[0]);
      }

      this.isEating = this.role.isEating();
      super.drawNpc(34, 150);
      int y = this.drawHealth(super.guiTop + 88);
   }

   private int drawHealth(int y) {
      super.mc.getTextureManager().bindTexture(Gui.icons);
      int max = this.role.getTotalArmorValue();
      int k;
      if(this.role.talents.containsKey(EnumCompanionTalent.ARMOR) || max > 0) {
         for(k = 0; k < 10; ++k) {
            int scale = super.guiLeft + 66 + k * 10;
            if(k * 2 + 1 < max) {
               this.drawTexturedModalRect(scale, y, 34, 9, 9, 9);
            }

            if(k * 2 + 1 == max) {
               this.drawTexturedModalRect(scale, y, 25, 9, 9, 9);
            }

            if(k * 2 + 1 > max) {
               this.drawTexturedModalRect(scale, y, 16, 9, 9, 9);
            }
         }

         y += 10;
      }

      max = MathHelper.ceiling_float_int(super.npc.getMaxHealth());
      k = (int)super.npc.getHealth();
      float var8 = 1.0F;
      if(max > 40) {
         var8 = (float)max / 40.0F;
         k = (int)((float)k / var8);
         max = 40;
      }

      int i;
      int x;
      for(i = 0; i < max; ++i) {
         x = super.guiLeft + 66 + i % 20 * 5;
         int offset = i / 20 * 10;
         this.drawTexturedModalRect(x, y + offset, 52 + i % 2 * 5, 9, i % 2 == 1?4:5, 9);
         if(k > i) {
            this.drawTexturedModalRect(x, y + offset, 52 + i % 2 * 5, 0, i % 2 == 1?4:5, 9);
         }
      }

      k = this.role.foodstats.getFoodLevel();
      y += 10;
      if(max > 20) {
         y += 10;
      }

      for(i = 0; i < 20; ++i) {
         x = super.guiLeft + 66 + i % 20 * 5;
         this.drawTexturedModalRect(x, y, 16 + i % 2 * 5, 27, i % 2 == 1?4:5, 9);
         if(k > i) {
            this.drawTexturedModalRect(x, y, 52 + i % 2 * 5, 27, i % 2 == 1?4:5, 9);
         }
      }

      return y;
   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      this.role.readFromNBT(compound);
   }
}
