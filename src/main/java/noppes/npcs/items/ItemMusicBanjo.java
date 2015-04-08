package noppes.npcs.items;

import noppes.npcs.items.ItemMusic;
import org.lwjgl.opengl.GL11;

public class ItemMusicBanjo extends ItemMusic {

   public void renderSpecial() {
      GL11.glScalef(0.85F, 0.85F, 0.85F);
      GL11.glTranslatef(0.1F, 0.4F, -0.14F);
      GL11.glRotatef(-90.0F, -1.0F, 0.0F, 0.0F);
   }
}
