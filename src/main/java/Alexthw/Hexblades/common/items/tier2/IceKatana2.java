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
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (target.hurtResistantTime > 0) {
                target.hurtResistantTime = 0;
                super.applyHexEffects(stack, target, (PlayerEntity) attacker);
                if (getAwakened(stack)) applyHexEffects(stack, target, (PlayerEntity) attacker);
            }
        }
        stack.setDamage(Math.max(stack.getDamage() - 10, 0));
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.addPotionEffect(new EffectInstance(Registry.CHILLED_EFFECT.get(), 300, 0));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 10);
        setAttackSpeed(weapon, devotion / 30);
    }
}
