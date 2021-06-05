package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

import static net.minecraft.util.DamageSource.DROWN;

public class WaterSaber1 extends HexSwordItem {
    public float shield = 0;
    public WaterSaber1(Properties props) {
        super(2, -2.4F, props);
        tooltipText = "tooltip.HexSwordItem.water_saber";
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource(DROWN.getDamageType(), attacker).setDamageBypassesArmor(), 3.0f);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 20);
        setShielding(weapon, (float) (devotion / 30));
    }

    public void setShielding(ItemStack weapon, float dmgred) {
        if (getAwakened(weapon)) {
            shield = dmgred;
            return;
        }
        shield = 0;
    }

}
