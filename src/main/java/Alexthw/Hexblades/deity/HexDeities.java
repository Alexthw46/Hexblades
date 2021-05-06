package Alexthw.Hexblades.deity;


import Alexthw.Hexblades.Hexblades;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.deity.Deity;
import net.minecraft.util.ResourceLocation;

public class HexDeities {

    public static Deity HEX_DEITY;

    public static void registerDeity() {
       HEX_DEITY = Deities.register(new HexDeity(new ResourceLocation(Hexblades.MOD_ID, "blade"), 0, 102, 255));
    }
}
