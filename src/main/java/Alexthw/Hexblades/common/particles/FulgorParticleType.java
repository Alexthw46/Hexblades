package Alexthw.Hexblades.common.particles;

import com.mojang.serialization.Codec;
import elucent.eidolon.particle.GenericParticleData;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleType;

public class FulgorParticleType extends ParticleType<GenericParticleData> {
    public FulgorParticleType() {
        super(false, GenericParticleData.DESERIALIZER);
    }

    public Codec<GenericParticleData> func_230522_e_() {
        return GenericParticleData.codecFor(this);
    }

    public static class Factory implements IParticleFactory<GenericParticleData> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        public Particle makeParticle(GenericParticleData data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
            FulgorParticle ret = new FulgorParticle(world, data, x, y, z, mx, my, mz);
            ret.selectSpriteRandomly(this.sprite);
            return ret;
        }
    }
}
