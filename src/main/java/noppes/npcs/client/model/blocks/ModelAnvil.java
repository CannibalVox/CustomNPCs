package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAnvil extends ModelBase {

   ModelRenderer Tail;
   ModelRenderer Nose1;
   ModelRenderer Nose2;
   ModelRenderer Nose3;
   ModelRenderer Nose4;
   ModelRenderer Head1;
   ModelRenderer Head2;
   ModelRenderer Neck2;
   ModelRenderer Bottom2;
   ModelRenderer Bottom3;
   ModelRenderer Foot4;


   public ModelAnvil() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Tail = new ModelRenderer(this, 0, 0);
      this.Tail.addBox(0.0F, 0.0F, 0.0F, 1, 2, 4);
      this.Tail.setRotationPoint(-7.0F, 12.0F, -2.0F);
      this.Nose1 = new ModelRenderer(this, 0, 0);
      this.Nose1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 6);
      this.Nose1.setRotationPoint(6.0F, 10.0F, -3.0F);
      this.Nose2 = new ModelRenderer(this, 0, 0);
      this.Nose2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5);
      this.Nose2.setRotationPoint(7.0F, 10.0F, -2.5F);
      this.Nose3 = new ModelRenderer(this, 0, 0);
      this.Nose3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4);
      this.Nose3.setRotationPoint(8.0F, 10.0F, -2.0F);
      this.Nose4 = new ModelRenderer(this, 0, 0);
      this.Nose4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2);
      this.Nose4.setRotationPoint(9.0F, 10.0F, -1.0F);
      this.Head1 = new ModelRenderer(this, 0, 0);
      this.Head1.addBox(0.0F, 0.0F, 0.0F, 12, 4, 7);
      this.Head1.setRotationPoint(-6.0F, 12.0F, -3.5F);
      this.Head2 = new ModelRenderer(this, 0, 0);
      this.Head2.addBox(0.0F, 0.0F, 0.0F, 14, 2, 9);
      this.Head2.setRotationPoint(-8.0F, 10.0F, -4.5F);
      this.Neck2 = new ModelRenderer(this, 0, 0);
      this.Neck2.addBox(0.0F, 0.0F, 0.0F, 10, 1, 6);
      this.Neck2.setRotationPoint(-5.0F, 16.0F, -3.0F);
      this.Bottom2 = new ModelRenderer(this, 0, 0);
      this.Bottom2.addBox(0.0F, 0.0F, 0.0F, 10, 2, 7);
      this.Bottom2.setRotationPoint(-5.0F, 20.0F, -3.5F);
      this.Bottom3 = new ModelRenderer(this, 0, 0);
      this.Bottom3.addBox(0.0F, 0.0F, 0.0F, 8, 3, 4);
      this.Bottom3.setRotationPoint(-4.0F, 17.0F, -2.0F);
      this.Foot4 = new ModelRenderer(this, 0, 0);
      this.Foot4.addBox(0.0F, 0.0F, 0.0F, 14, 2, 10);
      this.Foot4.setRotationPoint(-7.0F, 22.0F, -5.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Tail.render(f5);
      this.Nose1.render(f5);
      this.Nose2.render(f5);
      this.Nose3.render(f5);
      this.Nose4.render(f5);
      this.Head1.render(f5);
      this.Head2.render(f5);
      this.Neck2.render(f5);
      this.Bottom2.render(f5);
      this.Bottom3.render(f5);
      this.Foot4.render(f5);
   }
}
