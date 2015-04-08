package noppes.npcs.client.renderer;

import java.lang.reflect.Method;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.NPCRendererHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.ModelRenderPassHelper;
import noppes.npcs.client.renderer.RenderNPCHumanMale;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import org.lwjgl.opengl.GL11;

public class RenderCustomNpc extends RenderNPCHumanMale {

   private RendererLivingEntity renderEntity;
   private EntityLivingBase entity;
   private ModelRenderPassHelper renderpass = new ModelRenderPassHelper();


   public RenderCustomNpc() {
      super(new ModelMPM(0.0F), new ModelMPM(1.0F), new ModelMPM(0.5F));
   }

   public void renderPlayer(EntityNPCInterface npcInterface, double d, double d1, double d2, float f, float f1) {
      EntityCustomNpc npc = (EntityCustomNpc)npcInterface;
      this.entity = npc.modelData.getEntity(npc);
      ModelBase model = null;
      this.renderEntity = null;
      if(this.entity != null) {
         this.renderEntity = (RendererLivingEntity)RenderManager.instance.getEntityRenderObject(this.entity);
         model = NPCRendererHelper.getMainModel(this.renderEntity);
         if(PixelmonHelper.isPixelmon(this.entity)) {
            try {
               Class e = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.Entity2HasModel");
               Method m = e.getMethod("getModel", new Class[0]);
               model = (ModelBase)m.invoke(this.entity, new Object[0]);
            } catch (Exception var14) {
               var14.printStackTrace();
            }
         }

         super.renderPassModel = this.renderpass;
         this.renderpass.renderer = this.renderEntity;
         this.renderpass.entity = this.entity;
      }

      ((ModelMPM)super.modelArmor).entityModel = model;
      ((ModelMPM)super.modelArmor).entity = this.entity;
      ((ModelMPM)super.modelArmorChestplate).entityModel = model;
      ((ModelMPM)super.modelArmorChestplate).entity = this.entity;
      ((ModelMPM)super.mainModel).entityModel = model;
      ((ModelMPM)super.mainModel).entity = this.entity;
      super.renderPlayer(npc, d, d1, d2, f, f1);
   }

   protected void func_77029_c(EntityLivingBase entityliving, float f) {
      if(this.renderEntity != null) {
         NPCRendererHelper.renderEquippedItems(this.entity, f, this.renderEntity);
      } else {
         super.func_77029_c(entityliving, f);
      }

   }

   protected int func_77032_a(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.renderEntity != null?NPCRendererHelper.shouldRenderPass(this.entity, par2, par3, this.renderEntity):this.func_130006_a((EntityLiving)par1EntityLivingBase, par2, par3);
   }

   protected void preRenderCallback(EntityLivingBase entityliving, float f) {
      if(this.renderEntity != null) {
         EntityNPCInterface npc = (EntityNPCInterface)entityliving;
         int size = npc.display.modelSize;
         if(this.entity instanceof EntityNPCInterface) {
            ((EntityNPCInterface)this.entity).display.modelSize = 5;
         }

         NPCRendererHelper.preRenderCallback(this.entity, f, this.renderEntity);
         npc.display.modelSize = size;
         GL11.glScalef(0.2F * (float)npc.display.modelSize, 0.2F * (float)npc.display.modelSize, 0.2F * (float)npc.display.modelSize);
      } else {
         super.preRenderCallback(entityliving, f);
      }

   }

   protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
      return this.renderEntity != null?NPCRendererHelper.handleRotationFloat(this.entity, par2, this.renderEntity):super.handleRotationFloat(par1EntityLivingBase, par2);
   }
}
