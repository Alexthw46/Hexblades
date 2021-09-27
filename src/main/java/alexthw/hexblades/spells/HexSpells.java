package alexthw.hexblades.spells;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.deity.HexDeities;
import elucent.eidolon.spell.PrayerSpell;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.Spell;
import elucent.eidolon.spell.Spells;
import net.minecraft.util.ResourceLocation;

public class HexSpells {

    public static Spell FIRE_TOUCH;
    public static Spell HEX_TOUCH;
    public static Spell HEX_PRAY;

    public static void RegisterSpells() {

        HEX_TOUCH = Spells.register(new HexTouchSpell(new ResourceLocation(Hexblades.MOD_ID, "hex_conversion"),
                Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN));
        HEX_PRAY = Spells.register(new PrayerSpell(new ResourceLocation(Hexblades.MOD_ID, "hex_pray"), HexDeities.HEX_DEITY,
                Signs.WICKED_SIGN, Signs.SOUL_SIGN, Signs.WICKED_SIGN));
        FIRE_TOUCH = Spells.register(new FireTouchSpell(new ResourceLocation(Hexblades.MOD_ID, "fire_touch"),
                Signs.SACRED_SIGN, Signs.SACRED_SIGN, Signs.SACRED_SIGN));

    }

}
