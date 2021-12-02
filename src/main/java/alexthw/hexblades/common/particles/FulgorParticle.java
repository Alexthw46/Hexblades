package alexthw.hexblades.common.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import elucent.eidolon.ClientConfig;
import elucent.eidolon.ClientEvents;
import elucent.eidolon.particle.GenericParticle;
import elucent.eidolon.particle.GenericParticleData;
import elucent.eidolon.util.RenderUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class FulgorParticle extends GenericParticle {
    public FulgorParticle(ClientLevel world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    protected int getLightColor(float partialTicks) {
        return 15728880;
    }

    public void render(VertexConsumer b, Camera info, float pticks) {
        super.render(ClientConfig.BETTER_LAYERING.get() ? ClientEvents.getDelayedRender().getBuffer(RenderUtil.GLOWING_PARTICLE) : b, info, pticks);
    }
}
