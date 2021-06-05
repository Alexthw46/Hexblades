package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.WaterSaber1;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

import static net.minecraft.util.DamageSource.DROWN;

public class WaterSaber2 extends WaterSaber1 {

    public WaterSaber2(Properties props) {
        super(props);
        tooltipText = "tooltip.HexSwordItem.water_saber2";
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource(DROWN.getDamageType(), attacker).setDamageBypassesArmor(), 3.0f);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 10);
        setShielding(weapon, (float) (devotion / 15));
    }


}
