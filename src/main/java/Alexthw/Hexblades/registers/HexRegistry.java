package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.codex.CodexHexChapters;
import Alexthw.Hexblades.patrons.HexDeities;
import Alexthw.Hexblades.ritual.HexRituals;
import Alexthw.Hexblades.spells.HexSpells;

public class HexRegistry {
    public static void init() {

        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
    }

    public static void post_init(){

        HexRituals.init();
        CodexHexChapters.init();

    }
}
