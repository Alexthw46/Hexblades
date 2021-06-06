package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.Lightning_SSwordR1;
import Alexthw.Hexblades.registers.HexItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class Lightning_SSwordR2 extends Lightning_SSwordR1 {
    public Lightning_SSwordR2(Properties props) {
        super(props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);
        boolean active = getAwakened(weapon);

        if (hasTwin(player)) {
            if (!active) {
                setAwakenedState(weapon, true);

                setAttackPower(weapon, devotion / 20);
                setAttackSpeed(weapon, devotion / 40);
            }
        } else {
            setAwakenedState(weapon, false);
            setAttackPower(weapon, 0);
            setAttackSpeed(weapon, 0);
        }
    }

    @Override
    public boolean hasTwin(PlayerEntity player) {
        return player.getHeldItemOffhand().getItem() == HexItem.LIGHTNING_SSWORD_L.get();
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
        if (hasTwin(user)) {
            if (awakened) {
                user.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 200));
                user.addPotionEffect(new EffectInstance(Effects.SPEED, 200, 1));
            } else {
                user.addPotionEffect(new EffectInstance(Effects.SPEED, 200));
            }
        }
    }
}
