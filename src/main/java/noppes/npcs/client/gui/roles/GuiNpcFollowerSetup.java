package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcButtonYesNo;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.containers.ContainerNPCFollowerSetup;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class GuiNpcFollowerSetup extends GuiContainerNPCInterface2 {

   private RoleFollower role;
   private static final ResourceLocation field_110422_t = new ResourceLocation("textures/gui/followersetup.png");


   public GuiNpcFollowerSetup(EntityNPCInterface npc, ContainerNPCFollowerSetup container) {
      super(npc, container);
      super.ySize = 200;
      this.role = (RoleFollower)npc.roleInterface;
      this.setBackground("followersetup.png");
   }

   public void initGui() {
      super.initGui();

      int i;
      int day;
      for(i = 0; i < 3; ++i) {
         int x = super.field_147003_i + 66;
         day = super.field_147009_r + 37;
         day += i * 25;
         GuiNpcTextField tf = new GuiNpcTextField(i, this, super.fontRendererObj, x, day, 24, 20, "1");
         tf.numbersOnly = true;
         tf.setMinMaxDefault(1, Integer.MAX_VALUE, 1);
         this.addTextField(tf);
      }

      i = 0;

      for(Iterator var5 = this.role.rates.values().iterator(); var5.hasNext(); ++i) {
         day = ((Integer)var5.next()).intValue();
         this.getTextField(i).setText(day + "");
      }

      this.addTextField(new GuiNpcTextField(3, this, super.fontRendererObj, super.field_147003_i + 100, super.field_147009_r + 6, 286, 20, this.role.dialogHire));
      this.addTextField(new GuiNpcTextField(4, this, super.fontRendererObj, super.field_147003_i + 100, super.field_147009_r + 30, 286, 20, this.role.dialogFarewell));
      this.addLabel(new GuiNpcLabel(7, "follower.infiniteDays", super.field_147003_i + 180, super.field_147009_r + 80));
      this.addButton(new GuiNpcButtonYesNo(7, super.field_147003_i + 260, super.field_147009_r + 75, this.role.infiniteDays));
      this.addLabel(new GuiNpcLabel(8, "follower.guiDisabled", super.field_147003_i + 180, super.field_147009_r + 104));
      this.addButton(new GuiNpcButtonYesNo(8, super.field_147003_i + 260, super.field_147009_r + 99, this.role.disableGui));
      this.addLabel(new GuiNpcLabel(9, "follower.allowSoulstone", super.field_147003_i + 180, super.field_147009_r + 128));
      this.addButton(new GuiNpcButtonYesNo(9, super.field_147003_i + 260, super.field_147009_r + 123, !this.role.refuseSoulStone));
      this.addButton(new GuiNpcButton(10, super.field_147003_i + 195, super.field_147009_r + 147, 100, 20, "remote.reset"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 7) {
         this.role.infiniteDays = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

      if(guibutton.id == 8) {
         this.role.disableGui = ((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

      if(guibutton.id == 9) {
         this.role.refuseSoulStone = !((GuiNpcButtonYesNo)guibutton).getBoolean();
      }

      if(guibutton.id == 10) {
         this.role.killed();
      }

   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}

   public void save() {
      HashMap map = new HashMap();

      for(int i = 0; i < this.role.inventory.getSizeInventory(); ++i) {
         ItemStack item = this.role.inventory.getStackInSlot(i);
         if(item != null) {
            int days = 1;
            if(!this.getTextField(i).isEmpty() && this.getTextField(i).isInteger()) {
               days = this.getTextField(i).getInteger();
            }

            if(days <= 0) {
               days = 1;
            }

            map.put(Integer.valueOf(i), Integer.valueOf(days));
         }
      }

      this.role.rates = map;
      this.role.dialogHire = this.getTextField(3).getText();
      this.role.dialogFarewell = this.getTextField(4).getText();
      Client.sendData(EnumPacketServer.RoleSave, new Object[]{this.role.writeToNBT(new NBTTagCompound())});
   }

}
