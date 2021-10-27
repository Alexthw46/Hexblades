package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.Lightning_SSwordL1;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static java.lang.Math.max;

public class Lightning_SSwordL2 extends Lightning_SSwordL1 {
    public Lightning_SSwordL2(Properties props) {
        super(props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        if (isActivated) {
            double devotion = getDevotion(player);
            rechargeTick = max(1, (int) devotion / COMMON.DualsRR.get());
            setAttackPower(weapon, devotion / COMMON.DualsDS2.get());
            setAttackSpeed(weapon, devotion / COMMON.DualsAS2.get());
        }
    }

    @Override
    public boolean hasTwin(PlayerEntity player) {
        ItemStack is = player.getItemInHand(Hand.MAIN_HAND);
        if (is.getItem() instanceof Lightning_SSwordR2) {
            return ((Lightning_SSwordR2) (is.getItem())).isActivated();
        }
        return false;
    }
}
