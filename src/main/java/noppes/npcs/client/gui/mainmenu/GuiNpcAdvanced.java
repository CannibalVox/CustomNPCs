package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.client.Client;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.advanced.GuiNPCAdvancedLinkedNpc;
import noppes.npcs.client.gui.advanced.GuiNPCDialogNpcOptions;
import noppes.npcs.client.gui.advanced.GuiNPCFactionSetup;
import noppes.npcs.client.gui.advanced.GuiNPCLinesMenu;
import noppes.npcs.client.gui.advanced.GuiNPCNightSetup;
import noppes.npcs.client.gui.advanced.GuiNPCSoundsMenu;
import noppes.npcs.client.gui.roles.GuiNpcBard;
import noppes.npcs.client.gui.roles.GuiNpcCompanion;
import noppes.npcs.client.gui.roles.GuiNpcConversation;
import noppes.npcs.client.gui.roles.GuiNpcFollowerJob;
import noppes.npcs.client.gui.roles.GuiNpcGuard;
import noppes.npcs.client.gui.roles.GuiNpcHealer;
import noppes.npcs.client.gui.roles.GuiNpcPuppet;
import noppes.npcs.client.gui.roles.GuiNpcSpawner;
import noppes.npcs.client.gui.roles.GuiNpcTransporter;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumPacketServer;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class GuiNpcAdvanced extends GuiNPCInterface2 implements IGuiData {

   private boolean hasChanges = false;


   public GuiNpcAdvanced(EntityNPCInterface npc) {
      super(npc, 4);
      Client.sendData(EnumPacketServer.MainmenuAdvancedGet, new Object[0]);
   }

   public void initGui() {
      super.initGui();
      this.addButton(new GuiNpcButton(3, super.guiLeft + 85 + 160, super.guiTop + 20, 52, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(8, super.guiLeft + 85, super.guiTop + 20, 155, 20, new String[]{"role.none", "role.trader", "role.follower", "role.bank", "role.transporter", "role.mailman", NoppesStringUtils.translate(new Object[]{"role.companion", "(WIP)"})}, super.npc.advanced.role.ordinal()));
      this.getButton(3).setEnabled(super.npc.advanced.role != EnumRoleType.None && super.npc.advanced.role != EnumRoleType.Postman);
      this.addButton(new GuiNpcButton(4, super.guiLeft + 85 + 160, super.guiTop + 43, 52, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(5, super.guiLeft + 85, super.guiTop + 43, 155, 20, new String[]{"job.none", "job.bard", "job.healer", "job.guard", "job.itemgiver", "role.follower", "job.spawner", "job.conversation", "job.chunkloader", "job.puppet"}, super.npc.advanced.job.ordinal()));
      this.getButton(4).setEnabled(super.npc.advanced.job != EnumJobType.None && super.npc.advanced.job != EnumJobType.ChunkLoader);
      this.addButton(new GuiNpcButton(7, super.guiLeft + 85, super.guiTop + 66, 214, 20, "advanced.lines"));
      this.addButton(new GuiNpcButton(9, super.guiLeft + 85, super.guiTop + 89, 214, 20, "menu.factions"));
      this.addButton(new GuiNpcButton(10, super.guiLeft + 85, super.guiTop + 112, 214, 20, "dialog.dialogs"));
      this.addButton(new GuiNpcButton(11, super.guiLeft + 85, super.guiTop + 135, 214, 20, "advanced.sounds"));
      this.addButton(new GuiNpcButton(12, super.guiLeft + 85, super.guiTop + 158, 214, 20, "advanced.night"));
      this.addButton(new GuiNpcButton(13, super.guiLeft + 85, super.guiTop + 181, 214, 20, "global.linked"));
   }

   protected void actionPerformed(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if(button.field_146127_k == 3) {
         this.save();
         Client.sendData(EnumPacketServer.RoleGet, new Object[0]);
      }

      if(button.field_146127_k == 8) {
         this.hasChanges = true;
         super.npc.advanced.setRole(button.getValue());
         this.getButton(3).setEnabled(super.npc.advanced.role != EnumRoleType.None && super.npc.advanced.role != EnumRoleType.Postman);
      }

      if(button.field_146127_k == 4) {
         this.save();
         Client.sendData(EnumPacketServer.JobGet, new Object[0]);
      }

      if(button.field_146127_k == 5) {
         this.hasChanges = true;
         super.npc.advanced.setJob(button.getValue());
         this.getButton(4).setEnabled(super.npc.advanced.job != EnumJobType.None && super.npc.advanced.job != EnumJobType.ChunkLoader);
      }

      if(button.field_146127_k == 9) {
         this.save();
         NoppesUtil.openGUI(super.player, new GuiNPCFactionSetup(super.npc));
      }

      if(button.field_146127_k == 10) {
         this.save();
         NoppesUtil.openGUI(super.player, new GuiNPCDialogNpcOptions(super.npc, this));
      }

      if(button.field_146127_k == 11) {
         this.save();
         NoppesUtil.openGUI(super.player, new GuiNPCSoundsMenu(super.npc));
      }

      if(button.field_146127_k == 7) {
         this.save();
         NoppesUtil.openGUI(super.player, new GuiNPCLinesMenu(super.npc));
      }

      if(button.field_146127_k == 12) {
         this.save();
         NoppesUtil.openGUI(super.player, new GuiNPCNightSetup(super.npc));
      }

      if(button.field_146127_k == 13) {
         this.save();
         NoppesUtil.openGUI(super.player, new GuiNPCAdvancedLinkedNpc(super.npc));
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if(compound.hasKey("RoleData")) {
         if(super.npc.roleInterface != null) {
            super.npc.roleInterface.readFromNBT(compound);
         }

         if(super.npc.advanced.role == EnumRoleType.Trader) {
            NoppesUtil.requestOpenGUI(EnumGuiType.SetupTrader);
         } else if(super.npc.advanced.role == EnumRoleType.Follower) {
            NoppesUtil.requestOpenGUI(EnumGuiType.SetupFollower);
         } else if(super.npc.advanced.role == EnumRoleType.Bank) {
            NoppesUtil.requestOpenGUI(EnumGuiType.SetupBank);
         } else if(super.npc.advanced.role == EnumRoleType.Transporter) {
            this.displayGuiScreen(new GuiNpcTransporter(super.npc));
         } else if(super.npc.advanced.role == EnumRoleType.Companion) {
            this.displayGuiScreen(new GuiNpcCompanion(super.npc));
         }
      } else if(compound.hasKey("JobData")) {
         if(super.npc.jobInterface != null) {
            super.npc.jobInterface.readFromNBT(compound);
         }

         if(super.npc.advanced.job == EnumJobType.Bard) {
            NoppesUtil.openGUI(super.player, new GuiNpcBard(super.npc));
         } else if(super.npc.advanced.job == EnumJobType.Healer) {
            NoppesUtil.openGUI(super.player, new GuiNpcHealer(super.npc));
         } else if(super.npc.advanced.job == EnumJobType.Guard) {
            NoppesUtil.openGUI(super.player, new GuiNpcGuard(super.npc));
         } else if(super.npc.advanced.job == EnumJobType.ItemGiver) {
            NoppesUtil.requestOpenGUI(EnumGuiType.SetupItemGiver);
         } else if(super.npc.advanced.job == EnumJobType.Follower) {
            NoppesUtil.openGUI(super.player, new GuiNpcFollowerJob(super.npc));
         } else if(super.npc.advanced.job == EnumJobType.Spawner) {
            NoppesUtil.openGUI(super.player, new GuiNpcSpawner(super.npc));
         } else if(super.npc.advanced.job == EnumJobType.Conversation) {
            NoppesUtil.openGUI(super.player, new GuiNpcConversation(super.npc));
         } else if(super.npc.advanced.job == EnumJobType.Puppet) {
            NoppesUtil.openGUI(super.player, new GuiNpcPuppet(this, (EntityCustomNpc)super.npc));
         }
      } else {
         super.npc.advanced.readToNBT(compound);
         this.initGui();
      }

   }

   public void save() {
      if(this.hasChanges) {
         Client.sendData(EnumPacketServer.MainmenuAdvancedSave, new Object[]{super.npc.advanced.writeToNBT(new NBTTagCompound())});
         this.hasChanges = false;
      }

   }
}
