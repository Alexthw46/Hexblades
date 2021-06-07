package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class WaterSaber1 extends HexSwordItem {

    public float shield = 0;

    public WaterSaber1(Properties props) {
        super(5, -2.4F, props);
        tooltipText = "tooltip.HexSwordItem.water_saber";
    }

    public WaterSaber1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity entity, boolean awakened) {
        if (awakened) entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0, false, false));
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 25);
        setShielding(weapon, (float) (devotion / 20));
    }

    public void setShielding(ItemStack weapon, float dmgred) {
        if (getAwakened(weapon)) {
            shield = dmgred;
            return;
        }
        shield = 0;
    }

}
