package alexthw.hexblades.network;

import alexthw.hexblades.common.items.IHexblade;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WeaponAwakenPacket {

    public WeaponAwakenPacket() {

    }

    public static void encode(WeaponAwakenPacket object, FriendlyByteBuf buffer) {
    }

    public static WeaponAwakenPacket decode(FriendlyByteBuf buffer) {
        return new WeaponAwakenPacket();
    }

    public static void consume(WeaponAwakenPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER;

            Player player = ctx.get().getSender();

            if (player != null && !player.level.isClientSide()) {
                ItemStack IStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (IStack.getItem() instanceof IHexblade hexblade) {
                    hexblade.recalculatePowers(IStack, player.level, player);
                }
            }
        });
        ctx.get().setPacketHandled(true);

    }
}
