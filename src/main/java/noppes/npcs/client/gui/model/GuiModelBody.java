package noppes.npcs.client.gui.model;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.gui.model.GuiModelColor;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.entity.EntityCustomNpc;

public class GuiModelBody extends GuiModelInterface {

   private GuiScreen parent;
   private final String[] arrWing = new String[]{"gui.no", "Player", "Type1", "Type2", "Type3"};
   private final String[] arrBreasts = new String[]{"gui.no", "Type1", "Type2", "Type3"};
   private final String[] arrParticles = new String[]{"gui.no", "Player", "Type1", "Type2", "Rainbow"};
   private final String[] arrfins = new String[]{"gui.no", "Player", "Type1"};


   public GuiModelBody(GuiScreen parent, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      super.xOffset = 60;
   }

   public void initGui() {
      super.initGui();
      int y = super.guiTop + 20;
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(1, var10004, y, 70, 20, this.arrBreasts, super.playerdata.breasts);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(1, "Breasts", super.guiLeft, y + 5, 16777215));
      ModelPartData wing = super.playerdata.getPartData("wings");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(0, var10004, y, 70, 20, this.arrWing, wing == null?0:wing.type + 1);
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(0, "Wings", super.guiLeft, y + 5, 16777215));
      if(wing != null) {
         this.addButton(new GuiNpcButton(11, super.guiLeft + 122, y, 40, 20, wing.getColor()));
      }

      ModelPartData particles = super.playerdata.getPartData("particles");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(2, var10004, y, 70, 20, this.arrParticles, this.getParticleIndex(particles));
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(2, "Particles", super.guiLeft, y + 5, 16777215));
      if(particles != null && particles.type != 1) {
         this.addButton(new GuiNpcButton(12, super.guiLeft + 122, y, 40, 20, particles.getColor()));
      }

      ModelPartData fin = super.playerdata.getPartData("fin");
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(3, var10004, y, 70, 20, this.arrfins, this.getFinIndex(fin));
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(3, "Fin", super.guiLeft, y + 5, 16777215));
      if(fin != null) {
         this.addButton(new GuiNpcButton(13, super.guiLeft + 122, y, 40, 20, fin.getColor()));
      }

   }

   private int getFinIndex(ModelPartData fin) {
      return fin == null?0:(fin.playerTexture?1:2);
   }

   private int getParticleIndex(ModelPartData particles) {
      if(particles == null) {
         return 0;
      } else {
         if(particles.type == 0) {
            if(particles.playerTexture) {
               return 1;
            }

            if(particles.texture.contains("1")) {
               return 2;
            }

            if(particles.texture.contains("2")) {
               return 3;
            }
         }

         return particles.type == 1?4:0;
      }
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      GuiNpcButton button = (GuiNpcButton)btn;
      if(button.field_146127_k == 0) {
         if(button.getValue() == 0) {
            super.playerdata.removePart("wings");
         } else {
            ModelPartData value = super.playerdata.getOrCreatePart("wings");
            if(button.getValue() > 1) {
               value.setTexture("wings/wing" + (button.getValue() - 1), button.getValue() - 1);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 1) {
         super.playerdata.breasts = (byte)button.getValue();
      }

      ModelPartData particles;
      int value1;
      if(button.field_146127_k == 2) {
         value1 = button.getValue();
         if(value1 == 0) {
            super.playerdata.removePart("particles");
         } else {
            particles = super.playerdata.getOrCreatePart("particles");
            if(value1 == 1) {
               particles.setTexture("", 0);
            }

            if(value1 == 2) {
               particles.setTexture("particle/type1", 0);
            }

            if(value1 == 3) {
               particles.setTexture("particle/type2", 0);
            }

            if(value1 == 4) {
               particles.setTexture("", 1);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 3) {
         value1 = button.getValue();
         if(value1 == 0) {
            super.playerdata.removePart("fin");
         } else {
            particles = super.playerdata.getOrCreatePart("fin");
            if(value1 == 1) {
               particles.setTexture("", 0);
            }

            if(value1 == 2) {
               particles.setTexture("fin/fin1", 0);
            }
         }

         this.initGui();
      }

      if(button.field_146127_k == 11) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("wings"), super.npc));
      }

      if(button.field_146127_k == 12) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("particles"), super.npc));
      }

      if(button.field_146127_k == 13) {
         super.mc.displayGuiScreen(new GuiModelColor(this, super.playerdata.getPartData("fin"), super.npc));
      }

   }

   public void close() {
      super.mc.displayGuiScreen(this.parent);
   }
}
