package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.codex.CodexHexChapters;
import Alexthw.Hexblades.common.particles.FulgorParticleType;
import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.ritual.HexRituals;
import Alexthw.Hexblades.spells.HexSpells;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HexRegistry {

    static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Hexblades.MOD_ID);
    ;
    public static RegistryObject<FulgorParticleType> FULGOR_PARTICLE;


    static {

        //FULGOR_PARTICLE = PARTICLES.register("fulgor_particle", FulgorParticleType::new);

    }

    public static void init() {
        HexDeities.registerDeity();
        HexSpells.RegisterSpells();
    }

    public static void post_init() {

        HexRituals.init();
        CodexHexChapters.init();

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerFactories(ParticleFactoryRegisterEvent evt) {

        Minecraft.getInstance().particles.registerFactory(FULGOR_PARTICLE.get(), FulgorParticleType.Factory::new);

    }
}
