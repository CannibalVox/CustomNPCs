package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.part.tails.ModelDragonTail;
import noppes.npcs.client.model.part.tails.ModelSquirrelTail;
import noppes.npcs.client.model.util.ModelScaleRenderer;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.opengl.GL11;

public class ModelTail extends ModelScaleRenderer {

   private EntityCustomNpc entity;
   private ModelMPM base;
   private ModelRenderer tail;
   private ModelRenderer dragon;
   private ModelRenderer squirrel;
   private ModelRenderer horse;
   private int color = 16777215;
   private ResourceLocation location = null;


   public ModelTail(ModelMPM base) {
      super(base);
      this.base = base;
      super.rotationPointY = 11.0F;
      this.tail = new ModelRenderer(base, 56, 21);
      this.tail.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 2);
      this.tail.setRotationPoint(0.0F, 0.0F, 1.0F);
      this.setRotation(this.tail, 0.8714253F, 0.0F, 0.0F);
      this.addChild(this.tail);
      this.horse = new ModelRenderer(base);
      this.horse.setTextureSize(32, 32);
      this.horse.setRotationPoint(0.0F, -1.0F, 1.0F);
      this.addChild(this.horse);
      ModelRenderer tailBase = new ModelRenderer(base, 0, 26);
      tailBase.setTextureSize(32, 32);
      tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
      this.setRotation(tailBase, -1.134464F, 0.0F, 0.0F);
      this.horse.addChild(tailBase);
      ModelRenderer tailMiddle = new ModelRenderer(base, 0, 13);
      tailMiddle.setTextureSize(32, 32);
      tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
      this.setRotation(tailMiddle, -1.134464F, 0.0F, 0.0F);
      this.horse.addChild(tailMiddle);
      ModelRenderer tailTip = new ModelRenderer(base, 0, 0);
      tailTip.setTextureSize(32, 32);
      tailTip.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
      this.setRotation(tailTip, -1.40215F, 0.0F, 0.0F);
      this.horse.addChild(tailTip);
      this.horse.rotateAngleX = 0.5F;
      this.addChild(this.dragon = new ModelDragonTail(base));
      this.addChild(this.squirrel = new ModelSquirrelTail(base));
   }

   public void setData(EntityCustomNpc entity) {
      this.entity = entity;
      this.initData(entity);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      super.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.3F * par2;
      super.rotateAngleX = MathHelper.sin(par3 * 0.067F) * 0.05F;
      if(this.entity.modelData.legParts.type == 2) {
         super.rotationPointY = 13.0F;
         super.rotationPointZ = 14.0F * this.entity.modelData.legs.scaleZ;
         if(this.base.isSleeping(entity) || this.entity.currentAnimation == EnumAnimation.CRAWLING) {
            super.rotationPointY = 12.0F + 16.0F * this.entity.modelData.legs.scaleZ;
            super.rotationPointZ = 1.0F * this.entity.modelData.legs.scaleY;
            super.rotateAngleX = -0.7853982F;
         }
      } else if(this.entity.modelData.legParts.type == 3) {
         super.rotationPointY = 8.6F;
         super.rotationPointZ = 19.0F * this.entity.modelData.legs.scaleZ;
      } else {
         super.rotationPointY = 11.0F;
         super.rotationPointZ = -1.0F;
      }

      super.rotationPointZ += this.base.bipedRightLeg.rotationPointZ + 0.5F;
   }

   public void setLivingAnimations(ModelPartData data, EntityLivingBase entity, float par2, float par3, float par4) {}

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.modelData.getPartData("tail");
      if(config == null) {
         super.isHidden = true;
      } else {
         this.color = config.color;
         super.isHidden = false;
         this.tail.isHidden = config.type != 0;
         this.dragon.isHidden = config.type != 1;
         this.horse.isHidden = config.type != 2;
         this.squirrel.isHidden = config.type != 3;
         if(!config.playerTexture) {
            this.location = config.getResource();
         } else {
            this.location = null;
         }

      }
   }

   public void render(float par1) {
      if(!super.isHidden) {
         if(!this.base.isArmor) {
            if(this.location != null) {
               ClientProxy.bindTexture(this.location);
               this.base.currentlyPlayerTexture = false;
            } else if(!this.base.currentlyPlayerTexture) {
               ClientProxy.bindTexture(this.entity.textureLocation);
               this.base.currentlyPlayerTexture = true;
            }
         }

         boolean bo = this.entity.hurtTime <= 0 && this.entity.deathTime <= 0;
         if(bo) {
            float red = (float)(this.color >> 16 & 255) / 255.0F;
            float green = (float)(this.color >> 8 & 255) / 255.0F;
            float blue = (float)(this.color & 255) / 255.0F;
            GL11.glColor4f(red, green, blue, this.base.alpha);
         }

         super.render(par1);
         if(bo) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, this.base.alpha);
         }

      }
   }
}
