package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.IceKatana1;
import elucent.eidolon.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class IceKatana2 extends IceKatana1 {
    public IceKatana2(Properties props) {
        super(5, -2.2F, props);
        tooltipText = "tooltip.HexSwordItem.ice_katana2";
    }

    @Override
    protected boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        super.applyHexEffects(stack, target, attacker);
        if (getAwakened(stack)) {
            target.addPotionEffect(new EffectInstance(Registry.CHILLED_EFFECT.get(), 100, 0));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 10);
        setAttackSpeed(weapon, devotion / 30);
    }
}
