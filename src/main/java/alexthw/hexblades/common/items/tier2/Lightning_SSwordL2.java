package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.Lightning_SSwordL1;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class Lightning_SSwordL2 extends Lightning_SSwordL1 {
    public Lightning_SSwordL2(Properties props) {
        super(props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        boolean awakening = getAwakened(weapon);
        double devotion = getDevotion(player);

        setAttackPower(weapon, awakening, devotion / COMMON.DualsDS2.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.DualsAS2.get());
    }

    @Override
    public boolean hasTwin(Player player) {
        ItemStack is = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (is.getItem() instanceof Lightning_SSwordR2) {
            return getAwakened(is);
        }
        return false;
    }
}
