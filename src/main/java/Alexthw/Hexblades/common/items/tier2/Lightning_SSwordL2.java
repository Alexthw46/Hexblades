package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.Lightning_SSwordL1;
import Alexthw.Hexblades.registers.HexItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class Lightning_SSwordL2 extends Lightning_SSwordL1 {
    public Lightning_SSwordL2(Properties props) {
        super(props);
    }

    @Override
    public boolean hasTwin(PlayerEntity player) {
        return player.getHeldItem(Hand.MAIN_HAND).getItem() == HexItem.LIGHTNING_SSWORD_R.get();
    }
}
