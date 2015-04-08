package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class Resistances {

   public float knockback = 1.0F;
   public float arrow = 1.0F;
   public float playermelee = 1.0F;
   public float explosion = 1.0F;


   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setFloat("Knockback", this.knockback);
      compound.setFloat("Arrow", this.arrow);
      compound.setFloat("Melee", this.playermelee);
      compound.setFloat("Explosion", this.explosion);
      return compound;
   }

   public void readToNBT(NBTTagCompound compound) {
      this.knockback = compound.getFloat("Knockback");
      this.arrow = compound.getFloat("Arrow");
      this.playermelee = compound.getFloat("Melee");
      this.explosion = compound.getFloat("Explosion");
   }

   public float applyResistance(DamageSource source, float damage) {
      if(!source.damageType.equals("arrow") && !source.damageType.equals("thrown")) {
         if(!source.damageType.equals("player") && !source.damageType.equals("mob")) {
            if(source.damageType.equals("explosion") || source.damageType.equals("explosion.player")) {
               damage *= 2.0F - this.explosion;
            }
         } else {
            damage *= 2.0F - this.playermelee;
         }
      } else {
         damage *= 2.0F - this.arrow;
      }

      return damage;
   }
}
