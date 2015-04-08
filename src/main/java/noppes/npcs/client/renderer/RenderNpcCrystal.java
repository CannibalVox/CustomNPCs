package noppes.npcs.client.renderer;

import noppes.npcs.client.model.ModelNpcCrystal;
import noppes.npcs.client.renderer.RenderNPCInterface;

public class RenderNpcCrystal extends RenderNPCInterface {

   ModelNpcCrystal mainmodel;


   public RenderNpcCrystal(ModelNpcCrystal model) {
      super(model, 0.0F);
      this.mainmodel = model;
   }
}
