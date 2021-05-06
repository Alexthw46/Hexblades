package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.Lightning_SSwordR1;
import Alexthw.Hexblades.registers.HexItem;
import net.minecraft.entity.player.PlayerEntity;

public class Lightning_SSwordR2 extends Lightning_SSwordR1 {
    public Lightning_SSwordR2(Properties props) {
        super(props);
    }

    @Override
    public boolean hasTwin(PlayerEntity player) {
        return player.getHeldItemOffhand().getItem() == HexItem.LIGHTNING_SSWORD_L.get();
    }
}
