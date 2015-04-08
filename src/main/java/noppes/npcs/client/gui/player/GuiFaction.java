package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiButtonNextPage;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.PlayerFactionData;
import org.lwjgl.opengl.GL11;
import tconstruct.client.tabs.InventoryTabFactions;
import tconstruct.client.tabs.TabRegistry;

public class GuiFaction extends GuiNPCInterface implements IGuiData {

   private int xSize = 200;
   private int ySize = 195;
   private int guiLeft;
   private int guiTop;
   private ArrayList playerFactions = new ArrayList();
   private int page = 0;
   private int pages = 1;
   private GuiButtonNextPage buttonNextPage;
   private GuiButtonNextPage buttonPreviousPage;
   private ResourceLocation indicator;


   public GuiFaction() {
      super.drawDefaultBackground = false;
      super.title = "";
      NoppesUtilPlayer.sendData(EnumPlayerPacket.FactionsGet, new Object[0]);
      this.indicator = this.getResource("standardbg.png");
   }

   public void initGui() {
      super.initGui();
      this.guiLeft = (super.width - this.xSize) / 2;
      this.guiTop = (super.height - this.ySize) / 2 + 12;
      TabRegistry.updateTabValues(this.guiLeft, this.guiTop + 8, InventoryTabFactions.class);
      TabRegistry.addTabsToList(super.buttonList);
      super.buttonList.add(this.buttonNextPage = new GuiButtonNextPage(1, this.guiLeft + this.xSize - 43, this.guiTop + 180, true));
      super.buttonList.add(this.buttonPreviousPage = new GuiButtonNextPage(2, this.guiLeft + 20, this.guiTop + 180, false));
      this.updateButtons();
   }

   public void drawScreen(int i, int j, float f) {
      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.indicator);
      this.drawTexturedModalRect(this.guiLeft, this.guiTop + 8, 0, 0, this.xSize, this.ySize);
      this.drawTexturedModalRect(this.guiLeft + 4, this.guiTop + 8, 56, 0, 200, this.ySize);
      if(this.playerFactions.isEmpty()) {
         String noFaction = StatCollector.translateToLocal("faction.nostanding");
         super.fontRendererObj.drawString(noFaction, this.guiLeft + (this.xSize - super.fontRendererObj.getStringWidth(noFaction)) / 2, this.guiTop + 80, CustomNpcResourceListener.DefaultTextColor);
      } else {
         this.renderScreen();
      }

      super.drawScreen(i, j, f);
   }

   private void renderScreen() {
      int size = 5;
      if(this.pages == 1) {
         size = this.playerFactions.size();
      }

      if(this.page == this.pages) {
         size = this.playerFactions.size() % 5;
      }

      for(int s = 0; s < size; ++s) {
         this.drawHorizontalLine(this.guiLeft + 2, this.guiLeft + this.xSize, this.guiTop + 14 + s * 30, -16777216 + CustomNpcResourceListener.DefaultTextColor);
         Faction faction = (Faction)this.playerFactions.get((this.page - 1) * 5 + s);
         String name = faction.name;
         String points = " : " + faction.defaultPoints;
         String standing = StatCollector.translateToLocal("faction.friendly");
         int color = '\uff00';
         if(faction.defaultPoints < faction.neutralPoints) {
            standing = StatCollector.translateToLocal("faction.unfriendly");
            color = 16711680;
            points = points + "/" + faction.neutralPoints;
         } else if(faction.defaultPoints < faction.friendlyPoints) {
            standing = StatCollector.translateToLocal("faction.neutral");
            color = 15924992;
            points = points + "/" + faction.friendlyPoints;
         } else {
            points = points + "/-";
         }

         super.fontRendererObj.drawString(name, this.guiLeft + (this.xSize - super.fontRendererObj.getStringWidth(name)) / 2, this.guiTop + 19 + s * 30, faction.color);
         super.fontRendererObj.drawString(standing, super.width / 2 - super.fontRendererObj.getStringWidth(standing) - 1, this.guiTop + 33 + s * 30, color);
         super.fontRendererObj.drawString(points, super.width / 2, this.guiTop + 33 + s * 30, CustomNpcResourceListener.DefaultTextColor);
      }

      this.drawHorizontalLine(this.guiLeft + 2, this.guiLeft + this.xSize, this.guiTop + 14 + size * 30, -16777216 + CustomNpcResourceListener.DefaultTextColor);
      if(this.pages > 1) {
         String var8 = this.page + "/" + this.pages;
         super.fontRendererObj.drawString(var8, this.guiLeft + (this.xSize - super.fontRendererObj.getStringWidth(var8)) / 2, this.guiTop + 203, CustomNpcResourceListener.DefaultTextColor);
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton instanceof GuiButtonNextPage) {
         int id = guibutton.id;
         if(id == 1) {
            ++this.page;
         }

         if(id == 2) {
            --this.page;
         }

         this.updateButtons();
      }
   }

   private void updateButtons() {
      this.buttonNextPage.setVisible(this.page < this.pages);
      this.buttonPreviousPage.setVisible(this.page > 1);
   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {}

   public void keyTyped(char c, int i) {
      if(i == 1 || this.isInventoryKey(i)) {
         this.close();
      }

   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      this.playerFactions = new ArrayList();
      NBTTagList list = compound.getTagList("FactionList", 10);

      for(int data = 0; data < list.tagCount(); ++data) {
         Faction faction = new Faction();
         faction.readNBT(list.getCompoundTagAt(data));
         this.playerFactions.add(faction);
      }

      PlayerFactionData var9 = new PlayerFactionData();
      var9.loadNBTData(compound);
      Iterator var10 = var9.factionData.keySet().iterator();

      while(var10.hasNext()) {
         int id = ((Integer)var10.next()).intValue();
         int points = ((Integer)var9.factionData.get(Integer.valueOf(id))).intValue();
         Iterator var7 = this.playerFactions.iterator();

         while(var7.hasNext()) {
            Faction faction1 = (Faction)var7.next();
            if(faction1.id == id) {
               faction1.defaultPoints = points;
            }
         }
      }

      this.pages = (this.playerFactions.size() - 1) / 5;
      ++this.pages;
      this.page = 1;
      this.updateButtons();
   }
}
