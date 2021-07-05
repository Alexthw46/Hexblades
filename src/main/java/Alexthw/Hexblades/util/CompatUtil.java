package Alexthw.Hexblades.util;


import net.minecraftforge.fml.ModList;

public class CompatUtil {

    public static final String MOD_ID_MALUM = "malum";
    public static final String MOD_ID_BOTANIA = "botania";


    protected static boolean loadedMalum = false;
    protected static boolean loadedBotania = false;


    public static boolean isMalumLoaded() {
        return loadedMalum;
    }

    public static boolean isBotaniaLoaded() {
        return loadedBotania;
    }

    public static void check() {

        loadedMalum = ModList.get().isLoaded(MOD_ID_MALUM);
        loadedBotania = ModList.get().isLoaded(MOD_ID_BOTANIA);

    }

}
