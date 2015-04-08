package noppes.npcs.client.gui.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.Client;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobFollower;

public class GuiNpcFollowerJob extends GuiNPCInterface2 implements ICustomScrollListener {

   private JobFollower job;
   private GuiCustomScroll scroll;


   public GuiNpcFollowerJob(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobFollower)npc.jobInterface;
   }

   public void initGui() {
      super.initGui();
      this.addLabel(new GuiNpcLabel(1, "gui.name", super.guiLeft + 6, super.guiTop + 110));
      this.addTextField(new GuiNpcTextField(1, this, super.fontRendererObj, super.guiLeft + 50, super.guiTop + 105, 200, 20, this.job.name));
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.setSize(143, 208);
      this.scroll.guiLeft = super.guiLeft + 268;
      this.scroll.guiTop = super.guiTop + 4;
      this.addScroll(this.scroll);
      ArrayList names = new ArrayList();
      List list = super.npc.worldObj.getEntitiesWithinAABB(EntityNPCInterface.class, super.npc.boundingBox.expand(40.0D, 40.0D, 40.0D));
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         EntityNPCInterface npc = (EntityNPCInterface)var3.next();
         if(npc != super.npc && !names.contains(npc.display.name)) {
            names.add(npc.display.name);
         }
      }

      this.scroll.setList(names);
   }

   public void save() {
      this.job.name = this.getTextField(1).getText();
      Client.sendData(EnumPacketServer.JobSave, new Object[]{this.job.writeToNBT(new NBTTagCompound())});
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      this.getTextField(1).setText(guiCustomScroll.getSelected());
   }
}
