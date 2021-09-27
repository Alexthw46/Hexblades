package alexthw.hexblades.common.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import elucent.eidolon.ClientConfig;
import elucent.eidolon.ClientEvents;
import elucent.eidolon.particle.GenericParticle;
import elucent.eidolon.particle.GenericParticleData;
import elucent.eidolon.util.RenderUtil;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

public class FulgorParticle extends GenericParticle {
    public FulgorParticle(ClientWorld world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    protected int getLightColor(float partialTicks) {
        return 15728880;
    }

    public void render(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.render(ClientConfig.BETTER_LAYERING.get() ? ClientEvents.getDelayedRender().getBuffer(RenderUtil.GLOWING_PARTICLE) : b, info, pticks);
    }
}
