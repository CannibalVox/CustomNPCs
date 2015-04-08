package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.Faction;

public class SubGuiNpcFactionPoints extends SubGuiInterface implements ITextfieldListener {

   private Faction faction;


   public SubGuiNpcFactionPoints(Faction faction) {
      this.faction = faction;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(2, "faction.default", super.guiLeft + 4, super.guiTop + 33));
      this.addTextField(new GuiNpcTextField(2, this, super.guiLeft + 8 + super.fontRendererObj.getStringWidth(this.getLabel(2).label), super.guiTop + 28, 70, 20, this.faction.defaultPoints + ""));
      this.getTextField(2).setMaxStringLength(6);
      this.getTextField(2).numbersOnly = true;
      String title = StatCollector.translateToLocal("faction.unfriendly") + "<->" + StatCollector.translateToLocal("faction.neutral");
      this.addLabel(new GuiNpcLabel(3, title, super.guiLeft + 4, super.guiTop + 80));
      this.addTextField(new GuiNpcTextField(3, this, super.guiLeft + 8 + super.fontRendererObj.getStringWidth(title), super.guiTop + 75, 70, 20, this.faction.neutralPoints + ""));
      title = StatCollector.translateToLocal("faction.neutral") + "<->" + StatCollector.translateToLocal("faction.friendly");
      this.addLabel(new GuiNpcLabel(4, title, super.guiLeft + 4, super.guiTop + 105));
      this.addTextField(new GuiNpcTextField(4, this, super.guiLeft + 8 + super.fontRendererObj.getStringWidth(title), super.guiTop + 100, 70, 20, this.faction.friendlyPoints + ""));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(4).numbersOnly = true;
      if(this.getTextField(3).xPosition > this.getTextField(4).xPosition) {
         this.getTextField(4).xPosition = this.getTextField(3).xPosition;
      } else {
         this.getTextField(3).xPosition = this.getTextField(4).xPosition;
      }

      this.addButton(new GuiNpcButton(66, super.guiLeft + 20, super.guiTop + 192, 90, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 2) {
         this.faction.defaultPoints = textfield.getInteger();
      } else if(textfield.id == 3) {
         this.faction.neutralPoints = textfield.getInteger();
      } else if(textfield.id == 4) {
         this.faction.friendlyPoints = textfield.getInteger();
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 66) {
         this.close();
      }

   }
}
