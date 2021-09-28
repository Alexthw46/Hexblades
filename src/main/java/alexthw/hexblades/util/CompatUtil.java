package alexthw.hexblades.util;


import net.minecraftforge.fml.ModList;

public class CompatUtil {

    public static final String MOD_ID_MALUM = "malum";
    public static final String MOD_ID_BOTANIA = "botania";
    public static final String MOD_ID_DRUIDCRAFT = "druidcraft";
    public static final String MOD_ID_OCCULTISM = "occultism";

    protected static boolean loadedMalum = false;
    protected static boolean loadedBotania = false;
    protected static boolean loadedOccultism = false;
    protected static boolean loadedDruid = false;


    public static boolean isMalumLoaded() {
        return loadedMalum;
    }

    public static boolean isBotaniaLoaded() {
        return loadedBotania;
    }

    public static boolean isDruidLoaded() {
        return loadedDruid;
    }

    public static boolean isOccultismLoaded() {
        return loadedOccultism;
    }

    public static void check() {

        loadedMalum = ModList.get().isLoaded(MOD_ID_MALUM);
        loadedBotania = ModList.get().isLoaded(MOD_ID_BOTANIA);
        loadedDruid = ModList.get().isLoaded(MOD_ID_DRUIDCRAFT);
        loadedOccultism = ModList.get().isLoaded(MOD_ID_OCCULTISM);
    }

}
