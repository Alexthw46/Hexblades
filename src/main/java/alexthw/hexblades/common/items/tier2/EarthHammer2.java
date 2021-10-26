package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.EarthHammer1;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class EarthHammer2 extends EarthHammer1 {

    public EarthHammer2(Properties props) {
        super(9, -3.2F, props);
        baseMiningSpeed = newMiningSpeed = 8.0F;
        tooltipText = new TranslationTextComponent("tooltip.hexblades.earth_hammer2");
        mineSwitch = false;
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        float power = 1.5F;
        if (isActivated) {
            target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED2.get());
            target.addEffect(new EffectInstance(Effects.WEAKNESS, 300, 0));
            power = (float) (2.0F + getDevotion(attacker) / 20);
        }
        double X = attacker.getX() - target.getX();
        double Z = attacker.getZ() - target.getZ();
        target.knockback(power, X, Z);

    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, mineSwitch ? -8 : (devotion / COMMON.HammerDS2.get()));
        setMiningSpeed((float) (devotion / COMMON.HammerMS2.get()));
    }

}
