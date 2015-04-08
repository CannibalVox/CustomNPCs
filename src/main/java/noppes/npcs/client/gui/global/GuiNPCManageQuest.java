package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiMailmanSendSetup;
import noppes.npcs.client.gui.SubGuiNpcCommand;
import noppes.npcs.client.gui.SubGuiNpcFactionOptions;
import noppes.npcs.client.gui.SubGuiNpcTextArea;
import noppes.npcs.client.gui.global.SubGuiNpcQuestAdvanced;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeDialog;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeKill;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeLocation;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ISubGuiListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCManageQuest extends GuiNPCInterface2 implements IScrollData, ISubGuiListener, GuiSelectionListener, ICustomScrollListener, ITextfieldListener, IGuiData {

   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   public static Quest quest = new Quest();
   private QuestCategory category = new QuestCategory();
   private boolean categorySelection = true;
   private boolean questlogTA = false;
   public static GuiScreen Instance;


   public GuiNPCManageQuest(EntityNPCInterface npc) {
      super(npc);
      Instance = this;
      Client.sendData(EnumPacketServer.QuestCategoriesGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(0, super.guiLeft + 358, super.guiTop + 8, 58, 20, this.categorySelection?"quest.quests":"gui.categories"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 358, super.guiTop + 38, 58, 20, "gui.add"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 358, super.guiTop + 61, 58, 20, "gui.remove"));
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(143, 208);
      }

      this.scroll.guiLeft = super.guiLeft + 214;
      this.scroll.guiTop = super.guiTop + 4;
      this.addScroll(this.scroll);
      if(this.categorySelection && this.category.id >= 0) {
         this.categoryGuiInit();
      }

      if(!this.categorySelection && quest.id >= 0) {
         this.dialogGuiInit();
      }

   }

   private void dialogGuiInit() {
      this.addLabel(new GuiNpcLabel(1, "gui.title", super.guiLeft + 4, super.guiTop + 8));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 36, super.guiTop + 3, 140, 20, quest.title));
      this.addLabel(new GuiNpcLabel(0, "ID", super.guiLeft + 178, super.guiTop + 4));
      this.addLabel(new GuiNpcLabel(2, quest.id + "", super.guiLeft + 178, super.guiTop + 14));
      this.addLabel(new GuiNpcLabel(3, "quest.completedtext", super.guiLeft + 4, super.guiTop + 30));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 120, super.guiTop + 25, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(4, "quest.questlogtext", super.guiLeft + 4, super.guiTop + 51));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 120, super.guiTop + 46, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(5, "quest.reward", super.guiLeft + 4, super.guiTop + 72));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 120, super.guiTop + 67, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(6, "gui.type", super.guiLeft + 4, super.guiTop + 93));
      this.addButton(new GuiNpcButton(6, super.guiLeft + 90, super.guiTop + 88, 70, 20, new String[]{"quest.item", "quest.dialog", "quest.kill", "quest.location", "quest.areakill"}, quest.type.ordinal()));
      this.addButton(new GuiNpcButton(7, super.guiLeft + 162, super.guiTop + 88, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(8, "quest.repeatable", super.guiLeft + 4, super.guiTop + 114));
      this.addButton(new GuiNpcButton(8, super.guiLeft + 110, super.guiTop + 109, 70, 20, new String[]{"gui.no", "gui.yes", "quest.mcdaily", "quest.mcweekly", "quest.rldaily", "quest.rlweekly"}, quest.repeat.ordinal()));
      this.addButton(new GuiNpcButton(9, super.guiLeft + 4, super.guiTop + 131, 90, 20, new String[]{"quest.npc", "quest.instant"}, quest.completion.ordinal()));
      if(quest.completerNpc.isEmpty()) {
         quest.completerNpc = super.npc.display.name;
      }

      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 96, super.guiTop + 131, 114, 20, quest.completerNpc));
      this.getTextField(2).enabled = quest.completion == EnumQuestCompletion.Npc;
      this.addLabel(new GuiNpcLabel(10, "menu.advanced", super.guiLeft + 4, super.guiTop + 158));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 120, super.guiTop + 153, 50, 20, "selectServer.edit"));
   }

   private void categoryGuiInit() {
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 8, super.guiTop + 8, 160, 16, this.category.title));
   }

   public void buttonEvent(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.save();
         if(this.categorySelection) {
            if(this.category.id < 0) {
               return;
            }

            quest = new Quest();
            Client.sendData(EnumPacketServer.QuestsGet, new Object[]{Integer.valueOf(this.category.id)});
         } else if(!this.categorySelection) {
            quest = new Quest();
            this.category = new QuestCategory();
            Client.sendData(EnumPacketServer.QuestCategoriesGet, new Object[0]);
         }

         this.categorySelection = !this.categorySelection;
         this.getButton(0).setEnabled(false);
         this.scroll.clear();
         this.data.clear();
      }

      if(button.field_146127_k == 1) {
         this.save();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
            ;
         }

         if(this.categorySelection) {
            QuestCategory quest = new QuestCategory();
            quest.title = name;
            Client.sendData(EnumPacketServer.QuestCategorySave, new Object[]{quest.writeNBT(new NBTTagCompound())});
         } else {
            Quest quest1 = new Quest();
            quest1.title = name;
            Client.sendData(EnumPacketServer.QuestSave, new Object[]{Integer.valueOf(this.category.id), quest1.writeToNBT(new NBTTagCompound())});
         }
      }

      if(button.field_146127_k == 2 && this.data.containsKey(this.scroll.getSelected())) {
         if(this.categorySelection) {
            Client.sendData(EnumPacketServer.QuestCategoryRemove, new Object[]{Integer.valueOf(this.category.id)});
            this.category = new QuestCategory();
         } else {
            Client.sendData(EnumPacketServer.QuestRemove, new Object[]{Integer.valueOf(quest.id)});
            quest = new Quest();
         }

         this.scroll.clear();
      }

      if(button.field_146127_k == 3 && quest.id >= 0) {
         this.questlogTA = false;
         this.setSubGui(new SubGuiNpcTextArea(quest.completeText));
      }

      if(button.field_146127_k == 4 && quest.id >= 0) {
         this.questlogTA = true;
         this.setSubGui(new SubGuiNpcTextArea(quest.logText));
      }

      if(button.field_146127_k == 5 && quest.id >= 0) {
         Client.sendData(EnumPacketServer.QuestOpenGui, new Object[]{EnumGuiType.QuestReward, quest.writeToNBT(new NBTTagCompound())});
      }

      if(button.field_146127_k == 6 && quest.id >= 0) {
         quest.setType(EnumQuestType.values()[button.getValue()]);
      }

      if(button.field_146127_k == 7) {
         if(quest.type == EnumQuestType.Item) {
            Client.sendData(EnumPacketServer.QuestOpenGui, new Object[]{EnumGuiType.QuestItem, quest.writeToNBT(new NBTTagCompound())});
         }

         if(quest.type == EnumQuestType.Dialog) {
            this.setSubGui(new GuiNpcQuestTypeDialog(super.npc, quest, this));
         }

         if(quest.type == EnumQuestType.Kill) {
            this.setSubGui(new GuiNpcQuestTypeKill(super.npc, quest, this));
         }

         if(quest.type == EnumQuestType.Location) {
            this.setSubGui(new GuiNpcQuestTypeLocation(super.npc, quest, this));
         }

         if(quest.type == EnumQuestType.AreaKill) {
            this.setSubGui(new GuiNpcQuestTypeKill(super.npc, quest, this));
         }
      }

      if(button.field_146127_k == 8) {
         quest.repeat = EnumQuestRepeat.values()[button.getValue()];
      }

      if(button.field_146127_k == 9) {
         quest.completion = EnumQuestCompletion.values()[button.getValue()];
         this.getTextField(2).enabled = quest.completion == EnumQuestCompletion.Npc;
      }

      if(button.field_146127_k == 10) {
         this.setSubGui(new SubGuiNpcQuestAdvanced(quest, this));
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      String name;
      String old;
      if(guiNpcTextField.id == 0) {
         if(this.category.id < 0) {
            guiNpcTextField.setText("");
         } else {
            name = guiNpcTextField.getText();
            if(!name.isEmpty() && !this.data.containsKey(name)) {
               if(this.categorySelection && this.category.id >= 0) {
                  old = this.category.title;
                  this.data.remove(this.category.title);
                  this.category.title = name;
                  this.data.put(this.category.title, Integer.valueOf(this.category.id));
                  this.scroll.replace(old, this.category.title);
               }
            } else {
               guiNpcTextField.setText(this.category.title);
            }
         }
      }

      if(guiNpcTextField.id == 1) {
         if(quest.id < 0) {
            guiNpcTextField.setText("");
         } else {
            name = guiNpcTextField.getText();
            if(!name.isEmpty() && !this.data.containsKey(name)) {
               if(!this.categorySelection && quest.id >= 0) {
                  old = quest.title;
                  this.data.remove(old);
                  quest.title = name;
                  this.data.put(quest.title, Integer.valueOf(quest.id));
                  this.scroll.replace(old, quest.title);
               }
            } else {
               guiNpcTextField.setText(quest.title);
            }
         }
      }

      if(guiNpcTextField.id == 2) {
         quest.completerNpc = guiNpcTextField.getText();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if(this.categorySelection) {
         this.category.readNBT(compound);
         this.setSelected(this.category.title);
         this.initGui();
      } else {
         quest.readNBT(compound);
         this.setSelected(quest.title);
         this.initGui();
      }

   }

   public void subGuiClosed(SubGuiInterface subgui) {
      if(subgui instanceof SubGuiNpcTextArea) {
         SubGuiNpcTextArea sub = (SubGuiNpcTextArea)subgui;
         if(this.questlogTA) {
            quest.logText = sub.text;
         } else {
            quest.completeText = sub.text;
         }
      } else if(!(subgui instanceof SubGuiNpcFactionOptions) && !(subgui instanceof SubGuiMailmanSendSetup)) {
         if(subgui instanceof SubGuiNpcCommand) {
            SubGuiNpcCommand sub1 = (SubGuiNpcCommand)subgui;
            quest.command = sub1.command;
            this.setSubGui(new SubGuiNpcQuestAdvanced(quest, this));
         } else {
            this.initGui();
         }
      } else {
         this.setSubGui(new SubGuiNpcQuestAdvanced(quest, this));
      }

   }

   public void setData(Vector list, HashMap data) {
      this.getButton(0).setEnabled(true);
      String name = this.scroll.getSelected();
      this.data = data;
      this.scroll.setList(list);
      if(name != null) {
         this.scroll.setSelected(name);
      }

      this.initGui();
   }

   public void selected(int id, String name) {
      quest.nextQuestid = id;
      quest.nextQuestTitle = name;
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if(guiCustomScroll.id == 0) {
         this.save();
         String selected = this.scroll.getSelected();
         if(this.categorySelection) {
            this.category = new QuestCategory();
            Client.sendData(EnumPacketServer.QuestCategoryGet, new Object[]{this.data.get(selected)});
         } else {
            quest = new Quest();
            Client.sendData(EnumPacketServer.QuestGet, new Object[]{this.data.get(selected)});
         }
      }

   }

   public void close() {
      super.close();
      quest = new Quest();
   }

   public void save() {
      GuiNpcTextField.unfocus();
      if(!this.categorySelection && quest.id >= 0) {
         Client.sendData(EnumPacketServer.QuestSave, new Object[]{Integer.valueOf(this.category.id), quest.writeToNBT(new NBTTagCompound())});
      } else if(this.categorySelection && this.category.id >= 0) {
         Client.sendData(EnumPacketServer.QuestCategorySave, new Object[]{this.category.writeNBT(new NBTTagCompound())});
      }

   }

   public void setSelected(String selected) {}

}
