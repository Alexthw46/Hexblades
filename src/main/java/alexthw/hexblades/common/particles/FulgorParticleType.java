package alexthw.hexblades.common.particles;

import com.mojang.serialization.Codec;
import elucent.eidolon.particle.GenericParticleData;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleType;

public class FulgorParticleType extends ParticleType<GenericParticleData> {
    public FulgorParticleType() {
        super(false, GenericParticleData.DESERIALIZER);
    }

    public Codec<GenericParticleData> codec() {
        return GenericParticleData.codecFor(this);
    }

    public static class Factory implements ParticleProvider<GenericParticleData> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(GenericParticleData data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            FulgorParticle ret = new FulgorParticle(world, data, x, y, z, mx, my, mz);
            ret.pickSprite(this.sprite);
            return ret;
        }
    }
}
