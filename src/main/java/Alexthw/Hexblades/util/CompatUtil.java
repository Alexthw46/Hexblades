package Alexthw.Hexblades.util;


import net.minecraftforge.fml.ModList;

public class CompatUtil {

    public static final String MOD_ID_MALUM = "malum";

    protected static boolean loadedMalum = false;

    public static boolean isMalumLoaded() {
        return loadedMalum;
    }

    public static void check() {

        loadedMalum = ModList.get().isLoaded(MOD_ID_MALUM);

    }

}
