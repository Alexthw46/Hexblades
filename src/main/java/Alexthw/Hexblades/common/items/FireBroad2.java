package Alexthw.Hexblades.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FireBroad2 extends HexSwordItem {


    public FireBroad2(Properties props) {
        super(3, -2.9F, props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon,devotion/5);
    }
}
