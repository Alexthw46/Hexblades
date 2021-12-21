package alexthw.hexblades.deity;


import elucent.eidolon.deity.Deities;
import elucent.eidolon.deity.Deity;

import java.util.Arrays;
import java.util.List;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexDeities extends Deities {

    public static Deity HEX_DEITY;

    public static void registerDeity() {
        HEX_DEITY = Deities.register(new HexDeity(prefix("blade"), 0, 102, 255));
    }

    public static final List<String> DeityNames = Arrays.asList("blade", LIGHT_DEITY.getId().getPath(), DARK_DEITY.getId().getPath());

}
