package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.Lightning_SSwordR1;
import alexthw.hexblades.registers.HexItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class Lightning_SSwordR2 extends Lightning_SSwordR1 {
    public Lightning_SSwordR2(Properties props) {
        super(props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        if (hasTwin(player)) {
            if (!isActivated) {
                setAwakenedState(weapon, true);

                setAttackPower(weapon, devotion / COMMON.DualsDS2.get());
                setAttackSpeed(weapon, devotion / COMMON.DualsAS2.get());
            }
        } else {
            setAwakenedState(weapon, false);
            setAttackPower(weapon, 0);
            setAttackSpeed(weapon, 0);
        }
    }

    @Override
    public boolean hasTwin(PlayerEntity player) {
        return player.getOffhandItem().getItem() == HexItem.LIGHTNING_SSWORD_L.get();
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
        if (hasTwin(user)) {
            if (awakened) {
                user.addEffect(new EffectInstance(Effects.JUMP, 200, 0, false, false));
                user.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 200, 1, false, false));
            } else {
                user.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 200, 0, false, false));
            }
        }
    }
}