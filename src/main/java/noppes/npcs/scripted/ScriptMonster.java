package noppes.npcs.scripted;

import net.minecraft.entity.monster.EntityMob;
import noppes.npcs.scripted.ScriptLiving;

public class ScriptMonster extends ScriptLiving {

   public ScriptMonster(EntityMob entity) {
      super(entity);
   }

   public int getType() {
      return 3;
   }

   public boolean typeOf(int type) {
      return type == 3?true:super.typeOf(type);
   }
}
