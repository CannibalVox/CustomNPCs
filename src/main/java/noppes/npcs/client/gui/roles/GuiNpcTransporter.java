package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.TransportLocation;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcTransporter extends GuiNPCInterface2 implements IScrollData, IGuiData {

   private GuiCustomScroll scroll;
   public TransportLocation location = new TransportLocation();
   private HashMap data = new HashMap();


   public GuiNpcTransporter(EntityNPCInterface npc) {
      super(npc);
      Client.sendData(EnumPacketServer.TransportCategoriesGet, new Object[0]);
      Client.sendData(EnumPacketServer.TransportGetLocation, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      Vector list = new Vector();
      list.addAll(this.data.keySet());
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(143, 208);
      }

      this.scroll.guiLeft = super.guiLeft + 214;
      this.scroll.guiTop = super.guiTop + 4;
      this.addScroll(this.scroll);
      this.addLabel(new GuiNpcLabel(0, "gui.name", super.guiLeft + 4, super.height + 8));
      this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 60, super.guiTop + 3, 140, 20, this.location.name));
      this.addButton(new GuiNpcButton(0, super.guiLeft + 4, super.guiTop + 31, new String[]{"transporter.discovered", "transporter.start", "transporter.interaction"}, this.location.type));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 0) {
         this.location.type = button.getValue();
      }

   }

   public void save() {
      if(this.scroll.hasSelected()) {
         String name = this.getTextField(0).getText();
         if(!name.isEmpty()) {
            this.location.name = name;
         }

         this.location.posX = super.player.posX;
         this.location.posY = super.player.posY;
         this.location.posZ = super.player.posZ;
         this.location.dimension = super.player.dimension;
         int cat = ((Integer)this.data.get(this.scroll.getSelected())).intValue();
         Client.sendData(EnumPacketServer.TransportSave, new Object[]{Integer.valueOf(cat), this.location.writeNBT()});
      }
   }

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.scroll.setList(list);
   }

   public void setSelected(String selected) {
      this.scroll.setSelected(selected);
   }

   public void setGuiData(NBTTagCompound compound) {
      TransportLocation loc = new TransportLocation();
      loc.readNBT(compound);
      this.location = loc;
      this.initGui();
   }
}
