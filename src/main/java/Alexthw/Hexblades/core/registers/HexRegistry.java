package Alexthw.Hexblades.core.registers;

import Alexthw.Hexblades.codex.CodexHexChapters;
import Alexthw.Hexblades.patrons.HexDeities;
import Alexthw.Hexblades.spells.HexSpells;

public class HexRegistry {
    public static void init() {

        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
        CodexHexChapters.init();

    }
}
