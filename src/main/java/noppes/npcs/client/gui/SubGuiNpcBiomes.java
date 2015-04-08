package noppes.npcs.client.gui;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.world.biome.BiomeGenBase;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.SpawnData;

public class SubGuiNpcBiomes extends SubGuiInterface {

   private SpawnData data;
   private GuiCustomScroll scroll1;
   private GuiCustomScroll scroll2;


   public SubGuiNpcBiomes(SpawnData data) {
      this.data = data;
      this.setBackground("menubg.png");
      super.xSize = 346;
      super.ySize = 216;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      if(this.scroll1 == null) {
         this.scroll1 = new GuiCustomScroll(this, 0);
         this.scroll1.setSize(140, 180);
      }

      this.scroll1.guiLeft = super.guiLeft + 4;
      this.scroll1.guiTop = super.guiTop + 14;
      this.addScroll(this.scroll1);
      this.addLabel(new GuiNpcLabel(1, "spawning.availableBiomes", super.guiLeft + 4, super.guiTop + 4));
      if(this.scroll2 == null) {
         this.scroll2 = new GuiCustomScroll(this, 1);
         this.scroll2.setSize(140, 180);
      }

      this.scroll2.guiLeft = super.guiLeft + 200;
      this.scroll2.guiTop = super.guiTop + 14;
      this.addScroll(this.scroll2);
      this.addLabel(new GuiNpcLabel(2, "spawning.spawningBiomes", super.guiLeft + 200, super.guiTop + 4));
      ArrayList biomes = new ArrayList();
      BiomeGenBase[] var2 = BiomeGenBase.getBiomeGenArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         BiomeGenBase base = var2[var4];
         if(base != null && !this.data.biomes.contains(base.biomeName)) {
            biomes.add(base.biomeName);
         }
      }

      this.scroll1.setList(biomes);
      this.scroll2.setList(this.data.biomes);
      this.addButton(new GuiNpcButton(1, super.guiLeft + 145, super.guiTop + 40, 55, 20, ">"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 145, super.guiTop + 62, 55, 20, "<"));
      this.addButton(new GuiNpcButton(3, super.guiLeft + 145, super.guiTop + 90, 55, 20, ">>"));
      this.addButton(new GuiNpcButton(4, super.guiLeft + 145, super.guiTop + 112, 55, 20, "<<"));
      this.addButton(new GuiNpcButton(66, super.guiLeft + 260, super.guiTop + 194, 60, 20, "gui.done"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 1 && this.scroll1.hasSelected()) {
         this.data.biomes.add(this.scroll1.getSelected());
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 2 && this.scroll2.hasSelected()) {
         this.data.biomes.remove(this.scroll2.getSelected());
         this.scroll2.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 3) {
         this.data.biomes.clear();
         BiomeGenBase[] var3 = BiomeGenBase.getBiomeGenArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BiomeGenBase base = var3[var5];
            if(base != null) {
               this.data.biomes.add(base.biomeName);
            }
         }

         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 4) {
         this.data.biomes.clear();
         this.scroll1.selected = -1;
         this.scroll1.selected = -1;
         this.initGui();
      }

      if(button.field_146127_k == 66) {
         this.close();
      }

   }
}
