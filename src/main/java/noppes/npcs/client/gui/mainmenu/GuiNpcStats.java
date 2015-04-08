package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataStats;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiNpcMeleeProperties;
import noppes.npcs.client.gui.SubGuiNpcProjectiles;
import noppes.npcs.client.gui.SubGuiNpcRangeProperties;
import noppes.npcs.client.gui.SubGuiNpcResistanceProperties;
import noppes.npcs.client.gui.SubGuiNpcRespawn;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcButtonYesNo;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcStats extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {

   private DataStats stats;


   public GuiNpcStats(EntityNPCInterface npc) {
      super(npc, 2);
      this.stats = npc.stats;
      Client.sendData(EnumPacketServer.MainmenuStatsGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 10;
      this.addLabel(new GuiNpcLabel(0, "stats.health", super.guiLeft + 5, y + 5));
      this.addTextField(new GuiNpcTextField(0, this, super.guiLeft + 85, y, 50, 18, this.stats.maxHealth + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(1, 32767, 20);
      this.addLabel(new GuiNpcLabel(1, "stats.aggro", super.guiLeft + 140, y + 5));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 220, y, 50, 18, this.stats.aggroRange + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(1, 64, 2);
      this.addLabel(new GuiNpcLabel(34, "stats.creaturetype", super.guiLeft + 275, y + 5));
      this.addButton(new GuiNpcButton(8, super.guiLeft + 355, y, 56, 20, new String[]{"stats.normal", "stats.undead", "stats.arthropod"}, this.stats.creatureType.ordinal()));
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 82;
      y += 22;
      var10001 = new GuiNpcButton(0, var10004, y, 56, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(2, "stats.respawn", super.guiLeft + 5, y + 5));
      var10004 = super.guiLeft + 82;
      y += 22;
      var10001 = new GuiNpcButton(2, var10004, y, 56, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(5, "stats.meleeproperties", super.guiLeft + 5, y + 5));
      var10004 = super.guiLeft + 82;
      y += 22;
      var10001 = new GuiNpcButton(3, var10004, y, 56, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(6, "stats.rangedproperties", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(9, super.guiLeft + 217, y, 56, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(7, "stats.projectileproperties", super.guiLeft + 140, y + 5));
      var10004 = super.guiLeft + 82;
      y += 34;
      var10001 = new GuiNpcButton(15, var10004, y, 56, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(15, "potion.resistance", super.guiLeft + 5, y + 5));
      var10004 = super.guiLeft + 82;
      y += 34;
      var10001 = new GuiNpcButton(4, var10004, y, 56, 20, new String[]{"gui.no", "gui.yes"}, super.npc.isImmuneToFire()?1:0);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(10, "stats.fireimmune", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 217, y, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.canDrown?1:0));
      this.addLabel(new GuiNpcLabel(11, "stats.candrown", super.guiLeft + 140, y + 5));
      this.addTextField((new GuiNpcTextField(14, this, super.guiLeft + 355, y, 56, 20, this.stats.healthRegen + "")).setNumbersOnly());
      this.addLabel(new GuiNpcLabel(14, "stats.regenhealth", super.guiLeft + 275, y + 5));
      GuiNpcTextField var2;
      int var10005 = super.guiLeft + 355;
      y += 22;
      var2 = new GuiNpcTextField(16, this, var10005, y, 56, 20, this.stats.combatRegen + "");
      this.addTextField(var2.setNumbersOnly());
      this.addLabel(new GuiNpcLabel(16, "stats.combatregen", super.guiLeft + 275, y + 5));
      this.addButton(new GuiNpcButton(6, super.guiLeft + 82, y, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.burnInSun?1:0));
      this.addLabel(new GuiNpcLabel(12, "stats.burninsun", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(7, super.guiLeft + 217, y, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.noFallDamage?1:0));
      this.addLabel(new GuiNpcLabel(13, "stats.nofalldamage", super.guiLeft + 140, y + 5));
      GuiNpcButtonYesNo var3;
      var10004 = super.guiLeft + 82;
      y += 22;
      var3 = new GuiNpcButtonYesNo(17, var10004, y, 56, 20, this.stats.potionImmune);
      this.addButton(var3);
      this.addLabel(new GuiNpcLabel(17, "stats.potionImmune", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButtonYesNo(18, super.guiLeft + 217, y, 56, 20, this.stats.attackInvisible));
      this.addLabel(new GuiNpcLabel(18, "stats.attackInvisible", super.guiLeft + 140, y + 5));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 0) {
         this.stats.maxHealth = textfield.getInteger();
         super.npc.heal((float)this.stats.maxHealth);
      } else if(textfield.id == 1) {
         this.stats.aggroRange = textfield.getInteger();
      } else if(textfield.id == 14) {
         this.stats.healthRegen = textfield.getInteger();
      } else if(textfield.id == 16) {
         this.stats.combatRegen = textfield.getInteger();
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.setSubGui(new SubGuiNpcRespawn(this.stats));
      } else if(button.field_146127_k == 2) {
         this.setSubGui(new SubGuiNpcMeleeProperties(this.stats));
      } else if(button.field_146127_k == 3) {
         this.setSubGui(new SubGuiNpcRangeProperties(this.stats));
      } else if(button.field_146127_k == 4) {
         super.npc.setImmuneToFire(button.getValue() == 1);
      } else if(button.field_146127_k == 5) {
         this.stats.canDrown = button.getValue() == 1;
      } else if(button.field_146127_k == 6) {
         this.stats.burnInSun = button.getValue() == 1;
      } else if(button.field_146127_k == 7) {
         this.stats.noFallDamage = button.getValue() == 1;
      } else if(button.field_146127_k == 8) {
         this.stats.creatureType = EnumCreatureAttribute.values()[button.getValue()];
      } else if(button.field_146127_k == 9) {
         this.setSubGui(new SubGuiNpcProjectiles(this.stats));
      } else if(button.field_146127_k == 15) {
         this.setSubGui(new SubGuiNpcResistanceProperties(this.stats.resistances));
      } else if(button.field_146127_k == 17) {
         this.stats.potionImmune = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      } else if(button.field_146127_k == 18) {
         this.stats.potionImmune = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

   }

   public void save() {
      Client.sendData(EnumPacketServer.MainmenuStatsSave, new Object[]{this.stats.writeToNBT(new NBTTagCompound())});
   }

   public void setGuiData(NBTTagCompound compound) {
      this.stats.readToNBT(compound);
      this.initGui();
   }
}
