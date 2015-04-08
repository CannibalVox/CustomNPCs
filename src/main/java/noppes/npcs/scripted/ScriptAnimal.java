package noppes.npcs.scripted;

import net.minecraft.entity.passive.EntityAnimal;
import noppes.npcs.scripted.ScriptLiving;

public class ScriptAnimal extends ScriptLiving {

   public ScriptAnimal(EntityAnimal entity) {
      super(entity);
   }

   public int getType() {
      return 4;
   }

   public boolean typeOf(int type) {
      return type == 4?true:super.typeOf(type);
   }
}
