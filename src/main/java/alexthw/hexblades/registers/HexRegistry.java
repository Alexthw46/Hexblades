package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.codex.CodexHexChapters;
import alexthw.hexblades.common.potions.EChargedEffect;
import alexthw.hexblades.compat.MalumCompat;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.network.FlameEffectPacket;
import alexthw.hexblades.network.MiningSwitchPacket;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.network.WeaponAwakenPacket;
import alexthw.hexblades.ritual.HexRituals;
import alexthw.hexblades.spells.HexSpells;
import alexthw.hexblades.util.CompatUtil;
import elucent.eidolon.Registry;
import elucent.eidolon.mixin.PotionBrewingMixin;
import elucent.eidolon.network.Networking;

import static alexthw.hexblades.util.CompatUtil.isMalumLoaded;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class HexRegistry {


    public static RegistryObject<MobEffect> CHARGED_EFFECT;
    public static RegistryObject<Potion> CHARGED_POTION;
    public static RegistryObject<Potion> WITHER_POTION;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MODID);
    public static final DeferredRegister<MobEffect> POTIONS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Hexblades.MODID);
    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTIONS, Hexblades.MODID);

    static {
        CHARGED_EFFECT = POTIONS.register("electro_charged", EChargedEffect::new);
        CHARGED_POTION = POTION_TYPES.register("electro_charged", () -> new Potion(new MobEffectInstance(CHARGED_EFFECT.get(), 3600)));
        WITHER_POTION = POTION_TYPES.register("wither", () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 100)));
    }

    public static void init(IEventBus hexbus) {
        hexbus.register(new HexTileEntityType());
        hexbus.register(new HexParticles());
        hexbus.addGenericListener(RecipeSerializer.class, HexSerializers::registerRecipeSerializers);

        HexBlock.BLOCKS.register(hexbus);
        ITEMS.register(hexbus);
        HexEntityType.ENTITIES.register(hexbus);
        HexTileEntityType.TILE_ENTITIES.register(hexbus);
        HexParticles.PARTICLES.register(hexbus);
        POTIONS.register(hexbus);
        POTION_TYPES.register(hexbus);
        HexStructures.STRUCTURES.register(hexbus);

        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
        HexSerializers.registerCmdArgTypesSerializers();
    }

    public static void post_init() {
        Network();
        HexRituals.init();
        CompatUtil.check(); //may be useless, already checked before
        if (isMalumLoaded()) {
            try {
                MalumCompat.altar();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        CodexHexChapters.init();
        PotionBrewingMixin.callAddMix(Potions.HARMING, Registry.DEATH_ESSENCE.get(), WITHER_POTION.get());
    }

    private static void Network() {
        //TODO stop leeching elu's network
        Networking.INSTANCE.registerMessage(90, FlameEffectPacket.class, FlameEffectPacket::encode, FlameEffectPacket::decode, FlameEffectPacket::consume);
        Networking.INSTANCE.registerMessage(91, RefillEffectPacket.class, RefillEffectPacket::encode, RefillEffectPacket::decode, RefillEffectPacket::consume);
        Networking.INSTANCE.registerMessage(98, MiningSwitchPacket.class, MiningSwitchPacket::encode, MiningSwitchPacket::decode, MiningSwitchPacket::consume);
        Networking.INSTANCE.registerMessage(99, WeaponAwakenPacket.class, WeaponAwakenPacket::encode, WeaponAwakenPacket::decode, WeaponAwakenPacket::consume);
    }


}
