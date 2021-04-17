package Alexthw.Hexblades.spells;

import Alexthw.Hexblades.Hexblades;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.Spell;
import elucent.eidolon.spell.Spells;
import net.minecraft.util.ResourceLocation;

public class HexSpells {
    public static void RegisterSpells() {
        Spell HEX_TOUCH =
                Spells.register(new HexTouchSpell(new ResourceLocation(Hexblades.MOD_ID, "hex_conversion"),
                        Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN
                ));
    }
}
