package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class FireBroad2 extends HexSwordItem {


    public FireBroad2(Properties props) {
        super(7, -2.7F, props);
        tooltipText = "tooltip.HexSwordItem.flame_sword2";
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
        user.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 15);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (target.hurtResistantTime > 0) {
                target.hurtResistantTime = 0;
                target.setFire(3);
                if (getAwakened(stack)) applyHexEffects(stack, target, (PlayerEntity) attacker);
            }
        }
        stack.setDamage(Math.max(stack.getDamage() - 10, 0));
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("magic", attacker).setDamageBypassesArmor(), (float) (getDevotion(attacker) / 15));
    }

}
