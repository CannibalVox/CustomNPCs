package noppes.npcs.client.gui;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import noppes.npcs.client.Client;
import noppes.npcs.client.controllers.ClientCloneController;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketServer;

public class GuiNpcMobSpawner extends GuiNPCInterface implements IGuiData {

   private GuiCustomScroll scroll;
   private int posX;
   private int posY;
   private int posZ;
   private List list;
   private static int showingClones = 0;
   private static String search = "";
   private int activeTab = 1;


   public GuiNpcMobSpawner(int i, int j, int k) {
      super.xSize = 256;
      this.posX = i;
      this.posY = j;
      this.posZ = k;
      super.closeOnEsc = true;
      this.setBackground("menubg.png");
   }

   public void initGui() {
      super.initGui();
      super.guiTop += 10;
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 188);
      } else {
         this.scroll.clear();
      }

      this.scroll.guiLeft = super.guiLeft + 4;
      this.scroll.guiTop = super.guiTop + 26;
      this.addScroll(this.scroll);
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 4, super.guiTop + 4, 165, 20, search));
      GuiMenuTopButton button;
      this.addTopButton(button = new GuiMenuTopButton(3, super.guiLeft + 4, super.guiTop - 17, "spawner.clones"));
      button.active = showingClones == 0;
      this.addTopButton(button = new GuiMenuTopButton(4, button, "spawner.entities"));
      button.active = showingClones == 1;
      this.addTopButton(button = new GuiMenuTopButton(5, button, "gui.server"));
      button.active = showingClones == 2;
      this.addButton(new GuiNpcButton(1, super.guiLeft + 170, super.guiTop + 6, 82, 20, "item.monsterPlacer.name"));
      this.addButton(new GuiNpcButton(2, super.guiLeft + 170, super.guiTop + 100, 82, 20, "spawner.mobspawner"));
      if(showingClones != 0 && showingClones != 2) {
         this.showEntities();
      } else {
         this.addSideButton(new GuiMenuSideButton(21, super.guiLeft - 69, super.guiTop + 2, 70, 22, "Tab 1"));
         this.addSideButton(new GuiMenuSideButton(22, super.guiLeft - 69, super.guiTop + 23, 70, 22, "Tab 2"));
         this.addSideButton(new GuiMenuSideButton(23, super.guiLeft - 69, super.guiTop + 44, 70, 22, "Tab 3"));
         this.addSideButton(new GuiMenuSideButton(24, super.guiLeft - 69, super.guiTop + 65, 70, 22, "Tab 4"));
         this.addSideButton(new GuiMenuSideButton(25, super.guiLeft - 69, super.guiTop + 86, 70, 22, "Tab 5"));
         this.addSideButton(new GuiMenuSideButton(26, super.guiLeft - 69, super.guiTop + 107, 70, 22, "Tab 6"));
         this.addSideButton(new GuiMenuSideButton(27, super.guiLeft - 69, super.guiTop + 128, 70, 22, "Tab 7"));
         this.addSideButton(new GuiMenuSideButton(28, super.guiLeft - 69, super.guiTop + 149, 70, 22, "Tab 8"));
         this.addSideButton(new GuiMenuSideButton(29, super.guiLeft - 69, super.guiTop + 170, 70, 22, "Tab 9"));
         this.addButton(new GuiNpcButton(6, super.guiLeft + 170, super.guiTop + 30, 82, 20, "gui.remove"));
         this.getSideButton(20 + this.activeTab).active = true;
         this.showClones();
      }

   }

   private void showEntities() {
      Map data = EntityList.stringToClassMapping;
      ArrayList list = new ArrayList();
      Iterator var3 = data.keySet().iterator();

      while(var3.hasNext()) {
         Object name = var3.next();
         Class c = (Class)data.get(name);

         try {
            if(EntityLiving.class.isAssignableFrom(c) && c.getConstructor(new Class[]{World.class}) != null && !Modifier.isAbstract(c.getModifiers())) {
               list.add(name.toString());
            }
         } catch (SecurityException var7) {
            var7.printStackTrace();
         } catch (NoSuchMethodException var8) {
            ;
         }
      }

      this.list = list;
      this.scroll.setList(this.getSearchList());
   }

   private void showClones() {
      if(showingClones == 2) {
         Client.sendData(EnumPacketServer.CloneList, new Object[]{Integer.valueOf(this.activeTab)});
      } else {
         new ArrayList();
         this.list = ClientCloneController.Instance.getClones(this.activeTab);
         this.scroll.setList(this.getSearchList());
      }
   }

   public void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      if(!search.equals(this.getTextField(1).getText())) {
         search = this.getTextField(1).getText().toLowerCase();
         this.scroll.setList(this.getSearchList());
      }
   }

   private List getSearchList() {
      if(search.isEmpty()) {
         return new ArrayList(this.list);
      } else {
         ArrayList list = new ArrayList();
         Iterator var2 = this.list.iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();
            if(name.toLowerCase().contains(search)) {
               list.add(name);
            }
         }

         return list;
      }
   }

   private NBTTagCompound getCompound() {
      String sel = this.scroll.getSelected();
      if(sel == null) {
         return null;
      } else if(showingClones == 0) {
         return ClientCloneController.Instance.getCloneData(super.player, sel, this.activeTab);
      } else {
         Entity entity = EntityList.createEntityByName(sel, Minecraft.getMinecraft().theWorld);
         if(entity == null) {
            return null;
         } else {
            NBTTagCompound compound = new NBTTagCompound();
            entity.writeToNBTOptional(compound);
            return compound;
         }
      }
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(id == 0) {
         this.close();
      }

      String compound;
      NBTTagCompound compound1;
      if(id == 1) {
         if(showingClones == 2) {
            compound = this.scroll.getSelected();
            if(compound == null) {
               return;
            }

            Client.sendData(EnumPacketServer.SpawnMob, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.posX), Integer.valueOf(this.posY), Integer.valueOf(this.posZ), compound, Integer.valueOf(this.activeTab)});
            this.close();
         } else {
            compound1 = this.getCompound();
            if(compound1 == null) {
               return;
            }

            Client.sendData(EnumPacketServer.SpawnMob, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.posX), Integer.valueOf(this.posY), Integer.valueOf(this.posZ), compound1});
            this.close();
         }
      }

      if(id == 2) {
         if(showingClones == 2) {
            compound = this.scroll.getSelected();
            if(compound == null) {
               return;
            }

            Client.sendData(EnumPacketServer.MobSpawner, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.posX), Integer.valueOf(this.posY), Integer.valueOf(this.posZ), compound, Integer.valueOf(this.activeTab)});
            this.close();
         } else {
            compound1 = this.getCompound();
            if(compound1 == null) {
               return;
            }

            Client.sendData(EnumPacketServer.MobSpawner, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.posX), Integer.valueOf(this.posY), Integer.valueOf(this.posZ), compound1});
            this.close();
         }
      }

      if(id == 3) {
         showingClones = 0;
         this.initGui();
      }

      if(id == 4) {
         showingClones = 1;
         this.initGui();
      }

      if(id == 5) {
         showingClones = 2;
         this.initGui();
      }

      if(id == 6 && this.scroll.getSelected() != null) {
         if(showingClones == 2) {
            Client.sendData(EnumPacketServer.CloneRemove, new Object[]{Integer.valueOf(this.activeTab), this.scroll.getSelected()});
            return;
         }

         ClientCloneController.Instance.removeClone(this.scroll.getSelected(), this.activeTab);
         this.scroll.selected = -1;
         this.initGui();
      }

      if(id > 20) {
         this.activeTab = id - 20;
         this.initGui();
      }

   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      NBTTagList nbtlist = compound.getTagList("List", 8);
      ArrayList list = new ArrayList();

      for(int i = 0; i < nbtlist.tagCount(); ++i) {
         list.add(nbtlist.getStringTagAt(i));
      }

      this.list = list;
      this.scroll.setList(this.getSearchList());
   }

}
