package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.player.GuiMailmanWrite;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.ICustomScrollListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.PlayerMailData;

public class GuiMailbox extends GuiNPCInterface implements IGuiData, ICustomScrollListener, GuiYesNoCallback {

   private GuiCustomScroll scroll;
   private PlayerMailData data;
   private PlayerMail selected;


   public GuiMailbox() {
      super.xSize = 256;
      this.setBackground("menubg.png");
      NoppesUtilPlayer.sendData(EnumPlayerPacket.MailGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      if(this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 186);
      }

      this.scroll.guiLeft = super.guiLeft + 4;
      this.scroll.guiTop = super.guiTop + 4;
      this.addScroll(this.scroll);
      String title = StatCollector.translateToLocal("mailbox.name");
      int x = (super.xSize - super.fontRendererObj.getStringWidth(title)) / 2;
      this.addLabel(new GuiNpcLabel(0, title, super.guiLeft + x, super.guiTop - 8));
      if(this.selected != null) {
         this.addLabel(new GuiNpcLabel(3, StatCollector.translateToLocal("mailbox.sender") + ":", super.guiLeft + 170, super.guiTop + 6));
         this.addLabel(new GuiNpcLabel(1, this.selected.sender, super.guiLeft + 174, super.guiTop + 18));
         this.addLabel(new GuiNpcLabel(2, StatCollector.translateToLocalFormatted("mailbox.timesend", new Object[]{this.getTimePast()}), super.guiLeft + 174, super.guiTop + 30));
      }

      this.addButton(new GuiNpcButton(0, super.guiLeft + 4, super.guiTop + 192, 82, 20, "mailbox.read"));
      this.addButton(new GuiNpcButton(1, super.guiLeft + 88, super.guiTop + 192, 82, 20, "selectWorld.deleteButton"));
      this.getButton(1).setEnabled(this.selected != null);
   }

   private String getTimePast() {
      int minutes;
      if(this.selected.timePast > 86400000L) {
         minutes = (int)(this.selected.timePast / 86400000L);
         return minutes == 1?minutes + " " + StatCollector.translateToLocal("mailbox.day"):minutes + " " + StatCollector.translateToLocal("mailbox.days");
      } else if(this.selected.timePast > 3600000L) {
         minutes = (int)(this.selected.timePast / 3600000L);
         return minutes == 1?minutes + " " + StatCollector.translateToLocal("mailbox.hour"):minutes + " " + StatCollector.translateToLocal("mailbox.hours");
      } else {
         minutes = (int)(this.selected.timePast / 60000L);
         return minutes == 1?minutes + " " + StatCollector.translateToLocal("mailbox.minutes"):minutes + " " + StatCollector.translateToLocal("mailbox.minutes");
      }
   }

   public void confirmClicked(boolean flag, int i) {
      if(flag && this.selected != null) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.MailDelete, new Object[]{Long.valueOf(this.selected.time), this.selected.sender});
         this.selected = null;
      }

      NoppesUtil.openGUI(super.player, this);
   }

   protected void actionPerformed(GuiButton guibutton) {
      int id = guibutton.id;
      if(this.scroll.selected >= 0) {
         if(id == 0) {
            GuiMailmanWrite.parent = this;
            GuiMailmanWrite.mail = this.selected;
            NoppesUtilPlayer.sendData(EnumPlayerPacket.MailboxOpenMail, new Object[]{Long.valueOf(this.selected.time), this.selected.sender});
            this.selected = null;
            this.scroll.selected = -1;
         }

         if(id == 1) {
            GuiYesNo guiyesno = new GuiYesNo(this, "Confirm", StatCollector.translateToLocal("gui.delete"), 0);
            this.displayGuiScreen(guiyesno);
         }

      }
   }

   public void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      this.scroll.mouseClicked(i, j, k);
   }

   public void keyTyped(char c, int i) {
      if(i == 1 || this.isInventoryKey(i)) {
         this.close();
      }

   }

   public void save() {}

   public void setGuiData(NBTTagCompound compound) {
      PlayerMailData data = new PlayerMailData();
      data.loadNBTData(compound);
      ArrayList list = new ArrayList();
      Collections.sort(data.playermail, new Comparator<PlayerMail>() {
         public int compare(PlayerMail o1, PlayerMail o2) {
            return o1.time == o2.time?0:(o1.time > o2.time?-1:1);
         }
      });
      Iterator var4 = data.playermail.iterator();

      while(var4.hasNext()) {
         PlayerMail mail = (PlayerMail)var4.next();
         list.add(mail.subject);
      }

      this.data = data;
      this.scroll.clear();
      this.selected = null;
      this.scroll.setUnsortedList(list);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      this.selected = (PlayerMail)this.data.playermail.get(guiCustomScroll.selected);
      this.initGui();
      if(this.selected != null && !this.selected.beenRead) {
         this.selected.beenRead = true;
         NoppesUtilPlayer.sendData(EnumPlayerPacket.MailRead, new Object[]{Long.valueOf(this.selected.time), this.selected.sender});
      }

   }
}
