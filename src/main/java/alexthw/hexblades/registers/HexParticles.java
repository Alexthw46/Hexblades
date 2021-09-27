package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.particles.FulgorParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HexParticles {
    static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Hexblades.MOD_ID);
    public static RegistryObject<FulgorParticleType> FULGOR_PARTICLE;


    static {

        FULGOR_PARTICLE = PARTICLES.register("fulgor_particle", FulgorParticleType::new);

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerFactories(ParticleFactoryRegisterEvent evt) {

        Minecraft.getInstance().particleEngine.register(FULGOR_PARTICLE.get(), FulgorParticleType.Factory::new);

    }

}
