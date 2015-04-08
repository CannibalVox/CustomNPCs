package noppes.npcs.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityRainbowFX extends EntityFX {

   public static float[][] colorTable = new float[][]{{1.0F, 0.0F, 0.0F}, {1.0F, 0.5F, 0.0F}, {1.0F, 1.0F, 0.0F}, {0.0F, 1.0F, 0.0F}, {0.0F, 0.0F, 1.0F}, {0.0F, 4375.0F, 0.0F, 1.0F}, {0.5625F, 0.0F, 1.0F}};
   float reddustParticleScale;


   public EntityRainbowFX(World world, double d, double d1, double d2, double f, double f1, double f2) {
      this(world, d, d1, d2, 1.0F, f, f1, f2);
   }

   public EntityRainbowFX(World world, double d, double d1, double d2, float f, double f1, double f2, double f3) {
      super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
      super.motionX *= 0.10000000149011612D;
      super.motionY *= 0.10000000149011612D;
      super.motionZ *= 0.10000000149011612D;
      if(f1 == 0.0D) {
         f1 = 1.0D;
      }

      int i = world.rand.nextInt(colorTable.length);
      super.particleRed = colorTable[i][0];
      super.particleGreen = colorTable[i][1];
      super.particleBlue = colorTable[i][2];
      super.particleScale *= 0.75F;
      super.particleScale *= f;
      this.reddustParticleScale = super.particleScale;
      super.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      super.particleMaxAge = (int)((float)super.particleMaxAge * f);
      super.noClip = false;
   }

   public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
      float f6 = ((float)super.particleAge + f) / (float)super.particleMaxAge * 32.0F;
      if(f6 < 0.0F) {
         f6 = 0.0F;
      }

      if(f6 > 1.0F) {
         f6 = 1.0F;
      }

      super.particleScale = this.reddustParticleScale * f6;
      super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if(super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }

      this.setParticleTextureIndex(7 - super.particleAge * 8 / super.particleMaxAge);
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      if(super.posY == super.prevPosY) {
         super.motionX *= 1.1D;
         super.motionZ *= 1.1D;
      }

      super.motionX *= 0.9599999785423279D;
      super.motionY *= 0.9599999785423279D;
      super.motionZ *= 0.9599999785423279D;
      if(super.onGround) {
         super.motionX *= 0.699999988079071D;
         super.motionZ *= 0.699999988079071D;
      }

   }

}
