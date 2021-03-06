package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.EarthHammer1;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EarthHammer2 extends EarthHammer1 {

    public EarthHammer2(Properties props) {
        super(9, -3.2F, props, 7.0F);
        baseMiningSpeed = 8.0F;
        newMiningSpeed = baseMiningSpeed;
        tooltipText = "tooltip.HexSwordItem.earth_hammer2";
        mineSwitch = false;
    }

    @Override
    protected boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        float power = 1.5F;
        if (isActivated) {
            target.attackEntityFrom(new EntityDamageSource("anvil", attacker).setDamageBypassesArmor(), 2.0f);
            target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 300, 0));
            power = (float) (2.0F + getDevotion(attacker) / 20);
        }
        double X = attacker.getPosX() - target.getPosX();
        double Z = attacker.getPosZ() - target.getPosZ();
        target.applyKnockback(power, X, Z);

    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, mineSwitch ? -8 : (devotion / 15));
        setMiningSpeed((float) (devotion));
    }

}
