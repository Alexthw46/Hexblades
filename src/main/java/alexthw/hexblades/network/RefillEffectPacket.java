package alexthw.hexblades.network;

import elucent.eidolon.Eidolon;
import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class RefillEffectPacket {

    BlockPos pos;
    float size;

    public RefillEffectPacket(BlockPos pos, float size) {
        this.pos = pos;
        this.size = size;
    }

    public static void encode(RefillEffectPacket object, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(object.pos);
        buffer.writeFloat(object.size);
    }

    public static RefillEffectPacket decode(FriendlyByteBuf buffer) {
        return new RefillEffectPacket(buffer.readBlockPos(), buffer.readFloat());
    }

    public static void consume(RefillEffectPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Eidolon.proxy.getWorld();
            if (world != null) {
                BlockPos pos = packet.pos;
                float size = packet.size;
                double x = (double) pos.getX() + 0.5D;
                double y = (double) pos.getY() + (size < 1 ? 1.1D : 0.85D);
                double z = (double) pos.getZ() + 0.5D;
                float r = 0.25F;
                float g = 0.5F;
                float b = 1.0F;
                Particles.create(Registry.BUBBLE_PARTICLE).setAlpha(1.0F, 0.0F).setScale(0.075F).setLifetime(10).randomOffset(0.5D * size, 0.0D).randomVelocity(0.0D, 0.1D).addVelocity(0.0D, 0.05D, 0.0D).setColor(r, g, b, r, g * 0.5F, b * 1.5F).repeat(world, x, y, z, 10);

            }

        });
        ctx.get().setPacketHandled(true);
    }
}
