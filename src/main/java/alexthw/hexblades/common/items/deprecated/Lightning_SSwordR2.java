package alexthw.hexblades.common.items.deprecated;

import alexthw.hexblades.common.items.deprecated.Lightning_SSwordR1;
import alexthw.hexblades.registers.HexItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class Lightning_SSwordR2 extends Lightning_SSwordR1 {
    public Lightning_SSwordR2(Properties props) {
        super(props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        if (!hasTwin(player)) {
            setAwakenedState(weapon, false);
        } else if (getAwakened(weapon)) {
            setAwakenedState(weapon, weapon.getDamageValue() <= getMaxDamage(weapon) * 0.9);
        } else {
            setAwakenedState(weapon, true);
        }

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, devotion , COMMON.DualsDS2.get() );
        setAttackSpeed(weapon, devotion , COMMON.DualsAS2.get() );
    }

    @Override
    public boolean hasTwin(Player player) {
        return player.getOffhandItem().getItem() == HexItem.LIGHTNING_SSWORD_L.get();
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened, int souls) {
        if (hasTwin(user)) {
            if (awakened) {
                user.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 0, false, false));
                user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false));
            } else {
                user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0, false, false));
            }
        }
    }
}
