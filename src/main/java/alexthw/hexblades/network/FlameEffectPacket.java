package alexthw.hexblades.network;

import alexthw.hexblades.deity.HexDeities;
import elucent.eidolon.Eidolon;
import elucent.eidolon.Registry;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlameEffectPacket {
    BlockPos pos;

    public FlameEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(FlameEffectPacket object, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(object.pos);
    }

    public static FlameEffectPacket decode(FriendlyByteBuf buffer) {
        return new FlameEffectPacket(buffer.readBlockPos());
    }

    public static void consume(FlameEffectPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Eidolon.proxy.getWorld();
            if (world != null) {
                Deity HexDeity = HexDeities.HEX_DEITY;

                BlockPos pos = packet.pos;
                double x = (double) pos.getX() + 0.5D;
                double y = (double) pos.getY() + 0.1D;
                double z = (double) pos.getZ() + 0.5D;
                float r = HexDeity.getRed();
                float g = HexDeity.getGreen();
                float b = HexDeity.getBlue();
                Particles.create(Registry.FLAME_PARTICLE).setAlpha(1.0F, 0.0F).setScale(0.25F, 0.0F).setLifetime(20).randomOffset(0.5D, 0.0D).randomVelocity(0.0D, 0.375D).addVelocity(0.0D, 0.125D, 0.0D).setColor(r, g, b, r, g * 0.5F, b * 1.5F).setSpin(0.2F).repeat(world, x, y, z, 6);

            }

        });
        ctx.get().setPacketHandled(true);
    }
}

