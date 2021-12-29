package alexthw.hexblades.spells;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.deity.HexDeities;
import elucent.eidolon.spell.*;
import net.minecraft.resources.ResourceLocation;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexSpells {

    public static Spell FIRE_TOUCH;
    public static Spell HEX_TOUCH;
    public static Spell HEX_PRAY;
    public static Spell HEX_SUMMON;
    public static Spell HEX_EVOLUTION;

    public static void RegisterSpells() {

        HEX_TOUCH = Spells.register(new HexTouchSpell(prefix("hex_debug"),
                Signs.WICKED_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN));
        HEX_PRAY = Spells.register(new PrayerSpell(prefix("hex_pray"), HexDeities.HEX_DEITY,
                Signs.WICKED_SIGN, Signs.SOUL_SIGN, Signs.WICKED_SIGN));
        HEX_EVOLUTION = Spells.register(new HexEvolutionSpell(prefix("hex_evolution"),
                Signs.SACRED_SIGN, Signs.WICKED_SIGN, Signs.SOUL_SIGN));
        FIRE_TOUCH = Spells.register(new FireTouchSpell(prefix("fire_touch"),
                Signs.FLAME_SIGN, Signs.FLAME_SIGN, Signs.FLAME_SIGN));
        HEX_SUMMON = Spells.register(new HexSummonerSpell(prefix("activate_pedestals"),
                Signs.SACRED_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.SACRED_SIGN));
    }

}
