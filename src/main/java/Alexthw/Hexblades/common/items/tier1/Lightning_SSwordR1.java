package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.registers.HexItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Lightning_SSwordR1 extends HexSwordItem {

    public Lightning_SSwordR1(Properties props) {
        super(4, -1.5F, props);
        tooltipText = "tooltip.HexSwordItem.thunder_knives";
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);
        boolean active = getAwakened(weapon);

        if (hasTwin(player)) {
            if (!active) {
                setAwakenedState(weapon, true);

                setAttackPower(weapon, devotion / 30);
                setAttackSpeed(weapon, devotion / 50);
            }
        } else {
            setAwakenedState(weapon, false);
            setAttackPower(weapon, 0);
            setAttackSpeed(weapon, 0);
        }
    }

    public boolean hasTwin(PlayerEntity player) {
        return player.getHeldItemOffhand().getItem() == HexItem.LIGHTNING_DAGGER_L.get();
    }

}
