package noppes.npcs.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.controllers.ClientCloneController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketServer;

public class GuiNpcMobSpawnerAdd extends GuiNPCInterface implements GuiYesNoCallback, IGuiData {

   private Entity toClone;
   private NBTTagCompound compound;
   private static boolean serverSide = false;
   private static int tab = 1;


   public GuiNpcMobSpawnerAdd(NBTTagCompound compound) {
      this.toClone = EntityList.createEntityFromNBT(compound, Minecraft.getMinecraft().theWorld);
      this.compound = compound;
      this.setBackground("menubg.png");
      super.xSize = 256;
      super.ySize = 216;
   }

   public void initGui() {
      super.initGui();
      String name = this.toClone.getCommandSenderName();
      this.addLabel(new GuiNpcLabel(0, "Save as", super.guiLeft + 4, super.guiTop + 6));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 18, 200, 20, name));
      this.addLabel(new GuiNpcLabel(1, "Tab", super.guiLeft + 10, super.guiTop + 50));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 40, super.guiTop + 45, 20, 20, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, tab - 1));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 4, super.guiTop + 95, new String[]{"Client side", "Server side"}, serverSide?1:0));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 4, super.guiTop + 70, 80, 20, "gui.save"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 86, super.guiTop + 70, 80, 20, "gui.cancel"));
   }

   public void buttonEvent(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         String name = this.getTextField(0).getText();
         if(name.isEmpty()) {
            return;
         }

         int tab = ((GuiNpcButton)guibutton).getValue() + 1;
         if(!serverSide) {
            if(ClientCloneController.Instance.getCloneData((ICommandSender)null, name, tab) != null) {
               this.displayGuiScreen(new GuiYesNo(this, "Warning", "You are about to overwrite a clone", 1));
            } else {
               this.confirmClicked(true, 0);
            }
         } else {
            Client.sendData(EnumPacketServer.ClonePreSave, new Object[]{name, Integer.valueOf(tab)});
         }
      }

      if(id == 1) {
         this.close();
      }

      if(id == 2) {
         tab = ((GuiNpcButton)guibutton).getValue() + 1;
      }

      if(id == 3) {
         serverSide = ((GuiNpcButton)guibutton).getValue() == 1;
      }

   }

   public void confirmClicked(boolean confirm, int id) {
      if(confirm) {
         String name = this.getTextField(0).getText();
         if(!serverSide) {
            ClientCloneController.Instance.addClone(this.compound, name, tab);
         } else {
            Client.sendData(EnumPacketServer.CloneSave, new Object[]{name, Integer.valueOf(tab)});
         }

         this.close();
      } else {
         this.displayGuiScreen(this);
      }

   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      if(compound.hasKey("NameExists")) {
         if(compound.getBoolean("NameExists")) {
            this.displayGuiScreen(new GuiYesNo(this, "Warning", "You are about to overwrite a clone", 1));
         } else {
            this.confirmClicked(true, 0);
         }
      }

   }

}
