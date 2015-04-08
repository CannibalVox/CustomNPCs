package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.blocks.tiles.TileRedstoneBlock;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.SubGuiNpcAvailability;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketServer;

public class GuiNpcRedstoneBlock extends GuiNPCInterface {

   private TileRedstoneBlock tile;


   public GuiNpcRedstoneBlock(int x, int y, int z) {
      this.tile = (TileRedstoneBlock)super.player.worldObj.getTileEntity(x, y, z);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(4, super.guiLeft + 40, super.guiTop + 20, 120, 20, "availability.options"));
      this.addLabel(new GuiNpcLabel(11, "gui.detailed", super.guiLeft + 40, super.guiTop + 47, 16777215));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 110, super.guiTop + 42, 50, 20, new String[]{"gui.no", "gui.yes"}, this.tile.isDetailed?1:0));
      if(this.tile.isDetailed) {
         this.addLabel(new GuiNpcLabel(0, StatCollector.translateToLocal("bard.ondistance") + " X:", super.guiLeft + 1, super.guiTop + 76, 16777215));
         this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 71, 30, 20, this.tile.onRangeX + ""));
         this.getTextField(0).numbersOnly = true;
         this.getTextField(0).setMinMaxDefault(0, 50, 6);
         this.addLabel(new GuiNpcLabel(1, "Y:", super.guiLeft + 113, super.guiTop + 76, 16777215));
         this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 122, super.guiTop + 71, 30, 20, this.tile.onRangeY + ""));
         this.getTextField(1).numbersOnly = true;
         this.getTextField(1).setMinMaxDefault(0, 50, 6);
         this.addLabel(new GuiNpcLabel(2, "Z:", super.guiLeft + 155, super.guiTop + 76, 16777215));
         this.addTextField(new GuiNpcTextField(2, this, super.fontRendererObj, super.guiLeft + 164, super.guiTop + 71, 30, 20, this.tile.onRangeZ + ""));
         this.getTextField(2).numbersOnly = true;
         this.getTextField(2).setMinMaxDefault(0, 50, 6);
         this.addLabel(new GuiNpcLabel(3, StatCollector.translateToLocal("bard.offdistance") + " X:", super.guiLeft - 3, super.guiTop + 99, 16777215));
         this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 94, 30, 20, this.tile.offRangeX + ""));
         this.getTextField(3).numbersOnly = true;
         this.getTextField(3).setMinMaxDefault(0, 50, 10);
         this.addLabel(new GuiNpcLabel(4, "Y:", super.guiLeft + 113, super.guiTop + 99, 16777215));
         this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.guiLeft + 122, super.guiTop + 94, 30, 20, this.tile.offRangeY + ""));
         this.getTextField(4).numbersOnly = true;
         this.getTextField(4).setMinMaxDefault(0, 50, 10);
         this.addLabel(new GuiNpcLabel(5, "Z:", super.guiLeft + 155, super.guiTop + 99, 16777215));
         this.addTextField(new GuiNpcTextField(5, this, super.fontRendererObj, super.guiLeft + 164, super.guiTop + 94, 30, 20, this.tile.offRangeZ + ""));
         this.getTextField(5).numbersOnly = true;
         this.getTextField(5).setMinMaxDefault(0, 50, 10);
      } else {
         this.addLabel(new GuiNpcLabel(0, "bard.ondistance", super.guiLeft + 1, super.guiTop + 76, 16777215));
         this.addTextField(new GuiNpcTextField(0, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 71, 30, 20, this.tile.onRange + ""));
         this.getTextField(0).numbersOnly = true;
         this.getTextField(0).setMinMaxDefault(0, 50, 6);
         this.addLabel(new GuiNpcLabel(3, "bard.offdistance", super.guiLeft - 3, super.guiTop + 99, 16777215));
         this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.guiLeft + 80, super.guiTop + 94, 30, 20, this.tile.offRange + ""));
         this.getTextField(3).numbersOnly = true;
         this.getTextField(3).setMinMaxDefault(0, 50, 10);
      }

      this.addButton(new GuiNpcButton(0, super.guiLeft + 40, super.guiTop + 190, 120, 20, "Done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.close();
      }

      if(id == 1) {
         this.tile.isDetailed = ((GuiNpcButton)guibutton).getValue() == 1;
         this.initGui();
      }

      if(id == 4) {
         this.save();
         this.setSubGui(new SubGuiNpcAvailability(this.tile.availability));
      }

   }

   public void save() {
      if(this.tile != null) {
         if(this.tile.isDetailed) {
            this.tile.onRangeX = this.getTextField(0).getInteger();
            this.tile.onRangeY = this.getTextField(1).getInteger();
            this.tile.onRangeZ = this.getTextField(2).getInteger();
            this.tile.offRangeX = this.getTextField(3).getInteger();
            this.tile.offRangeY = this.getTextField(4).getInteger();
            this.tile.offRangeZ = this.getTextField(5).getInteger();
            if(this.tile.onRangeX > this.tile.offRangeX) {
               this.tile.offRangeX = this.tile.onRangeX;
            }

            if(this.tile.onRangeY > this.tile.offRangeY) {
               this.tile.offRangeY = this.tile.onRangeY;
            }

            if(this.tile.onRangeZ > this.tile.offRangeZ) {
               this.tile.offRangeZ = this.tile.onRangeZ;
            }
         } else {
            this.tile.onRange = this.getTextField(0).getInteger();
            this.tile.offRange = this.getTextField(3).getInteger();
            if(this.tile.onRange > this.tile.offRange) {
               this.tile.offRange = this.tile.onRange;
            }
         }

         this.tile.isActivated = false;
         NBTTagCompound compound = new NBTTagCompound();
         this.tile.writeToNBT(compound);
         Client.sendData(EnumPacketServer.SaveTileEntity, new Object[]{compound});
      }
   }
}
