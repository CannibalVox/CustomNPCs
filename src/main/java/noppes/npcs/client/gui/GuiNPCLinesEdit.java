package noppes.npcs.client.gui;

import java.util.HashMap;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcSoundSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.Lines;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNPCLinesEdit extends GuiNPCInterface2 implements IGuiData {

   private Lines lines;
   private GuiNpcTextField field;
   private GuiNpcSoundSelection gui;


   public GuiNPCLinesEdit(EntityNPCInterface npc, Lines lines) {
      super(npc);
      this.lines = lines;
      Client.sendData(EnumPacketServer.MainmenuAdvancedGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();

      for(int i = 0; i < 8; ++i) {
         String text = "";
         String sound = "";
         if(this.lines.lines.containsKey(Integer.valueOf(i))) {
            Line line = (Line)this.lines.lines.get(Integer.valueOf(i));
            text = line.text;
            sound = line.sound;
         }

         this.addTextField(new GuiNpcTextField(i, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 4 + i * 24, 200, 20, text));
         this.addTextField(new GuiNpcTextField(i + 8, this, super.fontRendererObj, super.guiLeft + 208, super.guiTop + 4 + i * 24, 146, 20, sound));
         this.addButton(new GuiNpcButton(i, super.guiLeft + 358, super.guiTop + 4 + i * 24, 60, 20, "mco.template.button.select"));
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      this.field = this.getTextField(button.field_146127_k + 8);
      NoppesUtil.openGUI(super.player, this.gui = new GuiNpcSoundSelection(super.npc, this, this.field.getText()));
   }

   public void elementClicked() {
      this.field.setText(this.gui.getSelected());
      this.saveLines();
   }

   public void setGuiData(NBTTagCompound compound) {
      super.npc.advanced.readToNBT(compound);
      this.initGui();
   }

   private void saveLines() {
      HashMap lines = new HashMap();

      for(int i = 0; i < 8; ++i) {
         GuiNpcTextField tf = this.getTextField(i);
         GuiNpcTextField tf2 = this.getTextField(i + 8);
         if(!tf.isEmpty()) {
            Line line = new Line();
            line.text = tf.getText();
            line.sound = tf2.getText();
            lines.put(Integer.valueOf(i), line);
         }
      }

      this.lines.lines = lines;
   }

   public void save() {
      this.saveLines();
      Client.sendData(EnumPacketServer.MainmenuAdvancedSave, new Object[]{super.npc.advanced.writeToNBT(new NBTTagCompound())});
   }
}
