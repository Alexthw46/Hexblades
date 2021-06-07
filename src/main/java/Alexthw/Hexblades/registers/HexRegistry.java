package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.codex.CodexHexChapters;
import Alexthw.Hexblades.common.potions.EChargedEffect;
import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.ritual.HexRituals;
import Alexthw.Hexblades.spells.HexSpells;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;

import static Alexthw.Hexblades.registers.Registry.POTIONS;
import static Alexthw.Hexblades.registers.Registry.POTION_TYPES;

public class HexRegistry {


    public static RegistryObject<Effect> CHARGED_EFFECT;
    public static RegistryObject<Potion> CHARGED_POTION;

    static {
        CHARGED_EFFECT = POTIONS.register("electro_charged", EChargedEffect::new);
        CHARGED_POTION = POTION_TYPES.register("electro_charged", () -> new Potion(new EffectInstance(CHARGED_EFFECT.get(), 3600)));
    }

    public static void init() {
        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
    }

    public static void post_init() {

        HexRituals.init();
        CodexHexChapters.init();

    }


}
