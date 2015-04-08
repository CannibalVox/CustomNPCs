package net.minecraft.client.resources;

import java.io.File;
import net.minecraft.client.resources.AbstractResourcePack;

public class NPCResourceHelper {

   public static File getPackFile(AbstractResourcePack pack) {
      return pack.resourcePackFile;
   }
}
