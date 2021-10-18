package alexthw.hexblades.deity;


import alexthw.hexblades.Hexblades;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.deity.Deity;
import net.minecraft.util.ResourceLocation;

public class HexDeities {

    public static Deity HEX_DEITY;
    public static ResourceLocation temp_id;

    public static void registerDeity() {
        temp_id = new ResourceLocation(Hexblades.MODID, "blade");
        HEX_DEITY = Deities.register(new HexDeity(temp_id, 0, 102, 255));
    }
}
