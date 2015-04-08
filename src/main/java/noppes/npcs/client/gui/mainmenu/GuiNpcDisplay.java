package noppes.npcs.client.gui.mainmenu;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataDisplay;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCTextures;
import noppes.npcs.client.gui.GuiNpcTextureCloaks;
import noppes.npcs.client.gui.GuiNpcTextureOverlays;
import noppes.npcs.client.gui.model.GuiCreationScreen;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcDisplay extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {

   private DataDisplay display;


   public GuiNpcDisplay(EntityNPCInterface npc) {
      super(npc, 1);
      this.display = npc.display;
      Client.sendData(EnumPacketServer.MainmenuDisplayGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 4;
      this.addLabel(new GuiNpcLabel(0, "gui.name", super.guiLeft + 5, y + 5));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 50, y, 200, 20, this.display.name));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 253, y, 110, 20, new String[]{"display.show", "display.hide", "display.showAttacking"}, this.display.showName));
      y += 23;
      this.addLabel(new GuiNpcLabel(11, "gui.title", super.guiLeft + 5, y + 5));
      this.addTextField(new GuiNpcTextField(11, this, super.fontRendererObj, super.guiLeft + 50, y, 200, 20, this.display.title));
      y += 23;
      this.addLabel(new GuiNpcLabel(1, "display.model", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 50, y, 110, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(2, "display.size", super.guiLeft + 175, y + 5));
      this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 203, y, 40, 20, this.display.modelSize + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(1, 30, 5);
      this.addLabel(new GuiNpcLabel(3, "(1-30)", super.guiLeft + 246, y + 5));
      y += 23;
      this.addLabel(new GuiNpcLabel(4, "display.texture", super.guiLeft + 5, y + 5));
      this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 80, y, 200, 20, this.display.skinType == 0?this.display.texture:this.display.url));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 325, y, 38, 20, "mco.template.button.select"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 283, y, 40, 20, new String[]{"display.texture", "display.player", "display.url"}, this.display.skinType));
      this.getButton(3).setEnabled(this.display.skinType == 0);
      if(this.display.skinType == 1 && this.display.playerProfile != null) {
         this.getTextField(3).setText(this.display.playerProfile.getName());
      }

      y += 23;
      this.addLabel(new GuiNpcLabel(8, "display.cape", super.guiLeft + 5, y + 5));
      this.addTextField(new GuiNpcTextField(8, this, super.fontRendererObj, super.guiLeft + 80, y, 200, 20, this.display.cloakTexture));
      this.addButton(new GuiNpcButton(8, super.guiLeft + 283, y, 80, 20, "display.selectTexture"));
      y += 23;
      this.addLabel(new GuiNpcLabel(9, "display.overlay", super.guiLeft + 5, y + 5));
      this.addTextField(new GuiNpcTextField(9, this, super.fontRendererObj, super.guiLeft + 80, y, 200, 20, this.display.glowTexture));
      this.addButton(new GuiNpcButton(9, super.guiLeft + 283, y, 80, 20, "display.selectTexture"));
      y += 23;
      this.addLabel(new GuiNpcLabel(5, "display.livingAnimation", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 120, y, 50, 20, new String[]{"gui.yes", "gui.no"}, this.display.NoLivingAnimation?1:0));
      y += 23;
      this.addLabel(new GuiNpcLabel(7, "display.visible", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(7, super.guiLeft + 120, y, 50, 20, new String[]{"gui.yes", "gui.no", "gui.partly"}, this.display.visible));
      y += 23;
      this.addLabel(new GuiNpcLabel(10, "display.bossbar", super.guiLeft + 5, y + 5));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 120, y, 110, 20, new String[]{"display.hide", "display.show", "display.showAttacking"}, this.display.showBossBar));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if(textfield.id == 0) {
         if(!textfield.isEmpty()) {
            this.display.name = textfield.getText();
         } else {
            textfield.setText(this.display.name);
         }
      } else if(textfield.id == 2) {
         this.display.modelSize = textfield.getInteger();
      } else if(textfield.id == 3) {
         if(this.display.skinType == 2) {
            this.display.url = textfield.getText();
         } else if(this.display.skinType == 1) {
            this.display.playerProfile = new GameProfile((UUID)null, textfield.getText());
         } else {
            this.display.texture = textfield.getText();
         }
      } else if(textfield.id == 8) {
         super.npc.textureCloakLocation = null;
         this.display.cloakTexture = textfield.getText();
      } else if(textfield.id == 9) {
         super.npc.textureGlowLocation = null;
         this.display.glowTexture = textfield.getText();
      } else if(textfield.id == 11) {
         this.display.title = textfield.getText();
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.display.showName = button.getValue();
      }

      if(button.field_146127_k == 1) {
         NoppesUtil.openGUI(super.player, new GuiCreationScreen(this, (EntityCustomNpc)super.npc));
      }

      if(button.field_146127_k == 2) {
         this.display.skinType = (byte)button.getValue();
         this.display.url = "";
         this.display.playerProfile = null;
         this.initGui();
      } else if(button.field_146127_k == 3) {
         NoppesUtil.openGUI(super.player, new GuiNPCTextures(super.npc, this));
      } else if(button.field_146127_k == 5) {
         this.display.NoLivingAnimation = button.getValue() == 1;
      } else if(button.field_146127_k == 7) {
         this.display.visible = button.getValue();
      } else if(button.field_146127_k == 8) {
         NoppesUtil.openGUI(super.player, new GuiNpcTextureCloaks(super.npc, this));
      } else if(button.field_146127_k == 9) {
         NoppesUtil.openGUI(super.player, new GuiNpcTextureOverlays(super.npc, this));
      } else if(button.field_146127_k == 10) {
         this.display.showBossBar = (byte)button.getValue();
      }

   }

   public void save() {
      if(this.display.skinType == 1) {
         this.display.loadProfile();
      }

      super.npc.textureLocation = null;
      super.mc.renderGlobal.onEntityDestroy(super.npc);
      super.mc.renderGlobal.onEntityCreate(super.npc);
      Client.sendData(EnumPacketServer.MainmenuDisplaySave, new Object[]{this.display.writeToNBT(new NBTTagCompound())});
   }

   public void setGuiData(NBTTagCompound compound) {
      this.display.readToNBT(compound);
      this.initGui();
   }
}
