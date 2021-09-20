package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.codex.CodexHexChapters;
import Alexthw.Hexblades.common.potions.EChargedEffect;
import Alexthw.Hexblades.compat.MalumCompat;
import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.network.FlameEffectPacket;
import Alexthw.Hexblades.network.RefillEffectPacket;
import Alexthw.Hexblades.network.WeaponAwakenPacket;
import Alexthw.Hexblades.ritual.HexRituals;
import Alexthw.Hexblades.spells.HexSpells;
import Alexthw.Hexblades.temp.TempRecipes;
import Alexthw.Hexblades.util.CompatUtil;
import elucent.eidolon.network.Networking;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.InvocationTargetException;

import static Alexthw.Hexblades.registers.Registry.POTIONS;
import static Alexthw.Hexblades.registers.Registry.POTION_TYPES;
import static Alexthw.Hexblades.util.CompatUtil.isMalumLoaded;

public class HexRegistry {


    public static RegistryObject<Effect> CHARGED_EFFECT;
    public static RegistryObject<Potion> CHARGED_POTION;

    public static final KeyBinding HEXBLADE_KEYBINDING = new KeyBinding("key.hexblades.awake", GLFW.GLFW_KEY_H, "key.categories.misc");

    static {
        CHARGED_EFFECT = POTIONS.register("electro_charged", EChargedEffect::new);
        CHARGED_POTION = POTION_TYPES.register("electro_charged", () -> new Potion(new EffectInstance(CHARGED_EFFECT.get(), 3600)));
    }

    public static void init() {
        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
    }

    public static void post_init() {
        Network();
        TempRecipes.init();
        HexRituals.init();
        CompatUtil.check();
        if (isMalumLoaded()) {
            try {
                MalumCompat.start();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        CodexHexChapters.init();
    }

    private static void Network() {
        Networking.INSTANCE.registerMessage(90, FlameEffectPacket.class, FlameEffectPacket::encode, FlameEffectPacket::decode, FlameEffectPacket::consume);
        Networking.INSTANCE.registerMessage(91, RefillEffectPacket.class, RefillEffectPacket::encode, RefillEffectPacket::decode, RefillEffectPacket::consume);
        Networking.INSTANCE.registerMessage(99, WeaponAwakenPacket.class, WeaponAwakenPacket::encode, WeaponAwakenPacket::decode, WeaponAwakenPacket::consume);
    }


}
