package noppes.npcs.client.gui.player;

import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.blocks.tiles.TileBigSign;
import noppes.npcs.client.gui.SubGuiNpcTextArea;
import noppes.npcs.constants.EnumPlayerPacket;

public class GuiBigSign extends SubGuiNpcTextArea {

   public TileBigSign tile;


   public GuiBigSign(int x, int y, int z) {
      super("");
      this.tile = (TileBigSign)super.player.worldObj.getTileEntity(x, y, z);
      super.text = this.tile.getText();
   }

   public void close() {
      super.close();
      NoppesUtilPlayer.sendData(EnumPlayerPacket.SignSave, new Object[]{Integer.valueOf(this.tile.xCoord), Integer.valueOf(this.tile.yCoord), Integer.valueOf(this.tile.zCoord), super.text});
   }
}
