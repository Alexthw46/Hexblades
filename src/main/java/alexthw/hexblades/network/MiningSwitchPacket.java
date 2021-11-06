package alexthw.hexblades.network;

import alexthw.hexblades.common.items.tier1.EarthHammer1;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MiningSwitchPacket {
    public MiningSwitchPacket() {

    }

    public static void encode(MiningSwitchPacket object, PacketBuffer buffer) {
    }

    public static MiningSwitchPacket decode(PacketBuffer buffer) {
        return new MiningSwitchPacket();
    }

    public static void consume(MiningSwitchPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER;

            PlayerEntity player = ctx.get().getSender();

            if (player != null && !player.level.isClientSide()) {
                ItemStack IStack = player.getItemInHand(Hand.MAIN_HAND);
                if (IStack.getItem() instanceof EarthHammer1) {
                    EarthHammer1 hexblade = (EarthHammer1) IStack.getItem();
                    hexblade.switchMining(IStack);
                    hexblade.recalculatePowers(IStack, player.level, player);
                }
            }
        });
        ctx.get().setPacketHandled(true);

    }
}
