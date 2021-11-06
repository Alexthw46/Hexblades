package alexthw.hexblades.registers;

import alexthw.hexblades.codex.CodexHexChapters;
import alexthw.hexblades.common.potions.EChargedEffect;
import alexthw.hexblades.compat.CrucibleCompatHandler;
import alexthw.hexblades.compat.MalumCompat;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.network.FlameEffectPacket;
import alexthw.hexblades.network.MiningSwitchPacket;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.network.WeaponAwakenPacket;
import alexthw.hexblades.ritual.HexRituals;
import alexthw.hexblades.spells.HexSpells;
import alexthw.hexblades.temp.TempRecipes;
import alexthw.hexblades.util.CompatUtil;
import elucent.eidolon.mixin.PotionBrewingMixin;
import elucent.eidolon.network.Networking;
import net.minecraft.potion.*;
import net.minecraftforge.fml.RegistryObject;

import java.lang.reflect.InvocationTargetException;

import static alexthw.hexblades.registers.Registry.POTIONS;
import static alexthw.hexblades.registers.Registry.POTION_TYPES;
import static alexthw.hexblades.util.CompatUtil.isMalumLoaded;
import static elucent.eidolon.Registry.DEATH_ESSENCE;

public class HexRegistry {


    public static RegistryObject<Effect> CHARGED_EFFECT;
    public static RegistryObject<Potion> CHARGED_POTION;
    public static RegistryObject<Potion> WITHER_POTION;


    static {
        CHARGED_EFFECT = POTIONS.register("electro_charged", EChargedEffect::new);
        CHARGED_POTION = POTION_TYPES.register("electro_charged", () -> new Potion(new EffectInstance(CHARGED_EFFECT.get(), 3600)));
        WITHER_POTION = POTION_TYPES.register("wither", () -> new Potion(new EffectInstance(Effects.WITHER, 100)));
    }

    public static void init() {
        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
        HexSerializers.registerCmdArgTypesSerializers();
    }

    public static void post_init() {
        Network();
        TempRecipes.init();
        HexRituals.init();
        CompatUtil.check(); //may be useless, already checked before
        CrucibleCompatHandler.start();
        if (isMalumLoaded()) {
            try {
                MalumCompat.altar();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        CodexHexChapters.init();
        PotionBrewingMixin.callAddMix(Potions.HARMING, DEATH_ESSENCE.get(), WITHER_POTION.get());
    }

    private static void Network() {
        Networking.INSTANCE.registerMessage(90, FlameEffectPacket.class, FlameEffectPacket::encode, FlameEffectPacket::decode, FlameEffectPacket::consume);
        Networking.INSTANCE.registerMessage(91, RefillEffectPacket.class, RefillEffectPacket::encode, RefillEffectPacket::decode, RefillEffectPacket::consume);
        Networking.INSTANCE.registerMessage(98, MiningSwitchPacket.class, MiningSwitchPacket::encode, MiningSwitchPacket::decode, MiningSwitchPacket::consume);
        Networking.INSTANCE.registerMessage(99, WeaponAwakenPacket.class, WeaponAwakenPacket::encode, WeaponAwakenPacket::decode, WeaponAwakenPacket::consume);
    }


}
