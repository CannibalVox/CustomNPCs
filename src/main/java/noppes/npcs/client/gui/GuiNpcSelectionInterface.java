package noppes.npcs.client.gui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import noppes.npcs.client.AssetsBrowser;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.entity.EntityNPCInterface;

public abstract class GuiNpcSelectionInterface extends GuiNPCInterface {

   public GuiNPCStringSlot slot;
   public GuiScreen parent;
   private String up = "..<" + StatCollector.translateToLocal("gui.up") + ">..";
   private String root = "";
   public AssetsBrowser assets;
   private HashSet dataFolder = new HashSet();
   protected HashSet dataTextures = new HashSet();


   public GuiNpcSelectionInterface(EntityNPCInterface npc, GuiScreen parent, String sound) {
      super(npc);
      this.root = AssetsBrowser.getRoot(sound);
      this.assets = new AssetsBrowser(this.root, this.getExtension());
      super.drawDefaultBackground = false;
      super.title = "";
      this.parent = parent;
   }

   public void initGui() {
      super.initGui();
      this.dataFolder.clear();
      String ss = "Current Folder: /assets" + this.root;
      this.addLabel(new GuiNpcLabel(0, ss, super.width / 2 - super.fontRendererObj.getStringWidth(ss) / 2, 20, 16777215));
      Vector list = new Vector();
      if(!this.assets.isRoot) {
         list.add(this.up);
      }

      Iterator var3 = this.assets.folders.iterator();

      String texture;
      while(var3.hasNext()) {
         texture = (String)var3.next();
         list.add("/" + texture);
         this.dataFolder.add("/" + texture);
      }

      var3 = this.assets.files.iterator();

      while(var3.hasNext()) {
         texture = (String)var3.next();
         list.add(texture);
         this.dataTextures.add(texture);
      }

      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.slot = new GuiNPCStringSlot(list, this, false, 18);
      this.slot.registerScrollButtons(4, 5);
      this.addButton(new GuiNpcButton(2, super.width / 2 - 100, super.height - 44, 98, 20, "gui.back"));
      this.addButton(new GuiNpcButton(3, super.width / 2 + 2, super.height - 44, 98, 20, "gui.up"));
      this.getButton(3).enabled = !this.assets.isRoot;
   }

   public void drawScreen(int i, int j, float f) {
      this.slot.drawScreen(i, j, f);
      super.drawScreen(i, j, f);
   }

   public void elementClicked() {
      if(this.slot.selected != null && this.dataTextures.contains(this.slot.selected)) {
         if(this.parent instanceof GuiNPCInterface) {
            ((GuiNPCInterface)this.parent).elementClicked();
         } else if(this.parent instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.parent).elementClicked();
         }
      }

   }

   public void doubleClicked() {
      if(this.slot.selected.equals(this.up)) {
         this.root = this.root.substring(0, this.root.lastIndexOf("/"));
         this.assets = new AssetsBrowser(this.root, this.getExtension());
         this.initGui();
      } else if(this.dataFolder.contains(this.slot.selected)) {
         this.root = this.root + this.slot.selected;
         this.assets = new AssetsBrowser(this.root, this.getExtension());
         this.initGui();
      } else {
         this.close();
         NoppesUtil.openGUI(super.player, this.parent);
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 2) {
         this.close();
         NoppesUtil.openGUI(super.player, this.parent);
      }

      if(id == 3) {
         this.root = this.root.substring(0, this.root.lastIndexOf("/"));
         this.assets = new AssetsBrowser(this.root, this.getExtension());
         this.initGui();
      }

   }

   public void save() {}

   public String getSelected() {
      return this.assets.getAsset(this.slot.selected);
   }

   public abstract String[] getExtension();
}
