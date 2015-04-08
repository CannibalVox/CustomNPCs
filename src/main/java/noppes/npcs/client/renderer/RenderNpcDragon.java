package noppes.npcs.client.renderer;

import net.minecraft.client.model.ModelBase;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderNpcDragon extends RenderNPCInterface {

   public RenderNpcDragon(ModelBase model, float f) {
      super(model, f);
   }

   protected void renderPlayerScale(EntityNPCInterface npc, float f) {
      GL11.glTranslatef(0.0F, 0.0F, 0.120000005F * (float)npc.display.modelSize);
      super.renderPlayerScale(npc, f);
   }
}
