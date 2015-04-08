package noppes.npcs.client.model;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.model.ModelNPCMale;
import noppes.npcs.client.model.animation.AniCrawling;
import noppes.npcs.client.model.animation.AniHug;
import noppes.npcs.client.model.part.ModelBeard;
import noppes.npcs.client.model.part.ModelBreasts;
import noppes.npcs.client.model.part.ModelClaws;
import noppes.npcs.client.model.part.ModelEars;
import noppes.npcs.client.model.part.ModelFin;
import noppes.npcs.client.model.part.ModelHair;
import noppes.npcs.client.model.part.ModelHeadwear;
import noppes.npcs.client.model.part.ModelLegs;
import noppes.npcs.client.model.part.ModelMohawk;
import noppes.npcs.client.model.part.ModelSnout;
import noppes.npcs.client.model.part.ModelTail;
import noppes.npcs.client.model.part.ModelWings;
import noppes.npcs.client.model.util.ModelPartInterface;
import noppes.npcs.client.model.util.ModelScaleRenderer;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.roles.JobPuppet;
import org.lwjgl.opengl.GL11;

public class ModelMPM extends ModelNPCMale {

   private ModelPartInterface wings;
   private ModelPartInterface mohawk;
   private ModelPartInterface hair;
   private ModelPartInterface beard;
   private ModelPartInterface breasts;
   private ModelPartInterface snout;
   private ModelPartInterface ears;
   private ModelPartInterface fin;
   private ModelPartInterface clawsR;
   private ModelPartInterface clawsL;
   private ModelLegs legs;
   private ModelScaleRenderer headwear;
   private ModelTail tail;
   public ModelBase entityModel;
   public EntityLivingBase entity;
   public boolean currentlyPlayerTexture;
   public boolean isArmor;
   public float alpha = 1.0F;


   public ModelMPM(float par1) {
      super(par1);
      this.isArmor = par1 > 0.0F;
      float par2 = 0.0F;
      super.bipedCloak = new ModelRenderer(this, 0, 0);
      super.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, par1);
      super.bipedEars = new ModelRenderer(this, 24, 0);
      super.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, par1);
      super.bipedHead = new ModelScaleRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
      super.bipedHead.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
      super.bipedHeadwear = new ModelScaleRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
      super.bipedBody = new ModelScaleRenderer(this, 16, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
      super.bipedBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
      super.bipedRightArm = new ModelScaleRenderer(this, 40, 16);
      super.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, par1);
      super.bipedRightArm.setRotationPoint(-5.0F, 2.0F + par2, 0.0F);
      super.bipedLeftArm = new ModelScaleRenderer(this, 40, 16);
      super.bipedLeftArm.mirror = true;
      super.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, par1);
      super.bipedLeftArm.setRotationPoint(5.0F, 2.0F + par2, 0.0F);
      super.bipedRightLeg = new ModelScaleRenderer(this, 0, 16);
      super.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
      super.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + par2, 0.0F);
      super.bipedLeftLeg = new ModelScaleRenderer(this, 0, 16);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
      super.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + par2, 0.0F);
      this.headwear = new ModelHeadwear(this);
      this.legs = new ModelLegs(this, (ModelScaleRenderer)super.bipedRightLeg, (ModelScaleRenderer)super.bipedLeftLeg);
      this.breasts = new ModelBreasts(this);
      super.bipedBody.addChild(this.breasts);
      if(!this.isArmor) {
         this.ears = new ModelEars(this);
         super.bipedHead.addChild(this.ears);
         this.mohawk = new ModelMohawk(this);
         super.bipedHead.addChild(this.mohawk);
         this.hair = new ModelHair(this);
         super.bipedHead.addChild(this.hair);
         this.beard = new ModelBeard(this);
         super.bipedHead.addChild(this.beard);
         this.snout = new ModelSnout(this);
         super.bipedHead.addChild(this.snout);
         this.tail = new ModelTail(this);
         this.wings = new ModelWings(this);
         super.bipedBody.addChild(this.wings);
         this.fin = new ModelFin(this);
         super.bipedBody.addChild(this.fin);
         this.clawsL = new ModelClaws(this, false);
         super.bipedLeftArm.addChild(this.clawsL);
         this.clawsR = new ModelClaws(this, true);
         super.bipedRightArm.addChild(this.clawsR);
      }

   }

   private void setPlayerData(EntityCustomNpc entity) {
      if(!this.isArmor) {
         this.mohawk.setData(entity.modelData, entity);
         this.beard.setData(entity.modelData, entity);
         this.hair.setData(entity.modelData, entity);
         this.snout.setData(entity.modelData, entity);
         this.tail.setData(entity);
         this.fin.setData(entity.modelData, entity);
         this.wings.setData(entity.modelData, entity);
         this.ears.setData(entity.modelData, entity);
         this.clawsL.setData(entity.modelData, entity);
         this.clawsR.setData(entity.modelData, entity);
      }

      this.breasts.setData(entity.modelData, entity);
      this.legs.setData(entity);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      EntityCustomNpc npc = (EntityCustomNpc)par1Entity;
      if(this.entityModel != null) {
         if(!this.isArmor) {
            this.entityModel.isChild = this.entity.isChild();
            this.entityModel.swingProgress = super.swingProgress;
            this.entityModel.isRiding = super.isRiding;
            if(this.entityModel instanceof ModelBiped) {
               ModelBiped job = (ModelBiped)this.entityModel;
               job.aimedBow = super.aimedBow;
               job.heldItemLeft = super.heldItemLeft;
               job.heldItemRight = super.heldItemRight;
               job.isSneak = super.isSneak;
            }

            this.entityModel.render(this.entity, par2, par3, par4, par5, par6, par7);
         }
      } else {
         this.alpha = npc.isInvisible() && !npc.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)?0.15F:1.0F;
         this.setPlayerData(npc);
         this.currentlyPlayerTexture = true;
         this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
         if(npc.advanced.job == EnumJobType.Puppet) {
            JobPuppet job1 = (JobPuppet)npc.jobInterface;
            if(job1.isActive()) {
               float pi = 3.1415927F;
               if(!job1.head.disabled) {
                  super.bipedHeadwear.rotateAngleX = super.bipedHead.rotateAngleX = job1.head.rotationX * pi;
                  super.bipedHeadwear.rotateAngleY = super.bipedHead.rotateAngleY = job1.head.rotationY * pi;
                  super.bipedHeadwear.rotateAngleZ = super.bipedHead.rotateAngleZ = job1.head.rotationZ * pi;
               }

               if(!job1.body.disabled) {
                  super.bipedBody.rotateAngleX = job1.body.rotationX * pi;
                  super.bipedBody.rotateAngleY = job1.body.rotationY * pi;
                  super.bipedBody.rotateAngleZ = job1.body.rotationZ * pi;
               }

               if(!job1.larm.disabled) {
                  super.bipedLeftArm.rotateAngleX = job1.larm.rotationX * pi;
                  super.bipedLeftArm.rotateAngleY = job1.larm.rotationY * pi;
                  super.bipedLeftArm.rotateAngleZ = job1.larm.rotationZ * pi;
               }

               if(!job1.rarm.disabled) {
                  super.bipedRightArm.rotateAngleX = job1.rarm.rotationX * pi;
                  super.bipedRightArm.rotateAngleY = job1.rarm.rotationY * pi;
                  super.bipedRightArm.rotateAngleZ = job1.rarm.rotationZ * pi;
               }

               if(!job1.rleg.disabled) {
                  super.bipedRightLeg.rotateAngleX = job1.rleg.rotationX * pi;
                  super.bipedRightLeg.rotateAngleY = job1.rleg.rotationY * pi;
                  super.bipedRightLeg.rotateAngleZ = job1.rleg.rotationZ * pi;
               }

               if(!job1.lleg.disabled) {
                  super.bipedLeftLeg.rotateAngleX = job1.lleg.rotationX * pi;
                  super.bipedLeftLeg.rotateAngleY = job1.lleg.rotationY * pi;
                  super.bipedLeftLeg.rotateAngleZ = job1.lleg.rotationZ * pi;
               }
            }
         }

         this.renderHead(npc, par7);
         this.renderArms(npc, par7, false);
         this.renderBody(npc, par7);
         this.renderLegs(npc, par7);
      }

   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      EntityCustomNpc npc = (EntityCustomNpc)entity;
      super.isRiding = npc.isRiding();
      if(super.isSneak && (npc.currentAnimation == EnumAnimation.CRAWLING || npc.currentAnimation == EnumAnimation.LYING)) {
         super.isSneak = false;
      }

      super.bipedBody.rotationPointX = 0.0F;
      super.bipedBody.rotationPointY = 0.0F;
      super.bipedBody.rotationPointZ = 0.0F;
      super.bipedBody.rotateAngleX = 0.0F;
      super.bipedBody.rotateAngleY = 0.0F;
      super.bipedBody.rotateAngleZ = 0.0F;
      super.bipedHead.rotateAngleZ = 0.0F;
      super.bipedHeadwear.rotateAngleZ = 0.0F;
      super.bipedLeftLeg.rotateAngleX = 0.0F;
      super.bipedLeftLeg.rotateAngleY = 0.0F;
      super.bipedLeftLeg.rotateAngleZ = 0.0F;
      super.bipedRightLeg.rotateAngleX = 0.0F;
      super.bipedRightLeg.rotateAngleY = 0.0F;
      super.bipedRightLeg.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotationPointY = 2.0F;
      super.bipedLeftArm.rotationPointZ = 0.0F;
      super.bipedRightArm.rotationPointY = 2.0F;
      super.bipedRightArm.rotationPointZ = 0.0F;
      super.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
      if(!this.isArmor) {
         this.hair.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         this.beard.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         this.wings.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         this.tail.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
      }

      this.legs.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
      if(this.isSleeping(entity)) {
         if(super.bipedHead.rotateAngleX < 0.0F) {
            super.bipedHead.rotateAngleX = 0.0F;
            super.bipedHeadwear.rotateAngleX = 0.0F;
         }
      } else if(npc.currentAnimation == EnumAnimation.CRY) {
         super.bipedHeadwear.rotateAngleX = super.bipedHead.rotateAngleX = 0.7F;
      } else if(npc.currentAnimation == EnumAnimation.HUG) {
         AniHug.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
      } else if(npc.currentAnimation == EnumAnimation.CRAWLING) {
         AniCrawling.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
      } else if(npc.currentAnimation == EnumAnimation.WAVING) {
         super.bipedRightArm.rotateAngleX = -0.1F;
         super.bipedRightArm.rotateAngleY = 0.0F;
         super.bipedRightArm.rotateAngleZ = (float)(2.141592653589793D - Math.sin((double)((float)entity.ticksExisted * 0.27F)) * 0.5D);
      } else if(super.isSneak) {
         super.bipedBody.rotateAngleX = 0.5F / npc.modelData.body.scaleY;
      }

   }

   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
      if(this.entityModel != null) {
         this.entityModel.setLivingAnimations(this.entity, par2, par3, par4);
      } else {
         EntityCustomNpc npc = (EntityCustomNpc)par1EntityLivingBase;
         if(!this.isArmor) {
            ModelPartData partData = npc.modelData.getPartData("tail");
            if(partData != null) {
               this.tail.setLivingAnimations(partData, par1EntityLivingBase, par2, par3, par4);
            }
         }
      }

   }

   public void loadPlayerTexture(EntityCustomNpc npc) {
      if(!this.isArmor && !this.currentlyPlayerTexture) {
         ClientProxy.bindTexture(npc.textureLocation);
         this.currentlyPlayerTexture = true;
      }

   }

   private void renderHead(EntityCustomNpc entity, float f) {
      this.loadPlayerTexture(entity);
      float x = 0.0F;
      float y = entity.modelData.getBodyY();
      float z = 0.0F;
      GL11.glPushMatrix();
      if(entity.currentAnimation == EnumAnimation.DANCING) {
         float head = (float)entity.ticksExisted / 4.0F;
         GL11.glTranslatef((float)Math.sin((double)head) * 0.075F, (float)Math.abs(Math.cos((double)head)) * 0.125F - 0.02F, (float)(-Math.abs(Math.cos((double)head))) * 0.075F);
      }

      ModelPartConfig head1 = entity.modelData.head;
      if(super.bipedHeadwear.showModel && !super.bipedHeadwear.isHidden) {
         if(entity.modelData.headwear != 1 && !this.isArmor) {
            if(entity.modelData.headwear == 2) {
               this.headwear.rotateAngleX = super.bipedHeadwear.rotateAngleX;
               this.headwear.rotateAngleY = super.bipedHeadwear.rotateAngleY;
               this.headwear.rotateAngleZ = super.bipedHeadwear.rotateAngleZ;
               this.headwear.rotationPointX = super.bipedHeadwear.rotationPointX;
               this.headwear.rotationPointY = super.bipedHeadwear.rotationPointY;
               this.headwear.rotationPointZ = super.bipedHeadwear.rotationPointZ;
               this.headwear.setConfig(head1, x, y, z);
               this.headwear.render(f);
            }
         } else {
            ((ModelScaleRenderer)super.bipedHeadwear).setConfig(head1, x, y, z);
            ((ModelScaleRenderer)super.bipedHeadwear).render(f);
         }
      }

      ((ModelScaleRenderer)super.bipedHead).setConfig(head1, x, y, z);
      ((ModelScaleRenderer)super.bipedHead).render(f);
      GL11.glPopMatrix();
   }

   private void renderBody(EntityCustomNpc entity, float f) {
      this.loadPlayerTexture(entity);
      float x = 0.0F;
      float y = entity.modelData.getBodyY();
      float z = 0.0F;
      GL11.glPushMatrix();
      if(entity.currentAnimation == EnumAnimation.DANCING) {
         float body = (float)entity.ticksExisted / 4.0F;
         GL11.glTranslatef((float)Math.sin((double)body) * 0.015F, 0.0F, 0.0F);
      }

      ModelPartConfig body1 = entity.modelData.body;
      ((ModelScaleRenderer)super.bipedBody).setConfig(body1, x, y, z);
      ((ModelScaleRenderer)super.bipedBody).render(f);
      GL11.glPopMatrix();
   }

   public void renderArms(EntityCustomNpc entity, float f, boolean bo) {
      this.loadPlayerTexture(entity);
      ModelPartConfig arms = entity.modelData.arms;
      float x = (1.0F - entity.modelData.body.scaleX) * 0.25F + (1.0F - arms.scaleX) * 0.075F;
      float y = entity.modelData.getBodyY() + (1.0F - arms.scaleY) * -0.1F;
      float z = 0.0F;
      GL11.glPushMatrix();
      if(entity.currentAnimation == EnumAnimation.DANCING) {
         float dancing = (float)entity.ticksExisted / 4.0F;
         GL11.glTranslatef((float)Math.sin((double)dancing) * 0.025F, (float)Math.abs(Math.cos((double)dancing)) * 0.125F - 0.02F, 0.0F);
      }

      if(!bo) {
         ((ModelScaleRenderer)super.bipedLeftArm).setConfig(arms, -x, y, z);
         ((ModelScaleRenderer)super.bipedLeftArm).render(f);
         ((ModelScaleRenderer)super.bipedRightArm).setConfig(arms, x, y, z);
         ((ModelScaleRenderer)super.bipedRightArm).render(f);
      } else {
         ((ModelScaleRenderer)super.bipedRightArm).setConfig(arms, 0.0F, 0.0F, 0.0F);
         ((ModelScaleRenderer)super.bipedRightArm).render(f);
      }

      GL11.glPopMatrix();
   }

   private void renderLegs(EntityCustomNpc entity, float f) {
      this.loadPlayerTexture(entity);
      ModelPartConfig legs = entity.modelData.legs;
      float x = (1.0F - legs.scaleX) * 0.125F;
      float y = entity.modelData.getLegsY();
      float z = 0.0F;
      GL11.glPushMatrix();
      this.legs.setConfig(legs, x, y, z);
      this.legs.render(f);
      if(!this.isArmor) {
         this.tail.setConfig(legs, 0.0F, y, z);
         this.tail.render(f);
      }

      GL11.glPopMatrix();
   }

   public ModelRenderer getRandomModelBox(Random par1Random) {
      int random = par1Random.nextInt(5);
      switch(random) {
      case 0:
         return super.bipedRightLeg;
      case 1:
         return super.bipedHead;
      case 2:
         return super.bipedLeftArm;
      case 3:
         return super.bipedRightArm;
      case 4:
         return super.bipedLeftLeg;
      default:
         return super.bipedBody;
      }
   }
}
