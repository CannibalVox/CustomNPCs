package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.controllers.Bank;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCManageBanks extends GuiContainerNPCInterface2 implements IScrollData, ICustomScrollListener, ITextfieldListener, IGuiData {

   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private ContainerManageBanks container;
   private Bank bank = new Bank();
   private String selected = null;


   public GuiNPCManageBanks(EntityNPCInterface npc, ContainerManageBanks container) {
      super(npc, container);
      this.container = container;
      super.drawDefaultBackground = false;
      Client.sendData(EnumPacketServer.BanksGet, new Object[0]);
      this.setBackground("npcbanksetup.png");
      super.ySize = 200;
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(6, super.field_147003_i + 340, super.field_147009_r + 10, 45, 20, "gui.add"));
      this.addButton(new GuiNpcButton(7, super.field_147003_i + 340, super.field_147009_r + 32, 45, 20, "gui.remove"));
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
      }

      this.scroll.setSize(160, 180);
      this.scroll.guiLeft = super.field_147003_i + 174;
      this.scroll.guiTop = super.field_147009_r + 8;
      this.addScroll(this.scroll);

      for(int i = 0; i < 6; ++i) {
         int x = super.field_147003_i + 6;
         int y = super.field_147009_r + 36 + i * 22;
         this.addButton(new GuiNpcButton(i, x + 50, y, 80, 20, new String[]{"Can Upgrade", "Can\'t Upgrade", "Upgraded"}, 0));
         this.getButton(i).setEnabled(false);
      }

      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.field_147003_i + 8, super.field_147009_r + 8, 160, 16, ""));
      this.getTextField(0).setMaxStringLength(20);
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.field_147003_i + 10, super.field_147009_r + 80, 16, 16, ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMaxStringLength(1);
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.field_147003_i + 10, super.field_147009_r + 110, 16, 16, ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMaxStringLength(1);
   }

   public void drawScreen(int x, int y, float f) {
      super.drawScreen(x, y, f);
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 6) {
         this.save();
         this.scroll.clear();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
            ;
         }

         Bank bank = new Bank();
         bank.name = name;
         NBTTagCompound compound = new NBTTagCompound();
         bank.writeEntityToNBT(compound);
         Client.sendData(EnumPacketServer.BankSave, new Object[]{compound});
      } else if(button.field_146127_k == 7) {
         if(this.data.containsKey(this.scroll.getSelected())) {
            Client.sendData(EnumPacketServer.BankRemove, new Object[]{this.data.get(this.selected)});
         }
      } else if(button.field_146127_k >= 0 && button.field_146127_k < 6) {
         this.bank.slotTypes.put(Integer.valueOf(button.field_146127_k), Integer.valueOf(button.getValue()));
      }

   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
      super.fontRendererObj.drawString("Tab Cost", 23, 28, CustomNpcResourceListener.DefaultTextColor);
      super.fontRendererObj.drawString("Upg. Cost", 123, 28, CustomNpcResourceListener.DefaultTextColor);
      super.fontRendererObj.drawString("Start", 6, 70, CustomNpcResourceListener.DefaultTextColor);
      super.fontRendererObj.drawString("Max", 9, 100, CustomNpcResourceListener.DefaultTextColor);
   }

   public void setGuiData(NBTTagCompound compound) {
      Bank bank = new Bank();
      bank.readEntityFromNBT(compound);
      this.bank = bank;
      int i;
      if(bank.id == -1) {
         this.getTextField(0).setText("");
         this.getTextField(1).setText("");
         this.getTextField(2).setText("");

         for(i = 0; i < 6; ++i) {
            this.getButton(i).setDisplay(0);
            this.getButton(i).setEnabled(false);
         }
      } else {
         this.getTextField(0).setText(bank.name);
         this.getTextField(1).setText(Integer.toString(bank.startSlots));
         this.getTextField(2).setText(Integer.toString(bank.maxSlots));

         for(i = 0; i < 6; ++i) {
            int type = 0;
            if(bank.slotTypes.containsKey(Integer.valueOf(i))) {
               type = ((Integer)bank.slotTypes.get(Integer.valueOf(i))).intValue();
            }

            this.getButton(i).setDisplay(type);
            this.getButton(i).setEnabled(true);
         }
      }

      this.setSelected(bank.name);
   }

   public void setData(Vector list, HashMap data) {
      String name = this.scroll.getSelected();
      this.data = data;
      this.scroll.setList(list);
      if(name != null) {
         this.scroll.setSelected(name);
      }

   }

   public void setSelected(String selected) {
      this.selected = selected;
      this.scroll.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if(guiCustomScroll.id == 0) {
         this.save();
         this.selected = this.scroll.getSelected();
         Client.sendData(EnumPacketServer.BankGet, new Object[]{this.data.get(this.selected)});
      }

   }

   public void save() {
      if(this.selected != null && this.data.containsKey(this.selected) && this.bank != null) {
         NBTTagCompound compound = new NBTTagCompound();
         this.bank.currencyInventory = this.container.bank.currencyInventory;
         this.bank.upgradeInventory = this.container.bank.upgradeInventory;
         this.bank.writeEntityToNBT(compound);
         Client.sendData(EnumPacketServer.BankSave, new Object[]{compound});
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      if(this.bank.id != -1) {
         if(guiNpcTextField.id == 0) {
            String num = guiNpcTextField.getText();
            if(!num.isEmpty() && !this.data.containsKey(num)) {
               String old = this.bank.name;
               this.data.remove(this.bank.name);
               this.bank.name = num;
               this.data.put(this.bank.name, Integer.valueOf(this.bank.id));
               this.selected = num;
               this.scroll.replace(old, this.bank.name);
            }
         } else if(guiNpcTextField.id == 1 || guiNpcTextField.id == 2) {
            int num1 = 1;
            if(!guiNpcTextField.isEmpty()) {
               num1 = guiNpcTextField.getInteger();
            }

            if(num1 > 6) {
               num1 = 6;
            }

            if(num1 < 0) {
               num1 = 0;
            }

            if(guiNpcTextField.id == 1) {
               this.bank.startSlots = num1;
            } else if(guiNpcTextField.id == 2) {
               this.bank.maxSlots = num1;
            }

            if(this.bank.startSlots > this.bank.maxSlots) {
               this.bank.maxSlots = this.bank.startSlots;
            }

            guiNpcTextField.setText(Integer.toString(num1));
         }
      }

   }
}
