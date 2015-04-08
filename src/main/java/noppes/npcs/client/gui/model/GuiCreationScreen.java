package noppes.npcs.client.gui.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.NPCRendererHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.model.GuiEntitySelection;
import noppes.npcs.client.gui.model.GuiModelArms;
import noppes.npcs.client.gui.model.GuiModelBody;
import noppes.npcs.client.gui.model.GuiModelHead;
import noppes.npcs.client.gui.model.GuiModelLegs;
import noppes.npcs.client.gui.model.GuiModelScale;
import noppes.npcs.client.gui.model.GuiPresetSave;
import noppes.npcs.client.gui.model.GuiPresetSelection;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiModelInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityFakeLiving;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiCreationScreen extends GuiModelInterface implements ICustomScrollListener {

   public HashMap data = new HashMap();
   private List list;
   private final String[] ignoredTags = new String[]{"CanBreakDoors", "Bred", "PlayerCreated", "Tame", "HasReproduced"};
   private GuiNpcButton prev;
   private GuiNpcButton next;
   private GuiScreen parent;
   private HashMap mapped = new HashMap();


   public GuiCreationScreen(GuiScreen parent, EntityCustomNpc npc) {
      super(npc);
      this.parent = parent;
      Map mapping = EntityList.stringToClassMapping;
      Iterator var4 = mapping.keySet().iterator();

      while(var4.hasNext()) {
         Object name = var4.next();
         Class c = (Class)mapping.get(name);

         try {
            if(!EntityCustomNpc.class.isAssignableFrom(c) && EntityLiving.class.isAssignableFrom(c) && c.getConstructor(new Class[]{World.class}) != null && !Modifier.isAbstract(c.getModifiers()) && RenderManager.instance.getEntityClassRenderObject(c) instanceof RendererLivingEntity) {
               this.data.put(name.toString(), c.asSubclass(EntityLivingBase.class));
            }
         } catch (SecurityException var8) {
            var8.printStackTrace();
         } catch (NoSuchMethodException var9) {
            ;
         }
      }

      this.list = new ArrayList(this.data.keySet());
      Collections.sort(this.list, String.CASE_INSENSITIVE_ORDER);
   }

   public void initGui() {
      EntityLivingBase entity = super.playerdata.getEntity(super.npc);
      super.xOffset = entity == null?0:50;
      super.initGui();
      String title = "CustomNPC";
      if(entity != null) {
         title = (String)EntityList.classToStringMapping.get(super.playerdata.getEntityClass());
      }

      this.addButton(new GuiNpcButton(1, super.guiLeft + 140, super.guiTop, 100, 20, title));
      this.addButton(this.prev = new GuiNpcButton(0, super.guiLeft + 118, super.guiTop, 20, 20, "<"));
      this.addButton(this.next = new GuiNpcButton(2, super.guiLeft + 242, super.guiTop, 20, 20, ">"));
      this.prev.enabled = this.getCurrentEntityIndex() >= 0;
      this.next.enabled = this.getCurrentEntityIndex() < this.list.size() - 1;
      if(entity == null) {
         this.showPlayerButtons();
      } else if(PixelmonHelper.isPixelmon(entity)) {
         this.showPixelmonMenu(entity);
      } else {
         this.showEntityButtons(entity);
      }

   }

   private void showPlayerButtons() {
      int y = super.guiTop;
      GuiNpcButton var10001;
      int var10004 = super.guiLeft + 4;
      y += 22;
      var10001 = new GuiNpcButton(8, var10004, y, 96, 20, "model.scale");
      this.addButton(var10001);
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(4, var10004, y, 50, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(1, "Head", super.guiLeft, y + 5, 16777215));
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(5, var10004, y, 50, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(2, "Body", super.guiLeft, y + 5, 16777215));
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(6, var10004, y, 50, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(3, "Arms", super.guiLeft, y + 5, 16777215));
      var10004 = super.guiLeft + 50;
      y += 22;
      var10001 = new GuiNpcButton(7, var10004, y, 50, 20, "selectServer.edit");
      this.addButton(var10001);
      this.addLabel(new GuiNpcLabel(4, "Legs", super.guiLeft, y + 5, 16777215));
      this.addButton(new GuiNpcButton(44, super.guiLeft + 310, super.guiTop + 14, 80, 20, "Save Model"));
      this.addButton(new GuiNpcButton(45, super.guiLeft + 310, super.guiTop + 36, 80, 20, "Load Model"));
   }

   private void showPixelmonMenu(EntityLivingBase entity) {
      GuiCustomScroll scroll = new GuiCustomScroll(this, 0);
      scroll.setSize(120, 200);
      scroll.guiLeft = super.guiLeft;
      scroll.guiTop = super.guiTop + 20;
      this.addScroll(scroll);
      scroll.setList(PixelmonHelper.getPixelmonList());
      scroll.setSelected(PixelmonHelper.getName(entity));
      Minecraft.getMinecraft().thePlayer.sendChatMessage(PixelmonHelper.getName(entity));
   }

   private void showEntityButtons(EntityLivingBase entity) {
      this.mapped.clear();
      if(!(entity instanceof EntityNPCInterface)) {
         int y = super.guiTop + 20;
         NBTTagCompound compound = this.getExtras(entity);
         Set keys = compound.getKeySet();
         int i = 0;
         Iterator var6 = keys.iterator();

         while(var6.hasNext()) {
            String name = (String)var6.next();
            if(!this.isIgnored(name)) {
               NBTBase base = compound.getTag(name);
               if(name.equals("Age")) {
                  ++i;
                  this.addLabel(new GuiNpcLabel(0, "Child", super.guiLeft, y + 5 + i * 22, 16777215));
                  this.addButton(new GuiNpcButton(30, super.guiLeft + 80, y + i * 22, 50, 20, new String[]{"gui.no", "gui.yes"}, entity.isChild()?1:0));
               } else if(base.getId() == 1) {
                  byte b = ((NBTTagByte)base).getByte();
                  if(b == 0 || b == 1) {
                     if(super.playerdata.extra.hasKey(name)) {
                        b = super.playerdata.extra.getByte(name);
                     }

                     ++i;
                     this.addLabel(new GuiNpcLabel(100 + i, name, super.guiLeft, y + 5 + i * 22, 16777215));
                     this.addButton(new GuiNpcButton(100 + i, super.guiLeft + 80, y + i * 22, 50, 20, new String[]{"gui.no", "gui.yes"}, b));
                     this.mapped.put(Integer.valueOf(i), name);
                  }
               }
            }
         }

      }
   }

   private boolean isIgnored(String tag) {
      String[] var2 = this.ignoredTags;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         if(s.equals(tag)) {
            return true;
         }
      }

      return false;
   }

   private NBTTagCompound getExtras(EntityLivingBase entity) {
      NBTTagCompound fake = new NBTTagCompound();
      (new EntityFakeLiving(entity.worldObj)).writeEntityToNBT(fake);
      NBTTagCompound compound = new NBTTagCompound();

      try {
         entity.writeEntityToNBT(compound);
      } catch (Exception var7) {
         ;
      }

      Set keys = fake.getKeySet();
      Iterator var5 = keys.iterator();

      while(var5.hasNext()) {
         String name = (String)var5.next();
         compound.removeTag(name);
      }

      return compound;
   }

   private int getCurrentEntityIndex() {
      return this.list.indexOf(EntityList.classToStringMapping.get(super.playerdata.getEntityClass()));
   }

   protected void actionPerformed(GuiButton btn) {
      super.actionPerformed(btn);
      GuiNpcButton button = (GuiNpcButton)btn;
      int name;
      EntityLivingBase ex;
      RendererLivingEntity render;
      if(button.field_146127_k == 0) {
         name = this.getCurrentEntityIndex();
         if(!this.prev.enabled) {
            return;
         }

         --name;

         try {
            if(name < 0) {
               super.playerdata.setEntityClass((Class)null);
               super.npc.display.texture = "customnpcs:textures/entity/humanmale/Steve.png";
            } else {
               super.playerdata.setEntityClass((Class)this.data.get(this.list.get(name)));
               ex = super.playerdata.getEntity(super.npc);
               if(ex != null) {
                  render = (RendererLivingEntity)RenderManager.instance.getEntityRenderObject(ex);
                  super.npc.display.texture = NPCRendererHelper.getTexture(render, ex);
               }
            }

            super.npc.display.glowTexture = "";
            super.npc.textureLocation = null;
            super.npc.textureGlowLocation = null;
            super.npc.updateHitbox();
         } catch (Exception var7) {
            super.npc.display.texture = "customnpcs:textures/entity/humanmale/Steve.png";
         }

         this.initGui();
      }

      if(button.field_146127_k == 2) {
         name = this.getCurrentEntityIndex();
         if(!this.next.enabled) {
            return;
         }

         ++name;
         super.playerdata.setEntityClass((Class)this.data.get(this.list.get(name)));

         try {
            ex = super.playerdata.getEntity(super.npc);
            if(ex != null) {
               render = (RendererLivingEntity)RenderManager.instance.getEntityRenderObject(ex);
               super.npc.display.texture = NPCRendererHelper.getTexture(render, ex);
            } else {
               super.npc.display.texture = "customnpcs:textures/entity/humanmale/Steve.png";
            }

            super.npc.display.glowTexture = "";
            super.npc.textureLocation = null;
            super.npc.textureGlowLocation = null;
            super.npc.updateHitbox();
         } catch (Exception var6) {
            super.npc.display.texture = "customnpcs:textures/entity/humanmale/Steve.png";
         }

         this.initGui();
      }

      if(button.field_146127_k == 1) {
         super.mc.displayGuiScreen(new GuiEntitySelection(this, super.playerdata, super.npc));
      }

      if(button.field_146127_k == 4) {
         super.mc.displayGuiScreen(new GuiModelHead(this, super.npc));
      }

      if(button.field_146127_k == 5) {
         super.mc.displayGuiScreen(new GuiModelBody(this, super.npc));
      }

      if(button.field_146127_k == 6) {
         super.mc.displayGuiScreen(new GuiModelArms(this, super.npc));
      }

      if(button.field_146127_k == 7) {
         super.mc.displayGuiScreen(new GuiModelLegs(this, super.npc));
      }

      if(button.field_146127_k == 8) {
         super.mc.displayGuiScreen(new GuiModelScale(this, super.playerdata, super.npc));
      }

      if(button.field_146127_k == 30) {
         super.playerdata.extra.setInteger("Age", button.getValue() == 1?-24000:0);
         super.playerdata.clearEntity();
      }

      if(button.field_146127_k == 44) {
         super.mc.displayGuiScreen(new GuiPresetSave(this, super.playerdata));
      }

      if(button.field_146127_k == 45) {
         super.mc.displayGuiScreen(new GuiPresetSelection(this, super.playerdata));
      }

      if(button.field_146127_k >= 100) {
         String var8 = (String)this.mapped.get(Integer.valueOf(button.field_146127_k - 100));
         if(var8 != null) {
            super.playerdata.extra.setBoolean(var8, button.getValue() == 1);
            super.playerdata.clearEntity();
         }
      }

   }

   public void close() {
      Client.sendData(EnumPacketServer.ModelDataSave, new Object[]{super.playerdata.writeToNBT()});
      this.displayGuiScreen(this.parent);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll scroll) {
      EntityLivingBase entity = super.playerdata.getEntity(super.npc);
      String name = scroll.getSelected();
      PixelmonHelper.setName(entity, name);
      super.playerdata.extra.setString("Name", name);
      RendererLivingEntity render = (RendererLivingEntity)RenderManager.instance.getEntityRenderObject(entity);
      super.npc.display.texture = NPCRendererHelper.getTexture(render, entity);
   }
}
