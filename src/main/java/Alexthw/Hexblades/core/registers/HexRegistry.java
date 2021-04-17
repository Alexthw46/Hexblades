package Alexthw.Hexblades.core.registers;

import Alexthw.Hexblades.patrons.HexDeities;
import Alexthw.Hexblades.spells.HexSpells;

public class HexRegistry {
    public static void init() {

        HexDeities.registerDeity();
        HexSpells.RegisterSpells();

    }
}
