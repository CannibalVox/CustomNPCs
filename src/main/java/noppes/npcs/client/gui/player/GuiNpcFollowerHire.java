package noppes.npcs.client.gui.player;

import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;
import org.lwjgl.opengl.GL11;

public class GuiNpcFollowerHire extends GuiContainerNPCInterface {

   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/followerhire.png");
   private EntityNPCInterface npc;
   private ContainerNPCFollowerHire container;
   private RoleFollower role;


   public GuiNpcFollowerHire(EntityNPCInterface npc, ContainerNPCFollowerHire container) {
      super(npc, container);
      this.container = container;
      this.npc = npc;
      this.role = (RoleFollower)npc.roleInterface;
      super.closeOnEsc = true;
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(5, super.field_147003_i + 26, super.field_147009_r + 60, 50, 20, StatCollector.translateToLocal("follower.hire")));
   }

   public void actionPerformed(GuiButton guibutton) {
      super.actionPerformed(guibutton);
      if(guibutton.id == 5) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerHire, new Object[0]);
         this.close();
      }

   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(this.resource);
      int l = (super.width - super.xSize) / 2;
      int i1 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(l, i1, 0, 0, super.xSize, super.ySize);
      int index = 0;
      Iterator var7 = this.role.inventory.items.keySet().iterator();

      while(var7.hasNext()) {
         int id = ((Integer)var7.next()).intValue();
         ItemStack itemstack = (ItemStack)this.role.inventory.items.get(Integer.valueOf(id));
         if(itemstack != null) {
            int days = 1;
            if(this.role.rates.containsKey(Integer.valueOf(id))) {
               days = ((Integer)this.role.rates.get(Integer.valueOf(id))).intValue();
            }

            int yOffset = index * 26;
            int x = super.field_147003_i + 78;
            int y = super.field_147009_r + yOffset + 10;
            GL11.glEnable('\u803a');
            RenderHelper.enableGUIStandardItemLighting();
            GuiScreen.itemRender.renderItemIntoGUI(super.fontRendererObj, super.mc.renderEngine, itemstack, x + 11, y);
            GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, super.mc.renderEngine, itemstack, x + 11, y);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable('\u803a');
            String daysS = days + " " + (days == 1?StatCollector.translateToLocal("follower.day"):StatCollector.translateToLocal("follower.days"));
            super.fontRendererObj.drawString(" = " + daysS, x + 27, y + 4, CustomNpcResourceListener.DefaultTextColor);
            ++index;
         }
      }

   }

   public void save() {}
}
