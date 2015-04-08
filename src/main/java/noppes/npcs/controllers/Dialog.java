package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.ICompatibilty;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.FactionOptions;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;

public class Dialog implements ICompatibilty {

   public int version;
   public int id;
   public String title;
   public String text;
   public int quest;
   public DialogCategory category;
   public HashMap options;
   public Availability availability;
   public FactionOptions factionOptions;
   public String sound;
   public String command;
   public PlayerMail mail;
   public boolean hideNPC;
   public boolean showWheel;
   public boolean disableEsc;


   public Dialog() {
      this.version = VersionCompatibility.ModRev;
      this.id = -1;
      this.title = "";
      this.text = "";
      this.quest = -1;
      this.options = new HashMap();
      this.availability = new Availability();
      this.factionOptions = new FactionOptions();
      this.command = "";
      this.mail = new PlayerMail();
      this.hideNPC = false;
      this.showWheel = false;
      this.disableEsc = false;
   }

   public boolean hasDialogs(EntityPlayer player) {
      Iterator var2 = this.options.values().iterator();

      DialogOption option;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         option = (DialogOption)var2.next();
      } while(option == null || option.optionType != EnumOptionType.DialogOption || !option.hasDialog() || !option.isAvailable(player));

      return true;
   }

   public void readNBT(NBTTagCompound compound) {
      this.id = compound.getInteger("DialogId");
      this.readNBTPartial(compound);
   }

   public void readNBTPartial(NBTTagCompound compound) {
      this.version = compound.getInteger("ModRev");
      VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
      this.title = compound.getString("DialogTitle");
      this.text = compound.getString("DialogText");
      this.quest = compound.getInteger("DialogQuest");
      this.sound = compound.getString("DialogSound");
      this.command = compound.getString("DialogCommand");
      this.mail.readNBT(compound.getCompoundTag("DialogMail"));
      this.hideNPC = compound.getBoolean("DialogHideNPC");
      this.showWheel = compound.getBoolean("DialogShowWheel");
      this.disableEsc = compound.getBoolean("DialogDisableEsc");
      NBTTagList options = compound.getTagList("Options", 10);
      HashMap newoptions = new HashMap();

      for(int iii = 0; iii < options.tagCount(); ++iii) {
         NBTTagCompound option = options.getCompoundTagAt(iii);
         int opslot = option.getInteger("OptionSlot");
         DialogOption dia = new DialogOption();
         dia.readNBT(option.getCompoundTag("Option"));
         newoptions.put(Integer.valueOf(opslot), dia);
      }

      this.options = newoptions;
      this.availability.readFromNBT(compound);
      this.factionOptions.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("DialogId", this.id);
      return this.writeToNBTPartial(compound);
   }

   public NBTTagCompound writeToNBTPartial(NBTTagCompound compound) {
      compound.setString("DialogTitle", this.title);
      compound.setString("DialogText", this.text);
      compound.setInteger("DialogQuest", this.quest);
      compound.setString("DialogCommand", this.command);
      compound.setTag("DialogMail", this.mail.writeNBT());
      compound.setBoolean("DialogHideNPC", this.hideNPC);
      compound.setBoolean("DialogShowWheel", this.showWheel);
      compound.setBoolean("DialogDisableEsc", this.disableEsc);
      if(this.sound != null && !this.sound.isEmpty()) {
         compound.setString("DialogSound", this.sound);
      }

      NBTTagList options = new NBTTagList();
      Iterator var3 = this.options.keySet().iterator();

      while(var3.hasNext()) {
         int opslot = ((Integer)var3.next()).intValue();
         NBTTagCompound listcompound = new NBTTagCompound();
         listcompound.setInteger("OptionSlot", opslot);
         listcompound.setTag("Option", ((DialogOption)this.options.get(Integer.valueOf(opslot))).writeNBT());
         options.appendTag(listcompound);
      }

      compound.setTag("Options", options);
      this.availability.writeToNBT(compound);
      this.factionOptions.writeToNBT(compound);
      compound.setInteger("ModRev", this.version);
      return compound;
   }

   public boolean hasQuest() {
      return this.getQuest() != null;
   }

   public Quest getQuest() {
      return (Quest)QuestController.instance.quests.get(Integer.valueOf(this.quest));
   }

   public boolean hasOtherOptions() {
      Iterator var1 = this.options.values().iterator();

      DialogOption option;
      do {
         if(!var1.hasNext()) {
            return false;
         }

         option = (DialogOption)var1.next();
      } while(option == null || option.optionType == EnumOptionType.Disabled);

      return true;
   }

   public Dialog copy(EntityPlayer player) {
      Dialog dialog = new Dialog();
      dialog.id = this.id;
      dialog.text = this.text;
      dialog.title = this.title;
      dialog.category = this.category;
      dialog.quest = this.quest;
      dialog.sound = this.sound;
      dialog.mail = this.mail;
      dialog.command = this.command;
      dialog.hideNPC = this.hideNPC;
      dialog.showWheel = this.showWheel;
      dialog.disableEsc = this.disableEsc;
      Iterator var3 = this.options.keySet().iterator();

      while(var3.hasNext()) {
         int slot = ((Integer)var3.next()).intValue();
         DialogOption option = (DialogOption)this.options.get(Integer.valueOf(slot));
         if(option.optionType != EnumOptionType.DialogOption || option.hasDialog() && option.isAvailable(player)) {
            dialog.options.put(Integer.valueOf(slot), option);
         }
      }

      return dialog;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }
}
