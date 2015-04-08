package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCDialogSelection;
import noppes.npcs.client.gui.SubGuiColorSelector;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ISubGuiListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;

public class SubGuiNpcDialogOption extends SubGuiInterface implements IGuiData, ITextfieldListener, GuiSelectionListener, ISubGuiListener {

   private DialogOption option;


   public SubGuiNpcDialogOption(DialogOption option) {
      this.option = option;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(66, "dialog.editoption", super.guiLeft, super.guiTop + 4));
      this.getLabel(66).center(super.xSize);
      this.addLabel(new GuiNpcLabel(0, "gui.title", super.guiLeft + 4, super.guiTop + 20));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 40, super.guiTop + 15, 196, 20, this.option.title));

      String color;
      for(color = Integer.toHexString(this.option.optionColor); color.length() < 6; color = 0 + color) {
         ;
      }

      this.addLabel(new GuiNpcLabel(2, "gui.color", super.guiLeft + 4, super.guiTop + 45));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 62, super.guiTop + 40, 92, 20, color));
      this.getButton(2).setTextColor(this.option.optionColor);
      this.addLabel(new GuiNpcLabel(1, "dialog.optiontype", super.guiLeft + 4, super.guiTop + 67));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 62, super.guiTop + 62, 92, 20, new String[]{"gui.close", "dialog.dialog", "gui.disabled", "menu.role", "tile.commandBlock.name"}, this.option.optionType.ordinal()));
      if(this.option.optionType == EnumOptionType.DialogOption) {
         this.addButton(new GuiNpcButton(3, super.guiLeft + 4, super.guiTop + 84, "availability.selectdialog"));
         if(this.option.dialogId >= 0) {
            Client.sendData(EnumPacketServer.DialogGet, new Object[]{Integer.valueOf(this.option.dialogId)});
         }
      }

      if(this.option.optionType == EnumOptionType.CommandBlock) {
         this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 84, 248, 20, this.option.command));
         this.getTextField(4).setMaxStringLength(32767);
         this.addLabel(new GuiNpcLabel(4, "advMode.command", super.guiLeft + 4, super.guiTop + 110));
         this.addLabel(new GuiNpcLabel(5, "advMode.nearestPlayer", super.guiLeft + 4, super.guiTop + 125));
         this.addLabel(new GuiNpcLabel(6, "advMode.randomPlayer", super.guiLeft + 4, super.guiTop + 140));
         this.addLabel(new GuiNpcLabel(7, "advMode.allPlayers", super.guiLeft + 4, super.guiTop + 155));
         this.addLabel(new GuiNpcLabel(8, "dialog.commandoptionplayer", super.guiLeft + 4, super.guiTop + 170));
      }

      this.addButton(new GuiNpcButton(66, super.guiLeft + 82, super.guiTop + 190, 98, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 1) {
         this.option.optionType = EnumOptionType.values()[button.getValue()];
         this.initGui();
      }

      if(button.field_146127_k == 2) {
         this.setSubGui(new SubGuiColorSelector(this.option.optionColor));
      }

      if(button.field_146127_k == 3) {
         GuiNPCDialogSelection gui = new GuiNPCDialogSelection(super.npc, this.getParent(), this.option.dialogId);
         gui.listener = this;
         NoppesUtil.openGUI(super.player, gui);
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 0) {
         if(textfield.isEmpty()) {
            textfield.setText(this.option.title);
         } else {
            this.option.title = textfield.getText();
         }
      }

      if(textfield.id == 4) {
         this.option.command = textfield.getText();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if(compound.hasKey("DialogId")) {
         Dialog dialog = new Dialog();
         dialog.readNBT(compound);
         this.option.dialogId = dialog.id;
         if(this.getButton(3) != null) {
            this.getButton(3).setDisplayText(dialog.title);
         }
      }

   }

   public void selected(int ob, String name) {
      this.option.dialogId = ob;
   }

   public void subGuiClosed(SubGuiInterface subgui) {
      this.option.optionColor = ((SubGuiColorSelector)subgui).color;
      this.initGui();
   }
}
