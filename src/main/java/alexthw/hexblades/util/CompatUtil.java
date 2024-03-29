package alexthw.hexblades.util;


import net.minecraftforge.fml.ModList;

public class CompatUtil {

    public static final String MOD_ID_MALUM = "malum";
    public static final String MOD_ID_BOTANIA = "botania";
    public static final String MOD_ID_DRUIDCRAFT = "druidcraft";
    public static final String MOD_ID_OCCULTISM = "occultism";
    public static final String MOD_ID_ARSNOUVEAU = "ars_nouveau";

    protected static boolean loadedMalum = false;
    protected static boolean loadedBotania = false;
    protected static boolean loadedOccultism = false;
    protected static boolean loadedDruid = false;
    protected static boolean loadedArsNov = false;


    public static boolean isMalumLoaded() {
        return loadedMalum;
    }

    public static boolean isBotaniaLoaded() {
        return loadedBotania;
    }

    public static boolean isArsNovLoaded() {
        return loadedArsNov;
    }

    public static void check() {

        ModList modList = ModList.get();

        loadedMalum = modList.isLoaded(MOD_ID_MALUM);
        loadedBotania = modList.isLoaded(MOD_ID_BOTANIA);
        loadedDruid = modList.isLoaded(MOD_ID_DRUIDCRAFT);
        loadedOccultism = modList.isLoaded(MOD_ID_OCCULTISM);
        loadedArsNov = modList.isLoaded(MOD_ID_ARSNOUVEAU);

    }

}
