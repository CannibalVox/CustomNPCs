package noppes.npcs.constants;


public enum EnumPlayerPacket {

   FollowerHire("FollowerHire", 0),
   FollowerExtend("FollowerExtend", 1),
   Trader("Trader", 2),
   FollowerState("FollowerState", 3),
   Transport("Transport", 4),
   BankUnlock("BankUnlock", 5),
   BankUpgrade("BankUpgrade", 6),
   Dialog("Dialog", 7),
   QuestLog("QuestLog", 8),
   QuestCompletion("QuestCompletion", 9),
   CheckQuestCompletion("CheckQuestCompletion", 10),
   BankSlotOpen("BankSlotOpen", 11),
   FactionsGet("FactionsGet", 12),
   MailGet("MailGet", 13),
   MailDelete("MailDelete", 14),
   MailSend("MailSend", 15),
   MailRead("MailRead", 16),
   MailboxOpenMail("MailboxOpenMail", 17),
   SignSave("SignSave", 18),
   SaveBook("SaveBook", 19),
   CompanionOpenInv("CompanionOpenInv", 20),
   RoleGet("RoleGet", 21),
   CompanionTalentExp("CompanionTalentExp", 22);
   // $FF: synthetic field
   private static final EnumPlayerPacket[] $VALUES = new EnumPlayerPacket[]{FollowerHire, FollowerExtend, Trader, FollowerState, Transport, BankUnlock, BankUpgrade, Dialog, QuestLog, QuestCompletion, CheckQuestCompletion, BankSlotOpen, FactionsGet, MailGet, MailDelete, MailSend, MailRead, MailboxOpenMail, SignSave, SaveBook, CompanionOpenInv, RoleGet, CompanionTalentExp};


   private EnumPlayerPacket(String var1, int var2) {}

}
