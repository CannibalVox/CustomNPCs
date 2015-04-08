package noppes.npcs;

import noppes.npcs.entity.EntityNPCInterface;

public interface IChatMessages {

   void addMessage(String var1, EntityNPCInterface var2);

   void renderMessages(double var1, double var3, double var5, float var7);
}
