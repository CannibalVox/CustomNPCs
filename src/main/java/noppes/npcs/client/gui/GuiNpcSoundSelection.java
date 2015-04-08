package noppes.npcs.client.gui;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcSoundSelection extends GuiNPCInterface {

   public GuiNPCStringSlot slot;
   private String domain;
   private GuiScreen parent;
   private String up = "..<" + StatCollector.translateToLocal("gui.up") + ">..";
   private HashMap domains = new HashMap();


   public GuiNpcSoundSelection(EntityNPCInterface npc, GuiScreen parent, String sound) {
      super(npc);
      SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
      SoundRegistry registry = (SoundRegistry)ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, 4);
      Set set = registry.getKeys();
      Iterator var7 = set.iterator();

      while(var7.hasNext()) {
         ResourceLocation location = (ResourceLocation)var7.next();
         Object list = (List)this.domains.get(location.getResourceDomain());
         if(list == null) {
            this.domains.put(location.getResourceDomain(), list = new ArrayList());
         }

         ((List)list).add(location.getResourcePath());
         this.domains.put(location.getResourceDomain(), list);
      }

      super.drawDefaultBackground = false;
      this.parent = parent;
   }

   public void initGui() {
      super.initGui();
      String ss = "Current domain: " + this.domain;
      if(this.domain == null) {
         ss = "Select domain";
      }

      this.addLabel(new GuiNpcLabel(0, ss, super.width / 2 - super.fontRendererObj.getStringWidth(ss) / 2, 20, 16777215));
      Object col = this.domains.keySet();
      if(this.domain != null) {
         col = (Collection)this.domains.get(this.domain);
         if(!((Collection)col).contains(this.up)) {
            ((Collection)col).add(this.up);
         }
      }

      this.slot = new GuiNPCStringSlot((Collection)col, this, false, 18);
      this.slot.registerScrollButtons(4, 5);
      if(this.domain != null) {
         this.addButton(new GuiNpcButton(1, super.width / 2 - 100, super.height - 27, 198, 20, "gui.play"));
         this.addButton(new GuiNpcButton(3, super.width / 2 - 100, super.height - 50, 98, 20, "gui.done"));
      } else {
         this.addButton(new GuiNpcButton(4, super.width / 2 - 100, super.height - 50, 98, 20, "gui.open"));
      }

      this.addButton(new GuiNpcButton(2, super.width / 2 + 2, super.height - 50, 98, 20, "gui.cancel"));
   }

   public void drawScreen(int i, int j, float f) {
      this.slot.drawScreen(i, j, f);
      super.drawScreen(i, j, f);
   }

   public void doubleClicked() {
      if(this.slot.selected != null && !this.slot.selected.isEmpty()) {
         if(this.slot.selected.equals(this.up)) {
            this.domain = null;
            this.initGui();
         } else if(this.domain == null) {
            this.domain = this.slot.selected;
            this.initGui();
         } else {
            if(this.parent instanceof GuiNPCInterface) {
               ((GuiNPCInterface)this.parent).elementClicked();
            } else if(this.parent instanceof GuiNPCInterface2) {
               ((GuiNPCInterface2)this.parent).elementClicked();
            }

            this.displayGuiScreen(this.parent);
         }

      }
   }

   protected void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      if(guibutton.id == 1) {
         MusicController.Instance.stopMusic();
         MusicController.Instance.playSound(this.getSelected(), (float)super.player.posX, (float)super.player.posY, (float)super.player.posZ);
      }

      if(guibutton.id == 2) {
         this.displayGuiScreen(this.parent);
      }

      if(guibutton.id == 3) {
         if(this.slot.selected == null || this.slot.selected.equals(this.up)) {
            return;
         }

         this.doubleClicked();
      }

      if(guibutton.id == 4) {
         this.doubleClicked();
      }

   }

   public void save() {}

   public String getSelected() {
      return this.slot.selected.isEmpty()?"":this.domain + ":" + this.slot.selected;
   }
}
