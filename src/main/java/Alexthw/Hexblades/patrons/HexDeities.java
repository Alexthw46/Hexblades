package Alexthw.Hexblades.patrons;


import Alexthw.Hexblades.Hexblades;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.deity.Deity;
import net.minecraft.util.ResourceLocation;

public class HexDeities {

    public static void registerDeity() {
        Deity HEX_DEITY = Deities.register(new HexDeity(new ResourceLocation(Hexblades.MOD_ID, "blade"), 0, 0, 0));
    }
}
