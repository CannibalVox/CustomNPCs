package noppes.npcs.client.gui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import noppes.npcs.client.gui.util.GuiNPCInterface;

public class GuiNPCStringSlot extends GuiSlot {

   private List list;
   public String selected;
   public HashSet selectedList = new HashSet();
   private boolean multiSelect;
   private GuiNPCInterface parent;
   public int size;
   private long prevTime = 0L;


   public GuiNPCStringSlot(Collection list, GuiNPCInterface parent, boolean multiSelect, int size) {
      super(Minecraft.getMinecraft(), parent.width, parent.height, 32, parent.height - 64, size);
      this.parent = parent;
      this.list = new ArrayList(list);
      Collections.sort(this.list, String.CASE_INSENSITIVE_ORDER);
      this.multiSelect = multiSelect;
      this.size = size;
   }

   public void setList(List list) {
      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.list = list;
      this.selected = "";
   }

   protected int getSize() {
      return this.list.size();
   }

   protected void elementClicked(int i, boolean flag, int j, int k) {
      long time = System.currentTimeMillis();
      if(this.selected != null && this.selected.equals(this.list.get(i)) && time - this.prevTime < 400L) {
         this.parent.doubleClicked();
      }

      this.selected = (String)this.list.get(i);
      if(this.selectedList.contains(this.selected)) {
         this.selectedList.remove(this.selected);
      } else {
         this.selectedList.add(this.selected);
      }

      this.parent.elementClicked();
      this.prevTime = time;
   }

   protected boolean isSelected(int i) {
      return !this.multiSelect?(this.selected == null?false:this.selected.equals(this.list.get(i))):this.selectedList.contains(this.list.get(i));
   }

   protected int getContentHeight() {
      return this.list.size() * this.size;
   }

   protected void drawBackground() {
      this.parent.drawDefaultBackground();
   }

   protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator, int var6, int var7) {
      String s = (String)this.list.get(i);
      this.parent.drawString(this.parent.getFontRenderer(), s, j + 50, k + 3, 16777215);
   }

   public void clear() {
      this.list.clear();
   }
}
