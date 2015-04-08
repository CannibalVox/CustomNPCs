package noppes.npcs.client.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.util.ModelPlaneRenderer;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.EntityNpcPony;
import org.lwjgl.opengl.GL11;

public class ModelPony extends ModelBase {

   private boolean rainboom;
   private float WingRotateAngleX;
   private float WingRotateAngleY;
   private float WingRotateAngleZ;
   private float TailRotateAngleY;
   public ModelRenderer Head;
   public ModelRenderer[] Headpiece;
   public ModelRenderer Helmet;
   public ModelRenderer Body;
   public ModelPlaneRenderer[] Bodypiece;
   public ModelRenderer RightArm;
   public ModelRenderer LeftArm;
   public ModelRenderer RightLeg;
   public ModelRenderer LeftLeg;
   public ModelRenderer unicornarm;
   public ModelPlaneRenderer[] Tail;
   public ModelRenderer[] LeftWing;
   public ModelRenderer[] RightWing;
   public ModelRenderer[] LeftWingExt;
   public ModelRenderer[] RightWingExt;
   public boolean isPegasus;
   public boolean isUnicorn;
   public boolean isFlying;
   public boolean isGlow;
   public boolean isSleeping;
   public boolean isSneak;
   public boolean aimedBow;
   public int heldItemRight;


   public ModelPony(float f) {
      this.init(f, 0.0F);
   }

   public void init(float strech, float f) {
      float f2 = 0.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;
      this.Head = new ModelRenderer(this, 0, 0);
      this.Head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 8, strech);
      this.Head.setRotationPoint(f2, f3 + f, f4);
      this.Headpiece = new ModelRenderer[3];
      this.Headpiece[0] = new ModelRenderer(this, 12, 16);
      this.Headpiece[0].addBox(-4.0F, -6.0F, -1.0F, 2, 2, 2, strech);
      this.Headpiece[0].setRotationPoint(f2, f3 + f, f4);
      this.Headpiece[1] = new ModelRenderer(this, 12, 16);
      this.Headpiece[1].addBox(2.0F, -6.0F, -1.0F, 2, 2, 2, strech);
      this.Headpiece[1].setRotationPoint(f2, f3 + f, f4);
      this.Headpiece[2] = new ModelRenderer(this, 56, 0);
      this.Headpiece[2].addBox(-0.5F, -10.0F, -4.0F, 1, 4, 1, strech);
      this.Headpiece[2].setRotationPoint(f2, f3 + f, f4);
      this.Helmet = new ModelRenderer(this, 32, 0);
      this.Helmet.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 8, strech + 0.5F);
      this.Helmet.setRotationPoint(f2, f3, f4);
      float f5 = 0.0F;
      float f6 = 0.0F;
      float f7 = 0.0F;
      this.Body = new ModelRenderer(this, 16, 16);
      this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, strech);
      this.Body.setRotationPoint(f5, f6 + f, f7);
      this.Bodypiece = new ModelPlaneRenderer[13];
      this.Bodypiece[0] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[0].addSidePlane(-4.0F, 4.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[0].setRotationPoint(f5, f6 + f, f7);
      this.Bodypiece[1] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[1].addSidePlane(4.0F, 4.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[1].setRotationPoint(f5, f6 + f, f7);
      this.Bodypiece[2] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[2].addTopPlane(-4.0F, 4.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[2].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[3] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[3].addTopPlane(-4.0F, 12.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[3].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[4] = new ModelPlaneRenderer(this, 0, 20);
      this.Bodypiece[4].addSidePlane(-4.0F, 4.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[4].setRotationPoint(f5, f6 + f, f7);
      this.Bodypiece[5] = new ModelPlaneRenderer(this, 0, 20);
      this.Bodypiece[5].addSidePlane(4.0F, 4.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[5].setRotationPoint(f5, f6 + f, f7);
      this.Bodypiece[6] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[6].addTopPlane(-4.0F, 4.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[6].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[7] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[7].addTopPlane(-4.0F, 12.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[7].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[8] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[8].addBackPlane(-4.0F, 4.0F, 14.0F, 8, 8, strech);
      this.Bodypiece[8].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[9] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[9].addTopPlane(-1.0F, 10.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[9].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[10] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[10].addTopPlane(-1.0F, 12.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[10].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[11] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[11].mirror = true;
      this.Bodypiece[11].addSidePlane(-1.0F, 10.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[11].setRotationPoint(f2, f3 + f, f4);
      this.Bodypiece[12] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[12].addSidePlane(1.0F, 10.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[12].setRotationPoint(f2, f3 + f, f4);
      this.RightArm = new ModelRenderer(this, 40, 16);
      this.RightArm.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.RightArm.setRotationPoint(-3.0F, 8.0F + f, 0.0F);
      this.LeftArm = new ModelRenderer(this, 40, 16);
      this.LeftArm.mirror = true;
      this.LeftArm.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.LeftArm.setRotationPoint(3.0F, 8.0F + f, 0.0F);
      this.RightLeg = new ModelRenderer(this, 40, 16);
      this.RightLeg.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.RightLeg.setRotationPoint(-3.0F, 0.0F + f, 0.0F);
      this.LeftLeg = new ModelRenderer(this, 40, 16);
      this.LeftLeg.mirror = true;
      this.LeftLeg.addBox(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.LeftLeg.setRotationPoint(3.0F, 0.0F + f, 0.0F);
      this.unicornarm = new ModelRenderer(this, 40, 16);
      this.unicornarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, strech);
      this.unicornarm.setRotationPoint(-5.0F, 2.0F + f, 0.0F);
      float f8 = 0.0F;
      float f9 = 8.0F;
      float f10 = -14.0F;
      float f11 = 0.0F - f8;
      float f12 = 10.0F - f9;
      float f13 = 0.0F;
      this.Tail = new ModelPlaneRenderer[10];
      this.Tail[0] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[0].addTopPlane(-2.0F + f8, -7.0F + f9, 16.0F + f10, 4, 4, strech);
      this.Tail[0].setRotationPoint(f11, f12 + f, f13);
      this.Tail[1] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[1].addTopPlane(-2.0F + f8, 9.0F + f9, 16.0F + f10, 4, 4, strech);
      this.Tail[1].setRotationPoint(f11, f12 + f, f13);
      this.Tail[2] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[2].addBackPlane(-2.0F + f8, -7.0F + f9, 16.0F + f10, 4, 8, strech);
      this.Tail[2].setRotationPoint(f11, f12 + f, f13);
      this.Tail[3] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[3].addBackPlane(-2.0F + f8, -7.0F + f9, 20.0F + f10, 4, 8, strech);
      this.Tail[3].setRotationPoint(f11, f12 + f, f13);
      this.Tail[4] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[4].addBackPlane(-2.0F + f8, 1.0F + f9, 16.0F + f10, 4, 8, strech);
      this.Tail[4].setRotationPoint(f11, f12 + f, f13);
      this.Tail[5] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[5].addBackPlane(-2.0F + f8, 1.0F + f9, 20.0F + f10, 4, 8, strech);
      this.Tail[5].setRotationPoint(f11, f12 + f, f13);
      this.Tail[6] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[6].mirror = true;
      this.Tail[6].addSidePlane(2.0F + f8, -7.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[6].setRotationPoint(f11, f12 + f, f13);
      this.Tail[7] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[7].addSidePlane(-2.0F + f8, -7.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[7].setRotationPoint(f11, f12 + f, f13);
      this.Tail[8] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[8].mirror = true;
      this.Tail[8].addSidePlane(2.0F + f8, 1.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[8].setRotationPoint(f11, f12 + f, f13);
      this.Tail[9] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[9].addSidePlane(-2.0F + f8, 1.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[9].setRotationPoint(f11, f12 + f, f13);
      this.TailRotateAngleY = this.Tail[0].rotateAngleY;
      this.TailRotateAngleY = this.Tail[0].rotateAngleY;
      float f14 = 0.0F;
      float f15 = 0.0F;
      float f16 = 0.0F;
      this.LeftWing = new ModelRenderer[3];
      this.LeftWing[0] = new ModelRenderer(this, 56, 16);
      this.LeftWing[0].mirror = true;
      this.LeftWing[0].addBox(4.0F, 5.0F, 2.0F, 2, 6, 2, strech);
      this.LeftWing[0].setRotationPoint(f14, f15 + f, f16);
      this.LeftWing[1] = new ModelRenderer(this, 56, 16);
      this.LeftWing[1].mirror = true;
      this.LeftWing[1].addBox(4.0F, 5.0F, 4.0F, 2, 8, 2, strech);
      this.LeftWing[1].setRotationPoint(f14, f15 + f, f16);
      this.LeftWing[2] = new ModelRenderer(this, 56, 16);
      this.LeftWing[2].mirror = true;
      this.LeftWing[2].addBox(4.0F, 5.0F, 6.0F, 2, 6, 2, strech);
      this.LeftWing[2].setRotationPoint(f14, f15 + f, f16);
      this.RightWing = new ModelRenderer[3];
      this.RightWing[0] = new ModelRenderer(this, 56, 16);
      this.RightWing[0].addBox(-6.0F, 5.0F, 2.0F, 2, 6, 2, strech);
      this.RightWing[0].setRotationPoint(f14, f15 + f, f16);
      this.RightWing[1] = new ModelRenderer(this, 56, 16);
      this.RightWing[1].addBox(-6.0F, 5.0F, 4.0F, 2, 8, 2, strech);
      this.RightWing[1].setRotationPoint(f14, f15 + f, f16);
      this.RightWing[2] = new ModelRenderer(this, 56, 16);
      this.RightWing[2].addBox(-6.0F, 5.0F, 6.0F, 2, 6, 2, strech);
      this.RightWing[2].setRotationPoint(f14, f15 + f, f16);
      float f17 = f2 + 4.5F;
      float f18 = f3 + 5.0F;
      float f19 = f4 + 6.0F;
      this.LeftWingExt = new ModelRenderer[7];
      this.LeftWingExt[0] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[0].mirror = true;
      this.LeftWingExt[0].addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, strech + 0.1F);
      this.LeftWingExt[0].setRotationPoint(f17, f18 + f, f19);
      this.LeftWingExt[1] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[1].mirror = true;
      this.LeftWingExt[1].addBox(0.0F, 8.0F, 0.0F, 1, 6, 2, strech + 0.1F);
      this.LeftWingExt[1].setRotationPoint(f17, f18 + f, f19);
      this.LeftWingExt[2] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[2].mirror = true;
      this.LeftWingExt[2].addBox(0.0F, -1.2F, -0.2F, 1, 8, 2, strech - 0.2F);
      this.LeftWingExt[2].setRotationPoint(f17, f18 + f, f19);
      this.LeftWingExt[3] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[3].mirror = true;
      this.LeftWingExt[3].addBox(0.0F, 1.8F, 1.3F, 1, 8, 2, strech - 0.1F);
      this.LeftWingExt[3].setRotationPoint(f17, f18 + f, f19);
      this.LeftWingExt[4] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[4].mirror = true;
      this.LeftWingExt[4].addBox(0.0F, 5.0F, 2.0F, 1, 8, 2, strech);
      this.LeftWingExt[4].setRotationPoint(f17, f18 + f, f19);
      this.LeftWingExt[5] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[5].mirror = true;
      this.LeftWingExt[5].addBox(0.0F, 0.0F, -0.2F, 1, 6, 2, strech + 0.3F);
      this.LeftWingExt[5].setRotationPoint(f17, f18 + f, f19);
      this.LeftWingExt[6] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[6].mirror = true;
      this.LeftWingExt[6].addBox(0.0F, 0.0F, 0.2F, 1, 3, 2, strech + 0.2F);
      this.LeftWingExt[6].setRotationPoint(f17, f18 + f, f19);
      float f20 = f2 - 4.5F;
      float f21 = f3 + 5.0F;
      float f22 = f4 + 6.0F;
      this.RightWingExt = new ModelRenderer[7];
      this.RightWingExt[0] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[0].mirror = true;
      this.RightWingExt[0].addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, strech + 0.1F);
      this.RightWingExt[0].setRotationPoint(f20, f21 + f, f22);
      this.RightWingExt[1] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[1].mirror = true;
      this.RightWingExt[1].addBox(0.0F, 8.0F, 0.0F, 1, 6, 2, strech + 0.1F);
      this.RightWingExt[1].setRotationPoint(f20, f21 + f, f22);
      this.RightWingExt[2] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[2].mirror = true;
      this.RightWingExt[2].addBox(0.0F, -1.2F, -0.2F, 1, 8, 2, strech - 0.2F);
      this.RightWingExt[2].setRotationPoint(f20, f21 + f, f22);
      this.RightWingExt[3] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[3].mirror = true;
      this.RightWingExt[3].addBox(0.0F, 1.8F, 1.3F, 1, 8, 2, strech - 0.1F);
      this.RightWingExt[3].setRotationPoint(f20, f21 + f, f22);
      this.RightWingExt[4] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[4].mirror = true;
      this.RightWingExt[4].addBox(0.0F, 5.0F, 2.0F, 1, 8, 2, strech);
      this.RightWingExt[4].setRotationPoint(f20, f21 + f, f22);
      this.RightWingExt[5] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[5].mirror = true;
      this.RightWingExt[5].addBox(0.0F, 0.0F, -0.2F, 1, 6, 2, strech + 0.3F);
      this.RightWingExt[5].setRotationPoint(f20, f21 + f, f22);
      this.RightWingExt[6] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[6].mirror = true;
      this.RightWingExt[6].addBox(0.0F, 0.0F, 0.2F, 1, 3, 2, strech + 0.2F);
      this.RightWingExt[6].setRotationPoint(f20, f21 + f, f22);
      this.WingRotateAngleX = this.LeftWingExt[0].rotateAngleX;
      this.WingRotateAngleY = this.LeftWingExt[0].rotateAngleY;
      this.WingRotateAngleZ = this.LeftWingExt[0].rotateAngleZ;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      super.isRiding = npc.isRiding();
      if(this.isSneak && (npc.currentAnimation == EnumAnimation.CRAWLING || npc.currentAnimation == EnumAnimation.LYING)) {
         this.isSneak = false;
      }

      this.rainboom = false;
      float f6;
      float f7;
      if(this.isSleeping) {
         f6 = 1.4F;
         f7 = 0.1F;
      } else {
         f6 = f3 / 57.29578F;
         f7 = f4 / 57.29578F;
      }

      this.Head.rotateAngleY = f6;
      this.Head.rotateAngleX = f7;
      this.Headpiece[0].rotateAngleY = f6;
      this.Headpiece[0].rotateAngleX = f7;
      this.Headpiece[1].rotateAngleY = f6;
      this.Headpiece[1].rotateAngleX = f7;
      this.Headpiece[2].rotateAngleY = f6;
      this.Headpiece[2].rotateAngleX = f7;
      this.Helmet.rotateAngleY = f6;
      this.Helmet.rotateAngleX = f7;
      this.Headpiece[2].rotateAngleX = f7 + 0.5F;
      float f8;
      float f9;
      float f10;
      float f11;
      if(this.isFlying && this.isPegasus) {
         if(f1 < 0.9999F) {
            this.rainboom = false;
            f8 = MathHelper.sin(0.0F - f1 * 0.5F);
            f9 = MathHelper.sin(0.0F - f1 * 0.5F);
            f10 = MathHelper.sin(f1 * 0.5F);
            f11 = MathHelper.sin(f1 * 0.5F);
         } else {
            this.rainboom = true;
            f8 = 4.712F;
            f9 = 4.712F;
            f10 = 1.571F;
            f11 = 1.571F;
         }

         this.RightArm.rotateAngleY = 0.2F;
         this.LeftArm.rotateAngleY = -0.2F;
         this.RightLeg.rotateAngleY = -0.2F;
         this.LeftLeg.rotateAngleY = 0.2F;
      } else {
         f8 = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.6F * f1;
         f9 = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
         f10 = MathHelper.cos(f * 0.6662F) * 0.3F * f1;
         f11 = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.3F * f1;
         this.RightArm.rotateAngleY = 0.0F;
         this.unicornarm.rotateAngleY = 0.0F;
         this.LeftArm.rotateAngleY = 0.0F;
         this.RightLeg.rotateAngleY = 0.0F;
         this.LeftLeg.rotateAngleY = 0.0F;
      }

      if(this.isSleeping) {
         f8 = 4.712F;
         f9 = 4.712F;
         f10 = 1.571F;
         f11 = 1.571F;
      }

      this.RightArm.rotateAngleX = f8;
      this.unicornarm.rotateAngleX = 0.0F;
      this.LeftArm.rotateAngleX = f9;
      this.RightLeg.rotateAngleX = f10;
      this.LeftLeg.rotateAngleX = f11;
      this.RightArm.rotateAngleZ = 0.0F;
      this.unicornarm.rotateAngleZ = 0.0F;
      this.LeftArm.rotateAngleZ = 0.0F;

      for(int f12 = 0; f12 < this.Tail.length; ++f12) {
         if(this.rainboom) {
            this.Tail[f12].rotateAngleZ = 0.0F;
         } else {
            this.Tail[f12].rotateAngleZ = MathHelper.cos(f * 0.8F) * 0.2F * f1;
         }
      }

      if(this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
         this.RightArm.rotateAngleX = this.RightArm.rotateAngleX * 0.5F - 0.3141593F;
      }

      float var37 = 0.0F;
      if(f5 > -9990.0F && !this.isUnicorn) {
         var37 = MathHelper.sin(MathHelper.sqrt_float(f5) * 3.141593F * 2.0F) * 0.2F;
      }

      this.Body.rotateAngleY = (float)((double)var37 * 0.2D);

      int f13;
      for(f13 = 0; f13 < this.Bodypiece.length; ++f13) {
         this.Bodypiece[f13].rotateAngleY = (float)((double)var37 * 0.2D);
      }

      for(f13 = 0; f13 < this.LeftWing.length; ++f13) {
         this.LeftWing[f13].rotateAngleY = (float)((double)var37 * 0.2D);
      }

      for(f13 = 0; f13 < this.RightWing.length; ++f13) {
         this.RightWing[f13].rotateAngleY = (float)((double)var37 * 0.2D);
      }

      for(f13 = 0; f13 < this.Tail.length; ++f13) {
         this.Tail[f13].rotateAngleY = var37;
      }

      float var38 = MathHelper.sin(this.Body.rotateAngleY) * 5.0F;
      float f14 = MathHelper.cos(this.Body.rotateAngleY) * 5.0F;
      float f15 = 4.0F;
      if(this.isSneak && !this.isFlying) {
         f15 = 0.0F;
      }

      if(this.isSleeping) {
         f15 = 2.6F;
      }

      if(this.rainboom) {
         this.RightArm.rotationPointZ = var38 + 2.0F;
         this.LeftArm.rotationPointZ = 0.0F - var38 + 2.0F;
      } else {
         this.RightArm.rotationPointZ = var38 + 1.0F;
         this.LeftArm.rotationPointZ = 0.0F - var38 + 1.0F;
      }

      this.RightArm.rotationPointX = 0.0F - f14 - 1.0F + f15;
      this.LeftArm.rotationPointX = f14 + 1.0F - f15;
      this.RightLeg.rotationPointX = 0.0F - f14 - 1.0F + f15;
      this.LeftLeg.rotationPointX = f14 + 1.0F - f15;
      this.RightArm.rotateAngleY += this.Body.rotateAngleY;
      this.LeftArm.rotateAngleY += this.Body.rotateAngleY;
      this.LeftArm.rotateAngleX += this.Body.rotateAngleY;
      this.RightArm.rotationPointY = 8.0F;
      this.LeftArm.rotationPointY = 8.0F;
      this.RightLeg.rotationPointY = 4.0F;
      this.LeftLeg.rotationPointY = 4.0F;
      float f21;
      float f27;
      float f32;
      float f36;
      if(f5 > -9990.0F) {
         f21 = 1.0F - f5;
         f21 *= f21 * f21;
         f21 = 1.0F - f21;
         f27 = MathHelper.sin(f21 * 3.141593F);
         f32 = MathHelper.sin(f5 * 3.141593F);
         f36 = f32 * -(this.Head.rotateAngleX - 0.7F) * 0.75F;
         if(this.isUnicorn) {
            this.unicornarm.rotateAngleX = (float)((double)this.unicornarm.rotateAngleX - ((double)f27 * 1.2D + (double)f36));
            this.unicornarm.rotateAngleY += this.Body.rotateAngleY * 2.0F;
            this.unicornarm.rotateAngleZ = f32 * -0.4F;
         } else {
            this.unicornarm.rotateAngleX = (float)((double)this.unicornarm.rotateAngleX - ((double)f27 * 1.2D + (double)f36));
            this.unicornarm.rotateAngleY += this.Body.rotateAngleY * 2.0F;
            this.unicornarm.rotateAngleZ = f32 * -0.4F;
         }
      }

      float f39;
      int f42;
      float f45;
      int f47;
      float f49;
      float f51;
      float f53;
      float f55;
      float f57;
      float f59;
      float f61;
      int var40;
      float var41;
      float var42;
      if(this.isSneak && !this.isFlying) {
         f21 = 0.4F;
         f27 = 7.0F;
         f32 = -4.0F;
         this.Body.rotateAngleX = f21;
         this.Body.rotationPointY = f27;
         this.Body.rotationPointZ = f32;

         for(var40 = 0; var40 < this.Bodypiece.length; ++var40) {
            this.Bodypiece[var40].rotateAngleX = f21;
            this.Bodypiece[var40].rotationPointY = f27;
            this.Bodypiece[var40].rotationPointZ = f32;
         }

         f36 = 3.5F;
         f39 = 6.0F;

         for(f42 = 0; f42 < this.LeftWingExt.length; ++f42) {
            this.LeftWingExt[f42].rotateAngleX = (float)((double)f21 + 2.3561947345733643D);
            this.LeftWingExt[f42].rotationPointY = f27 + f36;
            this.LeftWingExt[f42].rotationPointZ = f32 + f39;
            this.LeftWingExt[f42].rotateAngleX = 2.5F;
            this.LeftWingExt[f42].rotateAngleZ = -6.0F;
         }

         var41 = 4.5F;
         f45 = 6.0F;

         for(f47 = 0; f47 < this.LeftWingExt.length; ++f47) {
            this.RightWingExt[f47].rotateAngleX = (float)((double)f21 + 2.3561947345733643D);
            this.RightWingExt[f47].rotationPointY = f27 + var41;
            this.RightWingExt[f47].rotationPointZ = f32 + f45;
            this.RightWingExt[f47].rotateAngleX = 2.5F;
            this.RightWingExt[f47].rotateAngleZ = 6.0F;
         }

         this.RightLeg.rotateAngleX -= 0.0F;
         this.LeftLeg.rotateAngleX -= 0.0F;
         this.RightArm.rotateAngleX -= 0.4F;
         this.unicornarm.rotateAngleX += 0.4F;
         this.LeftArm.rotateAngleX -= 0.4F;
         this.RightLeg.rotationPointZ = 10.0F;
         this.LeftLeg.rotationPointZ = 10.0F;
         this.RightLeg.rotationPointY = 7.0F;
         this.LeftLeg.rotationPointY = 7.0F;
         if(this.isSleeping) {
            var42 = 2.0F;
            f49 = -1.0F;
            f51 = 1.0F;
         } else {
            var42 = 6.0F;
            f49 = -2.0F;
            f51 = 0.0F;
         }

         this.Head.rotationPointY = var42;
         this.Head.rotationPointZ = f49;
         this.Head.rotationPointX = f51;
         this.Helmet.rotationPointY = var42;
         this.Helmet.rotationPointZ = f49;
         this.Helmet.rotationPointX = f51;
         this.Headpiece[0].rotationPointY = var42;
         this.Headpiece[0].rotationPointZ = f49;
         this.Headpiece[0].rotationPointX = f51;
         this.Headpiece[1].rotationPointY = var42;
         this.Headpiece[1].rotationPointZ = f49;
         this.Headpiece[1].rotationPointX = f51;
         this.Headpiece[2].rotationPointY = var42;
         this.Headpiece[2].rotationPointZ = f49;
         this.Headpiece[2].rotationPointX = f51;
         f53 = 0.0F;
         f55 = 8.0F;
         f57 = -14.0F;
         f59 = 0.0F - f53;
         f61 = 9.0F - f55;
         float var43 = -4.0F - f57;
         float f63 = 0.0F;

         for(int i6 = 0; i6 < this.Tail.length; ++i6) {
            this.Tail[i6].rotationPointX = f59;
            this.Tail[i6].rotationPointY = f61;
            this.Tail[i6].rotationPointZ = var43;
            this.Tail[i6].rotateAngleX = f63;
         }
      } else {
         f21 = 0.0F;
         f27 = 0.0F;
         f32 = 0.0F;
         this.Body.rotateAngleX = f21;
         this.Body.rotationPointY = f27;
         this.Body.rotationPointZ = f32;

         for(var40 = 0; var40 < this.Bodypiece.length; ++var40) {
            this.Bodypiece[var40].rotateAngleX = f21;
            this.Bodypiece[var40].rotationPointY = f27;
            this.Bodypiece[var40].rotationPointZ = f32;
         }

         if(this.isPegasus) {
            if(!this.isFlying) {
               for(var40 = 0; var40 < this.LeftWing.length; ++var40) {
                  this.LeftWing[var40].rotateAngleX = (float)((double)f21 + 1.5707964897155762D);
                  this.LeftWing[var40].rotationPointY = f27 + 13.0F;
                  this.LeftWing[var40].rotationPointZ = f32 - 3.0F;
               }

               for(var40 = 0; var40 < this.RightWing.length; ++var40) {
                  this.RightWing[var40].rotateAngleX = (float)((double)f21 + 1.5707964897155762D);
                  this.RightWing[var40].rotationPointY = f27 + 13.0F;
                  this.RightWing[var40].rotationPointZ = f32 - 3.0F;
               }
            } else {
               f36 = 5.5F;
               f39 = 3.0F;

               for(f42 = 0; f42 < this.LeftWingExt.length; ++f42) {
                  this.LeftWingExt[f42].rotateAngleX = (float)((double)f21 + 1.5707964897155762D);
                  this.LeftWingExt[f42].rotationPointY = f27 + f36;
                  this.LeftWingExt[f42].rotationPointZ = f32 + f39;
               }

               var41 = 6.5F;
               f45 = 3.0F;

               for(f47 = 0; f47 < this.RightWingExt.length; ++f47) {
                  this.RightWingExt[f47].rotateAngleX = (float)((double)f21 + 1.5707964897155762D);
                  this.RightWingExt[f47].rotationPointY = f27 + var41;
                  this.RightWingExt[f47].rotationPointZ = f32 + f45;
               }
            }
         }

         this.RightLeg.rotationPointZ = 10.0F;
         this.LeftLeg.rotationPointZ = 10.0F;
         this.RightLeg.rotationPointY = 8.0F;
         this.LeftLeg.rotationPointY = 8.0F;
         f36 = MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
         f39 = MathHelper.sin(f2 * 0.067F) * 0.05F;
         this.unicornarm.rotateAngleZ += f36;
         this.unicornarm.rotateAngleX += f39;
         if(this.isPegasus && this.isFlying) {
            this.WingRotateAngleY = MathHelper.sin(f2 * 0.067F * 8.0F) * 1.0F;
            this.WingRotateAngleZ = MathHelper.sin(f2 * 0.067F * 8.0F) * 1.0F;

            for(f42 = 0; f42 < this.LeftWingExt.length; ++f42) {
               this.LeftWingExt[f42].rotateAngleX = 2.5F;
               this.LeftWingExt[f42].rotateAngleZ = -this.WingRotateAngleZ - 4.712F - 0.4F;
            }

            for(f42 = 0; f42 < this.RightWingExt.length; ++f42) {
               this.RightWingExt[f42].rotateAngleX = 2.5F;
               this.RightWingExt[f42].rotateAngleZ = this.WingRotateAngleZ + 4.712F + 0.4F;
            }
         }

         if(this.isSleeping) {
            var41 = 2.0F;
            f45 = 1.0F;
            var42 = 1.0F;
         } else {
            var41 = 0.0F;
            f45 = 0.0F;
            var42 = 0.0F;
         }

         this.Head.rotationPointY = var41;
         this.Head.rotationPointZ = f45;
         this.Head.rotationPointX = var42;
         this.Helmet.rotationPointY = var41;
         this.Helmet.rotationPointZ = f45;
         this.Helmet.rotationPointX = var42;
         this.Headpiece[0].rotationPointY = var41;
         this.Headpiece[0].rotationPointZ = f45;
         this.Headpiece[0].rotationPointX = var42;
         this.Headpiece[1].rotationPointY = var41;
         this.Headpiece[1].rotationPointZ = f45;
         this.Headpiece[1].rotationPointX = var42;
         this.Headpiece[2].rotationPointY = var41;
         this.Headpiece[2].rotationPointZ = f45;
         this.Headpiece[2].rotationPointX = var42;
         f49 = 0.0F;
         f51 = 8.0F;
         f53 = -14.0F;
         f55 = 0.0F - f49;
         f57 = 9.0F - f51;
         f59 = 0.0F - f53;
         f61 = 0.5F * f1;

         int l5;
         for(l5 = 0; l5 < this.Tail.length; ++l5) {
            this.Tail[l5].rotationPointX = f55;
            this.Tail[l5].rotationPointY = f57;
            this.Tail[l5].rotationPointZ = f59;
            if(this.rainboom) {
               this.Tail[l5].rotateAngleX = 1.571F + 0.1F * MathHelper.sin(f);
            } else {
               this.Tail[l5].rotateAngleX = f61;
            }
         }

         for(l5 = 0; l5 < this.Tail.length; ++l5) {
            if(!this.rainboom) {
               this.Tail[l5].rotateAngleX += f39;
            }
         }
      }

      this.LeftWingExt[2].rotateAngleX -= 0.85F;
      this.LeftWingExt[3].rotateAngleX -= 0.75F;
      this.LeftWingExt[4].rotateAngleX -= 0.5F;
      this.LeftWingExt[6].rotateAngleX -= 0.85F;
      this.RightWingExt[2].rotateAngleX -= 0.85F;
      this.RightWingExt[3].rotateAngleX -= 0.75F;
      this.RightWingExt[4].rotateAngleX -= 0.5F;
      this.RightWingExt[6].rotateAngleX -= 0.85F;
      this.Bodypiece[9].rotateAngleX += 0.5F;
      this.Bodypiece[10].rotateAngleX += 0.5F;
      this.Bodypiece[11].rotateAngleX += 0.5F;
      this.Bodypiece[12].rotateAngleX += 0.5F;
      if(this.rainboom) {
         for(int var39 = 0; var39 < this.Tail.length; ++var39) {
            this.Tail[var39].rotationPointY += 6.0F;
            ++this.Tail[var39].rotationPointZ;
         }
      }

      if(this.isSleeping) {
         this.RightArm.rotationPointZ += 6.0F;
         this.LeftArm.rotationPointZ += 6.0F;
         this.RightLeg.rotationPointZ -= 8.0F;
         this.LeftLeg.rotationPointZ -= 8.0F;
         this.RightArm.rotationPointY += 2.0F;
         this.LeftArm.rotationPointY += 2.0F;
         this.RightLeg.rotationPointY += 2.0F;
         this.LeftLeg.rotationPointY += 2.0F;
      }

      if(this.aimedBow) {
         if(this.isUnicorn) {
            f21 = 0.0F;
            f27 = 0.0F;
            this.unicornarm.rotateAngleZ = 0.0F;
            this.unicornarm.rotateAngleY = -(0.1F - f21 * 0.6F) + this.Head.rotateAngleY;
            this.unicornarm.rotateAngleX = 4.712F + this.Head.rotateAngleX;
            this.unicornarm.rotateAngleX -= f21 * 1.2F - f27 * 0.4F;
            this.unicornarm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
            this.unicornarm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
         } else {
            f21 = 0.0F;
            f27 = 0.0F;
            this.RightArm.rotateAngleZ = 0.0F;
            this.RightArm.rotateAngleY = -(0.1F - f21 * 0.6F) + this.Head.rotateAngleY;
            this.RightArm.rotateAngleX = 4.712F + this.Head.rotateAngleX;
            this.RightArm.rotateAngleX -= f21 * 1.2F - f27 * 0.4F;
            this.RightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
            this.RightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
            ++this.RightArm.rotationPointZ;
         }
      }

   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      EntityNpcPony pony = (EntityNpcPony)entity;
      if(pony.textureLocation != pony.checked && pony.textureLocation != null) {
         try {
            IResource scale = Minecraft.getMinecraft().getResourceManager().getResource(pony.textureLocation);
            BufferedImage j1 = ImageIO.read(scale.getInputStream());
            pony.isPegasus = false;
            pony.isUnicorn = false;
            Color color = new Color(j1.getRGB(0, 0), true);
            Color color1 = new Color(249, 177, 49, 255);
            Color color2 = new Color(136, 202, 240, 255);
            Color color3 = new Color(209, 159, 228, 255);
            Color color4 = new Color(254, 249, 252, 255);
            if(color.equals(color1)) {
               ;
            }

            if(color.equals(color2)) {
               pony.isPegasus = true;
            }

            if(color.equals(color3)) {
               pony.isUnicorn = true;
            }

            if(color.equals(color4)) {
               pony.isPegasus = true;
               pony.isUnicorn = true;
            }

            pony.checked = pony.textureLocation;
         } catch (IOException var16) {
            ;
         }
      }

      this.isSleeping = pony.isPlayerSleeping();
      this.isUnicorn = pony.isUnicorn;
      this.isPegasus = pony.isPegasus;
      this.isSneak = pony.isSneaking();
      this.heldItemRight = pony.getHeldItem() == null?0:1;
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glPushMatrix();
      if(this.isSleeping) {
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(0.0F, -0.5F, -0.9F);
      }

      float var17 = f5;
      this.Head.render(f5);
      this.Headpiece[0].render(f5);
      this.Headpiece[1].render(f5);
      if(this.isUnicorn) {
         this.Headpiece[2].render(f5);
      }

      this.Helmet.render(f5);
      this.Body.render(f5);

      int var18;
      for(var18 = 0; var18 < this.Bodypiece.length; ++var18) {
         this.Bodypiece[var18].render(var17);
      }

      this.LeftArm.render(var17);
      this.RightArm.render(var17);
      this.LeftLeg.render(var17);
      this.RightLeg.render(var17);

      for(var18 = 0; var18 < this.Tail.length; ++var18) {
         this.Tail[var18].render(var17);
      }

      if(this.isPegasus) {
         if(!this.isFlying && !this.isSneak) {
            for(var18 = 0; var18 < this.LeftWing.length; ++var18) {
               this.LeftWing[var18].render(var17);
            }

            for(var18 = 0; var18 < this.RightWing.length; ++var18) {
               this.RightWing[var18].render(var17);
            }
         } else {
            for(var18 = 0; var18 < this.LeftWingExt.length; ++var18) {
               this.LeftWingExt[var18].render(var17);
            }

            for(var18 = 0; var18 < this.RightWingExt.length; ++var18) {
               this.RightWingExt[var18].render(var17);
            }
         }
      }

      GL11.glPopMatrix();
   }

   protected void renderGlow(RenderManager rendermanager, EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.inventory.getCurrentItem();
      if(itemstack != null) {
         GL11.glPushMatrix();
         double d = entityplayer.posX;
         double d1 = entityplayer.posY;
         double d2 = entityplayer.posZ;
         GL11.glEnable('\u803a');
         GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
         GL11.glScalef(5.0F, 5.0F, 5.0F);
         GL11.glRotatef(-rendermanager.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(rendermanager.playerViewX, 1.0F, 0.0F, 0.0F);
         Tessellator tessellator = Tessellator.instance;
         float f = 0.0F;
         float f1 = 0.25F;
         float f2 = 0.0F;
         float f3 = 0.25F;
         float f4 = 1.0F;
         float f5 = 0.5F;
         float f6 = 0.25F;
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, 1.0F, 0.0F);
         tessellator.addVertexWithUV(-1.0D, -1.0D, 0.0D, 0.0D, 1.0D);
         tessellator.addVertexWithUV(-1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
         tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, 1.0D, 0.0D);
         tessellator.addVertexWithUV(1.0D, -1.0D, 0.0D, 0.0D, 0.0D);
         tessellator.draw();
         GL11.glDisable('\u803a');
         GL11.glPopMatrix();
      }
   }
}
