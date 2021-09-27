package alexthw.hexblades.network;

import alexthw.hexblades.common.items.HexSwordItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WeaponAwakenPacket {

    public WeaponAwakenPacket() {

    }

    public static void encode(WeaponAwakenPacket object, PacketBuffer buffer) {
    }

    public static WeaponAwakenPacket decode(PacketBuffer buffer) {
        return new WeaponAwakenPacket();
    }

    public static void consume(WeaponAwakenPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER;

            PlayerEntity player = ctx.get().getSender();

            if (player != null && !player.level.isClientSide()) {
                ItemStack IStack = player.getItemInHand(Hand.MAIN_HAND);
                if (IStack.getItem() instanceof HexSwordItem) {
                    HexSwordItem hexblade = (HexSwordItem) IStack.getItem();
                    hexblade.recalculatePowers(IStack, player.level, player);
                }
            }
        });
        ctx.get().setPacketHandled(true);

    }
}
